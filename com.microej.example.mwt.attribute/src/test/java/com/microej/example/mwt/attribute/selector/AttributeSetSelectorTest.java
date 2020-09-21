/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

import org.junit.Assert;
import org.junit.Test;

import com.microej.example.mwt.attribute.AttributeWidget;
import com.microej.example.mwt.attribute.selector.AttributeSetSelector;

import ej.mwt.stylesheet.selector.SelectorHelper;

public class AttributeSetSelectorTest {

	private static final String ATTRIBUTE = "attr";

	@Test
	public void test() {
		AttributeSetSelector selector = new AttributeSetSelector(ATTRIBUTE);

		Assert.assertEquals("Wrong specificity", SelectorHelper.getSpecificity(0, 0, 1, 0),
				selector.getSpecificity());

		// Test: the widget does not contain the attribute
		AttributeWidget widget = new AttributeWidget();
		Assert.assertFalse("Selector matches", selector.appliesToWidget(widget));

		// Test: the widget contains the attribute
		widget.addAttribute(ATTRIBUTE, "xxx");
		Assert.assertTrue("Selector does not match", selector.appliesToWidget(widget));
	}
}
