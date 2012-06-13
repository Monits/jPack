package com.monits.packer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare that the field is of an unsigned type.
 *
 * A standard conversion is applied to fit unsigned types in Java's signed types.
 * That is:
 * <ul>
 * 	<li>A short represents an unsigned byte</li>
 *  <li>An int represents an unsigned short</li>
 *  <li>A long represents an unsigned int</li>
 * </ul>
 * 
 * No other types are supported.
 * 
 * @author jpcivile
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unsigned {

}
