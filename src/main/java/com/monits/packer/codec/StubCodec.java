package com.monits.packer.codec;

public final class StubCodec implements Codec<Object> {

	@Override
	public byte[] encode(Object object, Object[] dependants) {
		return null;
	}

	@Override
	public Object decode(byte[] payload, Object[] dependants) {
		return null;
	}

	@Override
	public int computeSize(Object[] dependants) {
		return 0;
	}

}
