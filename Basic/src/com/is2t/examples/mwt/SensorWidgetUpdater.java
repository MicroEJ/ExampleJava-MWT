/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.examples.mwt;

import java.util.TimerTask;

import com.is2t.examples.mwt.widget.SensorWidget;

/**
 * This is a TimerTask able to update a {@link SensorWidget}.
 */
public class SensorWidgetUpdater extends TimerTask {

	private SensorWidget sensor;

	public SensorWidgetUpdater(SensorWidget sensor) {
		super();
		this.sensor = sensor;
	}

	@Override
	public void run() {
		System.out.println("Updater.run() is executed by thread "
				+ Thread.currentThread().getName());

		int value = sensor.getValue();
		sensor.setValue(value + 1);
	}

}
