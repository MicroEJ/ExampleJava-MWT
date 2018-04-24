/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt;

import com.microej.example.mwt.composite.ThreeComposite;
import com.microej.example.mwt.widget.Button;
import com.microej.example.mwt.widget.ButtonActionHandler;
import com.microej.example.mwt.widget.Label;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
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
		// Initialize UI stack
		MicroUI.start();

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
		Panel panel = new Panel();
		panel.setWidget(composite);
		panel.showFullScreen(desktop);
		desktop.show();
	}

}
