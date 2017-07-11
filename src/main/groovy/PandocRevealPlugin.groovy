import org.gradle.api.*
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.Copy

class PandocRevealPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.pluginManager.apply BasePlugin

        project.repositories {
            ivy {
                url 'https://github.com/hakimel'
                layout 'pattern', {
                    artifact '[module]/archive/[revision].[ext]'
                }
            }
        }

        project.configurations {
            revealJS
        }

        project.ext.revealJsVersion = '3.5.0'

        project.dependencies {
            revealJS group: 'se.hakimel.lab', name: 'reveal.js', version: project.revealJsVersion, ext: 'zip'
        }

        project.task('revealJS', type: Copy) {
            from project.configurations.revealJS.collect {
                project.zipTree(it)
            }
            into "$project.buildDir/reveal.js"
            eachFile {
                it.path = it.path - "reveal.js-$project.revealJsVersion/"
            }
            includeEmptyDirs = false
        }

        project.task('compileMarkdown', type: PandocExec) {
            dependsOn project.tasks.findByName('revealJS')
            project.tasks.findByName('assemble').dependsOn it
        }
    }
}
