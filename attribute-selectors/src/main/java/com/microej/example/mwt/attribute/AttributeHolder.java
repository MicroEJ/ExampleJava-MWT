/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
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
	 * @param name
	 *            the name of the attribute to get the value of.
	 * @return the value of the attribute or <code>null</code> if the attribute does not exist.
	 */
	@Nullable
	String getAttribute(String name);
}
