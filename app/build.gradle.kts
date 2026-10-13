import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

val uploadPropertiesFile = rootProject.file("play-upload.properties")
val uploadProperties = Properties()

if (uploadPropertiesFile.isFile) {
    uploadPropertiesFile.inputStream().use(uploadProperties::load)
    listOf("storeFile", "storePassword", "keyAlias", "keyPassword").forEach { name ->
        require(!uploadProperties.getProperty(name).isNullOrBlank()) {
            "Missing $name in play-upload.properties"
        }
    }
}

android {
    namespace = "com.togetherwords"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.togetherwords"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures { compose = true }

    androidResources { localeFilters += listOf("en", "es") }

    signingConfigs {
        if (uploadPropertiesFile.isFile) {
            create("playUpload") {
                storeFile = rootProject.file(uploadProperties.getProperty("storeFile"))
                storePassword = uploadProperties.getProperty("storePassword")
                keyAlias = uploadProperties.getProperty("keyAlias")
                keyPassword = uploadProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        getByName("release") {
            if (uploadPropertiesFile.isFile) {
                signingConfig = signingConfigs.getByName("playUpload")
            }
        }
    }

    bundle {
        language { enableSplit = false }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val verifyPlayUploadSigning =
    tasks.register("verifyPlayUploadSigning") {
        doLast {
            check(uploadPropertiesFile.isFile) {
                "Create play-upload.properties from play-upload.properties.example before building a Play release."
            }
            check(rootProject.file(uploadProperties.getProperty("storeFile")).isFile) {
                "The Play upload keystore listed in play-upload.properties does not exist."
            }
        }
    }

tasks
    .matching { it.name == "bundleRelease" }
    .configureEach {
        dependsOn(verifyPlayUploadSigning)
    }

dependencies {
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.compose.ui:ui:1.11.4")
    implementation("androidx.compose.foundation:foundation:1.11.4")
    implementation("androidx.compose.material3:material3:1.4.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.11.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.11.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.11.4")
}
