# Overview

This example shows a smart watch application that looks different depending on whether the user is wearing the device on the left arm or on the right arm. The widgets are placed according to the context (here, the position of the physical buttons). 

`ContextSensitiveContainer` is an example of a container that adapts to the context by changing how the children are laid out. In this case, the container can show two widgets (one at the center, the other on one side). Depending on the wrist mode, the side widget will be displayed on either the left or right side.

For demonstration purposes, the example displays the application in a `VirtualWatchDesktop` that simulates a watch device. The example can run on any target by replacing this virtual desktop with a standard desktop instead and provide a mean to change the wrist mode.

The user can flip the watch by clicking the "Flip watch" button of the virtual desktop.

# Usage

The main class is [ContextSensitiveDemo.java](src/main/java/com/microej/example/mwt/contextsensitive/ContextSensitiveDemo.java).

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
