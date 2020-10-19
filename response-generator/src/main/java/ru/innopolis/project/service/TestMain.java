package ru.innopolis.project.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

public class TestMain {
    public static void main(String[] args) throws ParseException {
//        IgniteConfiguration configuration = new IgniteConfiguration();
//        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
//        dataStorageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(false);
//        configuration.setDataStorageConfiguration(dataStorageConfiguration);
//
//        Ignite ignite = Ignition.start(configuration);
//
//
//        IgniteCache<String,String> cache = ignite.getOrCreateCache("testCash");
//
//
//
//            cache.put("Str1", "Val1");
//            cache.put("Str2", "Val2");
//            cache.put("Str1", "Val1");
//
//            System.out.println(cache.get("Str1"));
//            ignite.close();

        String arriveTime = "10:20";
        String departureTime = "10:40";
        LocalTime localTime = LocalTime.parse(arriveTime);
        System.out.println(localTime);
        LocalTime localTime1 = LocalTime.parse(departureTime);
        System.out.println(localTime1);
        System.out.println("=======");
        Duration duration = Duration.between(localTime,localTime1);
        System.out.println(duration.toMinutes());

    }
    }

