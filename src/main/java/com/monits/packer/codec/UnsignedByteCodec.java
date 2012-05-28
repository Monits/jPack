package com.monits.packer.codec;

import com.monits.packer.annotation.Encode;

public class UnsignedByteCodec extends Codec<Short> {

	public UnsignedByteCodec(Encode metadata) {
		super(metadata);
	}

	@Override
	public byte[] encode(Short payload, Object[] dependants) {
		return new byte[] { (byte) (0xFF & ((short) payload)) };
	}

	@Override
	public Short decode(byte[] payload, Object[] dependants) {
		return (short) (((short) payload[0]) & 0xFF);
	}

	@Override
	public int computeSize(Object[] dependants) {
		return 1;
	}

}
