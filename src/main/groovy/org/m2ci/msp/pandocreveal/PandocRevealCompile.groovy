package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

class PandocRevealCompile extends DefaultTask {

    @InputFile
    final RegularFileProperty pandocBinary = project.objects.fileProperty()
            .convention(project.pandoc.binary)

    @InputFile
    final RegularFileProperty markdownFile = project.objects.fileProperty()

    @InputFiles
    FileCollection revealJsFiles = project.files()

    @Optional
    @InputFiles
    FileCollection assetFiles = project.files()

    @Input
    final Property<Boolean> tableOfContents = project.objects.property(Boolean)
            .convention(false)

    @Input
    final Property<Integer> tableOfContentsDepth = project.objects.property(Integer)
            .convention(1)

    @Optional
    @InputFile
    final RegularFileProperty bibFile = project.objects.fileProperty()

    @Optional
    @InputFile
    final RegularFileProperty cslFile = project.objects.fileProperty()

    @Input
    final ListProperty pandocFilters = project.objects.listProperty(String)

    @Input
    final MapProperty pandocEnvironment = project.objects.mapProperty(String, String)

    @OutputDirectory
    final DirectoryProperty destDir = project.objects.directoryProperty()

    @TaskAction
    void compile() {
        project.copy {
            from revealJsFiles.collect {
                project.zipTree(it)
            }
            if (assetFiles) {
                from assetFiles
            }
            into destDir
            includeEmptyDirs = false
        }
        def command = [
                pandocBinary.get(),
                '--standalone',
                '--to', 'revealjs',
                '--variable', "revealjs-url=reveal.js-$project.revealJsVersion",
                '--output', destDir.file('index.html').get().asFile,
                markdownFile.get().asFile
        ]
        if (tableOfContents.get()) {
            command += [
                    '--toc',
                    '--toc-depth', tableOfContentsDepth.get()
            ]
        }
        if (bibFile.getOrNull()) {
            command += [
                    '--citeproc',
                    '--bibliography', bibFile.get().asFile
            ]
        }
        if (cslFile.getOrNull()) {
            command += [
                    '--csl', cslFile.get().asFile
            ]
        }
        pandocFilters.get().each { filter ->
            command += [
                    '--filter', filter
            ]
        }
        project.exec {
            commandLine command
            environment.putAll pandocEnvironment.get()
        }
    }
}
