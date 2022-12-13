/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.dragndrop;

import ej.annotation.Nullable;
import ej.basictool.ArrayTools;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.MicroUI;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.linear.LinearFunction;
import ej.mwt.Widget;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.widget.container.Grid;
import ej.widget.container.LayoutOrientation;
import ej.widget.util.motion.MotionAnimation;
import ej.widget.util.motion.MotionAnimationListener;

/**
 * A grid with drag and drop support.
 */
public class DragNDropGrid extends Grid {

	/** The delay before trying to drag the selected widget. */
	private static final int PRESS_DELAY = 500;
	/** The delay before a slot is selected as the destination for the drop. */
	private static final int DESTINATION_DELAY = 100;
	/** The duration of the animation when widgets are reordered. */
	private static final int MOTION_DURATION = 200;
	/** Max value of the motion. */
	private static final int MOTION_MAX_VALUE = 1000;

	private final Timer timer;
	private final int draggingClassSelector;

	private Position[] expectedPositions;

	private boolean pressed;
	private boolean dragging;
	@Nullable
	private Widget dragged;
	private int draggedIndex;

	@Nullable
	private MotionAnimation animation;
	@Nullable
	private TimerTask task;

	/**
	 * Creates an horizontal grid, given a number of columns.
	 *
	 * @param timer
	 *            the timer used to trigger some actions.
	 * @param columns
	 *            the number of columns of the grid.
	 * @param draggingClassSelector
	 *            the class selector applied on the dragged widget.
	 */
	public DragNDropGrid(Timer timer, int columns, int draggingClassSelector) {
		super(LayoutOrientation.HORIZONTAL, columns);
		this.timer = timer;
		this.draggingClassSelector = draggingClassSelector;
		this.expectedPositions = new Position[0];
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		super.layOutChildren(contentWidth, contentHeight);
		int childrenCount = getChildrenCount();
		this.expectedPositions = new Position[childrenCount];
		Widget[] children = getChildren();
		for (int i = 0; i < childrenCount; i++) {
			Widget child = children[i];
			this.expectedPositions[i] = new Position(child.getX(), child.getY());
		}
	}

	@Override
	public boolean handleEvent(int event) {
		final int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			final Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX() - getAbsoluteX() - getContentX();
			int pointerY = pointer.getY() - getAbsoluteY() - getContentY();
			int action = Buttons.getAction(event);
			switch (action) {
			case Buttons.PRESSED:
				onPointerPressed(pointerX, pointerY);
				break;
			case Pointer.DRAGGED:
				return onPointerDragged(pointerX, pointerY);
			case Buttons.RELEASED:
				onPointerReleased();
				break;
			}
		} else if (!this.dragging && type == DesktopEventGenerator.EVENT_TYPE
				&& Event.getData(event) == PointerEventDispatcher.EXITED) {
			cancelTask();
			stopAnimation();
			stopDragging();
		}
		return false;
	}

	private void onPointerPressed(final int pointerX, final int pointerY) {
		this.task = new TimerTask() {
			@Override
			public void run() {
				tryToDrag(pointerX, pointerY);
			}
		};
		this.timer.schedule(this.task, PRESS_DELAY);
	}

	private void tryToDrag(final int pointerX, final int pointerY) {
		final Widget[] children = getChildren();
		for (Widget child : children) {
			if (child.contains(pointerX, pointerY)) {
				// The user clicked on an element.
				this.pressed = true;

				this.draggedIndex = getChildIndex(child);
				this.dragged = child;

				child.addClassSelector(this.draggingClassSelector);
				child.updateStyle();

				// Move the child at the end in order to have it above the others.
				super.changeChildIndex(child, children.length - 1);
				System.arraycopy(this.expectedPositions, this.draggedIndex + 1, this.expectedPositions,
						this.draggedIndex, children.length - this.draggedIndex - 1);

				dragWidgetOnPointer(pointerX, pointerY);
				break;
			}
		}
	}

	private void dragWidgetOnPointer(int pointerX, int pointerY) {
		Widget dragged = this.dragged;
		assert dragged != null;
		// Align the center of the widget on the pointer.
		dragged.setPosition(pointerX - (dragged.getWidth() / 2), pointerY - (dragged.getHeight() / 2));
		requestRender();
	}

	private boolean onPointerDragged(final int pointerX, final int pointerY) {
		cancelTask();
		if (this.pressed) {
			this.dragging = true;
			dragWidgetOnPointer(pointerX, pointerY);
			this.task = new TimerTask() {
				@Override
				public void run() {
					MicroUI.callSerially(new Runnable() {
						@Override
						public void run() {
							insertWidgetAtCurrentPosition(pointerX, pointerY);
						}
					});
				}
			};
			this.timer.schedule(this.task, DESTINATION_DELAY);
			return true;
		}
		return false;
	}

	private void insertWidgetAtCurrentPosition(int pointerX, int pointerY) {
		Widget[] children = getChildren();
		for (Widget child : children) {
			if (child != DragNDropGrid.this.dragged && child.contains(pointerX, pointerY)) {
				// select this element as the destination
				updatePositions(child);
				break;
			}
		}
	}

	private void updatePositions(Widget destination) {
		reorderChildren(destination);
		setPositionsWhileDragging();
		Motion motion = new Motion(LinearFunction.INSTANCE, 0, MOTION_MAX_VALUE, MOTION_DURATION);
		MotionAnimation animation = new MotionAnimation(getDesktop().getAnimator(), motion,
				new MotionAnimationListener() {
					@Override
					public void tick(int value, boolean finished) {
						animateItems(value);
					}
				});
		animation.start();
		this.animation = animation;
	}

	private void animateItems(int value) {
		int childrenCount = getChildrenCount();
		Position[] expectedPositions = this.expectedPositions;
		Widget[] children = getChildren();
		for (int i = 0; i < childrenCount - 1; i++) {
			Widget child = children[i];
			Position expectedPosition = expectedPositions[i];
			int expectedX = expectedPosition.x;
			int expectedY = expectedPosition.y;
			int childX = child.getX();
			int childY = child.getY();

			int moveX = getMove(expectedX, childX, value);
			int moveY = getMove(expectedY, childY, value);

			child.setPosition(childX + moveX, childY + moveY);
		}
		requestRender();
	}

	private static int getMove(int expectedCoordinate, int childCoordinate, int value) {
		int shift = expectedCoordinate - childCoordinate;
		if (value == MOTION_MAX_VALUE) {
			return shift;
		} else {
			return shift * value / (MOTION_MAX_VALUE * 2);
		}
	}

	private void stopAnimation() {
		final MotionAnimation animation = this.animation;
		if (animation != null) {
			animation.stop();
		}
	}

	private void reorderChildren(Widget destination) {
		Widget[] children = getChildren();
		int destIndex = ArrayTools.getIndex(children, destination);
		if (destIndex >= this.draggedIndex) {
			destIndex++;
		}

		this.draggedIndex = destIndex;
	}

	private void setPositionsWhileDragging() {
		int childrenCount = getChildrenCount();
		int columnsCount = getCount();
		int rowsCount = (childrenCount + columnsCount - 1) / columnsCount;
		int index = 0;
		for (int i = 0; i < childrenCount - 1; i++) {
			if (i == this.draggedIndex) {
				// Simulate the position of the dragged widget.
				index++;
			}
			int rowIndex = index / columnsCount;
			int columnIndex = index - rowIndex * columnsCount;
			int x = columnIndex * getContentWidth() / columnsCount;
			int y = rowIndex * getContentHeight() / rowsCount;
			this.expectedPositions[i] = new Position(x, y);
			index++;
		}
	}

	private void onPointerReleased() {
		cancelTask();
		stopAnimation();

		stopDragging();
	}

	private void stopDragging() {
		if (this.pressed) {
			Widget dragged = this.dragged;
			assert dragged != null;
			dragged.removeClassSelector(this.draggingClassSelector);
			dragged.updateStyle();

			// Restore the actual position of the dragged widget.
			super.changeChildIndex(dragged, this.draggedIndex);

			this.pressed = false;
			this.dragged = null;
			this.dragging = false;

			setPositions();
			requestRender();
		}
	}

	private void setPositions() {
		Widget[] children = getChildren();
		int childrenCount = getChildrenCount();
		int columnsCount = getCount();
		int rowsCount = (childrenCount + columnsCount - 1) / columnsCount;
		for (int i = 0; i < childrenCount; i++) {
			Widget child = children[i];
			int rowIndex = i / columnsCount;
			int columnIndex = i - rowIndex * columnsCount;
			int x = columnIndex * getContentWidth() / columnsCount;
			int y = rowIndex * getContentHeight() / rowsCount;
			child.setPosition(x, y);
			this.expectedPositions[i] = new Position(x, y);
		}
	}

	private void cancelTask() {
		final TimerTask task = this.task;
		if (task != null) {
			task.cancel();
			this.task = null;
		}
	}

	@Override
	protected void onHidden() {
		stopAnimation();
		cancelTask();
		super.onHidden();
	}

}

class Position {
	int x;
	int y;

	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
