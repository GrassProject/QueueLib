package io.github.grassproject.queueLib.queue;

import io.github.grassproject.queueLib.QueueLib;
import io.github.grassproject.queueLib.events.PlayerQueueJoinEvent;
import io.github.grassproject.queueLib.events.PlayerQueueLeaveEvent;
import io.github.grassproject.queueLib.exception.NotExistPlayer;
import io.github.grassproject.queueLib.exception.QueueMaxed;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;

public class Queue {
    private final UUID id;
    private final String name;
    private final int maxPlayer;
    private final int minPlayer;
    private Set<Player> joinedPlayer;

    private final JavaPlugin plugin= QueueLib.getPlugin();

    public Queue(String name, int maxPlayer, int minPlayer) {
        this.name=name;
        this.maxPlayer=maxPlayer;
        this.minPlayer=minPlayer;
        this.id=UUID.randomUUID();
    }

    public Queue(String name) {this(name, 20, 0);}
    public Queue(String name, int maxPlayer) {this(name, maxPlayer, 0);}

    public void joinPlayer(Player player) throws Exception {
        if (this.joinedPlayer.size() >= this.maxPlayer) {
            throw new QueueMaxed();
        } else {
            this.joinedPlayer.add(player);
            boolean call = new PlayerQueueJoinEvent(player, id).callEvent();
            if (!call) throw new Exception("Bukkit Event not called");
        }
    }

    public void leavePlayer(Player player) throws Exception {
        if (this.joinedPlayer.contains(player)) {
            this.joinedPlayer.remove(player);
            boolean call= new PlayerQueueLeaveEvent(player, id).callEvent();
            if (!call) throw new Exception("Bukkit Event not called");
        } else {
            throw new NotExistPlayer(player);
        }
    }

    public void start() {}

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public Set<Player> getJoinedPlayer() {
        return joinedPlayer;
    }
}
