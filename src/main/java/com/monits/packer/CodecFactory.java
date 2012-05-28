package com.monits.packer;

import java.lang.reflect.Field;

import com.monits.packer.codec.Codec;
import com.monits.packer.codec.ObjectCodec;
import com.monits.packer.codec.UnsignedByteCodec;
import com.monits.packer.codec.UnsignedIntCodec;
import com.monits.packer.codec.UnsignedShortCodec;

public class CodecFactory {
	
	public static Codec<?> get(Field field, EncodingType as) {
		
		Class<? extends Codec<?>> codec = null;
		switch (as) {
		case AUTO:
			//TODO: Autodetect primitive types
			return new ObjectCodec<Object>(field.getType());
		case UNSIGNED_BYTE:
			return new UnsignedByteCodec();
		case UNSIGNED_INT16:
			return new UnsignedShortCodec();
		case UNSIGNED_INT32:
			return new UnsignedIntCodec();
		}
		
		return null;
	}

}
