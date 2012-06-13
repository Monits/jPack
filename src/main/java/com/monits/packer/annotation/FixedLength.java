package com.monits.packer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare the the field is a fixed length array of sorts.
 * 
 * @author jpcivile
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FixedLength {

	/**
	 * The length of the array.
	 * 
	 * @return
	 */
	public int value();
	
}
