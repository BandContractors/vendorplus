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
public class Timesheet_log implements Serializable {

    private static final long serialVersionUID = 1L;
    private long timesheet_log_id;
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
    private String submission_by;
    private Date last_edit_date;
    private String last_edit_by;
    private String action;

    /**
     * @return the timesheet_log_id
     */
    public long getTimesheet_log_id() {
        return timesheet_log_id;
    }

    /**
     * @param timesheet_log_id the timesheet_log_id to set
     */
    public void setTimesheet_log_id(long timesheet_log_id) {
        this.timesheet_log_id = timesheet_log_id;
    }

    /**
     * @return the timesheet_id
     */
    public long getTimesheet_id() {
        return timesheet_id;
    }

    /**
     * @param timesheet_id the timesheet_id to set
     */
    public void setTimesheet_id(long timesheet_id) {
        this.timesheet_id = timesheet_id;
    }

    /**
     * @return the activity_status
     */
    public String getActivity_status() {
        return activity_status;
    }

    /**
     * @param activity_status the activity_status to set
     */
    public void setActivity_status(String activity_status) {
        this.activity_status = activity_status;
    }

    /**
     * @return the transactor_id
     */
    public long getTransactor_id() {
        return transactor_id;
    }

    /**
     * @param transactor_id the transactor_id to set
     */
    public void setTransactor_id(long transactor_id) {
        this.transactor_id = transactor_id;
    }

    /**
     * @return the mode_activity_id
     */
    public int getMode_activity_id() {
        return mode_activity_id;
    }

    /**
     * @param mode_activity_id the mode_activity_id to set
     */
    public void setMode_activity_id(int mode_activity_id) {
        this.mode_activity_id = mode_activity_id;
    }

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
     * @return the category_activity_id
     */
    public int getCategory_activity_id() {
        return category_activity_id;
    }

    /**
     * @param category_activity_id the category_activity_id to set
     */
    public void setCategory_activity_id(int category_activity_id) {
        this.category_activity_id = category_activity_id;
    }

    /**
     * @return the subcategory_activity_id
     */
    public int getSubcategory_activity_id() {
        return subcategory_activity_id;
    }

    /**
     * @param subcategory_activity_id the subcategory_activity_id to set
     */
    public void setSubcategory_activity_id(int subcategory_activity_id) {
        this.subcategory_activity_id = subcategory_activity_id;
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
     * @return the activity_name
     */
    public String getActivity_name() {
        return activity_name;
    }

    /**
     * @param activity_name the activity_name to set
     */
    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    /**
     * @return the activity_date
     */
    public Date getActivity_date() {
        return activity_date;
    }

    /**
     * @param activity_date the activity_date to set
     */
    public void setActivity_date(Date activity_date) {
        this.activity_date = activity_date;
    }

    /**
     * @return the submission_date
     */
    public Date getSubmission_date() {
        return submission_date;
    }

    /**
     * @param submission_date the submission_date to set
     */
    public void setSubmission_date(Date submission_date) {
        this.submission_date = submission_date;
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

    /**
     * @return the submission_by
     */
    public String getSubmission_by() {
        return submission_by;
    }

    /**
     * @param submission_by the submission_by to set
     */
    public void setSubmission_by(String submission_by) {
        this.submission_by = submission_by;
    }

    /**
     * @return the last_edit_date
     */
    public Date getLast_edit_date() {
        return last_edit_date;
    }

    /**
     * @param last_edit_date the last_edit_date to set
     */
    public void setLast_edit_date(Date last_edit_date) {
        this.last_edit_date = last_edit_date;
    }

    /**
     * @return the last_edit_by
     */
    public String getLast_edit_by() {
        return last_edit_by;
    }

    /**
     * @param last_edit_by the last_edit_by to set
     */
    public void setLast_edit_by(String last_edit_by) {
        this.last_edit_by = last_edit_by;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }
}
