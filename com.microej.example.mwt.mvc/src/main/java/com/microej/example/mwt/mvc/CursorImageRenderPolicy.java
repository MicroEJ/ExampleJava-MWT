/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
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

/**
 * This render policy draws the image of a cursor at the pointer's position.
 */
public class CursorImageRenderPolicy extends DefaultRenderPolicy {

	/**
	 * The width of the cursor's image.
	 */
	public static final int CURSOR_WIDTH = 16;
	/**
	 * The height of the cursor's image.
	 */
	public static final int CURSOR_HEIGHT = 16;

	private final Image cursorImage;

	/**
	 * Creates a cursor image render policy.
	 *
	 * @param desktop
	 *            the desktop
	 */
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
