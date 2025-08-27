package io.github.grassproject.queueLib.exception;

public class NotEnoughPlayer extends Exception {
    public NotEnoughPlayer(String name) {
        super("Not Enough Player in QUEUE: "+name);
    }
}
