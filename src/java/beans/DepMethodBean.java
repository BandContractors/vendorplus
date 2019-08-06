package beans;

import connections.DBConnection;
import entities.DepMethod;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
@ManagedBean(name = "depMethodBean")
@SessionScoped
public class DepMethodBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setDepMethodFromResultset(DepMethod aDepMethod, ResultSet aResultSet) {
        try {
            try {
                aDepMethod.setDepMethodId(aResultSet.getInt("dep_method_id"));
            } catch (NullPointerException npe) {
                aDepMethod.setDepMethodId(0);
            }
            try {
                aDepMethod.setDepMethodName(aResultSet.getString("dep_method_name"));
            } catch (NullPointerException npe) {
                aDepMethod.setDepMethodName("");
            }
            try {
                aDepMethod.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                aDepMethod.setIsActive(0);
            }
            try {
                aDepMethod.setDepMethodCode(aResultSet.getString("dep_method_code"));
            } catch (NullPointerException npe) {
                aDepMethod.setDepMethodCode("");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public List<DepMethod> getDepMethods() {
        String sql = "";
        sql = "SELECT * FROM dep_method order by dep_method_id ASC";
        ResultSet rs = null;
        List<DepMethod> recs = new ArrayList<DepMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                DepMethod rec = new DepMethod();
                this.setDepMethodFromResultset(rec, rs);
                recs.add(rec);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return recs;
    }

    public DepMethod getDepMethodById(int aDepMethodId) {
        String sql = "";
        sql = "SELECT * FROM dep_method WHERE dep_method_id=" + aDepMethodId;
        ResultSet rs = null;
        DepMethod rec = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                rec = new DepMethod();
                this.setDepMethodFromResultset(rec, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return rec;
    }

}
