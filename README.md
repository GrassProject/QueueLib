# QueueLib
### Minecraft mini game QueueLib

# Dependency
[![](https://jitpack.io/v/GrassProject/QueueLib.svg)](https://jitpack.io/#GrassProject/QueueLib)
```groovy
maven { url = uri("https://jitpack.io") }

compileOnly("com.github.GrassProject:QueueLib:Tag")
```

# How to use?

## Build Queue & Join Player
```java
import io.github.grassproject.queueLib.QueueBuilder;
import io.github.grassproject.queueLib.queue.Queue;

Queue queue = new QueueBuilder()
        .setName("MiniGame1")
        .setType("main")
        .setMaxPlayer(4)
        .setMinPlayer(4)
        .setDisposable(false)
        .setMapUid(UUID).build();
queue.joinPlayer(Player);
queue.start();
```

## Get QueueManager
```java
import io.github.grassproject.queueLib.QueueLib;

QueueManager manager = QueueLib.getManager();

List<Queue> queueList= manager.getQueueList();
Queue queue= manager.getQueue(UUID);
manager.addPlayerInRandom(Player, String);
```

## Call Event
```java
import io.github.grassproject.queueLib.events.*;

@EventHanlder
public void playerQueueJoin(PlayerQueueJoinEvent event) {
    Player player= event.getPlayer();
    UUID uuid= event.queue;
}

@EventHanlder
public void playerQueueLeave(PlayerQueueLeaveEvent event) {
    Player player= event.getPlayer();
    UUID uuid= event.queue;
}

@EventHandler
public void queueStart(QueueStartEvent event) {
    UUID uid= event.uid;
    List<Player> players= event.players;
    UUID mapUUID= event.mapUID;
}
```