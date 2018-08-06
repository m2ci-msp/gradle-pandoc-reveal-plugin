[![Build Status](https://travis-ci.org/m2ci-msp/gradle-pandoc-reveal-plugin.svg?branch=master)](https://travis-ci.org/m2ci-msp/gradle-pandoc-reveal-plugin)

Gradle Pandoc reveal.js plugin
==============================

[Gradle] plugin to apply common build logic to a slideshow project.
Compilation from Markdown to HTML5 using [Pandoc] and [reveal.js].

Prerequisites
-------------

[Pandoc] and [Java] must be installed.

Usage
-----

See [here](https://plugins.gradle.org/plugin/org.m2ci.msp.pandocreveal) for usage instructions.

When applying the plugin, the `compileReveal` task must be configured with a valid markdown source file:

```gradle
compileReveal {
  markdownFile = file('src/source.md')
  // optionally, additional assets:
  assetFiles = files('some/asset.txt', tasks.findByName('assetProducingTask')) +
    fileTree('src').include('assets/**')
}
```

The generated slideshow will be created in `compileReveal.destDir` (default: `layout.buildDirectory.dir('slides')`)

[Gradle]: https://gradle.org
[Pandoc]: http://pandoc.org/
[reveal.js]: http://lab.hakim.se/reveal-js/
[Java]: https://www.java.com/
