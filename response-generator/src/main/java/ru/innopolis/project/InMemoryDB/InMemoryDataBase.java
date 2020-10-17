package ru.innopolis.project.InMemoryDB;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryDataBase<K,V> implements Repository<K,V>{
    private final Map<K,V> dataBase;

    public InMemoryDataBase(){
        dataBase=new HashMap<>();
    }


    private boolean isEmpty(String key){
        return key == null || key.isEmpty();
    }


    @Override
    public boolean save(K key, V value) {
        dataBase.put(key, value);
        return true;
    }

    @Override
    public boolean update(K key, V value) {
        dataBase.put(key, value);
        return true;
    }

    @Override
    public boolean delete(K key) {
        if (dataBase.containsKey(key)){
            dataBase.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (dataBase.containsKey(key)){
            return dataBase.get(key);
        }
        throw new IllegalArgumentException("No key in DB");
    }

    @Override
    public boolean containsKey(K key) {
        return dataBase.containsKey(key);
    }

}
