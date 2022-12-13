/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.maskinggrid;

import ej.microui.MicroUI;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;

/**
 * This example shows a masking container that hides its children.
 */
public class MaskingGridDemo {

	// Style (fonts, colors and boxes).
	private static final String SOURCE_SANS_PRO_19PX_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int AMBER = 0xf8a331;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;
	private static final int PADDING_MARGIN = 4;

	// Class selectors.
	private static final int ITEM = 100;

	// Utilities.
	private static final int ITEM_COUNT = 9;

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new Desktop();
		desktop.setWidget(getContentWidget());
		desktop.setStylesheet(createStylesheet());
		desktop.requestShow();
	}

	/**
	 * Gets the widget hierarchy that represents the content of this demo.
	 *
	 * @return the root widget of the hierarchy.
	 */
	private static Widget getContentWidget() {
		final MaskingGrid grid = new MaskingGrid(LayoutOrientation.HORIZONTAL, 3);
		for (int i = 0; i < ITEM_COUNT; i++) {
			final List item = new List(LayoutOrientation.HORIZONTAL);

			List left = new List(LayoutOrientation.VERTICAL);
			item.addChild(left);
			Label labelItem = new Label("Item" + (i + 1)); //$NON-NLS-1$
			left.addChild(labelItem);
			CircularIndeterminateProgress progress = new CircularIndeterminateProgress();
			left.addChild(progress);

			Button button = new Button("Hide"); //$NON-NLS-1$
			item.addChild(button);

			grid.addChild(item);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					grid.setVisible(item, false);
					grid.requestRender();
				}
			});
			item.addClassSelector(ITEM);
		}
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);

		Button showAll = new Button("Show All"); //$NON-NLS-1$
		showAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				grid.setAllVisible(true);
				grid.requestRender();
			}
		});
		Button hideAll = new Button("Hide All"); //$NON-NLS-1$
		hideAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				grid.setAllVisible(false);
				grid.requestRender();
			}
		});

		List list = new List(LayoutOrientation.HORIZONTAL);
		list.addChild(showAll);
		list.addChild(hideAll);
		dock.setFirstChild(list);
		dock.setCenterChild(grid);

		return dock;
	}

	private static CascadingStylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(CONCRETE_BLACK_75);
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setHorizontalAlignment(Alignment.HCENTER);

		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setPadding(new UniformOutline(PADDING_MARGIN));
		style.setMargin(new UniformOutline(PADDING_MARGIN));
		style.setBackground(new RectangularBackground(AMBER));
		style.setBorder(new RectangularBorder(CORAL, 1));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RectangularBackground(CORAL));

		style = stylesheet.getSelectorStyle(new ClassSelector(ITEM));
		style.setBorder(new RectangularBorder(AMBER, 1));
		style.setMargin(new UniformOutline(PADDING_MARGIN));

		style = stylesheet.getSelectorStyle(new TypeSelector(CircularIndeterminateProgress.class));
		style.setColor(POMEGRANATE);
		style.setPadding(new UniformOutline(PADDING_MARGIN));

		return stylesheet;
	}

}
