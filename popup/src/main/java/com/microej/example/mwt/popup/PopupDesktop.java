/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.popup;

import ej.annotation.Nullable;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.event.EventDispatcher;
import ej.mwt.event.PointerEventDispatcher;
import ej.mwt.render.OverlapRenderPolicy;
import ej.mwt.render.RenderPolicy;

/**
 * Desktop that can show popups.
 * <p>
 * It defines custom render policy and event dispatcher to manage popups.
 * <p>
 * The overlap render policy allows the popup to remain on top of the other elements. With the default render policy,
 * the progress bar would have been redrawn over it.
 * <p>
 * The custom event dispatcher is based on the {@link PointerEventDispatcher}. When no popup is shown it works the same
 * way as the pointer event dispatcher. But when a popup is shown, it only sends events to the widgets inside this
 * popup.
 */
public class PopupDesktop extends Desktop {

	private final PopupContainer container;
	private boolean information;

	/**
	 * Creates a popup desktop.
	 */
	public PopupDesktop() {
		this.container = new PopupContainer();
		super.setWidget(this.container);
	}

	@Override
	public void setWidget(@Nullable Widget child) {
		this.container.setChild(child);
	}

	/**
	 * Shows a popup on this container.
	 *
	 * An information popup can be dismissed by clicking outside its bounds, a standard popup must be closed explicitly
	 * by the user (usually with a button).
	 *
	 * @param popup
	 *            the popup.
	 * @param information
	 *            <code>true</code> if the popup is an information, <code>false</code> otherwise.
	 */
	public void showPopup(Widget popup, boolean information) {
		this.information = information;
		this.container.showPopup(popup);
		requestLayOut();
	}

	/**
	 * Hides a popup on this container.
	 *
	 * @param popup
	 *            the popup.
	 */
	public void hidePopup(Widget popup) {
		this.container.hidePopup(popup);
		requestLayOut();
	}

	/**
	 * Gets the popup shown on the desktop or <code>null</code>.
	 *
	 * @return the popup.
	 */
	public @Nullable Widget getPopup() {
		return this.container.getPopup();
	}

	/**
	 * Gets whether the open popup is an information one or not.
	 * <p>
	 * If no popup is open, the result is undetermined.
	 *
	 * @return <code>true</code> if the popup is an information, <code>false</code> otherwise.
	 */
	public boolean isInformationPopup() {
		return this.information;
	}

	@Override
	protected RenderPolicy createRenderPolicy() {
		return new OverlapRenderPolicy(this);
	}

	@Override
	protected EventDispatcher createEventDispatcher() {
		return new PopupEventDispatcher(this);
	}
}
