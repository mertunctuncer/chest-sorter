import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"

    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.peopo"
version = "1.0.0"
description = "Chest sorting plugin"

val pluginName: String = "ChestSorter"
val configNames: List<String> = File("$projectDir\\src\\main\\resources\\" ).list()!!.toList()
val configPath: String = "$projectDir\\run\\plugins\\$pluginName\\"



repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://jitpack.io" )

    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks {
    shadowJar {
        minimize {
            exclude(dependency("org.jetbrains.kotlin:.*:.*"))
            exclude(dependency("org.jetbrains.kotlinx:.*:.*"))
        }
    }

    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    runServer {
        minecraftVersion("1.18.2")
    }

    val cleanConfigs = register<Delete>("cleanConfigs") {
        group = "other"
        for(config in configNames) {
            File("$configPath\\$config").delete()
        }
    }

    register("cleanBuildThenRun") {
        group = "run paper"
        dependsOn(cleanConfigs)
        dependsOn(clean)
        dependsOn(runServer)
    }

    register("cleanConfigThenRun") {
        group = "run paper"
        dependsOn(cleanConfigs)
        dependsOn(runServer)
    }

    register("shadowAndReObf") {
        group ="shadow"
        dependsOn(clean)
        dependsOn(shadowJar)
        dependsOn(reobfJar)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkit {
    name = pluginName
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "$group.${rootProject.name.replace("-","")}.${pluginName}Plugin"
    version = project.version.toString()
    description = project.description
    apiVersion = "1.18"
    authors = listOf("Aki..#0001")
    depend = listOf()
    softDepend = listOf()
    commands {
        register("sortchest") {
            aliases = listOf("csort")
        }
    }
}
