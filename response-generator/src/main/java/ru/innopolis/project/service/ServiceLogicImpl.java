package ru.innopolis.project.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.project.InMemoryDB.InMemoryDataBase;
import ru.innopolis.project.entity.Car;
import ru.innopolis.project.entity.Condition;
import ru.innopolis.project.entity.Rule;
import ru.innopolis.project.repositories.ConditionRepository;
import ru.innopolis.project.repositories.RulesRepository;

import javax.cache.Cache;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceLogicImpl implements ServiceLogic {
//    Ignite ignite = Ignition.start();
    protected final ConditionRepository conditionRepository;
    protected final RulesRepository rulesRepository;
//    private final InMemoryDataBase inMemoryDataBase;
    private final IgniteCache<String, Car> igniteCache = getIgnite().getOrCreateCache("testCash");


    @Autowired
    public ServiceLogicImpl(ConditionRepository conditionRepository, RulesRepository rulesRepository) {
        this.conditionRepository = conditionRepository;
        this.rulesRepository = rulesRepository;
//        this.inMemoryDataBase = inMemoryDataBase;
    }

    @Override
    public Map<String, Boolean> execute(String[] rules, Map<String, Object> features) {
        HashMap<String, Boolean> hashMap = new HashMap<>();

        for (String ruleName : rules) {
            Rule rule = rulesRepository.findByName(ruleName);
            if (rule == null) {
                hashMap.put(ruleName, false);
                continue;
            }

            Long ruleId = rule.getId();
            hashMap.put(ruleName, checkByOneRule(features, ruleId));
        }
        return hashMap;
    }

    private boolean checkByOneRule(Map<String, Object> features, Long ruleId) {
        for (Condition k : conditionRepository.findAllByRuleId(ruleId)) {
            Rule foundRule = rulesRepository.findById(ruleId).orElseThrow(() -> new RuntimeException("not found rule"));
            if (foundRule.getName().equals("in") || foundRule.getName().equals("out") || foundRule.getName().equals("pay")) {
                if (!checkOneRowForPark(k, features)) {
                    return false;
                }
            } else if (!checkOneRow(k, features)) {
                return false;
            }
        }
        return true;
    }

    protected boolean checkOneRow(Condition condition, Map<String, Object> feature) {
        Object obj = feature.get(condition.getFeatureName());
        if (obj == null) {
            return false;
        }
        Integer s = Integer.parseInt(String.valueOf(obj));


        switch (condition.getOperation()) {
            case ">":
                return s > condition.getValue();
            case "<":
                return s < condition.getValue();
            case "=":
                return s.equals(condition.getValue());
        }

        return false;
    }

    protected boolean checkOneRowForPark(Condition condition, Map<String, Object> features) {
        String carNumber = String.valueOf(features.get("number"));

        if (carNumber == null) {
            throw new RuntimeException("Not car number");
        }

        switch (condition.getFeatureName()) {
            case "in":                                                                                                         //в случае in
                if (!igniteCache.containsKey(carNumber)) {                                                                     //если в мапе нет текущего гос номера(ключа)
                    igniteCache.put(carNumber, new Car(LocalTime.parse((CharSequence) features.get("time")), false));     //помещаем в мапу гос номер(ключ) и сам авто( поля в нем время заезда и флаг оплаты на false)
                    printIgnite(igniteCache);                                                                                  //просто проверка того, что в мапу что-то записалось
                    return true;                                                                                               //вернуть тру
                } else {                                                                                                       //если номера нет,то
                    System.out.println(igniteCache + " : " + (igniteCache.size()));                                            //(тут самопроверка)
                    return false;                                                                                              //вернуть фолс
                }
            case "out":                                                                                                         //в случае out
                if (igniteCache.containsKey(carNumber)) {                                                                       //если в мапе содержится такой ключ,то заходим в условие
                    LocalTime departureTime = LocalTime.parse((CharSequence) features.get("time"));                             //получаем время выезда (парсим входной json)
                    LocalTime arrivalTime = igniteCache.get(carNumber).getDate();                                               //получаем время въезда
                    if (Duration.between(arrivalTime, departureTime).toMinutes() < condition.getValue()) {                      //получаем фактическое время остановки, и проверяем меньше ли оно бесплатного периода(в нашем случае 5 минут)
                        igniteCache.get(carNumber).setPay(true);                                                                //если да,то устанавливаем флаг оплаты на тру и выпускаем машину
                        igniteCache.remove(carNumber);                                                                          //удаляем из кэша
                        System.out.println(igniteCache + " : " + (igniteCache.size()));                                         //самопроверка
                        return true;                                                                                            //т.к. время простоя меньше 5 минут, то выдаем тру и отпускаем машину
                    }
                    if (!igniteCache.get(carNumber).isPay()) {                                                                  //если текущая машина оплатила стоянку(т.е. !false (по русски true) , т.к. в момент создания авто у нас флаг в fasle
                                                                                                                                //не заходит! проверить!
                            igniteCache.remove(carNumber);                                                                      //удаляем машину из кэша
                            System.out.println(igniteCache + " : " + (igniteCache.size()));                                     //самопроверка
                            return true;                                                                                        //возвращаем тру
                                                                                                                              //возвращаем фолс (это если машина не оплатила стоянку и пытается выехать)
                    }
                    igniteCache.remove(carNumber);                                                                              //повтор кода по идее мы сюда даже не доберемся
                    System.out.println(igniteCache + " : " + (igniteCache.size()));                                             //самопроверка
                    return true;                                                                                                //возврат тру
                } else {                                                                                                        //если номера нет, то как ты тут оказался?!
                    System.out.println(igniteCache + " : " + (igniteCache.size()));                                             //самопроверка
                    return false;                                                                                               //т.к. номера нет, то возврат фолс

                }
            case "pay":                                                                                                         //в случае pay
                igniteCache.get(carNumber).setPay(true);                                                                        //устанавливаем у текущей машины флаг оплаты в тру
                System.out.println(igniteCache + " : " + (igniteCache.size()));                                                 //самопроверка
                return true;                                                                                                    //возвращаем тру
        }
        throw new IllegalArgumentException("I don't know what is it =\\");
    }

    private Ignite getIgnite() {                                                                                              //странный участок кода, без которого в первый раз ничего не запускалось
        IgniteConfiguration configuration = new IgniteConfiguration();                                                        //а теперь спокойно работает без него
        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
        dataStorageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(false);
        configuration.setDataStorageConfiguration(dataStorageConfiguration);

        return Ignition.start(configuration);
    }

    public void printIgnite(IgniteCache<String,Car> igniteCache){                                                               //перебор нашей мапы,
        for (Cache.Entry<String, Car> k : igniteCache) {                                                                        //Apache ignite по факту та же мапа.
            System.out.println(k.getKey() + " |--| " + k.getValue());
        }
    }
}
