/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming.stylesheet;

import com.microej.example.mwt.theming.ClassSelectors;
import com.microej.example.mwt.theming.theme.Theme;

import ej.microui.display.Image;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.ImageBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.border.RoundedBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;

/**
 * Rounded theme style.
 */
public class RoundedStyle extends StyleTheme {
	private static final int BORDER_COLOR = 0x6E777C;
	private static final int BTN_COLOR_PRESSED = 0x263138;

	@Override
	public String getName() {
		return "Rounded"; //$NON-NLS-1$
	}

	@Override
	public CascadingStylesheet extendStylesheet(Theme theme) {
		CascadingStylesheet stylesheet = super.extendStylesheet(theme);
		boolean condensed = theme.isCondensed();

		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.BACKGROUND_CENTER));
		style.setBackground(
				new ImageBackground(Image.getImage("/images/mascote.png"), Alignment.HCENTER, Alignment.BOTTOM)); //$NON-NLS-1$

		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.DIALOG_FRAME));
		style.setBorder(new RoundedBorder(BORDER_COLOR, 18, 1));
		Image backgroundImage;
		if (condensed) {
			backgroundImage = Image.getImage("/images/frame_transparent_condensed.png"); //$NON-NLS-1$
		} else {
			backgroundImage = Image.getImage("/images/frame_transparent_normal.png"); //$NON-NLS-1$
		}
		style.setBackground(new ImageBackground(backgroundImage));
		style.setDimension(new FixedDimension(backgroundImage.getWidth() - 2, backgroundImage.getHeight() - 2));

		Selector buttonSelector = new ClassSelector(ClassSelectors.DIALOG_BUTTON);
		Selector activeSelector = new StateSelector(Button.ACTIVE);

		// rectangle button
		style = stylesheet.getSelectorStyle(buttonSelector);
		if (condensed) {
			style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
			style.setHorizontalAlignment(Alignment.RIGHT);
			style.setVerticalAlignment(Alignment.BOTTOM);
			style.setMargin(new FlexibleOutline(0, 6, 6, 6));
		} else {
			style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_Y);
			style.setHorizontalAlignment(Alignment.HCENTER);
			style.setVerticalAlignment(Alignment.VCENTER);
			style.setMargin(new FlexibleOutline(0, 14, 14, 14));
		}

		style.setBackground(new RoundedBackground(BORDER_COLOR, 18, 1));
		style.setBorder(new RoundedBorder(BORDER_COLOR, 18, 1));
		style.setPadding(new FlexibleOutline(2, 20, 2, 20));

		// active rectangle button
		style = stylesheet.getSelectorStyle(new AndCombinator(buttonSelector, activeSelector));
		style.setBorder(new RoundedBorder(HIGHLIGHT_COLOR, 18, 1));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setBackground(new RoundedBackground(BTN_COLOR_PRESSED, 18, 1));
		style.setColor(HIGHLIGHT_COLOR);

		return stylesheet;
	}

}
