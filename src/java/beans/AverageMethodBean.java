package beans;

import connections.DBConnection;
import entities.AverageMethod;
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
@ManagedBean(name = "averageMethodBean")
@SessionScoped
public class AverageMethodBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public void setAverageMethodFromResultset(AverageMethod aAverageMethod, ResultSet aResultSet) {
        try {
            try {
                aAverageMethod.setAverageMethodId(aResultSet.getInt("average_method_id"));
            } catch (NullPointerException npe) {
                aAverageMethod.setAverageMethodId(0);
            }
            try {
                aAverageMethod.setAverageMethodName(aResultSet.getString("average_method_name"));
            } catch (NullPointerException npe) {
                aAverageMethod.setAverageMethodName("");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public List<AverageMethod> getAverageMethods() {
        String sql = "";
        sql = "SELECT * FROM average_method order by average_method_name ASC";
        ResultSet rs = null;
        List<AverageMethod> recs = new ArrayList<AverageMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AverageMethod rec = new AverageMethod();
                this.setAverageMethodFromResultset(rec, rs);
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

    public AverageMethod getAverageMethodById(int aAverageMethodId) {
        String sql = "";
        sql = "SELECT * FROM average_method WHERE average_method_id=" + aAverageMethodId;
        ResultSet rs = null;
        AverageMethod rec = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                rec = new AverageMethod();
                this.setAverageMethodFromResultset(rec, rs);
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
