package com.monits.packer.codec;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.monits.jpack.Packer;
import com.monits.jpack.annotation.Encode;
import com.monits.jpack.annotation.FixedLength;
import com.monits.jpack.annotation.Unsigned;


public class FixedLengthTest {
	
	@Test
	public void testPrimitive() {
		PrimitiveObject original = new PrimitiveObject();
		original.setValue(new int[] { 56323, 123 });
		
		byte[] encoded = Packer.pack(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(4, encoded.length);
		
		PrimitiveObject decoded = Packer.unpack(PrimitiveObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertTrue(Arrays.equals(original.getValue(), decoded.getValue()));
	}
	
	@Test
	public void testString() {
		StringObject original = new StringObject();
		original.setValue("asdq");
		
		byte[] encoded = Packer.pack(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(4, encoded.length);
		
		StringObject decoded = Packer.unpack(StringObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(original.getValue(), decoded.getValue());
		
		StringObject second = new StringObject();
		second.setValue("qwew");
		
		byte[] secondEncoded = Packer.pack(second);
		Assert.assertNotNull(secondEncoded);
		Assert.assertEquals(4, secondEncoded.length);
		
		StringObject secondDecoded = Packer.unpack(StringObject.class, secondEncoded);
		Assert.assertNotNull(secondDecoded);
		Assert.assertEquals(second.getValue(), secondDecoded.getValue());
		
		Assert.assertFalse(secondDecoded.getValue().equals(original.getValue()));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSmallerSize() {
		PrimitiveObject original = new PrimitiveObject();
		original.setValue(new int[] { 123 });
		
		Packer.pack(original);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBiggerSize() {
		PrimitiveObject original = new PrimitiveObject();
		original.setValue(new int[] { 123, 456, 23 });
		
		Packer.pack(original);
	}
	
	@Test
	public void testNested() {
		HasNestedObject original = new HasNestedObject();
		original.setObj(new PrimitiveObject[] {
			new PrimitiveObject(new int[] { 24, 34 }),
			new PrimitiveObject(new int[] { 45, 43 }),
		});
		
		byte[] encoded = Packer.pack(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(8, encoded.length);
		
		HasNestedObject decoded = Packer.unpack(HasNestedObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(original, decoded);
	}
	
	public static class HasNestedObject {
		
		@Encode(0)
		@FixedLength(2)
		private PrimitiveObject[] obj;

		public void setObj(PrimitiveObject[] obj) {
			this.obj = obj;
		}

		public PrimitiveObject[] getObj() {
			return obj;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HasNestedObject other = (HasNestedObject) obj;
			if (!Arrays.equals(this.obj, other.obj))
				return false;
			return true;
		}

	}
	
	public static class StringObject {
		
		@Encode(0)
		@FixedLength(4)
		private String value;

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
	}
	
	public static class PrimitiveObject {
		
		@Encode(0)
		@Unsigned
		@FixedLength(2)
		private int[] value;
		
		public PrimitiveObject() {
			super();
		}
		
		public PrimitiveObject(int[] value) {
			super();
			this.value = value;
		}

		public void setValue(int[] value) {
			this.value = value;
		}

		public int[] getValue() {
			return value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null) {
				return false;
			} else if (!(obj instanceof PrimitiveObject)) {
				return false;
			}
			
			PrimitiveObject other = (PrimitiveObject) obj;
			return Arrays.equals(value, other.value);
		}
		
	}

}
