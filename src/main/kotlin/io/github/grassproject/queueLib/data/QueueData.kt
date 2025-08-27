package io.github.grassproject.queueLib.data

import com.google.gson.Gson
import io.github.grassproject.queueLib.QueueLib
import io.github.grassproject.queueLib.exception.AlreadyJoinedQueue
import io.github.grassproject.queueLib.player.Waiter
import io.github.grassproject.queueLib.queue.Queue
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.util.*

class QueueData(private val uuid: UUID) {
    companion object {
        @JvmStatic
        fun save(queue: Queue) {
            val file= QueueData(queue.uid).file
            val config=YamlConfiguration.loadConfiguration(file)
            val encoded=Base64.getEncoder().encodeToString(
                Gson().toJson(queue.toData()).toByteArray(Charsets.UTF_8)
            )
            config.apply {
                set("${queue.uid}.info", encoded)
            }.save(file)
        }

        @JvmStatic
        fun remove(uid:UUID) {
            QueueData(uid).file.deleteOnExit()
        }

        private fun Queue.toData():QueueGen {
            return QueueGen(
                this.uid,
                this.name,
                this.type,
                this.maxPlayer,
                this.minPlayer,
                this.queueMapUid,
                this.mapUid,
                this.isDisposable
            )
        }
    }

    private val plugin=QueueLib.plugin
    internal val file=File(plugin.dataFolder, "queue/${uuid}")
    private val config=YamlConfiguration.loadConfiguration(file)

    fun getQueue():Queue? {
        config.load(file)
        val cod=config.getString("${uuid}.info") ?: return null
        val decoded=runCatching {
            String(Base64.getDecoder().decode(cod), Charsets.UTF_8)
        }.getOrNull() ?: return null
        return Gson().fromJson(decoded, QueueGen::class.java).toQueue()
    }

    fun addPlayer(player: Player):Waiter {
        val waiter=Waiter[player]
        if (waiter.getQueue()!=null) {
            throw AlreadyJoinedQueue(player)
        }
        config.load(file)
        val list=config.getStringList("${uuid}.joined")
            .mapNotNull { Bukkit.getPlayer(UUID.fromString(it)) }
            .toMutableList()
        list.add(player)
        config.apply {
            set("${uuid}.joined", list.map { it.uniqueId.toString() })
        }.save(file)
        waiter.setQueue(uuid)
        return waiter
    }

    fun removePlayer(player: Player) {
        val waiter=Waiter[player]
        config.load(file)
        val list=config.getStringList("${uuid}.joined")
            .mapNotNull { Bukkit.getPlayer(UUID.fromString(it)) }
            .toMutableList()
        list.remove(player)
        config.apply {
            set("${uuid}.joined", list.map { it.uniqueId.toString() })
        }.save(file)
        waiter.setQueue(null)
    }
}

data class QueueGen(
    val uuid: UUID,
    val name: String,
    val type:String,
    val maxPlayer: Int,
    val minPlayer: Int,
    val queueMapUid: UUID?,
    val mapUid: UUID,
    val disposable: Boolean
) {
    fun toQueue(): Queue {
        val queue = Queue(
            name, type,
            maxPlayer, minPlayer,
            queueMapUid, mapUid,
            disposable
        )

        val file = File(QueueLib.plugin.dataFolder, "queue/${uuid}")
        val config = YamlConfiguration.loadConfiguration(file)
        val players = config.getStringList("${uuid}.joined")
            .mapNotNull { UUID.fromString(it) }
            .toSet()

        queue.joinedPlayer.addAll(players)

        return queue
    }
}