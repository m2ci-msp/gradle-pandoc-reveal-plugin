package org.m2ci.msp.pandocreveal

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.*

@Test
class PandocRevealPluginTest {

    GradleRunner provideGradle() {
        def projectDir = File.createTempDir()
        new File(projectDir, 'assets').mkdirs()
        ['build.gradle', 'slides.md', 'refs.bib', 'expected.html', 'assets/asset.txt'].each { resourceName ->
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
    void testCompileReveal() {
        def gradle = provideGradle()
        def result = gradle.withArguments('testCompileReveal').build()
        println result.output
        assert result
    }

    @Test
    void testAssemble() {
        def gradle = provideGradle()
        def result = gradle.withArguments('assemble').build()
        println result.output
        assert result.tasks.find { it.path == ':compileReveal' }
    }
}
