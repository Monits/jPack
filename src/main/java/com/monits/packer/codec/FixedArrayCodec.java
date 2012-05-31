package com.monits.packer.codec;

import java.lang.reflect.Array;

import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;

public class FixedArrayCodec implements Codec<Object> {
	
	private Class<? extends Object> type;
	
	private Codec<Object> codec;
	
	private int length;
	
	public FixedArrayCodec(Class<? extends Object> type, Codec<Object> codec, int length) {
		super();
		this.type = type;
		this.codec = codec;
		this.length = length;
	}

	@Override
	public boolean encode(OutputByteStream payload, Object object, Object[] dependants) {

		if (length != Array.getLength(object)) {
			throw new IllegalArgumentException("The object is not an array of length " + length);
		}
		
		for (int i = 0; i < length; i++) {
			if (!codec.encode(payload, Array.get(object, i), dependants)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public Object decode(InputByteStream payload, Object[] dependants) {

		Object array = Array.newInstance(type, length);
		for (int i = 0; i < length; i++) {
			Array.set(array, i, codec.decode(payload, dependants));
		}
		
		return array;
	}

}
