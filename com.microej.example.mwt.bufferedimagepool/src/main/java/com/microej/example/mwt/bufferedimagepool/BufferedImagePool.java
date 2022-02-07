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
	private static BufferedImagePoolContext context;

	private static final int COUNT = Constants.getInt("com.microej.example.mwt.bufferedimagepool.count"); //$NON-NLS-1$

	private BufferedImagePool() {
	}

	/**
	 * Initializes the buffered image pool.
	 */
	public static void initialize() {
		getContext();
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
		return getContext().acquireImage(owner);
	}

	/**
	 * Releases an image.
	 *
	 * @param owner
	 *            the object that releases the image.
	 */
	public static void releaseImage(Object owner) {
		getContext().releaseImage(owner);
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
		getContext().changeImageOwner(formerOwner, newOwner);
	}

	private static BufferedImagePoolContext getContext() {
		if (BufferedImagePool.context != null) {
			return BufferedImagePool.context;
		}
		BufferedImagePool.context = new BufferedImagePoolContext();
		return BufferedImagePool.context;
	}

	private static class BufferedImagePoolContext {

		private static final int NOT_FOUND = -1;

		private final Object monitor;
		private final BufferedImage[] images;
		private final Object[] owners;

		private BufferedImagePoolContext() {
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

		private BufferedImage acquireImage(Object owner) throws OutOfImagesException {
			synchronized (this.monitor) {
				int index = searchForAFreeImage(owner);
				if (index == NOT_FOUND) {
					throw new OutOfImagesException();
				}
				this.owners[index] = owner;
				BufferedImage image = this.images[index];
				assert image != null;
				return image;
			}
		}

		private int searchForAFreeImage(Object owner) {
			for (int i = 0; i < COUNT; i++) {
				Object ownerCandidate = this.owners[i];
				if (ownerCandidate == owner || ownerCandidate == null) {
					return i;
				}
			}
			return NOT_FOUND;
		}

		public void releaseImage(Object owner) {
			synchronized (this.monitor) {
				for (int i = 0; i < COUNT; i++) {
					Object ownerCandidate = this.owners[i];
					if (ownerCandidate == owner) {
						this.owners[i] = null;
						return;
					}
				}
			}
		}

		public void changeImageOwner(Object formerOwner, Object newOwner) {
			synchronized (this.monitor) {
				// Ensure that newOwner does not already own a image.
				for (int i = 0; i < COUNT; i++) {
					Object ownerCandidate = this.owners[i];
					if (ownerCandidate == newOwner) {
						throw new IllegalArgumentException();
					}
				}
				for (int i = 0; i < COUNT; i++) {
					Object ownerCandidate = this.owners[i];
					if (ownerCandidate == formerOwner) {
						this.owners[i] = newOwner;
						return;
					}
				}
			}
		}

	}

}
