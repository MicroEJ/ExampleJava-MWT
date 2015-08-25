# Overview
This repository provides a collection of MWT examples. Each example has its own Eclipse/MicroEJ project.

## Project structure
Each folder is an Eclipse/MicroEJ project. They share a common structure:
- `src/main/java`
  - Java sources
- `src/main/resources`
  - Fonts/images
- `launches/`: MicroEJ launches

## Basic
This project shows how to create a basic MWT application. If you are new to MWT, you should start with this example.
It demonstrates the core concepts of MWT:
- Theme and Look,
- Widgets and Renderers,
- Composite.

It also shows how to periodically update the widget data.
Traces to standard output show which thread executes key methods.

### Requirements
- JRE 7 (or later) x86.
- MicroEJ 3.1 or later.
- Java platform with (at least) EDC-1.2, MWT-1.0.

## Button
This project shows how to create two buttons to modify a label with MWT.

### Requirements
- JRE 7 (or later) x86.
- MicroEJ 3.1 or later.
- Java platform with (at least) EDC-1.2, MWT-1.0.

## SlidingWidget
 This example shows how to slide widgets inside a composite. Two implementations are provided. 
 The first implementation is full widget based (all widgets are drawn during the animations - take more CPU time, consume no additional RAM) and the second implementation is screenshot based (1. current framebuffer content is saved to an image1 (local RAM buffer). 2. New screen is drawn in image2. 3. Animation is done by drawing image1 and image2. Consumes more RAM but takes less CPU time).
 
### Requirements
- JRE 7 (or later) x86.
- MicroEJ 3.1 or later.
- Java platform with (at least) EDC-1.2, BON-1.2, MICROUI-1.5.0, MWT-1.0, EJ.MOTION-2.1.

## Setup
Import the projects in MicroEJ.

## Usage
Import the projects. Each project comes with a Run Configuration. Simply run it to execute the example on SimJPF.

## Changes
Share Basic and Button examples.
Add SlidingWidget example and restructure Basic and Button examples.

## License
See the license file `LICENSE.md` located at the root of this repository.
