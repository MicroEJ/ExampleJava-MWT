/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.widget;

import com.is2t.example.mwt.transition.Area;
import com.is2t.example.mwt.transition.TransitionDrawer;

import ej.microui.io.Display;
import ej.mwt.Composite;
import ej.mwt.Widget;

/**
 * Implementation of {@link TransitionDrawer} full widget based.
 */
public class DrawTransitionComposite extends Composite implements TransitionDrawer {

	private Widget widget;
	private Widget newWidget;
	private int x1;
	private int y1;
	private int width1;
	private int height1;
	private int x2;
	private int y2;
	private int width2;
	private int height2;

	/**
	 * Creates a DrawTransitionComposite.
	 * 
	 * @param widget
	 *            the initial visible widget.
	 */
	public DrawTransitionComposite(Widget widget) {
		this.widget = widget;
		add(widget);
	}

	@Override
	public void show(Widget newWidget) {
		this.newWidget = newWidget;
		this.x1 = 0;
		this.y1 = 0;
		this.width1 = getWidth();
		this.height1 = getHeight();
		// hide new widget while not moved
		this.width2 = 0;
		this.height2 = 0;
		add(newWidget);
	}

	@Override
	public void move(final Area oldWidgetArea, final Area newWidgetArea) {
		// Need to synchronize to avoid graphic artifacts.
		Display.getDefaultDisplay().callSerially(new Runnable() {
			@Override
			public void run() {
				moveInternal(oldWidgetArea, newWidgetArea);
			}
		});
		revalidate();
	}

	/**
	 * Must be called in the display context.
	 */
	private void moveInternal(Area oldWidgetArea, Area newWidgetArea) {
		this.x1 = oldWidgetArea.getX();
		this.y1 = oldWidgetArea.getY();
		this.width1 = oldWidgetArea.getWidth();
		this.height1 = oldWidgetArea.getHeight();
		this.x2 = newWidgetArea.getX();
		this.y2 = newWidgetArea.getY();
		this.width2 = newWidgetArea.getWidth();
		this.height2 = newWidgetArea.getHeight();
	}

	@Override
	public void stop() {
		// Need to synchronize to avoid graphic artifacts.
		Display.getDefaultDisplay().callSerially(new Runnable() {
			@Override
			public void run() {
				stopInternal();
			}
		});
	}

	/**
	 * Must be called in the display context.
	 */
	private void stopInternal() {
		Widget oldWidget = this.widget;
		Widget newWidget = this.newWidget;
		this.widget = newWidget;
		this.newWidget = null;
		this.x1 = this.x2;
		this.y1 = this.y2;
		this.width1 = this.width2;
		this.height1 = this.height2;
		remove(oldWidget);
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		setPreferredSize(widthHint, heightHint);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (this.newWidget == null) {
			// not in a transition
			this.widget.setBounds(0, 0, width, height);
		} else {
			this.widget.setBounds(this.x1, this.y1, this.width1, this.height1);
			this.newWidget.setBounds(this.x2, this.y2, this.width2, this.height2);
		}
	}

}