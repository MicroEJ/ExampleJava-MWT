/*
 * Copyright 2009-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc.view;

import java.util.Observable;
import java.util.Observer;

import com.microej.example.mwt.mvc.model.PercentageModel;

import ej.annotation.Nullable;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 *
 * A widget displaying a percentage.
 *
 */
public abstract class PercentageWidget extends Widget implements Observer {

	/**
	 * The color used for the data border.
	 */
	protected static final int COLOR_DATA_BORDER = 0x506a96; // blue

	private final PercentageModel model;

	/**
	 * Instantiates a {@link PercentageWidget}.
	 *
	 * @param model
	 *            the model to follow.
	 */
	protected PercentageWidget(PercentageModel model) {
		this.model = model;
	}

	@Override
	protected void onAttached() {
		this.model.addObserver(this);
	}

	@Override
	protected void onDetached() {
		this.model.deleteObserver(this);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		// do nothing
	}

	/**
	 * Gets the model.
	 *
	 * @return the model.
	 */
	public PercentageModel getModel() {
		return this.model;
	}

	@Override
	public void update(Observable o, @Nullable Object arg) {
		// Called when the model has changed
		requestRender();
	}
}
