package com.wallet.datastore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.ObjectifyService;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuration class which configures Objectify
 *
 */
@Slf4j
@Configuration
public class ObjectifyConfig {

    private String datastoreHost;
    private static String PROJECT_ID = "giovanic";

    @Autowired
    private Environment environment;


    /**
     * Creates and registers a filter, because Objectify requires it to clean up any thread-local transaction contexts and pending asynchronous operations that
     * remain at the end of a request
     *
     * @return {@link FilterRegistrationBean<ObjectifyFilter>}
     */
    @Bean
    public FilterRegistrationBean<ObjectifyFilter> objectifyFilterRegistration() {
        final FilterRegistrationBean<ObjectifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ObjectifyFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * Registers the {@link ObjectifyListener} listener defined below which initializes Objectify
     *
     * @return {@link ServletListenerRegistrationBean<ObjectifyListener>}
     */
    @Bean
    public ServletListenerRegistrationBean<ObjectifyListener> listenerRegistrationBean() {
        ServletListenerRegistrationBean<ObjectifyListener> bean = new ServletListenerRegistrationBean<>();
        bean.setListener(new ObjectifyListener());
        return bean;
    }

    /**
     * Listener which initializes Objectify for the application
     */
    @WebListener
    public class ObjectifyListener implements ServletContextListener {

        /**
         * Picks the correct Datastore project based on Spring Profiles
         *
         * @throws IOException thrown by {@link GoogleCredentials}
         */
        private void initializeService() throws IOException {
            Set<String> activeProfiles = new HashSet<>(Arrays.asList(environment.getActiveProfiles()));
            DatastoreOptions.Builder datastoreBuilder = DatastoreOptions.newBuilder();
            datastoreHost = System.getenv("DATASTORE_HOST");

            log.info("Connecting to " + datastoreHost + " datastore server");

            if (datastoreHost != null && datastoreHost.length() > 0) {
                datastoreBuilder.setHost(datastoreHost);
            }

            datastoreBuilder.setProjectId(PROJECT_ID);

            ObjectifyService.init(new ObjectifyFactory(datastoreBuilder.build().getService()));
        }

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            try {
                initializeService();
                ObjectifyRegister.registerClasses();
            } catch (IOException e) {
                e.fillInStackTrace();
                throw new RuntimeException(e);
            }
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            // We need to implement this empty method because of the ServletContextListener interface
        }

    }

}
