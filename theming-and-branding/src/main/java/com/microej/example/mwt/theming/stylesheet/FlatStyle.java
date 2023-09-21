/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming.stylesheet;

import com.microej.example.mwt.theming.ClassSelectors;
import com.microej.example.mwt.theming.theme.Theme;

import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;

/**
 * Flat theme style.
 */
public class FlatStyle extends StyleTheme {
	private static final int BG_COLOR = 0x4b5357;
	private static final int BTN_COLOR_NORMAL = 0x6e777c;
	private static final int BTN_COLOR_PRESSED = 0x263138;

	@Override
	public String getName() {
		return "Flat"; //$NON-NLS-1$
	}

	@Override
	public CascadingStylesheet extendStylesheet(Theme theme) {
		CascadingStylesheet stylesheet = super.extendStylesheet(theme);
		boolean condensed = theme.isCondensed();

		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.DIALOG_FRAME));
		style.setBackground(new RectangularBackground(BG_COLOR));
		if (condensed) {
			style.setMargin(new FlexibleOutline(20, 12, 95, 0));
		}

		Selector buttonSelector = new ClassSelector(ClassSelectors.DIALOG_BUTTON);
		Selector activeSelector = new StateSelector(Button.ACTIVE);

		style = stylesheet.getSelectorStyle(buttonSelector);
		if (condensed) {
			style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
			style.setHorizontalAlignment(Alignment.LEFT);
			style.setVerticalAlignment(Alignment.BOTTOM);
			style.setMargin(new FlexibleOutline(0, 6, 6, 6));
		} else {
			style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_Y);
			style.setHorizontalAlignment(Alignment.HCENTER);
			style.setVerticalAlignment(Alignment.VCENTER);
			style.setMargin(new FlexibleOutline(0, 14, 14, 14));
		}

		style.setBackground(new RectangularBackground(BTN_COLOR_NORMAL));
		style.setPadding(new FlexibleOutline(2, 20, 2, 20));

		style = stylesheet.getSelectorStyle(new AndCombinator(buttonSelector, activeSelector));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setBackground(new RectangularBackground(BTN_COLOR_PRESSED));
		style.setColor(HIGHLIGHT_COLOR);

		return stylesheet;
	}

}
