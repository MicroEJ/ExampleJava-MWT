/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.popup;

import ej.annotation.Nullable;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.EventDispatcher;

/**
 * Dispatches the pointer events received on a desktop to its children.
 * <p>
 * If a popup is open, the events are sent only to this popup.
 */
class PopupEventDispatcher extends EventDispatcher {

	/**
	 * The "exited" action.
	 */
	public static final int EXITED = 0x00;

	private final PopupDesktop desktop;

	@Nullable
	private DesktopEventGenerator eventGenerator;

	@Nullable
	private Widget pressedHierarchyLeaf;
	@Nullable
	private Widget consumerWidget;

	/**
	 * Creates a desktop event dispatcher.
	 *
	 * @param desktop
	 *            the desktop to dispatch in.
	 */
	PopupEventDispatcher(PopupDesktop desktop) {
		super(desktop);
		this.desktop = desktop;
	}

	@Override
	public void initialize() {
		DesktopEventGenerator eventGenerator = createEventGenerator();
		eventGenerator.addToSystemPool();
		this.eventGenerator = eventGenerator;
	}

	@Override
	public void dispose() {
		DesktopEventGenerator eventGenerator = this.eventGenerator;
		if (eventGenerator != null) {
			eventGenerator.removeFromSystemPool();
			this.eventGenerator = null;
		}

		this.pressedHierarchyLeaf = null;
		this.consumerWidget = null;
	}

	/**
	 * Returns the leaf widget of the hierarchy which is subscribed to the ongoing pointer session.
	 * <p>
	 * When a pointer session is started, all the enabled widgets under the pointer become subscribed to this session.
	 * <p>
	 * The reference to this widget is initialized when the pointer is pressed ; it is updated when the pointer is
	 * dragged outside of the bounds of the widget or when a pointer event is consumed ; and it is reset when the
	 * pointer is released or when the pointer is dragged outside of the bounds of each subscribed widgets.
	 *
	 * @return the leaf widget of the subscribed hierarchy, or <code>null</code> if there is none.
	 */
	@Nullable
	public Widget getPressedHierarchyLeaf() {
		return this.pressedHierarchyLeaf;
	}

	/**
	 * Returns the widget which has consumed the ongoing pointer session.
	 * <p>
	 * When a pointer event is been consumed during a pointer session, it becomes the only widget to be subscribed to
	 * this session.
	 * <p>
	 * The reference to this widget is initialized when a pointer event is consumed ; and it is reset when the pointer
	 * is released or when the pointer is dragged outside of the bounds of the widget.
	 *
	 * @return the consumer widget, or <code>null</code> if there is none.
	 */
	@Nullable
	public Widget getConsumerWidget() {
		return this.consumerWidget;
	}

	/**
	 * Creates the event generator which is responsible for generating exit events to the widgets. By default, a
	 * {@link DesktopEventGenerator desktop event generator} is created.
	 *
	 * @return the event generator.
	 * @see DesktopEventGenerator
	 */
	protected DesktopEventGenerator createEventGenerator() {
		return new DesktopEventGenerator();
	}

	/**
	 * Gets the event generator of this dispatcher.
	 *
	 * @return the event generator of this dispatcher.
	 */
	@Nullable
	protected DesktopEventGenerator getEventGenerator() {
		return this.eventGenerator;
	}

	private int buildExitEvent() {
		DesktopEventGenerator eventGenerator = this.eventGenerator;
		assert (eventGenerator != null);
		return eventGenerator.buildEvent(EXITED);
	}

	@Override
	public boolean dispatchEvent(int event) {
		// dispatch events (separate pointer and other events)
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			pointerEvent(event);
			return true;
		}
		return false;
	}

	/**
	 * Handles a pointer button event.
	 */
	private void pointerEvent(int event) {
		Pointer pointer = (Pointer) Event.getGenerator(event);
		int action = Buttons.getAction(event);

		switch (action) {
		case Buttons.PRESSED:
			pressed(event, pointer);
			break;
		case Pointer.DRAGGED:
			dragged(event, pointer);
			break;
		case Buttons.RELEASED:
			released(event, pointer);
			break;
		default:
			// ignore those events
		}
	}

	private void pressed(int event, Pointer pointer) {
		updatePointerPress(pointer);
		dispatchEvent(event, true, false);
	}

	private void dragged(int event, Pointer pointer) {
		updatePointerDrag(pointer);
		dispatchEvent(event, true, true);
	}

	private void released(int event, Pointer pointer) {
		updatePointerDrag(pointer);
		dispatchEvent(event, false, true);

		this.consumerWidget = null;
		this.pressedHierarchyLeaf = null;
	}

	private void updatePointerPress(Pointer pointer) {
		int x = pointer.getX();
		int y = pointer.getY();

		PopupDesktop desktop = this.desktop;
		// search widget under the pointer
		Widget widget = getWidgetUnderPointer(desktop, x, y);

		// search the first enabled widget in the hierarchy
		while (widget != null && !widget.isEnabled()) {
			widget = widget.getParent();
		}

		// set pressed hierarchy leaf
		this.pressedHierarchyLeaf = widget;
		this.consumerWidget = null;
	}

	@Nullable
	private Widget getWidgetUnderPointer(PopupDesktop desktop, int x, int y) {
		Widget popup = desktop.getPopup();
		Widget widget;
		widget = desktop.getWidgetAt(x, y);
		// Restrict to the popup if shown.
		if (widget != null && popup != null && !popup.containsWidget(widget)) {
			if (desktop.isInformationPopup()) {
				desktop.hidePopup(popup);
			}
			widget = null;
		}
		return widget;
	}

	private void updatePointerDrag(Pointer pointer) {
		int x = pointer.getX();
		int y = pointer.getY();

		Widget oldPressedHierarchyLeaf = this.pressedHierarchyLeaf;
		if (oldPressedHierarchyLeaf == null) { // there is no point going further
			return;
		}

		// check that the pressed hierarchy is still attached to the desktop
		PopupDesktop desktop = this.desktop;
		if (!desktop.containsWidget(oldPressedHierarchyLeaf)) {
			this.consumerWidget = null;
			this.pressedHierarchyLeaf = null;
			return;
		}

		// search leaf widget under the pointer
		Widget widget = getWidgetUnderPointer(desktop, x, y);

		// search the first widget in the hierarchy which intersects with the pressed hierarchy
		while (widget != null && (!widget.isEnabled() || !widget.containsWidget(oldPressedHierarchyLeaf))) {
			widget = widget.getParent();
		}

		// check whether the widget under the pointer is still the same
		if (widget == oldPressedHierarchyLeaf) {
			return;
		}

		Widget consumerWidget = this.consumerWidget;
		if (consumerWidget != null) { // press has been consumed
			// send exit to consumer widget (because the pointer is no longer over the consumer widget)
			sendEventToWidget(consumerWidget, buildExitEvent());
			this.pressedHierarchyLeaf = null;
		} else { // press has not been consumed
			// send exit to all ancestors of the old leaf except the ancestors of the new leaf
			sendEventToLimitedWidgetHierarchy(oldPressedHierarchyLeaf, widget, buildExitEvent());
			this.pressedHierarchyLeaf = widget;
		}
	}

	/**
	 * Dispatches the given event.
	 * <p>
	 * If there is already a consumer widget, the event is sent to this widget.
	 * <p>
	 * If there is no consumer widget, the event is sent to the pressed hierarchy. If a widget consumes the event, it
	 * becomes the consumed widget and exit events are sent to all the other widgets of the hierarchy.
	 *
	 * @param event
	 *            the event to dispatch.
	 * @param exitOffsprings
	 *            whether an exit event should be sent to the offsprings of the widget which consumes the event.
	 * @param exitAncestors
	 *            whether an exit event should be sent to the ancestors of the widget which consumes the event.
	 */
	private void dispatchEvent(int event, boolean exitOffsprings, boolean exitAncestors) {
		Widget consumerWidget = this.consumerWidget;
		if (consumerWidget != null) {
			sendEventToWidget(consumerWidget, event);
		} else {
			Widget pressedHierarchyLeaf = this.pressedHierarchyLeaf;
			if (pressedHierarchyLeaf != null) {
				consumerWidget = sendEventToWidgetHierarchy(pressedHierarchyLeaf, event);
				if (consumerWidget != null) {
					int exitEvent = buildExitEvent();
					if (exitOffsprings) {
						sendEventToLimitedWidgetHierarchy(pressedHierarchyLeaf, consumerWidget, exitEvent);
					}
					if (exitAncestors) {
						sendEventToLimitedWidgetHierarchy(consumerWidget.getParent(), null, exitEvent);
					}
					this.consumerWidget = consumerWidget;
					this.pressedHierarchyLeaf = consumerWidget;
				}
			}
		}
	}

	/**
	 * Sends the given event to the widgets in the given old hierarchy unless they are also in the given new hierarchy.
	 * <p>
	 * Events are sent to all widgets even if one of them consumes the event.
	 *
	 * @param oldHierarchyLeaf
	 *            the leaf of the hierarchy to which the event should be sent.
	 * @param newHierarchyLeaf
	 *            the leaf of the hierarchy to which the event should NOT be sent (may be null).
	 * @param event
	 *            the event to send.
	 */
	private void sendEventToLimitedWidgetHierarchy(@Nullable Widget oldHierarchyLeaf, @Nullable Widget newHierarchyLeaf,
			int event) {
		Widget widget = oldHierarchyLeaf;
		while (widget != null) {
			if (widget == newHierarchyLeaf) {
				return;
			}

			// send event, ignore whether it is consumed
			sendEventToWidget(widget, event);

			// go to next ancestor
			widget = widget.getParent();
		}
	}

	/**
	 * Returns whether or not the given event is an exited event.
	 *
	 * @param event
	 *            the event to check.
	 * @return true if the given event is an exited event, false otherwise.
	 */
	public static final boolean isExited(int event) {
		return (Event.getType(event) == DesktopEventGenerator.EVENT_TYPE
				&& DesktopEventGenerator.getAction(event) == EXITED);
	}
}
