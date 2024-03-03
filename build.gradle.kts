import java.util.*

plugins {
  `java-library`
  `maven-publish`
  idea
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("xyz.jpenilla.run-paper") version "2.2.3"
}

group = "me.arian.nodurability"
version = "b3"
description = "Removes durability from the game"

val apiVersion = "1.13"
val main = "me.arian.nodurability.NoDurability"

java {
  targetCompatibility = JavaVersion.VERSION_1_8
  sourceCompatibility = JavaVersion.VERSION_1_8

  withJavadocJar()
  withSourcesJar()
}

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  maven("https://oss.sonatype.org/content/repositories/central")
  maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
  maven("https://jitpack.io")
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
  compileOnly("me.clip:placeholderapi:2.11.5")
  compileOnly("com.github.Ssomar-Developement:SCore:4.23.10.8")
}

tasks {
  compileJava {
    options.encoding = Charsets.UTF_8.name()
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
  build {
    dependsOn(shadowJar)
  }
  shadowJar {
    append("plugin.yml")
    archiveFileName = "NoDurability-$version.jar"
  }
  processResources {
    filteringCharset = Charsets.UTF_8.name()
    val props = mapOf(
      "name" to rootProject.name,
      "version" to version.toString(),
      "main" to main,
      "apiVersion" to apiVersion
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
      expand(props)
    }
  }
  runServer {
    minecraftVersion("1.19.4")
  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = group.toString().lowercase(Locale.ENGLISH)
      artifactId = rootProject.name.lowercase(Locale.ENGLISH)
      version = project.version.toString()

      from(components["java"])
    }
  }
}
