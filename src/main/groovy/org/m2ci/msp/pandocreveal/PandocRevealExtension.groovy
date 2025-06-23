package org.m2ci.msp.pandocreveal

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

class PandocRevealExtension {

    Project project

    final Property<String> pandocVersion = project.objects.property(String)
            .convention('3.7.0.2')
    final Property<String> revealVersion = project.objects.property(String)
            .convention('5.2.1')

    final RegularFileProperty markdownFile = project.objects.fileProperty()
    final RegularFileProperty headerFile = project.objects.fileProperty()
    final RegularFileProperty bibFile = project.objects.fileProperty()
    final RegularFileProperty cslFile = project.objects.fileProperty()

    FileCollection assetFiles = project.objects.fileCollection()

    final Property<Boolean> tableOfContents = project.objects.property(Boolean)
            .convention(false)
    final Property<Integer> tableOfContentsDepth = project.objects.property(Integer)
            .convention(1)

    final ListProperty pandocFilters = project.objects.listProperty(String)
    final MapProperty pandocEnvironment = project.objects.mapProperty(String, String)

    final DirectoryProperty destDir = project.objects.directoryProperty()
            .convention(project.layout.buildDirectory.dir('slides'))

    PandocRevealExtension(Project project) {
        this.project = project
    }

}
