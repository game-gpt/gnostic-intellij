# Gnostic Code Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and the version number follows [Semantic Versioning](https://semver.org/).

## [Unreleased]

### Added
- VON file syntax highlighting support
- Valkyrie script syntax highlighting support
- GG Shader (gs) syntax highlighting support
- Widget file syntax highlighting support
- VON code folding, showing element count and type name
- VON structure view, showing Dict, Pair, List nodes
- VON semantic highlighting, Pair key and Dict type highlighting
- Support for multiple game resource file types (.prefab, .scene, .material, etc.)

### Changed
- Remove VON_TYPED_OBJECT, use VON_DICT uniformly
- Optimize code folding placeholder display format

## [0.0.0] - 2026-04-15

### Added
- Initial project structure
- Basic plugin framework
- File type recognition
