package lk.ijse.pos.views.tm;

public class CartTM {
    private String itemCode;
    private String desc;
    private int qty;
    private double unitPrice;
    private double total;

    public CartTM() {
    }

    public CartTM(String itemCode, String desc, int qty, double unitPrice, double total) {
        this.itemCode = itemCode;
        this.desc = desc;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", desc='" + desc + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
