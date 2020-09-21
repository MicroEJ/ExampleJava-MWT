/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.focus;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.EventGenerator;
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

	private final Command command;

	/**
	 * Creates a joystick.
	 */
	public Joystick() {
		Command command = EventGenerator.get(Command.class, 0);
		if (command == null) {
			command = new Command();
			command.addToSystemPool();
		}
		this.command = command;
		setEnabled(true);
	}

	@Override
	protected void computeContentOptimalSize(Size availableSize) {
		availableSize.setSize(50, 50);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getColor());
		Painter.drawLine(g, 0, 0, contentWidth / 4, contentHeight / 4);
		Painter.drawLine(g, 3 * contentWidth / 4, 3 * contentHeight / 4, contentWidth, contentHeight);
		Painter.drawLine(g, contentWidth, 0, 3 * contentWidth / 4, contentHeight / 4);
		Painter.drawLine(g, contentWidth / 4, 3 * contentHeight / 4, 0, contentHeight);
		Painter.drawRectangle(g, contentWidth / 4, contentHeight / 4, contentWidth / 2, contentHeight / 2);
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Pointer.EVENT_TYPE && Pointer.isReleased(event)) {
			Pointer pointer = (Pointer) Event.getGenerator(event);

			int relativeX = pointer.getX() - getAbsoluteX();
			int relativeY = pointer.getY() - getAbsoluteY();

			int width = this.getWidth();
			int height = this.getHeight();

			int centerX = width / 2;
			int centerY = height / 2;

			float shiftRatioX = (float) (centerX - relativeX) / centerX;
			float shiftRatioY = (float) (centerY - relativeY) / centerY;

			if (Math.abs(shiftRatioX) < 0.5f && Math.abs(shiftRatioY) < 0.5f) {
				this.command.send(Command.SELECT);
			} else if (shiftRatioX > 0.5f && shiftRatioX > shiftRatioY && shiftRatioX > -shiftRatioY) {
				this.command.send(Command.LEFT);
			} else if (-shiftRatioX > 0.5f && -shiftRatioX > shiftRatioY && -shiftRatioX > -shiftRatioY) {
				this.command.send(Command.RIGHT);
			} else if (shiftRatioY > 0.5f && shiftRatioY > shiftRatioX && shiftRatioY > -shiftRatioX) {
				this.command.send(Command.UP);
			} else if (-shiftRatioY > 0.5f && -shiftRatioY > shiftRatioX && -shiftRatioY > -shiftRatioX) {
				this.command.send(Command.DOWN);
			}
		}
		return super.handleEvent(event);
	}

}
