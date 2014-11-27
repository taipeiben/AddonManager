package com.browniebytes.wow.addonmanager.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PleaseWaitController {

    @FXML
    private Label messageLabel;

    public void setMessage(final String message) {
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        messageLabel.setText(message);
                    }
                }
        );
    }
}
