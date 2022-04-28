package entities;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Timesheet_summary_ARH implements Serializable {

    private static final long serialVersionUID = 1L;
    private String first_name;
    private String second_name;
    private String third_name;
    //Average Reporting Hour
    private double arh;

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
     * @return the arh
     */
    public double getArh() {
        return arh;
    }

    /**
     * @param arh the arh to set
     */
    public void setArh(double arh) {
        this.arh = arh;
    }
}
