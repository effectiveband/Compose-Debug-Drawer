dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://effective-android.bytesafe.dev/maven/debug-drawer/")
        }
        maven {
            url = uri("https://effective-android.bytesafe.dev/maven/lint/")
        }
        flatDir {
            setDirs(listOf("aar"))
        }
    }
}
rootProject.name = "ComposeDrawer"
include(":app")
include(":drawer-modules")
include(":drawer-location")
include(":drawer")
