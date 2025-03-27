/*
 * Copyright 2015-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.immutablestylesheet.stylesheet;

import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.selector.Selector;

/**
 * Cascading stylesheet implementation strongly inspired by CSS.
 * <p>
 * This stylesheet contains:
 * <ul>
 * <li>a default style that defines all the attributes,</li>
 * <li>a set of rules with a selector and a partial style.</li>
 * </ul>
 * <p>
 * The style of a widget is determined following these steps:
 * <ol>
 * <li>create an empty result style (no attributes set),</li>
 * <li>merge widget with matching selectors rules,</li>
 * <li>recursively merge with inherited attributes of parents (repeat previous step for parent recursively) (see
 * {@link Widget#getParent()}),</li>
 * <li>merge global style.</li>
 * </ol>
 * The merge consists in completing the result style with all the set attributes of another style (see {@link Style}).
 * The result style is complete at the end of the resolution.
 */
public class CascadingStylesheet implements Stylesheet {

	private static class SelectorStyle {

		private final Selector selector;
		private final ImmutableStyle style;

		private SelectorStyle(SelectorStyle selectorStyle) { // NOSONAR Never used because instances are immutable but
																// necessary to compile.
			this.selector = selectorStyle.selector;
			this.style = selectorStyle.style;
		}
	}

	private final ImmutableStyle defaultStyle;
	private final SelectorStyle[] selectorsStyles;

	private CascadingStylesheet(CascadingStylesheet stylesheet) { // NOSONAR Never used because instances are immutable
																	// but necessary to compile.
		this.defaultStyle = stylesheet.defaultStyle;
		this.selectorsStyles = stylesheet.selectorsStyles;
	}

	@Override
	public Style getStyle(Widget widget) {
		CascadingStyle resultingStyle = new CascadingStyle(this.defaultStyle);

		// Global style selectors only.
		mergeSelectors(widget, resultingStyle);

		// Merge with parents inherited attributes.
		Widget parentWidget = widget.getParent();
		if (parentWidget != null) {
			Style parentStyle = parentWidget.getStyle();
			resultingStyle.inheritMerge(parentStyle);
		}

		resultingStyle.updateHashCode();
		return resultingStyle;
	}

	private void mergeSelectors(Widget widget, CascadingStyle resultingStyle) {
		for (SelectorStyle selectorStyle : this.selectorsStyles) {
			if (selectorStyle.selector.appliesToWidget(widget)) {
				resultingStyle.merge(selectorStyle.style);
			}
		}
	}
}
