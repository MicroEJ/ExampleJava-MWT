/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.widget;

import com.microej.example.mwt.Configuration;
import com.microej.example.mwt.DefaultLook;

import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;

/**
 * This is a basic widget that models a sensor. Its has a value with a unit as
 * well as a title.
 */
public class SensorWidget extends Widget {
	private static final int RELATION = 3;

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




	@Override
	public void render(GraphicsContext g) {
		Configuration.LOGGER.logInfo("SensorWidgetRenderer.render() is executed by thread "
				+ Thread.currentThread().getName());

		int width = getWidth();
		int height = getHeight();

		// Background
		g.setColor(DefaultLook.getProperty(DefaultLook.GET_BACKGROUND_COLOR_DEFAULT));
		g.fillRect(0, 0, width, height);

		// Text
		String title = getTitle();
		g.setColor(DefaultLook.getProperty(DefaultLook.GET_FOREGROUND_COLOR_DEFAULT));
		g.drawString(title, width / 2, height / RELATION, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		// StringBuilder are better than +
		StringBuilder builder = new StringBuilder();
		builder.append(getValue());
		builder.append(" ");
		builder.append(getUnit());
		String str = builder.toString();

		g.drawString(str, width / 2, height * 2 / RELATION, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}
	
	@Override
	public void validate(int widthHint, int heightHint) {
		//		Nothing to do.
	}
}
