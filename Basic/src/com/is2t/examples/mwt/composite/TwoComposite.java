/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.examples.mwt.composite;

import ej.mwt.Composite;
import ej.mwt.Widget;

/**
 * This is a very simple composite able to place to widget side-to-side.
 * 
 */
public class TwoComposite extends Composite {

	private Widget one;
	private Widget two;

	public TwoComposite(Widget one, Widget two) {
		super();
		this.one = one;
		this.two = two;

		// It is very important to add the widgets to our hierarchy!
		add(one);
		add(two);
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		// Validate the widgets we contain (composite design pattern)
		one.validate(widthHint / 2, heightHint);
		two.validate(widthHint / 2, heightHint);
		
		// Set the widget bounds
		one.setBounds(0, 0, widthHint / 2, heightHint);
		two.setBounds(widthHint / 2, 0, widthHint / 2, heightHint);

		// Fix our own preferred size
		setPreferredSize(widthHint, heightHint);
	}

}
