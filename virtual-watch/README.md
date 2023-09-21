# Overview

This example shows how to simulate the skin and inputs of a device with a different device (e.g., an evaluation board).
This can be a convenient option when the target hardware is not yet available.

Here, it simulates a watch with a round screen and 3 buttons.
The actual application is shown in a round area of the screen and receives events from the virtual buttons.

The virtual buttons send [commands](https://repository.microej.com/javadoc/microej_5.x/apis/ej/microui/event/generator/Command.html) when clicked (UP, DOWN and SELECT).
The same way a target device would have sent events from the native world.
The goal is to be able to migrate the application on the target device without modifying the application code.

The application shows some labels, including a value, and some buttons.
The value is modified by the commands (sent by the virtual buttons) or by the minus or plus buttons in the application.

# Usage

The main class is [VirtualWatchDemo.java](src/main/java/com/microej/example/mwt/virtual-watch/VirtualWatchDemo.java).

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
_Copyright 2021-2023 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
