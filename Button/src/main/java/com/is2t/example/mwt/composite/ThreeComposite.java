/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.composite;

import ej.mwt.Composite;
import ej.mwt.Widget;

/**
 * This is a very simple composite able to place one widget above and two
 * widgets side-by-side below.
 * 
 */
public class ThreeComposite extends Composite {

	private Widget one;
	private Widget two;
	private Widget three;

	/**
	 * Create a composite with three widgets.
	 * 
	 * @param one
	 *            the first widget
	 * @param two
	 *            the second widget
	 * @param three
	 *            the third widget
	 */
	public ThreeComposite(Widget one, Widget two, Widget three) {
		super();
		this.one = one;
		this.two = two;
		this.three = three;

		// It is very important to add the widgets to our hierarchy!
		add(one);
		add(two);
		add(three);
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		// Validate the widgets we contain (composite design pattern)
		one.validate(widthHint, heightHint / 2);
		two.validate(widthHint / 2, heightHint / 2);
		three.validate(widthHint / 2, heightHint / 2);

		// Set the widget bounds
		one.setBounds(0, 0, widthHint, heightHint / 2);
		two.setBounds(0, heightHint / 2, widthHint / 2, heightHint / 2);
		three.setBounds(widthHint / 2, heightHint / 2, widthHint / 2,
				heightHint / 2);

		// Set our own preferred size
		setPreferredSize(widthHint, heightHint);
	}
}
