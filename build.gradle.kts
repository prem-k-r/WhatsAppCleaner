// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.diffplug.spotless") version "7.0.2"
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    java {
        licenseHeaderFile("spotless-header")
        importOrder("android", "androidx", "com", "java", "")
        target("app/src/**/*.java")
        googleJavaFormat("1.25.2").aosp()
        removeUnusedImports()
    }
    kotlin {
        licenseHeaderFile("spotless-header")
        target("app/src/**/*.kt")
        ktlint("1.5.0")
            .editorConfigOverride(
                mapOf(
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                    "ktlint_standard_trailing-comma-on-declaration-site" to "disabled",
                    "ktlint_standard_trailing-comma-on-call-site" to "disabled",
                    "ktlint_standard_if-else-bracing" to "disabled",
                    "ktlint_standard_multiline-if-else" to "disabled",
                ),
            )
        trimTrailingWhitespace()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint("1.5.0")
    }
}
