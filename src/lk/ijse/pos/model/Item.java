package lk.ijse.pos.model;

public class Item {
    private String code;
    private double unitPrice;
    private int qtyOnHand;
    private String desc;

    public Item() {
    }

    public Item(String code, double unitPrice, int qtyOnHand, String desc) {
        this.code = code;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", desc='" + desc + '\'' +
                '}';
    }
}
