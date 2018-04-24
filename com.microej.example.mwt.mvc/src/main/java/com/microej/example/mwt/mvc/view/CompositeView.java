/**
 * Java
 *
 * Copyright 2016-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.mvc.view;

import ej.microui.display.GraphicsContext;
import ej.mwt.Composite;

/**
 * A view displaying different way to display a value.
 */
public class CompositeView extends Composite {

	private int xCross;
	private int yCross;
	private final TextWidget textWidget;
	private final PieWidget pieWidget;
	private final BarWidget barWidget;

	/**
	 * Instantiates a {@link CompositeView}.
	 * @param textWidget 
	 * 			the text widget.
	 * @param pieWidget 
	 * 			the pieWidget.
	 * @param barWidget
	 *			the barWidget.
	 */
	public CompositeView(TextWidget textWidget, PieWidget pieWidget, BarWidget barWidget) {
		super();
		this.textWidget = textWidget;
		this.pieWidget = pieWidget;
		this.barWidget = barWidget;
		add(textWidget);
		add(pieWidget);
		add(barWidget);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		// Does the positioning
		textWidget.setBounds(0, 0, xCross, yCross);
		barWidget.setBounds(0, yCross, xCross, height - yCross);
		pieWidget.setBounds(xCross, 0, width - xCross, height);
	}


	@Override
	public void validate(int widthHint, int heightHint) {
		setPreferredSize(widthHint, heightHint);
		// Validate widgets
		textWidget.validate(xCross, yCross);
		barWidget.validate(xCross, heightHint - yCross);
		pieWidget.validate(widthHint - xCross, heightHint);
	}

	@Override
	public void render(GraphicsContext g) {
		// nothing to do
	}

	/**
	 * Gets the xCross.
	 *
	 * @return the xCross.
	 */
	public int getXCross() {
		return xCross;
	}

	/**
	 * Sets the xCross.
	 *
	 * @param xCross
	 *            the xCross to set.
	 */
	public void setXCross(int xCross) {
		this.xCross = xCross;
	}

	/**
	 * Gets the yCross.
	 *
	 * @return the yCross.
	 */
	public int getYCross() {
		return yCross;
	}

	/**
	 * Sets the yCross.
	 *
	 * @param yCross
	 *            the yCross to set.
	 */
	public void setYCross(int yCross) {
		this.yCross = yCross;
	}
}
