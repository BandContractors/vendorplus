package beans;

import connections.DBConnection;
import entities.AccGroup;
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
@ManagedBean(name = "accGroupBean")
@SessionScoped
public class AccGroupBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private List<AccGroup> AccGroupObjectList;

    public void setAccGroupFromResultset(AccGroup aAccCoa, ResultSet aResultSet) {
        try {
            try {
                aAccCoa.setAccGroupId(aResultSet.getInt("acc_group_id"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccGroupId(0);
            }
            try {
                aAccCoa.setAccTypeId(aResultSet.getInt("acc_type_id"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccTypeId(0);
            }
            try {
                aAccCoa.setGroupCode(aResultSet.getString("group_code"));
            } catch (NullPointerException npe) {
                aAccCoa.setGroupCode("");
            }
            try {
                aAccCoa.setAccGroupName(aResultSet.getString("acc_group_name"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccGroupName("");
            }
            try {
                aAccCoa.setAccGroupDesc(aResultSet.getString("acc_group_desc"));
            } catch (NullPointerException npe) {
                aAccCoa.setAccGroupDesc("");
            }
            try {
                aAccCoa.setOrderGroup(aResultSet.getInt("order_group"));
            } catch (NullPointerException npe) {
                aAccCoa.setOrderGroup(0);
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

    public AccGroup getAccGroupByCodeOrId(String aGroupCode, int aAccGroupId) {
        String sql = "";
        if (aGroupCode.length() > 0) {
            sql = "SELECT * FROM acc_group WHERE group_code='" + aGroupCode + "' ";
        } else if (aAccGroupId > 0) {
            sql = "SELECT * FROM acc_group WHERE acc_group_id=" + aAccGroupId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccGroup accgroup = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accgroup = new AccGroup();
                this.setAccGroupFromResultset(accgroup, rs);
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
        return accgroup;
    }

    public AccGroup getAccGroupById(int aAccGroupId) {
        String sql = "";
        if (aAccGroupId > 0) {
            sql = "SELECT * FROM acc_group WHERE acc_group_id=" + aAccGroupId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccGroup accgroup = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accgroup = new AccGroup();
                this.setAccGroupFromResultset(accgroup, rs);
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
        return accgroup;
    }

    public AccGroup getAccGroupByCode(String aGroupCode) {
        String sql = "";
        if (aGroupCode.length() > 0) {
            sql = "SELECT * FROM acc_group WHERE group_code='" + aGroupCode + "' ";
        } else {
            return null;
        }
        ResultSet rs = null;
        AccGroup accgroup = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accgroup = new AccGroup();
                this.setAccGroupFromResultset(accgroup, rs);
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
        return accgroup;
    }

    public List<AccGroup> getAccGroupObjectListAll() {
        String sql;
        sql = "{select * from acc_group order by group_code asc}";
        ResultSet rs = null;
        this.setAccGroupObjectList(new ArrayList<AccGroup>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccGroup accgroup = new AccGroup();
                this.setAccGroupFromResultset(accgroup, rs);
                this.getAccGroupObjectList().add(accgroup);
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
        return getAccGroupObjectList();
    }

    public List<AccGroup> getAccGroupObjectListChild(String aParentCode) {
        String sql;
        sql = "{select * from acc_group where group_code like '?%' order by group_code asc}";
        ResultSet rs = null;
        this.setAccGroupObjectList(new ArrayList<AccGroup>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aParentCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccGroup accgroup = new AccGroup();
                this.setAccGroupFromResultset(accgroup, rs);
                this.getAccGroupObjectList().add(accgroup);
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
        return getAccGroupObjectList();
    }

    public List<AccGroup> getAccGroupsByType(int aAccTypeId) {
        String sql;
        sql = "select * from acc_group where acc_type_id=? order by order_group asc";
        ResultSet rs = null;
        List<AccGroup> ags = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aAccTypeId);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccGroup accgroup = new AccGroup();
                this.setAccGroupFromResultset(accgroup, rs);
                ags.add(accgroup);
            }
        } catch (SQLException se) {
            System.err.println("getAccGroupsByType:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getAccGroupsByType:" + ex.getMessage());
                }
            }
        }
        return ags;
    }

    public void clearAccGroup(AccGroup aAccGroup) {
        if (null != aAccGroup) {
            aAccGroup.setAccGroupId(0);
            aAccGroup.setAccTypeId(0);
            aAccGroup.setGroupCode("");
            aAccGroup.setAccGroupName("");
            aAccGroup.setAccGroupDesc("");
            aAccGroup.setOrderGroup(0);
            aAccGroup.setIsActive(0);
            aAccGroup.setIsDeleted(0);
            aAccGroup.setAddBy(0);
            aAccGroup.setLastEditBy(0);
            aAccGroup.setAddDate(null);
            aAccGroup.setLastEditDate(null);
        }
    }

    /**
     * @return the AccGroupObjectList
     */
    public List<AccGroup> getAccGroupObjectList() {
        return AccGroupObjectList;
    }

    /**
     * @param AccGroupObjectList the AccGroupObjectList to set
     */
    public void setAccGroupObjectList(List<AccGroup> AccGroupObjectList) {
        this.AccGroupObjectList = AccGroupObjectList;
    }
}
