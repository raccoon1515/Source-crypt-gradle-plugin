package net.raccoon.sourcecrypt

import org.gradle.api.tasks.TaskAction
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@Suppress("LeakingThis")
abstract class DecryptTask : CryptTask() {

    init {
        onlyIf { getInclude().get().all { file -> file.readText(StandardCharsets.UTF_8).matches(base64ValidRegex) } }
        doLast { project.logger.lifecycle("Resource files has been decrypted") }
    }

    @TaskAction
    override fun execute() {
        val password = getPassword()
        val salt = getSalt()
        getInclude().get().forEach { file ->
            val encryptedContent = Cryptor.decrypt(file.readBytes(), password.get(), salt.get())
            Files.write(file.toPath(), encryptedContent)
        }
    }

}
