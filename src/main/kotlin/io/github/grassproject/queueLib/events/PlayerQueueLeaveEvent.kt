package io.github.grassproject.queueLib.events

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import java.util.*

class PlayerQueueLeaveEvent(
    player: Player,
    val queue: UUID
): PlayerEvent(player) {
    override fun getEventName() = "PlayerQueueLeaveEvent"
    override fun getHandlers(): HandlerList = getHandlerList()
    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}