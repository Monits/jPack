package com.monits.packer.codec;

public class UnsignedShortCodec implements Codec<Integer> {

	@Override
	public byte[] encode(Integer payload, Object[] dependants) {
		int val = payload;
		
		return new byte[] {
			(byte) ((0xFF00 & val) >> 8),
			(byte) (0x00FF & val)
		};
	}

	@Override
	public Integer decode(byte[] payload, Object[] dependants) {
		return ((((int) payload[0]) & 0xFF) << 8)
			| (((int) payload[1]) & 0xFF);
	}

	@Override
	public int computeSize(Object[] dependants) {
		return 2;
	}

}
