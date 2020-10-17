package ru.innopolis.project.InMemoryDB;

import java.util.Map;

public interface Repository<K,V> {
    boolean save(K key,V value);
    boolean update(K key,V value);
    boolean delete(K key);
    V get (K key);
    boolean containsKey(K key);

}
