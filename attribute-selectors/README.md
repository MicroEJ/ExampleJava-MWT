# Overview

This example shows how to customize the style of widgets using attribute selectors, similar to CSS Attribute Selectors.

The application contains only one page with two widgets: a button and a label.
The label holds an attribute `status` that can be `ON` or `OFF`.
The button is used to toggle the value of the attribute in the label (and its text).

The classes in package `com.microej.example.mwt.attribute.selector` implement several types of attribute selectors:
- [AttributeSetSelector](src/main/java/com/microej/example/mwt/attribute/selector/AttributeSetSelector.java), equivalent to CSS `[attribute]` Selector.
- [AttributeValueSelector](src/main/java/com/microej/example/mwt/attribute/selector/AttributeValueSelector.java), equivalent to CSS `[attribute="value"]` Selector.
- [AttributeValueListSelector](src/main/java/com/microej/example/mwt/attribute/selector/AttributeValueListSelector.java), equivalent to CSS `[attribute~=value]` Selector.
- [AttributeValuePrefixSelector](src/main/java/com/microej/example/mwt/attribute/selector/AttributeValuePrefixSelector.java), equivalent to CSS `[attribute|=value]` Selector.
- [AttributeValueStartSelector](src/main/java/com/microej/example/mwt/attribute/selector/AttributeValueStartSelector.java), equivalent to CSS `[attribute^=value]` Selector.
- [AttributeValueEndSelector](src/main/java/com/microej/example/mwt/attribute/selector/AttributeValueEndSelector.java), equivalent to CSS `[attribute$=value]` Selector.
- [AttributeValueContainSelector](src/main/java/com/microej/example/mwt/attribute/selector/AttributeValueContainSelector.java), equivalent to CSS `[attribute*=value]` Selector.


Any of these classes can be used as selector in the stylesheet to select widgets based on their attributes.
In this example, we demonstrate the use of the selector `AttributeValueSelector`: depending on the value of the attribute `status` of the label (`ON` or `OFF`), the background color of the label will change.
Feel free to adapt this code to experiment the other attribute selectors.


# Usage

The main class is [AttributeDemo.java](src/main/java/com/microej/example/mwt/attribute/AttributeDemo.java).

To run the example in the Virtual Device:
- Right-click on the class `com.microej.example.mwt.attribute.AttributeDemo`.
- Select `Run As` > `MicroEJ Application`.


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
