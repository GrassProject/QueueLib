package io.github.grassproject.queueLib.data

import io.github.grassproject.framework.config.GPConfig
import io.github.grassproject.queueLib.QueueLib
import io.github.grassproject.queueLib.player.Waiter
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player

class UserData(private val waiter: Waiter) {
    companion object {
        @JvmStatic
        operator fun get(player: Player):UserData {
            return UserData(Waiter[player])
        }
    }

    private val plugin=QueueLib.plugin
    internal val userdata= GPConfig(plugin.dataFolder, "userdata/${waiter.getPlayer().uniqueId}")
    private val config= userdata.config
    private fun FileConfiguration.reload() {
        userdata.reload(userdata.file)
    }

    fun getConfig():FileConfiguration {
        config.reload()
        return config
    }
}