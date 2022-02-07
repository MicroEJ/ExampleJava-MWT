/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.virtualwatch;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.ResourceImage;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.mwt.style.Style;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.util.render.ImagePainter;

/**
 * A virtual button is a widget that displays an image and reacts to click events.
 * <p>
 * The image changes when the button is pressed.
 */
public class VirtualButton extends Widget {

	private static final String EXTENSION = ".png"; //$NON-NLS-1$
	private static final String PRESSED_SUFFIX = "-pressed"; //$NON-NLS-1$

	private static final int ALPHA_SHIFT = 24;

	private final String imagePath;
	private final Command commandGenerator;
	private final int command;

	private @Nullable Image image;

	/**
	 * Creates a virtual button.
	 *
	 * @param imagePath
	 *            the path to the image without extension.
	 * @param commandGenerator
	 *            the command generator.
	 * @param command
	 *            the command to send when clicked.
	 */
	public VirtualButton(String imagePath, Command commandGenerator, int command) {
		super(true);
		this.commandGenerator = commandGenerator;
		this.command = command;
		checkImage(imagePath + EXTENSION);
		checkImage(imagePath + PRESSED_SUFFIX + EXTENSION);
		this.imagePath = imagePath;
	}

	@Override
	protected void onAttached() {
		super.onAttached();
		this.image = Image.getImage(this.imagePath + EXTENSION);
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		this.image = null;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Image image = this.image;
		if (image != null) {
			Style style = getStyle();
			g.setColor(style.getColor());
			ImagePainter.drawImageInArea(g, image, 0, 0, contentWidth, contentHeight, style.getHorizontalAlignment(),
					style.getVerticalAlignment());
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Image image = this.image;
		if (image != null) {
			ImagePainter.computeOptimalSize(image, size);
		} else {
			size.setSize(0, 0);
		}
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			int action = Buttons.getAction(event);
			if (action == Buttons.PRESSED) {
				setPressed(true);
			} else if (action == Buttons.RELEASED) {
				setPressed(false);
				handleClick();
				return true;
			}
		} else if (type == DesktopEventGenerator.EVENT_TYPE) {
			int action = DesktopEventGenerator.getAction(event);
			if (action == PointerEventDispatcher.EXITED) {
				setPressed(false);
			}
		}

		return super.handleEvent(event);
	}

	private void setPressed(boolean pressed) {
		if (pressed) {
			this.image = Image.getImage(this.imagePath + PRESSED_SUFFIX + EXTENSION);
		} else {
			this.image = Image.getImage(this.imagePath + EXTENSION);
		}
		requestRender();
	}

	@Override
	public boolean contains(int x, int y) {
		// Do not react to the transparent part of the button that is in the virtual display.
		Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
		Style style = getStyle();
		style.getMargin().apply(bounds);

		int boundsX = bounds.getX();
		int boundsY = bounds.getY();
		if (x >= boundsX && x < boundsX + bounds.getWidth() && y >= boundsY && y < boundsY + bounds.getHeight()) {
			int imgX = x - boundsX;
			int imgY = y - boundsY;
			Image image = this.image;
			assert image != null;
			int readPixel = image.readPixel(imgX, imgY);
			return (readPixel >>> ALPHA_SHIFT) == 0xff; // 0xff = fully opaque.
		}
		return false;
	}

	/**
	 * Handles a click event.
	 */
	public void handleClick() {
		this.commandGenerator.send(this.command);
	}

	private static void checkImage(String imagePath) {
		if (!ResourceImage.canLoadImage(imagePath)) {
			throw new IllegalArgumentException();
		}
	}

}
