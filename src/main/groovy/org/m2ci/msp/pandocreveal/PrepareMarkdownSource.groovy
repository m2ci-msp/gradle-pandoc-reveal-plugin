package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

import static java.nio.charset.StandardCharsets.UTF_8

class PrepareMarkdownSource extends DefaultTask {

    @InputFile
    final RegularFileProperty markdownFile = project.objects.fileProperty()

    @Optional
    @InputFile
    final RegularFileProperty headerFile = project.objects.fileProperty()

    @OutputFile
    final RegularFileProperty destFile = project.objects.fileProperty()
            .convention(project.layout.buildDirectory.file('src.md'))

    @TaskAction
    void prepare() {
        destFile.get().asFile.withWriter UTF_8.name(), { dest ->
            if (headerFile.getOrNull()) {
                def options = new DumperOptions()
                options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                def yaml = new Yaml(options)
                def header = yaml.load(headerFile.get().asFile.newReader(UTF_8.name()))
                if (header.date instanceof Date)
                    header.date = header.date.format('EEE, MMM dd, yyyy')
                dest.println '---'
                yaml.dump header, dest
                dest.println '...'
            }
            dest << markdownFile.get().asFile.text
        }
    }
}
