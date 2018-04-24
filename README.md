<!--
 Markdown
 
 Copyright 2016-2018 IS2T. All rights reserved.
 For demonstration purpose only.
 IS2T PROPRIETARY. Use is subject to license terms.
-->

# Overview
This repository provides a collection of MWT examples. Each example has its own Eclipse/MicroEJ project.
Those examples are developed as standalone applications and as such can be run by following the associated instructions (see **README.md** file of each example).

Note that to run them on board:

* If you are using MicroEJ SDK:
 * You need a supported board (see http://developer.microej.com/index.php?resource=JPF for a list of supported boards using MicroEJ SDK evaluation version)
 * And the associated platform binary .jpf file (retrieve it from the previous link and import it into MicroEJ SDK)

* If you are using MicroEJ Studio:
 * You need to convert them from standalone applications to sandboxed applications.
 * Follow the [How-To convert a standalone app into a sandboxed app](https://github.com/MicroEJ/How-To/tree/master/StandaloneToSandboxed) guide.

# Details
## com.microej.example.mwt.helloworld
This example shows a simple hello world using MWT.

## com.microej.example.mwt.mvc
This example shows how to create and use a MVC design pattern.

## com.microej.example.mwt.basic
This project shows how to create a basic MWT application. If you are new to MWT, you should start with this example.
It demonstrates the core concepts of MWT:
- Widgets,
- Composite.

## com.microej.example.mwt.button
This project shows how to create two buttons to modify a label with MWT.

## com.microej.example.mwt.slidingwidget
 This example shows how to slide widgets inside a composite. Two implementations are provided. 
 The first implementation is full widget based (all widgets are drawn during the animations - take more CPU time, consume no additional RAM) and the second implementation is screenshot based (1. current framebuffer content is saved to an image1 (local RAM buffer). 2. New screen is drawn in image2. 3. Animation is done by drawing image1 and image2. Consumes more RAM but takes less CPU time).
 
