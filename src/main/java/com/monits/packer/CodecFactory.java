package com.monits.packer;

import com.monits.packer.annotation.Encode;
import com.monits.packer.codec.Codec;
import com.monits.packer.codec.ObjectCodec;
import com.monits.packer.codec.StubCodec;
import com.monits.packer.codec.UnsignedByteCodec;
import com.monits.packer.codec.UnsignedIntCodec;
import com.monits.packer.codec.UnsignedShortCodec;

public class CodecFactory {
	
	public static <E> Codec<E> get(Class <E> clz) {
		
		Encode metadata = clz.getAnnotation(Encode.class);
		if (metadata == null) {
			return null;
		}

		if (metadata.codec() != null && !metadata.codec().isAssignableFrom(StubCodec.class)) {

			try {
				//FIXME: This will blow up in a million pieces
				return (Codec<E>) metadata.codec().newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
			
		}
		
		if (metadata.as() == EncodingType.PROVIDED) {
			return null;
		}
			
		return (Codec<E>) get(clz, metadata);
	}
	
	public static Codec<?> get(Class<?> type, Encode metadata) {
		
		switch (metadata.as()) {
		case OBJECT:
			return new ObjectCodec<Object>(type, metadata);
		case UNSIGNED_BYTE:
			return new UnsignedByteCodec(metadata);
		case UNSIGNED_INT16:
			return new UnsignedShortCodec(metadata);
		case UNSIGNED_INT32:
			return new UnsignedIntCodec(metadata);
		}
		
		return null;
	}

}
