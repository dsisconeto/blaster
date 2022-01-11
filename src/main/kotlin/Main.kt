import com.github.dsisconeto.blaster.model.Ear
import com.github.dsisconeto.blaster.service.Blaster
import com.github.dsisconeto.blaster.service.Discovery
import com.github.dsisconeto.blaster.service.Extractor
import com.github.dsisconeto.blaster.service.ManifestExtractor
import com.github.dsisconeto.blaster.support.Storage
import com.github.dsisconeto.blaster.support.Zipper
import java.io.InputStream
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.inputStream


fun main() {
    val properties = loadProperties()

    blaster().explode(
        Ear(
            properties.getProperty("ear.name"),
            properties.getProperty("ear.artifact-name"),
            properties.getProperty("ear.exploded"),
            properties.getProperty("ear.submodules-version")
        )
    )
}

private fun loadProperties(): Properties {
    return Properties().apply {
        load(propertiesFile())
    }
}

private fun propertiesFile(): InputStream {
    return applicationPath().resolve("blaster.properties").inputStream()
}

private fun blaster() = Blaster(extractor(), discovery(), manifestExtractor())

private fun manifestExtractor() = ManifestExtractor(extractor())

private fun discovery() = Discovery(currentPath(), Zipper())


private fun extractor() = Extractor(currentPath(), Storage(), Zipper())

private fun applicationPath() = Path(object {}.javaClass.protectionDomain.codeSource.location.path).parent.parent

private fun currentPath() = System.getProperty("user.dir")
