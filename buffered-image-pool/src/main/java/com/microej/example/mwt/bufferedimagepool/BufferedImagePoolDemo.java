/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.bufferedimagepool;

import ej.annotation.Nullable;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.style.outline.border.RoundedBorder;
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

/**
 * This demo illustrates the usage of a pool of images shared between several widgets.
 */
public class BufferedImagePoolDemo {

	// Style (fonts, colors and boxes).
	private static final String SOURCE_SANS_PRO_19PX_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final int CORAL = 0xee502e;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int AMBER = 0xf8a331;
	private static final int CONCRETE_WHITE_50 = 0xcbd3d7;
	private static final int CONCRETE_BLACK_25 = 0x717d83;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;
	private static final int TITLE_UNDERLINE_BORDER = 2;
	private static final int ROUNDED_BORDER_RADIUS = 5;
	private static final int ROUNDED_BORDER_THICKNESS = 1;
	private static final int PADDING_MARGIN = 5;

	// Class selectors.
	private static final int TITLE = 1;
	private static final int NEXT = 2;

	// Utilities.
	private static final int NEW_VALUE_PERIOD = 1000;
	private static final int MAX_VALUE = 100;

	private final TransitionContainer transitionContainer;

	private int currentPage;

	@Nullable
	private Histogram histogram;

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();
		// Allocate images.
		BufferedImagePool.initialize();

		Desktop desktop = new Desktop();
		desktop.setStylesheet(createStylesheet());

		desktop.setWidget(new BufferedImagePoolDemo().getWidget());
		desktop.requestShow();
	}

	private BufferedImagePoolDemo() {
		this.transitionContainer = new TransitionContainer();

		Widget widget = createPage();
		this.transitionContainer.setChild(widget);

		// Generate random values periodically to feed the currently shown histogram.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int value = (int) (Math.random() * MAX_VALUE);
				Histogram histogram = BufferedImagePoolDemo.this.histogram;
				if (histogram != null) {
					histogram.addValue(value);
				}
			}
		}, 0, NEW_VALUE_PERIOD);
	}

	private TransitionContainer getWidget() {
		return this.transitionContainer;
	}

	private Widget createPage() {
		this.currentPage++;

		Dock dock = new Dock();
		Label title = new Label("Page " + this.currentPage); //$NON-NLS-1$
		title.addClassSelector(TITLE);
		dock.addChildOnTop(title);

		this.histogram = new Histogram(MAX_VALUE);
		dock.setCenterChild(this.histogram);

		Button next = new Button("Go To Next Page"); //$NON-NLS-1$
		next.addClassSelector(NEXT);
		dock.addChildOnBottom(next);

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				Widget nextPage = createPage();
				BufferedImagePoolDemo.this.transitionContainer.setChild(nextPage);
			}
		});

		return dock;
	}

	private static Stylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(CONCRETE_BLACK_75);
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setFont(Font.getFont(SOURCE_SANS_PRO_19PX_300));

		style = stylesheet.getSelectorStyle(new TypeSelector(TransitionContainer.class));
		style.setBackground(new RectangularBackground(Colors.WHITE));

		style = stylesheet.getSelectorStyle(new TypeSelector(Histogram.class));
		style.setExtraInt(Histogram.GRID_COLOR, CONCRETE_WHITE_50);
		style.setColor(AMBER);

		style = stylesheet.getSelectorStyle(new ClassSelector(TITLE));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setBorder(new FlexibleRectangularBorder(CONCRETE_BLACK_25, 0, 0, TITLE_UNDERLINE_BORDER, 0));
		style.setMargin(new UniformOutline(PADDING_MARGIN));
		style.setPadding(new UniformOutline(PADDING_MARGIN));

		style = stylesheet.getSelectorStyle(new ClassSelector(NEXT));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setBorder(new RoundedBorder(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(new RoundedBackground(CORAL, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setColor(Colors.WHITE);
		style.setMargin(new UniformOutline(PADDING_MARGIN));
		style.setPadding(new UniformOutline(PADDING_MARGIN));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new ClassSelector(NEXT), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RoundedBackground(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));

		return stylesheet;
	}
}
