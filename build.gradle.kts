import java.util.*

plugins {
  `java-library`
  `maven-publish`
  idea
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("xyz.jpenilla.run-paper") version "2.2.0"
}

group = "me.arian.nodurability"
version = "b1"
description = "Removes durability from the game"

val apiVersion = "1.18"
val main = "me.arian.nodurability.NoDurability"

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))

  withJavadocJar()
  withSourcesJar()
}

repositories {
  mavenLocal()
  mavenCentral()

  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
}

tasks {
  compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(17)
  }
  build {
    dependsOn(shadowJar)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
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
  shadowJar {
    append("plugin.yml")
    archiveFileName = "${rootProject.name}.jar"
  }
  runServer {
    minecraftVersion("1.19.4")
  }
  val jar by getting(Jar::class)

  file(jar.archiveFile.get()
    .asFile
    .parentFile
    .parentFile
    .parentFile
    .absolutePath +
    "/build/resources/main/plugin.yml")
    .delete()
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

val shadowJar: TaskProvider<Task> = tasks.named("shadowJar")

fun registerCustomOutputTask(name: String, path: String) {
  if (!System.getProperty("os.name").lowercase(Locale.ENGLISH).contains("windows")) {
    return
  }

  tasks.register("build$name", Copy::class) {
    group = "development"
    outputs.upToDateWhen { false }
    dependsOn(rootProject.name.lowercase(Locale.ENGLISH))
    from(File(buildDir, "${rootProject.name}-$version.jar"))
    into(File(path))
    rename { fileName ->
      fileName.replace("${rootProject.name}-$version.jar", "${rootProject.name}.jar")
    }
  }
}

fun registerCustomOutputTaskUnix(name: String, path: String) {
  if (System.getProperty("os.name").lowercase(Locale.ENGLISH).contains("windows")) {
    return
  }

  tasks.register("build$name", Copy::class) {
    group = "development"
    outputs.upToDateWhen { false }
    dependsOn(rootProject.name.lowercase(Locale.ENGLISH))
    from(File(buildDir, "${rootProject.name}-$version.jar"))
    into(File(path))
    rename { fileName ->
      fileName.replace("${rootProject.name}-$version.jar", "${rootProject.name}.jar")
    }
  }
}
