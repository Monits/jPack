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
package com.monits.jpack.codec;

import java.io.IOException;
import java.lang.reflect.Array;

import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;

public class VariableArrayCodec implements Codec<Object> {
	
	private Class<? extends Object> type;
	
	private Codec<Object> codec;
	
	public VariableArrayCodec(Class<? extends Object> type, Codec<Object> codec) {
		super();
		this.type = type;
		this.codec = codec;
	}

	@Override
	public boolean encode(OutputByteStream payload, Object object, Object[] dependants) {
		
		long length = getLength(dependants);
		
		if (length != Array.getLength(object)) {
			throw new IllegalArgumentException("The object is not an array of length " + length);
		}
		
		for (int i = 0; i < length; i++) {
			if (!codec.encode(payload, Array.get(object, i), dependants)) {
				return true;
			}
		}
		
		return true;
	}

	private long getLength(Object[] dependants) {

		if (dependants.length == 0 || dependants[0] == null) {
			throw new IllegalArgumentException("Was expecting array length as dependant. Use @DependsOn({ \"fieldSpecifyingLength\"})");
		}
		
		try {
			return Long.parseLong(dependants[0].toString());
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Was expecting array length as dependant, got non numeric value.");
		}
	}

	@Override
	public Object decode(InputByteStream payload, Object[] dependants) throws IOException {
		
		long length = getLength(dependants);
		
		Object array = Array.newInstance(type, (int) length);
		
		for (int i = 0; i < length; i++) {
			Array.set(array, i, codec.decode(payload, dependants));
		}
		
		return array;
	}

}
