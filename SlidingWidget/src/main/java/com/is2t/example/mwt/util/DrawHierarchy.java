/*
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.util;

import ej.microui.io.ComponentView;
import ej.microui.io.CompositeView;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Viewable;
import ej.mwt.Composite;
import ej.mwt.Desktop;
import ej.mwt.Panel;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Renderer;

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
	 * Draws the given viewable in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the viewable.
	 * @param viewable
	 *            the viewable to draw.
	 */
	public static void draw(GraphicsContext gc, Viewable viewable) {
		viewable.paint(gc);

		ComponentView componentView = viewable.getComponentView();
		draw(gc, componentView);
	}

	/**
	 * Draws the given componentView in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the componentView.
	 * @param componentView
	 *            the componentView to draw.
	 */
	public static void draw(GraphicsContext gc, ComponentView componentView) {
		componentView.paint(gc);

		if (componentView instanceof CompositeView) {
			CompositeView composite = (CompositeView) componentView;

			// draw children
			ComponentView[] viewsArray = composite.getViews();
			for (int j = viewsArray.length; --j >= 0;) {

				ComponentView child = viewsArray[j];

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

	/**
	 * Draws the given desktop in the given graphic context.
	 * 
	 * @param gc
	 *            the graphic context where to draw the desktop.
	 * @param desktop
	 *            the desktop to draw.
	 */
	public static void draw(GraphicsContext gc, Desktop desktop) {
		drawRenderable(gc, desktop);

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
		drawRenderable(gc, panel);

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
		drawRenderable(gc, widget);

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

	private static void drawRenderable(GraphicsContext gc, Renderable renderable) {
		Renderer renderer = renderable.getRenderer();

		if (renderer != null) {
			renderer.render(gc, renderable);
		}
	}

}
