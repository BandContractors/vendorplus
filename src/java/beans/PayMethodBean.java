package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.UserDetail;
import entities.PayMethod;
import entities.Trans;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;

@ManagedBean
@SessionScoped
public class PayMethodBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(PayMethodBean.class.getName());
    private List<PayMethod> PayMethods;
    private String ActionMessage;
    private PayMethod SelectedPayMethod = null;
    private int SelectedPayMethodId;
    private String SearchPayMethodName = "";
    private List<PayMethod> PayMethodsList;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setPayMethodFromResultset(PayMethod aPayMethod, ResultSet aResultSet) {
        try {
            try {
                aPayMethod.setPayMethodId(aResultSet.getInt("pay_method_id"));
            } catch (NullPointerException npe) {
                aPayMethod.setPayMethodId(0);
            }
            try {
                aPayMethod.setPayMethodName(aResultSet.getString("pay_method_name"));
            } catch (NullPointerException npe) {
                aPayMethod.setPayMethodName("");
            }
            try {
                aPayMethod.setDisplayOrder(aResultSet.getInt("display_order"));
            } catch (NullPointerException npe) {
                aPayMethod.setDisplayOrder(0);
            }
            try {
                aPayMethod.setIsDefault(aResultSet.getInt("is_default"));
            } catch (NullPointerException npe) {
                aPayMethod.setIsDefault(0);
            }
            try {
                aPayMethod.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                aPayMethod.setIsActive(0);
            }
            try {
                aPayMethod.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                aPayMethod.setIsDeleted(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
    }

    public void savePayMethod(PayMethod pm) {
        UtilityBean ub = new UtilityBean();
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        String sql = null;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (pm.getPayMethodId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (pm.getPayMethodId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (pm.getPayMethodId() == 0) {
                sql = "{call sp_insert_pay_method(?,?,?,?,?)}";
            } else if (pm.getPayMethodId() > 0) {
                sql = "{call sp_update_pay_method(?,?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (pm.getPayMethodId() == 0) {
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Adding New Payment Method is Not Supported Currently"));
                } else if (pm.getPayMethodId() > 0) {
                    cs.setInt("in_pay_method_id", pm.getPayMethodId());
                    cs.setString("in_pay_method_name", pm.getPayMethodName());
                    cs.setInt("in_display_order", pm.getDisplayOrder());
                    cs.setInt("in_is_default", pm.getIsDefault());
                    cs.setInt("in_is_active", pm.getIsActive());
                    cs.setInt("in_is_deleted", pm.getIsDeleted());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);//(se.getMessage());
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Method Not Saved"));
            }
        }
    }

    public PayMethod getPayMethod(int aPayMethodId) {
        String sql = "{call sp_search_pay_method_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aPayMethodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                return pm;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
            return null;
        }
    }

    public PayMethod getPayMethodDefault() {
        String sql = "{call sp_search_pay_method_default()}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                return pm;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
            return null;
        }
    }

    public void deletePayMethod() {
        this.deletePayMethodById(this.SelectedPayMethodId);
    }

    public void deletePayMethodByObject(PayMethod pm) {
        this.deletePayMethodById(pm.getPayMethodId());
    }

    public void deletePayMethodById(int PMId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        String sql = "DELETE FROM pay_method WHERE pay_method_id=?";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, PMId);
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);//(se.getMessage());
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Method Not Deleted"));
            }
        }
    }

    public void displayPayMethod(PayMethod PmFrom, PayMethod PmTo) {
        PmTo.setPayMethodId(PmFrom.getPayMethodId());
        PmTo.setPayMethodName(PmFrom.getPayMethodName());
        PmTo.setIsDefault(PmFrom.getIsDefault());
        PmTo.setIsDeleted(PmFrom.getIsDeleted());
        PmTo.setIsActive(PmFrom.getIsActive());
        PmTo.setDisplayOrder(PmFrom.getDisplayOrder());
    }

    public void clearPayMethod(PayMethod Cat) {
        Cat.setPayMethodId(0);
        Cat.setPayMethodName("");
        Cat.setIsActive(0);
        Cat.setIsDeleted(0);
        Cat.setDisplayOrder(0);
        Cat.setIsDefault(1);
    }

    public List<PayMethod> getPayMethods() {
        String sql;
        sql = "{call sp_search_pay_method_by_none()}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    public void refreshPayMethodsActive() {
        this.PayMethods = new PayMethodBean().getPayMethodsActive();
    }

    public void refreshPayMethodsPayReport() {
        String sql;
        sql = "select * from pay_method where pay_method_id!=6 and pay_method_id!=7";
        ResultSet rs = null;
        try {
            this.PayMethodsList.clear();
        } catch (Exception e) {
            this.PayMethodsList = new ArrayList<>();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                this.PayMethodsList.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//("refreshPayMethodsPayReport:" + se.getMessage());
        }
    }

    public void refreshPayMethodsPayReport(List<PayMethod> aPayMethods) {
        String sql;
        sql = "select * from pay_method where pay_method_id!=6 and pay_method_id!=7";
        ResultSet rs = null;
        try {
            aPayMethods.clear();
        } catch (Exception e) {
            aPayMethods = new ArrayList<>();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                aPayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//("refreshPayMethodsPayReport2:" + se.getMessage());
        }
    }

    public List<PayMethod> getPayMethodsActive() {
        String sql;
        sql = "{call sp_search_pay_method_active()}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    public List<PayMethod> getPayMethodsActiveForSales() {
        String sql;
        sql = "{call sp_search_pay_method_active_sales()}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    public List<PayMethod> getPayMethodsActiveIn(String aPayMethodIDs) {
        String sql;
        sql = "{call sp_search_pay_method_active_in(?)}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aPayMethodIDs);
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    public List<PayMethod> getPayMethodsActiveInAssign(String aPayMethodIDs, Trans aTrans) {
        String sql;
        sql = "{call sp_search_pay_method_active_in(?)}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aPayMethodIDs);
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
            try {
                aTrans.setPayMethod(PayMethods.get(0).getPayMethodId());
            } catch (NullPointerException npe) {
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    public void refreshPayMethodsActiveIn(String aPayMethodIDs) {
        String sql;
        sql = "{call sp_search_pay_method_active_in(?)}";
        ResultSet rs = null;
        try {
            this.PayMethods.clear();
        } catch (Exception e) {
            this.PayMethods = new ArrayList<>();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aPayMethodIDs);
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                this.PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
    }

    public List<PayMethod> getPayMethodsActiveForPurchases() {
        String sql;
        sql = "{call sp_search_pay_method_active_purchases()}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    public List<PayMethod> getPayMethodsActiveNoPrepaid() {
        String sql;
        sql = "{call sp_search_pay_method_active_no_prepaid()}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    /**
     * @param aPayMethodName
     * @return the PayMethods
     */
    public List<PayMethod> getPayMethodsByPayMethodName(String aPayMethodName) {
        String sql;
        sql = "{call sp_search_pay_method_by_name(?)}";
        ResultSet rs = null;
        PayMethods = new ArrayList<PayMethod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aPayMethodName);
            rs = ps.executeQuery();
            while (rs.next()) {
                PayMethod pm = new PayMethod();
                this.setPayMethodFromResultset(pm, rs);
                PayMethods.add(pm);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return PayMethods;
    }

    /**
     * @param PayMethods the PayMethods to set
     */
    public void setPayMethods(List<PayMethod> PayMethods) {
        this.PayMethods = PayMethods;
    }

    /**
     * @return the SelectedPayMethod
     */
    public PayMethod getSelectedPayMethod() {
        return SelectedPayMethod;
    }

    /**
     * @param SelectedPayMethod the SelectedPayMethod to set
     */
    public void setSelectedPayMethod(PayMethod SelectedPayMethod) {
        this.SelectedPayMethod = SelectedPayMethod;
    }

    /**
     * @return the SelectedPayMethodId
     */
    public int getSelectedPayMethodId() {
        return SelectedPayMethodId;
    }

    /**
     * @param SelectedPayMethodId the SelectedPayMethodId to set
     */
    public void setSelectedPayMethodId(int SelectedPayMethodId) {
        this.SelectedPayMethodId = SelectedPayMethodId;
    }

    /**
     * @return the SearchPayMethodName
     */
    public String getSearchPayMethodName() {
        return SearchPayMethodName;
    }

    /**
     * @param SearchPayMethodName the SearchPayMethodName to set
     */
    public void setSearchPayMethodName(String SearchPayMethodName) {
        this.SearchPayMethodName = SearchPayMethodName;
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
     * @return the PayMethodsList
     */
    public List<PayMethod> getPayMethodsList() {
        return PayMethodsList;
    }

    /**
     * @param PayMethodsList the PayMethodsList to set
     */
    public void setPayMethodsList(List<PayMethod> PayMethodsList) {
        this.PayMethodsList = PayMethodsList;
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
