package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Timesheet;
import entities.Timesheet_summary_ARH;
import entities.Timesheet_summary_total_time;
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

    private List<Timesheet> filteredTimesheetList;
    private List<Timesheet_summary_total_time> timesheetSummary_totalTime;
    //Average Reporting Hour
    private List<Timesheet_summary_ARH> timesheetSummary_ARH;
    private String ActionMessage = null;
    private int filterCategoryActivityId = 0;
    private Date filterFromActivityDate;
    private Date filterToActivityDate;
    private int filterStaffId = 0;

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
                aTimesheet.setActivity_status(aResultSet.getString("activity_status"));
            } catch (Exception e) {
                aTimesheet.setActivity_status("");
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

            if (aTimesheet.getTimesheet_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "134", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getTimesheet_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "134", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getStaff_id() == 0) {
                msg = "Staff Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getActivity_name().length() == 0) {
                msg = "Activity Name Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getTime_taken() == 0) {
                msg = "Time Quantity Cannot be Zero";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getUnit_of_time().length() == 0) {
                msg = "Unit of Time Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getActivity_status().length() == 0) {
                msg = "Activity Status Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getMode_activity_id() == 0) {
                msg = "Mode Activity Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getCategory_activity_id() == 0) {
                msg = "Category Activity Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getSubcategory_activity_id() == 0) {
                msg = "Subcategory Activity Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aTimesheet.getActivity_date() == null) {
                msg = "Activity Date Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aTimesheet.getTimesheet_id() == 0) {
                    saved = this.insertTimesheet(aTimesheet);
                } else if (aTimesheet.getTimesheet_id() > 0) {
                    saved = this.updateTimesheet(aTimesheet);
                }
                if (saved > 0) {
                    msg = " Time Sheet Saved Successfully";
                    this.clearTimesheet(aTimesheet);
                    this.getFilteredTimesheets();
                } else {
                    msg = "Time Sheet NOT Saved";
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
            TimesheetTo.setTransactor_id(TimesheetFrom.getTransactor_id());
            TimesheetTo.setTransactor(new TransactorBean().findTransactor(TimesheetFrom.getTransactor_id()));
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
            if (aTimesheet.getTransactor() != null) {
                ps.setLong(2, aTimesheet.getTransactor().getTransactorId());
            } else {
                ps.setLong(2, 0);
            }
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
                + " time_taken=?,submission_date=?,activity_name=?,activity_date=?,unit_of_time=?,subcategory_activity_id=?,project_id=? WHERE timesheet_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, aTimesheet.getActivity_status());
            if (aTimesheet.getTransactor() != null) {
                ps.setLong(2, aTimesheet.getTransactor().getTransactorId());
            } else {
                ps.setLong(2, 0);
            }
            ps.setInt(3, aTimesheet.getMode_activity_id());
            ps.setInt(4, aTimesheet.getStaff_id());
            ps.setInt(5, aTimesheet.getCategory_activity_id());
            ps.setDouble(6, aTimesheet.getTime_taken());
            //ps.setDate(7, new java.sql.Date(aTimesheet.getSubmission_date().getTime()));
            ps.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
            ps.setString(8, aTimesheet.getActivity_name());
            ps.setDate(9, new java.sql.Date(aTimesheet.getActivity_date().getTime()));
            ps.setString(10, aTimesheet.getUnit_of_time());
            ps.setInt(11, aTimesheet.getSubcategory_activity_id());
            ps.setLong(12, aTimesheet.getProject_id());
            ps.setLong(13, aTimesheet.getTimesheet_id());

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
                try {
                    aTimesheet.setUnit_of_time(new Parameter_listBean().getParameter_listByContextNameMemory("TIME_SHEET", "TIME_UNIT").getParameter_value());
                } catch (Exception e) {
                    aTimesheet.setUnit_of_time("");
                }
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
            this.setFilterFromActivityDate(new Date());
            this.setFilterToActivityDate(new Date());
            this.setFilterCategoryActivityId(0);
            this.setFilterStaffId(0);
            this.getFilteredTimesheets();
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
            if (this.filterCategoryActivityId > 0) {
                wheresql = wheresql + " AND category_activity_id=" + this.filterCategoryActivityId;
            }
            if (this.getFilterStaffId() > 0) {
                wheresql = wheresql + " AND staff_id=" + this.getFilterStaffId();
            }
            if (this.getFilterFromActivityDate() != null && this.getFilterToActivityDate() != null) {
                //convert java.util date to sql date
                java.sql.Date from = new java.sql.Date(this.getFilterFromActivityDate().getTime());
                java.sql.Date to = new java.sql.Date(this.getFilterToActivityDate().getTime());

                wheresql = wheresql + " AND activity_date between '" + from + "' and '" + to + "'";
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        sql = sql + wheresql + ordersql;
        ResultSet rs;
        filteredTimesheetList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Timesheet aTimesheet = new Timesheet();
                this.setTimesheetFromResultset(aTimesheet, rs);
                getFilteredTimesheetList().add(aTimesheet);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        //summary
        this.getFilteredTimesheetSummary();
    }

    public void getFilteredTimesheetSummary() {
        try {
            //summarise status
            this.getFilteredTimesheetSummary_totalTime();
            this.getFilteredTimesheetSummary_ARH();
            //this.getFilteredTimesheetSummary_activityCategory();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getFilteredTimesheetSummary_totalTime() {
        String sql;
        sql = "SELECT s.first_name, s.second_name, s.third_name, sum(t.time_taken) as time_taken, t.unit_of_time from timesheet t inner join staff s on t.staff_id = s.staff_id  where t.timesheet_id > 0";
        String wheresql = "";
        String groupbysum = " GROUP BY t.unit_of_time, t.staff_id";
        String ordersql = " ORDER BY t.unit_of_time, time_taken DESC";
        try {
            if (this.filterCategoryActivityId > 0) {
                wheresql = wheresql + " AND category_activity_id=" + this.filterCategoryActivityId;
            }
            if (this.getFilterStaffId() > 0) {
                wheresql = wheresql + " AND t.staff_id=" + this.getFilterStaffId();
            }
            if (this.getFilterFromActivityDate() != null && this.getFilterToActivityDate() != null) {
                //convert java.util date to sql date
                java.sql.Date from = new java.sql.Date(this.getFilterFromActivityDate().getTime());
                java.sql.Date to = new java.sql.Date(this.getFilterToActivityDate().getTime());

                wheresql = wheresql + " AND activity_date between '" + from + "' and '" + to + "'";
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        sql = sql + wheresql + groupbysum + ordersql;
        ResultSet rs;
        this.timesheetSummary_totalTime = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Timesheet_summary_total_time summary = new Timesheet_summary_total_time();
                //this.setTimesheetFromResultset(aTimesheet, rs);
                try {
                    summary.setFirst_name(rs.getString("first_name"));
                } catch (Exception e) {
                    summary.setFirst_name("");
                }
                try {
                    summary.setSecond_name(rs.getString("second_name"));
                } catch (Exception e) {
                    summary.setSecond_name("");
                }
                try {
                    summary.setThird_name(rs.getString("third_name"));
                } catch (Exception e) {
                    summary.setThird_name("");
                }
                try {
                    summary.setTime_taken(rs.getDouble("time_taken"));
                } catch (Exception e) {
                    summary.setTime_taken(0);
                }
                try {
                    summary.setUnit_of_time(rs.getString("unit_of_time"));
                } catch (Exception e) {
                    summary.setUnit_of_time("");
                }
                this.getTimesheetSummary_totalTime().add(summary);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getFilteredTimesheetSummary_ARH() {
        String sql;
        sql = "SELECT s.first_name, s.second_name, s.third_name, round(avg(hour(t.submission_date)),0) as arh from timesheet t inner join staff s on t.staff_id = s.staff_id  where t.timesheet_id > 0";
        String wheresql = "";
        String groupbysum = " GROUP BY t.staff_id";
        String ordersql = " ORDER BY arh DESC";
        try {
            if (this.filterCategoryActivityId > 0) {
                wheresql = wheresql + " AND category_activity_id=" + this.filterCategoryActivityId;
            }
            if (this.getFilterStaffId() > 0) {
                wheresql = wheresql + " AND t.staff_id=" + this.getFilterStaffId();
            }
            if (this.getFilterFromActivityDate() != null && this.getFilterToActivityDate() != null) {
                //convert java.util date to sql date
                java.sql.Date from = new java.sql.Date(this.getFilterFromActivityDate().getTime());
                java.sql.Date to = new java.sql.Date(this.getFilterToActivityDate().getTime());

                wheresql = wheresql + " AND activity_date between '" + from + "' and '" + to + "'";
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        sql = sql + wheresql + groupbysum + ordersql;
        ResultSet rs;
        this.timesheetSummary_ARH = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Timesheet_summary_ARH summary = new Timesheet_summary_ARH();
                //this.setTimesheetFromResultset(aTimesheet, rs);
                try {
                    summary.setFirst_name(rs.getString("first_name"));
                } catch (Exception e) {
                    summary.setFirst_name("");
                }
                try {
                    summary.setSecond_name(rs.getString("second_name"));
                } catch (Exception e) {
                    summary.setSecond_name("");
                }
                try {
                    summary.setThird_name(rs.getString("third_name"));
                } catch (Exception e) {
                    summary.setThird_name("");
                }
                try {
                    summary.setArh(rs.getDouble("arh"));
                } catch (Exception e) {
                    summary.setArh(0);
                }
                this.getTimesheetSummary_ARH().add(summary);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
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
     * @return the filteredTimesheetList
     */
    public List<Timesheet> getFilteredTimesheetList() {
        return filteredTimesheetList;
    }

    /**
     * @param filteredTimesheetList the filteredTimesheetList to set
     */
    public void setFilteredTimesheetList(List<Timesheet> filteredTimesheetList) {
        this.filteredTimesheetList = filteredTimesheetList;
    }

    /**
     * @return the filterFromActivityDate
     */
    public Date getFilterFromActivityDate() {
        return filterFromActivityDate;
    }

    /**
     * @param filterFromActivityDate the filterFromActivityDate to set
     */
    public void setFilterFromActivityDate(Date filterFromActivityDate) {
        this.filterFromActivityDate = filterFromActivityDate;
    }

    /**
     * @return the filterToActivityDate
     */
    public Date getFilterToActivityDate() {
        return filterToActivityDate;
    }

    /**
     * @param filterToActivityDate the filterToActivityDate to set
     */
    public void setFilterToActivityDate(Date filterToActivityDate) {
        this.filterToActivityDate = filterToActivityDate;
    }

    /**
     * @return the timesheetSummary_totalTime
     */
    public List<Timesheet_summary_total_time> getTimesheetSummary_totalTime() {
        return timesheetSummary_totalTime;
    }

    /**
     * @param timesheetSummary_totalTime the timesheetSummary_totalTime to set
     */
    public void setTimesheetSummary_totalTime(List<Timesheet_summary_total_time> timesheetSummary_totalTime) {
        this.timesheetSummary_totalTime = timesheetSummary_totalTime;
    }

    /**
     * @return the timesheetSummary_ARH
     */
    public List<Timesheet_summary_ARH> getTimesheetSummary_ARH() {
        return timesheetSummary_ARH;
    }

    /**
     * @param timesheetSummary_ARH the timesheetSummary_ARH to set
     */
    public void setTimesheetSummary_ARH(List<Timesheet_summary_ARH> timesheetSummary_ARH) {
        this.timesheetSummary_ARH = timesheetSummary_ARH;
    }

}
