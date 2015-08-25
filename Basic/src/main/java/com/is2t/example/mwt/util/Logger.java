/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.util;

/**
 * Message logger interface.
 */
public interface Logger {

	/**
	 * Info prompt.
	 */
	String INFO_PROMPT = "[INFO]";

	/**
	 * Error prompt.
	 */
	String ERROR_PROMPT = "[ERROR]";

	/**
	 * Logs an info message.
	 * 
	 * @param message
	 *            info message
	 */
	void logInfo(String message);

	/**
	 * Logs an error message.
	 * 
	 * @param message
	 *            error message
	 */
	void logError(String message);

}
