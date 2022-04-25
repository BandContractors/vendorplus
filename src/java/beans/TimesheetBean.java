package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Staff;
import entities.Timesheet;
import entities.Transactor;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/**
 *
 * @author kolynz
 */
@ManagedBean
@SessionScoped
public class TimesheetBean implements Serializable {

    static Logger LOGGER = Logger.getLogger(TimesheetBean.class.getName());

    private List<Timesheet> Timesheets;
    private List<Timesheet> TimesheetsSummary__activitystatus;
    private List<Timesheet> TimesheetsSummary_subcategory_activity;
    private List<Timesheet> TimesheetsSummary_category_activity;
    private List<Timesheet> TimesheetsSummary_project;
    private List<Timesheet> timesheetsSummary_staff;

    private String ActionMessage = null;
    private Transactor filterTransactor;
    private String filterActivityStatus = "";
    private int filterSubCategoryActivityId = 0;
    private String[] filterSubCategoryActivityIds = null;
    private int filterCategoryActivityId = 0;
    private String[] filterCategoryActivityIds = null;
    private long filterProject_id = 0;
    private String filterUnit_Of_Time = "";
    private double filterTime_Taken = 0;
    private int filterModeActivityId = 0;
    private String filterActivityName = "";
    private Date filterActivityDate;
    private Staff selectStaff;
    private String filterStaff;
    private int filterStaffId;
    private String filterProject_name;
    private Transactor selectedTransactor;
    private Transactor selectedStaff;

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
                aTimesheet.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (Exception e) {
                aTimesheet.setTransactor_id(0);
            }
            try {
                aTimesheet.setMode_activity_id(aResultSet.getInt("mode_activity_id"));
            } catch (Exception e) {
                aTimesheet.setMode_activity_id(0);
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
                aTimesheet.setTime_taken(aResultSet.getDouble("time_taken"));
            } catch (Exception e) {
                aTimesheet.setTime_taken(0);
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
                aTimesheet.setActivity_date(new java.sql.Date(aResultSet.getDate("activity_date").getTime()));
            } catch (Exception e) {
                aTimesheet.setActivity_date(null);
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

    public void saveTimesheet(Timesheet aTimesheet) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg;
        String sql = null;
        try {
            try {
                BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
            } catch (Exception e) {
            }
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            if (aTimesheet.getTimesheet_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getTimesheet_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aTimesheet.getTimesheet_id() == 0) {
                    saved = this.insertTimesheet(aTimesheet);
                } else if (aTimesheet.getTimesheet_id() > 0) {
                    saved = this.updateTimesheet(aTimesheet);
                }
                if (saved > 0) {
                    msg = " Timesheet Saved Successfully";
                    this.clearTimesheet(aTimesheet);
                } else {
                    msg = "Timesheet NOT Saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displayTimesheet(Timesheet TimesheetFrom, Timesheet TimesheetTo) {
        try {
            this.clearTimesheet(TimesheetTo);
            TimesheetTo.setTimesheet_id(TimesheetFrom.getTimesheet_id());
//            TimesheetTo.setTransactor(TimesheetFrom.getTransactor());
            TimesheetTo.setTransactor_id(TimesheetFrom.getTransactor_id());
            TimesheetTo.setActivity_date(TimesheetFrom.getActivity_date());
            TimesheetTo.setActivity_name(TimesheetFrom.getActivity_name());
            TimesheetTo.setActivity_status(TimesheetFrom.getActivity_status());
            TimesheetTo.setCategory_activity_id(TimesheetFrom.getCategory_activity_id());
            TimesheetTo.setMode_activity_id(TimesheetFrom.getMode_activity_id());
            TimesheetTo.setProject_id(TimesheetFrom.getProject_id());
            TimesheetTo.setStaff_id(TimesheetFrom.getStaff_id());
            TimesheetTo.setSubcategory_activity_id(TimesheetFrom.getSubcategory_activity_id());
            TimesheetTo.setSubmission_date(TimesheetFrom.getSubmission_date());
            TimesheetTo.setTime_taken(TimesheetFrom.getTime_taken());
            TimesheetTo.setUnit_of_time(TimesheetFrom.getUnit_of_time());

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
            ps.setLong(2, aTimesheet.getTransactor().getTransactorId());
//            ps.setLong(2, aTimesheet.getTransactor_id());
            ps.setInt(3, aTimesheet.getMode_activity_id());
            ps.setInt(4, aTimesheet.getStaff_id());
            ps.setInt(5, aTimesheet.getCategory_activity_id());
            ps.setDouble(6, aTimesheet.getTime_taken());
            ps.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
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
        int IsUpdated = 0;
        String sql = "UPDATE timesheet SET activity_status=?,transactor_id=?,mode_activity_id=? ,staff_id=?,category_activity_id=?,"
                + " time_taken=?,activity_name=?,activity_date=?,unit_of_time=?,subcategory_activity_id=?,project_id=? WHERE timesheet_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, aTimesheet.getActivity_status());
            ps.setLong(2, aTimesheet.getTransactor().getTransactorId());
//            ps.setLong(2, aTimesheet.getTransactor_id());
            ps.setInt(3, aTimesheet.getMode_activity_id());
            ps.setInt(4, aTimesheet.getStaff_id());
            ps.setInt(5, aTimesheet.getCategory_activity_id());
            ps.setDouble(6, aTimesheet.getTime_taken());
            ps.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
            ps.setString(8, aTimesheet.getActivity_name());
            ps.setDate(9, new java.sql.Date(aTimesheet.getActivity_date().getTime()));
            ps.setString(10, aTimesheet.getUnit_of_time());
            ps.setInt(11, aTimesheet.getSubcategory_activity_id());
            ps.setLong(13, aTimesheet.getProject_id());
            ps.setLong(14, aTimesheet.getTimesheet_id());

            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
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

    public void clearTimesheet(Timesheet aTimesheet) {
        try {
            if (aTimesheet != null) {
                aTimesheet.setTimesheet_id(0);
                aTimesheet.setActivity_name("");
                aTimesheet.setActivity_status("");
                aTimesheet.setProject_id(0);
                aTimesheet.setStaff_id(0);
                aTimesheet.setSubcategory_activity_id(0);
                aTimesheet.getUnit_of_time();
                aTimesheet.setTime_taken(0);
                aTimesheet.setUnit_of_time("");
                aTimesheet.setCategory_activity_id(0);
                aTimesheet.setTransactor_id(0);
                aTimesheet.setActivity_date(new Date());
                aTimesheet.setMode_activity_id(0);
                aTimesheet.setStaff_id(0);
                aTimesheet.setTransactor(null);

            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearFilter() {
        try {
            this.setFilterActivityDate(new Date());
            this.setFilterActivityName("");
            this.setFilterActivityStatus("");
            this.setFilterModeActivityId(0);
            this.setFilterProject_id(0);
            this.setFilterStaffId(0);
            this.setFilterSubCategoryActivityId(0);
            this.setFilterCategoryActivityId(0);
            this.setFilterCategoryActivityIds(null);
            this.setFilterUnit_Of_Time("");
            this.setFilterTime_Taken(0);
            this.setFilterTransactor(null);
            this.setFilterSubCategoryActivityIds(null);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getFilteredTimesheets() {
        String sql;
        sql = "SELECT * FROM timesheet where timesheet_id > 0";
        String wheresql = "";
        String ordersql = " ORDER BY activity_date DESC";
        try {
            if (this.filterTransactor != null) {
                wheresql = wheresql + " AND transactor_id=" + this.filterTransactor.getTransactorId();
            }
            if (this.filterActivityStatus.length() > 0) {
                wheresql = wheresql + " AND current_status='" + this.filterActivityStatus + "'";
            }
            if (this.filterSubCategoryActivityIds != null) {
                if (this.filterSubCategoryActivityIds.length > 0) {
                    //array to comma seperated string
                    String commaSepString = String.join(",", this.filterSubCategoryActivityIds);
                    wheresql = wheresql + " AND subcategory_activity_id IN (" + commaSepString + ")";
                }
            }
//           if (this.filterActivityDate!=0){
           
           
           
            if (this.filterCategoryActivityIds != null) {
                if (this.filterCategoryActivityIds.length > 0) {
                    //array to comma seperated string
                    String commaSepString = String.join(",", this.filterCategoryActivityIds);
                    wheresql = wheresql + " AND category_activity_id IN (" + commaSepString + ")";
                }
            }
            if (this.filterUnit_Of_Time.length() > 0) {
                wheresql = wheresql + "AND unit_of_time='" + this.filterUnit_Of_Time + "'";
            }
            if (this.filterProject_name.length() > 0) {
                wheresql = wheresql + "AND project='" + this.filterProject_id + "'";
            }
            if (this.filterUnit_Of_Time != null) {
                wheresql = wheresql + "AND unit_of_time='" + this.filterUnit_Of_Time + "'";
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

//     public void getFilteredTimesheetsSummary_CategoryActivity() {
//        String sql;
//        sql = "SELECT subcategory_activity_id, count(*) as numbers, sum(unit_of_time) as unit_of_time FROM timesheet where timesheet_id > 0";
//        String wheresql = "";
//        String groupbysum = " GROUP BY subcategory_activity_id";
//        if (this.filterTransactor != null) {
//            wheresql = wheresql + " AND transactor_id=" + this.filterTransactor.getTransactorId();
//        }
//        if (!this.filterActivityStatus.isEmpty()) {
//            wheresql = wheresql + " AND activity_status='" + this.filterActivityStatus + "'";
//        }
//        if (this.filterSubCategoryActivityIds != null) {
//            if (this.filterSubCategoryActivityIds.length > 0) {
//                String commaSepString = String.join(",", this.filterSubCategoryActivityIds);
//                wheresql = wheresql + " AND subcategory_activity_id IN (" + commaSepString + ")";
//            }
//        }
//        if (!this.filterActivityStatus.isEmpty()) {
//            wheresql = wheresql + " AND activity_status='" + this.filterActivityStatus + "'";
//        }
//          if (this.filterCategoryActivityIds!=null){
//          wheresql = wheresql + "AND category_activity'" + this.filterCategoryActivityId+"'";
//          
//          }
//            }  
//        catch (Exception e) {
//            LOGGER.log(Level.ERROR, e);
//        }
//    }
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
     * @return the filterProject_id
     */
    public long getFilterProject_id() {
        return filterProject_id;
    }

    /**
     * @param filterProject_id the filterProject_id to set
     */
    public void setFilterProject_id(long filterProject_id) {
        this.filterProject_id = filterProject_id;
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

    /**
     * @return the selectStaff
     */
    public Staff getSelectStaff() {
        return selectStaff;
    }

    /**
     * @param selectStaff the selectStaff to set
     */
    public void setSelectStaff(Staff selectStaff) {
        this.selectStaff = selectStaff;
    }

    /**
     * @return the selectedStaff
     */
    public Transactor getSelectedStaff() {
        return selectedStaff;
    }

    /**
     * @param selectedStaff the selectedStaff to set
     */
    public void setSelectedStaff(Transactor selectedStaff) {
        this.selectedStaff = selectedStaff;
    }

    /**
     * @return the selectedTransactor
     */
    public Transactor getSelectedTransactor() {
        return selectedTransactor;
    }

    /**
     * @param selectedTransactor the selectedTransactor to set
     */
    public void setSelectedTransactor(Transactor selectedTransactor) {
        this.selectedTransactor = selectedTransactor;
    }

    /**
     * @return the filterStaffId
     */
    public int getFilterStaffId() {
        return filterStaffId;
    }

    /**
     * @param filterStaffId the filterStaffId to set
     */
    public void setFilterStaffId(int filterStaffId) {
        this.filterStaffId = filterStaffId;
    }

    /**
     * @return the filterUnit_Of_Time
     */
    public String getFilterUnit_Of_Time() {
        return filterUnit_Of_Time;
    }

    /**
     * @param filterUnit_Of_Time the filterUnit_Of_Time to set
     */
    public void setFilterUnit_Of_Time(String filterUnit_Of_Time) {
        this.filterUnit_Of_Time = filterUnit_Of_Time;
    }

    /**
     * @return the filterTime_Taken
     */
    public double getFilterTime_Taken() {
        return filterTime_Taken;
    }

    /**
     * @param filterTime_Taken the filterTime_Taken to set
     */
    public void setFilterTime_Taken(double filterTime_Taken) {
        this.filterTime_Taken = filterTime_Taken;
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param ActionMessage the ActionMessage to set
     */
    public void setActionMessage(String ActionMessage) {
        this.ActionMessage = ActionMessage;
    }

    /**
     * @return the filterStaff
     */
    public String getFilterStaff() {
        return filterStaff;
    }

    /**
     * @param filterStaff the filterStaff to set
     */
    public void setFilterStaff(String filterStaff) {
        this.filterStaff = filterStaff;
    }

    /**
     * @return the Timesheets
     */
    public List<Timesheet> getTimesheets() {
        return Timesheets;
    }

    /**
     * @param Timesheets the Timesheets to set
     */
    public void setTimesheets(List<Timesheet> Timesheets) {
        this.Timesheets = Timesheets;
    }

    /**
     * @return the TimesheetsSummary__activitystatus
     */
    public List<Timesheet> getTimesheetsSummary__activitystatus() {
        return TimesheetsSummary__activitystatus;
    }

    /**
     * @param TimesheetsSummary__activitystatus the
     * TimesheetsSummary__activitystatus to set
     */
    public void setTimesheetsSummary__activitystatus(List<Timesheet> TimesheetsSummary__activitystatus) {
        this.TimesheetsSummary__activitystatus = TimesheetsSummary__activitystatus;
    }

    /**
     * @return the TimesheetsSummary_subcategory_activity
     */
    public List<Timesheet> getTimesheetsSummary_subcategory_activity() {
        return TimesheetsSummary_subcategory_activity;
    }

    /**
     * @param TimesheetsSummary_subcategory_activity the
     * TimesheetsSummary_subcategory_activity to set
     */
    public void setTimesheetsSummary_subcategory_activity(List<Timesheet> TimesheetsSummary_subcategory_activity) {
        this.TimesheetsSummary_subcategory_activity = TimesheetsSummary_subcategory_activity;
    }

    /**
     * @return the TimesheetsSummary_category_activity
     */
    public List<Timesheet> getTimesheetsSummary_category_activity() {
        return TimesheetsSummary_category_activity;
    }

    /**
     * @param TimesheetsSummary_category_activity the
     * TimesheetsSummary_category_activity to set
     */
    public void setTimesheetsSummary_category_activity(List<Timesheet> TimesheetsSummary_category_activity) {
        this.TimesheetsSummary_category_activity = TimesheetsSummary_category_activity;
    }

    /**
     * @return the TimesheetsSummary_project
     */
    public List<Timesheet> getTimesheetsSummary_project() {
        return TimesheetsSummary_project;
    }

    /**
     * @param TimesheetsSummary_project the TimesheetsSummary_project to set
     */
    public void setTimesheetsSummary_project(List<Timesheet> TimesheetsSummary_project) {
        this.TimesheetsSummary_project = TimesheetsSummary_project;
    }

    /**
     * @return the timesheetsSummary_staff
     */
    public List<Timesheet> getTimesheetsSummary_staff() {
        return timesheetsSummary_staff;
    }

    /**
     * @param timesheetsSummary_staff the timesheetsSummary_staff to set
     */
    public void setTimesheetsSummary_staff(List<Timesheet> timesheetsSummary_staff) {
        this.timesheetsSummary_staff = timesheetsSummary_staff;
    }

    /**
     * @return the filterProject_name
     */
    public String getFilterProject_name() {
        return filterProject_name;
    }

    /**
     * @param filterProject_name the filterProject_name to set
     */
    public void setFilterProject_name(String filterProject_name) {
        this.filterProject_name = filterProject_name;
    }

    /**
     * @return the filterActivityDate
     */
    public Date getFilterActivityDate() {
        return filterActivityDate;
    }

    /**
     * @param filterActivityDate the filterActivityDate to set
     */
    public void setFilterActivityDate(Date filterActivityDate) {
        this.filterActivityDate = filterActivityDate;
    }

}
