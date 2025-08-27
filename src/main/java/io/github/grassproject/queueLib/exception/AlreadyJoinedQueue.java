package io.github.grassproject.queueLib.exception;

import org.bukkit.entity.Player;

public class AlreadyJoinedQueue extends RuntimeException {
    public AlreadyJoinedQueue(Player player) {
        super("The Player is Already Joined in Other Queue"+ player.getName());
    }
}
