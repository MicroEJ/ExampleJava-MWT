/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc;

import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.render.DefaultRenderPolicy;

public class CursorImageRenderPolicy extends DefaultRenderPolicy {

	public static final int CURSOR_WIDTH = 16;
	public static final int CURSOR_HEIGHT = 16;

	private final Image cursorImage;

	public CursorImageRenderPolicy(Desktop desktop) {
		super(desktop);
		this.cursorImage = Image.getImage("/images/mouse_cursor.png");
	}

	@Override
	protected void renderWidget(Widget widget, int x, int y, int width, int height) {
		super.renderWidget(widget, x, y, width, height);

		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		if (pointer != null) {
			GraphicsContext g = Display.getDisplay().getGraphicsContext();
			g.resetTranslation();
			g.setClip(widget.getAbsoluteX() + x, widget.getAbsoluteY() + y, width, height);
			Painter.drawImage(g, this.cursorImage, pointer.getX(), pointer.getY());
		}
	}
}
