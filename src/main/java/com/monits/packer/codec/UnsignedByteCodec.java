package com.monits.packer.codec;

public class UnsignedByteCodec implements Codec<Short> {

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
