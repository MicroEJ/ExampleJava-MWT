/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.transition;

import ej.mwt.Widget;

/**
 * Allows to draw the transition states with some widgets.
 */
public interface TransitionDrawer {

	/**
	 * Starts the transition to the new widget.
	 * 
	 * @param newWidget
	 *            the new widget.
	 */
	void show(Widget newWidget);

	// /**
	// * Moves both widgets.
	// *
	// * @param x1
	// * the x coordinate of the old widget.
	// * @param y1
	// * the y coordinate of the old widget.
	// * @param width1
	// * the width of the old widget.
	// * @param height1
	// * the height of the old widget.
	// * @param x2
	// * the x coordinate of the new widget.
	// * @param y2
	// * the y coordinate of the new widget.
	// * @param width2
	// * the width of the new widget.
	// * @param height2
	// * the height of the new widget.
	// */
	// void move(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2);

	/**
	 * Moves both widgets.
	 * 
	 * @param oldWidgetArea
	 *            the area of the old widget.
	 * @param newWidgetArea
	 *            the area of the new widget.
	 */
	void move(Area oldWidgetArea, Area newWidgetArea);

	/**
	 * Ends the transition.
	 */
	void stop();

}
