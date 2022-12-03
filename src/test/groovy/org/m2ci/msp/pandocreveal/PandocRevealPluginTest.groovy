package org.m2ci.msp.pandocreveal

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.*

@Test
class PandocRevealPluginTest {

    GradleRunner provideGradle() {
        def projectDir = File.createTempDir()
        new File(projectDir, 'assets').mkdirs()
        ['build.gradle', 'slides.md', 'header.yaml', 'refs.bib', 'expected.html', 'assets/asset.txt'].each { resourceName ->
            new File(projectDir, resourceName).withWriter {
                it << this.class.getResourceAsStream(resourceName)
            }
        }
        GradleRunner.create().withPluginClasspath().withProjectDir(projectDir).forwardOutput()
    }

    @Test
    void testPlugin() {
        def gradle = provideGradle()
        def result = gradle.build()
        assert result
    }

    @DataProvider
    Object[][] tasks() {
        [
                ['testCompileReveal'],
                ['assemble'],
                ['testDate']
        ]
    }

    @Test(dataProvider = 'tasks')
    void testTasks(taskName) {
        def gradle = provideGradle()
        def result = gradle.withArguments('--warning-mode', 'all', taskName).build()
        assert result
    }
}
