package entities;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Timesheet_summary_last_seven_days implements Serializable {

    private static final long serialVersionUID = 1L;
    private int staff_id;
    private double day1;
    private double day2;
    private double day3;
    private double day4;
    private double day5;
    private double day6;
    private double day7;
    private double total;

    /**
     * @return the staff_id
     */
    public int getStaff_id() {
        return staff_id;
    }

    /**
     * @param staff_id the staff_id to set
     */
    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    /**
     * @return the day1
     */
    public double getDay1() {
        return day1;
    }

    /**
     * @param day1 the day1 to set
     */
    public void setDay1(double day1) {
        this.day1 = day1;
    }

    /**
     * @return the day2
     */
    public double getDay2() {
        return day2;
    }

    /**
     * @param day2 the day2 to set
     */
    public void setDay2(double day2) {
        this.day2 = day2;
    }

    /**
     * @return the day3
     */
    public double getDay3() {
        return day3;
    }

    /**
     * @param day3 the day3 to set
     */
    public void setDay3(double day3) {
        this.day3 = day3;
    }

    /**
     * @return the day4
     */
    public double getDay4() {
        return day4;
    }

    /**
     * @param day4 the day4 to set
     */
    public void setDay4(double day4) {
        this.day4 = day4;
    }

    /**
     * @return the day5
     */
    public double getDay5() {
        return day5;
    }

    /**
     * @param day5 the day5 to set
     */
    public void setDay5(double day5) {
        this.day5 = day5;
    }

    /**
     * @return the day6
     */
    public double getDay6() {
        return day6;
    }

    /**
     * @param day6 the day6 to set
     */
    public void setDay6(double day6) {
        this.day6 = day6;
    }

    /**
     * @return the day7
     */
    public double getDay7() {
        return day7;
    }

    /**
     * @param day7 the day7 to set
     */
    public void setDay7(double day7) {
        this.day7 = day7;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }
}
