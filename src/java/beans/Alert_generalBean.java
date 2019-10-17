package beans;

import connections.DBConnection;
import entities.Alert_general;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "alert_generalBean")
@SessionScoped
public class Alert_generalBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Alert_general> Alert_generalObjectList;
    private Alert_general Alert_generalObj;

    public void setAlert_generalFromResultset(Alert_general aAlert_general, ResultSet aResultSet) {
        try {
            try {
                aAlert_general.setAlert_general_id(aResultSet.getLong("alert_general_id"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_general_id(0);
            }
            try {
                aAlert_general.setAlert_type(aResultSet.getString("alert_type"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_type("");
            }
            try {
                aAlert_general.setMessage(aResultSet.getString("message"));
            } catch (NullPointerException npe) {
                aAlert_general.setMessage("");
            }
            try {
                aAlert_general.setAlert_users(aResultSet.getString("alert_users"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_users("");
            }
            try {
                aAlert_general.setRead_by(aResultSet.getString("read_by"));
            } catch (NullPointerException npe) {
                aAlert_general.setRead_by("");
            }
            try {
                aAlert_general.setAlert_items(aResultSet.getString("alert_items"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_items("");
            }
            try {
                aAlert_general.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aAlert_general.setAdd_date(null);
            }
            try {
                aAlert_general.setLast_update_date(new Date(aResultSet.getTimestamp("last_update_date").getTime()));
            } catch (NullPointerException npe) {
                aAlert_general.setLast_update_date(null);
            }
            try {
                aAlert_general.setAdd_by(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                aAlert_general.setAdd_by(0);
            }
            try {
                aAlert_general.setLast_update_by(aResultSet.getInt("last_update_by"));
            } catch (NullPointerException npe) {
                aAlert_general.setLast_update_by(0);
            }
            try {
                aAlert_general.setStatus_code(aResultSet.getString("status_code"));
            } catch (NullPointerException npe) {
                aAlert_general.setStatus_code("");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public Alert_general getAlert_general(long aAlert_general_id) {
        String sql = "SELECT * FROM alert_general WHERE alert_general_id=" + aAlert_general_id;
        ResultSet rs = null;
        Alert_general ag = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ag = new Alert_general();
                this.setAlert_generalFromResultset(ag, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return ag;
    }

    public void saveAlert_general(Alert_general aAlert_general) {
        String sql = null;
        if (aAlert_general.getAlert_general_id() == 0) {
            sql = "INSERT INTO alert_general(alert_type,subject,message,alert_users,read_by,alert_items,add_date,add_by,status_code) VALUES (?,?,?,?,?,?,?,?,?)";
        } else if (aAlert_general.getAlert_general_id() > 0) {
            sql = "UPDATE alert_general SET status_code=?,read_by=?,last_update_date=?,last_update_by=? WHERE alert_general_id=?";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //Insert
            if (aAlert_general.getAlert_general_id() == 0) {
                ps.setString(1, aAlert_general.getAlert_type());
                ps.setString(2, aAlert_general.getSubject());
                ps.setString(3, aAlert_general.getMessage());
                ps.setString(4, aAlert_general.getAlert_users());
                ps.setString(5, aAlert_general.getRead_by());
                ps.setString(6, aAlert_general.getAlert_items());
                try {
                    ps.setTimestamp(7, new java.sql.Timestamp(aAlert_general.getAdd_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(7, null);
                }
                ps.setInt(8, aAlert_general.getAdd_by());
                ps.setString(9, aAlert_general.getStatus_code());
            }
            //update
            if (aAlert_general.getAlert_general_id() > 0) {
                ps.setString(1, aAlert_general.getStatus_code());
                ps.setString(2, aAlert_general.getRead_by());
                try {
                    ps.setTimestamp(3, new java.sql.Timestamp(aAlert_general.getLast_update_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(3, null);
                }
                ps.setInt(4, aAlert_general.getLast_update_by());
            }
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("saveAlert_general:" + e.getMessage());
        }
    }

    public void clearAlert_general(Alert_general aAlert_general) {
        if (null != aAlert_general) {
            aAlert_general.setAlert_general_id(0);
            aAlert_general.setAlert_type("");
            aAlert_general.setMessage("");
            aAlert_general.setAlert_users("");
            aAlert_general.setRead_by("");
            aAlert_general.setAlert_items("");
            aAlert_general.setAdd_date(null);
            aAlert_general.setLast_update_date(null);
            aAlert_general.setAdd_by(0);
            aAlert_general.setLast_update_by(0);
            aAlert_general.setStatus_code("");
        }
    }

    /**
     * @return the Alert_generalObjectList
     */
    public List<Alert_general> getAlert_generalObjectList() {
        return Alert_generalObjectList;
    }

    /**
     * @param Alert_generalObjectList the Alert_generalObjectList to set
     */
    public void setAlert_generalObjectList(List<Alert_general> Alert_generalObjectList) {
        this.Alert_generalObjectList = Alert_generalObjectList;
    }

    /**
     * @return the Alert_generalObj
     */
    public Alert_general getAlert_generalObj() {
        return Alert_generalObj;
    }

    /**
     * @param Alert_generalObj the Alert_generalObj to set
     */
    public void setAlert_generalObj(Alert_general Alert_generalObj) {
        this.Alert_generalObj = Alert_generalObj;
    }
}
