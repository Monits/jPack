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

import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;


public class UnsignedIntCodec implements Codec<Long> {

	@Override
	public boolean encode(OutputByteStream payload, Long object, Object[] dependants) {
		long val = object;
		
		payload.putByte((byte) ((0xFF000000 & val) >> 24));
		payload.putByte((byte) ((0x00FF0000 & val) >> 16));
		payload.putByte((byte) ((0x0000FF00 & val) >> 8));
		payload.putByte((byte)  (0x000000FF & val));
		
		return true;
	}

	@Override
	public Long decode(InputByteStream payload, Object[] dependants) throws IOException {
		
		byte[] data = payload.getBytes(4);
		
		return ((((long) data[0]) & 0xFF) << 24)
			| ((((long) data[1]) & 0xFF) << 16)
			| ((((long) data[2]) & 0xFF) << 8)
			| (((long) data[3]) & 0xFF);
	}

}
