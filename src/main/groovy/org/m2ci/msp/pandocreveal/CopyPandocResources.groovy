package org.m2ci.msp.pandocreveal

import org.gradle.api.DefaultTask
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

class CopyPandocResources extends DefaultTask {

    private ArchiveOperations archiveOperations
    private FileSystemOperations fileSystemOperations

    @InputFiles
    final ConfigurableFileCollection configFiles = project.files()

    @OutputDirectory
    final DirectoryProperty destDir = project.objects.directoryProperty()

    @OutputFile
    final RegularFileProperty binary = project.objects.fileProperty()

    @Inject
    CopyPandocResources(ArchiveOperations archiveOperations, FileSystemOperations fileSystemOperations) {
        this.archiveOperations = archiveOperations
        this.fileSystemOperations = fileSystemOperations
    }

    @TaskAction
    void copyResources() {
        def fsOps = fileSystemOperations
        def archiveOps = archiveOperations
        fsOps.copy {
            into destDir
            from configFiles
            filesMatching '*.tar.gz', { tarDetails ->
                fsOps.copy {
                    into destDir
                    from archiveOps.tarTree(tarDetails.file)
                }
            }
            filesMatching '*.zip', { zipDetails ->
                fsOps.copy {
                    into destDir
                    from archiveOps.zipTree(zipDetails.file)
                }
            }
        }
    }
}
