/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.slidecontainer;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.microui.MicroUI;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseOutFunction;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.mwt.util.Size;
import ej.widget.util.motion.MotionAnimation;
import ej.widget.util.motion.MotionAnimationListener;

/**
 * A slide container holds several children.
 * <p>
 * Each child is the size of the content of the container. Hence, only the last added child is visible.
 * <p>
 * When adding a child, an animation is done by translating the departing child and the new one from the right of the
 * screen. When removing a child, an animation is done by translating the departing child and the new one to the right
 * of the screen.
 * <p>
 * The last added child can be removed by swiping it to the right of the screen.
 */
public class SlideContainer extends Container {

	private static final int TRANSITION_DURATION = 400;

	@Nullable
	private Animation draggedAnimation;
	@Nullable
	private MotionAnimation releasedAnimation;

	// Drag management.
	private boolean pressed;
	private boolean moving;
	private int previousX;
	private int previousY;

	// Rendering management.
	private int previousPosition;
	private int draggedPosition;
	private int position;

	/**
	 * Creates a slide container.
	 */
	public SlideContainer() {
		super(true);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * An animation is started to slide the widget from outside of the container on the right to its final position.
	 */
	@Override
	public void addChild(final Widget child) {
		// Stop any animation/refresh running.
		stopAnimations();

		super.addChild(child);

		if (isAttached()) {
			int childrenCount = getChildrenCount();
			if (childrenCount >= 2) {
				Widget formerChild = getChild(childrenCount - 2);
				setHiddenChild(formerChild);

				int contentWidth = getContentWidth();
				int contentHeight = getContentHeight();
				computeChildOptimalSize(child, contentWidth, contentHeight);
				layOutChild(child, contentWidth, 0, contentWidth, contentHeight);

				this.moving = true;
				showChild(formerChild, child, contentWidth);
			}
		}
	}

	@Override
	protected void setShownChildren() {
		int childrenCount = getChildrenCount();
		if (childrenCount >= 1) {
			Widget visibleChild = getChild(childrenCount - 1);
			setShownChild(visibleChild);
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		stopAnimations();
	}

	/**
	 * Removes the last added child.
	 * <p>
	 * An animation is started to slide the widget from its current position to the outside of the container on the
	 * right.
	 */
	public void removeLast() {
		stopAnimations();
		int childrenCount = getChildrenCount();
		if (childrenCount == 0) {
			return;
		}
		final Widget child = getChild(childrenCount - 1);
		if (childrenCount > 1) {
			this.moving = true;
			setHiddenChild(child);
			Widget newlyVisibleChild = getChild(childrenCount - 2);
			newlyVisibleChild.setPosition(-getContentWidth(), 0);
			hideChild(newlyVisibleChild, child, 0);
		}
	}

	/**
	 * Starts the animation translating the child from the given position to its final position (<code>0</code>).
	 *
	 * @param child
	 *            the child to move.
	 * @param startX
	 *            the initial position.
	 */
	private void showChild(Widget formerChild, Widget child, int startX) {
		this.previousPosition = startX;
		doAnimation(formerChild, child, startX, 0);
	}

	/**
	 * Starts the animation translating the child from the given position to outside of the container (content width).
	 *
	 * @param child
	 *            the child to move.
	 * @param startX
	 *            the initial position.
	 */
	private void hideChild(Widget newlyVisibleChild, Widget child, int startX) {
		removeChild(child);
		this.previousPosition = startX;
		doAnimation(newlyVisibleChild, child, startX, getContentWidth());
	}

	private void doAnimation(final Widget leftChild, final Widget rightChild, int startX, int endX) {
		// Save initial position for rendering.
		this.position = startX;
		// Compute duration depending on the distance to walk.
		long duration = TRANSITION_DURATION * Math.abs(endX - startX) / getContentWidth();
		Motion motion = new Motion(QuadEaseOutFunction.INSTANCE, startX, endX, duration);
		this.releasedAnimation = new MotionAnimation(getAnimator(), motion, new MotionAnimationListener() {
			@Override
			public void tick(int value, boolean finished) {
				updatePosition(value, leftChild, rightChild);
				if (finished) {
					restore();
				}
			}
		});
		this.releasedAnimation.start();
	}

	private void stopAnimations() {
		Animation draggedAnimation = this.draggedAnimation;
		if (draggedAnimation != null) {
			getAnimator().stopAnimation(draggedAnimation);
			draggedAnimation = null;
		}
		MotionAnimation releasedAnimation = this.releasedAnimation;
		if (releasedAnimation != null) {
			releasedAnimation.stop();
			restore();
		}
	}

	private void restore() {
		this.moving = false;
		int childrenCount = getChildrenCount();
		if (childrenCount >= 1) {
			// Restart any animation/refresh on the newly visible child.
			Widget newlyVisibleChild = getChild(childrenCount - 1);
			newlyVisibleChild.setPosition(0, 0);
			setShownChild(newlyVisibleChild);
			requestRender();
		}
	}

	@Override
	public void render(GraphicsContext g) {
		if (this.moving) {
			// The widget on top is moving.
			int contentX = getContentX();
			int contentY = getContentY();
			int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();
			// "Move" the display from the previous position to the new one.
			int shift = this.position - this.previousPosition;
			g.setClip(contentX, contentY, contentWidth, contentHeight);
			Painter.drawDisplayRegion(g, contentX, contentY, contentWidth - shift, contentHeight, contentX + shift,
					contentY);
			// Draws only the modified part of the container and its children.
			if (shift > 0) {
				// The widgets are moved to the right.
				// Draw the part of the left widget previously outside of the container.
				g.setClip(contentX, contentY, shift, contentHeight);
			} else {
				// The widgets are moved to the left.
				// Draw the part of the right widget previously outside of the container.
				g.setClip(contentX + contentWidth + shift, contentY, -shift, contentHeight);
			}
			super.render(g);
			this.previousPosition = this.position;
		} else {
			super.render(g);
		}
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		// Draw only last children, the others are not necessary.
		int childrenCount = getChildrenCount();
		if (this.moving && this.pressed && this.previousPosition < this.position && childrenCount > 1) {
			// Draw the penultimate widget when partially visible.
			int translateX = g.getTranslationX();
			int translateY = g.getTranslationY();
			int x = g.getClipX();
			int y = g.getClipY();
			int width = g.getClipWidth();
			int height = g.getClipHeight();

			Widget child = getChild(childrenCount - 2);
			renderChild(child, g);

			g.setTranslation(translateX, translateY);
			g.setClip(x, y, width, height);
		}
		if (childrenCount > 0) {
			// Draw the widget on top.
			Widget child = getChild(childrenCount - 1);
			renderChild(child, g);
		}
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		for (Widget child : getChildren()) {
			assert (child != null);
			layOutChild(child, 0, 0, contentWidth, contentHeight);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int widthHint = size.getWidth();
		int heightHint = size.getHeight();

		int width = 0;
		int height = 0;
		for (Widget child : getChildren()) {
			assert (child != null);

			// Compute child optimal size.
			computeChildOptimalSize(child, widthHint, heightHint);

			// Update container optimal size.
			width = Math.max(width, child.getWidth());
			height = Math.max(height, child.getHeight());
		}

		// Set container optimal size.
		size.setSize(width, height);
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX();
			int pointerY = pointer.getY();
			int contentWidth = getContentWidth();
			int childrenCount = getChildrenCount();
			switch (Buttons.getAction(event)) {
			case Buttons.PRESSED:
				onPointerPressed(pointerX, pointerY, childrenCount);
				break;
			case Pointer.DRAGGED:
				if (onPointerDragged(contentWidth, childrenCount, pointerX, pointerY)) {
					return true;
				}
				break;
			case Buttons.RELEASED:
				if (onPointerReleased(contentWidth, childrenCount)) {
					return true;
				}
				break;
			}
		} else if (Event.getType(event) == DesktopEventGenerator.EVENT_TYPE) {
			int action = DesktopEventGenerator.getAction(event);
			if (action == PointerEventDispatcher.EXITED) {
				this.pressed = false;
			}
		}
		return super.handleEvent(event);
	}

	private void onPointerPressed(int pointerX, int pointerY, final int childrenCount) {
		if (childrenCount > 1) {
			this.pressed = true;
			this.previousX = pointerX;
			this.previousY = pointerY;
			this.draggedPosition = this.position;
		} else {
			this.pressed = false;
		}
	}

	private Animator getAnimator() {
		return getDesktop().getAnimator();
	}

	private void updatePosition(int value, Widget leftChild, Widget rightChild) {
		this.position = value;
		leftChild.setPosition(value - getContentWidth(), 0);
		rightChild.setPosition(value, 0);
		requestRender();
	}

	private boolean onPointerDragged(int contentWidth, int childrenCount, int pointerX, int pointerY) {
		int shiftX = pointerX - this.previousX;
		if (this.pressed && shiftX != 0) {
			int shiftY = pointerY - this.previousY;
			final Widget lastChild = getChild(childrenCount - 1);
			if (!this.moving && Math.abs(shiftX) > Math.abs(shiftY) && shiftX > 0) {
				// Start to drag when moving horizontally.
				stopAnimations();
				final Widget leftChild = getChild(childrenCount - 2);
				Animation draggedAnimation = new Animation() {
					@Override
					public boolean tick(long platformTimeMillis) {
						int value = (SlideContainer.this.draggedPosition + SlideContainer.this.previousPosition) / 2;
						updatePosition(value, leftChild, lastChild);
						return SlideContainer.this.pressed;
					}
				};
				this.draggedAnimation = draggedAnimation;
				getAnimator().startAnimation(draggedAnimation);
				setHiddenChild(lastChild);
				this.moving = true;
				this.previousPosition = 0;
			}
			if (this.moving) {
				int childX = this.draggedPosition + shiftX;
				childX = XMath.limit(childX, 0, contentWidth);
				this.draggedPosition = childX;

				this.previousX = pointerX;
				this.previousY = pointerY;
				return true;
			}
		}
		return false;
	}

	private boolean onPointerReleased(final int contentWidth, final int childrenCount) {
		if (this.moving) {
			MicroUI.callSerially(new Runnable() {
				@Override
				public void run() {
					SlideContainer.this.pressed = false;
					Widget lastChild = getChild(childrenCount - 1);
					int childX = lastChild.getX();
					// Depending on the position of the child with the middle of the container, keep it or remove it.
					Widget penultimateChild = getChild(childrenCount - 2);
					if (childX < contentWidth / 2) {
						doAnimation(penultimateChild, lastChild, childX, 0);
					} else {
						removeChild(lastChild);
						doAnimation(penultimateChild, lastChild, childX, contentWidth);
					}
				}
			});
			return true;
		} else {
			this.pressed = false;
		}
		return false;
	}

}
