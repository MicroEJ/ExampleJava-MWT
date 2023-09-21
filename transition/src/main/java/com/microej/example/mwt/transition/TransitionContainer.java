/*
 * Java
 *
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.transition;

import ej.annotation.Nullable;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 * Makes the new widget appear square by square.
 */
public class TransitionContainer extends Container {

	@Nullable
	private Widget child;

	@Nullable
	private BufferedImage screenshot;
	private boolean animating;

	private TransitionEffect effect;

	/**
	 * Creates a bands transition container.
	 *
	 * @param effect
	 *            the transition effect to apply
	 */
	public TransitionContainer(TransitionEffect effect) {
		this.effect = effect;
	}

	/**
	 * Changes the effect.
	 *
	 * @param effect
	 *            the transition effect to apply
	 */
	public void setEffect(TransitionEffect effect) {
		this.effect = effect;
	}

	/**
	 * Sets the child of this container.
	 * <p>
	 * If there is already a child shown, an animation is performed.
	 *
	 * @param child
	 *            the child
	 */
	public void setMainChild(Widget child) {
		Widget currentChild = this.child;
		this.child = child;
		if (currentChild == null) {
			// First child, no animation to run.
			addChild(child);
		} else {
			// There is already a child, start an animation.

			// Remove the current child and its children. If it (or one of its children) owns an image from
			// the pool, it releases it (see Histogram.onHidden()). And the transition container can then
			// acquire it to do the animation.
			removeChild(currentChild);
			addChild(child);

			// Lay out the child hidden.
			final int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();
			computeChildOptimalSize(child, contentWidth, contentHeight);
			layOutChild(child, 0, 0, contentWidth, contentHeight);

			// Here could be used buffered image pool
			// Get an image and prepare it.
			this.screenshot = new BufferedImage(contentWidth, contentHeight);
			GraphicsContext g = this.screenshot.getGraphicsContext();
			g.reset();

			renderChild(child, g);

			this.effect.start(getDesktop().getAnimator(), contentWidth, contentHeight, this);
			this.animating = true;
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int widthHint = size.getWidth();
		int heightHint = size.getHeight();

		Widget currentMainChild = this.child;
		if (currentMainChild != null) {
			computeChildOptimalSize(currentMainChild, widthHint, heightHint);
		}
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		Widget currentMainChild = this.child;
		if (currentMainChild != null) {
			layOutChild(currentMainChild, 0, 0, contentWidth, contentHeight);
		}
	}

	@Override
	public void render(GraphicsContext g) {
		if (this.animating) {
			// An animation is running.
			// - Do not draw outlines (in particular the background) to keep the current state of the screen.
			g.translate(getContentX(), getContentY());
			int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();
			g.intersectClip(0, 0, contentWidth, contentHeight);
			// - Draw the screenshot of the incoming widget.
			assert this.screenshot != null;
			this.effect.render(g, this.screenshot, contentWidth, contentHeight);
		} else {
			super.render(g);
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		onAnimationStopped();
	}

	@Override
	@Nullable
	public Widget getWidgetAt(int x, int y) {
		// Ignore clicks during the animations.
		if (this.animating) {
			return null;
		}
		return super.getWidgetAt(x, y);
	}

	/**
	 * Stop animation.
	 *
	 */
	public void onAnimationStopped() {
		this.animating = false;
		BufferedImage bufferedScreenshot = this.screenshot;
		if (bufferedScreenshot != null) {
			bufferedScreenshot.close();
			this.screenshot = null;
		}

		Widget currentMainChild = this.child;
		if (currentMainChild != null) {
			setShownChild(currentMainChild);
		}
	}
}
