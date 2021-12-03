package com.company.controllers;

import com.company.database.DatabaseConnection;
import com.company.models.Auto;
import com.company.models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.*;

public class AdminController extends Transition{
    public TableView<Auto> autoTable;
    public TableView<User> userTable;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    public TextField brandField;
    public TextField modelField;
    public TextField generationField;
    public TextField priceField;
    public TextField transmissionField;
    public TextField fuelField;
    public TextField yearField;
    public TextField volumeEField;

    public TextField emailField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField roleField;

    public BorderPane borderPane;

    public void updateCar(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT * FROM auto INNER JOIN detail ON id = autoId";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        ObservableList<Auto> cars = FXCollections.observableArrayList();

        while (resultSet.next()) {
            var auto = new Auto(
                    resultSet.getInt("id"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getString("generation"),
                    resultSet.getInt("price"),
                    resultSet.getString("transmission"),
                    resultSet.getString("fuel"),
                    resultSet.getInt("year"),
                    resultSet.getFloat("volumeE")
            );
            cars.add(auto);
        }
        autoTable.setItems(cars);
        autoTable.setPlaceholder(new Label("It seems, there are no products here!"));
        System.out.println("Auto table data updated");
    }

    public void updateUser(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sqlUser = "SELECT * FROM users";

        ResultSet resultSetUser = connection.createStatement().executeQuery(sqlUser);
        ObservableList<User> users = FXCollections.observableArrayList();

        while (resultSetUser.next()) {
            var user = new User(
                    resultSetUser.getInt("id"),
                    resultSetUser.getString("email"),
                    resultSetUser.getString("username"),
                    resultSetUser.getString("password"),
                    resultSetUser.getString("admin")
            );
            users.add(user);
        }
        userTable.setItems(users);
        userTable.setPlaceholder(new Label("It seems, there are no products here!"));
        System.out.println("Users table data updated");
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        initUser();
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sqlAuto = "SELECT * FROM auto INNER JOIN detail ON id = autoId";

        ResultSet resultSet = connection.createStatement().executeQuery(sqlAuto);
        ObservableList<Auto> cars = FXCollections.observableArrayList();

        while (resultSet.next()) {
            var auto = new Auto(
                    resultSet.getInt("id"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getString("generation"),
                    resultSet.getInt("price"),
                    resultSet.getString("transmission"),
                    resultSet.getString("fuel"),
                    resultSet.getInt("year"),
                    resultSet.getFloat("volumeE")
            );
            cars.add(auto);
        }
        autoTable.setItems(cars);
        autoTable.setPlaceholder(new Label("It seems, there are no products here!"));

        TableColumn<Auto, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Auto, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Auto, String> generationColumn = new TableColumn<>("Generation");
        generationColumn.setCellValueFactory(new PropertyValueFactory<>("generation"));

        TableColumn<Auto, String> priceColumn = new TableColumn<>("Price($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Auto, String> transmissionColumn = new TableColumn<>("Transmission");
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Auto, String> fuelColumn = new TableColumn<>("Fuel");
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<Auto, String> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Auto, String> volumeEColumn = new TableColumn<>("Volume engine");
        volumeEColumn.setCellValueFactory(new PropertyValueFactory<>("volumeE"));

        brandColumn.setPrefWidth(100);
        modelColumn.setPrefWidth(60);
        generationColumn.setPrefWidth(80);
        priceColumn.setPrefWidth(55);
        transmissionColumn.setPrefWidth(90);
        fuelColumn.setPrefWidth(53);
        yearColumn.setPrefWidth(52);
        volumeEColumn.setPrefWidth(90);

        autoTable.getColumns().add(brandColumn);
        autoTable.getColumns().add(modelColumn);
        autoTable.getColumns().add(generationColumn);
        autoTable.getColumns().add(priceColumn);
        autoTable.getColumns().add(transmissionColumn);
        autoTable.getColumns().add(fuelColumn);
        autoTable.getColumns().add(yearColumn);
        autoTable.getColumns().add(volumeEColumn);
        System.out.println("Auto table is full");
        autoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(autoTable.getSelectionModel().getSelectedItem() != null)
                {
                    var sel = autoTable.getSelectionModel().getSelectedItem();
                    System.out.println("Selected car " + sel.getBrand() + " " + sel.getModel() + " " + sel.getGeneration());
                }
            }
        });
    }

    public void initUser() throws SQLException, ClassNotFoundException {
        ObservableList<User> users = FXCollections.observableArrayList();

        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sqlUser = "SELECT * FROM autostore.users";

        ResultSet resultSetUser = connection.createStatement().executeQuery(sqlUser);
        userTable.getColumns().clear();
        while (resultSetUser.next()) {
            var user = new User(
                    resultSetUser.getInt("id"),
                    resultSetUser.getString("email"),
                    resultSetUser.getString("username"),
                    resultSetUser.getString("password"),
                    resultSetUser.getString("admin")
            );
            users.add(user);
        }
        userTable.setItems(users);
        userTable.setPlaceholder(new Label("It seems, there are no products here!"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        roleColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

        emailColumn.setPrefWidth(100);
        usernameColumn.setPrefWidth(100);
        passwordColumn.setPrefWidth(100);
        roleField.setPrefWidth(100);

        userTable.getColumns().add(emailColumn);
        userTable.getColumns().add(usernameColumn);
        userTable.getColumns().add(passwordColumn);
        userTable.getColumns().add(roleColumn);

        System.out.println("Users table is full");
        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(userTable.getSelectionModel().getSelectedItem() != null)
                {
                    var sel = userTable.getSelectionModel().getSelectedItem();
                    System.out.println("Selected user " + sel.getUsername());
                }
            }
        });
    }

    public void deleteCar(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var selectedCar = autoTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null){
            try {
                DatabaseConnection connectDB = new DatabaseConnection();
                Connection connection = connectDB.getConnection();
                System.out.println("Select car " + selectedCar.getBrand());

                String deleteDetail = "DELETE detail FROM detail " +
                        "WHERE autoId = " + selectedCar.getId();

                String deleteCar = "DELETE auto FROM auto " +
                        "WHERE id = " + selectedCar.getId();

                try {
                    PreparedStatement preparedStatement1 = connection.prepareStatement(deleteDetail);
                    PreparedStatement preparedStatement2 = connection.prepareStatement(deleteCar);

                    preparedStatement1.executeUpdate();
                    preparedStatement2.executeUpdate();
                    System.out.println("Auto " + selectedCar.getBrand() + " " + selectedCar.getModel() + " " + selectedCar.getGeneration() + " deleted from database");
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException{
        var selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            try {
                DatabaseConnection connectDB = new DatabaseConnection();
                Connection connection = connectDB.getConnection();
                System.out.println("Select user" + selectedUser.getUsername());

                String deleteUser = "DELETE users FROM users " +
                        "WHERE id = " + selectedUser.getId();

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(deleteUser);

                    preparedStatement.executeUpdate();
                    System.out.println("User " + selectedUser.getUsername() + " deleted from database");


                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void addCar(MouseEvent mouseEvent){
        var brand = brandField.getText();
        var model = modelField.getText();
        var generation = generationField.getText();
        var price = priceField.getText();
        var transmission = transmissionField.getText();
        var fuel = fuelField.getText();
        var year = yearField.getText();
        var volumeE = volumeEField.getText();

        if(!brand.equals("") && !model.equals("") && !generation.equals("") && !price.equals("") &&
            !transmission.equals("") && !fuel.equals("") && !year.equals("") && !volumeE.equals("")){

            try{
                DatabaseConnection connectDB = new DatabaseConnection();
                Connection connection = connectDB.getConnection();

                String insertAuto = "INSERT INTO auto(brand, model, generation) VALUES(?,?,?);";

                String insertDetail = "INSERT INTO detail(autoId, price, transmission, fuel, year, volumeE) " +
                        "VALUES(" +
                        "(SELECT id FROM auto WHERE auto.id = LAST_INSERT_ID()), ?, ?, ?, ?, ?);";

                try{
                    PreparedStatement preparedStatement1 = connection.prepareStatement(insertAuto);
                    PreparedStatement preparedStatement2 = connection.prepareStatement(insertDetail);
                    preparedStatement1.setString(1, brand);
                    preparedStatement1.setString(2, model);
                    preparedStatement1.setString(3, generation);

                    preparedStatement2.setString(1, price);
                    preparedStatement2.setString(2, transmission);
                    preparedStatement2.setString(3, fuel);
                    preparedStatement2.setString(4, year);
                    preparedStatement2.setString(5, volumeE);

                    preparedStatement1.executeUpdate();
                    preparedStatement2.executeUpdate();
                    System.out.println("Auto " + brand + " " + model + " " + generation + " added to database");
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                brandField.clear();;
                modelField.clear();
                generationField.clear();
                priceField.clear();
                transmissionField.clear();
                fuelField.clear();
                yearField.clear();
                volumeEField.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(brand);
                alert.setHeaderText(null);
                alert.setContentText("Added " + brand + " " + model + " " + generation + " to database");
                alert.showAndWait();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void addUser(MouseEvent mouseEvent){
        var email = emailField.getText();
        var username = usernameField.getText();
        var password = passwordField.getText();
        var role = roleField.getText();


        if(!email.equals("") && !username.equals("") && !password.equals("") && !role.equals("")){
            try{
                DatabaseConnection connectDB = new DatabaseConnection();
                Connection connection = connectDB.getConnection();

                String insertUser = "INSERT INTO users(email, username, password, admin) VALUES(?,?,?,?);";

                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, username);
                    preparedStatement.setString(3, password);
                    preparedStatement.setString(4, role);

                    preparedStatement.executeUpdate();
                    System.out.println("User " + username + " added to database");
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                emailField.clear();;
                usernameField.clear();
                passwordField.clear();
                roleField.clear();

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
        System.out.println("Admin logOut");
        borderPane.getChildren().removeAll();
        borderPane.getChildren().setAll(root);
    }
}
