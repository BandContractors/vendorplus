package entities;


import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emmanuelmuwonge
 */
@ManagedBean
@SessionScoped
public class Transaction_shift implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_shift_id;
    private long transaction_id;
    private int shift_id;
    private int transaction_type_id;

    /**
     * @return the transaction_shift_id
     */
    public long getTransaction_shift_id() {
        return transaction_shift_id;
    }

    /**
     * @param transaction_shift_id the transaction_shift_id to set
     */
    public void setTransaction_shift_id(long transaction_shift_id) {
        this.transaction_shift_id = transaction_shift_id;
    }

    /**
     * @return the transaction_id
     */
    public long getTransaction_id() {
        return transaction_id;
    }

    /**
     * @param transaction_id the transaction_id to set
     */
    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    /**
     * @return the shift_id
     */
    public int getShift_id() {
        return shift_id;
    }

    /**
     * @param shift_id the shift_id to set
     */
    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    /**
     * @return the transaction_type_id
     */
    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    /**
     * @param transaction_type_id the transaction_type_id to set
     */
    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }
}
