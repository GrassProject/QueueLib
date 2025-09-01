package io.github.grassproject.queueLib.events

import io.github.grassproject.framework.events.GPPlayerEvent
import org.bukkit.entity.Player
import java.util.*

class PlayerQueueJoinEvent(
    player:Player,
    val queue: UUID
): GPPlayerEvent(player, "PlayerQueueJoinEvent")