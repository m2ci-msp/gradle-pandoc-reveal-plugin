package org.m2ci.msp.pandocreveal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
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
        def pandocDependency = "org.pandoc:pandoc:${pandocReveal.pandocVersion.get()}"
        switch (OperatingSystem.current()) {
            case { it.isLinux() }:
                pandocDependency += System.properties['os.arch'] == 'amd64' ? ':linux-amd64' : ':linux-arm64'
                pandocDependency += '@tar.gz'
                break
            case { it.isMacOsX() }:
                pandocDependency += System.properties['os.arch'] == 'x86_64' ? ':x86_64-macOS' : ':arm64-macOS'
                pandocDependency += '@zip'
                break
        }
        project.dependencies.add(pandocConfig.name, pandocDependency)

        def pandocTask = project.tasks.register('pandoc', CopyPandocResources) {
            destDir = project.layout.buildDirectory.dir('pandoc')
            configFiles = project.files(pandocConfig)
            def packageDir = "pandoc-${pandocReveal.pandocVersion.get()}"
            if (OperatingSystem.current().isMacOsX())
                packageDir += "-${System.properties['os.arch'] == 'aarch64' ? 'arm64' : 'x86_64'}"
            binary = destDir.get().file("$packageDir/bin/pandoc")
        }

        def revealConfig = project.configurations.create('reveal')
        def revealDependency = "se.hakimel.lab:reveal.js:${pandocReveal.revealVersion.get()}@zip"
        project.dependencies.add(revealConfig.name, revealDependency)

        def prepareMarkdownSourceTask = project.tasks.register('prepareMarkdownSource', PrepareMarkdownSource) {
            markdownFile = pandocReveal.markdownFile
            headerFile = pandocReveal.headerFile
        }

        def copyRevealResourcesTask = project.tasks.register('copyRevealResources', CopyRevealResources) {
            configFiles = project.files(revealConfig)
            assetFiles = pandocReveal.assetFiles
            destDir = pandocReveal.destDir
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
