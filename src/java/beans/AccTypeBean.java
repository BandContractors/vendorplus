package beans;

import connections.DBConnection;
import entities.AccType;
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
@ManagedBean(name = "accTypeBean")
@SessionScoped
public class AccTypeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private List<AccType> AccTypeObjectList;

    public void setAccTypeFromResultset(AccType aAccCoa, ResultSet aResultSet) {
        try {
            try {
                aAccCoa.setAccTypeId(aResultSet.getInt("acc_type_id"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccTypeId(0);
            }
            try {
                aAccCoa.setTypeCode(aResultSet.getString("type_code"));
            } catch (NullPointerException npe) {
                aAccCoa.setTypeCode("");
            }
            try {
                aAccCoa.setAccTypeName(aResultSet.getString("acc_type_name"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccTypeName("");
            }
            try {
                aAccCoa.setAccTypeDesc(aResultSet.getString("acc_type_desc"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccTypeDesc("");
            }
            try {
                aAccCoa.setOrderType(aResultSet.getInt("order_type"));
            } catch (NullPointerException npe) {
                aAccCoa.setOrderType(0);
            }
            try {
                aAccCoa.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                aAccCoa.setIsActive(0);
            }
            try {
                aAccCoa.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                aAccCoa.setIsDeleted(0);
            }
            try {
                aAccCoa.setAddBy(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                aAccCoa.setAddBy(0);
            }
            try {
                aAccCoa.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (NullPointerException npe) {
                aAccCoa.setLastEditBy(0);
            }
            try {
                aAccCoa.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aAccCoa.setAddDate(null);
            }
            try {
                aAccCoa.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (NullPointerException npe) {
                aAccCoa.setLastEditDate(null);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public AccType getAccTypeByCodeOrId(String aTypeCode, int aAccTypeId) {
        String sql = "";
        if (aTypeCode.length() > 0) {
            sql = "SELECT * FROM acc_type WHERE type_code='" + aTypeCode + "' ";
        } else if (aAccTypeId > 0) {
            sql = "SELECT * FROM acc_type WHERE acc_type_id=" + aAccTypeId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccType acctype = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                acctype = new AccType();
                this.setAccTypeFromResultset(acctype, rs);
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
        return acctype;
    }

    public AccType getAccTypeByCode(String aTypeCode) {
        String sql = "";
        if (aTypeCode.length() > 0) {
            sql = "SELECT * FROM acc_type WHERE type_code='" + aTypeCode + "' ";
        } else {
            return null;
        }
        ResultSet rs = null;
        AccType acctype = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                acctype = new AccType();
                this.setAccTypeFromResultset(acctype, rs);
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
        return acctype;
    }

    public AccType getAccTypeById(int aAccTypeId) {
        String sql = "";
        if (aAccTypeId > 0) {
            sql = "SELECT * FROM acc_type WHERE acc_type_id=" + aAccTypeId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccType acctype = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                acctype = new AccType();
                this.setAccTypeFromResultset(acctype, rs);
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
        return acctype;
    }

    public List<AccType> getAccTypeObjectListAll() {
        String sql;
        sql = "select * from acc_type order by order_type asc";
        ResultSet rs = null;
        List<AccType> ats = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccType acctype = new AccType();
                this.setAccTypeFromResultset(acctype, rs);
                ats.add(acctype);
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
        return ats;
    }

    public void clearAccType(AccType aAccType) {
        if (null != aAccType) {
            aAccType.setAccTypeId(0);
            aAccType.setTypeCode("");
            aAccType.setAccTypeName("");
            aAccType.setAccTypeDesc("");
            aAccType.setOrderType(0);
            aAccType.setIsActive(0);
            aAccType.setIsDeleted(0);
            aAccType.setAddBy(0);
            aAccType.setLastEditBy(0);
            aAccType.setAddDate(null);
            aAccType.setLastEditDate(null);
        }
    }

    /**
     * @return the AccTypeObjectList
     */
    public List<AccType> getAccTypeObjectList() {
        return AccTypeObjectList;
    }

    /**
     * @param AccTypeObjectList the AccTypeObjectList to set
     */
    public void setAccTypeObjectList(List<AccType> AccTypeObjectList) {
        this.AccTypeObjectList = AccTypeObjectList;
    }
}
