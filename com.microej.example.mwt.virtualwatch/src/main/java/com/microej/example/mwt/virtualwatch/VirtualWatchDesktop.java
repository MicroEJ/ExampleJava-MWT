/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.virtualwatch;

import ej.annotation.Nullable;
import ej.microui.event.Event;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Command;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.event.EventDispatcher;
import ej.mwt.event.PointerEventDispatcher;
import ej.mwt.render.DefaultRenderPolicy;
import ej.mwt.render.RenderPolicy;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;

/**
 * Represents the virtual watch: its skin and its buttons.
 */
public class VirtualWatchDesktop extends Desktop {

	private static final String WATCHFACE = "/images/watchface.png"; //$NON-NLS-1$
	private static final int SCREEN_X = 136;
	private static final int SCREEN_Y = 35;
	private static final int SCREEN_WIDTH = 200;
	private static final int SCREEN_HEIGHT = 200;
	private static final String RIGHT_BUTTON = "/images/right-button"; //$NON-NLS-1$
	private static final int RIGHT_BUTTON_X = 343;
	private static final int RIGHT_BUTTON_Y = 115;
	private static final int RIGHT_BUTTON_WIDTH = 14;
	private static final int RIGHT_BUTTON_HEIGHT = 40;
	private static final String RIGHT_TOP_BUTTON = "/images/right-top-button"; //$NON-NLS-1$
	private static final int RIGHT_TOP_BUTTON_X = 314;
	private static final int RIGHT_TOP_BUTTON_Y = 55;
	private static final int RIGHT_TOP_BUTTON_WIDTH = 30;
	private static final int RIGHT_TOP_BUTTON_HEIGHT = 36;
	private static final String RIGHT_BOTTOM_BUTTON = "/images/right-bottom-button"; //$NON-NLS-1$
	private static final int RIGHT_BOTTOM_BUTTON_X = 314;
	private static final int RIGHT_BOTTOM_BUTTON_Y = 179;
	private static final int RIGHT_BOTTOM_BUTTON_WIDTH = 30;
	private static final int RIGHT_BOTTOM_BUTTON_HEIGHT = 36;

	private final VirtualWatchContainer watchContainer;

	/**
	 * Creates a virtual watch.
	 */
	public VirtualWatchDesktop() {
		this.watchContainer = new VirtualWatchContainer(WATCHFACE, SCREEN_X, SCREEN_Y, SCREEN_WIDTH, SCREEN_HEIGHT);

		Command command = getCommand();

		VirtualButton rightButton = new VirtualButton(RIGHT_BUTTON, command, Command.SELECT);
		this.watchContainer.addChild(rightButton, RIGHT_BUTTON_X, RIGHT_BUTTON_Y, RIGHT_BUTTON_WIDTH,
				RIGHT_BUTTON_HEIGHT);

		VirtualButton rightTopButton = new VirtualButton(RIGHT_TOP_BUTTON, command, Command.UP);
		this.watchContainer.addChild(rightTopButton, RIGHT_TOP_BUTTON_X, RIGHT_TOP_BUTTON_Y, RIGHT_TOP_BUTTON_WIDTH,
				RIGHT_TOP_BUTTON_HEIGHT);

		VirtualButton rightBottomButton = new VirtualButton(RIGHT_BOTTOM_BUTTON, command, Command.DOWN);
		this.watchContainer.addChild(rightBottomButton, RIGHT_BOTTOM_BUTTON_X, RIGHT_BOTTOM_BUTTON_Y,
				RIGHT_BOTTOM_BUTTON_WIDTH, RIGHT_BOTTOM_BUTTON_HEIGHT);

		super.setWidget(this.watchContainer);
	}

	@Override
	public void setWidget(@Nullable Widget widget) {
		this.watchContainer.setContent(widget);
	}

	/**
	 * Sets the stylesheet instance.
	 *
	 * @param stylesheet
	 *            the stylesheet instance.
	 */
	public void setStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(VirtualButton.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		super.setStylesheet(stylesheet);
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

	@Override
	protected EventDispatcher createEventDispatcher() {
		return new PointerEventDispatcher(this) {
			@Override
			public boolean dispatchEvent(int event) {
				// When a command is received, dispatch it to the screen content.
				if (Event.getType(event) == Command.EVENT_TYPE) {
					Widget content = VirtualWatchDesktop.this.watchContainer.getContent();
					if (content != null) {
						content.handleEvent(event);
					}
				}
				return super.dispatchEvent(event);
			}
		};
	}

	private static Command getCommand() {
		Command command = EventGenerator.get(Command.class, 0);
		if (command == null) {
			command = new Command();
			command.addToSystemPool();
		}
		return command;
	}

}
