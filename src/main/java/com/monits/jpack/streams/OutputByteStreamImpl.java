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

public class OutputByteStreamImpl implements OutputByteStream {
	
	private byte[] data = new byte[5000];
	
	private int pos = 0;

	@Override
	public void putByte(byte payload) {
		data[pos++] = payload;
	}
	
	public byte[] getData() {
		byte[] res = new byte[pos];
		System.arraycopy(data, 0, res, 0, pos);
		return res;
	}

}
