package com.forum.hub.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}