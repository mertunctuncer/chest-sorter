package dev.peopo.chestsorter

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class SortAction(private val inventory: Inventory) {

	private val totalAmounts = mutableMapOf<ItemStack, Int>()

	fun calculateTotals() : SortAction {
		for(item in inventory.contents) {
			if(item == null || item.type == Material.AIR) continue
			var typeExists = false
			for(cachedItem in totalAmounts.keys) {
				if(cachedItem.isSimilar(item)) {
					totalAmounts[cachedItem] = totalAmounts[cachedItem]!! + item.amount
					typeExists = true
					break
				}
			}
			if(!typeExists) {
				totalAmounts[item] = item.amount
			}
		}

		return this
	}


	fun sort() {
		inventory.clear()
		var slot = 0
		for((item, amount) in totalAmounts) {
			var mutableAmount = amount
			while(mutableAmount > 0) {
				val amountToPlace = if(mutableAmount > item.maxStackSize) item.maxStackSize else mutableAmount
				val itemToPlace = item.clone()
				itemToPlace.amount = amountToPlace
				inventory.setItem(slot, itemToPlace)
				mutableAmount -= amountToPlace
				slot++
			}
		}
	}
}