package io.github.grassproject.queueLib.manager;

import io.github.grassproject.queueLib.QueueLib;
import io.github.grassproject.queueLib.data.QueueData;
import io.github.grassproject.queueLib.queue.Queue;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.util.*;

@Deprecated
/*
  @deprecated Dont Use it
 * */
@ApiStatus.Experimental
public class QueueManager {
    private final JavaPlugin plugin= QueueLib.getPlugin();
    private final List<UUID> queueList= new ArrayList<>();
    private final File file=new File(plugin.getDataFolder(), "manager");
    private final Random random = new Random();

    public List<Queue> getQueueList() {
        return queueList.stream()
                .map(u -> new QueueData(u).getQueue())
                .filter(Objects::nonNull).toList();
    }

    public void addQueue(Queue queue) {
        this.queueList.add(queue.getUid());
    }

    public void removeQueue(Queue queue) {
        this.queueList.remove(queue.getUid());
    }

    public boolean addPlayerInRandom(Player player, String type) {
        List<Queue> filtered=queueList.stream()
                .map(u-> new QueueData(u).getQueue())
                .filter(Objects::nonNull).filter(q-> q.getType().equals(type))
                .filter(q -> q.getJoinedPlayer().size() < q.getMaxPlayer())
                .toList();
        if (filtered.isEmpty()) return false;
        Queue queue=filtered.get(random.nextInt(filtered.size()));
        try {
            queue.joinPlayer(player);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
