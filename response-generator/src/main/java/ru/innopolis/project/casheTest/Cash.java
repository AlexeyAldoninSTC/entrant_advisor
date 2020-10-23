package ru.innopolis.project.casheTest;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

public class Cash {
    public static void main(String[] args) {
        IgniteConfiguration configuration = new IgniteConfiguration();
        DataStorageConfiguration storageConfiguration = new DataStorageConfiguration();
        storageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(false);
        configuration.setDataStorageConfiguration(storageConfiguration);

        Ignite ignite = Ignition.start(configuration);
        IgniteCache<String,String> cache = ignite.getOrCreateCache("testCash");
        cache.put("Str1","val1");
        cache.put("Str2","val2");
        System.out.println(cache.get("Str1"));
//        cache.close();
        ignite.close();

    }
}
