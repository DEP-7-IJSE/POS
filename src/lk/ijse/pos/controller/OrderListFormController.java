package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.db.Database;
import lk.ijse.pos.model.Order;
import lk.ijse.pos.views.tm.OrderTM;

import java.io.IOException;

public class OrderListFormController {
    public AnchorPane contextOrderForm;
    public TableView<OrderTM> tblOrder;
    public TableColumn col_Id;
    public TableColumn col_Date;
    public TableColumn col_Customer;
    public TableColumn col_Cost;

    public void initialize(){
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_Customer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        col_Cost.setCellValueFactory(new PropertyValueFactory<>("total"));
        loadAllOrders();
        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../views/OrderDetailForm.fxml"));
            try {
                Parent root=loader.load();
                OrderDetailFormController controller = loader.getController();
                controller.setData(newValue.getId());
                Stage s = new Stage();
                s.setScene(new Scene(root));
                s.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadAllOrders() {
        ObservableList<OrderTM> orderObList = FXCollections.observableArrayList();
        for (Order o :
                Database.orderList) {
            orderObList.add(new OrderTM(o.getOrderId(),o.getOrderDate(),o.getCustomerId(),o.getTotalCost()));
        }
        tblOrder.setItems(orderObList);
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOrderForm.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/DashBoardForm.fxml"))));
    }
}
