/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.focus;

import ej.annotation.Nullable;
import ej.mwt.Widget;
import ej.mwt.event.DesktopEventGenerator;

/**
 * This event generator is used by the {@link FocusEventDispatcher focus event dispatcher} of the desktop in order to
 * send focus events to the widgets.
 */
public class FocusEventGenerator extends DesktopEventGenerator {

	private final FocusEventDispatcher dispatcher;

	/**
	 * Creates a focus event generator.
	 *
	 * @param dispatcher
	 *            the focus event dispatcher.
	 */
	public FocusEventGenerator(FocusEventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * Returns the widget which is currently focused.
	 *
	 * @return the widget which is currently focused, or null if no widget if focused.
	 */
	@Nullable
	public Widget getFocusedWidget() {
		return this.dispatcher.getFocusedWidget();
	}
}
