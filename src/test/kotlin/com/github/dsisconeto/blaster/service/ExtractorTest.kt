package com.github.dsisconeto.blaster.service

import com.github.dsisconeto.blaster.support.Storage
import com.github.dsisconeto.blaster.support.Zipper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.inOrder

class ExtractorTest {

    @Mock
    private lateinit var storage: Storage

    @Mock
    private lateinit var zipper: Zipper

    private lateinit var extractor: Extractor

    @BeforeEach
    internal fun setUp() {
        openMocks(this)
        extractor = Extractor("/root", storage, zipper)
    }


    @Test
    fun `should unzip the artifact when destination directory has the same name as the artifact`() {
        extractor.extract("/app.jar")

        inOrder(storage, zipper) {
            verify(storage).renameDirectory("/root/app.jar", "/root/app.jar.tmp")
            verify(zipper).unzip("/root/app.jar.tmp", "/root/app.jar")
            verify(storage).deleteFile("/root/app.jar.tmp")
        }
    }

    @Test
    fun `should delete directory before extract the artifact`() {
        extractor.extract("/app.jar", "/exploded.jar")

        inOrder(storage, zipper) {
            verify(storage).deleteDirectory("/root/exploded.jar")
            verify(zipper).unzip("/root/app.jar", "/root/exploded.jar")
        }
    }

    @Test
    fun `should extract a file of artifact`() {
        extractor.extract("/app.jar", "META-INF/MANIFEST.MF", "/exploded.jar/target/classes")

        inOrder(storage, zipper) {
            verify(storage).deleteFile("/root/exploded.jar/target/classes/META-INF/MANIFEST.MF")
            verify(zipper).unzip(
                "/root/app.jar",
                "META-INF/MANIFEST.MF",
                "/root/exploded.jar/target/classes"
            )
        }
    }
}

