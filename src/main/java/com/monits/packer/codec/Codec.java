package com.monits.packer.codec;

import com.monits.packer.annotation.Encode;

public abstract class Codec<E> {
	
	protected Encode metadata;
	
	public Codec(Encode metadata) {
		super();
		this.metadata = metadata;
	}

	public abstract byte[] encode(E object, Object[] dependants);

	public abstract E decode(byte[] payload, Object[] dependants);
	
	public abstract int computeSize(Object[] dependants);

}
