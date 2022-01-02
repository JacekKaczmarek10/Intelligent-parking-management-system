package pl.kaczmarek.rest;

import java.io.Serializable;

public class EntityDTO<T extends Serializable> {
    private T id;

    public T getId() {
        return id;
    }

    public EntityDTO<T> setId(T id) {
        this.id = id;
        return this;
    }
}
