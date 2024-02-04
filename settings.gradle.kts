import kotlin.system.exitProcess

pluginManagement {
  repositories {
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

if (JavaVersion.current().toString() != "17") {
  System.err.println()
  System.err.println("=========================================================================================================")
  System.err.println("You must run gradle on Java 17. You are using " + JavaVersion.current())
  System.err.println()
  System.err.println("=== For IDEs ===")
  System.err.println("1. Configure the project for Java 17")
  System.err.println("2. Configure the bundled gradle to use Java 17 in settings")
  System.err.println()
  System.err.println("=== For Command Line (gradlew) ===")
  System.err.println("1. Install JDK 17 from https://adoptium.net/")
  System.err.println("2. In the setup enable the path configuration")
  System.err.println("=========================================================================================================")
  System.err.println()
  exitProcess(69);
}

rootProject.name = "NoDurability"
