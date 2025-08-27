package io.github.grassproject.queueLib.data

import io.github.grassproject.queueLib.QueueLib
import io.github.grassproject.queueLib.player.Waiter
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class UserData(private val waiter: Waiter) {
    companion object {
        @JvmStatic
        operator fun get(player: Player):UserData {
            return UserData(Waiter[player])
        }
    }

    private val plugin=QueueLib.plugin
    internal val file= File(plugin.dataFolder, "userdata/${waiter.getPlayer().uniqueId}")
    private val config=YamlConfiguration.loadConfiguration(file)
    private fun YamlConfiguration.reload() {
        this.load(file)
    }

    fun getConfig():YamlConfiguration {
        config.reload()
        return config
    }


}