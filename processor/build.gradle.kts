plugins {
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":annotation"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.7.20-1.0.7")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
}
