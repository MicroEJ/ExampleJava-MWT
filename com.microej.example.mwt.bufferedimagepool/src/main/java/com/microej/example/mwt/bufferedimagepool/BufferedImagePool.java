/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.bufferedimagepool;

import ej.annotation.Nullable;
import ej.bon.Constants;
import ej.microui.display.BufferedImage;
import ej.microui.display.Display;

/**
 * Declares one or several buffered images the size of the screen.
 */
public class BufferedImagePool {

	@Nullable
	private static BufferedImagePool instance;

	private static final int COUNT = Constants.getInt("com.microej.example.mwt.bufferedimagepool.count"); //$NON-NLS-1$

	private static final int NOT_FOUND = -1;

	private final Object monitor;
	private final BufferedImage[] images;
	private final Object[] owners;

	private BufferedImagePool() {
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		this.images = new BufferedImage[COUNT];
		for (int i = 0; i < COUNT; i++) {
			this.images[i] = new BufferedImage(displayWidth, displayHeight);
		}
		this.owners = new Object[COUNT];
		this.monitor = new Object();
	}

	/**
	 * Initializes the buffered image pool.
	 * <p>
	 * Should be called at the startup of the application to allocate the buffers at the beginning of the images memory.
	 */
	public static void initialize() {
		getInstance();
	}

	private static BufferedImagePool getInstance() {
		if (BufferedImagePool.instance != null) {
			return BufferedImagePool.instance;
		}
		BufferedImagePool.instance = new BufferedImagePool();
		return BufferedImagePool.instance;
	}

	/**
	 * Requests an image.
	 * <p>
	 * This method blocks while the image is owned by another object.
	 * <p>
	 * It returns <code>null</code> if the thread has been interrupted.
	 *
	 * @param owner
	 *            the object that needs the image.
	 *
	 * @return the shared image.
	 * @throws OutOfImagesException
	 *             if there is no more images available.
	 */
	public static BufferedImage acquireImage(Object owner) throws OutOfImagesException {
		return getInstance().acquireBuffer(owner);
	}

	private BufferedImage acquireBuffer(Object owner) throws OutOfImagesException {
		synchronized (this.monitor) {
			int index = searchForAFreeBuffer(owner);
			if (index == NOT_FOUND) {
				throw new OutOfImagesException();
			}
			this.owners[index] = owner;
			BufferedImage image = this.images[index];
			assert image != null;
			return image;
		}
	}

	private int searchForAFreeBuffer(Object owner) {
		Object[] owners = this.owners;
		for (int i = 0; i < COUNT; i++) {
			Object ownerCandidate = owners[i];
			if (ownerCandidate == owner || ownerCandidate == null) {
				return i;
			}
		}
		return NOT_FOUND;
	}

	/**
	 * Releases an image.
	 *
	 * @param owner
	 *            the object that releases the image.
	 */
	public static void releaseImage(Object owner) {
		getInstance().releaseBuffer(owner);
	}

	private void releaseBuffer(Object owner) {
		synchronized (this.monitor) {
			Object[] owners = this.owners;
			for (int i = 0; i < COUNT; i++) {
				Object ownerCandidate = owners[i];
				if (ownerCandidate == owner) {
					owners[i] = null;
					return;
				}
			}
		}
	}

	/**
	 * Changes the owner of an image.
	 *
	 * @param formerOwner
	 *            the current owner.
	 * @param newOwner
	 *            the new owner.
	 * @throws IllegalArgumentException
	 *             if the new owner already owns a shared image.
	 */
	public static void changeImageOwner(Object formerOwner, Object newOwner) {
		getInstance().changeBufferOwner(formerOwner, newOwner);
	}

	private void changeBufferOwner(Object formerOwner, Object newOwner) {
		synchronized (this.monitor) {
			Object[] owners = this.owners;
			// Ensure that newOwner does not already own a image.
			for (int i = 0; i < COUNT; i++) {
				Object ownerCandidate = owners[i];
				if (ownerCandidate == newOwner) {
					throw new IllegalArgumentException();
				}
			}
			for (int i = 0; i < COUNT; i++) {
				Object ownerCandidate = owners[i];
				if (ownerCandidate == formerOwner) {
					owners[i] = newOwner;
					return;
				}
			}
		}
	}

}
