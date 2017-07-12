package org.m2ci.msp.pandocreveal

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.*

@Test
class PandocRevealPluginTest {

    GradleRunner provideGradle() {
        def projectDir = File.createTempDir()
        ['build.gradle', 'slides.md', 'expected.html'].each { resourceName ->
            new File(projectDir, resourceName).withWriter {
                it << this.class.getResourceAsStream(resourceName)
            }
        }
        GradleRunner.create().withPluginClasspath().withProjectDir(projectDir)
    }

    @Test
    void testPlugin() {
        def gradle = provideGradle()
        def result = gradle.build()
        assert result
    }

    @Test
    void testCompileMarkdown() {
        def gradle = provideGradle()
        def result = gradle.withArguments('testCompileMarkdown').build()
        println result.output
        assert result
    }
}
