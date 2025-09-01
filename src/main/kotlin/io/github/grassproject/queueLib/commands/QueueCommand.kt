package io.github.grassproject.queueLib.commands

import io.github.grassproject.framework.command.GPCommand
import io.github.grassproject.queueLib.QueueLib
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.UUID

class QueueCommand: GPCommand<QueueLib>(
    QueueLib.plugin, "queue",
    listOf(), "QueueLib Command",
    "gp.queue.command"
) {
    override fun execute(
        sender: CommandSender,
        args: Array<out String>
    ): Boolean {
        val manager = QueueLib.manager

        if (args.isEmpty()) {
            sender.sendMessage("/queue list - 큐 목록 보기")
            sender.sendMessage("/queue info <uuid> - 특정 큐 정보 보기")
            return true
        }

        when (args[0].lowercase()) {
            "list" -> {
                val list = manager.queueList
                if (list.isEmpty()) {
                    sender.sendMessage("현재 등록된 큐가 없습니다.")
                } else {
                    sender.sendMessage("큐 목록 (${list.size})")
                    list.forEach { q ->
                        sender.sendMessage("- ${q.uid} [${q.type}] ( ${q.joinedPlayer.size}/${q.maxPlayer} )")
                    }
                }
            }

            "info" -> {
                if (args.size < 2) {
                    sender.sendMessage("/queue info <uuid>")
                    return true
                }
                try {
                    val uuid = UUID.fromString(args[1])
                    val queue = manager.getQueue(uuid)
                    if (queue == null) {
                        sender.sendMessage("해당 UUID의 큐를 찾을 수 없습니다.")
                        return true
                    }
                    sender.sendMessage("큐 정보:")
                    sender.sendMessage("UUID: ${queue.uid}")
                    sender.sendMessage("타입: ${queue.type}")
                    sender.sendMessage("플레이어: ${queue.joinedPlayer.size}/${queue.maxPlayer}")
                    sender.sendMessage("참가자: ${queue.joinedPlayer.mapNotNull { Bukkit.getPlayer(it)?.name }.joinToString(", ")}")
                } catch (e: IllegalArgumentException) {
                    sender.sendMessage("올바른 UUID 형식이 아닙니다.")
                }
            }

            else -> {
                sender.sendMessage("알 수 없는 명령어입니다. /queue 로 확인하세요.")
            }
        }

        return true
    }

    override fun tabComplete(
        sender: CommandSender,
        args: Array<out String>
    ): List<String?> {
        return when (args.size) {
            1 -> listOf("list", "info").filter { it.startsWith(args[0], ignoreCase = true) }
            2 -> if (args[0].equals("info", true)) {
                QueueLib.manager.queueList.map { it.uid.toString() }
                    .filter { it.startsWith(args[1], ignoreCase = true) }
            } else emptyList()
            else -> emptyList()
        }
    }
}