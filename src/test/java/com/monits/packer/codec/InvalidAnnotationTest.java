package com.monits.packer.codec;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.monits.packer.Packer;
import com.monits.packer.annotation.Encode;
import com.monits.packer.annotation.FixedLength;
import com.monits.packer.annotation.Unsigned;


public class InvalidAnnotationTest {
	
	@Ignore
	@Test
	public void testInvalidFixedLength() {
		InvalidFixedLengthObject original = new InvalidFixedLengthObject();
		original.setValue(56323);
		
		byte[] encoded = Packer.encode(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(2, encoded.length);
		
		InvalidFixedLengthObject decoded = Packer.decode(InvalidFixedLengthObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(original.getValue(), decoded.getValue());
	}
	
	@Test
	public void testInvalidUnsigned() {
		InvalidUnsignedObject original = new InvalidUnsignedObject();
		original.setValue("test");
		
		byte[] encoded = Packer.encode(original);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(4, encoded.length);
		
		InvalidUnsignedObject decoded = Packer.decode(InvalidUnsignedObject.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(original.getValue(), decoded.getValue());
	}
	
	public static class InvalidFixedLengthObject {
		
		@Encode(0)
		@FixedLength(2)
		private int value;
		
		public InvalidFixedLengthObject() {
			super();
		}
		
		public InvalidFixedLengthObject(int value) {
			super();
			this.value = value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public int getValue() {
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
