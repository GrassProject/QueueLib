# QueueLib
### Minecraft mini game QueueLib

# Dependency
```groovy
maven { url = uri("https://jitpack.io") }

compileOnly("com.github.GrassProject:QueueLib:Tag")
```

# How to use?
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