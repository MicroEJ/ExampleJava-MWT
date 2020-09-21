/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

/**
 * An attribute value contain selector selects by checking that the value of an attribute contains a specific string.
 * <p>
 * Equivalent to <code>[attribute*=value]</code> selector in CSS. Its specificity is (0,0,1,0).
 *
 * @see com.microej.example.mwt.attribute.AttributeHolder#getAttribute(String)
 */
public class AttributeValueContainSelector extends AbstractAttributeSelector {

	private final String value;

	/**
	 * Creates an attribute value contain selector.
	 *
	 * @param attribute
	 *            the attribute to check.
	 * @param value
	 *            the value to check.
	 */
	public AttributeValueContainSelector(String attribute, String value) {
		super(attribute);
		this.value = value;
	}

	@Override
	public boolean attributeFits(String value) {
		return value.contains(this.value);
	}
}
