package lk.ijse.pos.db;

import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.model.Order;

import java.util.ArrayList;

public class Database {
    public static ArrayList<Customer> customerList = new ArrayList<>();
    public static ArrayList<Item> itemList = new ArrayList<>();
    public static ArrayList<Order> orderList = new ArrayList<>();

    static {
        customerList.add(new Customer("C-001","Nimal","Panadura",500000));
        customerList.add(new Customer("C-002","Wasantha","Galle",250000));

        itemList.add(new Item("I-001",15,5,"Pen"));
        itemList.add(new Item("I-002",18,6,"Marker"));
    }
}
