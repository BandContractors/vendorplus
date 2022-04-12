package beans;

import connections.DBConnection;
import entities.Item;
import entities.Project;

import entities.Timesheet;
import entities.Transactor;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author kolynz
 */
@ManagedBean
@SessionScoped
public class TimesheetBean implements Serializable {

    static Logger LOGGER = Logger.getLogger(TimesheetBean.class.getName());
    private Transactor filterTransactor;
    private String filterActivityStatus = "";
    private int filterSubCategoryActivityId = 0;
    private String[] filterSubCategoryActivityIds = null;
    private int filterCategoryActivityId = 0;
    private String[] filterCategoryActivityIds = null;
    private long filterProjectId = 0;
    private String filterTimeTaken = "";
    private int filterModeActivityId = 0;
    private String filterActivityName = "";
    private String filterActivityDate = "";
    private String filterSubmissionDate = "";
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setTimesheetFromResultset(Timesheet aTimesheet, ResultSet aResultSet) {

        try {
            try {
                aTimesheet.setTimesheet_id(aResultSet.getLong("timesheet_id"));
            } catch (Exception e) {
                aTimesheet.setTimesheet_id(0);
            }
            try {
                aTimesheet.setStaff_id(aResultSet.getInt("staff_id"));
            } catch (Exception e) {
                aTimesheet.setStaff_id(0);
            }
            try {
                aTimesheet.setCategory_activity_id(aResultSet.getInt("category_activity_id"));
            } catch (Exception e) {
                aTimesheet.setCategory_activity_id(0);
            }
            try {
                aTimesheet.setSubcategory_activity_id(aResultSet.getInt("subcategory_activity_id"));
            } catch (Exception e) {
                aTimesheet.setSubcategory_activity_id(0);
            }
            try {
                aTimesheet.setActivity_date(new java.sql.Date(aResultSet.getDate("activity_date").getTime()));
            } catch (Exception e) {
                aTimesheet.setActivity_date(null);
            }
            try {
                aTimesheet.setSubmission_date(new java.sql.Date(aResultSet.getTimestamp("activity_date").getTime()));
            } catch (Exception e) {
                aTimesheet.setSubmission_date(null);
            }
            try {
                aTimesheet.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (Exception e) {
                aTimesheet.setTransactor_id(0);
            }
            try {
                String unit_of_time = aResultSet.getString("Unit_of_time");
                if (null == unit_of_time) {
                    aTimesheet.setUnit_of_time("");
                } else {
                    aTimesheet.setUnit_of_time(unit_of_time);
                }
            } catch (Exception e) {
                aTimesheet.setUnit_of_time("");
            }
            try {
                aTimesheet.setSubcategory_activity_id(aResultSet.getInt("subcategory_activity_id"));
            } catch (Exception e) {
                aTimesheet.setSubcategory_activity_id(0);
            }
            try {
                String activity_name = aResultSet.getString("activity_name");
                if (null == activity_name) {
                    aTimesheet.setActivity_name("");
                } else {
                    aTimesheet.setActivity_name(activity_name);
                }
            } catch (Exception e) {
                aTimesheet.setActivity_name("");
            }
            try {
                aTimesheet.setProject_id(aResultSet.getInt("project_id"));
            } catch (Exception e) {
                aTimesheet.setProject_id(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertTimesheet(Timesheet aTimesheet) {
        int InsertedId = 0;
        String sql = "INSERT INTO timesheet (activity_status,transactor_id,mode_activity_id ,staff_id,category_activity_id, time_taken,submission_date,activity_name,activity_date,unit_of_time,subcategory_activity_id,project_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aTimesheet.getActivity_status());
            ps.setLong(2, aTimesheet.getTransactor_id());
            ps.setInt(3, aTimesheet.getMode_activity_id());
            ps.setInt(4, aTimesheet.getStaff_id());
            ps.setInt(5, aTimesheet.getCategory_activity_id());
            ps.setDouble(6, aTimesheet.getTime_taken());
            ps.setTimestamp(7, new java.sql.Timestamp(aTimesheet.getSubmission_date().getTime()));
            ps.setString(8, aTimesheet.getActivity_name());
            ps.setDate(9, new java.sql.Date(aTimesheet.getActivity_date().getTime()));
            ps.setString(10, aTimesheet.getUnit_of_time());
            ps.setInt(11, aTimesheet.getSubcategory_activity_id());
            ps.setLong(12, aTimesheet.getProject_id());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                InsertedId = rs.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return InsertedId;
    }

    public int updateTimesheet(Timesheet aTimesheet) {
        int InsertedId = 0;
        String sql = "UPDATE timesheet SET activity_status=?,transactor_id=?,mode_activity_id=? ,staff_id=?,category_activity_id=?, time_taken=?,submission_date=?,activity_name=?,activity_date=?,unit_of_time=?,subcategory_activity_id=?,project_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTimesheet.getActivity_status());
            ps.setLong(2, aTimesheet.getTransactor_id());
            ps.setInt(3, aTimesheet.getMode_activity_id());
            ps.setInt(4, aTimesheet.getStaff_id());
            ps.setInt(5, aTimesheet.getCategory_activity_id());
            ps.setDouble(6, aTimesheet.getTime_taken());
            //ps.setDate(7, aTimesheet.getSubmission_date());
            ps.setString(8, aTimesheet.getActivity_name());
            //ps.setDate(9, aTimesheet.getActivity_date());
            ps.setString(10, aTimesheet.getUnit_of_time());
            ps.setInt(11, aTimesheet.getSubcategory_activity_id());
            ps.setLong(12, aTimesheet.getProject_id());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                InsertedId = rs.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return InsertedId;
    }

    public int deleteTimesheet(Timesheet aTimesheet) {
        int IsDeleted = 0;
        String sql = "DELETE FROM timesheet WHERE timesheet_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTimesheet.getTimesheet_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public Timesheet getTimesheet(long aTimesheet_id) {
        String sql = "SELECT * FROM timesheet WHERE timesheet=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTimesheet_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Timesheet obj = new Timesheet();
                this.setTimesheetFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Timesheet> getTimesheetAll() {
        String sql = "SELECT * FROM timesheet";
        ResultSet rs;
        List<Timesheet> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Timesheet obj = new Timesheet();
                this.setTimesheetFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    /**
     * @return the filterTransactor
     */
    public Transactor getFilterTransactor() {
        return filterTransactor;
    }

    /**
     * @param filterTransactor the filterTransactor to set
     */
    public void setFilterTransactor(Transactor filterTransactor) {
        this.filterTransactor = filterTransactor;
    }

    /**
     * @return the filterActivityStatus
     */
    public String getFilterActivityStatus() {
        return filterActivityStatus;
    }

    /**
     * @param filterActivityStatus the filterActivityStatus to set
     */
    public void setFilterActivityStatus(String filterActivityStatus) {
        this.filterActivityStatus = filterActivityStatus;
    }

    /**
     * @return the filterSubCategoryActivityId
     */
    public int getFilterSubCategoryActivityId() {
        return filterSubCategoryActivityId;
    }

    /**
     * @param filterSubCategoryActivityId the filterSubCategoryActivityId to set
     */
    public void setFilterSubCategoryActivityId(int filterSubCategoryActivityId) {
        this.filterSubCategoryActivityId = filterSubCategoryActivityId;
    }

    /**
     * @return the filterSubCategoryActivityIds
     */
    public String[] getFilterSubCategoryActivityIds() {
        return filterSubCategoryActivityIds;
    }

    /**
     * @param filterSubCategoryActivityIds the filterSubCategoryActivityIds to
     * set
     */
    public void setFilterSubCategoryActivityIds(String[] filterSubCategoryActivityIds) {
        this.filterSubCategoryActivityIds = filterSubCategoryActivityIds;
    }

    /**
     * @return the filterCategoryActivityId
     */
    public int getFilterCategoryActivityId() {
        return filterCategoryActivityId;
    }

    /**
     * @param filterCategoryActivityId the filterCategoryActivityId to set
     */
    public void setFilterCategoryActivityId(int filterCategoryActivityId) {
        this.filterCategoryActivityId = filterCategoryActivityId;
    }

    /**
     * @return the filterCategoryActivityIds
     */
    public String[] getFilterCategoryActivityIds() {
        return filterCategoryActivityIds;
    }

    /**
     * @param filterCategoryActivityIds the filterCategoryActivityIds to set
     */
    public void setFilterCategoryActivityIds(String[] filterCategoryActivityIds) {
        this.filterCategoryActivityIds = filterCategoryActivityIds;
    }

    /**
     * @return the filterProjectId
     */
    public long getFilterProjectId() {
        return filterProjectId;
    }

    /**
     * @param filterProjectId the filterProjectId to set
     */
    public void setFilterProjectId(long filterProjectId) {
        this.filterProjectId = filterProjectId;
    }

    /**
     * @return the filterTimeTaken
     */
    public String getFilterTimeTaken() {
        return filterTimeTaken;
    }

    /**
     * @param filterTimeTaken the filterTimeTaken to set
     */
    public void setFilterTimeTaken(String filterTimeTaken) {
        this.filterTimeTaken = filterTimeTaken;
    }

    /**
     * @return the filterModeActivityId
     */
    public int getFilterModeActivityId() {
        return filterModeActivityId;
    }

    /**
     * @param filterModeActivityId the filterModeActivityId to set
     */
    public void setFilterModeActivityId(int filterModeActivityId) {
        this.filterModeActivityId = filterModeActivityId;
    }

    /**
     * @return the filterActivityName
     */
    public String getFilterActivityName() {
        return filterActivityName;
    }

    /**
     * @param filterActivityName the filterActivityName to set
     */
    public void setFilterActivityName(String filterActivityName) {
        this.filterActivityName = filterActivityName;
    }

    /**
     * @return the filterActivityDate
     */
    public String getFilterActivityDate() {
        return filterActivityDate;
    }

    /**
     * @param filterActivityDate the filterActivityDate to set
     */
    public void setFilterActivityDate(String filterActivityDate) {
        this.filterActivityDate = filterActivityDate;
    }

    /**
     * @return the filterSubmissionDate
     */
    public String getFilterSubmissionDate() {
        return filterSubmissionDate;
    }

    /**
     * @param filterSubmissionDate the filterSubmissionDate to set
     */
    public void setFilterSubmissionDate(String filterSubmissionDate) {
        this.filterSubmissionDate = filterSubmissionDate;
    }

    /**
     * @return the menuItemBean
     */
    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    /**
     * @param menuItemBean the menuItemBean to set
     */
    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }

}
