package com.monits.packer.codec;

import org.junit.Assert;
import org.junit.Test;

import com.monits.packer.Packer;
import com.monits.packer.annotation.Encode;
import com.monits.packer.annotation.Unsigned;

public class ObjectCodecTest {

	@Test
	public void testSimpleObject() {
		 SimpleObject original = new SimpleObject();
		 original.setValue(5);
		 
		 byte[] encoded = Packer.encode(original);
		 Assert.assertArrayEquals(new byte[] { 00, 05 }, encoded);
		 
		 SimpleObject decoded = Packer.decode(SimpleObject.class, encoded);
		 Assert.assertNotNull(decoded);
		 Assert.assertEquals(original.getValue(), decoded.getValue());
	}
	
	@Test
	public void testComplexObject() {
		
		ComplexObject original = new ComplexObject();
		SimpleObject simple = new SimpleObject();
		
		simple.setValue(60);
		original.setNested(simple);
		original.setUint(232);
		original.setUshort((short) 255);
		
		byte[] encoded = Packer.encode(original);
		Assert.assertNotNull(encoded);
		Assert.assertTrue(encoded.length > 0);
		
		ComplexObject decoded = Packer.decode(ComplexObject.class, encoded);
		
		Assert.assertNotNull(decoded);
		Assert.assertNotNull(decoded.getNested());
		Assert.assertEquals(decoded.getNested().getValue(), original.getNested().getValue());
		Assert.assertEquals(decoded.getUint(), original.getUint());
		Assert.assertEquals(decoded.getUshort(), original.getUshort());
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
