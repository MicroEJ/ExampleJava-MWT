/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.widget;

import com.is2t.example.mwt.transition.Area;
import com.is2t.example.mwt.transition.TransitionDrawer;
import com.is2t.example.mwt.util.DrawHierarchy;

import ej.microui.io.Display;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.mwt.Composite;
import ej.mwt.Widget;

/**
 * Implementation of {@link TransitionDrawer} screenshot based.
 */
public class ScreenshotTransitionComposite extends Composite implements TransitionDrawer {

	private Image widgetImage;
	private Image newWidgetImage;
	private Widget widget;
	private Widget newWidget;
	private int x1;
	private int y1;
	private int x2;
	private int y2;

	/**
	 * Creates a DrawTransitionComposite.
	 * 
	 * @param widget
	 *            the initial visible widget.
	 */
	public ScreenshotTransitionComposite(Widget widget) {
		this.widget = widget;
		add(widget);
	}

	@Override
	public void show(final Widget newWidget) {
		this.newWidget = newWidget;
		this.x1 = 0;
		this.y1 = 0;
		// put new widget out of screen while not moved
		this.x2 = getWidth();
		this.newWidgetImage = createImage(newWidget);
		Widget widget = this.widget;
		this.widgetImage = createImage(widget);
		this.widget = null;
		remove(widget);
	}

	private Image createImage(Widget widget) {
		// can be optimized by creating images only once
		int widgetWidth = getWidth();
		int widgetHeight = getHeight();
		widget.setSize(widgetWidth, widgetHeight);
		Image widgetImage = Image.createImage(widgetWidth, widgetHeight);
		DrawHierarchy.draw(widgetImage.getGraphicsContext(), widget);
		return widgetImage;
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
		this.x2 = newWidgetArea.getX();
		this.y2 = newWidgetArea.getY();
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
		this.widget = this.newWidget;
		this.newWidget = null;
		this.widgetImage = null;
		this.newWidgetImage = null;
		this.x1 = this.x2;
		this.y1 = this.y2;
		add(this.widget);
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		setPreferredSize(widthHint, heightHint);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (this.widget != null) {
			// not in transition
			this.widget.setBounds(0, 0, width, height);
		}
	}

	/**
	 * Renders the widget in the given graphic context.
	 * 
	 * @param g
	 *            the graphic context used to render the widget.
	 */
	public void paint(GraphicsContext g) {
		if (this.widget == null) {
			g.drawImage(this.widgetImage, this.x1, this.y1, GraphicsContext.LEFT | GraphicsContext.TOP);
			g.drawImage(this.newWidgetImage, this.x2, this.y2, GraphicsContext.LEFT | GraphicsContext.TOP);
		}
	}
}
