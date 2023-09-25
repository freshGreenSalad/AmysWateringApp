
pluginManagement {
    //includeBuild("build-logic") add this if you want tho have the build logic top level folder like in nia
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
    versionCatalogs{
        create("libs"){
           (files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "AmysWateringApp"
include(":app")