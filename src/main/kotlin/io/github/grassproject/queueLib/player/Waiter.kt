package io.github.grassproject.queueLib.player

import io.github.grassproject.queueLib.data.UserData
import org.bukkit.entity.Player
import java.util.*

class Waiter(private val player:Player) {
    companion object {
        @JvmStatic
        operator fun get(player: Player):Waiter {
            return Waiter(player)
        }
    }

    fun getPlayer(): Player = player

    fun setQueue(uuid:UUID?) {
        val userData=UserData(this)
        userData.userdata.apply {
            set("user.queue", uuid?.toString())
        }.save()
    }

    fun getQueue():UUID? {
        val str= UserData(this).userdata.getValue(
            "user.queue",
            String::class
        )
        return UUID.fromString(str)
    }
}