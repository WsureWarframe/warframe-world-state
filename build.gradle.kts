import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.4.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
//    kotlin("kapt") version "1.4.21"
    id("net.mamoe.mirai-console") version "2.0-RC" // mirai-console version
}

group = "top.wsure.warframe"
version = "0.0.4"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

val miraiCoreVersion = "+" // 2.0.0
val miraiConsoleVersion = "+" //

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    implementation(kotlin("stdlib-jdk8"))
    // SQLite
    implementation("org.xerial:sqlite-jdbc:3.34.0")

    implementation("org.sqldroid:sqldroid:1.0.3")

    implementation("com.baomidou:mybatis-plus:3.4.2")

//    implementation ("com.thinkinglogic.builder:kotlin-builder-annotation:1.2.1")
//    kapt("com.thinkinglogic.builder:kotlin-builder-processor:1.2.1")
}

kotlin.sourceSets.all { languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn") }
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}