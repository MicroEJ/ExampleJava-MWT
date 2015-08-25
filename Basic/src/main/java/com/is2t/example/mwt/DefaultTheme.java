/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt;

import com.is2t.example.mwt.renderer.SensorWidgetRenderer;

import ej.mwt.rendering.Look;
import ej.mwt.rendering.Theme;

/**
 * A simple theme with the renderer for our sensor widgets.
 */
public class DefaultTheme extends Theme {

	@Override
	public String getName() {
		return "Default Theme";
	}

	@Override
	protected void populate() {
		add(new SensorWidgetRenderer());
	}

	@Override
	public Look getDefaultLook() {
		return new DefaultLook();
	}

	@Override
	public boolean isStandard() {
		return true;
	}

}
