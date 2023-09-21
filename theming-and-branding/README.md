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

# Usage

The main class is [ThemingDemo.java](./src/main/java/com/microej/example/mwt/theming-and-branding/ThemingDemo.java)

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
_Copyright 2023 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
