package ru.bagmet.demowebshop.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"classpath:configs/demoshop.properties","system:properties"})
public interface DemoShopConfig extends Config {

    @Key("user.login")
    String getUserLogin();

    @Key("user.password")
    String getUserPassword();

}
