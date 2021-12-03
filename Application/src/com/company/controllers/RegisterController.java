package com.company.controllers;

import com.company.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController extends Transition{
    public TextField emailField;
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public BorderPane borderPane;

    public void registerUser(MouseEvent mouseEvent)  {
        var username = usernameField.getText();
        var email = emailField.getText();
        var password1 = passwordField.getText();
        var password2 = confirmPasswordField.getText();
        if(!username.equals("") && !email.equals("") && !password1.equals("") && !password1.equals(""))
        {
            String textError = "";
            if(!email.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")){
                textError = "Invalid E-mail address";
            }
            if(!password1.equals(password2)){
                textError = "Passwords aren't equal";
            }
            if(!textError.equals("")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(username);
                alert.setHeaderText(null);
                alert.setContentText(textError);
                alert.showAndWait();
                return;
            }
            try{
                DatabaseConnection connectDB = new DatabaseConnection();
                Connection connection = connectDB.getConnection();

                String insert = "INSERT INTO users(email, username, password, admin)" +
                        "VALUES(?, ?, ?, 'user')";
                try{
                    PreparedStatement prSt = connection.prepareStatement(insert);
                    prSt.setString(1, email);
                    prSt.setString(2, username);
                    prSt.setString(3, password1);

                    prSt.executeUpdate();
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                usernameField.clear();;
                emailField.clear();
                passwordField.clear();
                confirmPasswordField.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(username);
                alert.setHeaderText(null);
                alert.setContentText("Added " + username + " to database");
                alert.showAndWait();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        borderPane.getChildren().removeAll();
        borderPane.getChildren().setAll(root);
    }
}
