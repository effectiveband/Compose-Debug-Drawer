plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("${project.rootDir}/keystore")
            storePassword = "123456"
            keyAlias = "key"
            keyPassword = "123456"
        }
    }
    compileSdk = 32

    defaultConfig {
        applicationId = "effective.band.compose.drawer"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlin.Experimental",
            "-Xuse-experimental=kotlin.Experimental"
        )
    }
    buildTypes{
        release {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc01"
    }
}

dependencies {
    debugImplementation("band.effective:drawer-base:1.0.0")
    debugImplementation("band.effective:drawer-modules:1.0.0")
    debugImplementation("band.effective:drawer-location:1.0.0")

    implementation(libs.androidx.compose.activity)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.mock)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.coil.compose)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.timber)
}
