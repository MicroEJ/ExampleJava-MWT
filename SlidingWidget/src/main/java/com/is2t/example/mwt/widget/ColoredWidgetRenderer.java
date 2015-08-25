/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.widget;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.rendering.Renderer;

/**
 * Renderer for the {@link ColoredWidget}.
 */
public class ColoredWidgetRenderer extends Renderer {

	private static final int PADDING = 10;

	private static final DisplayFont BIG_FONT = DisplayFont.getFont(DisplayFont.LATIN, 50, DisplayFont.STYLE_BOLD);

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		ColoredWidget coloredWidget = (ColoredWidget) renderable;
		int width = coloredWidget.getWidth();
		int height = coloredWidget.getHeight();
		// draw border
		g.setColor(coloredWidget.getOtherColor());
		g.fillRect(0, 0, width, height);
		// fill background
		g.setColor(coloredWidget.getColor());
		g.fillRect(PADDING, PADDING, width - PADDING * 2, height - PADDING * 2);
		// draw centered value
		g.setFont(BIG_FONT);
		g.setColor(coloredWidget.getOtherColor());
		String value = String.valueOf(coloredWidget.getValue());
		g.drawString(value, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	@Override
	public Class<ColoredWidget> getManagedType() {
		return ColoredWidget.class;
	}
}