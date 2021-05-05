package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.db.Database;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.views.tm.ItemTM;

import java.io.IOException;

public class ItemFormController {
    public AnchorPane contextOfItem;
    public TextField txtItemCode;
    public TextField txtPrice;
    public TextField txtQtyOnHand;
    public TextField txtDescription;
    public Button btnSave;
    public TableView<ItemTM> tblItem;
    public TableColumn col_Code;
    public TableColumn col_Description;
    public TableColumn col_Price;
    public TableColumn col_Qty;
    public TableColumn col_Delete;
    public TextField txtSearch;

    ObservableList<ItemTM> obList = FXCollections.observableArrayList();

    public void initialize(){
        col_Code.setCellValueFactory(new PropertyValueFactory<>("code"));
        col_Price.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        col_Qty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("desc"));
        col_Delete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadAllItem("");
        setItemId();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData(newValue);
            }
        });
    }

    private void setItemId() {
        if (Database.itemList.size()>0){
            String tempNumber = Database.itemList.get(Database.itemList.size()-1).getCode();
            String array[]= tempNumber.split("I-");
            int number=Integer.parseInt(array[1]);
            number++;

            if (number>100){
                tempNumber="I-"+number;
            }else if(number>10){
                tempNumber="I-0"+number;
            }else{
                tempNumber="I-00"+number;
            }
            txtItemCode.setText(tempNumber);

        }else{
            txtItemCode.setText("I-001");
        }
    }

    private void setData(ItemTM newValue) {
        txtItemCode.setText(newValue.getCode());
        txtPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
        txtDescription.setText(newValue.getDesc());
        btnSave.setText("Update");
    }

    private void loadAllItem(String searchText) {
        obList.clear();
        for (Item i:Database.itemList
        ) {
            Button btn = new Button("Delete");
            btn.setOnAction(e->{
                if (Database.itemList.remove(i)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                    loadAllItem("");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again").show();
                }
            });
            if (i.getCode().contains(searchText) || i.getDesc().contains(searchText)){
                obList.add(new ItemTM(i.getCode(),i.getUnitPrice(),i.getQtyOnHand(),i.getDesc(),btn));
            }
        }
        tblItem.setItems(obList);
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Item item = new Item(
                txtItemCode.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()),
                txtDescription.getText()
        );
        if(btnSave.getText().equalsIgnoreCase("Save"))
        if(Database.itemList.add(item)){
            new Alert(Alert.AlertType.CONFIRMATION,"Done",ButtonType.OK).show();
            loadAllItem("");
        }else{
            new Alert(Alert.AlertType.WARNING,"Close",ButtonType.CLOSE).show();
        }else{
            int counter = 0;
            for (Item i : Database.itemList
            ) {
                if (txtItemCode.getText().equals(i.getCode())) {
                    Database.itemList.get(counter).setDesc(txtDescription.getText());
                    Database.itemList.get(counter).setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
                    Database.itemList.get(counter).setUnitPrice(Double.parseDouble(txtPrice.getText()));
                    loadAllItem("");
                }
            }
        }
    }

    public void refreshForm(ActionEvent actionEvent) {
        setItemId();
        txtDescription.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
        btnSave.setText("Save");
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOfItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/DashBoardForm.fxml"))));
    }

    public void searchItem(KeyEvent keyEvent) {
        loadAllItem(txtSearch.getText());
    }
}
