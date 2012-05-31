package com.monits.packer.codec;

import java.io.IOException;

import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;


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
