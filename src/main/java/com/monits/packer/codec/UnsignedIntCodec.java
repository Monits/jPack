package com.monits.packer.codec;

public class UnsignedIntCodec implements Codec<Long> {

	@Override
	public byte[] encode(Long payload, Object[] dependants) {
		long val = payload;
		
		return new byte[] {
			(byte) ((0xFF000000 & val) >> 24),
			(byte) ((0x00FF0000 & val) >> 16),
			(byte) ((0x0000FF00 & val) >> 8),
			(byte)  (0x000000FF & val)
		};
	}

	@Override
	public Long decode(byte[] payload, Object[] dependants) {
		return ((((long) payload[0]) & 0xFF) << 24)
			| ((((long) payload[1]) & 0xFF) << 16)
			| ((((long) payload[2]) & 0xFF) << 8)
			| (((long) payload[3]) & 0xFF);
	}

	@Override
	public int computeSize(Object[] dependants) {
		return 4;
	}

}
