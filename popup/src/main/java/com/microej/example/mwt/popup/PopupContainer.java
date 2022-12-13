/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.popup;

import ej.annotation.Nullable;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 * Container containing a child that takes all the available space (i.e., the content) and a popup child that takes the
 * space it needs. The popup is centered horizontally and vertically within this container.
 * <p>
 * Its visibility is the default one (package) because it is only used by the popup desktop.
 */
class PopupContainer extends Container {

	@Nullable
	private Widget child;
	@Nullable
	private Widget popup;

	void setChild(@Nullable Widget child) {
		Widget formerChild = this.child;
		if (formerChild != null) {
			removeChild(formerChild);
		}
		if (child != null) {
			addChild(child);
		}
		this.child = child;
	}

	void showPopup(Widget popup) {
		Widget formerPopup = this.popup;
		if (formerPopup != null) {
			removeChild(formerPopup);
		}
		addChild(popup);
		this.popup = popup;
	}

	void hidePopup(Widget popup) {
		if (this.popup == popup) {
			removeChild(popup);
			this.popup = null;
		}
	}

	@Nullable
	Widget getPopup() {
		return this.popup;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Widget child = this.child;
		if (child != null) {
			int width = size.getWidth();
			int height = size.getHeight();
			computeChildOptimalSize(child, width, height);
		}
		Widget popup = this.popup;
		if (popup != null) {
			computeChildOptimalSize(popup, NO_CONSTRAINT, NO_CONSTRAINT);
		}
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		Widget child = this.child;
		if (child != null) {
			layOutChild(child, 0, 0, contentWidth, contentHeight);
		}
		Widget popup = this.popup;
		if (popup != null) {
			int popupWidth = popup.getWidth();
			int popupHeight = popup.getHeight();
			int popupX = (contentWidth - popupWidth) / 2;
			int popupY = (contentHeight - popupHeight) / 2;
			layOutChild(popup, popupX, popupY, popupWidth, popupHeight);
		}
	}

}
