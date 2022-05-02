package net.raccoon.sourcecrypt

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.charset.StandardCharsets

abstract class CryptTask : DefaultTask() {

    @TaskAction
    abstract fun execute()

    @Internal
    override fun getGroup(): String = "Crypt"

    @Input
    abstract fun getInclude(): SetProperty<File>

    @Input
    abstract fun getSalt(): Property<ByteArray>

    @Input
    abstract fun getPassword(): Property<CharArray>

    @get:Internal
    protected val base64ValidRegex = Regex("^([A-Za-z\\d+/]{4})*([A-Za-z\\d+/]{3}=|[A-Za-z\\d+/]{2}==)?\$")
}
