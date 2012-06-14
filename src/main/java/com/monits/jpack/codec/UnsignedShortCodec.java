package com.monits.jpack.codec;

import java.io.IOException;

import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;


public class UnsignedShortCodec implements Codec<Integer> {

	@Override
	public boolean encode(OutputByteStream payload, Integer object, Object[] dependants) {
		int val = object;
		
		payload.putByte((byte) ((0xFF00 & val) >> 8));
		payload.putByte((byte) (0x00FF & val));
		
		return true;
	}

	@Override
	public Integer decode(InputByteStream payload, Object[] dependants) throws IOException {
		
		byte[] data = payload.getBytes(2);
		
		return ((((int) data[0]) & 0xFF) << 8)
			| (((int) data[1]) & 0xFF);
	}

}