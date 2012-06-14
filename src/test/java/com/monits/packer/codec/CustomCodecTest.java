package com.monits.packer.codec;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.monits.jpack.Packer;
import com.monits.jpack.annotation.Encode;
import com.monits.jpack.annotation.UseCodec;
import com.monits.jpack.codec.Codec;
import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;


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
		public Integer decode(InputByteStream payload, Object[] dependants) throws IOException {
			byte[] bytes = payload.getBytes(4);
			return (bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3];
		}
		
	}

}
