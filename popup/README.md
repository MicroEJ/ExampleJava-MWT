# Overview

This example shows how to show a popup in an application.

A popup is a menu that appears over the content of the application.
It is used to inform or to request an action from the user.

In this example, these two types of popups are illustrated:
- The information popup can be dismissed by clicking outside of its bounds.
- The action popup needs the user to click on a button to close it.

The popup management is done by defining a custom desktop that changes the render policy and the event dispatcher.
See [PopupDesktop.java](src/main/java/com/microej/example/mwt/popup/PopupDesktop.java) for more information.

# Usage

The main class is [PopupDemo.java](src/main/java/com/microej/example/mwt/popup/PopupDemo.java).

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
