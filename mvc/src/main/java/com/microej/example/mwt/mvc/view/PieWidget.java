/*
* Copyright 2009-2022 MicroEJ Corp. All rights reserved.
* Use of this source code is governed by a BSD-style license that can be found with this software.
*/
package com.microej.example.mwt.mvc.view;

import com.microej.example.mwt.mvc.model.PercentageModel;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * A widget displaying a pie.
 */
public class PieWidget extends PercentageWidget {

	private static final int COLOR_CONTENT = 0x2fc19c; // green

	/**
	 * Creates a pie widget.
	 *
	 * @param model
	 *            the percentage model.
	 */
	public PieWidget(PercentageModel model) {
		super(model);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		// get value of the percentage
		int percentage = getModel().getValue();

		// compute pie coordinates
		int width = getWidth();
		int height = getHeight();
		int pieDiameter = Math.min(width, height) / 2;
		int pieXCenter = (width - pieDiameter) / 2;
		int pieYCenter = (height - pieDiameter) / 2;

		// compute angle fit the percentage
		int fillAngle = percentage * 360 / 100;

		// draw pie fill
		g.setColor(COLOR_CONTENT);
		Painter.fillCircleArc(g, pieXCenter, pieYCenter, pieDiameter, 0, fillAngle);

		// draw pie border
		g.setColor(COLOR_DATA_BORDER);
		Painter.drawCircle(g, pieXCenter, pieYCenter, pieDiameter);

	}
}
