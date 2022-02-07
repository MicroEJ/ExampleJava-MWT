/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.contextsensitive.configuration;

/**
 * Defines an object which listens to click events.
 */
public interface OnConfigurationChangedListener {

	/**
	 * Invoked when the configuration has changed.
	 */
	void onConfigurationChanged();
}
