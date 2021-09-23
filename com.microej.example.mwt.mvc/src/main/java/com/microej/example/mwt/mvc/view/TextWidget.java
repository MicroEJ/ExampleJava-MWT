/*
 * Copyright 2009-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc.view;

import com.microej.example.mwt.mvc.model.PercentageModel;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.util.Alignment;

/**
 * A widget displaying the value as a text.
 */
public class TextWidget extends PercentageWidget {

	/**
	 * Creates a textual widget.
	 *
	 * @param model
	 *            the percentage model.
	 */
	public TextWidget(PercentageModel model) {
		super(model);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		// build message to print
		String message = new StringBuffer().append("Value: ").append(getModel().getValue()).append("%").toString();

		// draw message in the middle of the view
		Font font = getStyle().getFont();
		int x = Alignment.computeLeftX(font.stringWidth(message), contentWidth / 2, Alignment.HCENTER);
		int y = Alignment.computeTopY(font.getHeight(), contentHeight / 2, Alignment.VCENTER);
		Painter.drawString(g, message, font, x, y);

	}
}
