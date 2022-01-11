package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.model.Ear
import org.apache.logging.log4j.kotlin.logger


class Blaster(
    private val extractor: Extractor,
    private val discovery: Discovery,
    private val manifestExtractor: ManifestExtractor
) {
    fun explode(ear: Ear) {
        extractEarArtifact(ear)
        discoveryModules(ear)
        extractEarSubModules(ear)
        extractManifests(ear)
        logger().info("Boomm!!!")
    }

    private fun discoveryModules(ear: Ear) {
        discovery.subModulesOf(ear).forEach { artifactName ->
            ear.addSubmoduleFrom(artifactName)
        }
    }

    private fun extractEarArtifact(ear: Ear) {
        logger().info("Explodindo EAR.")
        extractor.extract(ear.artifactDirectory, ear.explodedDirectory)
        logger().info("EAR explodido")
    }

    private fun extractEarSubModules(ear: Ear) {
        logger().info("Explodindo submódulos.")
        ear.forEachSubModule { subModule ->
            logger().info { "Explodindo submódulo: ${subModule.name}" }
            extractor.extract(ear.explodedDirectoryOf(subModule))
        }
        logger().info("Explosão dos submódulos finalizada.")
    }

    private fun extractManifests(ear: Ear) {
        manifestExtractor.extractFrom(ear)
    }


}
