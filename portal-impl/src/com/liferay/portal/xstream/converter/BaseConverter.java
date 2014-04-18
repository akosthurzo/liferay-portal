/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.xstream.converter;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Akos Thurzo
 */
public abstract class BaseConverter implements Converter {

	@Override
	public abstract boolean canConvert(Class clazz);

	public void doMarshal(
			Object source, HierarchicalStreamWriter writer,
			MarshallingContext context)
		throws Exception {

		for (String field : getFields()) {
			writer.startNode(field);

			Object value = PropertyUtils.getProperty(source, field);

			if (value != null) {
				context.convertAnother(value);
			}

			writer.endNode();
		}
	}

	public abstract Object doUnmarshal(
			HierarchicalStreamReader reader, UnmarshallingContext context)
		throws Exception;

	@Override
	public void marshal(
		Object source, HierarchicalStreamWriter writer,
		MarshallingContext context) {

		try {
			doMarshal(source, writer, context);
		}
		catch (Exception e) {
			throw new ConversionException(e);
		}
	}

	@Override
	public Object unmarshal(
		HierarchicalStreamReader reader, UnmarshallingContext context) {

		try {
			return doUnmarshal(reader, context);
		}
		catch (Exception e) {
			throw new ConversionException(e);
		}
	}

	protected abstract List<String> getFields();

}