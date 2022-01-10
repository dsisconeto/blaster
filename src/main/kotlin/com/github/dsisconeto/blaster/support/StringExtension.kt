package com.github.dsisconeto.blaster.support


fun String.removeExtension(): String {
    return this.substring(0..this.length - 5)
}
