/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.focus;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.event.Event;
import ej.mwt.Desktop;
import ej.mwt.event.EventDispatcher;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Dock;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * This demo illustrates the usage of focus to navigate through an application.
 */
public class FocusDemo {

	// Prevents initialization.
	private FocusDemo() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Start MicroUI framework.
		MicroUI.start();

		// Show the UI.
		Desktop desktop = new Desktop() {
			@Override
			protected EventDispatcher createEventDispatcher() {
				return new FocusEventDispatcher(this);
			}
		};
		desktop.setStylesheet(createStylesheet());

		Dock dock = new Dock();

		dock.addChildOnRight(new Joystick());

		dock.addChildOnTop(new Label("Menu"));

		List list = new List(LayoutOrientation.VERTICAL);
		for (int i = 0; i < 5; i++) {
			final String item = "Item " + i;
			Button menuItem = new VerboseButton(item);
			menuItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					System.out.println("Go to " + item);
				}
			});
			list.addChild(menuItem);
		}
		dock.setCenterChild(list);

		Button button = new VerboseButton("Go");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				System.out.println("Go!");
			}
		});
		dock.addChildOnBottom(button);

		desktop.setWidget(dock);
		desktop.requestShow();
	}

	private static Stylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(FocusSelector.FOCUS_SELECTOR);
		style.setBackground(new RectangularBackground(Colors.RED));
		style.setColor(Colors.WHITE);

		return stylesheet;
	}

	private static class VerboseButton extends Button {

		public VerboseButton(String text) {
			super(text);
		}

		@Override
		public boolean handleEvent(int event) {
			if (Event.getType(event) == FocusEventGenerator.EVENT_TYPE) {
				int action = FocusEventGenerator.getAction(event);
				if (action == FocusEventDispatcher.FOCUS_GAINED) {
					System.out.println("Focus gained on '" + getText() + "'");
				} else if (action == FocusEventDispatcher.FOCUS_LOST) {
					System.out.println("Focus lost on '" + getText() + "'");
				}
				FocusEventGenerator focusGenerator = (FocusEventGenerator) Event.getGenerator(event);
				System.out.println("Focused widget: " + focusGenerator.getFocusedWidget());

				updateStyle();
				requestRender();
			}

			return super.handleEvent(event);
		}
	}
}
