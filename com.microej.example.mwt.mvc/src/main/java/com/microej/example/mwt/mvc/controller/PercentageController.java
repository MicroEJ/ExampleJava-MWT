/*
 * Copyright 2009-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc.controller;

import com.microej.example.mwt.mvc.CursorImageRenderPolicy;
import com.microej.example.mwt.mvc.model.PercentageModel;
import com.microej.example.mwt.mvc.view.CompositeView;

import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;

public class PercentageController implements EventHandler {

	private final CompositeView view;
	private final PercentageModel model;

	private static final int MINIMUM_SIZE = 20;
	private static final int TOLERANCE = 10;

	private boolean pressed;
	private int previousX;
	private int previousY;

	/**
	 * Instantiates a {@link PercentageController}.
	 *
	 * @param view
	 *            the view to display the percentage.
	 * @param model
	 *            the model to listen.
	 */
	public PercentageController(CompositeView view, PercentageModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public boolean handleEvent(int event) {
		// get the type of the event
		int type = Event.getType(event);

		if (type == Command.EVENT_TYPE) {
			// get command
			int cmd = Event.getData(event);

			switch (cmd) {
			case Command.UP:
				// command up -> increment model value
				this.model.incrementValue();
				break;
			case Command.DOWN:
				// command down -> decrement model value
				this.model.decrementValue();
				break;
			case Command.LEFT:
				// command left -> page decrement model value
				this.model.pageDecrementValue();
				break;
			case Command.RIGHT:
				// command right -> page increment model value
				this.model.pageIncrementValue();
				break;
			default:
				// other commands -> random model value
				this.model.random();
			}

			// The event has been managed
			return true;
		} else if (type == Pointer.EVENT_TYPE) {
			try {
				Pointer pointer = (Pointer) Event.getGenerator(event);
				receiveMouseEvent(pointer.getX(), pointer.getY(), event);

				// The event has been managed
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.model.random();
		}

		// The event has not been managed
		return false;
	}

	private void receiveMouseEvent(int px, int py, int event) {
		boolean repaintCursor = (px != this.previousX || py != this.previousY);

		if (Buttons.isPressed(event)) {
			// check pointer is over the cross (plus or minus TOLERANCE)
			if (over(px, py, this.view.getXCross(), this.view.getYCross())) {
				this.pressed = true;
			}
		} else if (Buttons.isReleased(event)) {
			this.pressed = false;
		} else if (this.pressed) {
			int width = this.view.getWidth();
			int height = this.view.getHeight();

			// crop to a minimum size
			if (px < MINIMUM_SIZE) {
				px = MINIMUM_SIZE;
			} else if (px > width - MINIMUM_SIZE) {
				px = width - MINIMUM_SIZE;
			}
			if (py < MINIMUM_SIZE) {
				py = MINIMUM_SIZE;
			} else if (py > height - MINIMUM_SIZE) {
				py = height - MINIMUM_SIZE;
			}

			// set cross coordinates
			this.view.setXCross(px);
			this.view.setYCross(py);

			// refresh screen
			this.view.requestLayOut();
			repaintCursor = false;
		}

		if (repaintCursor) {
			int cursorWidth = CursorImageRenderPolicy.CURSOR_WIDTH;
			int cursorHeight = CursorImageRenderPolicy.CURSOR_HEIGHT;
			this.view.requestRender(this.previousX, this.previousY, cursorWidth, cursorHeight);
			this.view.requestRender(px, py, cursorWidth, cursorHeight);
		}

		this.previousX = px;
		this.previousY = py;
	}

	private static boolean over(int px, int py, int x, int y) {
		return (px >= x - TOLERANCE && px <= x + TOLERANCE && py >= y - TOLERANCE && py <= y + TOLERANCE);
	}
}
