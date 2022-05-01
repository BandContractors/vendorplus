package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.Subscription_category;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean
@SessionScoped
public class SubscriptionCategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(SubscriptionCategoryBean.class.getName());

    private List<Subscription_category> SubscriptionCategorys;
    private String ActionMessage = null;
    private Subscription_category SelectedSubscriptionCategory = null;
    private int SelectedSubscriptionCategoryId;
    private String SearchSubscriptionCategoryName = "";
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setSubscriptionCategoryFromResultset(Subscription_category aSubscription_category, ResultSet aResultSet) {
        try {
            try {
                aSubscription_category.setSubscription_category_id(aResultSet.getInt("subscription_category_id"));
            } catch (Exception e) {
                aSubscription_category.setSubscription_category_id(0);
            }
            try {
                aSubscription_category.setCategory_code(aResultSet.getString("category_code"));
            } catch (Exception e) {
                aSubscription_category.setCategory_code("");
            }
            try {
                aSubscription_category.setCategory_name(aResultSet.getString("category_name"));
            } catch (Exception e) {
                aSubscription_category.setCategory_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveSubscriptionCategory(Subscription_category aSubscription_category) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg;
        String sql = null;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (aSubscription_category.getSubscription_category_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aSubscription_category.getSubscription_category_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aSubscription_category.getCategory_name().length() <= 0 || aSubscription_category.getCategory_code().length() <= 0) {
            msg = "Subscription Category Name and Code Cannot be Empty";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aSubscription_category.getSubscription_category_id() == 0 && this.getSubscriptionCategoryByExactSubscriptionCategoryName(aSubscription_category.getCategory_name()).size() > 0) {
            msg = "Subscription Category Name Already Exist";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aSubscription_category.getSubscription_category_id() == 0 && this.getSubscriptionCategoryBySubscriptionCategoryCode(aSubscription_category.getCategory_code()).size() > 0) {
            msg = "Subscription Code Already Exist";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (aSubscription_category.getSubscription_category_id() == 0) {
                sql = "{call sp_insert_subscription_category(?,?)}";
            } else if (aSubscription_category.getSubscription_category_id() > 0) {
                sql = "{call sp_update_subscription_category(?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (aSubscription_category.getSubscription_category_id() == 0) {
                    cs.setString(1, aSubscription_category.getCategory_name());
                    cs.setString(2, aSubscription_category.getCategory_code());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearSubscriptionCategory(aSubscription_category);
                } else if (aSubscription_category.getSubscription_category_id() > 0) {
                    cs.setInt(1, aSubscription_category.getSubscription_category_id());
                    cs.setString(2, aSubscription_category.getCategory_name());
                    cs.setString(3, aSubscription_category.getCategory_code());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearSubscriptionCategory(aSubscription_category);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "SubscriptionCategory NOT saved"));
            }
        }

    }

    public Subscription_category getSubscriptionCategory(int aSubscriptionCategoryId) {
        String sql = "{call sp_search_subscription_category_by_id(?)}";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aSubscriptionCategoryId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Subscription_category subscription_category = new Subscription_category();
                subscription_category.setSubscription_category_id(rs.getInt("subscription_category_id"));
                subscription_category.setCategory_name(rs.getString("category_name"));
                subscription_category.setCategory_code(rs.getString("category_code"));
                return subscription_category;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deleteSubscriptionCategory() {
        this.deleteSubscriptionCategoryById(this.SelectedSubscriptionCategoryId);
    }

    public void deleteSubscriptionCategoryByObject(Subscription_category subscription_category) {
        this.deleteSubscriptionCategoryById(subscription_category.getSubscription_category_id());
    }

    public void deleteSubscriptionCategoryById(int SubscriptionCategoryId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "DELETE FROM subscription_category WHERE subscription_category_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, SubscriptionCategoryId);
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "SubscriptionCategory Not Deleted"));
            }
        }
    }

    public void displaySubscriptionCategory(Subscription_category SubscriptionCategoryFrom, Subscription_category SubscriptionCategoryTo) {
        try {
            this.clearSubscriptionCategory(SubscriptionCategoryTo);
            SubscriptionCategoryTo.setSubscription_category_id(SubscriptionCategoryFrom.getSubscription_category_id());
            SubscriptionCategoryTo.setCategory_name(SubscriptionCategoryFrom.getCategory_name());
            SubscriptionCategoryTo.setCategory_code(SubscriptionCategoryFrom.getCategory_code());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearSubscriptionCategory(Subscription_category subscription_category) {
        try {
            subscription_category.setSubscription_category_id(0);
            subscription_category.setCategory_name("");
            subscription_category.setCategory_code("");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Subscription_category> getSubscriptionCategory() {
        String sql;
        sql = "{call sp_search_subscription_category_by_none()}";
        ResultSet rs;
        SubscriptionCategorys = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription_category subscription_category = new Subscription_category();
                this.setSubscriptionCategoryFromResultset(subscription_category, rs);
                SubscriptionCategorys.add(subscription_category);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return SubscriptionCategorys;
    }

    /**
     * @param aSubscriptionCategoryName
     * @return the SubscriptionCategorys
     */
    public List<Subscription_category> getSubscriptionCategoryBySubscriptionCategoryName(String aSubscriptionCategoryName) {
        String sql;
        sql = "{call sp_search_subscription_category_by_name(?)}";
        ResultSet rs;
        SubscriptionCategorys = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSubscriptionCategoryName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription_category subscription_category = new Subscription_category();
                this.setSubscriptionCategoryFromResultset(subscription_category, rs);
                SubscriptionCategorys.add(subscription_category);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return SubscriptionCategorys;
    }

    public List<Subscription_category> getSubscriptionCategoryByExactSubscriptionCategoryName(String aSubscriptionCategoryName) {
        String sql;
        sql = "{call sp_search_subscription_category_by_exact_name(?)}";
        ResultSet rs;
        SubscriptionCategorys = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSubscriptionCategoryName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription_category subscription_category = new Subscription_category();
                this.setSubscriptionCategoryFromResultset(subscription_category, rs);
                SubscriptionCategorys.add(subscription_category);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return SubscriptionCategorys;
    }

    public List<Subscription_category> getSubscriptionCategoryBySubscriptionCategoryCode(String aSubscriptionCategoryCode) {
        String sql;
        sql = "{call sp_search_subscription_category_by_code(?)}";
        ResultSet rs;
        SubscriptionCategorys = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSubscriptionCategoryCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription_category subscription_category = new Subscription_category();
                this.setSubscriptionCategoryFromResultset(subscription_category, rs);
                SubscriptionCategorys.add(subscription_category);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return SubscriptionCategorys;
    }

    /**
     * @param SubscriptionCategorys the SubscriptionCategorys to set
     */
    public void setSubscriptionCategorys(List<Subscription_category> SubscriptionCategorys) {
        this.SubscriptionCategorys = SubscriptionCategorys;
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
     * @return the SelectedSubscriptionCategory
     */
    public Subscription_category getSelectedSubscriptionCategory() {
        return SelectedSubscriptionCategory;
    }

    /**
     * @param SelectedSubscriptionCategory the SelectedSubscriptionCategory to
     * set
     */
    public void setSelectedSubscriptionCategory(Subscription_category SelectedSubscriptionCategory) {
        this.SelectedSubscriptionCategory = SelectedSubscriptionCategory;
    }

    /**
     * @return the SelectedSubscriptionCategoryId
     */
    public int getSelectedSubscriptionCategoryId() {
        return SelectedSubscriptionCategoryId;
    }

    /**
     * @param SelectedSubscriptionCategoryId the SelectedSubscriptionCategoryId
     * to set
     */
    public void setSelectedSubscriptionCategoryId(int SelectedSubscriptionCategoryId) {
        this.SelectedSubscriptionCategoryId = SelectedSubscriptionCategoryId;
    }

    /**
     * @return the SearchSubscriptionCategoryName
     */
    public String getSearchSubscriptionCategoryName() {
        return SearchSubscriptionCategoryName;
    }

    /**
     * @param SearchSubscriptionCategoryName the SearchSubscriptionCategoryName
     * to set
     */
    public void setSearchSubscriptionCategoryName(String SearchSubscriptionCategoryName) {
        this.SearchSubscriptionCategoryName = SearchSubscriptionCategoryName;
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
