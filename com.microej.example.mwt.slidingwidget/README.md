<!--
 Markdown
 
 Copyright 2016-2018 IS2T. All rights reserved.
 For demonstration purpose only.
 IS2T PROPRIETARY. Use is subject to license terms.
-->

# Overview
This example shows how to slide widgets inside a composite. Two implementations are provided. 
The first implementation is full widget based (all widgets are drawn during the animations - take more CPU time, consume no additional RAM) and the second implementation is screenshot based (1. current framebuffer content is saved to an image1 (local RAM buffer). 2. New screen is drawn in image2. 3. Animation is done by drawing image1 and image2. Consumes more RAM but takes less CPU time).

# Usage
## Run on MicroEJ Simulator
1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select your platform 
4. Press **Ok**


## Run on device
### Build
1. Right Click on [SlidingWidget.java](src/main/java/com/microej/example/mwt/mvc/SlidingWidget.java)
2. Select **Run as -> Run Configuration**
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform (but not a virtual device)
	2. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Build & Deploy**
6. Press **Apply**
7. Press **Run**
8. Copy the generated `.out` file path shown by the console

### Flash
1. Use the appropriate flashing tool.

# Requirements
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or higher
	* MICROUI-2.0 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.

# Source
N/A

# Restrictions
None.
