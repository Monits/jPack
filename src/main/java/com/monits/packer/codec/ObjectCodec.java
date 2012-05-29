package com.monits.packer.codec;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;

import com.monits.packer.CodecFactory;
import com.monits.packer.annotation.Encode;
import com.monits.packer.streams.InputByteStream;
import com.monits.packer.streams.OutputByteStream;

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
				data.codec = CodecFactory.get(field);
				
				data.field = field;
				data.field.setAccessible(true);
				
				fields.add(data);
			}
		}
		
		Collections.sort(fields, new Comparator<FieldData>() {

			@Override
			public int compare(FieldData a, FieldData b) {
				return b.metadata.value() - a.metadata.value();
			}
			
		});
	}
	
	@Override
	public void encode(OutputByteStream payload, E obj, Object[] dependants) {
		
		for (FieldData field : fields) {
			
			try {
				Object value = field.field.get(obj);
				Object[] params = new Object[] { payload, value, new Object[0] };
				MethodUtils.invokeMethod(field.codec, "encode", params);
			} catch (IllegalArgumentException e) {
				return;
			} catch (IllegalAccessException e) {
				return;
			} catch (InvocationTargetException e) {
				return;
			} catch (NoSuchMethodException e) {
				return;
			}
			
		}
		
	}

	@Override
	public E decode(InputByteStream payload, Object[] dependants) {
		
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
			
			try {
				Object[] params = new Object[] { payload, new Object[0] };
				Object value = MethodUtils.invokeMethod(field.codec, "decode", params);
				field.field.set(res, value);
			} catch (IllegalArgumentException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			} catch (InvocationTargetException e) {
				return null;
			} catch (NoSuchMethodException e) {
				return null;
			}
			
		}
		
		return res;
	}

	private static class FieldData {
		
		Encode metadata;
		
		Field field;
		
		Codec<?> codec;
	}


}