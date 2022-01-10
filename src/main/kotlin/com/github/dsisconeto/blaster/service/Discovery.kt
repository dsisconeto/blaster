package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.model.Ear
import com.github.dsisconeto.blaster.support.Zipper
import org.apache.logging.log4j.kotlin.logger

class Discovery(private val rootDirectory: String, private val zipper: Zipper) {


    fun subModulesOf(artifact: Ear): List<String> {
        logger().info("Descobrindo submódulos para explosão.")
        return zipper.artifactsInRoot("$rootDirectory${artifact.artifactDirectory}", listOf("war", "jar"))
    }


}
