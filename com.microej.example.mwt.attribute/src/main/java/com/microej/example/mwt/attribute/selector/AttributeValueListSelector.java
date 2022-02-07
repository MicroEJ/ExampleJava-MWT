/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

import java.util.StringTokenizer;

/**
 * An attribute value list selector selects by checking that a value is contained in an attribute.
 * <p>
 * The attribute value is considered as a white space-separated list of words. Checks that one of those is exactly the
 * given value.
 * <p>
 * Equivalent to <code>[attribute~=value]</code> selector in CSS. Its specificity is (0,0,1,0).
 *
 * @see com.microej.example.mwt.attribute.AttributeHolder#getAttribute(String)
 */
public class AttributeValueListSelector extends AbstractAttributeSelector {

	private static final String SPACE = " ";

	private final String value;

	/**
	 * Creates an attribute value list selector.
	 *
	 * @param attribute
	 *            the attribute to check.
	 * @param value
	 *            the value to check.
	 */
	public AttributeValueListSelector(String attribute, String value) {
		super(attribute);
		this.value = value;
	}

	@Override
	public boolean attributeFits(String value) {
		StringTokenizer tokenizer = new StringTokenizer(value, SPACE);
		while (tokenizer.hasMoreTokens()) {
			String nextToken = tokenizer.nextToken();
			if (nextToken.equals(this.value)) {
				return true;
			}
		}
		return false;
	}
}
