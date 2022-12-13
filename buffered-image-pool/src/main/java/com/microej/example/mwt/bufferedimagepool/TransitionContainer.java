/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.bufferedimagepool;

import ej.annotation.Nullable;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.quart.QuartEaseOutFunction;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;
import ej.widget.util.motion.MotionAnimation;
import ej.widget.util.motion.MotionAnimationListener;

/**
 * Container executing a transition when its content is changed.
 */
public class TransitionContainer extends Container {

	private static final int TRANSITION_DURATION = 500;

	@Nullable
	private BufferedImage screenshot;
	private boolean animating;
	private int position;

	@Override
	protected void computeContentOptimalSize(Size size) {
		if (getChildrenCount() != 0) {
			Widget child = getChild(0);
			int width = size.getWidth();
			int height = size.getHeight();
			assert child != null;
			computeChildOptimalSize(child, width, height);
		}
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		if (getChildrenCount() != 0) {
			Widget child = getChild(0);
			assert child != null;
			layOutChild(child, 0, 0, contentWidth, contentHeight);
		}
	}

	@Override
	public void render(GraphicsContext g) {
		if (this.animating) {
			// An animation is running.
			// - Do not draw outlines (in particular the background) to keep the current state of the screen.
			g.translate(getContentX(), getContentY());
			g.intersectClip(0, 0, getContentWidth(), getContentHeight());
			// - Draw the screenshot of the incoming widget.
			assert this.screenshot != null;
			Painter.drawImage(g, this.screenshot, this.position, 0);
		} else {
			super.render(g);
		}
	}

	/**
	 * Sets the child of this container.
	 * <p>
	 * If there is already a child shown, an animation is performed.
	 *
	 * @param child
	 *            the child.
	 */
	public void setChild(Widget child) {
		if (getChildrenCount() == 0) {
			// First child, no animation to run.
			addChild(child);
		} else {
			// There is already a child, start an animation.

			// Remove the current child and its children. If it (or one of its children) owns an image from
			// the pool, it releases it (see Histogram.onHidden()). And the transition container can then
			// acquire it to do the animation.
			removeAllChildren();
			addChild(child);

			// Lay out the child hidden.
			int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();
			computeChildOptimalSize(child, contentWidth, contentHeight);
			layOutChild(child, 0, 0, contentWidth, contentHeight);

			try {
				// Get an image and prepare it.
				this.screenshot = BufferedImagePool.acquireImage(this);
				GraphicsContext g = this.screenshot.getGraphicsContext();
				g.reset();

				// Apply the background of this container in case the child is transparent.
				getStyle().getBackground().apply(g, contentWidth, contentHeight);

				renderChild(child, g);

				Motion motion = new Motion(QuartEaseOutFunction.INSTANCE, contentWidth, 0, TRANSITION_DURATION);
				new MotionAnimation(getDesktop().getAnimator(), motion, new MotionAnimationListener() {
					@Override
					public void tick(int value, boolean finished) {
						TransitionContainer.this.position = value;
						if (finished) {
							onAnimationStopped();
						}
						requestRender();
					}
				}).start();

				this.animating = true;
			} catch (OutOfImagesException e) {
				// Can't do an animation since there is no image available.
				onAnimationStopped();
				requestRender();
			}
		}
	}

	private void onAnimationStopped() {
		this.animating = false;
		BufferedImage screenshot = this.screenshot;
		if (screenshot != null) {
			BufferedImagePool.releaseImage(this);
			this.screenshot = null;
		}
		setShownChild(getChild(0));
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		onAnimationStopped();
	}

}
