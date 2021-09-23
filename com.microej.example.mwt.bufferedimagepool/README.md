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

# Usage

The main class is [BufferedImagePoolDemo.java](src/main/java/com/microej/example/mwt/bufferedimagepool/BufferedImagePoolDemo.java).

Be careful to use the [properties](build/common.properties) in your launcher to configure the images heap.

# Requirements

This library requires the following Foundation Libraries:

    EDC-1.3, BON-1.4, MICROUI-3.0, DRAWING-1.0

# Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.

# Source

N/A

# Restrictions

None.

---  
_Copyright 2021 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
