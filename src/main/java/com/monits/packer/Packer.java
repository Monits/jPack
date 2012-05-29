package com.monits.packer;

import com.monits.packer.codec.ObjectCodec;
import com.monits.packer.streams.InputByteStreamImpl;
import com.monits.packer.streams.OutputByteStreamImpl;

public class Packer {
	
	public static <E> E decode(Class<? extends E> clz, byte[] payload) {
		ObjectCodec<E> codec = new ObjectCodec<E>(clz);
		return codec.decode(new InputByteStreamImpl(payload), new Object[0]);
	}
	
	public static <E> byte[] encode(E payload) {
		
		ObjectCodec<E> codec = new ObjectCodec<E>((Class<? extends E>) payload.getClass());
		OutputByteStreamImpl output = new OutputByteStreamImpl();
		codec.encode(output, payload, new Object[0]);
		
		return output.getData();
	}

}
