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
    }
}
rootProject.name = "ComposeDrawer"
include(":app")
include(":drawer-base")
include(":drawer-modules")
include(":drawer-ui-modules")
include(":drawer-retrofit")
include(":drawer-okhttplogger")
include(":drawer-leak")
include(":drawer-timber")
include(":drawer-location")
include(":drawer")
