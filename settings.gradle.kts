pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Weather App"
include(":app")
include(":feature:home")
include(":feature:settings")
include(":feature:add")
include(":data")
include(":domain")
include(":core")
include(":feature:search")
