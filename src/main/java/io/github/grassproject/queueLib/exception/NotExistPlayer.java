package io.github.grassproject.queueLib.exception;

import org.bukkit.entity.Player;

public class NotExistPlayer extends Exception {
    public NotExistPlayer(Player player) {
        super("can't find the player: "+player.getName());
    }
}
