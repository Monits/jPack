package com.monits.packer;

import java.io.IOException;

import com.monits.packer.codec.ObjectCodec;
import com.monits.packer.streams.InputByteStreamImpl;
import com.monits.packer.streams.OutputByteStreamImpl;

public class Packer {
	
	private Packer() {
	}
	
	public static <E> E decode(Class<? extends E> clz, byte[] payload) {
		ObjectCodec<E> codec = new ObjectCodec<E>(clz);
		try {
			return codec.decode(new InputByteStreamImpl(payload), new Object[0]);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static <E> byte[] encode(E payload) {
		
		ObjectCodec<E> codec = new ObjectCodec<E>((Class<? extends E>) payload.getClass());
		OutputByteStreamImpl output = new OutputByteStreamImpl();
		codec.encode(output, payload, new Object[0]);
		
		return output.getData();
	}

}
