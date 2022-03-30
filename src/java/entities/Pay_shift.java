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
public class Pay_shift implements Serializable {

    private static final long serialVersionUID = 1L;
    private long pay_shift_id;
    private long pay_id;
    private int shift_id;
    private int pay_type_id;

    /**
     * @return the pay_shift_id
     */
    public long getPay_shift_id() {
        return pay_shift_id;
    }

    /**
     * @param pay_shift_id the pay_shift_id to set
     */
    public void setPay_shift_id(long pay_shift_id) {
        this.pay_shift_id = pay_shift_id;
    }

    /**
     * @return the pay_id
     */
    public long getPay_id() {
        return pay_id;
    }

    /**
     * @param pay_id the pay_id to set
     */
    public void setPay_id(long pay_id) {
        this.pay_id = pay_id;
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
     * @return the pay_type_id
     */
    public int getPay_type_id() {
        return pay_type_id;
    }

    /**
     * @param pay_type_id the pay_type_id to set
     */
    public void setPay_type_id(int pay_type_id) {
        this.pay_type_id = pay_type_id;
    }
}
