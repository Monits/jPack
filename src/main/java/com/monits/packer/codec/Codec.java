package com.monits.packer.codec;

public interface Codec<E> {
	
	public byte[] encode(E object, Object[] dependants);

	public E decode(byte[] payload, Object[] dependants);
	
	public int computeSize(Object[] dependants);

}
