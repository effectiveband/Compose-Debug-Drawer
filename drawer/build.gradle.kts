import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

version = "1.0.0"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val androidSourcesJar = task("androidSourcesJar", type = org.gradle.jvm.tasks.Jar::class){
    archiveClassifier.set("sources")
    if (project.plugins.hasPlugin("com.android.library") ) {
        from(android.sourceSets.getByName("main").java.srcDirs)
//        from(android.sourceSets.getByName("main").kotlin.srcDirs("kotlin"))
    } else {
        from(sourceSets.getByName("main").allSource.srcDirs)
    }
}

val javadocJar = task("javadocJar", type = org.gradle.jvm.tasks.Jar::class){
    archiveClassifier.set("javadoc")
}

artifacts {
    archives(androidSourcesJar)
    archives(javadocJar)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("Compose Debug Drawer") {
                groupId = "effective.band"
                artifactId = "drawer"
                version = version

                artifact(androidSourcesJar)
                artifact(javadocJar)
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

fun getLocalProperty(key: String, file: String = "local.properties"): Any {
    val properties = Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties.getProperty(key)
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


dependencies {

    api(project(":drawer-ui-modules"))
    api(project(":drawer-modules"))
    api(project(":drawer-base"))
    api(project(":drawer-okhttplogger"))
    api(project(":drawer-retrofit"))
    api(project(":drawer-leak"))
    api(project(":drawer-timber"))
    api(project(":drawer-location"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.core)
}