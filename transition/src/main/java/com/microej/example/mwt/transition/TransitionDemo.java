/*
 * Java
 *
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.transition;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RoundedBorder;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Dock;

/**
 * This demo illustrates the transition container.
 */
public class TransitionDemo {
	/**
	 * Selectors.
	 */
	private static final int ROUNDED_BUTTON_CLASS_SELECTOR = 1;
	private static final int GRAY_BACKGROUND_CLASS_SELECTOR = 2;
	private static final int CORAL_BACKGROUND_CLASS_SELECTOR = 3;
	private static final int LABEL_CLASS_SELECTOR = 4;
	/**
	 * Sizes.
	 */
	private static final int BACKGROUND_MARGIN_HORIZONTAL = 35;
	private static final int BACKGROUND_MARGIN_VERTICAL = 15;
	private static final int BUTTON_PADDING = 5;
	private static final int ROUNDED_CORNER_RADIUS = 4;
	private static final int ROUNDED_BORDER_THICKNESS = 1;
	/**
	 * Font.
	 */
	private static final String SOURCE_19_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	/**
	 * Colors.
	 */
	private static final int CORAL = 0xee502e;
	private static final int GRAY_LIGHT = 0x909090;
	private static final int POMEGRANATE = 0xcf4520;
	private static final int ALTERNATE_BACKGROUND = 0x4b5357;

	private static final int BANDS = 0;
	private static final int CATHODIC = 1;
	private static final int FADE = 2;
	private static final int GROWING = 3;
	private static final int RANDOM_PAVING = 4;

	private final TransitionContainer transitionContainer;
	private int currentPage;
	private int currentTransition;

	/**
	 * Simple main.
	 *
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();

		Desktop desktop = new Desktop();
		desktop.setStylesheet(populateStylesheet());

		desktop.setWidget(new TransitionDemo().getContentWidget());
		desktop.requestShow();
	}

	TransitionDemo() {
		this.transitionContainer = new TransitionContainer(new FadeEffect());
	}

	private Widget getContentWidget() {
		Widget widget = createPage();
		this.transitionContainer.setMainChild(widget);
		return this.transitionContainer;
	}

	private Widget createPage() {
		this.currentPage++;

		String effectName = getEffectName();
		Button roundedButton = new Button("Go to next page with " + effectName + " effect."); //$NON-NLS-1$ //$NON-NLS-2$
		roundedButton.addClassSelector(ROUNDED_BUTTON_CLASS_SELECTOR);

		Label label = new Label("Page " + this.currentPage); //$NON-NLS-1$
		label.addClassSelector(LABEL_CLASS_SELECTOR);

		Dock dock = new Dock();
		if (this.currentPage % 2 == 0) {
			dock.addClassSelector(GRAY_BACKGROUND_CLASS_SELECTOR);
		} else {
			dock.addClassSelector(CORAL_BACKGROUND_CLASS_SELECTOR);
		}

		roundedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				showNewChild();
			}
		});

		dock.addChildOnBottom(roundedButton);
		dock.setCenterChild(label);

		return dock;
	}

	private String getEffectName() {
		String effectName;
		switch (this.currentTransition) {
		case BANDS:
			effectName = "Bands"; //$NON-NLS-1$
			break;
		case CATHODIC:
			effectName = "Cathodic"; //$NON-NLS-1$
			break;
		case FADE:
			effectName = "Fade"; //$NON-NLS-1$
			break;
		case GROWING:
			effectName = "Growing"; //$NON-NLS-1$
			break;
		case RANDOM_PAVING:
		default:
			effectName = "Random Paving"; //$NON-NLS-1$
			break;
		}
		return effectName;
	}

	private void showNewChild() {
		nextTransition();
		Widget nextPage = createPage();
		TransitionDemo.this.transitionContainer.setMainChild(nextPage);
	}

	private void nextTransition() {
		TransitionEffect effect;
		switch (this.currentTransition) {
		case BANDS:
			this.currentTransition++;
			effect = new BandsEffect();
			break;
		case CATHODIC:
			this.currentTransition++;
			effect = new CathodicEffect();
			break;
		case FADE:
			this.currentTransition++;
			effect = new FadeEffect();
			break;
		case GROWING:
			this.currentTransition++;
			effect = new GrowingEffect(true);
			break;
		case RANDOM_PAVING:
		default:
			effect = new RandomPavingEffect();
			this.currentTransition = BANDS;
			break;
		}
		this.transitionContainer.setEffect(effect);
	}

	private static Stylesheet populateStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		Selector roundedButton = new ClassSelector(ROUNDED_BUTTON_CLASS_SELECTOR);
		Selector activeSelector = new StateSelector(Button.ACTIVE);
		Selector coralBackgroundClassSelector = new ClassSelector(CORAL_BACKGROUND_CLASS_SELECTOR);
		Selector grayBackgroundClassSelector = new ClassSelector(GRAY_BACKGROUND_CLASS_SELECTOR);

		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(TransitionContainer.class));
		style.setBackground(NoBackground.NO_BACKGROUND);

		// all buttons
		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setMargin(new FlexibleOutline(BACKGROUND_MARGIN_VERTICAL, BACKGROUND_MARGIN_HORIZONTAL,
				BACKGROUND_MARGIN_VERTICAL, BACKGROUND_MARGIN_HORIZONTAL));
		style.setPadding(new UniformOutline(BUTTON_PADDING));
		style.setFont(Font.getFont(SOURCE_19_300));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setColor(Colors.WHITE);

		// rounded button
		style = stylesheet.getSelectorStyle(roundedButton);
		style.setBorder(new RoundedBorder(ALTERNATE_BACKGROUND, ROUNDED_CORNER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(
				new RoundedBackground(ALTERNATE_BACKGROUND, ROUNDED_CORNER_RADIUS, ROUNDED_BORDER_THICKNESS));

		// active rounded button
		style = stylesheet.getSelectorStyle(new AndCombinator(roundedButton, activeSelector));
		style.setBorder(new RoundedBorder(POMEGRANATE, ROUNDED_CORNER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(new RoundedBackground(POMEGRANATE, ROUNDED_CORNER_RADIUS, ROUNDED_BORDER_THICKNESS));

		// coral background
		style = stylesheet.getSelectorStyle(coralBackgroundClassSelector);
		style.setBackground(new RectangularBackground(CORAL));

		// gray light background
		style = stylesheet.getSelectorStyle(grayBackgroundClassSelector);
		style.setBackground(new RectangularBackground(GRAY_LIGHT));

		// label
		style = stylesheet.getSelectorStyle(new TypeSelector(Label.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setFont(Font.getFont(SOURCE_19_300));
		style.setColor(Colors.WHITE);

		return stylesheet;
	}
}
