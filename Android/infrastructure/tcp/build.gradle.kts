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
    implementation(project(":core:common"))
    implementation(project(":infrastructure:common"))
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(project(":core:tests"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}