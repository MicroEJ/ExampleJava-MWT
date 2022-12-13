# Overview

This example shows how to add and remove widgets in a widget hierarchy.

The application displays a ``Grid`` container that can contain up to 4 items. The items can be removed from the container by clicking on the trash button. New items can be added to the container by using the "Add Item" button.

The layout adapts automatically to the number of items, because `requestLayout()` is called upon each addition/deletion on the container.

# Usage

The main class is [RemoveWidgetDemo.java](./src/main/java/com/microej/example/mwt/removewidget/RemoveWidgetDemo.java)

# Requirements

This library requires the following Foundation Libraries:

    EDC-1.3, BON-1.4, MICROUI-3.1, DRAWING-1.0

# Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.

# Restrictions

None.

---  
_Copyright 2021-2022 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._ 