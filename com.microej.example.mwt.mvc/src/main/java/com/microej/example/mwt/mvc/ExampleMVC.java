/**
 * Java
 *
 * Copyright 2009-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.mvc;

import java.io.IOException;

import com.microej.example.mwt.mvc.controller.PercentageController;
import com.microej.example.mwt.mvc.model.PercentageModel;
import com.microej.example.mwt.mvc.view.BarWidget;
import com.microej.example.mwt.mvc.view.CompositeView;
import com.microej.example.mwt.mvc.view.PieWidget;
import com.microej.example.mwt.mvc.view.TextWidget;

import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.microui.display.FlyingImage;
import ej.microui.display.Image;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.mwt.Panel;

/**
 * Shows three views (bar, pie, text) that represents the same data model (a percentage value).
 * It is possible to resize the views by dragging the cross at the intersection of the three views.
 */
public class ExampleMVC {

	public static void main(String[] args) {
		// start microUI
		MicroUI.start();

		// get the default display
		Display display = Display.getDefaultDisplay();

		// Add an image to display the pointer
		EventGenerator eventGen = EventGenerator.get(Pointer.class, 0);
		Pointer gen = (Pointer) eventGen;
		if (gen != null) {
			// try to create an image for the cursor
			try {
				Image cursor = Image.createImage("/images/mouse_cursor.png");
				gen.setFlyingImage(new FlyingImage(cursor));
			} catch (IOException e) {
				System.out.println("Cannot load cursor image.");
				return;
			}
		}

		Desktop desktop = new Desktop(display);

		// create model
		PercentageModel model = new PercentageModel();
		model.setValue(25);

		// create the views
		// set the views model (the views become listeners to the model)
		TextWidget textWidget = new TextWidget(model);
		BarWidget barWidget = new BarWidget(model);
		PieWidget pieWidget = new PieWidget(model);

		// add the views to the composite view
		CompositeView compositeView = new CompositeView(textWidget, pieWidget, barWidget);
		compositeView.setXCross(desktop.getWidth() / 2);
		compositeView.setYCross(desktop.getHeight() / 2);

		// create the panel
		Panel panel = new Panel();
		panel.setEventHandler(new PercentageController(compositeView, model));
		panel.setWidget(compositeView);

		// show the displayable
		panel.showFullScreen(desktop);
		desktop.show();
	}

}
