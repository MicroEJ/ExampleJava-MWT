/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming.theme;

import ej.microui.display.Font;

/**
 * Normal theme implementation.
 */
public class NormalTheme implements Theme {
	@Override
	public String getName() {
		return "Normal"; //$NON-NLS-1$
	}

	@Override
	public Font getFont() {
		return Font.getFont("/fonts/barlow-Cond_18pt_400.ejf"); //$NON-NLS-1$
	}

	@Override
	public boolean isCondensed() {
		return false;
	}

}
