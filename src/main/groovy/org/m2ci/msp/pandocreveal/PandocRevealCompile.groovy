package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*

class PandocRevealCompile extends DefaultTask {

    @InputFile
    final RegularFileProperty markdownFile = newInputFile()

    @InputFiles
    FileCollection revealJsFiles = project.files()

    @Optional
    @InputFiles
    FileCollection assetFiles = project.files()

    @Optional
    @InputFile
    final RegularFileProperty bibFile = newInputFile()

    @Optional
    @InputFile
    final RegularFileProperty cslFile = newInputFile()

    @OutputDirectory
    final DirectoryProperty destDir = newOutputDirectory()

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
        def command = ['pandoc', '--standalone', '--to', 'revealjs', '-V', "revealjs-url=reveal.js-$project.revealJsVersion",
                       markdownFile.get().asFile, '--output', destDir.file('index.html').get().asFile]
        if (bibFile.getOrNull()) {
            command += ['--bibliography', bibFile.get().asFile]
        }
        if (cslFile.getOrNull()) {
            command += ['--csl', cslFile.get().asFile]
        }
        project.exec {
            commandLine command
        }
    }
}
