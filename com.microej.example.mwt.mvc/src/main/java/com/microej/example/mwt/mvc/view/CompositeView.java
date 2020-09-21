/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc.view;

import ej.mwt.Container;
import ej.mwt.util.Size;

/**
 * A view displaying different way to display a value.
 */
public class CompositeView extends Container {

	private final TextWidget textWidget;
	private final PieWidget pieWidget;
	private final BarWidget barWidget;

	private int xCross;
	private int yCross;

	/**
	 * Instantiates a {@link CompositeView}.
	 * 
	 * @param textWidget
	 *            the text widget.
	 * @param pieWidget
	 *            the pieWidget.
	 * @param barWidget
	 *            the barWidget.
	 */
	public CompositeView(TextWidget textWidget, PieWidget pieWidget, BarWidget barWidget) {
		this.textWidget = textWidget;
		this.pieWidget = pieWidget;
		this.barWidget = barWidget;
		addChild(textWidget);
		addChild(pieWidget);
		addChild(barWidget);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		// Does the positioning
		layOutChild(this.textWidget, 0, 0, this.xCross, this.yCross);
		layOutChild(this.barWidget, 0, this.yCross, this.xCross, contentHeight - this.yCross);
		layOutChild(this.pieWidget, this.xCross, 0, contentWidth - this.xCross, contentHeight);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		// Validate widgets
		computeChildOptimalSize(this.textWidget, this.xCross, this.yCross);
		computeChildOptimalSize(this.barWidget, this.xCross, size.getHeight() - this.yCross);
		computeChildOptimalSize(this.pieWidget, size.getWidth() - this.xCross, size.getHeight());
	}

	/**
	 * Gets the xCross.
	 *
	 * @return the xCross.
	 */
	public int getXCross() {
		return this.xCross;
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
		return this.yCross;
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
