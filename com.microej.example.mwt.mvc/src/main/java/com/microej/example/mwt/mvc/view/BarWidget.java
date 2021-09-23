/*
 * Copyright 2009-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc.view;

import com.microej.example.mwt.mvc.model.PercentageModel;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * A widget displaying the percentage as a bar.
 */
public class BarWidget extends PercentageWidget {

	private static final int COLOR_CONTENT = 0xe86337; // orange

	/**
	 * Creates a bar widget.
	 *
	 * @param model
	 *            the percentage model.
	 */
	public BarWidget(PercentageModel model) {
		super(model);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		// get value of the percentage
		int percentage = getModel().getValue();

		// compute bar coordinates
		int width = getWidth();
		int height = getHeight();
		int barWidth = width / 2;
		int barHeight = height / 2;
		int barX = (width - barWidth) / 2;
		int barY = (height - barHeight) / 2;

		// compute the height that fits the percentage
		int fillHeight = percentage * barHeight / 100;

		// draw bar fill
		g.setColor(COLOR_CONTENT);
		Painter.fillRectangle(g, barX, barY + barHeight - fillHeight, barWidth, fillHeight);

		// draw bar border
		g.setColor(COLOR_DATA_BORDER);
		Painter.drawRectangle(g, barX, barY, barWidth, barHeight);
	}
}
