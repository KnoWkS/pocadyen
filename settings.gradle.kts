val localProps = File(rootDir, "local.properties").inputStream().use {
    java.util.Properties().apply { load(it) }
}
val adyenApiKey = localProps.getProperty("ADYEN_API_KEY") ?: error("ADYEN_API_KEY not found in local.properties")

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

        // ✅ Repo sécurisé Adyen avec clé API — utilisé pour *toutes* les dépendances com.adyen.*
        maven {
            url = uri("https://pos-mobile-test.cdn.adyen.com/adyen-pos-android")
            credentials(HttpHeaderCredentials::class) {
                name = "x-api-key"
                value = adyenApiKey // ta variable, déjà chargée depuis local.properties
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
            content {
                includeGroup("com.adyen.ipp")
                includeGroup("com.adyen.ipp.tools")
                includeGroup("com.adyen.ipp.authentication")
            }
        }
    }
}



include(":app")
