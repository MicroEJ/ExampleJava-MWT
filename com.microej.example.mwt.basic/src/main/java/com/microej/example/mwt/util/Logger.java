/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.util;

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
