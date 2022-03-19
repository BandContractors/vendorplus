/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.Category_activity;
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
public class Category_activityBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Category_activityBean.class.getName());
    
//    public void test() {
//        Category_activity obj=new Category_activity();
//        obj.setCategory_name("Deployment");
//        int x=this.insertCategory_activity(obj);
//        System.out.println("Inserted:" + x);
//    }
    
    public void setCategory_activityFromResultset(Category_activity aCategory_activity, ResultSet aResultSet) {
        try {
            try {
                aCategory_activity.setCategory_activity_id(aResultSet.getInt("category_activity_id"));
            } catch (Exception e) {
                aCategory_activity.setCategory_activity_id(0);
            }
            try {
                String category_name = aResultSet.getString("category_name");
                if (null == category_name) {
                    aCategory_activity.setCategory_name("");
                } else {
                    aCategory_activity.setCategory_name(category_name);
                }
            } catch (Exception e) {
                aCategory_activity.setCategory_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertCategory_activity(Category_activity aCategory_activity) {
        int InsertedId = 0;
        String sql = "INSERT INTO category_activity(category_name) VALUES(?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aCategory_activity.getCategory_name());
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

    public int updateCategory_activity(Category_activity aCategory_activity) {
        int IsUpdated = 0;
        String sql = "UPDATE category_activity SET category_name=? WHERE category_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCategory_activity.getCategory_name());
            ps.setInt(2, aCategory_activity.getCategory_activity_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteCategory_activity(Category_activity aCategory_activity) {
        int IsDeleted = 0;
        String sql = "DELETE FROM category_activity WHERE category_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aCategory_activity.getCategory_activity_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public Category_activity getCategory_activity(int aCategory_activity_id) {
        String sql = "SELECT * FROM category_activity WHERE category_activity_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aCategory_activity_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Category_activity obj = new Category_activity();
                this.setCategory_activityFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Category_activity> getCategory_activityAll() {
        String sql = "SELECT * FROM category_activity";
        ResultSet rs;
        List<Category_activity> maList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Category_activity obj = new Category_activity();
                this.setCategory_activityFromResultset(obj, rs);
                maList.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return maList;
    }
}
