package io.github.grassproject.queueLib.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

class QueueStartEvent(
    uid: UUID,
    players:Set<Player>,
    mapUID: UUID
):Event() {
    override fun getEventName() = "QueueStartEvent"
    override fun getHandlers(): HandlerList = getHandlerList()
    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}