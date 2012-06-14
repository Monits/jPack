/*
 *
 * Copyright 2012 Monits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.monits.jpack.codec;

import java.io.IOException;

import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;

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
