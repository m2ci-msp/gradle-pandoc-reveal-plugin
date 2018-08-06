package org.m2ci.msp.pandocreveal

import org.gradle.api.*
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.bundling.Zip

class PandocRevealPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        final String REVEALJS = 'revealJS'

        project.pluginManager.apply BasePlugin

        project.repositories {
            ivy {
                url 'https://github.com/hakimel'
                layout 'pattern', {
                    artifact '[module]/archive/[revision].[ext]'
                }
            }
        }

        project.configurations.maybeCreate REVEALJS

        project.ext.revealJsVersion = '3.7.0'

        project.dependencies.add REVEALJS, [group: 'se.hakimel.lab', name: 'reveal.js', version: project.revealJsVersion, ext: 'zip']

        project.tasks.register 'compileReveal', PandocRevealCompile, {
            revealJsFiles = project.files(project.configurations.getByName(REVEALJS))
            destDir = project.layout.buildDirectory.dir('slides')
        }

        project.tasks.register 'packageReveal', Zip, {
            from project.tasks.named('compileReveal').get().destDir
        }

        project.artifacts {
            'default' project.tasks.named('packageReveal')
        }
    }
}
