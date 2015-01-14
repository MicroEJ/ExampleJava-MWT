/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.examples.mwt.renderer;

import com.is2t.examples.mwt.widget.SensorWidget;

import ej.microui.io.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

/**
 * This renderer knows how to display a {@link SensorWidget}.
 */
public class SensorWidgetRenderer extends WidgetRenderer {

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
		return SensorWidget.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		System.out.println("SensorWidgetRenderer.render() is executed by thread " + Thread.currentThread().getName());

		SensorWidget sensor = (SensorWidget) renderable; // MWT guarantees that 'renderable' is in fact a SensorWidget (see getManagedType())
		Look look = getLook(); // We retrieve properties from the look to display the widget

		// Background
		g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT));
		g.fillRect(0, 0, renderable.getWidth(), renderable.getHeight());

		// Text
		String title = sensor.getTitle();
		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
		g.drawString(title, renderable.getWidth() / 2, renderable.getHeight() / 3, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		// StringBuilder are better than +
		StringBuilder builder = new StringBuilder();
		builder.append(sensor.getValue());
		builder.append(" ");
		builder.append(sensor.getUnit());
		String str = builder.toString();

		g.drawString(str, renderable.getWidth() / 2, renderable.getHeight() * 2 / 3, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

}