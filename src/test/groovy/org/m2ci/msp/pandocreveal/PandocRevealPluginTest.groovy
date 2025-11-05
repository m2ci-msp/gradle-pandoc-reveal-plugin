package org.m2ci.msp.pandocreveal

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

import java.util.stream.Stream

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

    @Test
    void testPluginWithUnsupportedLegacyGradle() {
        def gradle = provideGradle().withGradleVersion('7.1')
        def result = gradle.buildAndFail()
        assert result
    }

    @Test
    void testPluginWithLegacyGradle() {
        def gradle = provideGradle().withGradleVersion('7.2')
        def result = gradle.build()
        assert result
    }

    static Stream<String> taskNames() {
        Stream.of(
                'testPandoc',
                'testCompileReveal',
                'testDate'
        )
    }

    @ParameterizedTest
    @MethodSource('taskNames')
    void testTasks(String taskName) {
        runTask(taskName, false)
    }

    @ParameterizedTest
    @MethodSource('taskNames')
    void testTasksWithConfigurationCacheEnabled(String taskName) {
        runTask(taskName, true)
    }

    void runTask(String taskName, boolean configurationCacheEnabled) {
        def gradle = provideGradle()
        def args = ['--warning-mode', 'all', '--stacktrace', taskName]
        if (configurationCacheEnabled)
            args += '--configuration-cache'
        def result = gradle.withArguments(args).build()
        assert result.task(":$taskName").outcome == SUCCESS
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    void testToc(boolean withToc) {
        def gradle = provideGradle()
        def taskName = 'testToc'
        def result = gradle.withArguments('--warning-mode', 'all',
                '--stacktrace',
                '--project-prop', "tocEnabled=$withToc",
                taskName).build()
        assert result.task(":$taskName").outcome == SUCCESS
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    void testMermaid(boolean withMermaid) {
        def gradle = provideGradle()
        def taskName = 'testMermaid'
        def result = gradle.withArguments('--warning-mode', 'all',
                '--stacktrace',
                '--project-prop', "mermaidEnabled=$withMermaid",
                taskName).build()
        assert result.task(":$taskName").outcome == SUCCESS
    }
}
