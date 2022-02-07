/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.contextsensitive.virtual;

import com.microej.example.mwt.contextsensitive.configuration.Configuration;
import com.microej.example.mwt.contextsensitive.configuration.WristMode;

import ej.annotation.Nullable;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.render.DefaultRenderPolicy;
import ej.mwt.render.RenderPolicy;
import ej.widget.basic.Button;
import ej.widget.basic.OnClickListener;

/**
 * Represents the virtual watch. It also displays a button to flip the watch.
 */
public class VirtualWatchDesktop extends Desktop {

	private static final String WATCHFACE = "/images/watchface.png"; //$NON-NLS-1$
	private static final int SCREEN_X = 140;
	private static final int SCREEN_Y = 35;
	private static final int SCREEN_WIDTH = 200;
	private static final int SCREEN_HEIGHT = 200;
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 50;

	private final VirtualWatchContainer watchContainer;

	/**
	 * Creates a virtual watch.
	 */
	public VirtualWatchDesktop() {
		this.watchContainer = new VirtualWatchContainer(WATCHFACE, SCREEN_X, SCREEN_Y, SCREEN_WIDTH, SCREEN_HEIGHT);

		Button flipButton = new Button("Flip watch"); //$NON-NLS-1$
		flipButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				VirtualWatchDesktop.this.watchContainer.flipImage();

				Configuration configuration = Configuration.getInstance();
				WristMode wristMode = configuration.getWristMode();
				configuration.setWristMode(wristMode == WristMode.LEFT ? WristMode.RIGHT : WristMode.LEFT);
			}
		});
		Display display = Display.getDisplay();
		this.watchContainer.addChild(flipButton, 0, display.getHeight() - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

		super.setWidget(this.watchContainer);
	}

	@Override
	public void setWidget(@Nullable Widget widget) {
		this.watchContainer.setContent(widget);
	}

	@Override
	protected RenderPolicy createRenderPolicy() {
		return new DefaultRenderPolicy(this) {
			@Override
			public void requestRender(Widget widget, int x, int y, int width, int height) {
				// When a widget is asked to be repaint, repaint the watch container in order to draw the rim of
				// the watch and the possible virtual buttons intersecting the widget.
				int absoluteX = widget.getAbsoluteX() + x;
				int absoluteY = widget.getAbsoluteY() + y;
				super.requestRender(VirtualWatchDesktop.this.watchContainer, absoluteX, absoluteY, width, height);
			}
		};
	}

}
