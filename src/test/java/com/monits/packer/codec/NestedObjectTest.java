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

import org.junit.Assert;
import org.junit.Test;

import com.monits.jpack.Packer;
import com.monits.jpack.annotation.Encode;
import com.monits.jpack.annotation.Unsigned;


public class NestedObjectTest {
	
	@Test
	public void testComplexObject() {
		
		ComplexObject original = new ComplexObject();
		SimpleObject simple = new SimpleObject();
		
		simple.setValue(60);
		original.setNested(simple);
		original.setUint(232);
		original.setUshort((short) 255);
		
		byte[] encoded = Packer.pack(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(5, encoded.length);
		
		ComplexObject decoded = Packer.unpack(ComplexObject.class, encoded);
		
		Assert.assertNotNull(decoded);
		Assert.assertNotNull(decoded.getNested());
		Assert.assertEquals(original.getNested().getValue(), decoded.getNested().getValue());
		Assert.assertEquals(original.getUint(), decoded.getUint());
		Assert.assertEquals(original.getUshort(), decoded.getUshort());
	}
	
	public static class SimpleObject {
		
		@Unsigned
		@Encode(0)
		private int value;
		
		public SimpleObject() {
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
		
	}
	
	public static class ComplexObject {
		
		@Unsigned
		@Encode(0)
		private SimpleObject nested;
		
		@Unsigned
		@Encode(1)
		private int uint;
		
		@Unsigned
		@Encode(2)
		private short ushort;

		public SimpleObject getNested() {
			return nested;
		}

		public void setNested(SimpleObject nested) {
			this.nested = nested;
		}

		public int getUint() {
			return uint;
		}

		public void setUint(int uint) {
			this.uint = uint;
		}

		public short getUshort() {
			return ushort;
		}

		public void setUshort(short ushort) {
			this.ushort = ushort;
		}
		
	}

}
