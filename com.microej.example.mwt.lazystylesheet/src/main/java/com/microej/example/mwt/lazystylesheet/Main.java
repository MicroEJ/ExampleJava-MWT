/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.lazystylesheet;

import com.microej.example.mwt.lazystylesheet.style.CascadingLazyStylesheet;
import com.microej.example.mwt.lazystylesheet.style.StyleFactory;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;

/**
 * Main class.
 */
public class Main {

	private static final String SOURCE_LIGHT_20 = "/fonts/source_l_14px-20px-26h.ejf";
	private static final int BUTTON_MARGIN = 20;
	private static final int BORDER_THICKNESS = 2;

	/**
	 * Starts the application.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();

		CascadingLazyStylesheet stylesheet = new CascadingLazyStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setFont(Font.getFont(SOURCE_LIGHT_20));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		stylesheet.setSelectorStyle(new TypeSelector(Button.class), new StyleFactory() {
			@Override
			public void applyOn(EditableStyle style) {
				style.setMargin(new UniformOutline(BUTTON_MARGIN));
				style.setBorder(new RectangularBorder(Colors.SILVER, BORDER_THICKNESS));
			}
		});

		stylesheet.setSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)),
				new StyleFactory() {
					@Override
					public void applyOn(EditableStyle style) {
						style.setColor(Colors.RED);
						style.setBorder(new RectangularBorder(Colors.RED, BORDER_THICKNESS));
						style.setBackground(new RectangularBackground(Colors.SILVER));
					}
				});

		Button button = new Button("Press me");

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(button);
		desktop.requestShow();
	}
}
