# Overview

This example shows how to create theming and branding for your project.

The application contains only one page.
The page contains:
    - A title text at the top.
    - A radio group of 3 RadioButtons: `Flat`, `Rounded` & `Image` to select the `StyleTheme`.
    - A radio group of 2 RadioButtons: `Normal` & `Condensed` to select the `Theme`.
    - An 'Apply' button at the bottom.

There are two different types of theming shown:
1) Changing from normal to condensed by passing a `Theme` when building the stylesheet.
2) Changing the styling (including padding, margin, background, etc.) itself with a `StyleTheme`.

To change the `StyleTheme` and / or the `Theme` you have to select the RadioButton for the option you want and then press the `Apply` button.

The `ThemeHandler` singleton class is used to store the configuration of the selected theme, which is then applied to the screen when pressing `Apply`.

# Requirements

- MICROEJ SDK 6.
- A VEE Port that contains:

    - EDC-1.3 or higher.
    - BON-1.4 or higher.
    - MICROUI-3.4 or higher.
    - DRAWING-1.0 or higher.

This example has been tested on:

- IntelliJ IDEA 2023.3.3.
- STM32F7508-DK VEE Port 2.2.0.

# Usage

By default, the sample will use the STM32F7508-DK VEE Port.

Refer to the [Select a VEE Port](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html) documentation for more information.

## Configuration

Configuration options can be found in: `configuration/common.properties`.

## Run on simulator

In IntelliJ IDEA or Android Studio:
- Open the Gradle tool window by clicking on the elephant icon on the right side,
- Expand the `Tasks` list,
- From the `Tasks` list, expand the `microej` list,
- Double-click on `runOnSimulator`,
- The application starts, the traces are visible in the Run view.

Alternative ways to run in simulation are described in the [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) documentation.

## Run on device

Make sure to properly setup the VEE Port environment before going further.
Refer to the VEE Port README for more information.

In IntelliJ IDEA or Android Studio:
- Open the Gradle tool window by clicking on the elephant on the right side,
- Expand the `Tasks` list,
- From the `Tasks` list, expand the `microej` list,
- Double-Click on `runOnDevice`.
- The device is flashed. Use the appropriate tool to retrieve the execution traces.

Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) documentation.

# Dependencies

_All dependencies are retrieved transitively by gradle_.

# Source

N/A

# Restrictions

None.

---  
_Copyright 2023-2024 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
