package ru.innopolis.project.InMemoryDB;

import org.springframework.stereotype.Component;
import ru.innopolis.project.entity.Car;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryDataBase implements Repository<String, Car>{
    private final Map<String, Car> dataBase;

    public InMemoryDataBase() {
        dataBase = new HashMap<>();
    }


    private boolean isEmpty(String key) {
        return key == null || key.isEmpty();
    }


    public boolean save(String carNumber, Car value) {
        dataBase.put(carNumber, value);
        return true;
    }

    public boolean update(String carNumber, Car value) {
        dataBase.put(carNumber, value);
        return true;
    }

    public boolean delete(String carNumber) {
        if (dataBase.containsKey(carNumber)) {
            dataBase.remove(carNumber);
            return true;
        }
        return false;
    }

    public Car get(String carNumber) {
        if (dataBase.containsKey(carNumber)) {
            return dataBase.get(carNumber);
        }
        throw new IllegalArgumentException("No key in DB");
    }

    public boolean containsKey(String carNumber) {
        return dataBase.containsKey(carNumber);
    }

}
