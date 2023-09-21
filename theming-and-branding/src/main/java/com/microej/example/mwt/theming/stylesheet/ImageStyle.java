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
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;

/**
 * Image theme style.
 */
public class ImageStyle extends StyleTheme {

	@Override
	public String getName() {
		return "Image"; //$NON-NLS-1$
	}

	@Override
	public CascadingStylesheet extendStylesheet(Theme theme) {
		CascadingStylesheet stylesheet = super.extendStylesheet(theme);
		boolean condensed = theme.isCondensed();

		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.BACKGROUND_CENTER));
		style.setBackground(
				new ImageBackground(Image.getImage("/images/mascote.png"), Alignment.HCENTER, Alignment.BOTTOM)); //$NON-NLS-1$

		style = stylesheet.getSelectorStyle(new ClassSelector(ClassSelectors.DIALOG_FRAME));
		Image backgroundImage;
		if (condensed) {
			backgroundImage = Image.getImage("/images/frame_condensed.png"); //$NON-NLS-1$
		} else {
			backgroundImage = Image.getImage("/images/frame_normal.png"); //$NON-NLS-1$
		}
		style.setBackground(new ImageBackground(backgroundImage));
		style.setDimension(new FixedDimension(backgroundImage.getWidth(), backgroundImage.getHeight()));

		Selector buttonSelector = new ClassSelector(ClassSelectors.DIALOG_BUTTON);
		Selector activeSelector = new StateSelector(Button.ACTIVE);

		// normal image button state
		style = stylesheet.getSelectorStyle(buttonSelector);
		Image buttonImage = Image.getImage(condensed ? "/images/btn_short_normal.png" : "/images/btn_long_normal.png"); //$NON-NLS-1$ //$NON-NLS-2$
		if (condensed) {
			int rightShift = 35;
			style.setPadding(new FlexibleOutline(0, rightShift, 0, 0));
			style.setDimension(new FixedDimension(buttonImage.getWidth() - rightShift, buttonImage.getHeight() - 10));
			style.setHorizontalAlignment(Alignment.RIGHT);
			style.setVerticalAlignment(Alignment.TOP);
		} else {
			style.setPadding(new FlexibleOutline(5, 0, 0, 0));
			style.setDimension(new FixedDimension(buttonImage.getWidth(), buttonImage.getHeight() - 15));
			style.setHorizontalAlignment(Alignment.HCENTER);
			style.setVerticalAlignment(Alignment.TOP);
		}
		style.setBackground(new ImageBackground(buttonImage));

		// pressed image button state
		style = stylesheet.getSelectorStyle(new AndCombinator(buttonSelector, activeSelector));
		style.setFont(theme.getFont());
		Image pressedButtonImage = Image
				.getImage(condensed ? "/images/btn_short_pressed.png" : "/images/btn_long_pressed.png"); //$NON-NLS-1$ //$NON-NLS-2$
		style.setBackground(new ImageBackground(pressedButtonImage));

		return stylesheet;
	}

}
