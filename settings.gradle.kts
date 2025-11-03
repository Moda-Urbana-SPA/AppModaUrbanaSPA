pluginManagement {
  repositories { google(); mavenCentral(); gradlePluginPortal() }
  plugins {
    id("com.android.application") version "8.6.1"
    id("org.jetbrains.kotlin.android") version "1.9.25"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories { google(); mavenCentral() }
}
rootProject.name = "ModaUrbanaSPA"
include(":app")