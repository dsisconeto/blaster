package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.model.Ear
import com.github.dsisconeto.blaster.support.Zipper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class DiscoveryTest {


    @Mock
    private lateinit var zipper: Zipper
    private lateinit var discovery: Discovery


    @BeforeEach
    fun setUp() {
        openMocks(this)
        discovery = Discovery("/root", zipper)
    }

    @Test
    fun `should discovery modules in root of artifact`() {
        whenever(zipper.artifactsInRoot("/root/SisconetoEAR/target/Sisconeto.ear", listOf("war", "jar")))
            .thenReturn(
                listOf(
                    "SisconetoWeb-1.0-SNAPSHOT.war",
                    "SisconetoDomain-1.0-SNAPSHOT.jar",
                ),
            )

        val ear = Ear(
            "SisconetoEAR",
            "Sisconeto.ear",
            "exploded.ear",
            "-1.0-SNAPSHOT"
        )

        val modules = discovery.subModulesOf(ear)

        assertEquals(modules.count(), 2)

        assertEquals("SisconetoWeb-1.0-SNAPSHOT.war", modules[0])
        assertEquals("SisconetoDomain-1.0-SNAPSHOT.jar", modules[1])
    }
}
