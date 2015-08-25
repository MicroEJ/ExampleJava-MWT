/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.widget;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.WidgetRenderer;

/**
 * 
 */
public class ScreenshotTransitionCompositeRenderer extends WidgetRenderer {

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return widget.getWidth();
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return widget.getHeight();
	}

	@Override
	public Class<ScreenshotTransitionComposite> getManagedType() {
		return ScreenshotTransitionComposite.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		// Delegates to the widget.
		ScreenshotTransitionComposite composite = (ScreenshotTransitionComposite) renderable;
		composite.paint(g);
	}

}
