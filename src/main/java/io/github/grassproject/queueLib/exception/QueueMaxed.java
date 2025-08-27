package io.github.grassproject.queueLib.exception;

public class QueueMaxed extends Exception {
    public QueueMaxed() {
        super("Queue Fulled");
    }
}
