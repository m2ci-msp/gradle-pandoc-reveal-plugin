[![CI](https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/actions/workflows/main.yml/badge.svg)](https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/actions/workflows/main.yml)
[![License: Apache-2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Gradle Pandoc reveal.js plugin
==============================

[Gradle] plugin to apply common build logic to a slideshow project.
Compilation from Markdown to HTML5 using [Pandoc] and [reveal.js].

Prerequisites
-------------

[Java] must be installed.

This plugin requires Gradle v6.2 or higher.

Usage
-----

See [here](https://plugins.gradle.org/plugin/org.m2ci.msp.pandocreveal) for usage instructions.

When applying the plugin, the `compileReveal` task must be configured with a valid markdown source file:

```gradle
compileReveal {
  markdownFile = file('src/source.md')

  // optionally, YAML header in separate file:
  headerFile = file('src/header.yaml')

  // optionally, additional assets:
  assetFiles = files('some/asset.txt', tasks.findByName('assetProducingTask')) +
    fileTree('src').include('assets/**')

  // optionally, filters and/or environment variables for Pandoc exec, e.g.,:
  pandocFilters = ['mermaid-filter']
  pandocEnvironment = ['MERMAID_FILTER_FORMAT': 'svg']
}
```

The generated slideshow will be created in `compileReveal.destDir` (default: `layout.buildDirectory.dir('slides')`)

[Gradle]: https://gradle.org
[Pandoc]: https://pandoc.org/
[reveal.js]: https://revealjs.com/
[Java]: https://www.java.com/
