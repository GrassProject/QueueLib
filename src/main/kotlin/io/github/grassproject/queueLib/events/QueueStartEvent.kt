package io.github.grassproject.queueLib.events

import io.github.grassproject.framework.core.events.GPEvent
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

class QueueStartEvent(
    uid: UUID,
    players:Set<Player>,
    mapUID: UUID
): GPEvent("QueueStartEvent")