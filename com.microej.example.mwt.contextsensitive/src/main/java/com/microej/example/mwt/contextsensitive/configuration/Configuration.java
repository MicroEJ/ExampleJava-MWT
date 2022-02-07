/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.contextsensitive.configuration;

import ej.annotation.Nullable;

/**
 * Provides configuration options to the application.
 *
 * <p>
 * Classes can be notified of configuration changes by using {@link #setListener(OnConfigurationChangedListener)}.
 */
public class Configuration {

	private static final Configuration INSTANCE = new Configuration();

	private WristMode wristMode;

	@Nullable
	private OnConfigurationChangedListener listener;

	private Configuration() {
		this.wristMode = WristMode.LEFT;
	}

	/**
	 * Gets the wrist mode.
	 *
	 * @return the wrist mode.
	 */
	public WristMode getWristMode() {
		return this.wristMode;
	}

	/**
	 * Sets the wrist mode.
	 *
	 * @param wristMode
	 *            the wrist mode to set.
	 */
	public void setWristMode(WristMode wristMode) {
		this.wristMode = wristMode;
		notifyListener();
	}

	private void notifyListener() {
		OnConfigurationChangedListener listener = this.listener;
		if (listener != null) {
			listener.onConfigurationChanged();
		}
	}

	/**
	 * Sets the listener.
	 *
	 * <p>
	 * The specified instance will be notified when the configuration changes.
	 *
	 * @param listener
	 *            the listener to set.
	 */
	public void setListener(OnConfigurationChangedListener listener) {
		this.listener = listener;
	}

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration.
	 */
	public static Configuration getInstance() {
		return INSTANCE;
	}
}
