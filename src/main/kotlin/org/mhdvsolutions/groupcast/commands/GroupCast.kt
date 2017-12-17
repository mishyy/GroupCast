package org.mhdvsolutions.groupcast.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.mhdvsolutions.groupcast.GroupCastPlugin
import org.mhdvsolutions.groupcast.utils.Utils

class GroupCast(private val plugin: GroupCastPlugin) : CommandExecutor {

    init {
        val command = Bukkit.getPluginCommand("groupcast")
        command.aliases = listOf("gcast")
        command.permission = "groupcast.use"
        command.permissionMessage = Utils.color("&cYou do not have permission to use this command.")
        command.executor = this
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty() || args.size < 2) {
            Utils.sendMsg(sender, "&cUsage: &f/groupcast <rank> <message>")
            return true
        }

        val permission = plugin.permission
        if (!permission.groups.any { it.equals(args[0], true) }) {
            Utils.sendMsg(sender, "&cCould not find group matching &f${args[0]}&c.")
            return true
        }

        val group = permission.groups.find { it.equals(args[0], true) }
        if (group == null) {
            Utils.sendMsg(sender, "&cUh oh, an error has occurred, are you sure you entered the group correctly?")
            return true
        }

        var msgFormat = plugin.config["message-format"].toString()
        val msg = args.copyOfRange(1, args.size).joinToString(" ")

        Bukkit.getOnlinePlayers().filter { permission.playerInGroup(it, group) }.forEach {
            msgFormat = msgFormat.replace("%player%", it.name).replace("%group%", group).replace("%message%", msg)
            Utils.sendMsg(it, msgFormat)
            Utils.sendMsg(sender, msgFormat)
        }
        return true
    }

}