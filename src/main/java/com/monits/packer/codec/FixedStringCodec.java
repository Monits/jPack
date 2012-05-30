package com.monits.packer.codec;

import java.nio.charset.Charset;

import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;

public class FixedStringCodec implements Codec<String> {
	
	private int length;
	
	public FixedStringCodec(int length) {
		super();
		this.length = length;
	}

	@Override
	public void encode(OutputByteStream payload, String object, Object[] dependants) {
		
		if (object.length() != length) {
			throw new IllegalArgumentException("The string is not of length " + length);
		}
		
		for (byte el : object.getBytes(Charset.forName("ascii"))) {
			payload.putByte(el);
		}
	}

	@Override
	public String decode(InputByteStream payload, Object[] dependants) {
		return new String(payload.getBytes(length), Charset.forName("ascii"));
	}

}
