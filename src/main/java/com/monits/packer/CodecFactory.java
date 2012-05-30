package com.monits.packer;

import java.lang.reflect.Field;

import com.monits.packer.annotation.FixedLength;
import com.monits.packer.annotation.Unsigned;
import com.monits.packer.annotation.UseCodec;
import com.monits.packer.codec.Codec;
import com.monits.packer.codec.FixedLengthCodec;
import com.monits.packer.codec.ObjectCodec;
import com.monits.packer.codec.UnsignedByteCodec;
import com.monits.packer.codec.UnsignedIntCodec;
import com.monits.packer.codec.UnsignedShortCodec;

public class CodecFactory {
	
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
			} catch (IllegalAccessException e) {
			}
		} else {
			return buildSimpleCodec(field, field.getType());
		}
		
		return null;
	}
	
	private static Codec<?> buildFixedLengthCodec(Field field) {
		
		Class<?> arrayType = field.getType();
		if (!arrayType.isArray()) {
			return null;
		}
		
		Class<?> type = arrayType.getComponentType();
		Codec<Object> codec = null;
		
		if (field.isAnnotationPresent(UseCodec.class)) {
			
			UseCodec ann = field.getAnnotation(UseCodec.class);
			try {
				codec = (Codec<Object>) ann.value().newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		} else {
			codec = (Codec<Object>) buildSimpleCodec(field, type);
		}
		
		if (codec == null) {
			return null;
		}
		
		return new FixedLengthCodec(type, codec, field.getAnnotation(FixedLength.class).value());
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
		} else {
			return new ObjectCodec(field.getType());
		}
		
		return null;
	}

}
