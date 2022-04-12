/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.ActivityStatus;
import entities.GroupRight;
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
public class ActivityStatusBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(ActivityStatusBean.class.getName());

    private String SearchActivityStatusName = "";

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
//    public void test() {
//        Category_activity obj=new Category_activity();
//        obj.setCategory_name("Deployment");
//        int x=this.insertCategory_activity(obj);
//        System.out.println("Inserted:" + x);
//    }

    public void setActivityStatusFromResultset(ActivityStatus aActivity_status, ResultSet aResultSet) {
        try {
            try {
                aActivity_status.setActivity_status_id(aResultSet.getInt("activity_status_id"));
            } catch (Exception e) {
                aActivity_status.setActivity_status_id(0);
            }
            try {
                String activity_status_name = aResultSet.getString("activity_status_name");
                if (null == activity_status_name) {
                    aActivity_status.setActivity_status_name("");
                } else {
                    aActivity_status.setActivity_status_name(activity_status_name);
                }
            } catch (Exception e) {
                aActivity_status.setActivity_status_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveActivityStatus(ActivityStatus aActivity_status) {
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

            String sql1 = "SELECT count(*) as n FROM activity_status WHERE activity_status_name='" + aActivity_status.getActivity_status_name() + "'";

            if (aActivity_status.getActivity_status_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aActivity_status.getActivity_status_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aActivity_status.getActivity_status_name().length() <= 0) {
                msg = "Activity Status Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aActivity_status.getActivity_status_id() == 0 && ub.getN(sql1) > 0) {
                msg = "Activity Status Already Exists ##: " + aActivity_status.getActivity_status_name();
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aActivity_status.getActivity_status_id() == 0) {
                    saved = this.insertActivity_status(aActivity_status);
                } else if (aActivity_status.getActivity_status_id() > 0) {
                    saved = this.updateActivityStatus(aActivity_status);
                }
                if (saved > 0) {
                    msg = "Activity Status Saved Successfully";
                    this.clearActivityStatus(aActivity_status);
                } else {
                    msg = "Activity Status NOT Saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displayActivityStatus(ActivityStatus ActivityStatusFrom, ActivityStatus ActivityStatusTo) {
        try {
            this.clearActivityStatus(ActivityStatusTo);
            ActivityStatusTo.setActivity_status_id(ActivityStatusFrom.getActivity_status_id());
            ActivityStatusTo.setActivity_status_name(ActivityStatusFrom.getActivity_status_name());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertActivity_status(ActivityStatus aActivity_status) {
        int InsertedId = 0;
        String sql = "INSERT INTO activity_status(activity_status_name) VALUES(?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aActivity_status.getActivity_status_name());
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

    public int updateActivityStatus(ActivityStatus aActivity_status) {
        int IsUpdated = 0;
        String sql = "UPDATE activity_status SET activity_status_name=? WHERE activity_status_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aActivity_status.getActivity_status_name());
            ps.setInt(2, aActivity_status.getActivity_status_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteActivityStatus(ActivityStatus aActivity_status) {
        int IsDeleted = 0; 
        String sql = "DELETE FROM activity_status WHERE activity_status_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aActivity_status.getActivity_status_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public ActivityStatus getActivity_status(int aActivity_status_id) {
        String sql = "SELECT * FROM activity_status WHERE activity_status_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aActivity_status_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ActivityStatus obj = new ActivityStatus();
                this.setActivityStatusFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<ActivityStatus> getActivityStatusAll() {
        String sql = "SELECT * FROM activity_status";
        ResultSet rs;
        List<ActivityStatus> maList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                ActivityStatus obj = new ActivityStatus();
                this.setActivityStatusFromResultset(obj, rs);
                maList.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return maList;
    }

    public void clearActivityStatus(ActivityStatus aActivity_status) {
        try {
            if (null != aActivity_status) {
                aActivity_status.setActivity_status_id(0);
                aActivity_status.setActivity_status_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<ActivityStatus> getActivityStatusByActivityStatusName(String aActivityStatusName) {
        String sql;
        sql = "SELECT * FROM activity_status WHERE activity_status_name LIKE CONCAT('%',?,'%')";
        ResultSet rs;
        List<ActivityStatus> ActivityStatusList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aActivityStatusName);

            rs = ps.executeQuery();
            while (rs.next()) {
                ActivityStatus activityStatus = new ActivityStatus();
                this.setActivityStatusFromResultset(activityStatus, rs);
                ActivityStatusList.add(activityStatus);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ActivityStatusList;
    }

    public String getSearchActivityStatusName() {
        return SearchActivityStatusName;
    }

    public void setSearchActivityStatusName(String SearchActivityStatusName) {
        this.SearchActivityStatusName = SearchActivityStatusName;
    }

    /**
     * @return the menuItemBean
     */
    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    /**
     * @param menuItemBean the menuItemBean to set
     */
    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }
}
