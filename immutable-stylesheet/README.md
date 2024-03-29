# Overview

This example shows how to create and use an immutable stylesheet.
The immutable stylesheet resolves the style for a widget with the same algorithm as the cascading stylesheet.
The difference is that the immutable stylesheet is described in an immutable file instead of a Java code.

The application contains only one page with two labels. 
The first one has the value `This is an example` and the second one has the value `of immutable stylesheet.` and the `YELLOWBACKGROUND` class selector.

The immutable file ([stylesheet.xml](src/main/resources/immutables/stylesheet.xml)) defines two rules (a rule is a couple of selector and style). 
The first one (`labelSelectorStyle`) is a type selector which changes the color and the font of all the labels. 
The second one (`yellowBackgroundSelectorStyle`) is a class selector which changes the background of the `YELLOWBACKGROUND` class.

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

## Add a new rule in the stylesheet

This section presents how to add another rule style/selector in [stylesheet.xml](src/main/resources/immutables/stylesheet.xml).
For example how to modify the horizontal alignment of the class selector with value `35`.

### 1 - Create a new style

In [CascadingStyle.java](src/main/java/com/microej/example/mwt/immutablestylesheet/stylesheet/CascadingStyle.java), get the attribute field position in bit map (e.g. `1`).

Then add the style in the immutable file.
In the following example, the horizontal alignment will be modified because the bit 1 is activated in the map field:
```
<object id="myStyle" type="com.microej.example.mwt.immutablestylesheet.stylesheet.ImmutableStyle">
	<field name="map" value="2"/> <!-- Binary: 0000 0000 0000 0010 -->
	<refField name="dimension" ref="NoDimension"/>
	<field name="horizontalAlignment" value="3"/>
	<field name="verticalAlignment" value="4"/>
	<refField name="margin" ref="NoOutline"/>
	<refField name="border" ref="NoOutline"/>
	<refField name="padding" ref="NoOutline"/>
	<refField name="background" ref="NoBackground"/>
	<field name="color" value="0x0000FF"/>
	<refField name="fontProvider" ref="resourceFontProvider"/>
	<refField name="extraFields" ref="EMPTY_EXTRA_FIELDS"/>
</object>
```
Even if they will not be modified, fill the other attributes because all the fields of an immutable object must be set.

### 2 - Create a new selector

The style has to be applied to a selector.
Create it in the immutable file.

Following is an example of a selector for the class selector number 35.
Other selectors can be defined in a similar fashion.

```
<object id="mySelector" type="ej.mwt.stylesheet.selector.ClassSelector">
	<field name="classSelector" value="35"/>
</object>
```

### 3 - Create a rule

Now that the style and the selector are created, link them in a rule like the following example:

```
<object id="mySelectorStyle" type="com.microej.example.mwt.immutablestylesheet.stylesheet.CascadingStylesheet$SelectorStyle">
	<refField name="selector" ref="mySelector"/>
	<refField name="style" ref="myStyle"/>
</object>
```

### 4 - Add a rule in the list

The last step is to add the new rule in the list of rules.

For example, if there is already two others rules in that list, add the new one and increase the length attribute by one.

```
<array id="selectorsStyles" type="com.microej.example.mwt.immutablestylesheet.stylesheet.CascadingStylesheet$SelectorStyle[]" length="3">
	<refElem ref="labelSelectorStyle"/>
	<refElem ref="yellowBackgroundSelectorStyle"/>
	<refElem ref="mySelectorStyle"/>
</array>
```

# Dependencies

_All dependencies are retrieved transitively by gradle_.

# Source

N/A

# Restrictions

None.

---  
_Copyright 2020-2024 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
