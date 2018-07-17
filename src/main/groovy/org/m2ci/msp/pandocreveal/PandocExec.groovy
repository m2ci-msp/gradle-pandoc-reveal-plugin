package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*

class PandocExec extends DefaultTask {

    @InputFile
    RegularFileProperty markdownFile = newInputFile()

    @Optional
    @InputFile
    RegularFileProperty bibFile = newInputFile()

    @Optional
    @InputFile
    RegularFileProperty cslFile = newInputFile()

    @OutputFile
    RegularFileProperty htmlFile = newOutputFile()

    @TaskAction
    void compile() {
        def command = ['pandoc', '--standalone', '--to', 'revealjs', markdownFile.get().asFile, '--output', htmlFile.get().asFile]
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
