plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
    }
}
dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":infrastructure:common"))
    implementation(libs.junit)
    implementation(libs.sqlite)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.coroutines.jvm.test)
}