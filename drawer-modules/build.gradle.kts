plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc01"
    }

    kotlinOptions {
        jvmTarget = "1.8"

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlin.Experimental",
            "-Xuse-experimental=kotlin.Experimental"
        )
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("ComposeDebugDrawer") {
            groupId = PublishConfig.groupId
            artifactId = PublishConfig.DrawerModules.artifactId
            version = PublishConfig.drawerVersion

            afterEvaluate {
                from(components["release"])
            }
        }


        repositories {
            maven {
                url = uri(
                    getLocalProperty(
                        "myMavenRepoReadUrl",
                        "${project.rootDir}/local.properties"
                    )
                )
                credentials {
                    username = getLocalProperty(
                        "username",
                        "${project.rootDir}/local.properties"
                    ).toString()
                    password = getLocalProperty(
                        "password",
                        "${project.rootDir}/local.properties"
                    ).toString()
                }
            }
        }
    }
}


dependencies {
    implementation("effective.band:drawer-base:1.0.0")

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.core)

    implementation(libs.okhttp.client)
    implementation(libs.okhttp.logging)
    implementation(libs.timber)
    implementation(libs.leak.canary)
    implementation(libs.androidx.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.mock)
    implementation(libs.process.phoenix)
}
