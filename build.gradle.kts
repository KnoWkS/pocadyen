// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.11.1" apply false // à adapter
    kotlin("android") version "2.2.0" apply false
    alias(libs.plugins.kotlin.compose) apply false
}
