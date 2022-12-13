/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.contextsensitive;

import com.microej.example.mwt.contextsensitive.configuration.Configuration;
import com.microej.example.mwt.contextsensitive.configuration.WristMode;

import ej.annotation.Nullable;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 * Lays out two children horizontally. The position of the children within the container depends on the context (e.g.,
 * the device configuration).
 * <p>
 * The two children are laid out on the same horizontal line, one at the center and the other on one side. The side
 * (left or right) depends on the context.
 * <p>
 * The side widget will have the height of the available space and will have its optimal width. The main widget will
 * have the size of the remaining space.
 */
public class ContextSensitiveContainer extends Container {

	@Nullable
	private Widget main;

	@Nullable
	private Widget side;

	/**
	 * Sets the main widget.
	 *
	 * @param child
	 *            the widget to add.
	 * @throws NullPointerException
	 *             if the given widget is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the specified widget is already in a hierarchy (already contained in a container or desktop).
	 * @see #addChild(Widget)
	 */
	public void setMainChild(Widget child) {
		Widget main = this.main;
		if (main != null) {
			removeChild(main);
		}

		this.main = child;
		addChild(child);
	}

	/**
	 * Sets the side widget. Depending on the configuration, the side widget is on the left side, otherwise it is on the
	 * right side.
	 *
	 * @param child
	 *            the widget to add.
	 * @throws NullPointerException
	 *             if the given widget is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the specified widget is already in a hierarchy (already contained in a container or desktop).
	 * @see #addChild(Widget)
	 */
	public void setSideChild(Widget child) {
		Widget side = this.side;
		if (side != null) {
			removeChild(side);
		}

		this.side = child;
		addChild(child);
	}

	@Override
	public void removeChild(Widget widget) {
		if (widget == this.side) {
			this.side = null;
		} else if (widget == this.main) {
			this.main = null;
		}
		super.removeChild(widget);
	}

	@Override
	public void removeAllChildren() {
		super.removeAllChildren();
		this.side = null;
		this.main = null;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		// handle case with no widget
		int childrenCount = getChildrenCount();
		if (childrenCount == 0) {
			size.setSize(0, 0);
			return;
		}

		int widthHint = size.getWidth();
		int heightHint = size.getHeight();

		// compute optimal size of the side widget
		Widget side = this.side;
		int sideOptimalWidth;
		int sideOptimalHeight;
		if (side != null) {
			computeChildOptimalSize(side, Widget.NO_CONSTRAINT, heightHint);
			sideOptimalWidth = side.getWidth();
			sideOptimalHeight = side.getHeight();
		} else {
			sideOptimalWidth = 0;
			sideOptimalHeight = 0;
		}

		// compute the optimal size of the main widget
		int mainOptimalWidth;
		int mainOptimalHeight;
		Widget main = this.main;
		if (main != null) {
			int mainWidthHint = widthHint != Widget.NO_CONSTRAINT ? computeMainSize(widthHint, sideOptimalWidth)
					: widthHint;
			computeChildOptimalSize(main, mainWidthHint, heightHint);
			mainOptimalWidth = main.getWidth();
			mainOptimalHeight = main.getHeight();
		} else {
			mainOptimalWidth = 0;
			mainOptimalHeight = 0;
		}

		// compute the container optimal size
		int width = sideOptimalWidth + mainOptimalWidth;
		int height = Math.max(sideOptimalHeight, mainOptimalHeight);

		// set the container optimal size
		size.setSize(width, height);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		// handle case with no widget
		int childrenCount = getChildrenCount();
		if (childrenCount == 0) {
			return;
		}

		Widget side = this.side;

		// compute the size of the side widget
		int sideWidth = (side != null ? side.getWidth() : 0);
		int mainWidth = computeMainSize(contentWidth, sideWidth);

		// compute the x coordinate of the widgets, depending on the wrist mode
		boolean sideFirst = Configuration.getInstance().getWristMode() == WristMode.RIGHT;
		int sideX;
		int mainX;
		if (sideFirst) {
			sideX = 0;
			mainX = sideWidth;
		} else {
			sideX = mainWidth;
			mainX = 0;
		}

		// lay out the side widget
		if (side != null) {
			layOutChild(side, sideX, 0, sideWidth, contentHeight);
		}

		// lay out the main widget
		Widget main = this.main;
		if (main != null) {
			layOutChild(main, mainX, 0, mainWidth, contentHeight);
		}
	}

	private static int computeMainSize(int totalSize, int firstSize) {
		return Math.max(0, totalSize - firstSize);
	}

}
