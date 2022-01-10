package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.support.Storage
import com.github.dsisconeto.blaster.support.Zipper

class Extractor(private val rootDirectory: String, private val storage: Storage, private val zipper: Zipper) {


    fun extract(artifact: String, destinationDirectory: String) {
        storage.deleteDirectory(rootPath(destinationDirectory))
        zipper.unzip(rootPath(artifact), rootPath(destinationDirectory))
    }

    fun extract(artifact: String) {
        storage.renameDirectory(rootPath(artifact), tempArtifactPath(artifact))
        zipper.unzip(tempArtifactPath(artifact), rootPath(artifact))
        storage.deleteFile(tempArtifactPath(artifact))
    }

    fun extract(artifact: String, file: String, destinationDirectory: String) {
        storage.deleteFile(rootPath("$destinationDirectory/$file"))
        zipper.unzip(rootPath(artifact), file, rootPath(destinationDirectory))
    }

    private fun tempArtifactPath(artifact: String) = rootPath("$artifact.tmp")

    private fun rootPath(destination: String) = "$rootDirectory$destination"

}
