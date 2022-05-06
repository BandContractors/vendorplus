/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Staff;
import entities.UserDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/**
 *
 * @author HP
 */
@ManagedBean
@RequestScoped
public class StaffBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(StaffBean.class.getName());
    private String SearchStaffName = "";

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

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

    public void saveStaff(Staff aStaff) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg;
        String sql = null;
        try {
            try {
                BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
            } catch (Exception e) {
            }
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            //String sql1 = "SELECT count(*) as n FROM staff WHERE first_name='" + aStaff.getFirst_name() + "'";
            if (aStaff.getStaff_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aStaff.getStaff_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aStaff.getFirst_name().length() == 0) {
                msg = "First Name Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aStaff.getSecond_name().length() == 0) {
                msg = "Second Name Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aStaff.getStaff_id() == 0) {
                    saved = this.insertStaff(aStaff);
                } else if (aStaff.getStaff_id() > 0) {
                    saved = this.updateStaff(aStaff);
                }
                if (saved > 0) {
                    msg = "Staff Saved Successfully";
                    this.clearStaff(aStaff);
                } else {
                    msg = "Staff NOT Saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearStaff(Staff astaff) {
        try {
            if (null != astaff) {
                astaff.setStaff_id(0);
                astaff.setFirst_name("");
                astaff.setSecond_name("");
                astaff.setThird_name("");

            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displayStaff(Staff StaffFrom, Staff StaffTo) {
        try {
            this.clearStaff(StaffTo);
            StaffTo.setStaff_id(StaffFrom.getStaff_id());
            StaffTo.setFirst_name(StaffFrom.getFirst_name());
            StaffTo.setSecond_name(StaffFrom.getSecond_name());
            StaffTo.setThird_name(StaffFrom.getThird_name());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertStaff(Staff aStaff) {
        int InsertedId = 0;
        //sql code to insert
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
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg = "";
        int IsDeleted = 0;
        long N = 0;
        try {
            String sqlFind = "SELECT COUNT(*) AS n FROM timesheet WHERE staff_id=" + aStaff.getStaff_id();
            N = N + new UtilityBean().getN(sqlFind);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (aStaff.getStaff_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
        } else if (N > 0) {
            msg = "Staff has been used and cannot be Deleted";
        } else {
            String sql = "DELETE FROM staff WHERE staff_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aStaff.getStaff_id());
                ps.executeUpdate();
                IsDeleted = 1;
                msg = "Staff has been Deleted";
                this.clearStaff(aStaff);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
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

    public List<Staff> getStaffByStaffName(String aStaffName) {
        String sql;
        sql = "SELECT * FROM staff WHERE first_name LIKE CONCAT('%',?,'%') OR second_name LIKE CONCAT('%',?,'%') OR third_name LIKE CONCAT('%',?,'%')";
        ResultSet rs;
        List<Staff> staffList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aStaffName);
            ps.setString(2, aStaffName);
            ps.setString(3, aStaffName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff();
                this.setStaffFromResultset(staff, rs);
                staffList.add(staff);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return staffList;
    }

    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }

    /**
     * @return the SearchStaffName
     */
    public String getSearchStaffName() {
        return SearchStaffName;
    }

    /**
     * @param SearchStaffName the SearchStaffName to set
     */
    public void setSearchStaffName(String SearchStaffName) {
        this.SearchStaffName = SearchStaffName;
    }

}
