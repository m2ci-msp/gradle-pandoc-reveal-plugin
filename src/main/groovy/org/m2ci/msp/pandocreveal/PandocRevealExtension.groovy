package org.m2ci.msp.pandocreveal

import org.gradle.api.Project
import org.gradle.api.provider.Property

class PandocRevealExtension {

    Project project

    Property<String> pandocVersion = project.objects.property(String)
            .convention('3.7.0.2')
    Property<String> revealVersion = project.objects.property(String)
            .convention('5.2.1')

    PandocRevealExtension(Project project) {
        this.project = project
    }

}
