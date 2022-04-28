package entities;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Timesheet_summary_total_time implements Serializable {

    private static final long serialVersionUID = 1L;
    private String first_name;
    private String second_name;
    private String third_name;
    private double time_taken;
    private String unit_of_time;

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the second_name
     */
    public String getSecond_name() {
        return second_name;
    }

    /**
     * @param second_name the second_name to set
     */
    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    /**
     * @return the third_name
     */
    public String getThird_name() {
        return third_name;
    }

    /**
     * @param third_name the third_name to set
     */
    public void setThird_name(String third_name) {
        this.third_name = third_name;
    }

    /**
     * @return the time_taken
     */
    public double getTime_taken() {
        return time_taken;
    }

    /**
     * @param time_taken the time_taken to set
     */
    public void setTime_taken(double time_taken) {
        this.time_taken = time_taken;
    }

    /**
     * @return the unit_of_time
     */
    public String getUnit_of_time() {
        return unit_of_time;
    }

    /**
     * @param unit_of_time the unit_of_time to set
     */
    public void setUnit_of_time(String unit_of_time) {
        this.unit_of_time = unit_of_time;
    }
}
