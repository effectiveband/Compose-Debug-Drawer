import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

version = "1.0.0"

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
    }
}

val javadocJar = task("javadocJar", type = Jar::class) {
    archiveClassifier.set("javadoc")
}

artifacts {
    archives(javadocJar)
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("ComposeDebugDrawer") {
                groupId = "effective.band"
                artifactId = "drawer"
                version = version

                artifact(javadocJar)
                artifact("$buildDir/outputs/aar/drawer-release.aar")

                pom {
                    name.set("Compose Debug Drawer")
                }
            }
        }

        repositories {
            maven {
                url = uri(getLocalProperty("myMavenRepoWriteUrl"))
                credentials {
                    username = getLocalProperty("username").toString()
                    password = getLocalProperty("password").toString()
                }
            }
        }
    }
}



dependencies {

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.core)

    implementation(libs.timber)

    implementation(libs.leak.canary)

    implementation(libs.okhttp.client)
    implementation(libs.okhttp.logging)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.mock)

    implementation(libs.process.phoenix)
}

fun getLocalProperty(key: String, file: String = "${project.rootDir}/local.properties"): Any {
    val properties = Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties.getProperty(key)
}