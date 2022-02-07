/*
 * Copyright 2009-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.mvc.model;

import java.util.Observable;
import java.util.Random;

/**
 * A model providing a percentage.
 */
public class PercentageModel extends Observable {

	private static final int PAGE_INCREMENT = 10;
	private static final int RANGE = 100;

	private final Random random;

	private int value;

	/**
	 * Instantiates a {@link PercentageModel}.
	 */
	public PercentageModel() {
		this.random = new Random(System.currentTimeMillis());
		this.value = 0;
	}

	private void increment(int incr) {
		// get old value
		int oldValue = getValue();
		// increment value
		int value = oldValue + incr;
		// crop to [0..RANGE].
		if (value > RANGE) {
			value = RANGE;
		}
		if (value < 0) {
			value = 0;
		}
		update(value);
	}

	private void update(int value) {
		// update value and notify listeners
		if (getValue() != value) {
			setValue(value);
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Increments the value by one.
	 */
	public void incrementValue() {
		increment(1);
	}

	/**
	 * Decrements the value by one.
	 */
	public void decrementValue() {
		increment(-1);
	}

	/**
	 * Increments the value by 10.
	 */
	public void pageIncrementValue() {
		increment(PAGE_INCREMENT);
	}

	/**
	 * Decrements the value by 10.
	 */
	public void pageDecrementValue() {
		increment(-PAGE_INCREMENT);
	}

	/**
	 * Sets a random value.
	 */
	public void random() {
		update(this.random.nextInt(RANGE + 1));
	}

	/**
	 * Gets the value.
	 *
	 * @return the value.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
