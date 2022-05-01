/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Mode_activity;
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
public class Mode_activityBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Mode_activityBean.class.getName());

    private String SearchModeName = "";

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

//    public void test() {
//        List<Mode_activity> maList = this.getMode_activityAll();
//        System.out.println("Found:" + maList.size());
//    }
    public void setMode_activityFromResultset(Mode_activity aMode_activity, ResultSet aResultSet) {
        try {
            try {
                aMode_activity.setMode_activity_id(aResultSet.getInt("mode_activity_id"));
            } catch (Exception e) {
                aMode_activity.setMode_activity_id(0);
            }
            try {
                String mode_name = aResultSet.getString("mode_name");
                if (null == mode_name) {
                    aMode_activity.setMode_name("");
                } else {
                    aMode_activity.setMode_name(mode_name);
                }
            } catch (Exception e) {
                aMode_activity.setMode_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveMode_activity(Mode_activity aMode_activity) {
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

            String sql1 = "SELECT count(*) as n FROM mode_activity WHERE mode_name='" + aMode_activity.getMode_name() + "'";

            if (aMode_activity.getMode_activity_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aMode_activity.getMode_activity_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aMode_activity.getMode_name().length() <= 0) {
                msg = "Mode Activity Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aMode_activity.getMode_activity_id() == 0 && ub.getN(sql1) > 0) {
                msg = "Mode Activity Already Exists ##: " + aMode_activity.getMode_name();
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aMode_activity.getMode_activity_id() == 0) {
                    saved = this.insertMode_activity(aMode_activity);
                } else if (aMode_activity.getMode_activity_id() > 0) {
                    saved = this.updateMode_activity(aMode_activity);
                }
                if (saved > 0) {
                    msg = "Mode Activity Saved Successfully";
                    this.clearMode_activity(aMode_activity);
                } else {
                    msg = "Mode Activity NOT Saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displayMode_activity(Mode_activity Mode_activityFrom, Mode_activity Mode_activityTo) {
        try {
            this.clearMode_activity(Mode_activityTo);
            Mode_activityTo.setMode_activity_id(Mode_activityFrom.getMode_activity_id());
            Mode_activityTo.setMode_name(Mode_activityFrom.getMode_name());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearMode_activity(Mode_activity aMode_activity) {
        try {
            if (null != aMode_activity) {
                aMode_activity.setMode_activity_id(0);
                aMode_activity.setMode_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertMode_activity(Mode_activity aMode_activity) {
        int InsertedId = 0;
        String sql = "INSERT INTO mode_activity(mode_name) VALUES(?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aMode_activity.getMode_name());
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

    public int updateMode_activity(Mode_activity aMode_activity) {
        int IsUpdated = 0;
        String sql = "UPDATE mode_activity SET mode_name=? WHERE mode_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aMode_activity.getMode_name());
            ps.setInt(2, aMode_activity.getMode_activity_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }
    
    public int deleteMode_activity(Mode_activity aMode_activity) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg = "";
        int IsDeleted = 0;
        long N = 0;
        try {
            String sqlFind = "SELECT COUNT(*) AS n FROM timesheet WHERE mode_activity_id=" + aMode_activity.getMode_activity_id();
            N = N + new UtilityBean().getN(sqlFind);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (aMode_activity.getMode_activity_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
        } else if (N > 0) {
            msg = "Mode Activity has been used and cannot be Deleted";
        } else {
            String sql = "DELETE FROM mode_activity WHERE mode_activity_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aMode_activity.getMode_activity_id());
                ps.executeUpdate();
                IsDeleted = 1;
                msg = "Activity Mode has been Deleted";
                this.clearMode_activity(aMode_activity);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        return IsDeleted;
    }

    public Mode_activity getMode_activity(int aMode_activity_id) {
        String sql = "SELECT * FROM mode_activity WHERE mode_activity_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aMode_activity_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Mode_activity obj = new Mode_activity();
                this.setMode_activityFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Mode_activity> getMode_activityAll() {
        String sql = "SELECT * FROM mode_activity";
        ResultSet rs;
        List<Mode_activity> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Mode_activity obj = new Mode_activity();
                this.setMode_activityFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }
    
    
     public List<Mode_activity> getMode_activityByMode_activityName(String aMode_activityName) {
        String sql;
        sql = "SELECT * FROM mode_activity WHERE mode_name LIKE CONCAT('%',?,'%')";
        ResultSet rs;
        List<Mode_activity> Mode_activityList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aMode_activityName);

            rs = ps.executeQuery();
            while (rs.next()) {
                Mode_activity mode_activity = new Mode_activity();
                this.setMode_activityFromResultset(mode_activity, rs);
                Mode_activityList.add(mode_activity);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Mode_activityList;
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

    /**
     * @return the SearchModeName
     */
    public String getSearchModeName() {
        return SearchModeName;
    }

    /**
     * @param SearchModeName the SearchModeName to set
     */
    public void setSearchModeName(String SearchModeName) {
        this.SearchModeName = SearchModeName;
    }
}
