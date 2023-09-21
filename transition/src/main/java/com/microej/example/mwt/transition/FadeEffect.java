/*
 * Java
 *
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.transition;

import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseInOutFunction;
import ej.mwt.animation.Animator;
import ej.widget.util.motion.MotionAnimation;
import ej.widget.util.motion.MotionAnimationListener;

/**
 * Make the new widget appear with a fade in.
 */
public class FadeEffect implements TransitionEffect {

	private static final int TRANSITION_DURATION = 500;

	private int alpha;

	@Override
	public void start(Animator animator, int contentWidth, int contentHeight, final TransitionContainer container) {
		final Motion motion = new Motion(QuadEaseInOutFunction.INSTANCE, GraphicsContext.TRANSPARENT,
				GraphicsContext.OPAQUE, TRANSITION_DURATION);
		new MotionAnimation(animator, motion, new MotionAnimationListener() {
			@Override
			public void tick(int value, boolean finished) {
				FadeEffect.this.alpha = value;
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
		Painter.drawImage(g, screenshot, 0, 0, this.alpha);
	}
}
