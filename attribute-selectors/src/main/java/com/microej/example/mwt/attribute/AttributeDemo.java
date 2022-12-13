/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute;

import com.microej.example.mwt.attribute.selector.AttributeValueSelector;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * This demo illustrates the usage of attributes to customize the styles of widgets.
 */
public class AttributeDemo {

	private static final String SOME_ATTRIBUTE = "status";
	private static final String ON = "ON";
	private static final String OFF = "OFF";

	private static final int LIST_PADDING = 80;
	private static final int PADDING_MARGIN = 4;
	private static final int GREEN = 0x009900;
	private static final int AMBER = 0xf8a331;

	// Prevents initialization.
	private AttributeDemo() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();

		// Show the UI.
		Desktop desktop = new Desktop();
		desktop.setStylesheet(createStylesheet());

		final AttributeLabel attributeLabel = new AttributeLabel(OFF);
		attributeLabel.setAttribute(SOME_ATTRIBUTE, OFF);

		final Button button = new Button("Toggle attribute value");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				String oldValue = attributeLabel.getAttribute(SOME_ATTRIBUTE);
				assert (oldValue != null);
				String newValue = (oldValue.equals(ON) ? OFF : ON);
				attributeLabel.setText(newValue);
				attributeLabel.setAttribute(SOME_ATTRIBUTE, newValue);
				attributeLabel.requestRender();
			}
		});

		List list = new List(LayoutOrientation.HORIZONTAL);
		list.addChild(button);
		list.addChild(attributeLabel);

		desktop.setWidget(list);
		desktop.requestShow();
	}

	private static Stylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(new TypeSelector(List.class));
		style.setPadding(new UniformOutline(LIST_PADDING));

		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setPadding(new UniformOutline(PADDING_MARGIN));
		style.setBorder(new RectangularBorder(AMBER, 2));

		style = stylesheet.getSelectorStyle(new AttributeValueSelector(SOME_ATTRIBUTE, ON));
		style.setBackground(new RectangularBackground(GREEN));

		return stylesheet;
	}
}
