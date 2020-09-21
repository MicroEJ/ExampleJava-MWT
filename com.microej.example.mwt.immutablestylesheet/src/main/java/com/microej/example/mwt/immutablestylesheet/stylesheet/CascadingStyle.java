/*
 * Copyright 2019-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.immutablestylesheet.stylesheet;

import ej.annotation.Nullable;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.Style;

/*package*/ class CascadingStyle extends EditableStyle {

	/**
	 * Dimension field position in bit map.
	 */
	private static final int DIMENSION_SHIFT = 0;
	/**
	 * Horizontal alignment field position in bit map.
	 */
	private static final int HORIZONTAL_ALIGNMENT_SHIFT = 1;
	/**
	 * Vertical alignment field position in bit map.
	 */
	private static final int VERTICAL_ALIGNMENT_SHIFT = 2;
	/**
	 * Margin field position in bit map.
	 */
	private static final int MARGIN_SHIFT = 3;
	/**
	 * Border field position in bit map.
	 */
	private static final int BORDER_SHIFT = 4;
	/**
	 * Padding field position in bit map.
	 */
	private static final int PADDING_SHIFT = 5;
	/**
	 * Background field position in bit map.
	 */
	private static final int BACKGROUND_SHIFT = 6;
	/**
	 * Color field position in bit map.
	 */
	private static final int COLOR_SHIFT = 7;
	/**
	 * Font field position in bit map.
	 */
	private static final int FONT_SHIFT = 8;
	/**
	 * Extra fields position in bit map.
	 */
	private static final int EXTRA_FIELDS_SHIFT = 9;
	/**
	 * Map value of all fields which can be inherited.
	 */
	private static final int INHERIT_MASK = (0x1 << HORIZONTAL_ALIGNMENT_SHIFT) | (0x1 << COLOR_SHIFT)
			| (0x1 << FONT_SHIFT);
	/**
	 * Map value of all extra fields.
	 */
	private static final int EXTRA_FIELDS_MAP = ~((0x1 << DIMENSION_SHIFT) | (0x1 << HORIZONTAL_ALIGNMENT_SHIFT)
			| (0x1 << VERTICAL_ALIGNMENT_SHIFT) | (0x1 << MARGIN_SHIFT) | (0x1 << BORDER_SHIFT) | (0x1 << PADDING_SHIFT)
			| (0x1 << BACKGROUND_SHIFT) | (0x1 << COLOR_SHIFT) | (0x1 << FONT_SHIFT));

	/**
	 * Bit map for set fields.
	 */
	private short map;
	/**
	 * Cached hash code.
	 */
	private short hashCode;

	/* package */ CascadingStyle(ImmutableStyle style) {
		super();
		setDimension(style.dimension);
		setHorizontalAlignment(style.horizontalAlignment);
		setVerticalAlignment(style.verticalAlignment);
		setMargin(style.margin);
		setBorder(style.border);
		setPadding(style.padding);
		setBackground(style.background);
		setColor(style.color);
		setFont(style.fontProvider.getFont());
		for (int fieldId = style.extraFields.length - 1; fieldId >= 0; fieldId--) {
			Object fieldValue = style.extraFields[fieldId];
			if (fieldValue != null) {
				setExtraObject(fieldId, fieldValue);
			}
		}
	}

	/**
	 * Fills all missing attributes of this style with those in the given style.
	 *
	 * @param style
	 *            the style to merge with.
	 */
	/* package */ void merge(ImmutableStyle style) {
		int map = this.map;
		int styleMap = style.map;
		int computedMap = ~map & styleMap;
		if (computedMap != 0) {
			merge(style, computedMap);
		}
	}

	private void merge(ImmutableStyle style, int mergeMap) {
		if ((mergeMap & (0x1 << DIMENSION_SHIFT)) != 0x0) {
			setDimension(style.dimension);
		}
		if ((mergeMap & (0x1 << HORIZONTAL_ALIGNMENT_SHIFT)) != 0x0) {
			setHorizontalAlignment(style.horizontalAlignment);
		}
		if ((mergeMap & (0x1 << VERTICAL_ALIGNMENT_SHIFT)) != 0x0) {
			setVerticalAlignment(style.verticalAlignment);
		}
		if ((mergeMap & (0x1 << MARGIN_SHIFT)) != 0x0) {
			setMargin(style.margin);
		}
		if ((mergeMap & (0x1 << BORDER_SHIFT)) != 0x0) {
			setBorder(style.border);
		}
		if ((mergeMap & (0x1 << PADDING_SHIFT)) != 0x0) {
			setPadding(style.padding);
		}
		if ((mergeMap & (0x1 << BACKGROUND_SHIFT)) != 0x0) {
			setBackground(style.background);
		}
		if ((mergeMap & (0x1 << COLOR_SHIFT)) != 0x0) {
			setColor(style.color);
		}
		if ((mergeMap & (0x1 << FONT_SHIFT)) != 0x0) {
			setFont(style.fontProvider.getFont());
		}
		if ((mergeMap & EXTRA_FIELDS_MAP) != 0x0) {
			int extraFieldsMergeMap = (mergeMap >> EXTRA_FIELDS_SHIFT);
			// iterate in reverse order to make sure that the extra fields array is resized only once
			for (int fieldId = MAX_EXTRA_FIELDS - 1; fieldId >= 0; fieldId--) {
				if ((extraFieldsMergeMap & (0x1 << fieldId)) != 0x0) {
					Object fieldValue = style.extraFields[fieldId];
					assert (fieldValue != null);
					setExtraObject(fieldId, fieldValue);
				}
			}
		}
		this.map |= mergeMap;
	}

	/**
	 * Fills all missing attributes of this style with the inherited ones from the given style.
	 *
	 * @param parentStyle
	 *            the style of the parent.
	 */
	/* package */ void inheritMerge(Style parentStyle) {
		int map = this.map;
		if ((map & (0x1 << HORIZONTAL_ALIGNMENT_SHIFT)) == 0x0) {
			setHorizontalAlignment((byte) parentStyle.getHorizontalAlignment());
		}
		if ((map & (0x1 << COLOR_SHIFT)) == 0x0) {
			setColor(parentStyle.getColor());
		}
		if ((map & (0x1 << FONT_SHIFT)) == 0x0) {
			setFont(parentStyle.getFont());
		}
		this.map |= INHERIT_MASK;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		return super.equals(obj);
	}

	/* package */ void updateHashCode() {
		this.hashCode = (short) super.hashCode();
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}
}
