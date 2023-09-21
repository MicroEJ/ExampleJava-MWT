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
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;

/**
 * Container executing a transition when its content is changed.
 */
public class RandomPavingEffect implements TransitionEffect {

	private static final int CUTS = 12;

	private boolean[] filled;
	private int currentTileH;
	private int currentTileV;
	private int step;

	/**
	 * Creates a random paving effect.
	 */
	public RandomPavingEffect() {
		this.filled = new boolean[CUTS * CUTS];
	}

	@Override
	public void start(Animator animator, int contentWidth, int contentHeight, final TransitionContainer container) {
		this.filled = new boolean[CUTS * CUTS];
		this.step = 0;
		container.requestRender();
		Animation animation = new Animation() {
			@Override
			public boolean tick(long platformTimeMillis) {
				boolean finished = step();
				if (!finished) {
					container.requestRender();
				} else {
					container.onAnimationStopped();
					container.requestLayOut();
				}
				return !finished;
			}
		};
		animator.startAnimation(animation);
	}

	private boolean step() {
		if (++this.step > CUTS * CUTS) {
			return true;
		}
		int tileIndex;
		do {
			tileIndex = (int) (Math.random() * CUTS * CUTS);
		} while (this.filled[tileIndex]);
		this.filled[tileIndex] = true;
		this.currentTileH = tileIndex / CUTS;
		this.currentTileV = tileIndex - (this.currentTileH * CUTS);
		return false;
	}

	@Override
	public void render(GraphicsContext g, BufferedImage screenshot, int contentWidth, int contentHeight) {
		int tileWidth = contentWidth / CUTS;
		int tileHeight = contentHeight / CUTS;
		int tileX = this.currentTileH * tileWidth;
		int tileY = this.currentTileV * tileHeight;

		Painter.drawImageRegion(g, screenshot, tileX, tileY, tileWidth, tileHeight, tileX, tileY);
	}

}
