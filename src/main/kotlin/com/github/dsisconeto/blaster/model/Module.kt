package com.github.dsisconeto.blaster.model

open class Module(val name: String, val artifactName: String) {

    protected val targetDirectory = "/$name/target"

    val artifactDirectory: String
        get() = "$targetDirectory/$artifactName"





}
