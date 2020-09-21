/*
 * Copyright 2019-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.lazystylesheet.style;

import ej.annotation.Nullable;
import ej.microui.display.Font;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.Style;
import ej.mwt.style.background.Background;
import ej.mwt.style.dimension.Dimension;
import ej.mwt.style.outline.Outline;

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
	 * Bit map for set fields.
	 */
	private short map;
	/**
	 * Cached hash code.
	 */
	private short hashCode;

	/* package */ CascadingStyle(EditableStyle style) {
		super(style);
	}

	@Override
	public void setDimension(Dimension dimension) {
		if ((this.map & (0x1 << DIMENSION_SHIFT)) == 0x0) {
			super.setDimension(dimension);
			this.map |= 0x1 << DIMENSION_SHIFT;
		}
	}

	@Override
	public void setHorizontalAlignment(int horizontalAlignment) {
		if ((this.map & (0x1 << HORIZONTAL_ALIGNMENT_SHIFT)) == 0x0) {
			super.setHorizontalAlignment(horizontalAlignment);
			this.map |= 0x1 << HORIZONTAL_ALIGNMENT_SHIFT;
		}
	}

	@Override
	public void setVerticalAlignment(int verticalAlignment) {
		if ((this.map & (0x1 << VERTICAL_ALIGNMENT_SHIFT)) == 0x0) {
			super.setVerticalAlignment(verticalAlignment);
			this.map |= 0x1 << VERTICAL_ALIGNMENT_SHIFT;
		}
	}

	@Override
	public void setMargin(Outline margin) {
		if ((this.map & (0x1 << MARGIN_SHIFT)) == 0x0) {
			super.setMargin(margin);
			this.map |= 0x1 << MARGIN_SHIFT;
		}
	}

	@Override
	public void setBorder(Outline border) {
		if ((this.map & (0x1 << BORDER_SHIFT)) == 0x0) {
			super.setBorder(border);
			this.map |= 0x1 << BORDER_SHIFT;
		}
	}

	@Override
	public void setPadding(Outline padding) {
		if ((this.map & (0x1 << PADDING_SHIFT)) == 0x0) {
			super.setPadding(padding);
			this.map |= 0x1 << PADDING_SHIFT;
		}
	}

	@Override
	public void setBackground(Background background) {
		if ((this.map & (0x1 << BACKGROUND_SHIFT)) == 0x0) {
			super.setBackground(background);
			this.map |= 0x1 << BACKGROUND_SHIFT;
		}
	}

	@Override
	public void setColor(int color) {
		if ((this.map & (0x1 << COLOR_SHIFT)) == 0x0) {
			super.setColor(color);
			this.map |= 0x1 << COLOR_SHIFT;
		}
	}

	@Override
	public void setFont(Font font) {
		if ((this.map & (0x1 << FONT_SHIFT)) == 0x0) {
			super.setFont(font);
			this.map |= 0x1 << FONT_SHIFT;
		}
	}

	@Override
	public void setExtraObject(int fieldId, Object fieldValue) {
		if ((this.map & (0x1 << (EXTRA_FIELDS_SHIFT + fieldId))) == 0x0) {
			super.setExtraObject(fieldId, fieldValue);
			this.map |= 0x1 << (EXTRA_FIELDS_SHIFT + fieldId);
		}
	}

	@Override
	public void setExtraInt(int fieldId, int fieldValue) {
		if ((this.map & (0x1 << (EXTRA_FIELDS_SHIFT + fieldId))) == 0x0) {
			super.setExtraInt(fieldId, fieldValue);
			this.map |= 0x1 << (EXTRA_FIELDS_SHIFT + fieldId);
		}
	}

	@Override
	public void setExtraFloat(int fieldId, float fieldValue) {
		if ((this.map & (0x1 << (EXTRA_FIELDS_SHIFT + fieldId))) == 0x0) {
			super.setExtraFloat(fieldId, fieldValue);
			this.map |= 0x1 << (EXTRA_FIELDS_SHIFT + fieldId);
		}
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
			super.setHorizontalAlignment((byte) parentStyle.getHorizontalAlignment());
		}
		if ((map & (0x1 << COLOR_SHIFT)) == 0x0) {
			super.setColor(parentStyle.getColor());
		}
		if ((map & (0x1 << FONT_SHIFT)) == 0x0) {
			super.setFont(parentStyle.getFont());
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
