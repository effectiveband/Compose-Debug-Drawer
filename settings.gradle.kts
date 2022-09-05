dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://repsy.io/mvn/effective/compose-debug-drawer/")
        }
    }
}
rootProject.name = "ComposeDrawer"
include(":app")
include(":drawer-base")
include(":drawer-modules")
include(":drawer-location")
