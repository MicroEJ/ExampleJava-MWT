/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.focus;

import ej.annotation.Nullable;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Command;
import ej.mwt.Container;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;

/**
 * Dispatches the event received on a desktop to its children and manages a focus.
 * <p>
 * The focus changes from a widget to an other in the hierarchy tree using depth-first algorithm.
 * <ul>
 * <li>The commands {@link Command#RIGHT} or {@link Command#DOWN} focuses the next enabled widget.</li>
 * <li>The commands {@link Command#LEFT} or {@link Command#UP} focuses the previous enabled widget.</li>
 * <li>The other events are sent to the widget owning the focus at the time of the event.</li>
 * </ul>
 */
public class FocusEventDispatcher extends PointerEventDispatcher {

	/**
	 * The "focus gained" action.
	 */
	public static final int FOCUS_GAINED = 0x01;

	/**
	 * The "focus lost" action.
	 */
	public static final int FOCUS_LOST = 0x02;

	@Nullable
	private Widget focusedWidget;

	/**
	 * Creates a focus event dispatcher.
	 *
	 * @param desktop
	 *            the desktop to dispatch in.
	 */
	public FocusEventDispatcher(Desktop desktop) {
		super(desktop);
	}

	@Override
	protected DesktopEventGenerator createEventGenerator() {
		return new FocusEventGenerator(this);
	}

	/**
	 * Returns the widget which is currently focused.
	 *
	 * @return the widget which is currently focused, or null if no widget if focused.
	 */
	@Nullable
	public Widget getFocusedWidget() {
		return this.focusedWidget;
	}

	/**
	 * Returns whether the given widget is currently focused.
	 *
	 * @param widget
	 *            the widget to check.
	 * @return true if the given widget is focused, false otherwise.
	 */
	public boolean isFocusedWidget(Widget widget) {
		return (widget == this.focusedWidget);
	}

	private int buildFocusEvent(int action) {
		DesktopEventGenerator eventGenerator = getEventGenerator();
		assert (eventGenerator != null);
		return eventGenerator.buildEvent(action);
	}

	@Override
	public boolean dispatchEvent(int event) {
		// handle pointer events
		if (super.dispatchEvent(event)) {
			if (!Buttons.isReleased(event)) {
				Widget pressedWidget = getPressedHierarchyLeaf();
				if (pressedWidget == null || isFocusable(pressedWidget)) {
					focus(pressedWidget);
				}
			}
			return true;
		}

		// handle focus change events
		if (Event.getType(event) == Command.EVENT_TYPE) {
			switch (Event.getData(event)) {
			case Command.LEFT:
			case Command.UP:
				return focusPrevious();
			case Command.RIGHT:
			case Command.DOWN:
				return focusNext();
			default:
				// perform same behavior as for other event types
			}
		}

		return sendToFocusedWidget(event);
	}

	private boolean sendToFocusedWidget(int event) {
		Widget focusedWidget = this.focusedWidget;
		if (focusedWidget != null) {
			if (!getDesktop().containsWidget(focusedWidget)) {
				// Widget is no more attached to the desktop.
				focus(null);
				focusedWidget = null;
			} else {
				return sendEventToWidget(focusedWidget, event);
			}
		}
		return false;
	}

	/**
	 * Focuses the next enabled widget starting from the current focused one. If no widget currently owns the focus,
	 * starts from the first widget of the hierarchy tree.
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusNext() {
		Widget focusedWidget = this.focusedWidget;
		if (focusedWidget == null) {
			return focusFirst();
		} else {
			if (!focusNext(focusedWidget)) {
				return focusFirst();
			}
		}
		return false;
	}

	/**
	 * Focuses the first enabled widget using a depth-first algorithm.
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusFirst() {
		Widget widget = getDesktop().getWidget();
		if (widget != null) {
			return focusFirstIn(widget);
		}
		return false;
	}

	/**
	 * Focuses the first enabled widget in the given widget.
	 * <p>
	 * <ol>
	 * <li>If the given widget is a container, its first enabled child is focused (using a depth-first algorithm).</li>
	 * <li>Otherwise if the widget is enabled, it is focused.</li>
	 * </ol>
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusFirstIn(Widget widget) {
		if (widget instanceof Container) {
			Container container = (Container) widget;
			return focusNext(container, -1);
		} else if (isFocusable(widget)) {
			focus(widget);
			return true;
		}
		return false;
	}

	/**
	 * Focuses the next enabled sibling of the given widget.
	 * <p>
	 * <ol>
	 * <li>First, by searching in the direct siblings of the given widget (using a depth-first algorithm).</li>
	 * <li>Otherwise recursively in the parent containers.</li>
	 * </ol>
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusNext(Widget widget) {
		Container parent = widget.getParent();
		if (parent != null) {
			int index = parent.getChildIndex(widget);
			if (focusNext(parent, index)) {
				return true;
			}
			return focusNext(parent);
		}
		return false;
	}

	/**
	 * Focuses the first enabled child of the given container starting from the given index.
	 * <p>
	 * By searching only in the direct siblings of the given widget (using a depth-first algorithm).
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusNext(Container container, int from) {
		int childrenCount = container.getChildrenCount();
		for (int i = from + 1; i < childrenCount; i++) {
			Widget widget = container.getChild(i);
			assert widget != null;
			if (focusFirstIn(widget)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Focuses the previous enabled widget starting from the current focused one. If no widget currently owns the focus,
	 * starts from the last widget of the hierarchy tree.
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusPrevious() {
		Widget focusedWidget = this.focusedWidget;
		if (focusedWidget == null) {
			return focusLast();
		} else {
			if (!focusPrevious(focusedWidget)) {
				return focusLast();
			}
		}
		return false;
	}

	/**
	 * Focuses the last enabled widget using a depth-first algorithm.
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusLast() {
		Widget widget = getDesktop().getWidget();
		if (widget != null) {
			return focusLastIn(widget);
		}
		return false;
	}

	/**
	 * Focuses the last enabled widget in the given widget.
	 * <p>
	 * <ol>
	 * <li>If the given widget is a container, its first enabled child is focused (browsing from last to first, using a
	 * depth-first algorithm).</li>
	 * <li>Otherwise if the widget is enabled, it is focused.</li>
	 * </ol>
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusLastIn(Widget widget) {
		if (widget instanceof Container) {
			Container container = (Container) widget;
			return focusPrevious(container, container.getChildrenCount());
		} else if (isFocusable(widget)) {
			focus(widget);
			return true;
		}
		return false;
	}

	/**
	 * Focuses the previous enabled sibling of the given widget.
	 * <p>
	 * <ol>
	 * <li>First, by searching in the direct siblings of the given widget (using a depth-first algorithm).</li>
	 * <li>Otherwise recursively in the parent containers.</li>
	 * </ol>
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusPrevious(Widget widget) {
		Container parent = widget.getParent();
		if (parent != null) {
			int index = parent.getChildIndex(widget);
			if (focusPrevious(parent, index)) {
				return true;
			}
			return focusPrevious(parent);
		}
		return false;
	}

	/**
	 * Focuses the last enabled child of the given container starting from the given index.
	 * <p>
	 * By searching only in the direct siblings of the given widget (browsing from last to first, using a depth-first
	 * algorithm).
	 *
	 * @return <code>true</code> if a new widget has been focused, <code>false</code> otherwise.
	 */
	private boolean focusPrevious(Container container, int from) {
		for (int i = from - 1; i >= 0; i--) {
			Widget widget = container.getChild(i);
			assert widget != null;
			if (focusLastIn(widget)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Focuses the given widget.
	 *
	 * @param widget
	 *            the widget to focus.
	 */
	private void focus(@Nullable Widget widget) {
		Widget previousFocus = this.focusedWidget;
		if (widget != previousFocus) {
			this.focusedWidget = widget;

			if (previousFocus != null) {
				sendEventToWidget(previousFocus, buildFocusEvent(FOCUS_LOST));
			}

			if (widget != null) {
				sendEventToWidget(widget, buildFocusEvent(FOCUS_GAINED));
			}
		}
	}

	/**
	 * Focuses the given widget.
	 *
	 * @param widget
	 *            the widget to focus.
	 * @throws IllegalArgumentException
	 *             if the given widget does not belong to the desktop managed by this dispatcher.
	 */
	public void requestFocus(Widget widget) {
		if (!getDesktop().containsWidget(widget)) {
			throw new IllegalArgumentException();
		}
		focus(widget);
	}

	private static boolean isFocusable(Widget widget) {
		return (widget.isEnabled() && !(widget instanceof Joystick));
	}
}
