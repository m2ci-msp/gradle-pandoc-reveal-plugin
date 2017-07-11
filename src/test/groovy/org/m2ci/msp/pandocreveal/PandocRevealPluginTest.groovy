package org.m2ci.msp.pandocreveal

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.*

@Test
class PandocRevealPluginTest {

    GradleRunner provideGradle() {
        def projectDir = File.createTempDir()
        new File(projectDir, 'build.gradle').withWriter {
            it << this.class.getResourceAsStream('build.gradle')
        }
        GradleRunner.create().withPluginClasspath().withProjectDir(projectDir)
    }

    @Test
    void testPlugin() {
        def gradle = provideGradle()
        def result = gradle.build()
        assert result
    }
}
