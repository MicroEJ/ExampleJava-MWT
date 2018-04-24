/**
 * Java
 *
 * Copyright 2014-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.util;

import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.mwt.Composite;
import ej.mwt.Desktop;
import ej.mwt.Panel;
import ej.mwt.Widget;

/**
 * Utilities to take a screenshot of displayable objects.
 */
public class DrawHierarchy {

	// Prevents initialization.
	private DrawHierarchy() {
	}

	/**
	 * Draws the given displayable in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the displayable.
	 * @param displayable
	 *            the displayable to draw.
	 */
	public static void draw(GraphicsContext gc, Displayable displayable) {
		displayable.paint(gc);
	}


	/**
	 * Draws the given desktop in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the desktop.
	 * @param desktop
	 *            the desktop to draw.
	 */
	public static void draw(GraphicsContext gc, Desktop desktop) {
		desktop.render(gc);

		Panel[] panels = desktop.getPanels();

		for (int i = panels.length; --i >= 0;) {
			Panel child = panels[i];

			// set translation
			gc.translate(child.getX(), child.getY());
			int clipX = gc.getClipX();
			int clipY = gc.getClipY();
			int clipWidth = gc.getClipWidth();
			int clipHeight = gc.getClipHeight();
			gc.clipRect(0, 0, child.getWidth(), child.getHeight());
			try {
				draw(gc, child);
			} finally {
				// restore translation
				gc.setClip(clipX, clipY, clipWidth, clipHeight);
				gc.translate(-child.getX(), -child.getY());
			}
		}
	}

	/**
	 * Draws the given panel in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the panel.
	 * @param panel
	 *            the panel to draw.
	 */
	public static void draw(GraphicsContext gc, Panel panel) {
		panel.render(gc);

		Widget child = panel.getWidget();
		if (child != null && child.isVisible()) {
			// set translation
			gc.translate(child.getX(), child.getY());
			int clipX = gc.getClipX();
			int clipY = gc.getClipY();
			int clipWidth = gc.getClipWidth();
			int clipHeight = gc.getClipHeight();
			gc.clipRect(0, 0, child.getWidth(), child.getHeight());
			try {
				draw(gc, child);
			} finally {
				// restore translation
				gc.setClip(clipX, clipY, clipWidth, clipHeight);
				gc.translate(-child.getX(), -child.getY());
			}
		}
	}

	/**
	 * Draws the given widget in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the widget.
	 * @param widget
	 *            the widget to draw.
	 */
	public static void draw(GraphicsContext gc, Widget widget) {
		widget.render(gc);
		if (widget instanceof Composite) {
			Composite composite = (Composite) widget;
			draw(gc, composite);
		}
	}

	/**
	 * Draws the given composite in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the composite.
	 * @param composite
	 *            the composite to draw.
	 */
	public static void draw(GraphicsContext gc, Composite composite) {
		Widget[] widgetsArray = composite.getWidgets();

		for (int j = widgetsArray.length; --j >= 0;) {
			Widget child = widgetsArray[j];

			if (!child.isVisible()) {
				continue;
			}

			// set translation
			gc.translate(child.getX(), child.getY());
			int clipX = gc.getClipX();
			int clipY = gc.getClipY();
			int clipWidth = gc.getClipWidth();
			int clipHeight = gc.getClipHeight();
			gc.clipRect(0, 0, child.getWidth(), child.getHeight());
			try {
				draw(gc, child);
			} finally {
				// restore translation
				gc.setClip(clipX, clipY, clipWidth, clipHeight);
				gc.translate(-child.getX(), -child.getY());
			}
		}
	}

}
