package com.monits.packer.codec;

import org.junit.Assert;
import org.junit.Test;

import com.monits.jpack.Packer;
import com.monits.jpack.annotation.Encode;
import com.monits.jpack.annotation.Unsigned;

public class UnsignedTest {

	@Test
	public void testSimpleObject() {
		 SimpleObject original = new SimpleObject();
		 original.setUshort((short) 255);
		 original.setUint(256);
		 original.setUlong(153456L);
		 
		 byte[] encoded = Packer.pack(original);
		 Assert.assertNotNull(encoded);
		 Assert.assertEquals(7, encoded.length);
		 
		 SimpleObject decoded = Packer.unpack(SimpleObject.class, encoded);
		 Assert.assertNotNull(decoded);
		 Assert.assertEquals(original.getUshort(), decoded.getUshort());
		 Assert.assertEquals(original.getUint(), decoded.getUint());
		 Assert.assertEquals(original.getUlong(), decoded.getUlong());
	}
	
	public static class SimpleObject {
		
		@Unsigned
		@Encode(0)
		private long ulong;
		
		@Unsigned
		@Encode(1)
		private int uint;
		
		@Unsigned
		@Encode(2)
		private short ushort;
		
		public SimpleObject() {
		}

		public long getUlong() {
			return ulong;
		}

		public void setUlong(long ulong) {
			this.ulong = ulong;
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
