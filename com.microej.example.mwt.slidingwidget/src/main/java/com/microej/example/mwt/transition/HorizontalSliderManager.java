/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.transition;

import com.microej.example.mwt.Configuration;

import ej.bon.Timer;
import ej.motion.Motion;
import ej.motion.bounce.BounceEaseInMotion;
import ej.motion.quad.QuadEaseOutMotion;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.Widget;

/**
 * Implementation of {@link TransitionManager} that slides the widgets horizontally.
 */
public class HorizontalSliderManager implements TransitionManager {

	private final int width;
	private final int height;
	private final Timer timer;
	private final TransitionDrawer transitionDrawer;

	/**
	 * Creates an HorizontalSliderManager.
	 * 
	 * @param width
	 *            the available width to perform the transition.
	 * @param height
	 *            the available height to perform the transition.
	 * @param timer
	 *            the timer to use to perform the transition.
	 * @param transitionDrawer
	 *            the drawer to use to draw the steps of the transition.
	 */
	public HorizontalSliderManager(int width, int height, Timer timer, TransitionDrawer transitionDrawer) {
		this.width = width;
		this.height = height;
		this.timer = timer;
		this.transitionDrawer = transitionDrawer;
	}

	@Override
	public void goTo(Widget widget) {
		int start = this.width;
		int stop = 0;
		MotionListener listener = new MotionListenerSlide(this.width, this.height, widget, this.transitionDrawer, true);
		Motion motion = new QuadEaseOutMotion(start, stop, Configuration.TRANSITION_DURATION);
		MotionAnimator motionAnimator = new MotionAnimator(motion, listener);
		motionAnimator.start(this.timer, Configuration.TRANSITION_PERIOD);
	}

	private static final class MotionListenerSlide implements MotionListener {
		private final int desktopWidth;
		private final int desktopHeight;
		private final Widget widget;
		private final TransitionDrawer transitionDrawer;
		private final boolean forward;

		private MotionListenerSlide(int desktopWidth, int desktopHeight, Widget widget,
				TransitionDrawer transitionDrawer, boolean forward) {
			this.desktopWidth = desktopWidth;
			this.desktopHeight = desktopHeight;
			this.widget = widget;
			this.transitionDrawer = transitionDrawer;
			this.forward = forward;
		}

		@Override
		public void start(int value) {
			this.transitionDrawer.show(this.widget);
		}

		@Override
		public void step(int value) {
			int x1;
			int x2;
			if (this.forward) {
				x1 = value - this.desktopWidth;
				x2 = value;
			} else {
				x1 = value;
				x2 = value - this.desktopWidth;
			}
			this.transitionDrawer.move(new Area(x1, 0, this.desktopWidth, this.desktopHeight), new Area(x2, 0,
					this.desktopWidth, this.desktopHeight));
		}

		@Override
		public void stop(int value) {
			this.transitionDrawer.stop();
		}

		@Override
		public void cancel() {
			this.transitionDrawer.stop();
		}
	}
}
