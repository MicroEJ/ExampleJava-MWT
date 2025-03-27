# Overview

This example shows how to use a pool of BufferedImages to share them across an application.

Buffered images can be used:

- to speed-up animations by drawing several widgets in an image and drawing this image instead of the widgets (e.g. the page transitions in this demo),
- to speed-up upcoming the renderings of a widget that is pretty long to render by keeping a static part in the image (e.g. the background of the histogram in this demo).

In this demo, there is only one image defined. And it is shared between the histogram and the transition container.

The pros of keeping these images in the memory are:

- images are usually allocated once at the start-up of the application: this limits images heap memory fragmentation (best fit allocator),
- no image needs to be allocated before running an animation, so it starts faster.

The downside is that the images stay in memory during the whole execution of the application.

In a widget, the image is usually acquired and initialized in the `onLaidOut()` method.
When this method is called, the widget has a style and a size and is ready to be drawn on the display.
The image should be released in the `onHidden()` method because the widget does not need to be rendered anymore.

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

The example is optimized for a WQVA (480x272) display with 16 bpp pixel format.
The required [image heap](https://docs.microej.com/en/latest/ApplicationDeveloperGuide/UI/MicroUI/images.html#images-heap) should be adjusted according to the VEE Port used to run the sample.

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
_Copyright 2021-2024 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
