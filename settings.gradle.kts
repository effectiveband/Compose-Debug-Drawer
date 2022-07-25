dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://effective-android.bytesafe.dev/maven/drawer/")
        }
    }
}
rootProject.name = "ComposeDrawer"
include(":app")
include(":drawer-base")
include(":drawer-modules")
include(":drawer-location")
