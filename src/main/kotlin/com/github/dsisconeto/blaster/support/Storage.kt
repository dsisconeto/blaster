package com.github.dsisconeto.blaster.support

import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path


class Storage {

    fun deleteFile(path: String) {
        Files.deleteIfExists(Path(path))
    }

    fun renameDirectory(currentName: String, newName: String) {
        File(currentName).renameTo(File(newName))
    }

    fun deleteDirectory(directory: String) {
        File(directory).deleteRecursively()
    }

}
