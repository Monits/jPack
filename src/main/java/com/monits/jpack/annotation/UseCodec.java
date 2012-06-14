package com.monits.jpack.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.monits.jpack.codec.Codec;

/**
 * Declare that a field uses a custom codec.
 * 
 * All other annotations applied to this field, except for {@link Encode} and {@link DependsOn}, are ignored.
 * 
 * @author jpcivile
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseCodec {

	public Class<? extends Codec<?>> value();
	
}
