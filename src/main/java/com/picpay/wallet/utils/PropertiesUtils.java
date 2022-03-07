package com.picpay.wallet.utils;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */


import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertiesUtils {

    public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    public static void initProperties() {
        String activeProfile = System.getProperty(SPRING_PROFILES_ACTIVE);
        System.err.println(activeProfile);
        if (activeProfile == null) {
            activeProfile = "dev";
        }
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
                = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]
                {new ClassPathResource("application.properties"),
                        new ClassPathResource("application-" + activeProfile + ".properties")};
        propertySourcesPlaceholderConfigurer.setLocations(resources);

    }
}
