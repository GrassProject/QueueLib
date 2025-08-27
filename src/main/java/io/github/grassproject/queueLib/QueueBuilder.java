package io.github.grassproject.queueLib;

import io.github.grassproject.queueLib.queue.Queue;

import javax.annotation.Nullable;
import java.util.UUID;

public class QueueBuilder {
    private String name;
    private int maxPlayer;
    private int minPlayer;
    private UUID mapUid;
    @Nullable
    private UUID queuemapUUID;
    private boolean isDisposable=true;
    private String type="";

    /**
     * @param name the name of Queue
     */
    public QueueBuilder setName(String name) {
        this.name=name;
        return this;
    }

    /**
     * @param type Custom type of the Queue
     */
    public QueueBuilder setType(String type) {
        this.type=type;
        return this;
    }

    /**
     * @param maxPlayer Maximum player count of Queue
     */
    public QueueBuilder setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
        return this;
    }

    /**
     * @param minPlayer Minimum of the player to start Queue
     */
    public QueueBuilder setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
        return this;
    }

    /**
     * @param mapUid UUID of the world that player is moved when join Queue
     */
    public QueueBuilder setMapUid(UUID mapUid) {
        this.mapUid = mapUid;
        return this;
    }

    /**
     * @param queuemapUUID UUID of the world that player is moved when Queue starts
     */
    public QueueBuilder setQueuemapUUID(@Nullable UUID queuemapUUID) {
        this.queuemapUUID = queuemapUUID;
        return this;
    }

    /**
     * @param isDisposable If is True, when Queue Starts. It automatically removed
     * @apiNote Default is TRUE
     */
    public QueueBuilder setDisposable(boolean isDisposable) {
        this.isDisposable=isDisposable;
        return this;
    }

    public Queue build() {
        return new Queue(
                name, type, maxPlayer, minPlayer,
                queuemapUUID, mapUid, isDisposable
        );
    }
}
