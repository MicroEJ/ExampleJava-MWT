/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.renderer;

import com.is2t.example.mwt.widget.Label;

import ej.microui.io.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

/**
 * This renderer knows how to display a {@link Label}.
 */
public class LabelRenderer extends WidgetRenderer {

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return MWT.NONE;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return MWT.NONE;
	}

	@Override
	public Class<? extends Widget> getManagedType() {
		return Label.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		Label label = (Label) renderable;
		Look look = getLook();

		// Background
		g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT));
		g.fillRect(0, 0, renderable.getWidth(), renderable.getHeight());

		// Text
		String text = label.getText();
		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
		g.drawString(text, renderable.getWidth() / 2,
				renderable.getHeight() / 2, GraphicsContext.HCENTER
						| GraphicsContext.VCENTER);
	}

}
