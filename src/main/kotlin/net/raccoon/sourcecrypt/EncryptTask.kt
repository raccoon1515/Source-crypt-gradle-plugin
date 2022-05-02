package net.raccoon.sourcecrypt

import org.gradle.api.tasks.TaskAction
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@Suppress("LeakingThis")
abstract class EncryptTask : CryptTask() {

    init {
        onlyIf { getInclude().get().none { file -> file.readText(StandardCharsets.UTF_8).matches(base64ValidRegex) } }
        doLast {
            project.logger.lifecycle("Resource files has been encrypted")
        }
    }

    @TaskAction
    override fun execute() {
        val password = getPassword()
        val salt = getSalt()
        getInclude().get().forEach { file ->
            val encryptedContent = Cryptor.encrypt(file.readBytes(), password.get(), salt.get())
            Files.write(file.toPath(), encryptedContent)
        }
    }

}
