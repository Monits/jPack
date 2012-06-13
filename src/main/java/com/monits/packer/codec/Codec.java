package com.monits.packer.codec;

import java.io.IOException;

import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;

/**
 * Defines a encoder/decoder for the type <E> or it's subclasses.
 * 
 * Due to Java Generics being limited, <E> does not always indicate 
 * the resulting type. For example, several of the provided codecs say 
 * they are for Object when in practice, they return another type.
 * 
 * @author jpcivile
 *
 * @param <E> The type be encoded or decoded.
 */
public interface Codec<E> {

	/**
	 * Encode a given object into payload.
	 * 
	 * @param payload    The output stream to write into.
	 * @param object     The object to encode.
	 * @param dependants The values this depends on.
	 * 
	 * @return true if the object was encoded, false otherwise
	 */
	public boolean encode(OutputByteStream payload, E object, Object[] dependants);

	/**
	 * Decode an object from paylaod.
	 * 
	 * @param payload    The byte stream to decode from.
	 * @param dependants The values this depends on.
	 * 
	 * @return The decoded object.
	 * 
	 * @throws IOException thrown if the input stream failed to get data.
	 */
	public E decode(InputByteStream payload, Object[] dependants) throws IOException;
	
}
