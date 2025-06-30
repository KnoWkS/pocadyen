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
            url = uri("https://pos-mobile-test.cdn.adyen.com/adyen-pos-android")
            credentials(HttpHeaderCredentials::class) {
                name = "x-api-key"
                value = "<YOUR_SDK_API_KEY>" // Clé API pour accès SDK uniquement
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}

rootProject.name = "POC Adyen"
include(":app")
 