package com.monits.packer.codec;

import com.monits.packer.annotation.Encode;

public final class StubCodec extends Codec<Object> {

	public StubCodec(Encode metadata) {
		super(metadata);
	}

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
