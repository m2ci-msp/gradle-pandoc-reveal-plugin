package org.m2ci.msp.pandocreveal

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface PandocExtension {

    Property<String> getVersion()

    RegularFileProperty getBinary()

}
