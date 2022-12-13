/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.popup;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.style.outline.border.RoundedBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Dock;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * This demo illustrates the usage of popups.
 */
public class PopupDemo {

	// Style (fonts, colors and boxes).
	private static final String SOURCE_SANS_PRO_12PX_400 = "/fonts/SourceSansPro_12px-400.ejf"; //$NON-NLS-1$
	private static final String SOURCE_SANS_PRO_19PX_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int CHICK = 0xffc845;
	private static final int BONDI = 0x008eaa;
	private static final int AVOCADO = 0x48a23f;
	private static final int CONCRETE_WHITE_50 = 0xcbd3d7;
	private static final int CONCRETE_BLACK_25 = 0x717d83;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;
	private static final int PROGRESS_THICKNESS = 10;
	private static final int MESSAGE_TOP_PADDING = 5;
	private static final int TITLE_BOTTOM_BORDER = 2;
	private static final int PADDING_MARGIN = 5;
	private static final int ROUNDED_BORDER_RADIUS = 5;
	private static final int ROUNDED_BORDER_THICKNESS = 1;
	private static final int POPUP_SIDE_PADDING = 10;
	private static final int POPUP_BOTTOM_PADDING = 5;

	// Class selectors.
	private static final int MAIN_PAGE = 1;
	private static final int TITLE = 2;
	private static final int POPUP = 3;
	private static final int MESSAGE = 4;

	// Utilities.
	private static final int COLORS_COUNT = 3;

	private final PopupDesktop desktop;
	private final CascadingStylesheet stylesheet;
	private final CircularIndeterminateProgress progress;
	private int currentColor;

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();

		new PopupDemo().show();
	}

	private PopupDemo() {
		this.desktop = new PopupDesktop();
		this.stylesheet = createStylesheet();
		this.desktop.setStylesheet(this.stylesheet);

		Dock dock = new Dock();
		dock.addClassSelector(MAIN_PAGE);
		Label title = new Label("Popup Demo"); //$NON-NLS-1$
		title.addClassSelector(TITLE);
		dock.addChildOnTop(title);

		List list = new List(LayoutOrientation.HORIZONTAL);
		dock.setCenterChild(list);
		this.progress = new CircularIndeterminateProgress();
		list.addChild(this.progress);
		List openPopupButtons = new List(LayoutOrientation.VERTICAL);
		list.addChild(openPopupButtons);
		Button openInformationPopup = new Button("Open information popup"); //$NON-NLS-1$
		openPopupButtons.addChild(openInformationPopup);
		Button openPopup = new Button("Open action popup"); //$NON-NLS-1$
		openPopupButtons.addChild(openPopup);

		Button changeColor = new Button("Change color"); //$NON-NLS-1$
		dock.addChildOnBottom(changeColor);

		openInformationPopup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				openInformationPopup();
			}
		});
		openPopup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				openPopup();
			}
		});
		changeColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				EditableStyle style = PopupDemo.this.stylesheet
						.getSelectorStyle(new TypeSelector(CircularIndeterminateProgress.class));
				style.setColor(getNextColor());
				PopupDemo.this.progress.updateStyle();
			}

		});

		this.desktop.setWidget(dock);
	}

	private int getNextColor() {
		this.currentColor = getNextColorIndex(this.currentColor);
		switch (this.currentColor) {
		default:
		case 0:
			return CHICK;
		case 1:
			return BONDI;
		case 2:
			return AVOCADO;
		}
	}

	private int getNextColorIndex(int index) {
		index++;
		if (index == COLORS_COUNT) {
			index = 0;
		}
		return index;
	}

	private void show() {
		this.desktop.requestShow();
	}

	private void openInformationPopup() {
		Widget popup = createPopup("Information Popup", "Click outside the popup to close it."); //$NON-NLS-1$ //$NON-NLS-2$
		this.desktop.showPopup(popup, true);
	}

	private void openPopup() {
		final Dock popup = createPopup("User Request Popup", "Click on the button to close the popup."); //$NON-NLS-1$ //$NON-NLS-2$

		Button close = new Button("OK"); //$NON-NLS-1$
		popup.addChildOnBottom(close);

		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				PopupDemo.this.desktop.hidePopup(popup);
			}
		});

		this.desktop.showPopup(popup, false);
	}

	private Dock createPopup(String title, String extraMessage) {
		Dock popup = new Dock();
		popup.addClassSelector(POPUP);
		Label titleLabel = new Label(title);
		titleLabel.addClassSelector(TITLE);
		popup.addChildOnTop(titleLabel);

		Label content1 = new Label("The buttons outside the popup are not"); //$NON-NLS-1$
		content1.addClassSelector(MESSAGE);
		popup.addChildOnTop(content1);
		Label content2 = new Label("clickable while the popup is shown."); //$NON-NLS-1$
		popup.addChildOnTop(content2);
		Label content3 = new Label("The progress bar is constantly redrawn"); //$NON-NLS-1$
		content3.addClassSelector(MESSAGE);
		popup.addChildOnTop(content3);
		Label content4 = new Label("behind the popup."); //$NON-NLS-1$
		popup.addChildOnTop(content4);
		Label content5 = new Label(extraMessage);
		content5.addClassSelector(MESSAGE);
		popup.addChildOnTop(content5);

		return popup;
	}

	private static CascadingStylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(CONCRETE_BLACK_75);

		style = stylesheet.getSelectorStyle(new ClassSelector(MAIN_PAGE));
		style.setBackground(new RectangularBackground(Colors.WHITE));

		style = stylesheet.getSelectorStyle(new TypeSelector(CircularIndeterminateProgress.class));
		style.setExtraInt(CircularIndeterminateProgress.THICKNESS, PROGRESS_THICKNESS);
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setColor(CHICK);

		style = stylesheet.getSelectorStyle(new TypeSelector(Label.class));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_12PX_400));
		style.setBackground(NoBackground.NO_BACKGROUND);

		style = stylesheet.getSelectorStyle(new ClassSelector(MESSAGE));
		style.setPadding(new FlexibleOutline(MESSAGE_TOP_PADDING, 0, 0, 0));

		style = stylesheet.getSelectorStyle(new ClassSelector(TITLE));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setBorder(new FlexibleRectangularBorder(CONCRETE_BLACK_25, 0, 0, TITLE_BOTTOM_BORDER, 0));
		style.setMargin(new UniformOutline(PADDING_MARGIN));
		style.setPadding(new UniformOutline(PADDING_MARGIN));

		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setBorder(new RoundedBorder(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(new RoundedBackground(CORAL, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setColor(Colors.WHITE);
		style.setMargin(new UniformOutline(PADDING_MARGIN));
		style.setPadding(new UniformOutline(PADDING_MARGIN));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RoundedBackground(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));

		style = stylesheet.getSelectorStyle(new ClassSelector(POPUP));
		style.setBackground(new RoundedBackground(CONCRETE_WHITE_50, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBorder(new RoundedBorder(CONCRETE_BLACK_25, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setPadding(new FlexibleOutline(0, POPUP_SIDE_PADDING, POPUP_BOTTOM_PADDING, POPUP_SIDE_PADDING));

		return stylesheet;
	}
}
