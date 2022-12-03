package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

class PandocRevealCompile extends DefaultTask {

    @InputFile
    final RegularFileProperty markdownFile = project.objects.fileProperty()

    @Optional
    @InputFile
    final RegularFileProperty headerFile = project.objects.fileProperty()

    @InputFiles
    FileCollection revealJsFiles = project.files()

    @Optional
    @InputFiles
    FileCollection assetFiles = project.files()

    @Optional
    @InputFile
    final RegularFileProperty bibFile = project.objects.fileProperty()

    @Optional
    @InputFile
    final RegularFileProperty cslFile = project.objects.fileProperty()

    @OutputDirectory
    final DirectoryProperty destDir = project.objects.directoryProperty()

    @TaskAction
    void compile() {
        def srcFile = markdownFile.get().asFile
        if (headerFile.getOrNull()) {
            srcFile = project.file("$temporaryDir/src.md")
            srcFile.withWriter 'UTF-8', { writer ->
                def options = new DumperOptions()
                options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                def yaml = new Yaml(options)
                def header = yaml.load(headerFile.get().asFile.newReader('UTF-8'))
                if (header.date instanceof Date)
                    header.date = header.date.format('EEE, MMM dd, yyyy')
                writer.println '---'
                yaml.dump header, writer
                writer.println '...'
                writer << markdownFile.get().asFile.text
            }
        }
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
                'pandoc', '--standalone',
                '--to', 'revealjs',
                '--variable', "revealjs-url=reveal.js-$project.revealJsVersion",
                '--output', destDir.file('index.html').get().asFile,
                srcFile
        ]
        if (bibFile.getOrNull()) {
            command += [
                    '--bibliography', bibFile.get().asFile
            ]
        }
        if (cslFile.getOrNull()) {
            command += [
                    '--csl', cslFile.get().asFile
            ]
        }
        project.exec {
            commandLine command
        }
    }
}
