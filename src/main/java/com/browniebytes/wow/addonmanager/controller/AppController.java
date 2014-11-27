package com.browniebytes.wow.addonmanager.controller;

import com.browniebytes.wow.addonmanager.model.Addon;
import com.browniebytes.wow.addonmanager.model.AddonCatalog;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledExecutorService;

public class AppController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    private static final String GET_ADDON_CATALOG_URL = "http://api.mmoui.com/game/WOW/filelist.json";

    @Autowired
    private Configuration configuration;

    @Autowired
    private ScheduledExecutorService executorService;

    @Autowired
    private FXMLLoader fxmlLoader;

    @Autowired
    private PleaseWaitController pleaseWaitController;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AddonCatalog addonCatalog;

    @FXML
    private VBox centerBox;

    @FXML
    private VBox bottomBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOGGER.debug("Initializing controller...");
        showProgressIndicator("Fetching addon catalog...");

        final GetAddonCatalogWorker worker = new GetAddonCatalogWorker();
        executorService.execute(worker);
    }

    private void showProgressIndicator(final String message) {
        centerBox.setAlignment(Pos.CENTER);
        try {
            fxmlLoader.setLocation(getClass().getResource("/fxml/PleaseWaitPane.fxml"));
            final VBox vBox = (VBox) fxmlLoader.load();
            final PleaseWaitController controller = fxmlLoader.getController();
            controller.setMessage(message);

            centerBox.getChildren().clear();
            centerBox.getChildren().add(vBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class GetAddonCatalogWorker extends Task<String> {
        @Override
        protected String call() throws Exception {
            LOGGER.debug("Getting addon catalog...");
            try {
                final Addon[] addonData = restTemplate.getForObject(GET_ADDON_CATALOG_URL, Addon[].class);
                addonCatalog.loadCatalog(addonData);
            } catch (Throwable e) {
                LOGGER.error("Error encountered when fetching addon catalog.", e);
            }
            return null;
        }

        @Override
        protected void succeeded() {
            final DirectoryScanWorker scanWorker = new DirectoryScanWorker();
            executorService.execute(scanWorker);
        }
    }

    private class DirectoryScanWorker extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            LOGGER.debug("Scanning addon directory...");
            pleaseWaitController.setMessage("Scanning addon directory...");
            final String gameDirString = configuration.getString("game.dir");
            final String addonDirString = gameDirString + "/Interface/Addons";
            LOGGER.debug(String.format("Scanning game directory %s", gameDirString));

            final File gameDir = new File(gameDirString);
            final File addonDir = new File(addonDirString);

            if (gameDir.exists()) {
                if (addonDir.exists()) {
                    LOGGER.debug("Found addon directory, scanning for existing addons...");
                    final File[] fileList = addonDir.listFiles();
                    if (fileList != null) {
                        for (File f : fileList) {
                            if (f.isDirectory()) {
                                // Process addon
                            }
                        }
                    }
                } else {
                    LOGGER.debug("Addon directory does not exist");
                }
            } else {
                LOGGER.debug("Game directory does not exist");
            }

            return null;
        }

        @Override
        protected void succeeded() {

        }
    }
}
