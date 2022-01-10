package com.github.dsisconeto.blaster.support

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class StorageTest {


    private lateinit var storage: Storage

    @TempDir
    lateinit var tempDirectory: Path

    @BeforeEach
    internal fun setUp() {
        storage = Storage()
    }

    @Test
    fun `should dele a file if exists`() {
        val file = Files.createFile(tempDirectory.resolve("file.txt"))
        assertTrue { file.exists() }

        storage.deleteFile(file.toString())
        assertFalse { file.exists() }

        storage.deleteFile(file.toString())
        assertFalse { file.exists() }
    }

    @Test
    fun `should delete a directory with files`() {
        val files = Files.createDirectory(tempDirectory.resolve("files"))
        Files.createFile(files.resolve("file.txt"))
        assertTrue { files.exists() }
        assertFalse { files.toFile().listFiles()?.isEmpty() ?: true }

        storage.deleteDirectory(files.toString())
        assertFalse { files.exists() }

        storage.deleteDirectory(files.toString())
        assertFalse { files.exists() }
    }

    @Test
    fun `should rename directory`() {
        val files = Files.createDirectory(tempDirectory.resolve("files"))
        val newFile = tempDirectory.resolve("newName")
        assertTrue { files.exists() }
        assertFalse { newFile.toFile().exists() }

        storage.renameDirectory(files.toString(), newFile.toString())

        assertFalse { files.exists() }
        assertTrue { newFile.toFile().exists() }
    }
}
