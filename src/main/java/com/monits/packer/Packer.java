package com.monits.packer;

import java.io.IOException;

import com.monits.packer.codec.Codec;
import com.monits.packer.streams.InputByteStreamImpl;
import com.monits.packer.streams.OutputByteStreamImpl;

public class Packer {
	
	private Packer() {
	}
	
	public static <E> E decode(Class<? extends E> clz, byte[] payload) {
		Codec<? extends E> codec = CodecFactory.get(clz);
		try {
			return codec.decode(new InputByteStreamImpl(payload), null);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static <E> byte[] encode(E payload) {
		
		@SuppressWarnings("unchecked")
		Codec<E> codec = CodecFactory.get((Class<E>) payload.getClass());
		OutputByteStreamImpl output = new OutputByteStreamImpl();
		codec.encode(output, payload, null);
		
		return output.getData();
	}

}
