package org.m2ci.msp.pandocreveal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.bundling.Zip

class PandocRevealPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        final String REVEALJS = 'revealJS'

        project.pluginManager.apply BasePlugin

        project.repositories {
            exclusiveContent {
                forRepository {
                    ivy {
                        name 'GitHubHakimel'
                        url 'https://github.com/hakimel'
                        patternLayout {
                            artifact '[module]/archive/[revision].[ext]'
                        }
                        metadataSources {
                            artifact()
                        }
                    }
                }
                filter {
                    includeGroup 'se.hakimel.lab'
                }
            }
        }

        project.configurations.maybeCreate REVEALJS

        project.ext.revealJsVersion = '4.1.0'

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
