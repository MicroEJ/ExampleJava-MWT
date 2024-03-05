/*
 * Java
 *
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.stackcontainer;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.Stylesheet;
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
 * This example shows a stack container that stacks its children on top of each other.
 */
public class StackContainerDemo {

	// Style (fonts, colors and boxes).
	private static final String SOURCE_SANS_PRO_19PX_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int AMBER = 0xf8a331;
	private static final int AVOCADO = 0x48a23f;
	private static final int CONCRETE_WHITE_50 = 0xcbd3d7;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;
	private static final int PADDING_MARGIN = 5;

	// Class selectors.
	private static final int PAGE_CONTAINER = 1;
	private static final int PAGE_CONTAINER_ODD = 2;
	private static final int TITLE = 3;

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();

		Desktop desktop = new Desktop();
		desktop.setStylesheet(createStylesheet());

		StackContainer container = new StackContainer();

		createPage(container);

		desktop.setWidget(container);
		desktop.requestShow();
	}

	private static void createPage(final StackContainer container) {
		final int index = container.getChildrenCount() + 1;
		Dock dock = new Dock();
		if (index % 2 == 0) {
			dock.addClassSelector(PAGE_CONTAINER);
		} else {
			dock.addClassSelector(PAGE_CONTAINER_ODD);
		}

		Label title = new Label("Page " + index); //$NON-NLS-1$
		title.addClassSelector(TITLE);
		dock.addChildOnTop(title);

		List list = new List(LayoutOrientation.VERTICAL);
		dock.setCenterChild(list);

		Button goToNext = new Button("Show next"); //$NON-NLS-1$
		list.addChild(goToNext);
		goToNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				createPage(container);
			}
		});

		Button back = new Button("Back"); //$NON-NLS-1$
		list.addChild(back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				if (container.getChildrenCount() != 1) {
					container.removeLast();
				}
			}
		});

		CircularIndeterminateProgress progress = new CircularIndeterminateProgress();
		dock.addChildOnLeft(progress);

		container.addChild(dock);
	}

	private static Stylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(CONCRETE_BLACK_75);
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setBackground(NoBackground.NO_BACKGROUND);

		style = stylesheet.getSelectorStyle(new TypeSelector(StackContainer.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setBorder(new RectangularBorder(CONCRETE_BLACK_75, PADDING_MARGIN));

		style = stylesheet.getSelectorStyle(new ClassSelector(PAGE_CONTAINER));
		style.setBorder(new RectangularBorder(AMBER, PADDING_MARGIN));
		style.setBackground(new RectangularBackground(Colors.WHITE));

		style = stylesheet.getSelectorStyle(new ClassSelector(PAGE_CONTAINER_ODD));
		style.setBorder(new RectangularBorder(AVOCADO, PADDING_MARGIN));
		style.setBackground(new RectangularBackground(CONCRETE_WHITE_50));

		style = stylesheet.getSelectorStyle(new ClassSelector(TITLE));
		style.setBorder(new FlexibleRectangularBorder(POMEGRANATE, 0, 0, 1, 0));

		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setPadding(new UniformOutline(PADDING_MARGIN));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setBackground(new RectangularBackground(CORAL));
		style.setBorder(new RectangularBorder(POMEGRANATE, 1));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(new TypeSelector(CircularIndeterminateProgress.class));
		style.setColor(POMEGRANATE);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setPadding(new UniformOutline(PADDING_MARGIN));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RectangularBackground(POMEGRANATE));

		return stylesheet;
	}
}
