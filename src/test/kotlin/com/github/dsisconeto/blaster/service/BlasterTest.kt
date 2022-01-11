package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.model.Ear
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.whenever


class BlasterTest() {

    private lateinit var blaster: Blaster


    @Mock
    private lateinit var extractor: Extractor

    @Mock
    private lateinit var discovery: Discovery

    @Mock
    private lateinit var manifestExtractor: ManifestExtractor


    @BeforeEach
    internal fun setUp() {
        openMocks(this)
        blaster = Blaster(extractor, discovery, manifestExtractor)
    }


    @Test
    fun `should extract ear in exploded directory`() {
        val ear = Ear(
            "SisconetoEAR",
            "Sisconeto.ear",
            "exploded.ear",
            "-1.0-SNAPSHOT",
        )
        whenever(discovery.subModulesOf(ear)).thenReturn(
            listOf(
                "SisconetoWeb-1.0-SNAPSHOT.war",
                "SisconetoDomain-1.0-SNAPSHOT.jar"
            )
        )

        blaster.explode(ear)

        inOrder(extractor, manifestExtractor) {
            verify(extractor).extract(
                "/SisconetoEAR/target/Sisconeto.ear",
                "/SisconetoEAR/target/exploded.ear"
            )
            verify(extractor).extract("/SisconetoEAR/target/exploded.ear/SisconetoWeb-1.0-SNAPSHOT.war")
            verify(extractor).extract("/SisconetoEAR/target/exploded.ear/SisconetoDomain-1.0-SNAPSHOT.jar")
            verify(manifestExtractor).extractFrom(ear)
        }
    }


}
