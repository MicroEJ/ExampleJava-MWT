/*
 * Java
 *
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.transition;

import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.mwt.animation.Animator;

/**
 * A transition effect example.
 */
public interface TransitionEffect {

	/**
	 * Starts the transition.
	 *
	 * @param animator
	 *            the animator to run on
	 * @param contentWidth
	 *            the content width
	 * @param contentHeight
	 *            the content height
	 * @param container
	 *            the transition container
	 */
	public void start(Animator animator, int contentWidth, int contentHeight, final TransitionContainer container);

	/**
	 * Renders the transition.
	 *
	 * @param g
	 *            the graphics context to draw on
	 * @param screenshot
	 *            the screenshot to draw
	 * @param contentWidth
	 *            the content width
	 * @param contentHeight
	 *            the content height
	 */
	public void render(GraphicsContext g, BufferedImage screenshot, int contentWidth, int contentHeight);

}
