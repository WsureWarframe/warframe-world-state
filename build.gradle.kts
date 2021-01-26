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

    implementation("com.h2database:h2:1.4.200")

    implementation("org.ktorm:ktorm-core:3.3.0")

    implementation(files("${System.getProperty("java.home")}/lib/rt.jar"))

}

//sourceSets{
//    main {
//        java {
//            exclude("apple/**")
//            exclude("com/**")
//            exclude("java/**")
////        exclude("javax/**")
//            exclude("/jdk/**")
//            exclude("sun/**")
//            exclude("org/ietf/**")
//            exclude("org/jcp/**")
//            exclude("org/omg/**")
//            exclude("org/w3c/**")
//            exclude("org/xml/**")
////        ("javax/sql/**")
//        }
//    }
//}


afterEvaluate{
    tasks.withType<org.gradle.jvm.tasks.Jar>{
        exclude("apple/**")
        exclude("com/**")
        exclude("java/**")
        exclude("jdk/**")
        exclude("sun/**")
        exclude("org/ietf/**")
        exclude("org/jcp/**")
        exclude("org/omg/**")
        exclude("org/w3c/**")
        exclude("org/xml/**")
//        include("javax/sql/**")
        exclude { file ->
            file.relativePath.pathString.contains("javax/") and !file.relativePath.pathString.contains("javax/sql")
        }
    }
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