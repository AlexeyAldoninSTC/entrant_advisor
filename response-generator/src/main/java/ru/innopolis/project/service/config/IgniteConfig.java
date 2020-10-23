package ru.innopolis.project.service.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
//@EnableIgniteRepositories
//public class IgniteConfig {
//
//    @Bean
//    public IgniteConfiguration getConfiguration(){
//        IgniteConfiguration cfg = new IgniteConfiguration();
//        DataStorageConfiguration storageCfg = new DataStorageConfiguration();
//        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
//        cfg.setDataStorageConfiguration(storageCfg);
//        return cfg;
//    }
//}
