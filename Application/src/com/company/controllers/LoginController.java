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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController extends Transition{
    public BorderPane borderPane;
    public TextField usernameField;
    public PasswordField passwordField;

    public void openRegister(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/Register.fxml"));
        borderPane.getChildren().removeAll();
        borderPane.getChildren().setAll(root);
    }

    public void loginUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        var username = usernameField.getText();
        var password = passwordField.getText();
        if (!checkRole(username, password))
        //if(!username.equals("admin") && !password.equals("admin"))
        {
            DatabaseConnection connectDB = new DatabaseConnection();
            Connection connection = connectDB.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            callableStatement.execute();
            ResultSet res = callableStatement.getResultSet();

            if (res.next()) {

                    System.out.println("Login - user: " + username + "\n");
                    Stage mainStage = getNewStage(actionEvent, "../views/Catalog.fxml", true);
                    if(mainStage != null){
                        mainStage.show();
                    }

            } else {
                System.out.println("user: " + username + " doesn't exist\n");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(username);
                alert.setHeaderText(null);
                alert.setContentText("Wrong username/password combination");
                alert.showAndWait();
            }
            callableStatement.close();
        }
        else{
            Stage newStage = getNewStage(actionEvent, "../views/Admin.fxml", true);
            if(newStage != null){
                newStage.show();
            }
                System.out.println("Admin logging...");
        }
    }

    public boolean checkRole(String username, String password) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND admin = ?";

        var role = "admin";

        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, username);
        callableStatement.setString(2, password);
        callableStatement.setString(3, role);
        callableStatement.execute();
        ResultSet res = callableStatement.getResultSet();

        if (res.next()) {
            System.out.println("Login - admin");
            callableStatement.close();
            return true;
        }
        else {
            callableStatement.close();
            return false;
        }

    }

    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }
}
