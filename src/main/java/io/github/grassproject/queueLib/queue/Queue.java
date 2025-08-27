package io.github.grassproject.queueLib.queue;

import io.github.grassproject.queueLib.QueueLib;
import io.github.grassproject.queueLib.data.QueueData;
import io.github.grassproject.queueLib.events.PlayerQueueJoinEvent;
import io.github.grassproject.queueLib.events.PlayerQueueLeaveEvent;
import io.github.grassproject.queueLib.events.QueueStartEvent;
import io.github.grassproject.queueLib.exception.NotExistPlayer;
import io.github.grassproject.queueLib.exception.QueueMaxed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Queue {
    private final UUID uid;
    private final String name;
    private final String type;
    private final int maxPlayer;
    private final int minPlayer;
    private final Set<UUID> joinedPlayer;
    @Nullable
    private final UUID queueMapUid;
    private final UUID mapUid;
    private final boolean disposable;

    private final JavaPlugin plugin= QueueLib.getPlugin();
    private final QueueData data;

    public static Queue fromUUID(UUID uuid) {
        return new QueueData(uuid).getQueue();
    }

    public Queue(
            String name, String type,
            int maxPlayer, int minPlayer,
            @Nullable UUID queueMapUid, UUID mapUid,
            boolean disposable
    ) {
        this.name=name;
        this.type=type;
        this.maxPlayer=maxPlayer;
        this.minPlayer=minPlayer;
        this.queueMapUid=queueMapUid;
        this.mapUid= mapUid;
        this.disposable=disposable;
        this.uid =UUID.randomUUID();
        this.joinedPlayer = new HashSet<>();

        QueueData.save(this);
        data=new QueueData(uid);
    }

    public Queue(String name, String type, UUID queueMapUid, UUID mapUid) {
        this(name, type, 20, 0, queueMapUid, mapUid, true);
    }

    public Queue(String name, String type, int maxPlayer, UUID queueMapUid, UUID mapUid) {
        this(name, type, maxPlayer, 0, queueMapUid, mapUid, true);
    }

    public void joinPlayer(Player player) throws Exception {
        if (this.joinedPlayer.size() >= this.maxPlayer) {
            throw new QueueMaxed();
        } else {
            this.joinedPlayer.add(player.getUniqueId());
            this.data.addPlayer(player);

            if (queueMapUid!=null) {
                World world= Bukkit.getWorld(queueMapUid);
                Location location= world != null ? world.getSpawnLocation() : null;
                player.teleportAsync(
                        Objects.requireNonNull(location),
                        PlayerTeleportEvent.TeleportCause.PLUGIN
                );
            }

            boolean call = new PlayerQueueJoinEvent(player, uid).callEvent();
            if (!call) throw new Exception("Bukkit Event not called");
        }
    }

    public void leavePlayer(Player player) throws Exception {
        if (this.joinedPlayer.contains(player.getUniqueId())) {
            this.joinedPlayer.remove(player.getUniqueId());
            this.data.removePlayer(player);

            boolean call= new PlayerQueueLeaveEvent(player, uid).callEvent();
            if (!call) throw new Exception("Bukkit Event not called");
        } else {
            throw new NotExistPlayer(player);
        }
    }

    public void start() throws Exception {
        World world= Bukkit.getWorld(mapUid);
        Location location= world != null ? world.getSpawnLocation() : null;
        this.joinedPlayer.forEach( uuid-> {
            Player player=Bukkit.getPlayer(uuid);
            assert player != null;
            if (location != null) {
                player.teleportAsync(location);
                joinedPlayer.remove(uuid);
            } else {
                throw new RuntimeException("Spawn location of set map is NULL");
            }
        });

        if (disposable) this.boom(RemoveCause.QUEUE_START);

        boolean called=new QueueStartEvent(
                uid, new HashSet<>(joinedPlayer.stream().map(Bukkit::getPlayer).toList()),
                mapUid
        ).callEvent();
        if (!called) throw new Exception("Bukkit Event Not Called");
    }

    public void boom(RemoveCause cause) {
        QueueData.remove(this.uid);
        this.joinedPlayer.forEach(uuid-> {
            Player p=Bukkit.getPlayer(uuid);
            assert p!=null;
            try {
                leavePlayer(p);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public UUID getUid() {
        return uid;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public Set<UUID> getJoinedPlayer() {
        return joinedPlayer;
    }

    @Nullable
    public UUID getQueueMapUid() {
        return queueMapUid;
    }

    public UUID getMapUid() {
        return mapUid;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public enum RemoveCause {
        QUEUE_START,
        PLAYER_NOT_EXIST,
        FORCE,
        UNKNOWN
    }
}
