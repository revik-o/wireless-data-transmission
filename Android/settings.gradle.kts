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
    }
}

rootProject.name = "Wireless data transmission"
include(":app")
include(":core:common")
include(":core:data")
include(":core:tests")
include(":infrastructure:tcp")
include(":infrastructure:common")
include(":android:common")
include(":android:impl")
include(":android:test")
