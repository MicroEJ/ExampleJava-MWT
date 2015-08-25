/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt;

import java.util.Timer;
import java.util.TimerTask;

import com.is2t.example.mwt.composite.TwoComposite;
import com.is2t.example.mwt.widget.SensorWidget;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.Panel;

/**
 * This is the entry point of the program, with the main() method.
 * <p>
 * MicroUI / MWT context is created, widgets are created and displayed. Finally, a {@link Timer} is launched to
 * periodically update the widgets thanks to a {@link TimerTask}.
 * 
 */
public class Basic {

	private static final int SENSOR_UPDATE_PERIOD = 1000;
	private static final int STARTING_PRESSURE = 1020;
	private static final int STARTING_TEMPERATURE = 31;

	// Prevents initialization.
	private Basic() {
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		Configuration.LOGGER.logInfo("Main.main() is executed by thread " + Thread.currentThread().getName());

		// Initialize MicroUI / MWT context
		MicroUI.errorLog(true); // useful for development
		MWT.RenderingContext.add(new DefaultTheme());

		// Create and display widgets
		Desktop desktop = new Desktop();
		Panel panel = new Panel(0, 0, desktop.getWidth(), desktop.getHeight());

		SensorWidget temperature = new SensorWidget(STARTING_TEMPERATURE, "°C", "Temperature");
		SensorWidget pressure = new SensorWidget(STARTING_PRESSURE, "HPa", "Pressure");
		TwoComposite composite = new TwoComposite(temperature, pressure);

		panel.setWidget(composite);
		panel.show(desktop);
		desktop.show();

		// Start a Timer to update a SensorWidget
		Timer timer = new Timer("Updater timer");
		timer.scheduleAtFixedRate(new SensorWidgetUpdater(pressure), SENSOR_UPDATE_PERIOD / 2, SENSOR_UPDATE_PERIOD);
	}
}
