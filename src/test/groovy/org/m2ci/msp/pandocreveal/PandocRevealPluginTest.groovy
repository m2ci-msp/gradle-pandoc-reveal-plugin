package org.m2ci.msp.pandocreveal

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.*

@Test
class PandocRevealPluginTest {

    GradleRunner provideGradle() {
        GradleRunner.create().withProjectDir(File.createTempDir())
    }

    @Test
    void testPlugin() {
        def gradle = provideGradle()
        assert gradle.build()
    }
}
