/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

/**
 * An attribute set selector selects by checking if an attribute is set.
 * <p>
 * Equivalent to <code>[attribute]</code> selector in CSS. Its specificity is (0,0,1,0).
 *
 * @see com.microej.example.mwt.attribute.AttributeHolder#getAttribute(String)
 */
public class AttributeSetSelector extends AbstractAttributeSelector {

	/**
	 * Creates an attribute set selector.
	 *
	 * @param attribute
	 *            the attribute to check.
	 */
	public AttributeSetSelector(String attribute) {
		super(attribute);
	}

	@Override
	public boolean attributeFits(String value) {
		return true;
	}
}
