/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.stashinggrid;

import ej.annotation.Nullable;
import ej.basictool.map.PackedMap;
import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;
import ej.widget.container.Grid;
import ej.widget.container.LayoutOrientation;

/**
 * Lays out any number of children in a grid.
 * <p>
 * All the children are laid out in a grid, which has a fixed number of columns if the grid is horizontal, or a fixed
 * number of rows if the grid is vertical.
 * <p>
 * In a grid, all children have the same width and the same height, regardless of their optimal size.
 * <p>
 * The visibility of each child can be changed independently by calling the method {@link #setVisible(Widget, boolean)}.
 * Or globally by calling the method {@link #setAllVisible(boolean)}.
 * <p>
 * When requested to lay out, the grid only lays out the children that are visible (see
 * {@link #layOutChildren(int, int)} method).
 * <p>
 * When requested to render, the grid only renders the children that are visible (see
 * {@link #renderChild(Widget, GraphicsContext)} method).
 */
public class StashingGrid extends Grid {
	private final PackedMap<Widget, Boolean> widgetsVisibility;

	/**
	 * Creates a grid specifying its orientation and the number of widgets per row/column (depending on the orientation
	 * chosen via the horizontal parameter).
	 *
	 * @param orientation
	 *            the orientation of the grid (see {@link LayoutOrientation}).
	 * @param count
	 *            the number of widgets per row/column to set.
	 * @throws IllegalArgumentException
	 *             if the count is negative or zero.
	 */
	public StashingGrid(boolean orientation, int count) {
		super(orientation, count);
		this.widgetsVisibility = new PackedMap<>();
	}

	@Override
	protected void setShownChildren() {
		for (Widget child : getChildren()) {
			assert child != null;
			if (isVisible(child)) {
				setShownChild(child);
			}
		}
	}

	/**
	 * Sets the visibility of all of the children of the container.
	 * <p>
	 * The container needs to be laid out in order for the modification to be effective.
	 *
	 * @param visible
	 *            the visibility to set.
	 */
	public void setAllVisible(boolean visible) {
		for (Widget child : getChildren()) {
			assert child != null;
			setVisible(child, visible);
		}
	}

	/**
	 * Sets the visibility of the child widget.
	 * <p>
	 * The container needs to be laid out in order for the modification to be effective.
	 *
	 * @param child
	 *            the child.
	 * @param visible
	 *            the visibility to set.
	 */
	public void setVisible(Widget child, boolean visible) {
		if (visible) {
			setShownChild(child);
		} else {
			setHiddenChild(child);
		}
		this.widgetsVisibility.put(child, Boolean.valueOf(visible));
	}

	private boolean isVisible(Widget child) {
		return this.widgetsVisibility.get(child).booleanValue();
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		// handle case with no widget
		int childrenCount = getChildrenCount();
		if (childrenCount == 0) {
			return;
		}

		boolean isHorizontal = (this.getOrientation() == LayoutOrientation.HORIZONTAL);
		int mainContentLength = (isHorizontal ? contentWidth : contentHeight);
		int otherContentLength = (isHorizontal ? contentHeight : contentWidth);

		// compute number of cells on both axis
		int mainCount = this.getCount();
		int otherCount = getOtherCount(childrenCount, mainCount);
		int i = 0;
		for (Widget child : getChildren()) {
			assert (child != null);
			if (isVisible(child)) {

				// compute position on both axis
				int otherPosition = i / mainCount;
				int mainPosition = i - otherPosition * mainCount;

				// compute start and end on main axis
				int mainStart = mainPosition * mainContentLength / mainCount;
				int mainEnd = (mainPosition + 1) * mainContentLength / mainCount;

				// compute start and end on other axis
				int otherStart = otherPosition * otherContentLength / otherCount;
				int otherEnd = (otherPosition + 1) * otherContentLength / otherCount;

				if (isHorizontal) {
					layOutChild(child, mainStart, otherStart, mainEnd - mainStart, otherEnd - otherStart);
				} else {
					layOutChild(child, otherStart, mainStart, otherEnd - otherStart, mainEnd - mainStart);
				}
				i++;
			}
		}
	}

	private static int getOtherCount(int totalCount, int mainCount) {
		return (totalCount + mainCount - 1) / mainCount;
	}

	@Override
	@Nullable
	public Widget getWidgetAt(int x, int y) {
		// equivalent to super.getWidgetAt(x, y) == null
		if (!contains(x, y)) {
			return null;
		}

		int relX = x - this.getX() - this.getContentX();
		int relY = y - this.getY() - this.getContentY();
		// browse children recursively
		Widget[] children = this.getChildren();
		for (int i = children.length - 1; i >= 0; i--) {
			if (this.widgetsVisibility.get(getChild(i)).booleanValue()) {
				Widget at = getChild(i).getWidgetAt(relX, relY);
				if (at != null) {
					return at;
				}
			}
		}
		return this;
	}

	@Override
	public void addChild(Widget child) {
		addChildToMap(child);
		super.addChild(child);
	}

	@Override
	public void removeChild(Widget child) {
		this.widgetsVisibility.remove(child);
		super.removeChild(child);
	}

	@Override
	public void removeAllChildren() {
		this.widgetsVisibility.clear();
		super.removeAllChildren();
	}

	@Override
	public void insertChild(Widget child, int index) {
		addChildToMap(child);
		super.insertChild(child, index);
	}

	@Override
	public void replaceChild(int index, Widget child) {
		this.widgetsVisibility.remove(getChild(index));
		addChild(child);
		super.replaceChild(index, child);
	}

	private void addChildToMap(Widget child) {
		this.widgetsVisibility.put(child, Boolean.valueOf(true));
	}

	@Override
	protected void renderChild(Widget child, GraphicsContext c) {
		if (isVisible(child)) {
			super.renderChild(child, c);
		}
	}

}
