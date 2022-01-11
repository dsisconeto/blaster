package com.github.dsisconeto.blaster.support

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.FileHeader


class Zipper {

    fun unzip(zipFile: String, directory: String) {
        ZipFile(zipFile).extractAll(directory)
    }

    fun unzip(zipFile: String, file: String, destinationDirectory: String) {
        ZipFile(zipFile).extractFile(file, destinationDirectory)
    }

    fun artifactsInRoot(zipFile: String, artifactsTypes: List<String>): List<String> {
        return ZipFile(zipFile)
            .fileHeaders
            .filter { fileHeader ->
                fileHeader.isInRoot() && fileHeader.hasAnyExtensionIn(artifactsTypes)
            }
            .map { fileHeader -> fileHeader.fileName }
    }


    private fun FileHeader.isInRoot() = this.fileName.count { it == '/' } == 0

    private fun FileHeader.hasAnyExtensionIn(artifactsTypes: List<String>): Boolean {
        return artifactsTypes.any { artifactType -> this.fileName.endsWith(artifactType) }
    }

}
