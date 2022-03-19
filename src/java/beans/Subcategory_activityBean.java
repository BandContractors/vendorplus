/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.Subcategory_activity;
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
public class Subcategory_activityBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Subcategory_activityBean.class.getName());

//    public void test() {
//        List<Subcategory_activity> maList = this.getSubcategory_activityAll();
//        System.out.println("Found:" + maList.size());
//    }
    public void setSubcategory_activityFromResultset(Subcategory_activity aSubcategory_activity, ResultSet aResultSet) {
        try {
            try {
                aSubcategory_activity.setSubcategory_activity_id(aResultSet.getInt("subcategory_activity_id"));
            } catch (Exception e) {
                aSubcategory_activity.setSubcategory_activity_id(0);
            }
            try {
                aSubcategory_activity.setCategory_activity_id(aResultSet.getInt("category_activity_id"));
            } catch (Exception e) {
                aSubcategory_activity.setCategory_activity_id(0);
            }
            try {
                String subcategory_name = aResultSet.getString("subcategory_name");
                if (null == subcategory_name) {
                    aSubcategory_activity.setSubcategory_name("");
                } else {
                    aSubcategory_activity.setSubcategory_name(subcategory_name);
                }
            } catch (Exception e) {
                aSubcategory_activity.setSubcategory_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertSubcategory_activity(Subcategory_activity aSubcategory_activity) {
        int InsertedId = 0;
        String sql = "INSERT INTO subcategory_activity(subcategory_name,category_activity_id) VALUES(?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aSubcategory_activity.getSubcategory_name());
            ps.setInt(2, aSubcategory_activity.getCategory_activity_id());
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

    public int updateSubcategory_activity(Subcategory_activity aSubcategory_activity) {
        int IsUpdated = 0;
        String sql = "UPDATE subcategory_activity SET subcategory_name=?,category_activity_id=? WHERE subcategory_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSubcategory_activity.getSubcategory_name());
            ps.setInt(2, aSubcategory_activity.getCategory_activity_id());
            ps.setInt(3, aSubcategory_activity.getSubcategory_activity_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteSubcategory_activity(Subcategory_activity aSubcategory_activity) {
        int IsDeleted = 0;
        String sql = "DELETE FROM subcategory_activity WHERE subcategory_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aSubcategory_activity.getSubcategory_activity_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public Subcategory_activity getSubcategory_activity(int aSubcategory_activity_id) {
        String sql = "SELECT * FROM subcategory_activity WHERE subcategory_activity_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aSubcategory_activity_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Subcategory_activity obj = new Subcategory_activity();
                this.setSubcategory_activityFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Subcategory_activity> getSubcategory_activityAll() {
        String sql = "SELECT * FROM subcategory_activity";
        ResultSet rs;
        List<Subcategory_activity> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Subcategory_activity obj = new Subcategory_activity();
                this.setSubcategory_activityFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }
}
