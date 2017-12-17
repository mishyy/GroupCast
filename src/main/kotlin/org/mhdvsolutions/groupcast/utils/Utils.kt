package org.mhdvsolutions.groupcast.utils

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

object Utils {

    fun color(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    fun sendMsg(sender: CommandSender, string: String) {
        sender.sendMessage(color(string).split("\n").toTypedArray())
    }

}