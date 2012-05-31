package com.monits.packer.streams;

import java.io.IOException;

public interface InputByteStream {

	public byte peek() throws IOException;

	public byte[] getBytes(int count) throws IOException;

	public byte getByte() throws IOException;

}