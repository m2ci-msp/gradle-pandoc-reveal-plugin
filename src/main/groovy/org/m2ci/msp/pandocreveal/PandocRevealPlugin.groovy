package org.m2ci.msp.pandocreveal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Zip
import org.gradle.internal.os.OperatingSystem

class PandocRevealPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        final String REVEALJS = 'revealJS'

        project.pluginManager.apply BasePlugin

        project.repositories {
            exclusiveContent {
                forRepository {
                    ivy {
                        name = 'GitHubJgm'
                        url = 'https://github.com/jgm'
                        patternLayout {
                            artifact '[module]/releases/download/[revision]/[module]-[revision]-[classifier].[ext]'
                        }
                        metadataSources {
                            artifact()
                        }
                    }
                }
                filter {
                    includeGroup 'org.pandoc'
                }
            }

            exclusiveContent {
                forRepository {
                    ivy {
                        name = 'GitHubHakimel'
                        url = 'https://github.com/hakimel'
                        patternLayout {
                            artifact '[module]/archive/[revision].[ext]'
                        }
                        metadataSources {
                            artifact()
                        }
                    }
                }
                filter {
                    includeGroup 'se.hakimel.lab'
                }
            }
        }

        def pandocReveal = project.extensions.create('pandocReveal', PandocRevealExtension)

        def pandocConfig = project.configurations.create('pandoc')

        def pandocTask = project.tasks.register('pandoc', Copy) {
            def pandocDir = project.layout.buildDirectory.dir('pandoc')

            ext.binary = project.objects.fileProperty()
                    .convention(pandocDir.get().file('bin/pandoc'))

            into pandocDir
            from pandocConfig
            filesMatching '*.tar.gz', { tarDetails ->
                project.copy {
                    into pandocDir
                    from project.tarTree(tarDetails.file)
                }
            }
            filesMatching '*.zip', { zipDetails ->
                project.copy {
                    into pandocDir
                    from project.zipTree(zipDetails.file)
                    filesMatching '*/bin/pandoc', { pandocBinary ->
                        pandocBinary.path = 'bin/pandoc'
                    }
                }
            }
        }

        project.configurations.maybeCreate REVEALJS

        switch (OperatingSystem.current()) {
            case { it.isLinux() }:
                project.dependencies.add('pandoc', [group: 'org.pandoc', name: 'pandoc', version: pandocReveal.pandocVersion.get(), classifier: 'linux-amd64', ext: 'tar.gz'])
                break
            case { it.isMacOsX() }:
                project.dependencies.add('pandoc', [group: 'org.pandoc', name: 'pandoc', version: pandocReveal.pandocVersion.get(), classifier: System.properties['os.arch'] == 'x86_64' ? 'x86_64-macOS' : 'arm64-macOS', ext: 'zip'])
                break
        }
        project.dependencies.add REVEALJS, [group: 'se.hakimel.lab', name: 'reveal.js', version: pandocReveal.revealVersion.get(), ext: 'zip']

        def prepareMarkdownSourceTask = project.tasks.register('prepareMarkdownSource', PrepareMarkdownSource)

        project.tasks.register 'compileReveal', PandocRevealCompile, {
            dependsOn pandocTask
            markdownFile = prepareMarkdownSourceTask.get().destFile
            revealJsFiles = project.files(project.configurations.getByName(REVEALJS))
            destDir = project.layout.buildDirectory.dir('slides')
        }

        project.tasks.register 'packageReveal', Zip, {
            from project.tasks.named('compileReveal').get().destDir
        }

        project.artifacts {
            'default' project.tasks.named('packageReveal')
        }
    }
}
