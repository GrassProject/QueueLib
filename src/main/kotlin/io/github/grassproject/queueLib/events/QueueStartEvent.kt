package io.github.grassproject.queueLib.events

import io.github.grassproject.framework.events.GPEvent
import org.bukkit.entity.Player
import java.util.*

class QueueStartEvent(
    uid: UUID,
    players:Set<Player>,
    mapUID: UUID
): GPEvent("QueueStartEvent")