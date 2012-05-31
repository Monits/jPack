package com.monits.packer.codec;

import junit.framework.Assert;

import org.junit.Test;

import com.monits.packer.Packer;
import com.monits.packer.annotation.Encode;
import com.monits.packer.annotation.UseCodec;
import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;


public class CustomCodecTest {
	
	@Test
	public void testCustomCodec() {

		ContainsAnInt object = new ContainsAnInt();
		object.setValue((int) Math.pow(2, 29));
		
		byte[] encoded = Packer.encode(object);
		Assert.assertNotNull(encoded);
		Assert.assertEquals(4, encoded.length);

		ContainsAnInt decoded = Packer.decode(ContainsAnInt.class, encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals(object.getValue(), decoded.getValue());
		
	}
	
	public static class ContainsAnInt {
		
		@UseCodec(IntCodec.class)
		@Encode(0)
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
		
	}
	
	public static class IntCodec implements Codec<Integer> {

		@Override
		public boolean encode(OutputByteStream payload, Integer object, Object[] dependants) {
			payload.putByte((byte) ((0xFF000000 & object) >> 24));
			payload.putByte((byte) ((0x00FF0000 & object) >> 16));
			payload.putByte((byte) ((0x0000FF00 & object) >> 8));
			payload.putByte((byte) ((0x000000FF & object)));
			
			return true;
		}

		@Override
		public Integer decode(InputByteStream payload, Object[] dependants) {
			byte[] bytes = payload.getBytes(4);
			return (bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3];
		}
		
	}

}
