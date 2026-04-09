plugins {
    id("com.android.application") version "8.3.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
