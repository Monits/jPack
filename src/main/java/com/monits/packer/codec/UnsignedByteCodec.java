package com.monits.packer.codec;

import java.io.IOException;

import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;


public class UnsignedByteCodec implements Codec<Short> {

	@Override
	public boolean encode(OutputByteStream payload, Short object, Object[] dependants) {
		payload.putByte((byte) (0xFF & ((short) object)));
		
		return true;
	}

	@Override
	public Short decode(InputByteStream payload, Object[] dependants) throws IOException {
		return (short) (((short) payload.getByte()) & 0xFF);
	}

}
