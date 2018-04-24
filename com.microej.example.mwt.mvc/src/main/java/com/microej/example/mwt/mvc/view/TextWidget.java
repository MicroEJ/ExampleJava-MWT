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
 * A widget displaying the value as a text.
 */
public class TextWidget extends PercentageWidget {

	/**
	 * @param model
	 */
	public TextWidget(PercentageModel model) {
		super(model);
	}

	@Override
	public void render(GraphicsContext g) {

		super.render(g);

		// build message to print
		String message = new StringBuffer()
				.append("Value: ")
				.append(getModel().getValue())
				.append("%")
				.toString();

		// draw message in the middle of the view
		g.drawString(message, getWidth() / 2 + getX(), getHeight() / 2 + getY(),
				GraphicsContext.VCENTER | GraphicsContext.HCENTER);

	}
}
