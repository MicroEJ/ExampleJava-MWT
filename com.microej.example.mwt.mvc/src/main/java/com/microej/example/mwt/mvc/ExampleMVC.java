/*
 * Copyright 2009-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc;

import com.microej.example.mwt.mvc.controller.PercentageController;
import com.microej.example.mwt.mvc.model.PercentageModel;
import com.microej.example.mwt.mvc.view.BarWidget;
import com.microej.example.mwt.mvc.view.CompositeView;
import com.microej.example.mwt.mvc.view.PercentageWidget;
import com.microej.example.mwt.mvc.view.PieWidget;
import com.microej.example.mwt.mvc.view.TextWidget;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.render.RenderPolicy;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;

/**
 * Shows three views (bar, pie, text) that represents the same data model (a percentage value). It is possible to resize
 * the views by dragging the cross at the intersection of the three views.
 */
public class ExampleMVC {

	private static final int COLOR_BACKGROUND = Colors.WHITE;
	private static final int COLOR_VIEW_BORDER = Colors.BLACK;

	public static void main(String[] args) {
		// start microUI
		MicroUI.start();

		// get the default display
		Display display = Display.getDisplay();

		// create model
		PercentageModel model = new PercentageModel();
		model.setValue(25);

		// create the views
		// set the views model (the views become listeners to the model)
		TextWidget textWidget = new TextWidget(model);
		BarWidget barWidget = new BarWidget(model);
		PieWidget pieWidget = new PieWidget(model);

		// add the views to the composite view
		CompositeView compositeView = new CompositeView(textWidget, pieWidget, barWidget);
		compositeView.setXCross(display.getWidth() / 2);
		compositeView.setYCross(display.getHeight() / 2);

		// create the desktop
		final PercentageController controller = new PercentageController(compositeView, model);
		Desktop desktop = new Desktop() {
			@Override
			protected RenderPolicy createRenderPolicy() {
				return new CursorImageRenderPolicy(this);
			}

			@Override
			public boolean handleEvent(int event) {
				return controller.handleEvent(event);
			}
		};
		desktop.setStylesheet(createStylesheet());
		desktop.setWidget(compositeView);

		// show the displayable
		desktop.requestShow();
	}

	public static Stylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setBackground(new RectangularBackground(COLOR_BACKGROUND));

		style = stylesheet.getSelectorStyle(new TypeSelector(PercentageWidget.class));
		style.setBorder(new RectangularBorder(COLOR_VIEW_BORDER, 1));

		return stylesheet;
	}
}
