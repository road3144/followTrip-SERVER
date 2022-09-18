package com.road3144.followtrip.exception;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException() {
        super(ExceptionCode.ENTITY_NOT_FOUND);
    }
}
