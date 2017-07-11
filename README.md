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

When applying the plugin, the `compileMarkdown` task must be configured with a valid markdown source file and HTML target file (will be created):

```gradle
compileMarkdown {
  markdownFile = file("source.md")
  htmlFile = file("$buildDir/target.md")
}
```

[Gradle]: https://gradle.org
[Pandoc]: http://pandoc.org/
[reveal.js]: http://lab.hakim.se/reveal-js/
[Java]: https://www.java.com/
