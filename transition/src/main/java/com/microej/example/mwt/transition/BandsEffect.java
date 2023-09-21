/*
 * Java
 *
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.transition;

import ej.bon.Util;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.quint.QuintEaseOutFunction;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;

/**
 * Makes the new widget appear with sliding bands.
 */
public class BandsEffect implements TransitionEffect {

	private static final int TRANSITION_DURATION = 300;
	private static final int BAND_COUNT = 5;

	private final int[] bandsPosition;

	/**
	 * Creates a bands effect.
	 */
	public BandsEffect() {
		this.bandsPosition = new int[BAND_COUNT];
	}

	@Override
	public void start(Animator animator, final int contentWidth, int contentHeight,
			final TransitionContainer container) {
		final Motion motion = new Motion(QuintEaseOutFunction.INSTANCE, contentWidth, 0, TRANSITION_DURATION);
		final long startTime = Util.platformTimeMillis();
		for (int i = 0; i < BAND_COUNT; i++) {
			this.bandsPosition[i] = contentWidth;
		}
		Animation animation = new Animation() {
			@Override
			public boolean tick(long platformTimeMillis) {
				long elapsed = platformTimeMillis - startTime;
				for (int i = 0; i < BAND_COUNT; i++) {
					int previousPosition = BandsEffect.this.bandsPosition[i];
					if (previousPosition == 0 || previousPosition == -contentWidth) {
						// Put the band out of the screen to avoid drawing the same thing again.
						BandsEffect.this.bandsPosition[i] = -contentWidth;
					} else {
						BandsEffect.this.bandsPosition[i] = motion.getValue(elapsed);
					}
					elapsed -= TRANSITION_DURATION / 2;
				}
				if (elapsed <= TRANSITION_DURATION) {
					container.requestRender();
					return true;
				} else {
					container.onAnimationStopped();
					container.requestLayOut();
					return false;
				}
			}
		};
		animator.startAnimation(animation);
	}

	@Override
	public void render(GraphicsContext g, BufferedImage screenshot, int contentWidth, int contentHeight) {
		float bandHeight = (float) contentHeight / BAND_COUNT;
		float bandY = 0;
		for (int i = 0; i < BAND_COUNT; i++) {
			int intBandY = (int) bandY;
			bandY += bandHeight;
			int intBandHeight = (int) bandY - intBandY;
			Painter.drawImageRegion(g, screenshot, 0, intBandY, contentWidth, intBandHeight, this.bandsPosition[i],
					intBandY);
		}
	}

}
