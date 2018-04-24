/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.util;

/**
 * Class that logs messages on {@link System#out}.
 */
public class SysoutLogger implements Logger {

	@Override
	public void logInfo(String message) {
		log(INFO_PROMPT, message);
	}

	private void log(String prompt, String message) {
		System.out.println(prompt + " " + message);
	}

	@Override
	public void logError(String message) {
		log(ERROR_PROMPT, message);
	}

}
