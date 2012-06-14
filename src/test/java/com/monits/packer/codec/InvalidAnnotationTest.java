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
