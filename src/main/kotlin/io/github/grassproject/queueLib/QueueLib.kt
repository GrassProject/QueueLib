package io.github.grassproject.queueLib

import org.bukkit.plugin.java.JavaPlugin

class QueueLib : JavaPlugin() {
    companion object {
        @JvmStatic
        lateinit var plugin:QueueLib
            private set
    }

    override fun onLoad() {
        plugin=this
    }

    override fun onEnable() {
    }
}
