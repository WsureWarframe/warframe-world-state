plugins {
    val kotlinVersion = "1.4.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.0-RC" // mirai-console version
}

group = "top.wsure.warframe"
version = "0.0.3-alpha"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

val miraiCoreVersion = "+" // 1.2.2
val miraiConsoleVersion = "+" //

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
}

kotlin.sourceSets.all { languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn") }