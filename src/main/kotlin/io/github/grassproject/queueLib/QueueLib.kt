package io.github.grassproject.queueLib

import io.github.grassproject.framework.core.GPPlugin
import io.github.grassproject.queueLib.manager.QueueManager

abstract class QueueLib : GPPlugin() {
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
}
