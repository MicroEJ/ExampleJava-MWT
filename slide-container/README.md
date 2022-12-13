# Overview

This example shows a slide container. This is a container that shows only its last child.

An animation is done when adding/removing a child by translating the widgets from/to the right.

The user can also move the visible child by pressing and dragging.
When the user releases it, it is either put back into place or removed from the container (depending on the position of the child).

To speed-up the animations and drag, the rendering of the container is optimized.
It reuses the content of the display and draws only the necessary part of its children.
This feature works only when the back buffer can be read (it does not work with partial buffer management for instance).

# Usage

The main class is [SlideContainerDemo.java](src/main/java/com/microej/example/mwt/slidecontainer/SlideContainerDemo.java).

# Requirements

This library requires the following Foundation Libraries:

    EDC-1.3, BON-1.4, MICROUI-3.1, DRAWING-1.0

# Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.

# Source

N/A

# Restrictions

None.

---  
_Copyright 2021-2022 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
