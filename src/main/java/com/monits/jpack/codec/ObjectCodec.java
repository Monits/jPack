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
package com.monits.jpack.codec;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;

import com.monits.jpack.CodecFactory;
import com.monits.jpack.annotation.DependsOn;
import com.monits.jpack.annotation.Encode;
import com.monits.jpack.streams.InputByteStream;
import com.monits.jpack.streams.OutputByteStream;

public class ObjectCodec<E> implements Codec<E> {
	
	private Class<? extends E> struct;
	
	private List<FieldData> fields;

	public ObjectCodec(Class<? extends E> struct) {
		
		this.struct = struct;
		
		fields = new ArrayList<FieldData>();
		for (Field field : struct.getDeclaredFields()) {
			Encode annotation = field.getAnnotation(Encode.class);
			if (annotation != null) {
				FieldData data = new FieldData();
				
				data.metadata = annotation;
				data.codec = (Codec<Object>) CodecFactory.get(field);
				
				if (data.codec == null) {
					continue;
				}
				
				data.field = field;
				data.field.setAccessible(true);
				
				fields.add(data);
			}
		}
		
		Collections.sort(fields, new Comparator<FieldData>() {

			@Override
			public int compare(FieldData a, FieldData b) {
				return a.metadata.value() - b.metadata.value();
			}
			
		});
	}
	
	@Override
	public boolean encode(OutputByteStream payload, E obj, Object[] dependants) {
		
		for (FieldData field : fields) {
			
			Object value;
			try {
				value = field.field.get(obj);
			} catch (IllegalArgumentException e) {
				return false;
			} catch (IllegalAccessException e) {
				return false;
			}
			
			if (!field.codec.encode(payload, value, buildDependants(obj, field))) {
				return false;
			}
		}
		
		return true;
	}
	
	private Object[] buildDependants(E obj, FieldData field) {
		
		DependsOn ann = field.field.getAnnotation(DependsOn.class);
		if (ann == null) {
			return null;
		}
		
		String[] dependsOn = ann.value();
		Object[] res = new Object[dependsOn.length];
		for (int i = 0; i < res.length; i++) {
			
			for (FieldData el : fields) {
				
				if (el.field.getName().equals(dependsOn[i])) {
					try {
						res[i] = el.field.get(obj);
					} catch (IllegalArgumentException e) {
						res[i] = null;
					} catch (IllegalAccessException e) {
						res[i] = null;
					}
					
					break;
				}
			}
			
		}
		
		return res;
	}

	@Override
	public E decode(InputByteStream payload, Object[] dependants) throws IOException {
		
		E res;
		try {
			res = (E) ConstructorUtils.invokeConstructor(struct, null);
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		}
		
		for (FieldData field : fields) {
			
			Object value = field.codec.decode(payload, buildDependants(res, field));
			try {
				field.field.set(res, value);
			} catch (IllegalArgumentException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
			
		}
		
		return res;
	}

	private static class FieldData {
		
		Encode metadata;
		
		Field field;
		
		Codec<Object> codec;
	}


}
