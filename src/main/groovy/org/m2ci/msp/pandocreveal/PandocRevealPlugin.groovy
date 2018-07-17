package org.m2ci.msp.pandocreveal

import org.gradle.api.*
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.Copy

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

        project.ext.revealJsVersion = '3.6.0'

        project.dependencies.add REVEALJS, [group: 'se.hakimel.lab', name: 'reveal.js', version: project.revealJsVersion, ext: 'zip']

        project.task(REVEALJS, type: Copy) {
            from project.configurations.getByName(REVEALJS).collect {
                project.zipTree(it)
            }
            into "$project.buildDir/reveal.js"
            eachFile {
                it.path = it.path - "reveal.js-$project.revealJsVersion/"
            }
            includeEmptyDirs = false
        }

        project.task('compileMarkdown', type: PandocExec) {
            dependsOn project.tasks.findByName(REVEALJS)
            project.tasks.findByName('assemble').dependsOn it
        }
    }
}
