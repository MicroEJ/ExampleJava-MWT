/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute;

import ej.annotation.Nullable;

/**
 * Provides a method to get the value of an attribute.
 */
public interface AttributeHolder {

	/**
	 * Gets the value of an attribute.
	 *
	 * @param attribute
	 *            the attribute to get.
	 * @return the value of the attribute or <code>null</code> if the attribute does not exist.
	 */
	@Nullable
	public String getAttribute(String attribute);
}
