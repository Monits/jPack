package com.monits.packer.streams;

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
