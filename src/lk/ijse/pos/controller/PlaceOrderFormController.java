package lk.ijse.pos.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.db.Database;
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.model.ItemDetails;
import lk.ijse.pos.model.Order;
import lk.ijse.pos.views.tm.CartTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderFormController {
    public AnchorPane contextOrderForm;
    public Label txtDate;
    public Label txtTime;
    public ComboBox<String> cmbCustomerId;
    public TextField txtName;
    public TextField txtSalary;
    public TextField txtAddress;
    public ComboBox<String> cmbItemId;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TableColumn col_Code;
    public TableColumn col_Description;
    public TableColumn col_Qty;
    public TableColumn col_UnitPrice;
    public TableColumn col_Total;
    public TableView<CartTM> tblCart;
    public TextField txtQTY;
    public Label txtTotal;
    public Label txtOId;
    ObservableList<CartTM> cartObList = FXCollections.observableArrayList();

    public void initialize(){
        col_Code.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("desc"));
        col_Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        col_UnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        setDateAndTime();
        loadAllCustomerIds();
        loadAllItemIds();
        setOrderId();
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData(newValue);
        });
        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemData(newValue);
        });
        tblCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tempCartTM=newValue;
        });
    }

    private void setOrderId() {
        if (Database.orderList.size()>0){
            String tempNumber = Database.orderList.get(Database.customerList.size()-1).getOrderId();
            String array[]= tempNumber.split("O-");
            int number=Integer.parseInt(array[1]);
            number++;

            if (number>100){
                tempNumber="O-"+number;
            }else if(number>10){
                tempNumber="O-0"+number;
            }else{
                tempNumber="O-00"+number;
            }
            txtOId.setText(tempNumber);

        }else{
            txtOId.setText("O-001");
        }
    }

    private void loadAllItemIds() {
        ObservableList<String> customerObList= FXCollections.observableArrayList();
        for (Item c :
                Database.itemList) {
            customerObList.add(c.getCode());
        }
        cmbItemId.setItems(customerObList);
    }

    private void setItemData(String code) {
        for (Item i :
                Database.itemList) {
            if(code.equals(i.getCode())){
                txtDescription.setText(i.getDesc());
                txtQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
                break;
            }
        }
    }

    private void setCustomerData(String id) {
        for (Customer c :
                Database.customerList) {
            if(id.equals(c.getId())){
                txtName.setText(c.getName());
                txtAddress.setText(c.getAddress());
                txtSalary.setText(String.valueOf(c.getSalary()));
                break;
            }
        }
    }

    private void loadAllCustomerIds() {
        ObservableList<String> customerObList= FXCollections.observableArrayList();
        for (Customer c :
                Database.customerList) {
            customerObList.add(c.getId());
        }
        cmbCustomerId.setItems(customerObList);
    }

    private void setDateAndTime() {
        Date date= new Date();
        SimpleDateFormat f= new SimpleDateFormat("YYYY-MM-dd");
        txtDate.setText(f.format(date));
        Thread timer = new Thread(()->{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String time= simpleDateFormat.format(new Date());
                Platform.runLater(()->{
                    txtTime.setText(time);
                });
            }
        });
        timer.start();
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOrderForm.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/DashBoardForm.fxml"))));
    }

    public void addToCart(ActionEvent actionEvent) {
        if(Integer.parseInt(txtQTY.getText())<=Integer.parseInt(txtQtyOnHand.getText()) && Integer.parseInt(txtQTY.getText())!=0) {
            int qty = Integer.parseInt(txtQTY.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            double total = qty * unitPrice;
            CartTM tm = new CartTM(
                    cmbItemId.getValue(),
                    txtDescription.getText(),
                    qty,
                    unitPrice,
                    total
            );
            boolean isExists = checkIsExists(tm);
            if (isExists) {
                tblCart.refresh();
            } else {
                cartObList.add(tm);
                calculateTotalCost();
                tblCart.setItems(cartObList);
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"",ButtonType.CLOSE).show();
        }
    }

    private boolean checkIsExists(CartTM tm) {
        int counter = 0;
        for (CartTM temp :
                cartObList) {
            if(temp.getItemCode().equals(tm.getItemCode())) {
                if (cartObList.get(counter).getQty() >= Integer.parseInt(txtQTY.getText())+cartObList.get(counter).getQty()) {
                    cartObList.get(counter).setQty(tm.getQty() + temp.getQty());
                    cartObList.get(counter).setTotal(tm.getTotal() + temp.getTotal());
                    calculateTotalCost();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Invalid Qty",ButtonType.CLOSE).show();
                }
                return true;
            }
            counter++;
        }
        return false;
    }

    CartTM tempCartTM;

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        if (tempCartTM!=null){
            for (CartTM tm:cartObList
            ) {
                if (tm.getItemCode().equals(tempCartTM.getItemCode())){
                    cartObList.remove(tm);
                    calculateTotalCost();
                    tblCart.refresh();
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please Select a Row.").show();
        }
    }

    void calculateTotalCost() {
        double total=0.00;
        for (CartTM tm:cartObList
        ) {
            total+=tm.getTotal();
        }
        txtTotal.setText(total+" /=");
    }

    public void placeOrder(ActionEvent actionEvent) {
        ArrayList<ItemDetails> details = new ArrayList<>();
        for (CartTM tm :
                cartObList) {
            details.add(new ItemDetails(tm.getItemCode(), tm.getQty(), tm.getUnitPrice()));
        }
        Order order=new Order(
                txtOId.getText(),txtDate.getText(),cmbCustomerId.getValue(),Double.parseDouble(txtTotal.getText().split("/=")[0]),details
        );
        if(Database.orderList.add(order)){
            new Alert(Alert.AlertType.CONFIRMATION,"Placed",ButtonType.FINISH).show();
            cartObList.clear();
            calculateTotalCost();
        }
    }
}
