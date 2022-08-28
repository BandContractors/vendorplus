package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Transaction_item_unit implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_item_id;
    private int unit_id;
    private double base_unit_qty;

    /**
     * @return the transaction_item_id
     */
    public long getTransaction_item_id() {
        return transaction_item_id;
    }

    /**
     * @param transaction_item_id the transaction_item_id to set
     */
    public void setTransaction_item_id(long transaction_item_id) {
        this.transaction_item_id = transaction_item_id;
    }

    /**
     * @return the unit_id
     */
    public int getUnit_id() {
        return unit_id;
    }

    /**
     * @param unit_id the unit_id to set
     */
    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    /**
     * @return the base_unit_qty
     */
    public double getBase_unit_qty() {
        return base_unit_qty;
    }

    /**
     * @param base_unit_qty the base_unit_qty to set
     */
    public void setBase_unit_qty(double base_unit_qty) {
        this.base_unit_qty = base_unit_qty;
    }
}
