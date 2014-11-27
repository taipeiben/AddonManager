package com.browniebytes.wow.addonmanager.config;

import com.browniebytes.wow.addonmanager.controller.AppController;
import com.browniebytes.wow.addonmanager.controller.PleaseWaitController;
import com.browniebytes.wow.addonmanager.model.AddonCatalog;
import com.browniebytes.wow.addonmanager.util.AppControllerFactory;
import javafx.fxml.FXMLLoader;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@org.springframework.context.annotation.Configuration
public class AppConfig {

    private static final String PROPERTIES_FILENAME = "app.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public AppControllerFactory appControllerFactory() {
        LOGGER.debug("Creating AppControllerFactory...");
        return new AppControllerFactory();
    }

    @Bean
    @Scope("prototype")
    public AppController appController() {
        LOGGER.debug("Creating AppController...");
        return new AppController();
    }

    @Bean
    public FXMLLoader fxmlLoader() {
        LOGGER.debug("Creating FXMLLoader...");
        final FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(appControllerFactory());
        return loader;
    }

    @Bean
    public PleaseWaitController pleaseWaitController() {
        LOGGER.debug("Creating PleaseWaitController...");
        return new PleaseWaitController();
    }

    @Bean
    public Configuration appSettings() {
        LOGGER.debug("Loading configuration...");

        try {
            return new PropertiesConfiguration(PROPERTIES_FILENAME);
        } catch (ConfigurationException e) {
            LOGGER.debug("Could not load configuration", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ScheduledExecutorService executorService() {
        LOGGER.debug("Creating executor...");
        return Executors.newScheduledThreadPool(2);
    }

    @Bean
    public RestTemplate restTemplate() {
        LOGGER.debug("Creating RestTemplate...");
        return new RestTemplate();
    }

    @Bean
    public AddonCatalog addonCatalog() {
        return new AddonCatalog();
    }
}
