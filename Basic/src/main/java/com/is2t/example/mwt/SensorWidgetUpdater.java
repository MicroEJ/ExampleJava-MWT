/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt;

import java.util.TimerTask;

import com.is2t.example.mwt.widget.SensorWidget;

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
