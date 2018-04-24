/**
 * Java
 *
 * Copyright 2009-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.mvc.view;

import java.util.Observable;
import java.util.Observer;

import com.microej.example.mwt.mvc.model.PercentageModel;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;

/**
 * 
 * A widget displaying a percentage.
 *
 */
public abstract class PercentageWidget extends Widget implements Observer {

	private static final int COLOR_BACKGROUND = Colors.WHITE;
	private static final int COLOR_VIEW_BORDER = Colors.BLACK;
	/**
	 * The color used for the data border.
	 */
	protected static final int COLOR_DATA_BORDER = 0x506a96;	// blue
	
	/**
	 * The model.
	 */
	protected PercentageModel model;

	/**
	 * Instantiates a {@link PercentageWidget}.
	 * @param model the model to follow.
	 */
	public PercentageWidget(PercentageModel model) {
		setModel(model);
	}

	@Override
	public void render(GraphicsContext g) {
		int width = getWidth();
		int height = getHeight();

		// clear view
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(0, 0, width, height);

		// draw view borders
		g.setColor(COLOR_VIEW_BORDER);
		g.drawRect(0, 0, width - 1, height - 1);

	}

	@Override
	public void validate(int widthHint, int heightHint) {
		setPreferredSize(widthHint, heightHint);

	}

	/**
	 * Gets the model.
	 *
	 * @return the model.
	 */
	public PercentageModel getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model
	 *            the model to set.
	 */
	public void setModel(PercentageModel model) {
		if (this.model != null) {
			this.model.deleteObserver(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addObserver(this);
		}

	}

	@Override
	// Called when the model has changed
	public void update(Observable o, Object arg) {
		repaint();
	}
}
