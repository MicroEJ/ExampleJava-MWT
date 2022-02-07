/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.focus;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Size;

/**
 * The joystick displays 5 buttons: UP, DOWN, LEFT, RIGHT, SELECT. When one of these buttons is pressed, the matching
 * command is sent to the application (Respectively {@link Command#UP}, {@link Command#DOWN}, {@link Command#LEFT},
 * {@link Command#RIGHT} and {@link Command#SELECT}).
 */
public class Joystick extends Widget {

	private static final int OPTIMAL_SIZE = 50;
	private static final float QUARTER = 0.25f;
	private static final float HALF = 0.5f;
	private static final float THREE_QUARTER = 0.75f;

	private final Command command;

	/**
	 * Creates a joystick.
	 */
	public Joystick() {
		super(true);
		Command command = EventGenerator.get(Command.class, 0);
		if (command == null) {
			command = new Command();
			command.addToSystemPool();
		}
		this.command = command;
	}

	@Override
	protected void computeContentOptimalSize(Size availableSize) {
		availableSize.setSize(OPTIMAL_SIZE, OPTIMAL_SIZE);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getColor());

		int quarterWidth = (int) (contentWidth * QUARTER);
		int quarterHeight = (int) (contentHeight * QUARTER);
		int halfWidth = (int) (contentWidth * HALF);
		int halfHeight = (int) (contentHeight * HALF);
		int threeQuarterWidth = (int) (contentWidth * THREE_QUARTER);
		int threeQuarterHeight = (int) (contentHeight * THREE_QUARTER);

		Painter.drawLine(g, 0, 0, quarterWidth, quarterHeight);
		Painter.drawLine(g, threeQuarterWidth, threeQuarterHeight, contentWidth, contentHeight);
		Painter.drawLine(g, contentWidth, 0, threeQuarterWidth, quarterHeight);
		Painter.drawLine(g, quarterWidth, threeQuarterHeight, 0, contentHeight);
		Painter.drawRectangle(g, quarterWidth, quarterHeight, halfWidth, halfHeight);
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Pointer.EVENT_TYPE && Buttons.isReleased(event)) {
			Pointer pointer = (Pointer) Event.getGenerator(event);

			float shiftRatioX = getShiftRatio(pointer.getX(), getAbsoluteX(), getWidth());
			float shiftRatioY = getShiftRatio(pointer.getY(), getAbsoluteY(), getHeight());

			if (Math.abs(shiftRatioX) < HALF && Math.abs(shiftRatioY) < HALF) {
				this.command.send(Command.SELECT);
			} else if (shiftRatioX > HALF && shiftRatioX > shiftRatioY && shiftRatioX > -shiftRatioY) {
				this.command.send(Command.LEFT);
			} else if (-shiftRatioX > HALF && -shiftRatioX > shiftRatioY && -shiftRatioX > -shiftRatioY) {
				this.command.send(Command.RIGHT);
			} else if (shiftRatioY > HALF && shiftRatioY > shiftRatioX && shiftRatioY > -shiftRatioX) {
				this.command.send(Command.UP);
			} else if (-shiftRatioY > HALF && -shiftRatioY > shiftRatioX && -shiftRatioY > -shiftRatioX) {
				this.command.send(Command.DOWN);
			}
		}
		return super.handleEvent(event);
	}

	private float getShiftRatio(int pointerCoordinate, int absoluteCoordinate, int size) {
		int relativeCoordinate = pointerCoordinate - absoluteCoordinate;
		int centerCoordinate = size / 2;
		return (float) (centerCoordinate - relativeCoordinate) / centerCoordinate;
	}

}
