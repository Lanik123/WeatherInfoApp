// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // Is third party plugins
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.daggerPlugin) apply false
}