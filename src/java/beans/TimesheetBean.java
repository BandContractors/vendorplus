package beans;

import connections.DBConnection;

import entities.Timesheet;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
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

//    public void test() {
//        List<Timesheet> List = this.getTimesheetAll();
//        System.out.println("Found:" + List.size());
//    }
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

}
