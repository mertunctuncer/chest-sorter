package dev.peopo.chestsorter

import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SortChestCommand : TabExecutor{

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
		if (sender !is Player) {
			sender.sendMessage("Only players can use this command!")
			return true
		}

		if (!sender.hasPermission("chestsorter.sort")) {
			config.getString("no-permission-message")?.colorize()?.let { sender.sendMessage(it) }
				?: logger.severe("Config file is missing the no-permission-message!")
			return true
		}

		val targetBlock = sender.getTargetBlock(10)

		if (targetBlock == null || targetBlock.isEmpty) {
			config.getString("no-target-message")?.colorize()?.let { sender.sendMessage(it) } ?: logger.severe("Config file is missing the no-target-message!")
			return true
		}

		if (targetBlock.type != Material.CHEST && targetBlock.type != Material.TRAPPED_CHEST) {
			config.getString("not-chest-message")?.colorize()?.let { sender.sendMessage(it) } ?: logger.severe("Config file is missing the not-chest-message!")
			return true
		}

		val inventory = (targetBlock.state as Chest).inventory

		SortAction(inventory)
			.calculateTotals()
			.sort()

		config.getString("inventory-sorted-message")?.colorize()?.let { sender.sendMessage(it) }
			?: logger.severe("Config file is missing the inventory-sorted-message!")
		return true
	}

	override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
		return null
	}
}