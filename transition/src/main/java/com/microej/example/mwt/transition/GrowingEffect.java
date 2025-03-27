/*
 * Java
 *
 * Copyright 2023-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.transition;

import ej.drawing.TransformPainter;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseInOutFunction;
import ej.mwt.animation.Animator;
import ej.widget.motion.MotionAnimation;
import ej.widget.motion.MotionAnimationListener;

/**
 * Makes the new widget appear by growing from the center.
 */
public class GrowingEffect implements TransitionEffect {

	private static final int MAX = 1000;
	private static final int TRANSITION_DURATION = 500;

	private final boolean fade;
	private int step;

	/**
	 * Creates a growing effect.
	 *
	 * @param fade
	 *            <code>true</code> to add a fade effect, <code>false</code> otherwise
	 */
	public GrowingEffect(boolean fade) {
		this.fade = fade;
	}

	@Override
	public void start(Animator animator, int contentWidth, int contentHeight, final TransitionContainer container) {
		final Motion motion = new Motion(QuadEaseInOutFunction.INSTANCE, 0, MAX, TRANSITION_DURATION);
		new MotionAnimation(animator, motion, new MotionAnimationListener() {
			@Override
			public void tick(int value, boolean finished) {
				GrowingEffect.this.step = value;
				if (!finished) {
					container.requestRender();
				} else {
					container.onAnimationStopped();
					container.requestLayOut();
				}
			}
		}).start();
	}

	@Override
	public void render(GraphicsContext g, BufferedImage screenshot, int contentWidth, int contentHeight) {
		float factor = (float) this.step / MAX;
		int width = (int) (contentWidth * factor);
		int height = (int) (contentHeight * factor);
		int x = (contentWidth - width) / 2;
		int y = (contentHeight - height) / 2;

		int alpha;
		if (this.fade) {
			alpha = GraphicsContext.OPAQUE * this.step / MAX;
		} else {
			alpha = GraphicsContext.OPAQUE;
		}

		g.setClip(x, y, width, height);
		TransformPainter.drawScaledImageNearestNeighbor(g, screenshot, x, y, factor, factor, alpha);
	}

}
