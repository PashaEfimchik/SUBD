package com.company;

import com.company.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    public Button connectButton;
    public TextField showUsernameLabel;
    public Label textLabel;

    public void connectButton(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String query = "SELECT * FROM users";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                textLabel.setText(resultSet.getString("language"));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
