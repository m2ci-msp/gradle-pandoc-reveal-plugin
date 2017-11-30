package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

class PandocExec extends DefaultTask {

    @InputFile
    File markdownFile

    @Optional
    @InputFile
    File bibFile

    @Optional
    @InputFile
    File cslFile

    @OutputFile
    File htmlFile

    @TaskAction
    void compile() {
        def command = ['pandoc', '--standalone', '--to', 'revealjs', markdownFile, '--output', htmlFile]
        if (bibFile) {
            command += ['--bibliography', bibFile]
        }
        if (cslFile) {
            command += ['--csl', cslFile]
        }
        project.exec {
            commandLine command
        }
    }
}
