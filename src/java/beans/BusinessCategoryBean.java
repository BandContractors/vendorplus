package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.Business_category;
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
public class BusinessCategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(BusinessCategoryBean.class.getName());

    private List<Business_category> BusinessCategorys;
    private String ActionMessage = null;
    private Business_category SelectedBusinessCategory = null;
    private int SelectedBusinessCategoryId;
    private String SearchBusinessCategoryName = "";
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setBusinessCategoryFromResultset(Business_category aBusiness_category, ResultSet aResultSet) {
        try {
            try {
                aBusiness_category.setBusiness_category_id(aResultSet.getInt("business_category_id"));
            } catch (Exception e) {
                aBusiness_category.setBusiness_category_id(0);
            }
            try {
                aBusiness_category.setCategory_name(aResultSet.getString("category_name"));
            } catch (Exception e) {
                aBusiness_category.setCategory_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveBusinessCategory(Business_category aBusiness_category) {
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

        if (aBusiness_category.getBusiness_category_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aBusiness_category.getBusiness_category_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aBusiness_category.getCategory_name().length() <= 0) {
            msg = "Business Category Name Cannot be Empty";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (aBusiness_category.getBusiness_category_id() == 0 && this.getBusinessCategoryByExacttBusinessCategoryName(aBusiness_category.getCategory_name()).size() > 0) {
            msg = "Business Category Name Already Exist";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (aBusiness_category.getBusiness_category_id() == 0) {
                sql = "{call sp_insert_business_category(?)}";
            } else if (aBusiness_category.getBusiness_category_id() > 0) {
                sql = "{call sp_update_business_category(?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (aBusiness_category.getBusiness_category_id() == 0) {
                    cs.setString(1, aBusiness_category.getCategory_name());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearBusinessCategory(aBusiness_category);
                } else if (aBusiness_category.getBusiness_category_id() > 0) {
                    cs.setInt(1, aBusiness_category.getBusiness_category_id());
                    cs.setString(2, aBusiness_category.getCategory_name());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearBusinessCategory(aBusiness_category);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "BusinessCategory NOT saved"));
            }
        }

    }

    public Business_category getBusinessCategory(int aBusinessCategoryId) {
        String sql = "{call sp_search_business_category_by_id(?)}";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aBusinessCategoryId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Business_category business_category = new Business_category();
                business_category.setBusiness_category_id(rs.getInt("business_category_id"));
                business_category.setCategory_name(rs.getString("category_name"));
                return business_category;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deleteBusinessCategory() {
        this.deleteBusinessCategoryById(this.SelectedBusinessCategoryId);
    }

    public void deleteBusinessCategoryByObject(Business_category business_category) {
        this.deleteBusinessCategoryById(business_category.getBusiness_category_id());
        this.clearBusinessCategory(business_category);
    }

    public void deleteBusinessCategoryById(int BusinessCategoryId) {
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

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "DELETE FROM business_category WHERE business_category_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, BusinessCategoryId);
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "BusinessCategory Not Deleted"));
            }
        }
    }

    public void displayBusinessCategory(Business_category BusinessCategoryFrom, Business_category BusinessCategoryTo) {
        try {
            this.clearBusinessCategory(BusinessCategoryTo);
            BusinessCategoryTo.setBusiness_category_id(BusinessCategoryFrom.getBusiness_category_id());
            BusinessCategoryTo.setCategory_name(BusinessCategoryFrom.getCategory_name());
        } catch (Exception e) {
            System.out.println("displayBusinessCategory-a:" + e.getMessage());
        }
    }

    public void clearBusinessCategory(Business_category business_category) {
        business_category.setBusiness_category_id(0);
        business_category.setCategory_name("");
    }

    public List<Business_category> getBusinessCategory() {
        String sql;
        sql = "{call sp_search_business_category_by_none()}";
        ResultSet rs;
        BusinessCategorys = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Business_category business_category = new Business_category();
                this.setBusinessCategoryFromResultset(business_category, rs);
                BusinessCategorys.add(business_category);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return BusinessCategorys;
    }

    /**
     * @param aBusinessCategoryName
     * @return the BusinessCategorys
     */
    public List<Business_category> getBusinessCategoryByBusinessCategoryName(String aBusinessCategoryName) {
        String sql;
        sql = "{call sp_search_business_category_by_name(?)}";
        ResultSet rs;
        BusinessCategorys = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aBusinessCategoryName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Business_category business_category = new Business_category();
                this.setBusinessCategoryFromResultset(business_category, rs);
                BusinessCategorys.add(business_category);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return BusinessCategorys;
    }
    
    public List<Business_category> getBusinessCategoryByExacttBusinessCategoryName(String aBusinessCategoryName) {
        String sql;
        sql = "{call sp_search_business_category_by_exact_name(?)}";
        ResultSet rs;
        BusinessCategorys = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aBusinessCategoryName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Business_category business_category = new Business_category();
                this.setBusinessCategoryFromResultset(business_category, rs);
                BusinessCategorys.add(business_category);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return BusinessCategorys;
    }

    /**
     * @param BusinessCategorys the BusinessCategorys to set
     */
    public void setBusinessCategorys(List<Business_category> BusinessCategorys) {
        this.BusinessCategorys = BusinessCategorys;
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
     * @return the SelectedBusinessCategory
     */
    public Business_category getSelectedBusinessCategory() {
        return SelectedBusinessCategory;
    }

    /**
     * @param SelectedBusinessCategory the SelectedBusinessCategory to set
     */
    public void setSelectedBusinessCategory(Business_category SelectedBusinessCategory) {
        this.SelectedBusinessCategory = SelectedBusinessCategory;
    }

    /**
     * @return the SelectedBusinessCategoryId
     */
    public int getSelectedBusinessCategoryId() {
        return SelectedBusinessCategoryId;
    }

    /**
     * @param SelectedBusinessCategoryId the SelectedBusinessCategoryId to set
     */
    public void setSelectedBusinessCategoryId(int SelectedBusinessCategoryId) {
        this.SelectedBusinessCategoryId = SelectedBusinessCategoryId;
    }

    /**
     * @return the SearchBusinessCategoryName
     */
    public String getSearchBusinessCategoryName() {
        return SearchBusinessCategoryName;
    }

    /**
     * @param SearchBusinessCategoryName the SearchBusinessCategoryName to set
     */
    public void setSearchBusinessCategoryName(String SearchBusinessCategoryName) {
        this.SearchBusinessCategoryName = SearchBusinessCategoryName;
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
