package org.example.service;

public interface Service<T, ID> {
    ID add (T addEntity, ID ownerId);

    void remove (ID id);

    ID update (T entity, ID ownerId);

    T get (ID id);
}