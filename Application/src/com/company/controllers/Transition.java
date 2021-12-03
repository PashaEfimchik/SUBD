package com.company.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Transition {
    private static double x = 0;
    private static double y = 0;

    public static Stage getNewStage(Event event, String pathToFxml, Boolean hideCurrent) {
        try{
            Parent root = FXMLLoader.load(Transition.class.getResource(pathToFxml));
            Stage stage = new Stage();
            stage.setTitle("New Stage");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            root.setOnMousePressed(event1 -> {
                x = event1.getSceneX();
                y = event1.getSceneY();
            });
            root.setOnMouseDragged(event2 -> {
                stage.setX(event2.getScreenX() - x);
                stage.setY(event2.getScreenY() - y);
            });
            if(hideCurrent) {
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            return stage;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
