plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc01"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
            artifactId = PublishConfig.DrawerLocationModule.artifactId
            version = PublishConfig.drawerVersion

            afterEvaluate {
                from(components["release"])
            }
        }

        repositories {
            maven {
                url = uri(
                    getLocalProperty(
                        "myMavenRepoWriteUrl",
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
    implementation("effective.band:drawer:1.0.0")


    implementation(libs.androidx.compose.activity)


    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.timber)
    implementation(libs.androidx.core)
    implementation(libs.play.services.location)
}