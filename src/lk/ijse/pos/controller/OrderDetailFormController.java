package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.db.Database;
import lk.ijse.pos.model.ItemDetails;
import lk.ijse.pos.model.Order;

public class OrderDetailFormController {
    public TableColumn col_Code;
    public TableColumn col_Qty;
    public TableColumn col_UnitPrice;
    public TableView<ItemDetails> tblorderDetails;

    public void initialize(){
        col_Code.setCellValueFactory(new PropertyValueFactory<>("code"));
        col_Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        col_UnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }
    public void setData(String orderId){
        for (Order o: Database.orderList
        ) {
            if (o.getOrderId().equals(orderId)){
                ObservableList<ItemDetails> detailsObList=
                        FXCollections.observableArrayList(o.getItems());
                tblorderDetails.setItems(detailsObList);
                return;
            }
        }
    }

}
