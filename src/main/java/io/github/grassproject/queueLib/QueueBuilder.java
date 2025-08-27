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
    public void setName(String name) {
        this.name=name;
    }

    /**
     * @param type Custom type of the Queue
     */
    public void setType(String type) {
        this.type=type;
    }

    /**
     * @param maxPlayer Maximum player count of Queue
     */
    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    /**
     * @param minPlayer Minimum of the player to start Queue
     */
    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
    }

    /**
     * @param mapUid UUID of the world that player is moved when join Queue
     */
    public void setMapUid(UUID mapUid) {
        this.mapUid = mapUid;
    }

    /**
     * @param queuemapUUID UUID of the world that player is moved when Queue starts
     */
    public void setQueuemapUUID(@Nullable UUID queuemapUUID) {
        this.queuemapUUID = queuemapUUID;
    }

    /**
     * @param isDisposable If is True, when Queue Starts. It automatically removed
     * @apiNote Default is TRUE
     */
    public void setDisposable(boolean isDisposable) {
        this.isDisposable=isDisposable;
    }

    public Queue build() {
        return new Queue(
                name, type, maxPlayer, minPlayer,
                queuemapUUID, mapUid, isDisposable
        );
    }
}
