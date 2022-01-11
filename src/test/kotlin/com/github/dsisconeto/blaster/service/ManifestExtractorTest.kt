package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.model.Ear
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.inOrder

class ManifestExtractorTest {


    @Mock
    private lateinit var extractor: Extractor
    private lateinit var manifestExtractor: ManifestExtractor

    @BeforeEach
    fun setUp() {
        openMocks(this)
        manifestExtractor = ManifestExtractor(extractor)
    }

    @Test
    fun `should extract manifest from package artifacts`() {
        val ear = Ear(
            "SisconetoEAR",
            "Sisconeto.ear",
            "exploded.ear",
            "-1.0-SNAPSHOT",
        ).apply {
            addSubmoduleFrom("SisconetoWeb-1.0-SNAPSHOT.war")
            addSubmoduleFrom("SisconetoDomain-1.0-SNAPSHOT.jar")
        }

        manifestExtractor.extractFrom(ear)

        inOrder(extractor) {
            verify(extractor).extract(
                "/SisconetoWeb/target/SisconetoWeb-1.0-SNAPSHOT.war",
                "META-INF/MANIFEST.MF",
                "/SisconetoEAR/target/exploded.ear/SisconetoWeb-1.0-SNAPSHOT.war"
            )
            verify(extractor).extract(
                "/SisconetoDomain/target/SisconetoDomain-1.0-SNAPSHOT.jar",
                "META-INF/MANIFEST.MF",
                "/SisconetoEAR/target/exploded.ear/SisconetoDomain-1.0-SNAPSHOT.jar"
            )
        }
    }
}

