package com.monits.packer.codec;

import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;

public interface Codec<E> {

	public void encode(OutputByteStream payload, E object, Object[] dependants);

	public E decode(InputByteStream payload, Object[] dependants);
	
}
