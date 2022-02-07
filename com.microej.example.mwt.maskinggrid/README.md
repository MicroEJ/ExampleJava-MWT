# Overview

This example shows how to mask a widget temporarily.

The application contains a grid with 9 items.
On each item, there is a label with its name, a circular progress and a "Hide" button.
When this button is pressed, the associated item is hidden from the container.

To show or hide all elements at once, two buttons "Show All" or "Hide All" are also available.

The grid is a custom container (`MaskingGrid`) which exposes an API to change the visibility of its children (visible or invisible). 
When requested to render, the grid only renders the children that are visible (see `MaskingGrid.renderChild()` method).

# Usage

The main class is [MaskingGridDemo.java](src/main/java/com/microej/example/mwt/maskinggrid/MaskingGridDemo.java).

# Requirements

This library requires the following Foundation Libraries:

    EDC-1.3, BON-1.4, MICROUI-3.0, DRAWING-1.0

# Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.

# Restrictions

None.

---  
_Copyright 2021-2022 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
