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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.monits.jpack.Packer;
import com.monits.jpack.annotation.DependsOn;
import com.monits.jpack.annotation.Encode;
import com.monits.jpack.annotation.Unsigned;

public class VariableLengthTest {
	
	@Test
	public void testPrimitive() {
		VariablePrimitive original = new VariablePrimitive();
		original.setThing(2);
		original.setArray(new int[] { 234, 342 });
		
		byte[] encoded = Packer.pack(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(6, encoded.length);
	
		VariablePrimitive decoded = Packer.unpack(VariablePrimitive.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(original, decoded);
	}
	
	public static class VariablePrimitive {
		
		@Unsigned
		@Encode(0)
		private int thing;
		
		@DependsOn({ "thing" })
		@Encode(1)
		@Unsigned
		private int[] array;

		public void setArray(int[] array) {
			this.array = array;
		}

		public int[] getArray() {
			return array;
		}

		public void setThing(int thing) {
			this.thing = thing;
		}

		public int getThing() {
			return thing;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VariablePrimitive other = (VariablePrimitive) obj;
			if (!Arrays.equals(array, other.array))
				return false;
			if (thing != other.thing)
				return false;
			return true;
		}
		
	}

}
