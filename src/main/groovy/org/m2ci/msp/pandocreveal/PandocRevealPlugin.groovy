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
        def pandocDependency = [
                group  : 'org.pandoc',
                name   : 'pandoc',
                version: pandocReveal.pandocVersion.get()
        ]
        switch (OperatingSystem.current()) {
            case { it.isLinux() }:
                pandocDependency << [
                        classifier: 'linux-amd64',
                        ext       : 'tar.gz'
                ]
                break
            case { it.isMacOsX() }:
                pandocDependency << [
                        classifier: System.properties['os.arch'] == 'x86_64' ? 'x86_64-macOS' : 'arm64-macOS',
                        ext       : 'zip'
                ]
                break
        }
        project.dependencies.add(pandocConfig.name, pandocDependency)

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

        def revealConfig = project.configurations.create('reveal')
        def revealDependency = [
                group  : 'se.hakimel.lab',
                name   : 'reveal.js',
                version: pandocReveal.revealVersion.get(),
                ext    : 'zip'
        ]
        project.dependencies.add(revealConfig.name, revealDependency)

        def prepareMarkdownSourceTask = project.tasks.register('prepareMarkdownSource', PrepareMarkdownSource) {
            markdownFile = pandocReveal.markdownFile
            headerFile = pandocReveal.headerFile
        }

        def copyRevealResourcesTask = project.tasks.register('copyRevealResources', Copy) {
            into pandocReveal.destDir
            from revealConfig, {
                filesMatching '*.zip', { zipDetails ->
                    project.copy {
                        into pandocReveal.destDir
                        from project.zipTree(zipDetails.file)
                    }
                    zipDetails.exclude()
                }
            }
            from pandocReveal.assetFiles
        }

        project.tasks.register 'compileReveal', PandocRevealCompile, {
            dependsOn pandocTask,
                    copyRevealResourcesTask
            pandocBinary = pandocTask.get().binary
            markdownFile = prepareMarkdownSourceTask.get().destFile
            revealVersion = pandocReveal.revealVersion
            tableOfContents = pandocReveal.tableOfContents
            tableOfContentsDepth = pandocReveal.tableOfContentsDepth
            bibFile = pandocReveal.bibFile
            cslFile = pandocReveal.cslFile
            pandocFilters = pandocReveal.pandocFilters
            pandocEnvironment = pandocReveal.pandocEnvironment
            destDir = pandocReveal.destDir
        }

        project.tasks.register 'packageReveal', Zip, {
            from pandocReveal.destDir
        }

        project.artifacts {
            'default' project.tasks.named('packageReveal')
        }
    }
}
