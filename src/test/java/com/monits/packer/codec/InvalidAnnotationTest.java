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
package com.monits.packer.codec;

import junit.framework.Assert;

import org.junit.Test;

import com.monits.jpack.Packer;
import com.monits.jpack.annotation.Encode;
import com.monits.jpack.annotation.FixedLength;
import com.monits.jpack.annotation.Unsigned;


public class InvalidAnnotationTest {
	
	@Test
	public void testInvalidFixedLength() {
		InvalidFixedLengthObject original = new InvalidFixedLengthObject();
		original.setValue(56323);
		
		byte[] encoded = Packer.pack(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(4, encoded.length);
		
		InvalidFixedLengthObject decoded = Packer.unpack(InvalidFixedLengthObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(original.getValue(), decoded.getValue());
	}
	
	@Test
	public void testInvalidUnsigned() {
		InvalidUnsignedObject original = new InvalidUnsignedObject();
		original.setValue("test");
		
		byte[] encoded = Packer.pack(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(4, encoded.length);
		
		InvalidUnsignedObject decoded = Packer.unpack(InvalidUnsignedObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(original.getValue(), decoded.getValue());
	}
	
	public static class InvalidFixedLengthObject {
		
		@Encode(0)
		@FixedLength(2)
		@Unsigned
		private long value;
		
		public InvalidFixedLengthObject() {
			super();
		}
		
		public InvalidFixedLengthObject(long value) {
			super();
			this.value = value;
		}

		public void setValue(long value) {
			this.value = value;
		}

		public long getValue() {
			return value;
		}
	}

	public static class InvalidUnsignedObject {
		
		@Encode(0)
		@Unsigned
		@FixedLength(4)
		private String value;
		
		public InvalidUnsignedObject() {
			super();
		}
		
		public InvalidUnsignedObject(String value) {
			super();
			this.value = value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
