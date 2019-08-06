package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Trans_number_control implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int trans_number_control_id;
    private int trans_type_id;
    private int year_num;
    private int month_num;
    private int day_num;
    private int day_count;

    /**
     * @return the year_num
     */
    public int getYear_num() {
        return year_num;
    }

    /**
     * @param year_num the year_num to set
     */
    public void setYear_num(int year_num) {
        this.year_num = year_num;
    }

    /**
     * @return the month_num
     */
    public int getMonth_num() {
        return month_num;
    }

    /**
     * @param month_num the month_num to set
     */
    public void setMonth_num(int month_num) {
        this.month_num = month_num;
    }

    /**
     * @return the day_num
     */
    public int getDay_num() {
        return day_num;
    }

    /**
     * @param day_num the day_num to set
     */
    public void setDay_num(int day_num) {
        this.day_num = day_num;
    }

    /**
     * @return the day_count
     */
    public int getDay_count() {
        return day_count;
    }

    /**
     * @param day_count the day_count to set
     */
    public void setDay_count(int day_count) {
        this.day_count = day_count;
    }

    /**
     * @return the trans_number_control_id
     */
    public int getTrans_number_control_id() {
        return trans_number_control_id;
    }

    /**
     * @param trans_number_control_id the trans_number_control_id to set
     */
    public void setTrans_number_control_id(int trans_number_control_id) {
        this.trans_number_control_id = trans_number_control_id;
    }

    /**
     * @return the trans_type_id
     */
    public int getTrans_type_id() {
        return trans_type_id;
    }

    /**
     * @param trans_type_id the trans_type_id to set
     */
    public void setTrans_type_id(int trans_type_id) {
        this.trans_type_id = trans_type_id;
    }
}
