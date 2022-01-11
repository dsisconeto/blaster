package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.model.Ear
import org.apache.logging.log4j.kotlin.logger

class ManifestExtractor(private val extractor: Extractor) {
    fun extractFrom(ear: Ear) {
        logger().info("Explodindo MANIFEST.MFs dos submódulos.")
        ear.forEachSubModule { subModule ->
            logger().info { "Explodindo MANIFEST.MF do submódulo: ${subModule.name}" }
            extractor.extract(
                subModule.artifactDirectory,
                "META-INF/MANIFEST.MF",
                ear.explodedDirectoryOf(subModule)
            )
        }
        logger().info("Explosão dos MANIFEST.MFs dos submódulos finaliza.")
    }
}
