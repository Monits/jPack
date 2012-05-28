package com.monits.packer.codec;

import com.monits.packer.CodecFactory;
import com.monits.packer.annotation.Encode;

public class ArrayCodec extends Codec<Object> {

	private Codec<Object> elementCodec;
	
	public ArrayCodec(Encode metadata) {
		super(metadata);
		
		elementCodec = (Codec<Object>) CodecFactory.get(null, metadata);
	}

	@Override
	public byte[] encode(Object object, Object[] dependants) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object decode(byte[] payload, Object[] dependants) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int computeSize(Object[] dependants) {
		// TODO Auto-generated method stub
		return 0;
	}

}
