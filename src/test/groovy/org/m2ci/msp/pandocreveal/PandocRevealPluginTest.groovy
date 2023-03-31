package org.m2ci.msp.pandocreveal

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class PandocRevealPluginTest {

    GradleRunner provideGradle() {
        def projectDir = File.createTempDir()
        new File(projectDir, 'settings.gradle').createNewFile()
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

    static Object[][] tasks() {
        [
                ['testPandoc'],
                ['testCompileReveal'],
                ['assemble'],
                ['testDate']
        ]
    }

    @ParameterizedTest
    @MethodSource('tasks')
    void testTasks(taskName) {
        def gradle = provideGradle()
        def result = gradle.withArguments('--warning-mode', 'all', '--stacktrace', taskName).build()
        assert result.task(":$taskName").outcome == SUCCESS
    }
}
