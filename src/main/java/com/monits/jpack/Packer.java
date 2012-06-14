/*
 *
 * Copyright 2012 Monits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
	public static <E> E unpack(Class<? extends E> clz, byte[] payload) {
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
	public static <E> byte[] pack(E payload) {
		
		@SuppressWarnings("unchecked")
		Codec<E> codec = CodecFactory.get((Class<E>) payload.getClass());
		OutputByteStreamImpl output = new OutputByteStreamImpl();
		codec.encode(output, payload, null);
		
		return output.getData();
	}

}
