/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.virtualwatch;

import ej.annotation.Nullable;
import ej.basictool.map.PackedMap;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;

/**
 * Container that represents the watch and its peripherals (screen and button).
 * <p>
 * Its visibility is the default one (package) because it is only used by the virtual watch desktop.
 */
class VirtualWatchContainer extends Container {

	private final String imagePath;
	private final int screenX;
	private final int screenY;
	private final int screenWidth;
	private final int screenHeight;
	private final PackedMap<Widget, Rectangle> widgetBounds;

	@Nullable
	private Image image;
	@Nullable
	private Widget content;

	/**
	 * Creates a watch container.
	 *
	 * @param watchImagePath
	 *            the path to the image representing the watch.
	 * @param screenX
	 *            the screen x coordinate.
	 * @param screenY
	 *            the screen y coordinate.
	 * @param screenWidth
	 *            the screen width coordinate.
	 * @param screenHeight
	 *            the screen height coordinate.
	 */
	VirtualWatchContainer(String watchImagePath, int screenX, int screenY, int screenWidth, int screenHeight) {
		this.imagePath = watchImagePath;
		this.screenX = screenX;
		this.screenY = screenY;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.widgetBounds = new PackedMap<>();
	}

	/**
	 * Sets the content widget.
	 *
	 * @param content
	 *            the content widget.
	 */
	void setContent(@Nullable Widget content) {
		Widget formerContent = this.content;
		if (formerContent != null) {
			removeChild(formerContent);
		}
		this.content = content;
		if (content != null) {
			insertChild(content, 0);
		}
	}

	/**
	 * Gets the content.
	 *
	 * @return the content.
	 */
	@Nullable
	public Widget getContent() {
		return this.content;
	}

	/**
	 * Adds a widget. The widget is laid out with the given bounds.
	 *
	 * @param child
	 *            the widget.
	 * @param x
	 *            the x coordinate of the widget.
	 * @param y
	 *            the y coordinate of the widget.
	 * @param width
	 *            the width of the widget.
	 * @param height
	 *            the height of the widget.
	 */
	void addChild(Widget child, int x, int y, int width, int height) {
		super.addChild(child);
		this.widgetBounds.put(child, new Rectangle(x, y, width, height));
	}

	@Override
	protected void onAttached() {
		super.onAttached();
		this.image = Image.getImage(this.imagePath);
	}

	@Override
	protected void onDetached() {
		super.onDetached();
		this.image = null;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Image image = this.image;
		assert image != null;
		size.setSize(image.getWidth(), image.getHeight());

		Widget content = this.content;
		if (content != null) {
			computeChildOptimalSize(content, this.screenWidth, this.screenHeight);
		}

		PackedMap<Widget, Rectangle> widgetBounds = this.widgetBounds;
		for (Widget child : widgetBounds.keySet()) {
			assert (child != null);

			// get child desired bounds
			Rectangle bounds = widgetBounds.get(child);
			int childWidth = bounds.getWidth();
			int childHeight = bounds.getHeight();

			computeChildOptimalSize(child, childWidth, childHeight);
		}
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		Widget content = this.content;
		if (content != null) {
			layOutChild(content, this.screenX, this.screenY, this.screenWidth, this.screenHeight);
		}

		PackedMap<Widget, Rectangle> widgetBounds = this.widgetBounds;
		for (Widget child : widgetBounds.keySet()) {
			assert (child != null);

			// get child desired bounds
			Rectangle bounds = widgetBounds.get(child);
			int childX = bounds.getX();
			int childY = bounds.getY();
			int childWidth = bounds.getWidth();
			int childHeight = bounds.getHeight();

			layOutChild(child, childX, childY, childWidth, childHeight);
		}
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		int translateX = g.getTranslationX();
		int translateY = g.getTranslationY();
		int clipX = g.getClipX();
		int clipY = g.getClipY();
		int clipWidth = g.getClipWidth();
		int clipHeight = g.getClipHeight();

		Widget content = this.content;
		if (content != null) {
			renderChild(content, g);
		}

		g.setTranslation(translateX, translateY);
		g.setClip(clipX, clipY, clipWidth, clipHeight);
		assert this.image != null;
		Painter.drawImage(g, this.image, 0, 0);

		for (Widget child : this.widgetBounds.keySet()) {
			assert child != null;
			renderChild(child, g);
			g.setTranslation(translateX, translateY);
			g.setClip(clipX, clipY, clipWidth, clipHeight);
		}
	}

}
