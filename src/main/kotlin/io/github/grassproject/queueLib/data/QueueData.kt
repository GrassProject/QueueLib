package io.github.grassproject.queueLib.data

import io.github.grassproject.queueLib.QueueLib
import io.github.grassproject.queueLib.queue.Queue
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

class QueueData(private val uuid: UUID) {
    companion object {
        @JvmStatic
        fun save(queue: Queue) {

        }
    }

    private val plugin=QueueLib.plugin
    private val file=File(plugin.dataFolder, "queue/${uuid}")
    private val config=YamlConfiguration.loadConfiguration(file)

    fun Queue.toData():QueueGen {
        return QueueGen(
            this.id,
            this.name,
            this.maxPlayer,
            this.minPlayer,
            this.queueMapUid,
            this.mapUid
        )
    }

}

data class QueueGen(
    val uuid:UUID,
    val name:String,
    val maxPlayer:Int,
    val minPlayer:Int,
    val queueMapUid:UUID?,
    val mapUid:UUID
)