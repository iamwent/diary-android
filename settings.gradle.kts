pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
        }
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.toString() == "com.github.takahirom.decomposer") {
                useModule("com.github.takahirom:decomposer:main-SNAPSHOT")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":app")
 