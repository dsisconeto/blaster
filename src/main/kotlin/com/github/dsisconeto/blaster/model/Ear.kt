package com.github.dsisconeto.blaster.model

class Ear(
    name: String,
    artifactName: String,
    private val exploded: String,
    private val submodulesVersion: String
) :
    Module(name, artifactName) {

    private val subModules: MutableList<Module> = mutableListOf()


    val explodedDirectory: String
        get() = "$targetDirectory/$exploded"

    fun addSubmoduleFrom(artifactName: String) {
        val moduleName = artifactName.removeExtension().removeSuffix(submodulesVersion)
        subModules.add(Module(moduleName, artifactName))
    }


    fun explodedDirectoryOf(submodule: Module): String {
        return "${explodedDirectory}/${submodule.artifactName}"
    }

    fun forEachSubModule(function: (module: Module) -> Unit) {
        subModules.forEach(function)
    }


    private fun String.removeExtension(): String {
        return this.substring(0..this.length - 5)
    }

}
