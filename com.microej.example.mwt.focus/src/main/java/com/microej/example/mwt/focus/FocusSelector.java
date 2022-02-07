/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.focus;

import ej.mwt.Widget;
import ej.mwt.event.EventDispatcher;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.SelectorHelper;

/**
 * A focus selector selects by checking if the widget has the focus.
 * <p>
 * Equivalent to <code>:focus</code> selector in CSS. Its specificity is (0,0,1,0).
 *
 * @see SelectorHelper
 */
public class FocusSelector implements Selector {

	/**
	 * Focus selector singleton to avoid creating several ones.
	 */
	public static final FocusSelector FOCUS_SELECTOR = new FocusSelector();
	// equals() and hashCode() from Object are sufficient as long as there is only one instance.

	/**
	 * Creates a focus selector.
	 */
	private FocusSelector() {
	}

	@Override
	public boolean appliesToWidget(Widget widget) {
		EventDispatcher eventDispatcher = widget.getDesktop().getEventDispatcher();
		if (!(eventDispatcher instanceof FocusEventDispatcher)) {
			// this desktop does not support focus
			return false;
		}

		FocusEventDispatcher focusEventDispatcher = (FocusEventDispatcher) eventDispatcher;
		return focusEventDispatcher.isFocusedWidget(widget);
	}

	@Override
	public int getSpecificity() {
		return SelectorHelper.getSpecificity(0, 0, 1, 0);
	}
}
