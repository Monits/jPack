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

public class ObjectCodec<E> implements Codec<E> {
	
	private Class<? extends E> struct;
	
	private List<FieldData> fields;

	public ObjectCodec(Class<? extends E> struct) {
		super();
		
		this.struct = struct;
		
		fields = new ArrayList<FieldData>();
		for (Field field : struct.getDeclaredFields()) {
			Encode annotation = field.getAnnotation(Encode.class);
			if (annotation != null) {
				FieldData data = new FieldData();
				
				data.metadata = annotation;
				data.field = field;
				
				if (annotation.codec() != null && !annotation.codec().isAssignableFrom(StubCodec.class)) {
					
					try {
						data.codec = annotation.codec().newInstance();
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
					}
				}
				
				if (data.codec == null) {
					data.codec = CodecFactory.get(field, annotation.as());
					
					if (data.codec == null) {
						continue;
					}
				}
				
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
	
	public byte[] encode(E obj, Object[] dependants) {
		
		byte[] res = new byte[computeSize(null)];
		int offset = 0;
		for (FieldData field : fields) {
			
			try {
				Object value = field.field.get(obj);
				byte[] encoded = (byte[]) invokeEncode(field, value);
				System.arraycopy(encoded, 0, res, offset, encoded.length);
				
				offset += encoded.length;
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

	private Object invokeEncode(FieldData field, Object value)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		Object[] params = new Object[] { value, new Object[0] };
		return MethodUtils.invokeMethod(field.codec, "encode", params);
	}

	@Override
	public E decode(byte[] payload, Object[] dependants) {
		
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
		
		int offset = 0;
		for (FieldData field : fields) {
			
			byte[] data = new byte[field.codec.computeSize(null)];
			System.arraycopy(payload, offset, data, 0, data.length);
			offset += data.length;
			
			try {
				Object value = invokeDecode(field, data);
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

	private Object invokeDecode(FieldData field, byte[] data)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		Object[] params = new Object[] { data, new Object[0] };
		return MethodUtils.invokeMethod(field.codec, "decode", params);
	}

	@Override
	public int computeSize(Object[] dependants) {
		
		int size = 0;
		for (FieldData data : fields) {
			size += data.codec.computeSize(null);
		}
		
		return size;
	}


	private static class FieldData {
		
		Encode metadata;
		
		Field field;
		
		Codec<?> codec;
	}


}
