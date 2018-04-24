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
 * A widget displaying the percentage as a bar.
 */
public class BarWidget extends PercentageWidget {
	
	private static final int COLOR_CONTENT = 0xe86337; // orange

	/**
	 * Instantiates a {@link BarWidget}.
	 * @param model the model to follow.
	 */
	public BarWidget(PercentageModel model) {
		super(model);
	}

	@Override
	public void render(GraphicsContext g) {

		super.render(g);

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
		int fillHeight = percentage * (barHeight - 1) / 100;

		// draw bar fill
		g.setColor(COLOR_CONTENT);
		g.fillRect(barX, barY + barHeight - fillHeight - 1,
				barWidth, fillHeight);

		// draw bar border
		g.setColor(COLOR_DATA_BORDER);
		g.drawRect(barX, barY, barWidth - 1, barHeight - 1);
	}
}
