package com.rich.budgetapi.domain.exception;

public abstract class EntityNotfoundException extends DomainException {

    public EntityNotfoundException(String message) {
        super(message);
    }

}
