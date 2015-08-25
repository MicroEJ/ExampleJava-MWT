/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.transition;

import ej.mwt.Widget;

/**
 * Allows to perform transition with widget.
 */
public interface TransitionManager {

	/**
	 * Performs the transition.
	 * 
	 * @param widget
	 *            the final state of the transition will show that widget.
	 */
	void goTo(Widget widget);

}
