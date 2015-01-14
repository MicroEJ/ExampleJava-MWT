/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.examples.mwt.widget;

import ej.mwt.Widget;

/**
 * This is a basic widget that models a sensor.
 */
public class SensorWidget extends Widget {

	// Data that matter
	private int value;
	private String unit;
	private String title;

	public SensorWidget(int value, String unit, String title) {
		super();
		this.value = value;
		this.unit = unit;
		this.title = title;
	}

	@Override
	public String toString() {
		return "SensorWidget [value=" + value + ", unit=" + unit + ", title=" + title + "]";
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		repaint(); // The model has changed, we ask for a graphical update
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
		repaint(); // The model has changed, we ask for a graphical update
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		repaint(); // The model has changed, we ask for a graphical update
	}

}
