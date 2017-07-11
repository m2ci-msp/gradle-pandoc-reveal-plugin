import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

class PandocExec extends DefaultTask {

    @InputFile
    File markdownFile

    @OutputFile
    File htmlFile

    @TaskAction
    void compile() {
        project.exec {
            commandLine 'pandoc', '--standalone', '--smart', '--to', 'revealjs', markdownFile, '--output', htmlFile
        }
    }
}
