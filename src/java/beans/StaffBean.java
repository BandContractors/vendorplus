/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.Staff;
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
public class StaffBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(StaffBean.class.getName());

//    public void test() {
//        List<Staff> maList = this.getStaffAll();
//        System.out.println("Found:" + maList.size());
//    }
    public void setStaffFromResultset(Staff aStaff, ResultSet aResultSet) {
        try {
            try {
                aStaff.setStaff_id(aResultSet.getInt("staff_id"));
            } catch (Exception e) {
                aStaff.setStaff_id(0);
            }
            try {
                String first_name = aResultSet.getString("first_name");
                if (null == first_name) {
                    aStaff.setFirst_name("");
                } else {
                    aStaff.setFirst_name(first_name);
                }
            } catch (Exception e) {
                aStaff.setFirst_name("");
            }
            try {
                String second_name = aResultSet.getString("second_name");
                if (null == second_name) {
                    aStaff.setSecond_name("");
                } else {
                    aStaff.setSecond_name(second_name);
                }
            } catch (Exception e) {
                aStaff.setSecond_name("");
            }
            try {
                String third_name = aResultSet.getString("third_name");
                if (null == third_name) {
                    aStaff.setThird_name("");
                } else {
                    aStaff.setThird_name(third_name);
                }
            } catch (Exception e) {
                aStaff.setThird_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertStaff(Staff aStaff) {
        int InsertedId = 0;
        String sql = "INSERT INTO staff(first_name,second_name,third_name) VALUES(?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aStaff.getFirst_name());
            ps.setString(2, aStaff.getSecond_name());
            ps.setString(3, aStaff.getThird_name());
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

    public int updateStaff(Staff aStaff) {
        int IsUpdated = 0;
        String sql = "UPDATE staff SET first_name=?,second_name=?,third_name=? WHERE staff_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aStaff.getFirst_name());
            ps.setString(2, aStaff.getSecond_name());
            ps.setString(3, aStaff.getThird_name());
            ps.setInt(4, aStaff.getStaff_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteStaff(Staff aStaff) {
        int IsDeleted = 0;
        String sql = "DELETE FROM staff WHERE staff_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aStaff.getStaff_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public Staff getStaff(int aStaff_id) {
        String sql = "SELECT * FROM staff WHERE staff_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aStaff_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Staff obj = new Staff();
                this.setStaffFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Staff> getStaffAll() {
        String sql = "SELECT * FROM staff";
        ResultSet rs;
        List<Staff> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Staff obj = new Staff();
                this.setStaffFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }
}
