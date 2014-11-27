package com.browniebytes.wow.addonmanager;

import com.browniebytes.wow.addonmanager.config.AppConfig;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

public class AddonManagerMain extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddonManagerMain.class);

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Main.fxml"));
        loader.setControllerFactory(
                new Callback<Class<?>, Object>() {
                    @Override
                    public Object call(Class<?> aClass) {
                        return context.getBean(aClass);
                    }
                }
        );

        try {
            final Object root = loader.load();
            if (root instanceof Parent) {
                final Scene scene = new Scene((Parent) root);
                primaryStage.setOnCloseRequest(
                        new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent windowEvent) {
                                LOGGER.debug("Shutting down executor service...");
                                ((ScheduledExecutorService) context.getBean("executorService")).shutdownNow();
                            }
                        }
                );
                primaryStage.setScene(scene);
                primaryStage.setWidth(640.0);
                primaryStage.setHeight(480.0);
                primaryStage.show();
            } else {
                throw new IllegalArgumentException("Invalid root node");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
