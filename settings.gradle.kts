dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://mymavenrepo.com/repo/nFFsiSTFBS99YlQUZYju/")
        }
    }
}
rootProject.name = "ComposeDrawer"
include(":app")
include(":drawer-modules")
include(":drawer-location")
include(":drawer")
