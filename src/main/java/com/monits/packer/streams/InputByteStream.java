package com.monits.packer.streams;

public interface InputByteStream {

	public byte peek();

	public byte[] getBytes(int count);

	public byte getByte();

	public boolean hasRemaining();

}