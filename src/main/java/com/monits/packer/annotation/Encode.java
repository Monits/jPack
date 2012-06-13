package com.monits.packer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare that a field is to be encoded.
 * 
 * Any field in an object that is to encoded/decoded without this annotation is ignored.
 * 
 * @author jpcivile
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encode {

	/**
	 * 
	 * 
	 * @return
	 */
	public int value(); 
	
}
