package com.monits.packer.codec;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.monits.packer.Packer;
import com.monits.packer.annotation.DependsOn;
import com.monits.packer.annotation.Encode;
import com.monits.packer.annotation.Unsigned;

public class VariableLengthTest {
	
	@Test
	public void testPrimitive() {
		VariablePrimitive original = new VariablePrimitive();
		original.setThing(2);
		original.setArray(new int[] { 234, 342 });
		
		byte[] encoded = Packer.encode(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(6, encoded.length);
	
		VariablePrimitive decoded = Packer.decode(VariablePrimitive.class, encoded);
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
