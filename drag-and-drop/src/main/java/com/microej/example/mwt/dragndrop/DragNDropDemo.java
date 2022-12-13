/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.dragndrop;

import ej.bon.Timer;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularMulticoloredBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.OnClickListener;

/**
 * This demo illustrates the usage of a grid whose children can be dragged and dropped.
 */
public class DragNDropDemo {

	// Style (fonts, colors and boxes).
	private static final String SOURCE_SANS_PRO_19PX_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int CONCRETE_WHITE_25 = 0xb1bdc3;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;
	private static final int GRID_PADDING = 10;
	private static final int BUTTON_MARGIN = 5;
	private static final int ELEMENT_BORDER = 2;

	// Class selectors.
	private static final int DRAGGED = 0;

	// Utilities.
	private static final int NB_ELEMENTS = 9;
	private static final int COLUMNS_COUNT = 3;

	/**
	 * Creates a simple UI that demonstrates a drag'n'drop grid.
	 *
	 * @param args
	 *            the arguments of the program.
	 */
	public static void main(String[] args) {
		DragNDropDemo example = new DragNDropDemo();
		example.start();
	}

	private void start() {
		MicroUI.start();

		CascadingStylesheet stylesheet = createStylesheet();

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);

		Timer timer = new Timer();
		final DragNDropGrid grid = new DragNDropGrid(timer, COLUMNS_COUNT, DRAGGED);
		for (int i = 0; i < NB_ELEMENTS; i++) {
			final String index = String.valueOf(i);
			Button button = new Button("Button " + index); //$NON-NLS-1$
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					System.out.println("Clicked " + index); //$NON-NLS-1$
				}
			});
			grid.addChild(button);
		}
		desktop.setWidget(grid);

		desktop.requestShow();
	}

	private static CascadingStylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(CONCRETE_BLACK_75);

		style = stylesheet.getSelectorStyle(new TypeSelector(DragNDropGrid.class));
		style.setPadding(new UniformOutline(GRID_PADDING));

		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));
		style.setColor(Colors.WHITE);
		style.setBackground(new RectangularBackground(CORAL));
		style.setBorder(new UniformOutline(ELEMENT_BORDER));
		style.setMargin(new UniformOutline(BUTTON_MARGIN));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RectangularBackground(POMEGRANATE));

		style = stylesheet.getSelectorStyle(new ClassSelector(DRAGGED));
		style.setBorder(new FlexibleRectangularMulticoloredBorder(ELEMENT_BORDER, CONCRETE_WHITE_25, ELEMENT_BORDER,
				CONCRETE_BLACK_75, ELEMENT_BORDER, CONCRETE_BLACK_75, ELEMENT_BORDER, CONCRETE_WHITE_25));

		return stylesheet;
	}

}
