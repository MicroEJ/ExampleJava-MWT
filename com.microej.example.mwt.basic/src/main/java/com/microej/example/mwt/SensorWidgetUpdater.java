/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt;

import java.util.TimerTask;

import com.microej.example.mwt.widget.SensorWidget;

/**
 * This is a TimerTask able to update a {@link SensorWidget}.
 */
public class SensorWidgetUpdater extends TimerTask {

	private final SensorWidget sensor;

	/**
	 * Create a new updater.
	 * 
	 * @param sensor
	 *            the sensor to update
	 */
	public SensorWidgetUpdater(SensorWidget sensor) {
		super();
		this.sensor = sensor;
	}

	@Override
	public void run() {
		Configuration.LOGGER.logInfo("Updater.run() is executed by thread " + Thread.currentThread().getName());

		int value = this.sensor.getValue();
		this.sensor.setValue(value + 1);
	}

}
