package ru.innopolis.project.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("ignite_config")
public class ApplicationIgniteConfig {

    @Bean
    public Ignite ignite() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        DataStorageConfiguration storageCfg = new DataStorageConfiguration();
        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
        storageCfg.setStoragePath("IgniteCache");
        cfg.setDataStorageConfiguration(storageCfg);
        Ignite ignite = Ignition.start(cfg);
        ignite.cluster().active(true);
        return ignite;
    }
}
