/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.bufferedimagepool;

import ej.annotation.Nullable;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;

/**
 * Histogram that shows several values.
 * <p>
 * The grid is drawn in an image to avoid to draw it for each rendering.
 */
public class Histogram extends Widget {

	private static final int OPTIMAL_PIXELS_PER_ENTRY = 2;
	private static final int OPTIMAL_PIXELS_PER_VALUE = 5;

	private static final int SIDE_SPACE = 1;

	private static final int VALUES_COUNT = 10;
	private static final int INTERVAL_COUNT = 10;

	/**
	 * The grid color extra int index in the style.
	 */
	public static final int GRID_COLOR = 0;

	private final int maxValue;
	private final int[] values;

	@Nullable
	private BufferedImage grid;

	/**
	 * Creates an histogram.
	 *
	 * @param maxValue
	 *            the max value.
	 */
	public Histogram(int maxValue) {
		this.maxValue = maxValue;
		this.values = new int[VALUES_COUNT];
	}

	/**
	 * Adds a new value in the histogram.
	 *
	 * @param value
	 *            the value to add.
	 */
	public void addValue(int value) {
		System.arraycopy(this.values, 1, this.values, 0, VALUES_COUNT - 1);
		this.values[VALUES_COUNT - 1] = value;
		requestRender();
	}

	@Override
	protected void onLaidOut() {
		super.onLaidOut();
		if (isShown()) {
			// Acquire the image once the histogram is shown and laid out (its size is defined).
			try {
				this.grid = BufferedImagePool.acquireImage(this);
				GraphicsContext g = this.grid.getGraphicsContext();
				g.reset();

				Rectangle contentBounds = getContentBounds();
				int contentWidth = contentBounds.getWidth();
				int contentHeight = contentBounds.getHeight();

				// Apply the background to clean the graphics context.
				getStyle().getBackground().apply(g, contentWidth, contentHeight);

				// Draw the grid.
				drawGrid(g, contentWidth, contentHeight);
			} catch (OutOfImagesException e) {
				// Can't use an image since there is none available.
			}
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		// Release the image once the histogram is hidden.
		if (this.grid != null) {
			BufferedImagePool.releaseImage(this);
			this.grid = null;
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		size.setSize(VALUES_COUNT * OPTIMAL_PIXELS_PER_ENTRY, this.maxValue * OPTIMAL_PIXELS_PER_VALUE);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		if (this.grid != null) {
			Painter.drawImage(g, this.grid, 0, 0);
		} else {
			drawGrid(g, contentWidth, contentHeight);
		}

		g.setColor(getStyle().getColor());
		float xShift = (float) (contentWidth - 1) / VALUES_COUNT;
		int barWidth = Math.round(xShift) - 2 * SIDE_SPACE - 1;
		float x = 0;
		for (int value : this.values) {
			int valueHeight = value * contentHeight / this.maxValue;
			Painter.fillRectangle(g, Math.round(x) + 2 * SIDE_SPACE, contentHeight - valueHeight, barWidth,
					valueHeight);
			x += xShift;
		}
	}

	private void drawGrid(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getExtraInt(GRID_COLOR, style.getColor()));

		float xShift = (float) (contentWidth - 1) / VALUES_COUNT;
		float x = 0;
		for (int i = 0; i <= VALUES_COUNT; i++) {
			Painter.drawVerticalLine(g, Math.round(x), 0, contentHeight);
			x += xShift;
		}

		float yShift = (float) (contentHeight - 1) / INTERVAL_COUNT;
		float y = 0;
		for (int i = 0; i <= INTERVAL_COUNT; i++) {
			Painter.drawHorizontalLine(g, 0, Math.round(y), contentWidth);
			y += yShift;
		}
	}

}
