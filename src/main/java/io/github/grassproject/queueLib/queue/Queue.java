package io.github.grassproject.queueLib.queue;

import io.github.grassproject.queueLib.QueueLib;
import io.github.grassproject.queueLib.events.PlayerQueueJoinEvent;
import io.github.grassproject.queueLib.events.PlayerQueueLeaveEvent;
import io.github.grassproject.queueLib.exception.NotExistPlayer;
import io.github.grassproject.queueLib.exception.QueueMaxed;
import io.papermc.paper.entity.TeleportFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Queue {
    private final UUID id;
    private final String name;
    private final int maxPlayer;
    private final int minPlayer;
    private Set<Player> joinedPlayer;
    private final UUID mapUid;
    private final UUID queueMapUid;

    private final JavaPlugin plugin= QueueLib.getPlugin();

    public Queue(String name, int maxPlayer, int minPlayer, @Nullable UUID queueMapUid, UUID mapUid) {
        this.name=name;
        this.maxPlayer=maxPlayer;
        this.minPlayer=minPlayer;
        this.queueMapUid=queueMapUid;
        this.mapUid= mapUid;
        this.id=UUID.randomUUID();
    }

    public Queue(String name, UUID queueMapUid, UUID mapUid) {
        this(name, 20, 0, queueMapUid, mapUid);
    }

    public Queue(String name, int maxPlayer, UUID queueMapUid, UUID mapUid) {
        this(name, maxPlayer, 0, queueMapUid, mapUid);
    }

    public void joinPlayer(Player player) throws Exception {
        if (this.joinedPlayer.size() >= this.maxPlayer) {
            throw new QueueMaxed();
        } else {
            this.joinedPlayer.add(player);

            if (queueMapUid!=null) {
                World world= Bukkit.getWorld(queueMapUid);
                Location location= world != null ? world.getSpawnLocation() : null;
                player.teleportAsync(
                        Objects.requireNonNull(location),
                        PlayerTeleportEvent.TeleportCause.PLUGIN
                );
            }

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

    public void start() {

    }

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

    @Nullable
    public UUID getQueueMapUid() {
        return queueMapUid;
    }

    public UUID getMapUid() {
        return mapUid;
    }
}
