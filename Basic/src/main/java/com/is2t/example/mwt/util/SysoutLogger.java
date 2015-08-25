/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.util;

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
