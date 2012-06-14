package com.monits.jpack;

import java.io.IOException;

import com.monits.jpack.codec.Codec;
import com.monits.jpack.streams.InputByteStreamImpl;
import com.monits.jpack.streams.OutputByteStreamImpl;

public class Packer {
	
	private Packer() {
	}
	
	/**
	 * Create a new instance of <E> using the data in payload. 
	 * 
	 * @param clz     The object's class.
	 * @param payload The bytes to build the object from
	 * 
	 * @return The built object or null on failure.
	 */
	public static <E> E decode(Class<? extends E> clz, byte[] payload) {
		Codec<? extends E> codec = CodecFactory.get(clz);
		try {
			return codec.decode(new InputByteStreamImpl(payload), null);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Encode an object into an array of bytes.
	 * 
	 * @param payload The object to encode.
	 * 
	 * @return The bytes resulting from encoding the object.
	 */
	public static <E> byte[] encode(E payload) {
		
		@SuppressWarnings("unchecked")
		Codec<E> codec = CodecFactory.get((Class<E>) payload.getClass());
		OutputByteStreamImpl output = new OutputByteStreamImpl();
		codec.encode(output, payload, null);
		
		return output.getData();
	}

}
