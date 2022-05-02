package net.raccoon.sourcecrypt

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Nested
import java.io.File
import java.nio.charset.StandardCharsets

abstract class CryptExtension(project: Project) {

    @Nested
    abstract fun getResources(): Resources

    fun resources(action: Action<in Resources>) {
        action.execute(getResources())
    }

    var password: Property<CharArray> = project.objects.property(CharArray::class.java)
        .convention(getProperty(project, "encrypt.password").toCharArray())

    val salt: Property<ByteArray> = project.objects.property(ByteArray::class.java)
        .convention(getProperty(project, "encrypt.salt").toByteArray(StandardCharsets.UTF_8))

}

abstract class Resources(project: Project) {

    var include: SetProperty<File> = project.objects.setProperty(File::class.java)
        .convention(project.fileTree("src/main/resources").files)
}

private fun getProperty(project: Project, property: String): String = project.property(property) as String?
    ?: throw IllegalArgumentException("Missing $property property")
