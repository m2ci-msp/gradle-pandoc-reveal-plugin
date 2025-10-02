[![CI](https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/actions/workflows/main.yml/badge.svg)](https://github.com/m2ci-msp/gradle-pandoc-reveal-plugin/actions/workflows/main.yml)
[![License: Apache-2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Gradle Pandoc reveal.js plugin
==============================

[Gradle] plugin to apply common build logic to a slideshow project.
Compilation from Markdown to HTML5 using [Pandoc] and [reveal.js].

Prerequisites
-------------

[Java] must be installed.

This plugin requires Gradle v7.2 or higher.

### Warning

This plugin does not yet work properly when Gradle's configuration cache is enabled.
To avoid errors, ensure the configuration cache is disabled, e.g., by setting

    org.gradle.configuration-cache=false

in your `gradle.properties`, or running Gradle with the `--no-configuration-cache` option.

Usage
-----

See [here](https://plugins.gradle.org/plugin/org.m2ci.msp.pandocreveal) for usage instructions.

When applying the plugin, the `pandocReveal` extension in the `build.gradle` script must be configured with a valid markdown source file:

```gradle
pandocReveal {
  markdownFile = file('src/source.md')

  // optionally, YAML header in separate file:
  headerFile = file('src/header.yaml')

  // optionally, additional assets:
  assetFiles = files('some/asset.txt', tasks.findByName('assetProducingTask')) +
    fileTree('src').include('assets/**')

  // optionally, enable ToC and maybe set depth (default: 1)
  tableOfContents = true
  tableOfContentsDepth = 2
  // note that the ToC title heading will be empty; set `toc-title` to the desired heading in the YAML header file...

  // optionally, filters and/or environment variables for Pandoc exec, e.g.,:
  pandocFilters = ['mermaid-filter']
  pandocEnvironment = ['MERMAID_FILTER_FORMAT': 'svg']
}
```

Note that Pandoc filters such as [mermaid-filter] must be installed separately.

The generated slideshow will be created in `pandocReveal.destDir` (default value: `layout.buildDirectory.dir('slides')`)

[Gradle]: https://gradle.org
[Pandoc]: https://pandoc.org/
[reveal.js]: https://revealjs.com/
[Java]: https://www.java.com/
[mermaid-filter]: https://github.com/raghur/mermaid-filter
