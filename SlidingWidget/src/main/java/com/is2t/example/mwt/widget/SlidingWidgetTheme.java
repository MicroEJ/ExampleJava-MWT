/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.widget;

import ej.mwt.rendering.Look;
import ej.mwt.rendering.Theme;

/**
 * Example theme.
 */
public class SlidingWidgetTheme extends Theme {

	@Override
	public String getName() {
		return null;
	}

	@Override
	protected void populate() {
		// add the renderers
		add(new ScreenshotTransitionCompositeRenderer());
		add(new ColoredWidgetRenderer());
	}

	@Override
	public Look getDefaultLook() {
		return null;
	}

	@Override
	public boolean isStandard() {
		return false;
	}

}
