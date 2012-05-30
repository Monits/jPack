package com.monits.packer.codec;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.monits.packer.Packer;
import com.monits.packer.annotation.Encode;
import com.monits.packer.annotation.FixedLength;
import com.monits.packer.annotation.Unsigned;


public class FixedLengthArrayTest {
	
	@Test
	public void testPrimitive() {
		PrimitiveObject original = new PrimitiveObject();
		original.setValue(new int[] { 56323, 123 });
		
		byte[] encoded = Packer.encode(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(4, encoded.length);
		
		PrimitiveObject decoded = Packer.decode(PrimitiveObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertTrue(Arrays.equals(original.getValue(), decoded.getValue()));
	}
	
	public static class PrimitiveObject {
		
		@Encode(0)
		@Unsigned
		@FixedLength(2)
		private int[] value;

		public void setValue(int[] value) {
			assert(value != null);
			assert(value.length == 2);
			
			this.value = value;
		}

		public int[] getValue() {
			return value;
		}
		
	}

}
