/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.widget;

import ej.mwt.Widget;

/**
 * This is a basic widget that models a sensor. Its has a value with a unit as
 * well as a title.
 */
public class SensorWidget extends Widget {

	// Data that matter
	private int value;
	private String unit;
	private String title;

	/**
	 * Create a new sensor widget.
	 * 
	 * @param value
	 *            the value
	 * @param unit
	 *            the unit of the value
	 * @param title
	 *            the title of the sensor
	 */
	public SensorWidget(int value, String unit, String title) {
		super();
		this.value = value;
		this.unit = unit;
		this.title = title;
	}

	@Override
	public String toString() {
		return "SensorWidget [value=" + value + ", unit=" + unit + ", title="
				+ title + "]";
	}

	/**
	 * Get the value of this sensor.
	 * 
	 * @return the current value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Change the value of this sensor.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(int value) {
		this.value = value;
		repaint(); // The model has changed, we ask for a graphical update
	}

	/**
	 * Get the unit of the value.
	 * 
	 * @return the current unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Change the unit of this sensor value.
	 * 
	 * @param unit
	 *            the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
		repaint(); // The model has changed, we ask for a graphical update
	}

	/**
	 * Get the title of this sensor.
	 * 
	 * @return the current title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Change the title associated with this widget.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
		repaint(); // The model has changed, we ask for a graphical update
	}

}
