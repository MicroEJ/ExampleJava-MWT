/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.virtualwatch;

import ej.microui.MicroUI;
import ej.microui.display.Font;
import ej.microui.event.Event;
import ej.microui.event.generator.Command;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
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
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * This demo illustrates how to simulate a device on another device.
 */
public class VirtualWatchDemo {

	// Style (fonts, colors and boxes).
	private static final String SOURCE_SANS_PRO_12PX_400 = "/fonts/SourceSansPro_12px-400.ejf"; //$NON-NLS-1$
	private static final String SOURCE_SANS_PRO_19PX_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final String SOURCE_SANS_PRO_22PX_400 = "/fonts/SourceSansPro_22px-400.ejf"; //$NON-NLS-1$
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int CHICK = 0xffc845;
	private static final int BONDI = 0x008eaa;
	private static final int AVOCADO = 0x48a23f;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;
	private static final int BUTTON_SIDE_PADDING = 10;
	private static final int BUTTON_TOP_BOTTOM_PADDING = 2;
	private static final int ROUNDED_BORDER_RADIUS = 5;
	private static final int ROUNDED_BORDER_THICKNESS = 1;

	// Class selectors.
	private static final int TOP = 0;
	private static final int BOTTOM = 1;
	private static final int VALUE = 2;

	// Utilities.
	private static final int COLORS_COUNT = 3;

	private final CascadingStylesheet stylesheet;
	private final List mainContainer;
	private final Label valueLabel;
	private int currentValue;
	private int colorIndex;

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();

		VirtualWatchDemo virtualWatchDemo = new VirtualWatchDemo();
		// The virtual watch desktop replaces the standard desktop.
		// In order to execute the application on the target watch hardware, use a Desktop instance instead.
		VirtualWatchDesktop desktop = new VirtualWatchDesktop();
		desktop.setWidget(virtualWatchDemo.getWidget());
		desktop.setStylesheet(virtualWatchDemo.getStylesheet());
		desktop.requestShow();
	}

	private VirtualWatchDemo() {
		this.mainContainer = new List(LayoutOrientation.VERTICAL) {
			@Override
			public boolean handleEvent(int event) {
				if (Event.getType(event) == Command.EVENT_TYPE) {
					switch (Event.getData(event)) {
					case Command.SELECT:
						updateColor();
						return true;
					case Command.UP:
						incrementValue();
						return true;
					case Command.DOWN:
						decrementValue();
						return true;
					}
				}
				return super.handleEvent(event);
			}
		};

		Label chooseValue1 = new Label("Change value"); //$NON-NLS-1$
		chooseValue1.addClassSelector(BOTTOM);
		this.mainContainer.addChild(chooseValue1);
		Label chooseValue2 = new Label("with up & down buttons"); //$NON-NLS-1$
		chooseValue2.addClassSelector(TOP);
		this.mainContainer.addChild(chooseValue2);

		List middle = new List(LayoutOrientation.HORIZONTAL);
		this.mainContainer.addChild(middle);

		Button minus = new Button("-"); //$NON-NLS-1$
		middle.addChild(minus);
		minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				decrementValue();
			}
		});

		this.valueLabel = new Label();
		this.valueLabel.addClassSelector(VALUE);
		middle.addChild(this.valueLabel);

		Button plus = new Button("+"); //$NON-NLS-1$
		middle.addChild(plus);
		plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				incrementValue();
			}
		});

		Label changeColor1 = new Label("Change color with "); //$NON-NLS-1$
		changeColor1.addClassSelector(BOTTOM);
		this.mainContainer.addChild(changeColor1);
		Label changeColor2 = new Label("middle button"); //$NON-NLS-1$
		changeColor2.addClassSelector(TOP);
		this.mainContainer.addChild(changeColor2);

		updateValue(0);

		this.stylesheet = createStylesheet();
	}

	/**
	 * Gets the main widget.
	 *
	 * @return the main widget.
	 */
	public List getWidget() {
		return this.mainContainer;
	}

	/**
	 * Gets the stylesheet.
	 *
	 * @return the stylesheet.
	 */
	public CascadingStylesheet getStylesheet() {
		return this.stylesheet;
	}

	private void incrementValue() {
		updateValue(this.currentValue + 1);
	}

	private void decrementValue() {
		updateValue(this.currentValue - 1);
	}

	private void updateValue(int value) {
		this.currentValue = value;
		this.valueLabel.setText(Integer.toString(value));
		this.valueLabel.requestRender();
	}

	private void updateColor() {
		EditableStyle style = VirtualWatchDemo.this.stylesheet.getDefaultStyle();
		style.setBackground(new RectangularBackground(getNextColor()));
		this.mainContainer.updateStyle();
		this.mainContainer.requestRender();
	}

	private int getNextColor() {
		this.colorIndex = getNextColorIndex(this.colorIndex);
		switch (this.colorIndex) {
		default:
		case 0:
			return CHICK;
		case 1:
			return BONDI;
		case 2:
			return AVOCADO;
		}
	}

	private int getNextColorIndex(int colorIndex) {
		colorIndex++;
		if (colorIndex == COLORS_COUNT) {
			colorIndex = 0;
		}
		return colorIndex;
	}

	private CascadingStylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setBackground(new RectangularBackground(getNextColor()));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_12PX_400));
		style.setColor(CONCRETE_BLACK_75);
		style.setHorizontalAlignment(Alignment.HCENTER);

		style = stylesheet.getSelectorStyle(new ClassSelector(VALUE));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_22PX_400));
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(new ClassSelector(TOP));
		style.setVerticalAlignment(Alignment.TOP);

		style = stylesheet.getSelectorStyle(new ClassSelector(BOTTOM));
		style.setVerticalAlignment(Alignment.BOTTOM);

		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setPadding(new FlexibleOutline(BUTTON_TOP_BOTTOM_PADDING, BUTTON_SIDE_PADDING, BUTTON_TOP_BOTTOM_PADDING,
				BUTTON_SIDE_PADDING));
		style.setBorder(new RoundedBorder(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(new RoundedBackground(CORAL, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RoundedBackground(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));

		return stylesheet;
	}
}
