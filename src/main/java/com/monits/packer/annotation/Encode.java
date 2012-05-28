package com.monits.packer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.monits.packer.EncodingType;
import com.monits.packer.codec.Codec;
import com.monits.packer.codec.StubCodec;

@Target(value = { ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Encode {

	public int value() default 0; 
	
	public EncodingType as() default EncodingType.OBJECT;
	
	public Class<? extends Codec<?>> codec() default StubCodec.class;
	
}
