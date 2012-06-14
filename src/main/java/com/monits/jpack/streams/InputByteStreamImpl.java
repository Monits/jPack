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
package com.monits.jpack.streams;

public class InputByteStreamImpl implements InputByteStream {

	private byte[] data;
	
	private int pos = 0;
	
	public InputByteStreamImpl(byte[] data) {
		super();
		this.data = data;
	}

	@Override
	public byte peek() {
		return data[pos];
	}

	@Override
	public byte[] getBytes(int count) {
		if (pos + count > data.length) {
			return null;
		}
		
		byte[] res = new byte[count];
		System.arraycopy(data, pos, res, 0, count);
		pos += count;
		return res;
	}
	
	@Override
	public byte getByte() {
		return data[pos++];
	}
	
}
