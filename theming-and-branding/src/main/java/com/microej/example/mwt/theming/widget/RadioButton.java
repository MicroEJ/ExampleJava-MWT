/*
 * Copyright 2020-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming.widget;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.OnClickListener;

/**
 * A radio button is a widget which displays a text and a box that can be checked or unchecked.
 */
public class RadioButton extends Widget {
	/** The extra field ID for the color of the radio button when it is checked. */
	public static final int STYLE_CHECKED_COLOR = 0;
	/** The extra field ID for the circle color. */
	public static final int STYLE_CIRCLE_COLOR = 1;
	/** The extra field ID for the Y offset. */
	public static final int STYLE_Y_OFFSET = 2;
	/** The extra field ID to force the height. */
	public static final int STYLE_FORCE_HEIGHT = 3;

	private static final int BOX_SIZE_OFFSET = 4;
	private static final int DEFAULT_Y_OFFSET = 0;
	private static final int N_4 = 4;
	private static final double N_1_6 = 1.6;

	private final String text;
	private final RadioButtonGroup group;
	@Nullable
	private OnClickListener listener;

	/**
	 * Creates a radio button with the given text to display.
	 *
	 * @param text
	 *            the text to display.
	 * @param group
	 *            the group to which the radio button should belong.
	 */
	public RadioButton(String text, RadioButtonGroup group) {
		super(true);
		this.text = text;
		this.group = group;
	}

	/**
	 * Sets the on click listener for the radio's.
	 *
	 * @param onClickListener
	 *            the listener
	 */
	public void setOnClickListener(OnClickListener onClickListener) {
		this.listener = onClickListener;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int yOffset = style.getExtraInt(STYLE_Y_OFFSET, DEFAULT_Y_OFFSET);
		Font font = style.getFont();
		int horizontalAlignment = style.getHorizontalAlignment();
		int verticalAlignment = style.getVerticalAlignment();

		// draw box
		int boxSize = computeBoxSize(font) - N_4;
		int boxX = Alignment.computeLeftX(computeWidth(this.text, font), 0, contentWidth, horizontalAlignment);
		int boxY = Alignment.computeTopY(boxSize + N_4, 0, contentHeight, verticalAlignment);
		g.setColor(getCircleColor(style));
		ShapePainter.drawThickFadedPoint(g, boxX + boxSize / 2, (boxY + boxSize / 2) + yOffset, boxSize, 1);

		// fill box
		if (this.group.isChecked(this)) {
			g.setColor(getCheckedColor(style));
			ShapePainter.drawThickFadedPoint(g, (int) XMath.floor(boxX + boxSize / 2),
					((int) XMath.floor(boxY + boxSize / 2)) + yOffset, (int) XMath.floor(boxSize / N_1_6), 1);
		}

		// draw text
		int textX = boxX + boxSize + computeSpacing(font);
		int textY = Alignment.computeTopY(font.getHeight(), 0, contentHeight, verticalAlignment) + yOffset;
		g.setColor(style.getColor());
		Painter.drawString(g, this.text, font, textX, textY);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Font font = getStyle().getFont();
		int forceHeight = getStyle().getExtraInt(STYLE_FORCE_HEIGHT, 0);
		size.setSize(computeWidth(this.text, font), forceHeight > 0 ? forceHeight : font.getHeight());
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			int action = Buttons.getAction(event);
			if (action == Buttons.RELEASED) {
				this.group.setChecked(this);
				if (this.listener != null) {
					this.listener.onClick();
				}
				return true;
			}
		}

		return super.handleEvent(event);
	}

	private static int computeWidth(String text, Font font) {
		return computeBoxSize(font) + computeSpacing(font) + font.stringWidth(text);
	}

	private static int computeBoxSize(Font font) {
		return (font.getHeight() - BOX_SIZE_OFFSET);
	}

	private static int computeSpacing(Font font) {
		return font.getHeight() / 2 + 1;
	}

	private static int getCheckedColor(Style style) {
		return style.getExtraInt(STYLE_CHECKED_COLOR, style.getColor());
	}

	private static int getCircleColor(Style style) {
		return style.getExtraInt(STYLE_CIRCLE_COLOR, style.getColor());
	}
}
