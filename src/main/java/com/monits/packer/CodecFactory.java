package com.monits.packer;

import java.lang.reflect.Field;

import com.monits.packer.annotation.DependsOn;
import com.monits.packer.annotation.FixedLength;
import com.monits.packer.annotation.Unsigned;
import com.monits.packer.annotation.UseCodec;
import com.monits.packer.codec.ByteCodec;
import com.monits.packer.codec.Codec;
import com.monits.packer.codec.FixedArrayCodec;
import com.monits.packer.codec.FixedStringCodec;
import com.monits.packer.codec.ObjectCodec;
import com.monits.packer.codec.UnsignedByteCodec;
import com.monits.packer.codec.UnsignedIntCodec;
import com.monits.packer.codec.UnsignedShortCodec;
import com.monits.packer.codec.VariableArrayCodec;

public class CodecFactory {
	
	private CodecFactory() {
	}
	
	public static <E> Codec<E> get(Class <E> clz) {
		return new ObjectCodec<E>(clz);
	}
	
	public static Codec<?> get(Field field) {
		
		if (field.isAnnotationPresent(FixedLength.class)) {
			return buildFixedLengthCodec(field);
		} else if (field.isAnnotationPresent(UseCodec.class)) {
			
			UseCodec ann = field.getAnnotation(UseCodec.class);
			try {
				return (Codec<?>) ann.value().newInstance();
			} catch (InstantiationException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
			
		} else if (field.isAnnotationPresent(DependsOn.class)) {
			return buildVariableLengthCodec(field);
 		} else {
			return buildSimpleCodec(field, field.getType());
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
