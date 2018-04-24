/**
 * Java
 *
 * Copyright 2014-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt;

import com.microej.example.mwt.transition.HorizontalSliderManager;
import com.microej.example.mwt.transition.TransitionManager;
import com.microej.example.mwt.widget.ColoredWidget;
import com.microej.example.mwt.widget.DrawTransitionComposite;
import com.microej.example.mwt.widget.ScreenshotTransitionComposite;
import com.microej.example.mwt.widget.SplitComposite;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Panel;
import ej.mwt.Widget;

/**
 * This example shows how to slide widgets inside a composite. Two implementations are provided. The first
 * implementation is full widget based {@link DrawTransitionComposite}. The second implementation is screenshot based
 * {@link ScreenshotTransitionComposite}.
 * 
 * To illustrate this, the two implementations are shown at the same time.
 */
public class SlidingWidget {

	private static final float SPLIT_RATIO = 0.5f;

	private static class TransitionTask extends TimerTask {

		private boolean forward;
		private final Widget widget1;
		private final Widget widget2;
		private final TransitionManager transitionManager;

		public TransitionTask(Widget widget1, Widget widget2, TransitionManager transitionManager) {
			this.widget1 = widget1;
			this.widget2 = widget2;
			this.transitionManager = transitionManager;
			this.forward = true;
		}

		@Override
		public void run() {
			Widget destination = this.forward ? this.widget2 : this.widget1;
			this.transitionManager.goTo(destination);
			this.forward = !this.forward;
		}
	}

	// Prevents initialization.
	private SlidingWidget() {
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		MicroUI.start();
		// Create the environment.
		Desktop desktop = new Desktop();
		desktop.show();
		Panel panel = new Panel();
		Timer timer = new Timer();

		// Display splitted vertically in two equal parts.
		SplitComposite splitComposite = new SplitComposite();
		splitComposite.setRatio(SPLIT_RATIO);

		// On the top the full widget sliding.
		int widgetId = 1;
		Widget widget1 = new ColoredWidget(widgetId++, Colors.BLUE);
		Widget widget2 = new ColoredWidget(widgetId++, Colors.YELLOW);
		DrawTransitionComposite drawComposite = new DrawTransitionComposite(widget1);
		splitComposite.add(drawComposite);

		// On the bottom the screenshot widget sliding.
		Widget widget3 = new ColoredWidget(widgetId++, Colors.GREEN);
		Widget widget4 = new ColoredWidget(widgetId++, Colors.MAGENTA);
		ScreenshotTransitionComposite screenshotComposite = new ScreenshotTransitionComposite(widget3);
		splitComposite.add(screenshotComposite);

		panel.setWidget(splitComposite);
		panel.showFullScreen(desktop);

		Display display = Display.getDefaultDisplay();
		display.waitForEvent(); // show
		display.waitForEvent(); // validation
		display.waitForEvent(); // paint

		// Transitions started.
		TransitionManager transitionManagerForDraw = new HorizontalSliderManager(drawComposite.getWidth(),
				drawComposite.getHeight(), timer, drawComposite);
		timer.schedule(new TransitionTask(widget1, widget2, transitionManagerForDraw),
				Configuration.NEXT_DESTINATION_PERIOD - Configuration.TRANSITION_DURATION,
				Configuration.NEXT_DESTINATION_PERIOD);

		TransitionManager transitionManagerForScreenshot = new HorizontalSliderManager(screenshotComposite.getWidth(),
				screenshotComposite.getHeight(), timer, screenshotComposite);
		timer.schedule(new TransitionTask(widget3, widget4, transitionManagerForScreenshot),
				Configuration.NEXT_DESTINATION_PERIOD - Configuration.TRANSITION_DURATION,
				Configuration.NEXT_DESTINATION_PERIOD);
	}
}
