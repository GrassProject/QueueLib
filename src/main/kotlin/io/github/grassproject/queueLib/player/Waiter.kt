package io.github.grassproject.queueLib.player

import org.bukkit.entity.Player

class Waiter(private val player:Player) {
    companion object {
        @JvmStatic
        operator fun get(player: Player):Waiter {
            return Waiter(player)
        }
    }

    fun getPlayer(): Player = player
}