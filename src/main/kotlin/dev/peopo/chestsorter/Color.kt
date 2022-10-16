package dev.peopo.chestsorter

import org.bukkit.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern


fun String.colorize() = ChatColor.translateAlternateColorCodes('&', translateHexColorCodes(this))

private val hexPattern: Pattern = Pattern.compile("[§&]#([A-Fa-f\\d]{6})")
private fun translateHexColorCodes( message: String): String {
	val matcher: Matcher = hexPattern.matcher(message)
	val buffer = StringBuffer(message.length + 4 * 8)
	while (matcher.find()) {
		val group: String = matcher.group(1)
		matcher.appendReplacement(
			buffer, "§x§${group[0]}§${group[1]}§${group[2]}§${group[3]}§${group[4]}§${group[5]}"
		)
	}
	return matcher.appendTail(buffer).toString()
}
