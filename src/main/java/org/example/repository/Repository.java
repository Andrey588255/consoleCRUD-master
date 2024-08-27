package org.example.repository;

public interface Repository <T, ID>{
    ID save (T object, ID ownerId);

    void delete(long id);

    T get (ID id);
}