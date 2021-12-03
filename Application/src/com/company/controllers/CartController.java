package com.company.controllers;

import com.company.models.Auto;
import com.company.models.Cart;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CartController {
    public static ObservableList<Cart> cartObservableList = FXCollections.observableArrayList();
    public TableView<Cart> cartTable;
    public static Stage cartStage;
    public Label overallPriceField;

    public void initialize(){

        cartTable.setItems(cartObservableList);
        cartTable.setPlaceholder(new Label("Your requested products will appear here"));

        cartObservableList.addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Cart> c) {
                overallPriceField.setText("Overall cost: " + String.valueOf(getOverallCost()) + "$");
            }
        });

        TableColumn<Cart, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Cart, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Cart, String> generationColumn = new TableColumn<>("Generation");
        generationColumn.setCellValueFactory(new PropertyValueFactory<>("generation"));

        TableColumn<Cart, String> priceColumn = new TableColumn<>("Price($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Cart, String> transmissionColumn = new TableColumn<>("Transmission");
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Cart, String> fuelColumn = new TableColumn<>("Fuel");
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<Cart, String> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Cart, String> volumeEColumn = new TableColumn<>("Volume engine");
        volumeEColumn.setCellValueFactory(new PropertyValueFactory<>("volumeE"));

        cartTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        brandColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 30);
        modelColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        generationColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        priceColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        transmissionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        fuelColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        yearColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        volumeEColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);

        cartTable.getColumns().add(brandColumn);
        cartTable.getColumns().add(modelColumn);
        cartTable.getColumns().add(generationColumn);
        cartTable.getColumns().add(priceColumn);
        cartTable.getColumns().add(transmissionColumn);
        cartTable.getColumns().add(fuelColumn);
        cartTable.getColumns().add(yearColumn);
        cartTable.getColumns().add(volumeEColumn);
        overallPriceField.setText("Overall cost: " + String.valueOf(getOverallCost()) + "$");
    }

    public Integer getOverallCost(){
        int sum = 0;
        for(var item: cartObservableList){
            sum += item.getTotalPrice();
        }
        return sum;
    }

    public void closeCart(ActionEvent actionEvent) {
        cartStage.close();
        cartStage = null;
    }

    public void purchase(ActionEvent actionEvent) {
        if(cartObservableList.size() != 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cart");
            alert.setHeaderText(null);
            alert.setContentText("Purchased successfully!");
            alert.showAndWait();
            cartObservableList.clear();
        }
    }

    public void addToCart(Auto auto){
        int index = -1;
        for(var cartItem : cartObservableList){
            if(cartItem.getBrand().equals(auto.getBrand()) &&
                    cartItem.getModel().equals(auto.getModel()) &&
                    cartItem.getGeneration().equals(auto.getGeneration()) &&
                    cartItem.getPrice() == auto.getPrice() &&
                    cartItem.getTransmission().equals(auto.getTransmission()) &&
                    cartItem.getFuel().equals(auto.getFuel()) &&
                    cartItem.getYear() == auto.getYear() &&
                    cartItem.getVolumeE() == auto.getVolumeE())
            {
                index = cartObservableList.indexOf(cartItem);
            }
        }
        if(index != -1) {
            cartObservableList.remove(cartObservableList.get(index));
        }
        cartObservableList.add(new Cart(auto.getBrand(), auto.getModel(), auto.getGeneration(), auto.getPrice(), auto.getTransmission(),
                auto.getFuel(), auto.getYear(), auto.getVolumeE()));
    }

    public void deleteCartItem(ActionEvent actionEvent) {
        var cartItem = cartTable.getSelectionModel().getSelectedItem();
        if(cartItem != null) {
            cartObservableList.remove(cartItem);
        }
    }

    public void clearCart(ActionEvent actionEvent) {
        cartObservableList.clear();
    }
}
