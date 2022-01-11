package com.github.dsisconeto.blaster.support

import net.lingala.zip4j.ZipFile
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ZipperTest {

    private lateinit var zipper: Zipper

    @TempDir
    lateinit var tempDirectory: Path

    @BeforeEach
    fun setUp() {
        zipper = Zipper()
    }

    @Test
    fun `should unzip a file to a destination directory`() {
        val sisconetoJar = createArtifact("sisconeto.jar")
        val destinationDirectory = createDirectory("classes")
        assertFalse { destinationDirectory.resolve("META-INF/MANIFEST.MF").exists() }

        zipper.unzip(sisconetoJar.toString(), "META-INF/MANIFEST.MF", destinationDirectory.toString())

        assertTrue { destinationDirectory.resolve("META-INF/MANIFEST.MF").exists() }
    }

    @Test
    fun `should extract a folder to a destination directory`() {
        val sisconetoJar = createArtifact("sisconeto.jar")
        val destinationDirectory = createDirectory("destination")

        zipper.unzip(sisconetoJar.toString(), destinationDirectory.toString())

        assertTrue { destinationDirectory.exists() }
        assertTrue { destinationDirectory.resolve("META-INF/MANIFEST.MF").exists() }
    }

    @Test
    fun `should return artifacts in root of the zipped artifact`() {
        val earPath = tempDirectory.resolve("Sisconeto.ear")
        ZipFile(earPath.toString()).run {
            addFile(createArtifact("SisconetoDomain.jar"))
            addFile(createArtifact("SisconetoWeb.war"))
            addFolder(createDirectory("lib").also { libDirectory ->
                createArtifact(libDirectory.toPath(), "lib.jar")
            })
            addFile(createFileLog4jProperties())
        }
        assertTrue { earPath.toFile().exists() }

        val artifacts = zipper.artifactsInRoot(earPath.toString(), listOf("war", "jar"))


        assertEquals(2, artifacts.count())
        assertEquals("SisconetoDomain.jar", artifacts[0])
        assertEquals("SisconetoWeb.war", artifacts[1])
    }

    private fun createFileLog4jProperties() = Files.createFile(tempDirectory.resolve("log4j.properties")).toFile()

    private fun createArtifact(artifactName: String): File {
        return createArtifact(tempDirectory, artifactName).toFile()
    }

    private fun createArtifact(inDirectory: Path, artifactName: String): Path {
        return inDirectory.resolve(artifactName).also {
            ZipFile(it.toString()).addFolder(createMetaInfWithManifest())
        }
    }

    private fun createMetaInfWithManifest(): File {
        if (tempDirectory.resolve("META-INF").toFile().exists()) {
            return tempDirectory.resolve("META-INF").toFile()
        }
        val metaInf = createDirectory("META-INF")
        Files.createFile(metaInf.resolve("MANIFEST.MF").toPath())
        assertTrue { metaInf.exists() }
        return metaInf
    }


    private fun createDirectory(directory: String) = Files.createDirectory(tempDirectory.resolve(directory)).toFile()
}
