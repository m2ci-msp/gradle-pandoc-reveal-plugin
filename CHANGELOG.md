Gradle Pandoc reveal.js plugin
==============================

[Unreleased]
------------

### Changed

- Build with Gradle v6.3
- Upgrade dependencies
- [all changes since v0.3.1]

[v0.3.1] (2019-08-21)
---------------------

### Added

- ISO dates (e.g., `date: 2019-08-21`) in YAML header are properly formatted on title slide (i.e., "Wed, Aug 21, 2019")

### Fixed

- Resolved deprecation warnings for Gradle v6.0

### Changed

- Upgrade to reveal.js v3.8.0
- Build with Gradle v5.6
- [all changes since v0.3.0]

[v0.3.0] (2018-08-06)
---------------------

### Added

- optionally, provide header as separate YAML file
- optional extra assets as file collection

### Changed

- Upgrade to reveal.js v3.7.0
- Build with Gradle v4.9
- Use lazy configuration
- renamed compile task name to `compileReveal`
- reveal.js and HTML generated in same destination directory
- [all changes since v0.2.2]

[v0.2.2] (2018-02-26)
---------------------

### Changed

- Build with Gradle v4.5.1
- [all changes since v0.2.1]

[v0.2.1] (2017-11-30)
---------------------

### Added

- CI testing on Mac OSX

### Fixed

- Removed obsolete `--smart` option

### Changed

- Upgrade to reveal.js v3.6.0
- Build with Gradle v4.3.1
- Upgrade test dependency and plugin
- [all changes since v0.2.0]

[v0.2.0] (2017-10-17)
---------------------

### Added

- Support for citations (requires `pandoc-citeproc`)

### Changed

- Improved documentation
- [all changes since v0.1.0]

[v0.1.0] (2017-07-11)
---------------------

### Initial release

- Markdown to HTML5 conversion with installed Pandoc and reveal.js v3.5.0

[Unreleased]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/tree/master
[all changes since v0.3.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.3.1...HEAD
[v0.3.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.3.1
[all changes since v0.3.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.3.0...0.3.1
[v0.3.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.3.0
[all changes since v0.2.2]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.2.2...0.3.0
[v0.2.2]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.2.2
[all changes since v0.2.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.2.1...v0.2.2
[v0.2.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.2.1
[all changes since v0.2.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.2.0...v0.2.1
[v0.2.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.2.0
[all changes since v0.1.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.1.0...v0.2.0
[v0.1.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.1.0
