Gradle Pandoc reveal.js plugin
==============================

[v0.5.8] (2025-03-11)
---------------------

### Changed

- Build with Gradle v8.13
- Upgraded Pandoc to v3.6.3
- Upgraded dependencies
- [all changes since v0.5.7]

[v0.5.7] (2024-10-23)
---------------------

### Changed

- Build with Gradle v8.10.2
- Upgraded Pandoc to v3.5
- Upgraded dependencies
- [all changes since v0.5.6]

[v0.5.6] (2024-08-24)
---------------------

### Fixed

- Compatibility with projects built under Java 8 or 11

### Changed

- [all changes since v0.5.5]

[v0.5.5] (2024-08-15)
---------------------

### Added

- Testing on Java 21

### Changed

- Build with Gradle v8.10
- Upgraded Pandoc to v3.3
- Upgraded dependencies
- Upgraded GitHub Actions
- [all changes since v0.5.4]

[v0.5.4] (2024-06-04)
---------------------

### Fixed

- Testing on macOS in GitHub Actions

### Changed

- Build with Gradle v8.8
- Upgraded Pandoc to v3.2
- Upgraded reveal.js to v5.1.0
- [all changes since v0.5.3]

[v0.5.3] (2024-03-04)
---------------------

### Added

- Support for Table of Contents via Pandoc

### Changed

- Upgraded Pandoc to v3.1.12.2
- [all changes since v0.5.2]

[v0.5.2] (2024-02-26)
---------------------

### Added

- Support for Pandoc filters
- Support for environment variables in Pandoc exec

### Changed

- Upgraded reveal.js to v5.0.5
- [all changes since v0.5.1]

[v0.5.1] (2024-02-19)
---------------------

### Changed

- Build with Gradle v8.6
- Upgraded Pandoc to v3.1.12.1
- Upgraded reveal.js to v5.0.4
- Upgraded dependencies
- [all changes since v0.5.0]

[v0.5.0] (2023-04-02)
---------------------

### Fixed

- Configuration cache error with Gradle 8

### Changed

- Build with Gradle v8.0.2
- Upgraded Pandoc to v3.1.2
- Upgraded dependencies
- Migrated tests from TestNG to JUnit Jupiter
- [all changes since v0.4.0]

[v0.4.0] (2022-12-10)
---------------------

### Added

- Testing on Java 17

### Changed

- Switched from Travis CI to GitHub Actions
- Build with Gradle v7.5.1
- Upgraded dependencies
- Upgraded Pandoc to v2.19.2
- Download, unpack Pandoc dependency from GitHub
- Upgraded reveal.js to v4.4.0
- Upgraded Plugin Publish Plugin to v1.1.0
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

[v0.5.8]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.8
[all changes since v0.5.7]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.7...v0.5.8
[v0.5.7]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.7
[all changes since v0.5.6]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.6...v0.5.7
[v0.5.6]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.6
[all changes since v0.5.5]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.5...v0.5.6
[v0.5.5]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.5
[all changes since v0.5.4]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.4...v0.5.5
[v0.5.4]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.4
[all changes since v0.5.3]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.3...v0.5.4
[v0.5.3]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.3
[all changes since v0.5.2]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.2...v0.5.3
[v0.5.2]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.2
[all changes since v0.5.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.1...v0.5.2
[v0.5.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.1
[all changes since v0.5.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.5.0...v0.5.1
[v0.5.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.5.0
[all changes since v0.4.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.4.0...v0.5.0
[v0.4.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.4.0
[all changes since v0.3.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.3.1...v0.4.0
[v0.3.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.3.1
[all changes since v0.3.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.3.0...v0.3.1
[v0.3.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.3.0
[all changes since v0.2.2]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.2.2...v0.3.0
[v0.2.2]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.2.2
[all changes since v0.2.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.2.1...v0.2.2
[v0.2.1]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.2.1
[all changes since v0.2.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.2.0...v0.2.1
[v0.2.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.2.0
[all changes since v0.1.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/compare/v0.1.0...v0.2.0
[v0.1.0]: https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/releases/tag/v0.1.0
