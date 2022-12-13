# Overview

This example shows how to create and use a "lazy" stylesheet.
The lazy stylesheet resolves the style for a widget with the same algorithm as the cascading stylesheet.
The difference is that the lazy stylesheet associates a factory to the rules instead of a style.
This way the style elements are allocated "on demand" when a rule's selector applies to a widget.

# Usage

The main class is [Main.java](src/main/java/com/microej/example/mwt/lazystylesheet/Main.java).

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
_Copyright 2020-2022 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
