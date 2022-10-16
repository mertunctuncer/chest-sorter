package dev.peopo.chestsorter

import org.bukkit.plugin.java.JavaPlugin

internal lateinit var plugin: JavaPlugin
internal val config by lazy { YamlConfig(plugin,"config.yml")}
internal val logger by lazy { plugin.logger }

class ChestSorterPlugin : JavaPlugin() {


	override fun onEnable() {
		plugin = this
		getCommand("sortchest")?.setExecutor(SortChestCommand())
		logger.info("Chest Sorter enabled!")
	}
}