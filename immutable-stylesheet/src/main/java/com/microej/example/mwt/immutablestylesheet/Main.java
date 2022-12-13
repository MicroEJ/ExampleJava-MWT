/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.immutablestylesheet;

import ej.bon.Immutables;
import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.stylesheet.CachedStylesheet;
import ej.mwt.stylesheet.Stylesheet;
import ej.widget.basic.Label;
import ej.widget.container.Flow;
import ej.widget.container.LayoutOrientation;

/**
 * Contains the startup method.
 */
public class Main {

	private static final int YELLOWBACKGROUND = 600;

	/**
	 * Starts the immutable stylesheet example.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();

		Stylesheet stylesheet = loadStylesheet();

		Label label = new Label("This is an example");

		Label labelYellowBackground = new Label("of immutable stylesheet.");
		labelYellowBackground.addClassSelector(YELLOWBACKGROUND);

		Flow flowContainer = new Flow(LayoutOrientation.VERTICAL);
		flowContainer.addChild(label);
		flowContainer.addChild(labelYellowBackground);

		Desktop desktop = new Desktop();
		desktop.setStylesheet(new CachedStylesheet(stylesheet));
		desktop.setWidget(flowContainer);
		desktop.requestShow();
	}

	private static Stylesheet loadStylesheet() {
		return (Stylesheet) Immutables.get("com.microej.example.mwt.immutablestylesheet.STYLESHEET");
	}
}
