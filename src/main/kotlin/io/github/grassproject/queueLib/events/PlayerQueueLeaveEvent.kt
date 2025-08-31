package io.github.grassproject.queueLib.events

import io.github.grassproject.framework.core.events.GPPlayerEvent
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import java.util.*

class PlayerQueueLeaveEvent(
    player: Player,
    val queue: UUID
): GPPlayerEvent(player, "PlayerQueueLeaveEvent")