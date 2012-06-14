package com.monits.jpack.codec;

import java.io.IOException;

import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;

public class ByteCodec implements Codec<Byte> {

	@Override
	public boolean encode(OutputByteStream payload, Byte object, Object[] dependants) {
		payload.putByte(object);
		return true;
	}

	@Override
	public Byte decode(InputByteStream payload, Object[] dependants) throws IOException {
		return payload.getByte();
	}

}
