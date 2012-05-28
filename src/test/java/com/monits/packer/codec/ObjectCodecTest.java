package com.monits.packer.codec;

import org.junit.Assert;
import org.junit.Test;

import com.monits.packer.EncodingType;
import com.monits.packer.annotation.Encode;

public class ObjectCodecTest {

	@Test
	public void testSimpleObject() {
		 SimpleObject original = new SimpleObject();
		 original.setValue(5);
		 
		 ObjectCodec<SimpleObject> codec = new ObjectCodec<SimpleObject>(SimpleObject.class);
		 byte[] encoded = codec.encode(original, null);
		 Assert.assertArrayEquals(new byte[] { 00, 05 }, encoded);
		 
		 SimpleObject decoded = codec.decode(encoded, null);
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
		
		ObjectCodec<ComplexObject> codec = new ObjectCodec<ComplexObject>(ComplexObject.class);
		byte[] encoded = codec.encode(original, null);
		
		ComplexObject decoded = codec.decode(encoded, null);
		Assert.assertNotNull(decoded);
		Assert.assertNotNull(decoded.getNested());
		Assert.assertEquals(decoded.getNested().getValue(), original.getNested().getValue());
		Assert.assertEquals(decoded.getUint(), original.getUint());
		Assert.assertEquals(decoded.getUshort(), original.getUshort());
	}
	
	public static class SimpleObject {
		
		@Encode(value = 0, as = EncodingType.UNSIGNED_INT16)
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
		
		@Encode(value = 0)
		private SimpleObject nested;
		
		@Encode(value = 1, as = EncodingType.UNSIGNED_INT16)
		private int uint;
		
		@Encode(value = 2, as = EncodingType.UNSIGNED_BYTE)
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
