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
import java.nio.charset.Charset;

import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;

public class FixedStringCodec implements Codec<String> {
	
	private int length;
	
	public FixedStringCodec(int length) {
		super();
		this.length = length;
	}

	@Override
	public boolean encode(OutputByteStream payload, String object, Object[] dependants) {
		
		if (object.length() != length) {
			throw new IllegalArgumentException("The string is not of length " + length);
		}
		
		for (byte el : object.getBytes(Charset.forName("ascii"))) {
			payload.putByte(el);
		}
		
		return true;
	}

	@Override
	public String decode(InputByteStream payload, Object[] dependants) throws IOException {
		return new String(payload.getBytes(length), Charset.forName("ascii"));
	}

}
