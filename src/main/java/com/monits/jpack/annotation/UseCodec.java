/*
 *
 * Copyright 2012 Monits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
