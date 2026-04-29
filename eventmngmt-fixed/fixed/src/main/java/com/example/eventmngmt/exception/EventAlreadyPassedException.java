package com.example.eventmngmt.exception;

public class EventAlreadyPassedException extends RuntimeException {
    public EventAlreadyPassedException(String msg) { super(msg); }
}
