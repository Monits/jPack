package com.monits.jpack;

import java.lang.reflect.Field;

import com.monits.jpack.annotation.DependsOn;
import com.monits.jpack.annotation.FixedLength;
import com.monits.jpack.annotation.Unsigned;
import com.monits.jpack.annotation.UseCodec;
import com.monits.jpack.codec.ByteCodec;
import com.monits.jpack.codec.Codec;
import com.monits.jpack.codec.FixedArrayCodec;
import com.monits.jpack.codec.FixedStringCodec;
import com.monits.jpack.codec.ObjectCodec;
import com.monits.jpack.codec.UnsignedByteCodec;
import com.monits.jpack.codec.UnsignedIntCodec;
import com.monits.jpack.codec.UnsignedShortCodec;
import com.monits.jpack.codec.VariableArrayCodec;

public class CodecFactory {
	
	private CodecFactory() {
	}

	/**
	 * Get the {@link Codec} for a given type.
	 * 
	 * @param clz The type to get the codec for.
	 * 
	 * @return A codec
	 */
	public static <E> Codec<E> get(Class <E> clz) {
		return new ObjectCodec<E>(clz);
	}
	
	/**
	 * Get a suitable codec given a Field object.
	 * 
	 * This method is for internal use. Do not use it unless you're
	 * <b>reaaaaaaaaaaaaaaaaaaaally</b> sure of what you're doing.
	 * 
	 * This checks the annotations present in the field to detect the most 
	 * suitable codec for the given field.
	 * 
	 * @param field The field that needs a codec.
	 * 
	 * @return The most suitable codec, or null if none was found.
	 */
	public static Codec<?> get(Field field) {
		
		Codec<?> codec = null;
		
		if (field.isAnnotationPresent(FixedLength.class)) {
			codec = buildFixedLengthCodec(field);
		} else if (field.isAnnotationPresent(DependsOn.class)) {
			codec = buildVariableLengthCodec(field);
		} else if (field.isAnnotationPresent(UseCodec.class)) {
			
			UseCodec ann = field.getAnnotation(UseCodec.class);
			try {
				return (Codec<?>) ann.value().newInstance();
			} catch (InstantiationException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
			
 		}
		
		if (codec == null) {
			return buildSimpleCodec(field, field.getType());
		} else {
			return codec;
		}
		
	}

	private static Codec<?> buildVariableLengthCodec(Field field) {
		
		Class<?> enclosingType = field.getType();
		if (!enclosingType.isArray()) {
			return null;
		}
		
		Class<?> type = enclosingType.getComponentType();
		Codec<Object> codec = buildCodec(field, type);
		
		if (codec == null) {
			return null;
		}
		
		return new VariableArrayCodec(type, codec);
	}

	private static Codec<?> buildFixedLengthCodec(Field field) {
		
		int length = field.getAnnotation(FixedLength.class).value();
		Class<?> enclosingType = field.getType();
		
		if (enclosingType.isAssignableFrom(String.class)) {
			return new FixedStringCodec(length);
		} else if (!enclosingType.isArray()) {
			return null;
		}
		
		Class<?> type = enclosingType.getComponentType();
		Codec<Object> codec = buildCodec(field, type);
		
		if (codec == null) {
			return null;
		}
		
		return new FixedArrayCodec(type, codec, length);
	}

	private static Codec<Object> buildCodec(Field field, Class<?> type) {
		
		if (field.isAnnotationPresent(UseCodec.class)) {
			
			UseCodec ann = field.getAnnotation(UseCodec.class);
			try {
				return (Codec<Object>) ann.value().newInstance();
			} catch (InstantiationException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
		} else {
			return (Codec<Object>) buildSimpleCodec(field, type);
		}
	}

	private static Codec<?> buildSimpleCodec(Field field, Class<?> type) {
		
		boolean unsigned = field.isAnnotationPresent(Unsigned.class);

		if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
			if (unsigned) {
				return new UnsignedIntCodec();
			}
		} else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
			if (unsigned) {
				return new UnsignedShortCodec();
			}
		} else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
			if (unsigned) {
				return new UnsignedByteCodec();
			}
		} else if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(byte.class)) {
			return new ByteCodec();
		} else {
			return new ObjectCodec(type);
		}
		
		return null;
	}

}
