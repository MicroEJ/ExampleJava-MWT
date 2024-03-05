/*
 * Kotlin
 *
 * Copyright 2023-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

plugins {
	id("com.microej.gradle.application") version "0.15.0"
}

group = "com.microej.example.mwt"
version = "2.0.0"

microej {
	applicationMainClass = "com.microej.example.mwt.lazystylesheet.Main"
}
dependencies {
	api("ej.api:edc:1.3.5")
	implementation("ej.library.runtime:basictool:1.7.0")
	implementation("ej.library.ui:widget:5.1.0")

	microejVee("com.microej.veeport.st.stm32f7508-dk:M5QNX_eval:2.2.0")
}

tasks.withType<Javadoc> {
	options.encoding = "UTF-8"
}

testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			microej.useMicroejTestEngine(this)

			dependencies {
				implementation(project())
				implementation("ej.library.test:junit:1.7.1")
				implementation("org.junit.platform:junit-platform-launcher:1.8.2")
			}
		}
	}
}
