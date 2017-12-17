package org.mhdvsolutions.groupcast

import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.mhdvsolutions.groupcast.commands.GroupCast
import java.util.logging.Level

class GroupCastPlugin : JavaPlugin() {

    lateinit var permission: Permission
        private set

    override fun onLoad() {
        saveDefaultConfig()
    }

    override fun onEnable() {
        if (!initPermission()) {
            logger.log(Level.SEVERE, "Failed to obtain instance of a Vault Permissions provider...")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        GroupCast(this)
    }

    private fun initPermission(): Boolean {
        val rsp = Bukkit.getServicesManager().getRegistration(Permission::class.java) ?: return false
        permission = rsp.provider
        return true
    }

}