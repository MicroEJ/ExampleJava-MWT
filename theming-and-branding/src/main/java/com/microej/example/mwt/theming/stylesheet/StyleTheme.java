/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming.stylesheet;

import com.microej.example.mwt.theming.ClassSelectors;
import com.microej.example.mwt.theming.theme.Theme;
import com.microej.example.mwt.theming.widget.RadioButton;

import ej.microui.display.Font;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;

/**
 * StyleTheme used to set general layout of the application.
 */
public abstract class StyleTheme {
	private static final int RADIO_INNER_CIRCLE_COLOR = 0xD9D9D9;
	private static final int BACKGROUND_COLOR = 0x262a2c;
	private static final int TEXT_COLOR = 0xffffff;
	/**
	 * Highlight color.
	 */
	protected static final int HIGHLIGHT_COLOR = 0xee502e;

	/**
	 * Gets the theme name.
	 *
	 * @return the name of the theme.
	 */
	public abstract String getName();

	/**
	 * Builds the theme stylesheet.
	 *
	 * @param theme
	 *            the theme.
	 * @return the new stylesheet.
	 */
	public CascadingStylesheet extendStylesheet(Theme theme) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(TEXT_COLOR);
		style.setFont(Font.getFont("/fonts/barlow-Cond_18pt_400.ejf")); //$NON-NLS-1$ s
		style.setBackground(NoBackground.NO_BACKGROUND);

		// Header
		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.HEADER));
		style.setFont(Font.getFont("/fonts/SourceSansPro_22px-400.ejf")); //$NON-NLS-1$
		style.setMargin(new FlexibleOutline(0, 10, 0, 10));
		style.setPadding(new FlexibleOutline(0, 0, 0, 14));
		style.setBorder(new FlexibleRectangularBorder(HIGHLIGHT_COLOR, 0, 0, 2, 0));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setPadding(new FlexibleOutline(5, 10, 5, 10));

		// Radio Container
		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.RADIO_GROUP_CONTAINER));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setMargin(new UniformOutline(10));

		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.RADIO_GROUP));
		style.setMargin(new FlexibleOutline(0, 0, 0, 5));

		// Radio Button
		style = stylesheet.getSelectorStyle(new TypeSelector(RadioButton.class));
		style.setColor(TEXT_COLOR);
		style.setExtraInt(RadioButton.STYLE_CHECKED_COLOR, HIGHLIGHT_COLOR);
		style.setExtraInt(RadioButton.STYLE_CIRCLE_COLOR, RADIO_INNER_CIRCLE_COLOR);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setMargin(new FlexibleOutline(1, 0, 1, 0));

		// Main background
		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.BACKGROUND));
		style.setBackground(new RectangularBackground(getBackgroundColor()));

		// Dialog
		boolean condensed = theme.isCondensed();

		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.DIALOG_FRAME));
		style.setFont(theme.getFont()); // inherited in all widgets inside the dialog
		style.setVerticalAlignment(Alignment.TOP);
		style.setHorizontalAlignment(Alignment.RIGHT);
		style.setMargin(new FlexibleOutline(19, 12, 12, 0));

		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.DIALOG_TITLE));
		style.setFont(theme.getFont());
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);
		if (condensed) {
			style.setPadding(new FlexibleOutline(0, 0, 5, 0));
			style.setMargin(new FlexibleOutline(6, 6, 5, 6));
		} else {
			style.setPadding(new FlexibleOutline(14, 0, 5, 0));
			style.setMargin(new FlexibleOutline(0, 14, 0, 14));
		}

		style.setBorder(new FlexibleRectangularBorder(HIGHLIGHT_COLOR, 0, 0, 1, 0));

		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.DIALOG_CONTENT));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);
		if (condensed) {
			style.setMargin(new FlexibleOutline(0, 4, 0, 6));
		} else {
			style.setMargin(new FlexibleOutline(10, 14, 0, 14));
		}

		return stylesheet;
	}

	/**
	 * Gets the common background color.
	 *
	 * @return colors
	 */
	public int getBackgroundColor() {
		return BACKGROUND_COLOR;
	}
}
