package ru.innopolis.project.InMemoryDB;

import java.util.Map;

public interface Repository<K,V> {
    boolean save(String key,Map<K,V> value);
    boolean update(String key,Map<K,V> value);
    boolean delete(String key);
    Map<String,Object> getMap(String key);
}
