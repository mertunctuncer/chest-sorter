package dev.peopo.chestsorter

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

open class YamlConfig(private val plugin: Plugin, fileName: String) : YamlConfiguration() {
    private val file: File
    private val fileName: String

    init {
        if (fileName.endsWith(".yml")) this.fileName = fileName
        else this.fileName = "$fileName.yml"
        file = File(plugin.dataFolder, this.fileName)

        if (!file.exists()) {
            file.parentFile.mkdirs()
            this.saveDefault()
        }
        this.load()
    }

    private fun saveDefault() {
        plugin.saveResource(fileName, false)
    }

    private fun load() = try {
        super.load(file)
    } catch (e: Exception) {
        plugin.logger.severe("An error has occurred while loading the config file!")
    }
}

