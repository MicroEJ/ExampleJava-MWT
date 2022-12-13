/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute;

import java.util.HashMap;
import java.util.Map;

import com.microej.example.mwt.attribute.AttributeHolder;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;
import ej.mwt.util.Size;

public class AttributeWidget extends Widget implements AttributeHolder {

	private final Map<String, String> attributes;

	public AttributeWidget() {
		this.attributes = new HashMap<>();
	}

	public void addAttribute(String attribute, String value) {
		this.attributes.put(attribute, value);
	}

	@Override
	@Nullable
	public String getAttribute(String attribute) {
		return this.attributes.get(attribute);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		// do nothing
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		// do nothing
	}
}
