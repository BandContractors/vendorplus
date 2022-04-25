package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kolynz
 */
@ManagedBean
@SessionScoped
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 1L;
    private long timesheet_id;
    private String activity_status;
    private long transactor_id;
    private int mode_activity_id;
    private int staff_id;
    private int category_activity_id;
    private int subcategory_activity_id;
    private double time_taken;
    private String activity_name;
    private Date activity_date;
    private Date submission_date;
    private String unit_of_time;
    private long project_id;
    private Transactor transactor;

    public long getTimesheet_id() {
        return timesheet_id;
    }

    public void setTimesheet_id(long timesheet_id) {
        this.timesheet_id = timesheet_id;
    }

    public String getActivity_status() {
        return activity_status;
    }

    public void setActivity_status(String activity_status) {
        this.activity_status = activity_status;
    }

    public long getTransactor_id() {
        return transactor_id;
    }

    public void setTransactor_id(long transactor_id) {
        this.transactor_id = transactor_id;
    }

    public int getMode_activity_id() {
        return mode_activity_id;
    }

    public void setMode_activity_id(int mode_activity_id) {
        this.mode_activity_id = mode_activity_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getCategory_activity_id() {
        return category_activity_id;
    }

    public void setCategory_activity_id(int category_activity_id) {
        this.category_activity_id = category_activity_id;
    }

    public int getSubcategory_activity_id() {
        return subcategory_activity_id;
    }

    public void setSubcategory_activity_id(int subcategory_activity_id) {
        this.subcategory_activity_id = subcategory_activity_id;
    }

    public double getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(double time_taken) {
        this.time_taken = time_taken;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public Date getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(Date activity_date) {
        this.activity_date = activity_date;
    }

    public Date getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(Date submission_date) {
        this.submission_date = submission_date;
    }

    public String getUnit_of_time() {
        return unit_of_time;
    }

    public void setUnit_of_time(String unit_of_time) {
        this.unit_of_time = unit_of_time;
    }

    /**
     * @return the project_id
     */
    public long getProject_id() {
        return project_id;
    }

    /**
     * @param project_id the project_id to set
     */
    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    /**
     * @return the transactor
     */
    public Transactor getTransactor() {
        return transactor;
    }

    /**
     * @param transactor the transactor to set
     */
    public void setTransactor(Transactor transactor) {
        this.transactor = transactor;
    }
}
