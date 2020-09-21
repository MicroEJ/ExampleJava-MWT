/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.lazystylesheet.style;

import ej.annotation.Nullable;
import ej.basictool.ArrayTools;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
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
 * <li>merge widget with matching selectors rules (set by {@link #setSelectorStyle(Selector, StyleFactory)}),</li>
 * <li>recursively merge with inherited attributes of parents (repeat previous step for parent recursively) (see
 * {@link Widget#getParent()}),</li>
 * <li>merge global style (set by {@link #getDefaultStyle()}).</li>
 * </ol>
 * The merge consists in completing the result style with all the set attributes of another style (see {@link Style}).
 * The result style is complete at the end of the resolution.
 */
public class CascadingLazyStylesheet implements Stylesheet {

	private static class SelectorStyle {

		private final Selector selector;
		private final StyleFactory factory;

		private SelectorStyle(Selector selector, StyleFactory style) {
			this.selector = selector;
			this.factory = style;
		}
	}

	private static final int CACHE_SIZE = 10;

	/**
	 * Default style.
	 */
	private EditableStyle defaultStyle;
	/**
	 * Rules.
	 */
	// The list ensures the definition order.
	private SelectorStyle[] selectorsStyles;
	/**
	 * Cached styles.
	 */
	private final CascadingStyle[] stylesCache;
	private byte stylesCount;

	/**
	 * Creates a new cascading stylesheet.
	 */
	public CascadingLazyStylesheet() {
		// Equivalent of #reset()
		this.defaultStyle = new EditableStyle();
		this.selectorsStyles = new SelectorStyle[0];
		this.stylesCache = new CascadingStyle[CACHE_SIZE];
	}

	@Override
	public Style getStyle(Widget widget) {
		CascadingStyle resultingStyle = computeStyle(widget);

		return getCachedStyle(resultingStyle);
	}

	private CascadingStyle computeStyle(Widget widget) {
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
				selectorStyle.factory.applyOn(resultingStyle);
			}
		}
	}

	/**
	 * Search a style in the cache to avoid having several instances of the same style.
	 * <p>
	 * If an equal style is available, it is returned, otherwise the given style is cached.
	 *
	 * @param style
	 *            the style to search.
	 * @return a cached style.
	 */
	private Style getCachedStyle(CascadingStyle style) {
		CascadingStyle result = style;
		CascadingStyle[] stylesCache = this.stylesCache;
		byte stylesCount = this.stylesCount;
		for (int i = 0; i < stylesCount; i++) {
			CascadingStyle cachedStyle = stylesCache[i];
			assert cachedStyle != null;
			if (style.hashCode() == cachedStyle.hashCode() && style.equals(cachedStyle)) {
				if (i != 0) {
					// Bring this style forward.
					stylesCache[i] = stylesCache[i - 1];
					stylesCache[i - 1] = cachedStyle;
				}
				return cachedStyle;
			}
		}
		// Not found add it at the center.
		int half = stylesCount >> 1;
		System.arraycopy(stylesCache, half, stylesCache, half + 1,
				Math.min(stylesCount, stylesCache.length - 1) - half);
		stylesCache[half] = style;
		if (stylesCount != stylesCache.length) {
			++stylesCount;
		}
		this.stylesCount = stylesCount;
		return result;
	}

	/**
	 * Gets the default style. The style can be modified.
	 * <p>
	 * This style is used as the root style of the cascading resolution.
	 * <p>
	 * This method should be called in the display thread to avoid concurrency issues.
	 *
	 * @return the style setter to fill.
	 * @throws NullPointerException
	 *             if the given style is <code>null</code>.
	 */
	public EditableStyle getDefaultStyle() {
		return this.defaultStyle;
	}

	/**
	 * Resets the default style attributes to their initial value.
	 * <p>
	 * This method should be called in the display thread to avoid concurrency issues.
	 */
	public void resetDefaultStyle() {
		this.defaultStyle = new EditableStyle();
	}

	/**
	 * Gets the style for a selector. The style can be modified.
	 * <p>
	 * This style is applied to the widgets matching the selector.
	 * <p>
	 * This method should be called in the display thread to avoid concurrency issues.
	 *
	 * @param selector
	 *            the selector.
	 * @param factory
	 *            the style factory associated with the selector.
	 * @throws NullPointerException
	 *             if a parameter is <code>null</code>.
	 */
	public void setSelectorStyle(Selector selector, StyleFactory factory) {
		// Get the array in local to avoid synchronization (with add and remove).
		SelectorStyle[] selectorsStyles = this.selectorsStyles;
		SelectorStyle selectorStyle = new SelectorStyle(selector, factory);
		// TODO manage several factories with the same selector?
		int index = getIndex(selector, selectorsStyles);
		// Add the selector at the first place to ensure order (last added is resolved first).
		this.selectorsStyles = ArrayTools.insert(selectorsStyles, index, selectorStyle);
	}

	/**
	 * Resets the style attributes for a selector.
	 * <p>
	 * This method should be called in the display thread to avoid concurrency issues.
	 *
	 * @param selector
	 *            the selector.
	 * @throws NullPointerException
	 *             if the given selector is <code>null</code>.
	 */
	public void resetSelectorStyle(Selector selector) {
		// Get the array in local to avoid synchronization (with add and remove).
		SelectorStyle[] selectorsStyles = this.selectorsStyles;
		SelectorStyle selectorStyle = getSelectorStyle(selector, selectorsStyles);
		if (selectorStyle != null) {
			this.selectorsStyles = ArrayTools.remove(selectorsStyles, selectorStyle);
		}
	}

	/**
	 * Resets the stylesheet to its initial state.
	 */
	public void reset() {
		// Duplicated in the constructor.
		this.defaultStyle = new EditableStyle();
		this.selectorsStyles = new SelectorStyle[0];
		// don't reset cache, it can't be done without synchronization
	}

	private int getIndex(Selector selector, SelectorStyle[] selectorsStyles) {
		int specificity = selector.getSpecificity();
		int minIndex = 0;
		int maxIndex = selectorsStyles.length;
		while (maxIndex - minIndex > 1) {
			int currentIndex = (maxIndex - minIndex) / 2 + minIndex;
			if (isMoreSpecific(selectorsStyles, currentIndex, specificity)) {
				minIndex = currentIndex;
			} else {
				maxIndex = currentIndex;
			}
		}
		if (maxIndex > minIndex) {
			if (isMoreSpecific(selectorsStyles, minIndex, specificity)) {
				return maxIndex;
			} else {
				return minIndex;
			}
		}
		return minIndex;
	}

	private boolean isMoreSpecific(SelectorStyle[] selectorsStyles, int index, int specificity) {
		int specificityCandidate = selectorsStyles[index].selector.getSpecificity();
		// If equals, the first added remains first.
		return specificityCandidate > specificity;
	}

	@Nullable
	private SelectorStyle getSelectorStyle(Selector selector, SelectorStyle[] selectorsStyles) {
		for (SelectorStyle selectorStyle : selectorsStyles) {
			if (selector.equals(selectorStyle.selector)) {
				return selectorStyle;
			}
		}
		return null;
	}

}
