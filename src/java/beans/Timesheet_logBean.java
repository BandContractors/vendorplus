package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Timesheet_log;
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
public class Timesheet_logBean implements Serializable {

    static Logger LOGGER = Logger.getLogger(Timesheet_logBean.class.getName());

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setTimesheet_logFromResultset(Timesheet_log aTimesheet_log, ResultSet aResultSet) {

        try {
            try {
                aTimesheet_log.setTimesheet_log_id(aResultSet.getLong("timesheet_log_id"));
            } catch (Exception e) {
                aTimesheet_log.setTimesheet_log_id(0);
            }
            try {
                aTimesheet_log.setTimesheet_id(aResultSet.getLong("timesheet_id"));
            } catch (Exception e) {
                aTimesheet_log.setTimesheet_id(0);
            }
            try {
                aTimesheet_log.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (Exception e) {
                aTimesheet_log.setTransactor_id(0);
            }
            try {
                aTimesheet_log.setActivity_status(aResultSet.getString("activity_status"));
            } catch (Exception e) {
                aTimesheet_log.setActivity_status("");
            }
            try {
                aTimesheet_log.setMode_activity_id(aResultSet.getInt("mode_activity_id"));
            } catch (Exception e) {
                aTimesheet_log.setMode_activity_id(0);
            }
            try {
                aTimesheet_log.setStaff_id(aResultSet.getInt("staff_id"));
            } catch (Exception e) {
                aTimesheet_log.setStaff_id(0);
            }
            try {
                aTimesheet_log.setCategory_activity_id(aResultSet.getInt("category_activity_id"));
            } catch (Exception e) {
                aTimesheet_log.setCategory_activity_id(0);
            }
            try {
                aTimesheet_log.setSubcategory_activity_id(aResultSet.getInt("subcategory_activity_id"));
            } catch (Exception e) {
                aTimesheet_log.setSubcategory_activity_id(0);
            }
            try {
                aTimesheet_log.setTime_taken(aResultSet.getDouble("time_taken"));
            } catch (Exception e) {
                aTimesheet_log.setTime_taken(0);
            }
            try {
                String unit_of_time = aResultSet.getString("Unit_of_time");
                if (null == unit_of_time) {
                    aTimesheet_log.setUnit_of_time("");
                } else {
                    aTimesheet_log.setUnit_of_time(unit_of_time);
                }
            } catch (Exception e) {
                aTimesheet_log.setUnit_of_time("");
            }
            try {
                String activity_name = aResultSet.getString("activity_name");
                if (null == activity_name) {
                    aTimesheet_log.setActivity_name("");
                } else {
                    aTimesheet_log.setActivity_name(activity_name);
                }
            } catch (Exception e) {
                aTimesheet_log.setActivity_name("");
            }
            try {
                aTimesheet_log.setActivity_date(new java.sql.Date(aResultSet.getDate("activity_date").getTime()));
            } catch (Exception e) {
                aTimesheet_log.setActivity_date(null);
            }
            try {
                aTimesheet_log.setSubmission_date(new java.sql.Date(aResultSet.getDate("submission_date").getTime()));
            } catch (Exception e) {
                aTimesheet_log.setSubmission_date(null);
            }
            try {
                aTimesheet_log.setProject_id(aResultSet.getInt("project_id"));
            } catch (Exception e) {
                aTimesheet_log.setProject_id(0);
            }
            try {
                aTimesheet_log.setSubmission_by(aResultSet.getString("submission_by"));
            } catch (Exception e) {
                aTimesheet_log.setSubmission_by("");
            }
            try {
                aTimesheet_log.setLast_edit_date(new java.sql.Date(aResultSet.getDate("last_edit_date").getTime()));
            } catch (Exception e) {
                aTimesheet_log.setLast_edit_date(null);
            }
            try {
                aTimesheet_log.setLast_edit_by(aResultSet.getString("last_edit_by"));
            } catch (Exception e) {
                aTimesheet_log.setLast_edit_by("");
            }
            try {
                aTimesheet_log.setAction(aResultSet.getString("action"));
            } catch (Exception e) {
                aTimesheet_log.setAction("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertTimesheet_log(Timesheet_log aTimesheet_log) {
        int saved = 0;
        String sql = "INSERT INTO timesheet_log (timesheet_id,activity_status,transactor_id,mode_activity_id ,staff_id,category_activity_id, time_taken,"
                + "submission_date,activity_name,activity_date,unit_of_time,subcategory_activity_id,project_id,submission_by,last_edit_date,last_edit_by,action) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTimesheet_log.getTimesheet_id());
            ps.setString(2, aTimesheet_log.getActivity_status());
            ps.setLong(3, aTimesheet_log.getTransactor_id());
            ps.setInt(4, aTimesheet_log.getMode_activity_id());
            ps.setInt(5, aTimesheet_log.getStaff_id());
            ps.setInt(6, aTimesheet_log.getCategory_activity_id());
            ps.setDouble(7, aTimesheet_log.getTime_taken());
            ps.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
            ps.setString(9, aTimesheet_log.getActivity_name());
            ps.setDate(10, new java.sql.Date(aTimesheet_log.getActivity_date().getTime()));
            ps.setString(11, aTimesheet_log.getUnit_of_time());
            ps.setInt(12, aTimesheet_log.getSubcategory_activity_id());
            ps.setLong(13, aTimesheet_log.getProject_id());
            ps.setString(14, new GeneralUserSetting().getCurrentUser().getUserName());
            if (aTimesheet_log.getLast_edit_date() != null) {
                ps.setDate(15, new java.sql.Date(aTimesheet_log.getLast_edit_date().getTime()));
            } else {
                ps.setDate(15, null);
            }
            ps.setString(16, aTimesheet_log.getLast_edit_by());
            ps.setString(17, aTimesheet_log.getAction());
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int updateTimesheet_log(Timesheet_log aTimesheet_log) {
        int IsUpdated = 0;
        String sql = "UPDATE timesheet_log SET timesheet_id=?,activity_status=?,transactor_id=?,mode_activity_id=? ,staff_id=?,category_activity_id=?,"
                + " time_taken=?,submission_date=?,activity_name=?,activity_date=?,unit_of_time=?,subcategory_activity_id=?,"
                + "project_id=?,submission_by=?,last_edit_date=?,last_edit_by=?,action=? WHERE timesheet_log_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setLong(1, aTimesheet_log.getTimesheet_id());
            ps.setString(2, aTimesheet_log.getActivity_status());
            ps.setLong(3, aTimesheet_log.getTransactor_id());
            ps.setInt(4, aTimesheet_log.getMode_activity_id());
            ps.setInt(5, aTimesheet_log.getStaff_id());
            ps.setInt(6, aTimesheet_log.getCategory_activity_id());
            ps.setDouble(7, aTimesheet_log.getTime_taken());
            //ps.setDate(7, new java.sql.Date(aTimesheet_log.getSubmission_date().getTime()));
            ps.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
            ps.setString(9, aTimesheet_log.getActivity_name());
            ps.setDate(10, new java.sql.Date(aTimesheet_log.getActivity_date().getTime()));
            ps.setString(11, aTimesheet_log.getUnit_of_time());
            ps.setInt(12, aTimesheet_log.getSubcategory_activity_id());
            ps.setLong(13, aTimesheet_log.getProject_id());
            ps.setString(14, aTimesheet_log.getSubmission_by());
            ps.setTimestamp(15, new java.sql.Timestamp(new java.util.Date().getTime()));
            ps.setString(16, new GeneralUserSetting().getCurrentUser().getUserName());
            ps.setString(17, aTimesheet_log.getAction());
            ps.setLong(18, aTimesheet_log.getTimesheet_log_id());

            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteTimesheet_log(Timesheet_log aTimesheet_log) {
        int IsDeleted = 0;
        String sql = "DELETE FROM timesheet_log WHERE timesheet_log_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTimesheet_log.getTimesheet_log_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public Timesheet_log getTimesheet_log(long aTimesheet_log_id) {
        String sql = "SELECT * FROM timesheet_log WHERE timesheet_log_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTimesheet_log_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Timesheet_log obj = new Timesheet_log();
                this.setTimesheet_logFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Timesheet_log> getTimesheet_logAll() {
        String sql = "SELECT * FROM timesheet_log";
        ResultSet rs;
        List<Timesheet_log> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Timesheet_log obj = new Timesheet_log();
                this.setTimesheet_logFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    public List<Timesheet_log> getTimesheetLogByTimesheetId(long aTimesheet_id) {
        String sql = "SELECT * FROM timesheet_log where timesheet_id = ?";
        ResultSet rs;
        List<Timesheet_log> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTimesheet_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Timesheet_log obj = new Timesheet_log();
                this.setTimesheet_logFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
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
