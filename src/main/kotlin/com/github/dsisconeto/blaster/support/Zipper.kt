package com.github.dsisconeto.blaster.support

import net.lingala.zip4j.ZipFile


class Zipper {

    fun unzip(zipFile: String, directory: String) {
        ZipFile(zipFile).extractAll(directory);
    }

    fun unzip(zipFile: String, file: String, destinationDirectory: String) {
        ZipFile(zipFile).extractFile(file, destinationDirectory)
    }

    fun artifactsInRoot(zipFile: String, artifactsTypes: List<String>): List<String> {
        return ZipFile(zipFile)
            .fileHeaders
            .map { fileHeader -> fileHeader.fileName }
            .filter { fileName ->
                artifactsTypes.any { artifactsType ->
                    fileName.endsWith(artifactsType) && fileName.count { it == '/' } == 0
                }
            }

    }
}
