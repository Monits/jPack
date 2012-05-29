package com.monits.packer.streams;

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
	
	@Override
	public boolean hasRemaining() {
		return pos < data.length - 1;
	}
	
}
