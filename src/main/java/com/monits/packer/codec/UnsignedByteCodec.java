package com.monits.packer.codec;

import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;


public class UnsignedByteCodec implements Codec<Short> {

	@Override
	public void encode(OutputByteStream payload, Short object, Object[] dependants) {
		payload.putByte((byte) (0xFF & ((short) object)));
	}

	@Override
	public Short decode(InputByteStream payload, Object[] dependants) {
		return (short) (((short) payload.getByte()) & 0xFF);
	}

}
