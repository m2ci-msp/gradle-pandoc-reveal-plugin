package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class UnpackPandoc extends DefaultTask {

    @Input
    final Property<String> config = project.objects.property(String)

    @Input
    final Property<String> version = project.objects.property(String)

    @OutputFile
    final RegularFileProperty binary = project.objects.fileProperty()
            .convention(project.layout.buildDirectory.file('pandoc'))

    @TaskAction
    void unpack() {
        project.copy {
            into temporaryDir
            from project.configurations.named(config.get())
            filesMatching('*.tar.gz') { tarDetails ->
                project.copy {
                    into temporaryDir
                    from project.tarTree(tarDetails.file)
                }
            }
            filesMatching('*.zip') { zipDetails ->
                project.copy {
                    into temporaryDir
                    from project.zipTree(zipDetails.file)
                }
            }
        }
        project.copy {
            into project.layout.buildDirectory
            from "$temporaryDir/pandoc-${version.get()}/bin"
            include 'pandoc'
        }
    }
}
