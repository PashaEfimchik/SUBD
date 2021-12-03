package com.company.controllers;

import com.company.database.DatabaseConnection;
import com.company.models.Auto;
import com.company.models.Cart;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.sql.*;

import static com.company.controllers.CartController.cartStage;
import static com.company.controllers.CartController.cartObservableList;

public class CatalogController extends Transition{
    public TableView<Auto> autoTable;
    public Label brandLabel;
    public Label modelLabel;
    public Label generationLabel;
    public CartController cartController;
    public Button addToCartButton;
    public Pane infoPane;
    public TextField totalPriceField;
    public CatalogController()
    {
        cartController = new CartController();
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        infoPane.setVisible(false);
        addToCartButton.setVisible(false);
        totalPriceField.setVisible(false);

        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT * FROM auto INNER JOIN detail ON id = autoId";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        ObservableList<Auto> cars = FXCollections.observableArrayList();

        while (resultSet.next()) {
            System.out.println("resultSet: " + resultSet.getInt("id"));
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

        autoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(autoTable.getSelectionModel().getSelectedItem() != null)
                {
                    var sel = autoTable.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Value " + sel.getBrand() + " " + sel.getModel() + " " + sel.getGeneration());
                    brandLabel.setText("Brand: " + sel.getBrand());
                    modelLabel.setText("Model: " + sel.getModel());
                    generationLabel.setText("Generation: " + sel.getGeneration());
                    infoPane.setVisible(true);
                    addToCartButton.setVisible(true);
                    totalPriceField.setVisible(true);
                    var selected = autoTable.getSelectionModel().getSelectedItem();
                    if(selected != null){
                        int cost = Integer.parseInt("0" + selected.getPrice());
                        if(cost != 0){
                            totalPriceField.setText(String.valueOf(cost));
                        }else{
                            totalPriceField.clear();
                        }
                    }
                }
                else{
                    infoPane.setVisible(false);
                    addToCartButton.setVisible(false);
                    totalPriceField.setVisible(false);
                }
            }
        });
    }

    public Integer getOverallCost(){
        int sum = 0;
        for(var item: cartObservableList){
            sum += item.getTotalPrice();
        }
        return sum;
    }

    public void addToCart(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var selectedCar = autoTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null){
            cartController.addToCart(selectedCar);

            try {
                DatabaseConnection connectDB = new DatabaseConnection();
                Connection connection = connectDB.getConnection();
                System.out.println("selectCar.getId(): " + selectedCar.getId());

                String insert = "INSERT INTO cart(autoId, totalPrice)" +
                      "VALUES(" +
                        " (SELECT id FROM auto WHERE auto.id = '" + selectedCar.getId() + "'), ?)";
                try {
                    PreparedStatement prSt = connection.prepareStatement(insert);
                    prSt.setInt(1, getOverallCost());

                    prSt.executeUpdate();
                    System.out.println("Auto " + selectedCar.getBrand() + " added to cart");
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void logOut(ActionEvent actionEvent) {
        if(cartStage!= null){
            cartStage.close();
            cartStage = null;
        }
        cartObservableList.clear();
        Stage login = getNewStage(actionEvent, "../views/Login.fxml", true);
        if(login != null) {
            login.show();
        }
    }

    public void showCart(ActionEvent actionEvent) {
        if(cartStage == null) {
            cartStage = getNewStage(actionEvent, "../views/Cart.fxml", false);
            if(cartStage != null) {
                cartStage.show();
            }
        }
    }

    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

}
