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

package com.liferay.changeset;

import com.liferay.portal.kernel.model.ClassedModel;

import java.io.Serializable;

import java.util.stream.Stream;

/**
 * @author Mate Thurzo
 */
public interface ChangeSet extends Serializable {

	public <T extends ClassedModel> void addModel(
		ChangeSetModelSupplier<T> modelSupplier);

	public String getUuid();

	public Stream<? extends ClassedModel> stream();

}