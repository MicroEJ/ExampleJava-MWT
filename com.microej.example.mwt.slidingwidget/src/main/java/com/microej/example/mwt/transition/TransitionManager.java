/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.transition;

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
