/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.removewidget;

import ej.microui.MicroUI;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.ImageButton;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Grid;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.SimpleDock;

/**
 * This example shows how to add and remove widgets within a container.
 */
public class RemoveWidgetDemo {

	// Class Selectors.
	private static final int ITEM = 0;

	// Style.
	private static final String SOURCE_SANS_PRO_19PX_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final int PADDING_MARGIN = 4;
	private static final int CORAL = 0xee502e;
	private static final int AMBER = 0xf8a331;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;

	// Utilities.
	private static final int CHILDREN_COUNT = 4;
	private static final int INITIAL_ITEM_ID = 1;

	private int itemId;

	private final Desktop desktop;

	/**
	 * Simple main that starts the application.
	 *
	 * @param args
	 *            args for the main function, they're not used on this application.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		RemoveWidgetDemo demo = new RemoveWidgetDemo();
		demo.start();
	}

	/**
	 * Creates the demo.
	 */
	public RemoveWidgetDemo() {
		this.itemId = INITIAL_ITEM_ID;
		this.desktop = new Desktop();
	}

	private void start() {
		Desktop desktop = this.desktop;
		desktop.setWidget(getContentWidget());
		desktop.setStylesheet(createStylesheet());
		desktop.requestShow();
	}

	private Widget getContentWidget() {
		final SimpleDock dock = new SimpleDock(LayoutOrientation.HORIZONTAL);

		final Grid grid = createGrid();
		dock.setCenterChild(grid);

		Button addItemButton = new Button("Add Item"); //$NON-NLS-1$
		addItemButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				if (grid.getChildrenCount() < CHILDREN_COUNT) {
					grid.addChild(createItem(grid));
					RemoveWidgetDemo.this.desktop.requestLayOut();
				}
			}
		});
		dock.setFirstChild(addItemButton);

		return dock;
	}

	private Grid createGrid() {
		Grid grid = new Grid(LayoutOrientation.VERTICAL, CHILDREN_COUNT);
		for (int i = 0; i < CHILDREN_COUNT; i++) {
			grid.addChild(createItem(grid));
		}

		return grid;
	}

	private Widget createItem(final Grid parent) {
		int id = this.itemId++;

		final SimpleDock item = new SimpleDock(LayoutOrientation.HORIZONTAL);
		item.addClassSelector(ITEM);

		final Label label = new Label("Item " + id); //$NON-NLS-1$
		item.setCenterChild(label);

		final ImageButton button = new ImageButton("/images/trash.png"); //$NON-NLS-1$
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				parent.removeChild(item);
				RemoveWidgetDemo.this.desktop.requestLayOut();
			}
		});
		item.setLastChild(button);

		return item;
	}

	private static CascadingStylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(CONCRETE_BLACK_75);
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setHorizontalAlignment(Alignment.HCENTER);

		// style for the docks
		style = stylesheet.getSelectorStyle(new TypeSelector(SimpleDock.class));
		style.setPadding(new UniformOutline(PADDING_MARGIN));

		// style for the grid that contains the items.
		style = stylesheet.getSelectorStyle(new TypeSelector(Grid.class));
		style.setVerticalAlignment(Alignment.TOP);

		// style for the items of the grid.
		style = stylesheet.getSelectorStyle(new ClassSelector(ITEM));
		style.setPadding(new UniformOutline(PADDING_MARGIN));
		style.setMargin(new UniformOutline(PADDING_MARGIN));
		style.setBackground(new RectangularBackground(AMBER));
		style.setBorder(new RectangularBorder(CORAL, 1));

		// style for the "Add Item" button.
		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setPadding(new UniformOutline(PADDING_MARGIN));
		style.setMargin(new UniformOutline(PADDING_MARGIN));
		style.setBackground(new RectangularBackground(AMBER));
		style.setBorder(new RectangularBorder(CORAL, 1));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);

		// style for the trash button.
		style = stylesheet.getSelectorStyle(new TypeSelector(ImageButton.class));
		style.setPadding(new FlexibleOutline(PADDING_MARGIN, 2 * PADDING_MARGIN, PADDING_MARGIN, PADDING_MARGIN));

		return stylesheet;
	}

}
