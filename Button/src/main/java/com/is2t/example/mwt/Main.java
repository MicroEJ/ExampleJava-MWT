/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt;

import com.is2t.example.mwt.composite.ThreeComposite;
import com.is2t.example.mwt.widget.Button;
import com.is2t.example.mwt.widget.ButtonActionHandler;
import com.is2t.example.mwt.widget.Label;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.Panel;

/**
 * This is the entry point of the program, with the main() method.
 * <p>
 * MicroUI / MWT context is created, widgets are created and displayed.
 * 
 */
public class Main {

	// Prevents initialization.
	private Main() {
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		// Initialize MicroUI / MWT context
		MicroUI.errorLog(true); // useful for development
		MWT.RenderingContext.add(new DefaultTheme());

		// Create the widgets
		final Label label = new Label("Application starts...");

		// Create a ButtonActionHandler to update the label when a button is
		// pressed and released
		ButtonActionHandler handler = new ButtonActionHandler() {

			@Override
			public void onReleased(Button button) {
				String text = button.getText();
				label.setText(text);
			}

			@Override
			public void onPressed(Button button) {
				// Nothing to do, the label is changed upon button release
			}
		};

		Button buttonOne = new Button("Hello", handler);
		Button buttonTwo = new Button("World", handler);

		// Create a composite to organize them
		ThreeComposite composite = new ThreeComposite(label, buttonOne, buttonTwo);

		// Display the widgets
		Desktop desktop = new Desktop();
		Panel panel = new Panel(0, 0, desktop.getWidth(), desktop.getHeight());
		panel.setWidget(composite);
		panel.show(desktop);
		desktop.show();
	}

}
