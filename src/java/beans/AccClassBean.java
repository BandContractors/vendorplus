package beans;

import connections.DBConnection;
import entities.AccClass;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
@ManagedBean(name = "accClassBean")
@SessionScoped
public class AccClassBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private List<AccClass> AccClassObjectList;

    public void setAccClassFromResultset(AccClass aAccClass, ResultSet aResultSet) {
        try {
            try {
                aAccClass.setAccClassId(aResultSet.getInt("acc_class_id"));
            } catch (NullPointerException npe) {
                aAccClass.setAccClassId(0);
            }
            try {
                aAccClass.setAccClassName(aResultSet.getString("acc_class_name"));
            } catch (NullPointerException npe) {
                aAccClass.setAccClassName("");
            }
            try {
                aAccClass.setAccClassDesc(aResultSet.getString("acc_class_desc"));
            } catch (NullPointerException npe) {
                aAccClass.setAccClassDesc("");
            }
            try {
                aAccClass.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                aAccClass.setIsActive(0);
            }
            try {
                aAccClass.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                aAccClass.setIsDeleted(0);
            }
            try {
                aAccClass.setAddBy(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                aAccClass.setAddBy(0);
            }
            try {
                aAccClass.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (NullPointerException npe) {
                aAccClass.setLastEditBy(0);
            }
            try {
                aAccClass.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aAccClass.setAddDate(null);
            }
            try {
                aAccClass.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (NullPointerException npe) {
                aAccClass.setLastEditDate(null);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public AccClass getAccClassById(int aAccClassId) {
        String sql = "";
        if (aAccClassId > 0) {
            sql = "SELECT * FROM acc_class WHERE acc_class_id=" + aAccClassId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccClass accclass = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accclass = new AccClass();
                this.setAccClassFromResultset(accclass, rs);
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
        return accclass;
    }
    
    public List<AccClass> getAccClassObjectListAll() {
        String sql;
        sql = "{select * from acc_class order by acc_class_name asc}";
        ResultSet rs = null;
        this.setAccClassObjectList(new ArrayList<AccClass>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccClass accclass = new AccClass();
                this.setAccClassFromResultset(accclass, rs);
                this.getAccClassObjectList().add(accclass);
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
        return getAccClassObjectList();
    }

    public void clearAccClass(AccClass aAccClass) {
        if (null != aAccClass) {
            aAccClass.setAccClassId(0);
            aAccClass.setAccClassName("");
            aAccClass.setAccClassDesc("");
            aAccClass.setIsActive(0);
            aAccClass.setIsDeleted(0);
            aAccClass.setAddBy(0);
            aAccClass.setLastEditBy(0);
            aAccClass.setAddDate(null);
            aAccClass.setLastEditDate(null);
        }
    }

    /**
     * @return the AccClassObjectList
     */
    public List<AccClass> getAccClassObjectList() {
        return AccClassObjectList;
    }

    /**
     * @param AccClassObjectList the AccClassObjectList to set
     */
    public void setAccClassObjectList(List<AccClass> AccClassObjectList) {
        this.AccClassObjectList = AccClassObjectList;
    }
}
