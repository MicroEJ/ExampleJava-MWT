/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute;

import ej.annotation.Nullable;
import ej.basictool.map.PackedMap;
import ej.widget.basic.Label;

/**
 * An attribute label is a label which can hold attributes.
 */
public class AttributeLabel extends Label implements AttributeHolder {

	private final PackedMap<String, String> attributes;

	/**
	 * Creates an attribute label with an empty text.
	 */
	public AttributeLabel() {
		this("");
	}

	/**
	 * Creates an attribute label with the given text to display.
	 * <p>
	 * The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 */
	public AttributeLabel(String text) {
		super(text);
		this.attributes = new PackedMap<>();
	}

	/**
	 * Sets the value of an attribute.
	 *
	 * @param attribute
	 *            the attribute to set.
	 * @param value
	 *            the value to set.
	 */
	public void setAttribute(String attribute, String value) {
		this.attributes.put(attribute, value);
		updateStyle();
		requestRender();
	}

	@Override
	@Nullable
	public String getAttribute(String attribute) {
		return this.attributes.get(attribute);
	}
}
