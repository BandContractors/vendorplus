package beans;

import connections.DBConnection;
import entities.AccCategory;
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
@ManagedBean(name = "accCategoryBean")
@SessionScoped
public class AccCategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private List<AccCategory> AccCategoryObjectList;

    public void setAccCategoryFromResultset(AccCategory aAccCoa, ResultSet aResultSet) {
        try {
            try {
                aAccCoa.setAccCategoryId(aResultSet.getInt("acc_category_id"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccCategoryId(0);
            }
            try {
                aAccCoa.setAccGroupId(aResultSet.getInt("acc_group_id"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccGroupId(0);
            }
            try {
                aAccCoa.setCategoryCode(aResultSet.getString("category_code"));
            } catch (NullPointerException npe) {
                aAccCoa.setCategoryCode("");
            }
            try {
                aAccCoa.setAccCategoryName(aResultSet.getString("acc_category_name"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccCategoryName("");
            }
            try {
                aAccCoa.setAccCategoryDesc(aResultSet.getString("acc_category_desc"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccCategoryDesc("");
            }
            try {
                aAccCoa.setOrderCategory(aResultSet.getInt("order_category"));
            } catch (NullPointerException npe) {
                aAccCoa.setOrderCategory(0);
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

    public AccCategory getAccCategoryByCodeOrId(String aCategoryCode, int aAccCategoryId) {
        String sql = "";
        if (aCategoryCode.length() > 0) {
            sql = "SELECT * FROM acc_category WHERE category_code='" + aCategoryCode + "' ";
        } else if (aAccCategoryId > 0) {
            sql = "SELECT * FROM acc_category WHERE acc_category_id=" + aAccCategoryId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccCategory acccategory = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                acccategory = new AccCategory();
                this.setAccCategoryFromResultset(acccategory, rs);
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
        return acccategory;
    }

    public AccCategory getAccCategoryById(int aAccCategoryId) {
        String sql = "";
        if (aAccCategoryId > 0) {
            sql = "SELECT * FROM acc_category WHERE acc_category_id=" + aAccCategoryId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccCategory acccategory = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                acccategory = new AccCategory();
                this.setAccCategoryFromResultset(acccategory, rs);
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
        return acccategory;
    }

    public AccCategory getAccCategoryByCode(String aCategoryCode) {
        String sql = "";
        if (aCategoryCode.length() > 0) {
            sql = "SELECT * FROM acc_category WHERE category_code='" + aCategoryCode + "' ";
        } else {
            return null;
        }
        ResultSet rs = null;
        AccCategory acccategory = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                acccategory = new AccCategory();
                this.setAccCategoryFromResultset(acccategory, rs);
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
        return acccategory;
    }

    public List<AccCategory> getAccCategoryObjectListAll() {
        String sql;
        sql = "select * from acc_category order by category_code asc";
        ResultSet rs = null;
        this.setAccCategoryObjectList(new ArrayList<AccCategory>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCategory acccategory = new AccCategory();
                this.setAccCategoryFromResultset(acccategory, rs);
                this.getAccCategoryObjectList().add(acccategory);
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
        return getAccCategoryObjectList();
    }

    public List<AccCategory> getAccCategoryObjectListChild(String aParentCode) {
        String sql;
        sql = "select * from acc_category where category_code like '?%' order by category_code asc";
        ResultSet rs = null;
        this.setAccCategoryObjectList(new ArrayList<AccCategory>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aParentCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCategory acccategory = new AccCategory();
                this.setAccCategoryFromResultset(acccategory, rs);
                this.getAccCategoryObjectList().add(acccategory);
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
        return getAccCategoryObjectList();
    }

    public List<AccCategory> getAccCategoriesByGroup(int aAccGroupId) {
        String sql;
        sql = "select * from acc_category where acc_group_id=? order by order_category asc";
        ResultSet rs = null;
        List<AccCategory> acs = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aAccGroupId);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCategory acccategory = new AccCategory();
                this.setAccCategoryFromResultset(acccategory, rs);
                acs.add(acccategory);
            }
        } catch (SQLException se) {
            System.err.println("getAccCategoriesByGroup:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getAccCategoriesByGroup:" + ex.getMessage());
                }
            }
        }
        return acs;
    }

    public void clearAccCategory(AccCategory aAccCategory) {
        if (null != aAccCategory) {
            aAccCategory.setAccCategoryId(0);
            aAccCategory.setAccGroupId(0);
            aAccCategory.setCategoryCode("");
            aAccCategory.setAccCategoryName("");
            aAccCategory.setAccCategoryDesc("");
            aAccCategory.setOrderCategory(0);
            aAccCategory.setIsActive(0);
            aAccCategory.setIsDeleted(0);
            aAccCategory.setAddBy(0);
            aAccCategory.setLastEditBy(0);
            aAccCategory.setAddDate(null);
            aAccCategory.setLastEditDate(null);
        }
    }

    /**
     * @return the AccCategoryObjectList
     */
    public List<AccCategory> getAccCategoryObjectList() {
        return AccCategoryObjectList;
    }

    /**
     * @param AccCategoryObjectList the AccCategoryObjectList to set
     */
    public void setAccCategoryObjectList(List<AccCategory> AccCategoryObjectList) {
        this.AccCategoryObjectList = AccCategoryObjectList;
    }
}
