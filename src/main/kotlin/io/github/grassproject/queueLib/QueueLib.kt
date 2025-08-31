package io.github.grassproject.queueLib

import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.queueLib.commands.QueueCommand
import io.github.grassproject.queueLib.manager.QueueManager

class QueueLib : GPPlugin() {
    companion object {
        @JvmStatic
        lateinit var plugin:QueueLib
            private set

        @JvmStatic
        lateinit var manager: QueueManager
            private set
    }

    override fun load() {
        plugin=this
        manager= QueueManager()
    }

    override fun enable() {
        QueueCommand()
    }

    override fun disable() {

    }
}
