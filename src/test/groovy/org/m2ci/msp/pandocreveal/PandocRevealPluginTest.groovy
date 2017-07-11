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
        def result = gradle.withArguments('compileMarkdown').build()
        assert result
        def actualFile = new File(gradle.projectDir, 'actual.html')
        assert actualFile.exists()
        def expectedFile = new File(gradle.projectDir, 'expected.html')
//        assert expectedFile.text == actualFile.text
    }
}
