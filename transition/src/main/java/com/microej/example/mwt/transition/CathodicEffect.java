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
 * Makes the new widget appear by growing its visible zone starting by the center.
 */
public class CathodicEffect implements TransitionEffect {

	private static final int TRANSITION_DURATION = 800;
	private static final int REMAINING_HEIGHT = 20;

	private int animationStep;
	private int previousAnimationStep;

	@Override
	public void start(Animator animator, int contentWidth, int contentHeight, final TransitionContainer container) {
		final Motion motion = new Motion(QuadEaseInOutFunction.INSTANCE, 0, (contentWidth + contentHeight) / 2,
				TRANSITION_DURATION);
		new MotionAnimation(animator, motion, new MotionAnimationListener() {
			@Override
			public void tick(int value, boolean finished) {
				CathodicEffect.this.animationStep = value;
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
		int animationStep = this.animationStep;
		int previousAnimationStep = this.previousAnimationStep;
		int middleY = (contentHeight - REMAINING_HEIGHT) / 2;
		if (animationStep < middleY) {
			int animationHeight = animationStep - previousAnimationStep;
			Painter.drawImageRegion(g, screenshot, 0, previousAnimationStep, contentWidth, animationHeight, 0,
					previousAnimationStep);
			Painter.drawImageRegion(g, screenshot, 0, contentHeight - animationStep, contentWidth, animationHeight, 0,
					contentHeight - animationStep);
		} else {
			if (previousAnimationStep < middleY) {
				int animationHeight = middleY - previousAnimationStep;
				Painter.drawImageRegion(g, screenshot, 0, previousAnimationStep, contentWidth, animationHeight, 0,
						previousAnimationStep);
				Painter.drawImageRegion(g, screenshot, 0, contentHeight - middleY, contentWidth, animationHeight, 0,
						contentHeight - middleY);
			}
			int previousAnimationX = Math.max(previousAnimationStep - middleY, 0);
			int animationX = animationStep - middleY;
			int animationWidth = animationX - previousAnimationX;
			Painter.drawImageRegion(g, screenshot, previousAnimationX, middleY, animationWidth, REMAINING_HEIGHT,
					previousAnimationX, middleY);
			Painter.drawImageRegion(g, screenshot, contentWidth - animationX, middleY, animationWidth, REMAINING_HEIGHT,
					contentWidth - animationX, middleY);
		}
		this.previousAnimationStep = animationStep;
	}

}
