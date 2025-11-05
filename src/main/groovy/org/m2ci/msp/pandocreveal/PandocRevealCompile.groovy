package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations

import javax.inject.Inject

class PandocRevealCompile extends DefaultTask {

    private ExecOperations execOperations

    @InputFile
    final RegularFileProperty pandocBinary = project.objects.fileProperty()

    @InputFile
    final RegularFileProperty markdownFile = project.objects.fileProperty()

    @Input
    final Property<String> revealVersion = project.objects.property(String)

    @Input
    final Property<Boolean> tableOfContents = project.objects.property(Boolean)

    @Input
    final Property<Integer> tableOfContentsDepth = project.objects.property(Integer)

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

    @Inject
    PandocRevealCompile(ExecOperations execOperations) {
        this.execOperations = execOperations
    }

    @TaskAction
    void compile() {
        def command = [
                pandocBinary.get(),
                '--standalone',
                '--to', 'revealjs',
                '--variable', "revealjs-url=reveal.js-${revealVersion.get()}",
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
        execOperations.exec {
            commandLine command
            workingDir = destDir.get()
            environment.putAll pandocEnvironment.get()
        }
    }
}
