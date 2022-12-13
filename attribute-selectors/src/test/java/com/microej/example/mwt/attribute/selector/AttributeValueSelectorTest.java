/*
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

import org.junit.Assert;
import org.junit.Test;

import com.microej.example.mwt.attribute.AttributeWidget;
import com.microej.example.mwt.attribute.selector.AttributeValueSelector;

import ej.mwt.stylesheet.selector.SelectorHelper;

public class AttributeValueSelectorTest {

	private static final String ATTRIBUTE = "attr";
	private static final String VALUE = "val";

	@Test
	public void test() {
		AttributeValueSelector selector = new AttributeValueSelector(ATTRIBUTE, VALUE);

		Assert.assertEquals("Wrong specificity", SelectorHelper.getSpecificity(0, 0, 1, 0),
				selector.getSpecificity());

		// Test: the widget does not contain the attribute
		AttributeWidget widget = new AttributeWidget();
		Assert.assertFalse("Selector matches", selector.appliesToWidget(widget));

		// Test: the widget contains the attribute with another value
		widget.addAttribute(ATTRIBUTE, "xxx");
		Assert.assertFalse("Selector matches", selector.appliesToWidget(widget));

		// Test: the widget contains the attribute with the right value
		widget.addAttribute(ATTRIBUTE, VALUE);
		Assert.assertTrue("Selector does not match", selector.appliesToWidget(widget));
	}
}
