package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.CompanySetting;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.*;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;

@ManagedBean
@SessionScoped
public class CompanySettingBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(CompanySettingBean.class.getName());
    private String ActionMessage;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void saveCompanySetting(CompanySetting company) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = null;

        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            sql = "{call sp_update_company_setting(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                cs.setString("in_ECompanyName", new CompanySetting().getLICENSE_CLIENT_NAME());
                cs.setString("in_EPhysicalAddress", company.getEPhysicalAddress());
                cs.setString("in_EPhone", company.getEPhone());
                cs.setString("in_EFax", company.getEFax());
                cs.setString("in_EEmail", company.getEEmail());
                cs.setString("in_EWebsite", company.getEWebsite());
                cs.setString("in_ELogoUrl", company.getELogoUrl());
                cs.setString("in_ESloghan", company.getESloghan());
                cs.setString("in_ECurrencyUsed", company.getECurrencyUsed());
                cs.setDouble("in_EVatPerc", company.getEVatPerc());
                cs.setString("in_EIsAllowDiscount", company.getEIsAllowDiscount());
                cs.setString("in_EIsAllowDebt", company.getEIsAllowDebt());
                cs.setString("in_EIsCustomerMandatory", company.getEIsCustomerMandatory());
                cs.setString("in_EIsSupplierMandatory", company.getEIsSupplierMandatory());
                cs.setString("in_EIsVatInclusive", company.getEIsVatInclusive());
                cs.setString("in_EIsTradeDiscountVatLiable", company.getEIsTradeDiscountVatLiable());
                cs.setString("in_EIsCashDiscountVatLiable", company.getEIsCashDiscountVatLiable());
                cs.setString("in_EIsMapItemsActive", company.getEIsMapItemsActive());
                cs.setString("in_EBranchCode", company.getEBranchCode());
                cs.setInt("in_EBranchId", company.getEBranchId());
                cs.setDouble("in_EAwardAmountPerPoint", company.getEAwardAmountPerPoint());
                cs.setDouble("in_ESpendAmountPerPoint", company.getESpendAmountPerPoint());
                cs.setString("in_ETaxIdentity", company.getETaxIdentity());
                cs.setString("in_ESalesReceiptName", company.getESalesReceiptName());
                cs.setString("in_EIsShowDeveloper", company.getEIsShowDeveloper());
                cs.setString("in_EDeveloperEmail", company.getEDeveloperEmail());
                cs.setString("in_EDeveloperPhone", company.getEDeveloperPhone());
                cs.setString("in_EShowLogoInvoice", company.getEShowLogoInvoice());
                cs.setString("in_EShowBranchInvoice", company.getEShowBranchInvoice());
                cs.setString("in_EShowStoreInvoice", company.getEShowStoreInvoice());
                cs.setString("in_EIsAllowAutoUnpack", company.getEIsAllowAutoUnpack());
                cs.setString("in_ETimeZone", company.getETimeZone());
                cs.setString("in_EDateFormat", company.getEDateFormat());
                cs.setString("in_ELicenseKey", company.getELicenseKey());
                cs.setInt("in_ESalesReceiptVersion", company.getESalesReceiptVersion());
                cs.setString("in_EEnforceTransUserSelect", company.getEEnforceTransUserSelect());
                cs.setString("in_EShowVatAnalysisInvoice", company.getEShowVatAnalysisInvoice());
                cs.setString("in_EStoreEquivName", company.getEStoreEquivName());
                cs.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Updated Successfully"));
                CompanySetting.RefreshStaticCompanySettings();
            } catch (SQLException | NullPointerException e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Company Setting Not Updated"));
            }
        }
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param aActionMessage the ActionMessage to set
     */
    public void setActionMessage(String aActionMessage) {
        ActionMessage = aActionMessage;
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
