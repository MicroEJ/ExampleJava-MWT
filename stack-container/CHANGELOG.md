# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.1] - 2025-03-27

### Changed

- Update Gradle plug-in to `1.1.0`.

### Fixed

- Reset images heap to 0.

## [2.0.0] - 2024-03-05

### Changed

- Migrate to gradle.
- Changed style to minimize number of drawn pixels.

### Fixed

- Fix duplicate rendering at the end of animations.
- Fix drag animations.

## [1.0.2] - 2022-12-13

### Changed

- Rename the project.
- Bump the versions of the module dependencies.

## [1.0.1] - 2022-10-18

### Changed

- Update StackContainer to have a smoother animation when dragging pointer.

## [1.0.0] - 2022-01-21

### Added

- Create a stack container that slides the incoming/departing widget and reusing the content of the display to avoid
  drawing everything for each step.

---  
_Copyright 2021-2025 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  