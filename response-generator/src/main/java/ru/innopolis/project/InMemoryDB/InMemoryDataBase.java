package ru.innopolis.project.InMemoryDB;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryDataBase<K,V> implements Repository<K,V>{
    private final Map<String,Object> dataBase;

    public InMemoryDataBase(){
        dataBase=new HashMap<>();
    }


    @Override
    public boolean save(String key, Map value) {
        dataBase.put(key,value);
        return true;
    }

    @Override
    public boolean update(String key, Map value) {
        dataBase.put(key,value);
        return true;
    }

    @Override
    public boolean delete(String key) {
        Set<String> keySet = dataBase.keySet();
        if (!keySet.contains(key)){
            return false;
        }
        dataBase.remove(key);
        return true;
    }

    @Override
    public Map<String, Object> getMap(String key) {
        Set<String> keySet = dataBase.keySet();
        if (!keySet.contains(key)){
            throw new IllegalArgumentException("Not valid key");
        }
        return (Map<String, Object>) dataBase.get(key);
    }
    private boolean isEmpty(String key){
        return key == null || key.isEmpty();
    }


}
