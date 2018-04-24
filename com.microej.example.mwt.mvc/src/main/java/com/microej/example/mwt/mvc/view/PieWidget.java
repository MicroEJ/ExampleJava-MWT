/**
 * Java
 *
 * Copyright 2009-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.mvc.view;

import com.microej.example.mwt.mvc.model.PercentageModel;

import ej.microui.display.GraphicsContext;

/**
 * A widget displaying a pie.
 */
public class PieWidget extends PercentageWidget {

	public static final int COLOR_CONTENT = 0x2fc19c; // green

	/**
	 * @param model
	 */
	public PieWidget(PercentageModel model) {
		super(model);
	}

	@Override
	public void render(GraphicsContext g) {
		super.render(g);

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
		g.fillCircleArc(pieXCenter, pieYCenter, pieDiameter, 0, fillAngle);

		// draw pie border
		g.setColor(COLOR_DATA_BORDER);
		g.drawCircle(pieXCenter, pieYCenter, pieDiameter);

	}
}
