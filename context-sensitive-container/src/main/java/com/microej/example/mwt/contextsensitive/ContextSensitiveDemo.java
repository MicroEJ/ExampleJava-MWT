/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.contextsensitive;

import com.microej.example.mwt.contextsensitive.configuration.Configuration;
import com.microej.example.mwt.contextsensitive.configuration.OnConfigurationChangedListener;
import com.microej.example.mwt.contextsensitive.configuration.WristMode;
import com.microej.example.mwt.contextsensitive.virtual.VirtualWatchDesktop;

import ej.annotation.Nullable;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Container;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.dimension.RelativeDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RoundedBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.ImageWidget;
import ej.widget.basic.Label;
import ej.widget.container.List;

/**
 * This demo shows a container (see {@link ContextSensitiveContainer} that can adapt to a device or user configuration (i.e., the
 * wrist mode - right or left).
 */
public class ContextSensitiveDemo implements OnConfigurationChangedListener {

	// Resources (fonts and images)
	private static final String SOURCE_SANS_PRO_12PX_400 = "/fonts/SourceSansPro_12px-400.ejf"; //$NON-NLS-1$
	private static final String SOURCE_SANS_PRO_BOLD_20PX = "/fonts/SourceSansProBold_20px.ejf"; //$NON-NLS-1$
	private static final String ICON_HANGUP = "/images/hangup.png"; //$NON-NLS-1$
	private static final String ICON_TEXT = "/images/text.png"; //$NON-NLS-1$
	private static final String ICON_PICKUP = "/images/pickup.png"; //$NON-NLS-1$

	// Colors
	private static final int POMEGRANATE = 0xcf4520;
	private static final int CORAL = 0xee502e;
	private static final int CONCRETE_BLACK_75 = 0x262a2c;

	// Style (padding, margin, thickness, etc.)
	private static final int BUTTON_SIDE_PADDING = 10;
	private static final int BUTTON_TOP_BOTTOM_PADDING = 2;
	private static final int ROUNDED_BORDER_RADIUS = 5;
	private static final int ROUNDED_BORDER_THICKNESS = 1;
	private static final int CONTENT_MARGIN = 20;
	private static final float NAME_HEIGHT_RATIO = 0.25f;

	// Class selectors
	private static final int BOLD = 0;
	private static final int INFOS = 1;
	private static final int ALIGNED_TEXT = 2;

	private final CascadingStylesheet stylesheet;

	private final Container mainContainer;

	@Nullable
	private Desktop desktop;

	private ContextSensitiveDemo() {
		this.mainContainer = createContent();
		this.stylesheet = createStylesheet();
	}

	/**
	 * The entry point of the example.
	 *
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();

		ContextSensitiveDemo demo = new ContextSensitiveDemo();

		// For demonstration purposes, the virtual watch desktop replaces the standard desktop.
		// It renders the application into a virtual watch that simulates the wrist mode changes.
		// In order to execute the application on the target watch hardware, use a Desktop instance instead.
		Desktop desktop = new VirtualWatchDesktop();
		demo.start(desktop);
	}

	/**
	 * Starts the demo, using the specified {@link Desktop} instance.
	 *
	 * @param desktop
	 *            the desktop to use.
	 */
	public void start(Desktop desktop) {
		this.desktop = desktop;
		desktop.setWidget(this.mainContainer);
		desktop.setStylesheet(this.stylesheet);
		desktop.requestShow();
		getConfiguration().setListener(this);
	}

	private ContextSensitiveContainer createContent() {
		// Use a container that can adapt to the wrist mode.
		ContextSensitiveContainer container = new ContextSensitiveContainer();

		List icons = createIcons();
		container.setSideChild(icons);

		List infos = createInfos();
		container.setMainChild(infos);

		return container;
	}

	private List createInfos() {
		List infos = new List(false);
		infos.addClassSelector(INFOS);

		Label call = new Label("Incoming call"); //$NON-NLS-1$
		call.addClassSelector(ALIGNED_TEXT);
		infos.addChild(call);

		Label name = new Label("John Doe"); //$NON-NLS-1$
		name.addClassSelector(BOLD);
		name.addClassSelector(ALIGNED_TEXT);
		infos.addChild(name);

		return infos;
	}

	private List createIcons() {
		List icons = new List(false);
		icons.addChild(new ImageWidget(ICON_PICKUP));
		icons.addChild(new ImageWidget(ICON_TEXT));
		icons.addChild(new ImageWidget(ICON_HANGUP));

		return icons;
	}

	private CascadingStylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setBackground(new RectangularBackground(Colors.WHITE));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_12PX_400));
		style.setColor(CONCRETE_BLACK_75);
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(new TypeSelector(ContextSensitiveContainer.class));
		style.setMargin(new UniformOutline(CONTENT_MARGIN));

		style = stylesheet.getSelectorStyle(new TypeSelector(ImageWidget.class));
		style.setColor(CORAL);

		style = stylesheet.getSelectorStyle(new ClassSelector(BOLD));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_BOLD_20PX));

		style = stylesheet.getSelectorStyle(new ClassSelector(INFOS));
		style.setDimension(new RelativeDimension(1f, NAME_HEIGHT_RATIO));

		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setFont(Font.getFont(SOURCE_SANS_PRO_12PX_400));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setPadding(new FlexibleOutline(BUTTON_TOP_BOTTOM_PADDING, BUTTON_SIDE_PADDING, BUTTON_TOP_BOTTOM_PADDING,
				BUTTON_SIDE_PADDING));
		style.setBorder(new RoundedBorder(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(new RoundedBackground(CORAL, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RoundedBackground(POMEGRANATE, ROUNDED_BORDER_RADIUS, ROUNDED_BORDER_THICKNESS));

		setWristModeStyle(stylesheet);

		return stylesheet;
	}

	@Override
	public void onConfigurationChanged() {
		setWristModeStyle(this.stylesheet);
		assert this.desktop != null;
		this.desktop.requestLayOut();
	}

	private void setWristModeStyle(CascadingStylesheet stylesheet) {
		Configuration configuration = getConfiguration();
		boolean isLeft = configuration.getWristMode() == WristMode.LEFT;

		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(ALIGNED_TEXT));
		style.setHorizontalAlignment(isLeft ? Alignment.LEFT : Alignment.RIGHT);
	}

	private Configuration getConfiguration() {
		return Configuration.getInstance();
	}
}
