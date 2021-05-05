package lk.ijse.pos.views.tm;

import javafx.scene.control.Button;

public class ItemTM {
    private String code;
    private double unitPrice;
    private int qtyOnHand;
    private String desc;
    private Button btn;

    public ItemTM() {
    }

    public ItemTM(String code, double unitPrice, int qtyOnHand, String desc,  Button btn) {
        this.code = code;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.desc = desc;
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + code + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", btn=" + btn +
                '}';
    }
}
