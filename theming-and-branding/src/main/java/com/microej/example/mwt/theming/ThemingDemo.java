/*
 *  Java
 *
 *  Copyright 2023-2024 MicroEJ Corp. All rights reserved.
 *  Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming;

import com.microej.example.mwt.theming.stylesheet.FlatStyle;
import com.microej.example.mwt.theming.stylesheet.ImageStyle;
import com.microej.example.mwt.theming.stylesheet.RoundedStyle;
import com.microej.example.mwt.theming.stylesheet.StyleTheme;
import com.microej.example.mwt.theming.theme.CondensedTheme;
import com.microej.example.mwt.theming.theme.NormalTheme;
import com.microej.example.mwt.theming.theme.Theme;
import com.microej.example.mwt.theming.widget.LineWrappingLabel;
import com.microej.example.mwt.theming.widget.RadioButton;
import com.microej.example.mwt.theming.widget.RadioButtonGroup;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Grid;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;

/**
 * Defines the app class of the demo.
 */
public class ThemingDemo {
	/**
	 * Main method.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();

		Desktop desktop = new Desktop();
		desktop.setStylesheet(ThemeHandler.INSTANCE.getStylesheet());
		desktop.setWidget(createContent(desktop));
		desktop.requestShow();
	}

	private static Widget createContent(final Desktop desktop) {
		SimpleDock outerContainer = new SimpleDock(LayoutOrientation.VERTICAL);
		outerContainer.addClassSelector(ClassSelectors.BACKGROUND);

		Label header = new Label("Theming"); //$NON-NLS-1$
		header.addClassSelector(ClassSelectors.HEADER);
		outerContainer.setFirstChild(header);

		Grid split = new Grid(LayoutOrientation.HORIZONTAL, 2);
		outerContainer.setCenterChild(split);
		split.addClassSelector(ClassSelectors.BACKGROUND_CENTER);

		List mainContainer = new List(LayoutOrientation.VERTICAL);

		// Theme Select
		mainContainer.addChild(createDesignSelect());

		// Color Select
		mainContainer.addChild(createTextSelect());
		split.addChild(mainContainer);

		SimpleDock frameContent = new SimpleDock(LayoutOrientation.VERTICAL);
		frameContent.addClassSelector(ClassSelectors.DIALOG_FRAME);
		Label title = new Label("Select shaping"); //$NON-NLS-1$
		title.addClassSelector(ClassSelectors.DIALOG_TITLE);
		frameContent.setFirstChild(title);
		LineWrappingLabel content = new LineWrappingLabel("Change the container style, color and compactness."); //$NON-NLS-1$
		content.addClassSelector(ClassSelectors.DIALOG_CONTENT);
		frameContent.setCenterChild(content);

		Button apply = new Button("Apply"); //$NON-NLS-1$
		apply.addClassSelector(ClassSelectors.DIALOG_BUTTON);
		frameContent.setLastChild(apply);

		apply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				desktop.setStylesheet(ThemeHandler.INSTANCE.getStylesheet());
				// After updating the Stylesheet we need to request a new layout to update the shown elements.
				desktop.requestLayOut();
			}
		});

		split.addChild(frameContent);

		return outerContainer;
	}

	private static Widget createDesignSelect() {
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);
		dock.addClassSelector(ClassSelectors.RADIO_GROUP_CONTAINER);
		Label label = new Label("Design"); //$NON-NLS-1$
		dock.setFirstChild(label);
		List container = new List(LayoutOrientation.VERTICAL);
		container.addClassSelector(ClassSelectors.RADIO_GROUP);
		dock.setCenterChild(container);
		RadioButtonGroup radioGroup = new RadioButtonGroup();
		container.addChild(createThemeButton(new FlatStyle(), radioGroup));
		container.addChild(createThemeButton(new RoundedStyle(), radioGroup));
		container.addChild(createThemeButton(new ImageStyle(), radioGroup));
		return dock;
	}

	private static Widget createTextSelect() {
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);
		dock.addClassSelector(ClassSelectors.RADIO_GROUP_CONTAINER);
		Label label = new Label("Text");//$NON-NLS-1$
		dock.setFirstChild(label);
		List container = new List(LayoutOrientation.VERTICAL);
		container.addClassSelector(ClassSelectors.RADIO_GROUP);
		dock.setCenterChild(container);
		RadioButtonGroup radioGroup = new RadioButtonGroup();
		container.addChild(createColorButton(new NormalTheme(), radioGroup));
		container.addChild(createColorButton(new CondensedTheme(), radioGroup));
		return dock;
	}

	private static Widget createThemeButton(final StyleTheme styleTheme, RadioButtonGroup group) {
		return createButton(styleTheme.getName(), new OnClickListener() {
			@Override
			public void onClick() {
				ThemeHandler.INSTANCE.setStyleTheme(styleTheme);
			}
		}, group, ThemeHandler.INSTANCE.getCurrentThemeName());
	}

	private static Widget createColorButton(final Theme colorTheme, RadioButtonGroup group) {
		return createButton(colorTheme.getName(), new OnClickListener() {
			@Override
			public void onClick() {
				ThemeHandler.INSTANCE.setColorTheme(colorTheme);
			}
		}, group, ThemeHandler.INSTANCE.getCurrentColorName());
	}

	private static Widget createButton(String name, OnClickListener onClickListener, RadioButtonGroup group,
			String currentName) {
		RadioButton button = new RadioButton(name, group);
		button.setOnClickListener(onClickListener);
		if (name.equals(currentName)) {
			group.setChecked(button);
		}
		return button;
	}

}
