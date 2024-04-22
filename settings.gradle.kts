pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials.username = "mapbox"
            credentials.password = "sk.eyJ1IjoiZnNrdXJuaWsiLCJhIjoiY2x2MmE1MjQ0MDliZjJrcnc5eW9ueDg0ayJ9.fdaLrxAc-BRil_-Ep-_4yA"
            authentication.create<BasicAuthentication>("basic")
        }
    }
}

rootProject.name = "ScaffoldingV2"
include(":app")
