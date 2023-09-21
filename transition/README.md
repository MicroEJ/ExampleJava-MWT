# Overview

This example shows a transition container that triggers effect between pages. It could be based on a pool of BufferedImages.

# Usage

The main class is [TransitionDemo.java](src/main/java/com/microej/example/mwt/transition/TransitionDemo.java).

The on-the-fly creation of a BufferedImage in TransitionContainer can be replaced by the usage of a BufferedImagePool.

Make sure that your launcher uses the application options defined in the [properties](build/common.properties) file to configure the size of the images heap.
Otherwise, there will not be enough space in the images heap to allocate the BufferedImage's native data and it will cause an error.
Refer to the [Application Developer Guide](https://docs.microej.com/en/latest/ApplicationDeveloperGuide/applicationOptions.html#using-a-properties-file) to see how to use a properties file.

# Requirements

This example requires the following Foundation Libraries:

    EDC-1.3, BON-1.4, MICROUI-3.1

# Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.

# Source

N/A

# Restrictions

None.

---  
_Copyright 2023 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
