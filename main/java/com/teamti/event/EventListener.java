package com.teamti.event;

public interface EventListener<T> {
    void call(T event);
}
