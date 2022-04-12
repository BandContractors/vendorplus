/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.Category_activity;
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
public class Category_activityBean {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Category_activityBean.class.getName());

    private String SearchCategoryActivityName = "";

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setCategory_activityFromResultset(Category_activity aCategory_activity, ResultSet aResultSet) {
        try {
            try {
                aCategory_activity.setCategory_activity_id(aResultSet.getInt("category_activity_id"));
            } catch (Exception e) {
                aCategory_activity.setCategory_activity_id(0);
            }
            try {
                String category_name = aResultSet.getString("category_name");
                if (null == category_name) {
                    aCategory_activity.setCategory_name("");
                } else {
                    aCategory_activity.setCategory_name(category_name);
                }
            } catch (Exception e) {
                aCategory_activity.setCategory_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveCategoryActivity(Category_activity aCategory_activity) {
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

            String sql1 = "SELECT count(*) as n FROM category_activity WHERE category_name='" + aCategory_activity.getCategory_name() + "'";

            if (aCategory_activity.getCategory_activity_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aCategory_activity.getCategory_activity_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aCategory_activity.getCategory_name().length() <= 0) {
                msg = "Activity Category Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aCategory_activity.getCategory_activity_id() == 0 && ub.getN(sql1) > 0) {
                msg = "Activity Category Already Exists ##: " + aCategory_activity.getCategory_name();
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aCategory_activity.getCategory_activity_id() == 0) {
                    saved = this.insertCategory_activity(aCategory_activity);
                } else if (aCategory_activity.getCategory_activity_id() > 0) {
                    saved = this.updateCategory_activity(aCategory_activity);
                }
                if (saved > 0) {
                    msg = "Activity Category Saved Successfully";
                    this.clearCategory_activity(aCategory_activity);
                } else {
                    msg = "Activity Category NOT Saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertCategory_activity(Category_activity aCategory_activity) {
        int InsertedId = 0;
        String sql = "INSERT INTO category_activity(category_name) VALUES(?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aCategory_activity.getCategory_name());
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

    public int updateCategory_activity(Category_activity aCategory_activity) {
        int IsUpdated = 0;
        String sql = "UPDATE category_activity SET category_name=? WHERE category_activity_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCategory_activity.getCategory_name());
            ps.setInt(2, aCategory_activity.getCategory_activity_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteCategory_activity(Category_activity aCategory_activity) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg;
        int IsDeleted = 0;
        long N = 0;
        try {
            String sqlFind = "SELECT COUNT(*) AS n FROM timesheet WHERE category_activity_id=" + aCategory_activity.getCategory_activity_id();
            N = N + new UtilityBean().getN(sqlFind);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        try {
            String sqlFind = "SELECT COUNT(*) AS n FROM subcategory_activity WHERE category_activity_id=" + aCategory_activity.getCategory_activity_id();
            N = N + new UtilityBean().getN(sqlFind);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        if (N > 0) {
            msg = "Activity Category has been used and cannot be deleted";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "DELETE FROM category_activity WHERE category_activity_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aCategory_activity.getCategory_activity_id());
                ps.executeUpdate();
                IsDeleted = 1;
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        return IsDeleted;
    }

    public Category_activity getCategory_activity(int aCategory_activity_id) {
        String sql = "SELECT * FROM category_activity WHERE category_activity_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aCategory_activity_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Category_activity obj = new Category_activity();
                this.setCategory_activityFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void displayCategory_activity(Category_activity Category_activityFrom, Category_activity Category_activityTo) {
        try {
            this.clearCategory_activity(Category_activityTo);
            Category_activityTo.setCategory_activity_id(Category_activityFrom.getCategory_activity_id());
            Category_activityTo.setCategory_name(Category_activityFrom.getCategory_name());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearCategory_activity(Category_activity aCategory_activity) {
        try {
            if (null != aCategory_activity) {
                aCategory_activity.setCategory_activity_id(0);
                aCategory_activity.setCategory_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Category_activity> getCategory_activities() {
        String sql = "SELECT * FROM category_activity";
        ResultSet rs;
        List<Category_activity> maList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Category_activity obj = new Category_activity();
                this.setCategory_activityFromResultset(obj, rs);
                maList.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return maList;
    }

    public List<Category_activity> getCategory_activityByCategory_activityName(String aCategory_activityName) {
        String sql;
        sql = "SELECT * FROM category_activity WHERE category_name LIKE CONCAT('%',?,'%')";
        ResultSet rs;
        List<Category_activity> Category_activityList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCategory_activityName);

            rs = ps.executeQuery();
            while (rs.next()) {
                Category_activity category_activity = new Category_activity();
                this.setCategory_activityFromResultset(category_activity, rs);
                Category_activityList.add(category_activity);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Category_activityList;
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
     * @return the SearchCategoryActivityName
     */
    public String getSearchCategoryActivityName() {
        return SearchCategoryActivityName;
    }

    /**
     * @param SearchCategoryActivityName the SearchCategoryActivityName to set
     */
    public void setSearchCategoryActivityName(String SearchCategoryActivityName) {
        this.SearchCategoryActivityName = SearchCategoryActivityName;
    }
}
