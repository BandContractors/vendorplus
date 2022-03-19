/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.Mode_activity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author HP
 */
@ManagedBean
@RequestScoped
public class Mode_activityBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Mode_activityBean.class.getName());
    
//    public void test() {
//        List<Mode_activity> maList = this.getMode_activityAll();
//        System.out.println("Found:" + maList.size());
//    }
    public void setMode_activityFromResultset(Mode_activity aMode_activity, ResultSet aResultSet) {
        try {
            try {
                aMode_activity.setMode_activity_id(aResultSet.getInt("mode_activity_id"));
            } catch (Exception e) {
                aMode_activity.setMode_activity_id(0);
            }
            try {
                String mode_name = aResultSet.getString("mode_name");
                if (null == mode_name) {
                    aMode_activity.setMode_name("");
                } else {
                    aMode_activity.setMode_name(mode_name);
                }
            } catch (Exception e) {
                aMode_activity.setMode_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertMode_activity(Mode_activity aMode_activity) {
        int InsertedId = 0;
        String sql = "INSERT INTO mode_activity(mode_name) VALUES(?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aMode_activity.getMode_name());
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

    public int updateMode_activity(Mode_activity aMode_activity) {
        int IsUpdated = 0;
        String sql = "UPDATE mode_activity SET mode_name=? WHERE mode_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aMode_activity.getMode_name());
            ps.setInt(2, aMode_activity.getMode_activity_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteMode_activity(Mode_activity aMode_activity) {
        int IsDeleted = 0;
        String sql = "DELETE FROM mode_activity WHERE mode_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aMode_activity.getMode_activity_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public Mode_activity getMode_activity(int aMode_activity_id) {
        String sql = "SELECT * FROM mode_activity WHERE mode_activity_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aMode_activity_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Mode_activity obj = new Mode_activity();
                this.setMode_activityFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Mode_activity> getMode_activityAll() {
        String sql = "SELECT * FROM mode_activity";
        ResultSet rs;
        List<Mode_activity> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Mode_activity obj = new Mode_activity();
                this.setMode_activityFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }
}
