/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Subcategory_activity;
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
import javax.faces.bean.SessionScoped;
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
@SessionScoped
public class Subcategory_activityBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Subcategory_activityBean.class.getName());

    private String SearchSubCategoryActivityName = "";
    private String ActionMessage = null;
    private int SelectedSubCategoryActivityId;

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setSubcategory_activityFromResultset(Subcategory_activity aSubcategory_activity, ResultSet aResultSet) {
        try {
            try {
                aSubcategory_activity.setSubcategory_activity_id(aResultSet.getInt("subcategory_activity_id"));
            } catch (Exception e) {
                aSubcategory_activity.setSubcategory_activity_id(0);
            }
            try {
                aSubcategory_activity.setCategory_activity_id(aResultSet.getInt("category_activity_id"));
            } catch (Exception e) {
                aSubcategory_activity.setCategory_activity_id(0);
            }
            try {
                String subcategory_name = aResultSet.getString("subcategory_name");
                if (null == subcategory_name) {
                    aSubcategory_activity.setSubcategory_name("");
                } else {
                    aSubcategory_activity.setSubcategory_name(subcategory_name);
                }
            } catch (Exception e) {
                aSubcategory_activity.setSubcategory_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Subcategory_activity> getSubcategory_activityAll() {
        String sql = "SELECT * FROM subcategory_activity";
        ResultSet rs;
        List<Subcategory_activity> maList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Subcategory_activity obj = new Subcategory_activity();
                this.setSubcategory_activityFromResultset(obj, rs);
                maList.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return maList;
    }

    public void saveSubCategoryActivity(Subcategory_activity aSubcategory_activity) {
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

            String sql1 = "SELECT count(*) as n FROM subcategory_activity WHERE subcategory_name='" + aSubcategory_activity.getSubcategory_name() + "'";

            if (aSubcategory_activity.getSubcategory_activity_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aSubcategory_activity.getSubcategory_activity_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aSubcategory_activity.getSubcategory_name().length() <= 0) {
                msg = "Sub Activity Category Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aSubcategory_activity.getSubcategory_activity_id() == 0 && ub.getN(sql1) > 0) {
                msg = "Sub Activity Category Already Exists ##: " + aSubcategory_activity.getSubcategory_name();
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aSubcategory_activity.getSubcategory_activity_id() == 0) {
                    saved = this.insertSubcategory_activity(aSubcategory_activity);
                } else if (aSubcategory_activity.getSubcategory_activity_id() > 0) {
                    saved = this.updateSubcategory_activity(aSubcategory_activity);
                }
                if (saved > 0) {
                    msg = "Sub Activity Category Saved Successfully";
                    this.clearSubcategory_activity(aSubcategory_activity);
                } else {
                    msg = "Sub Activity Category NOT Saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displaySubcategory_activity(Subcategory_activity Subcategory_activityFrom, Subcategory_activity Subcategory_activityTo) {
        Subcategory_activityTo.setCategory_activity_id(Subcategory_activityFrom.getCategory_activity_id());
        Subcategory_activityTo.setSubcategory_activity_id(Subcategory_activityFrom.getSubcategory_activity_id());
        Subcategory_activityTo.setSubcategory_name(Subcategory_activityFrom.getSubcategory_name());
    }

    public int insertSubcategory_activity(Subcategory_activity aSubcategory_activity) {
        int InsertedId = 0;
        String sql = "INSERT INTO subcategory_activity(subcategory_name,category_activity_id) VALUES(?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aSubcategory_activity.getSubcategory_name());
            ps.setInt(2, aSubcategory_activity.getCategory_activity_id());
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

    public int updateSubcategory_activity(Subcategory_activity aSubcategory_activity) {
        int IsUpdated = 0;
        String sql = "UPDATE subcategory_activity SET subcategory_name=?,category_activity_id=? WHERE subcategory_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSubcategory_activity.getSubcategory_name());
            ps.setInt(2, aSubcategory_activity.getCategory_activity_id());
            ps.setInt(3, aSubcategory_activity.getSubcategory_activity_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public void clearSubcategory_activity(Subcategory_activity aSubcategory_activity) {
        try {
            if (null != aSubcategory_activity) {
                aSubcategory_activity.setSubcategory_activity_id(0);
                aSubcategory_activity.setCategory_activity_id(0);
                aSubcategory_activity.setSubcategory_name("");

            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void deleteSubCategoryActivity() {
        this.deleteSubCategoryActivityById(this.getSelectedSubCategoryActivityId());
    }

    public void deleteSubCategoryActivityByObject(Subcategory_activity aSubcategory_activity) {
        int status = this.deleteSubCategoryActivityById(aSubcategory_activity.getSubcategory_activity_id());
        if (status == 1) {
            this.clearSubcategory_activity(aSubcategory_activity);
        }
    }

    public int deleteSubCategoryActivityById(int aSubCategoryActivityId) {
        int deleteStatus = 0;
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
        } else {
            String sql = "DELETE FROM subcategory_activity WHERE subcategory_activity_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aSubCategoryActivityId);
                ps.executeUpdate();
                msg = "Subcategory Activity Deleted Successfully";
                deleteStatus = 1;
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                msg = "Subcategory Activity Not Deleted";
            }
        }
        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        return deleteStatus;
    }

    public Subcategory_activity getSubcategory_activity(int aSubcategory_activity_id) {
        String sql = "SELECT * FROM subcategory_activity WHERE subcategory_activity_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aSubcategory_activity_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Subcategory_activity obj = new Subcategory_activity();
                this.setSubcategory_activityFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Subcategory_activity> getSubcategory_activityByCategoryActivityId(int aCategory_activity_id) {
        String sql = "SELECT * FROM subcategory_activity WHERE category_activity_id=?";
        ResultSet rs;
        List<Subcategory_activity> Subcategory_activityList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aCategory_activity_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subcategory_activity subcategory_activity = new Subcategory_activity();
                this.setSubcategory_activityFromResultset(subcategory_activity, rs);
                Subcategory_activityList.add(subcategory_activity);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Subcategory_activityList;
    }

    public List<Subcategory_activity> getSubcategory_activityBySubcategory_activityName(String aSubcategory_activityName) {
        String sql;
        sql = "SELECT * FROM subcategory_activity WHERE subcategory_name LIKE CONCAT('%',?,'%')";
//        sql1 = "SELECT s.subcategory_activity_id, s.subcategory_name ,c.category_name FROM subcategory_activity s INNER JOIN category_activity c ON s.category_activity_id = c.category_activity_id";
        ResultSet rs;
        List<Subcategory_activity> Subcategory_activityList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSubcategory_activityName);

            rs = ps.executeQuery();
            while (rs.next()) {
                Subcategory_activity subcategory_activity = new Subcategory_activity();
                this.setSubcategory_activityFromResultset(subcategory_activity, rs);
//                subcategory_activity.setCategory_name(rs.getString("category_name"));
                Subcategory_activityList.add(subcategory_activity);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Subcategory_activityList;
    }

    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }

    /**
     * @return the SearchSubCategoryActivityName
     */
    public String getSearchSubCategoryActivityName() {
        return SearchSubCategoryActivityName;
    }

    /**
     * @param SearchSubCategoryActivityName the SearchSubCategoryActivityName to
     * set
     */
    public void setSearchSubCategoryActivityName(String SearchSubCategoryActivityName) {
        this.SearchSubCategoryActivityName = SearchSubCategoryActivityName;
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param ActionMessage the ActionMessage to set
     */
    public void setActionMessage(String ActionMessage) {
        this.ActionMessage = ActionMessage;
    }

    /**
     * @return the SelectedSubCategoryActivityId
     */
    public int getSelectedSubCategoryActivityId() {
        return SelectedSubCategoryActivityId;
    }

    /**
     * @param SelectedSubCategoryActivityId the SelectedSubCategoryActivityId to
     * set
     */
    public void setSelectedSubCategoryActivityId(int SelectedSubCategoryActivityId) {
        this.SelectedSubCategoryActivityId = SelectedSubCategoryActivityId;
    }
}
