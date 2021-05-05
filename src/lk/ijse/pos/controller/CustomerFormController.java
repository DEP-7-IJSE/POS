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
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.views.tm.CustomerTM;

import javax.xml.crypto.Data;
import java.io.IOException;

public class CustomerFormController {
    public AnchorPane contextOfCustomer;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerSalary;
    public TextField txtCustomerAddress;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn col_Id;
    public TableColumn col_Name;
    public TableColumn col_Address;
    public TableColumn col_Salary;
    public TableColumn col_Delete;
    public Button btnSave;
    public TextField txtSearch;

    public void initialize(){
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_Salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        col_Delete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadAllCustomers();
        setCustomerId();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData(newValue);
            }
        });
    }

    private void setCustomerId() {
        if (Database.customerList.size()>0){
            String tempNumber = Database.customerList.get(Database.customerList.size()-1).getId();
            String array[]= tempNumber.split("C-");
            int number=Integer.parseInt(array[1]);
            number++;

            if (number>100){
                tempNumber="C-"+number;
            }else if(number>10){
                tempNumber="C-0"+number;
            }else{
                tempNumber="C-00"+number;
            }
            txtCustomerId.setText(tempNumber);

        }else{
            txtCustomerId.setText("C-001");
        }
    }

    private void setData(CustomerTM newValue) {
        txtCustomerId.setText(newValue.getId());
        txtCustomerName.setText(newValue.getName());
        txtCustomerAddress.setText(newValue.getAddress());
        txtCustomerSalary.setText(String.valueOf(newValue.getSalary()));
        btnSave.setText("Update");
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTM> observableList= FXCollections.observableArrayList();
        for (Customer temp : Database.customerList) {
            Button btn=new Button("Delete");

            observableList.add(new CustomerTM(temp.getId(), temp.getName(), temp.getAddress(), temp.getSalary(), btn));

            btn.setOnAction(e->{
                if(Database.customerList.remove(temp)){
                    loadAllCustomers();
                }
            });
        }
        tblCustomer.setItems(observableList);
    }

    public void refreshForm(ActionEvent actionEvent) {
        setCustomerId();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerSalary.clear();
        btnSave.setText("Save");
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOfCustomer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/DashBoardForm.fxml"))));
    }

    public void btnsaveOnAction(ActionEvent actionEvent) {
        Customer customer=new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText())
        );
        if(btnSave.getText().equalsIgnoreCase("Save")){
            if(Database.customerList.add(customer)){
                new Alert(Alert.AlertType.CONFIRMATION,"Done",ButtonType.OK).show();
                loadAllCustomers();
            }else{
                new Alert(Alert.AlertType.WARNING,"Close",ButtonType.CLOSE).show();
            }
        }else{
            for (int i = 0; i < Database.customerList.size(); i++) {
                if (txtCustomerId.getText().equals(Database.customerList.get(i).getId())) {
                    Database.customerList.remove(i);
                    if (Database.customerList.add(customer)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Updated", ButtonType.OK).show();
                        loadAllCustomers();
                        break;
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Close", ButtonType.CLOSE).show();
                    }
                }
            }
        }
    }

    public void search(KeyEvent keyEvent) {
        String searchText = "";
        searchText = txtSearch.getText();

        ObservableList<CustomerTM> searchTm = FXCollections.observableArrayList();

        for (Customer c1 : Database.customerList
        ) {
            if (
                    c1.getId().contains(searchText) ||
                            c1.getName().contains(searchText) ||
                            c1.getAddress().contains(searchText)
            ) {
                Button btn = new Button("Delete");
                searchTm.add(new CustomerTM(
                        c1.getId(),
                        c1.getName(),
                        c1.getAddress(), c1.getSalary(), btn));
            }
        }
        tblCustomer.setItems(searchTm);
    }
}
