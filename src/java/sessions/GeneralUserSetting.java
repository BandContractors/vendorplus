package sessions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import beans.OutputDetail;
import entities.AccCurrency;
import entities.Alert_general;
import entities.CompanySetting;
import entities.Store;
import entities.GroupRight;
import entities.Trans;
import entities.UserDetail;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author btwesigye
 */
@ManagedBean
@SessionScoped
public class GeneralUserSetting implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(GeneralUserSetting.class.getName());
    private boolean ReportRetrievalOption;

    /**
     * Creates a new instance of GeneralUserSetting
     */
    public GeneralUserSetting() {
    }

    public void setSessionCurrentPayId(long aPayId) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("CURRENT_PAY_ID", aPayId);
    }

    public UserDetail getCurrentUser() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (UserDetail) httpSession.getAttribute("CURRENT_USER");
        } catch (NullPointerException | ClassCastException npe) {
            return null;
        }
    }

    public String getCurrentUserNames() {
        try {
            UserDetail ud = new UserDetail();
            ud = this.getCurrentUser();
            return ud.getFirstName() + " " + ud.getSecondName() + " " + ud.getThirdName();
        } catch (NullPointerException | ClassCastException npe) {
            return "";
        }
    }

    public Store getCurrentStore() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (Store) httpSession.getAttribute("CURRENT_STORE");
        } catch (NullPointerException | ClassCastException npe) {
            return null;
        }
    }

    public Store getCurrentStoreChild() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (Store) httpSession.getAttribute("CURRENT_STORE_CHILD");
        } catch (NullPointerException | ClassCastException npe) {
            return null;
        }
    }

    public int getCurrentStore2Id() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("CURRENT_STORE2_ID");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public int getCurrentStore2IdChild() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("CURRENT_STORE2_ID_CHILD");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public List<GroupRight> getCurrentGroupRights() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (List<GroupRight>) httpSession.getAttribute("CURRENT_GROUP_RIGHTS");
        } catch (NullPointerException | ClassCastException npe) {
            return null;
        }
    }

    public int getIsApprovePointsNeeded() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("IS_APPROVE_POINTS_NEEDED");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public int getIsApproveDiscountNeeded() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("IS_APPROVE_DISCOUNT_NEEDED");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public int getIsAllowedToBackDate() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("IS_ALLOWED_TO_BACKDATE");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public int getCurrentTransactionTypeId() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("TRANSACTION_TYPE_ID");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public int getCurrentTransactionReasonId() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("TRANSACTION_REASON_ID");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public String getCurrentTransactionReasonIdStr() {
        String IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = Integer.toString((int) httpSession.getAttribute("TRANSACTION_REASON_ID"));
        } catch (NullPointerException npe) {
            IntResponse = "";
        }
        return IntResponse;
    }

    public String getCurrentTransactionTypeName() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("TRANSACTION_TYPE_NAME");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentTransactionReasonName() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("TRANSACTION_REASON_NAME");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentTransactorType() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("TRANSACTOR_TYPE");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentInvokeMode() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("INVOKE_MODE");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentTransactorLabel() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("TRANSACTOR_LABEL");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentTransactionNumberLabel() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("TRANSACTION_NUMBER_LABEL");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentTransactionOutputLabel() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("TRANSACTION_OUTPUT_LABEL");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentPayCategory() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("PAY_CATEGORY");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentSaleType() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("SALE_TYPE");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public int getCurrentApproveUserId() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("APPROVE_USER_ID");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public String getCurrentApproveDiscountStatus() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("APPROVE_DISCOUNT_STATUS");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentApprovePointsStatus() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("APPROVE_POINTS_STATUS");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getCurrentPrintoutJsfFile() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("CURRENT_PRINT_OUT_JSF_FILE");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public int getChangePasswordAllowed() {
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("CHANGE_PASSWORD_ALLOWED");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public long getCurrentTransactionId() {
        long LongResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LongResponse = (long) httpSession.getAttribute("CURRENT_TRANSACTION_ID");
        } catch (NullPointerException | ClassCastException npe) {
            LongResponse = 0;
        }
        return LongResponse;
    }

    public String getCurrentTransactionNumber() {
        String resp;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            resp = (String) httpSession.getAttribute("CURRENT_TRANSACTION_NUMBER");
        } catch (NullPointerException | ClassCastException npe) {
            resp = "";
        }
        return resp;
    }

    public long getCurrentTransactionIdChild() {
        long LongResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LongResponse = (long) httpSession.getAttribute("CURRENT_TRANSACTION_ID_CHILD");
        } catch (NullPointerException | ClassCastException npe) {
            LongResponse = 0;
        }
        return LongResponse;
    }

    public String getCurrentTransactionAction() {
        String LongResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LongResponse = (String) httpSession.getAttribute("CURRENT_TRANSACTION_ACTION");
        } catch (NullPointerException | ClassCastException npe) {
            LongResponse = "";
        }
        return LongResponse;
    }

    public String getCurrentItemPurpose() {
        String StringResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StringResponse = (String) httpSession.getAttribute("ITEM_PURPOSE");
            if (null == StringResponse) {
                StringResponse = "";
            }
        } catch (NullPointerException | ClassCastException npe) {
            StringResponse = "";
        }
        return StringResponse;
    }

    public int getIsInited() {
        int LongResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LongResponse = (int) httpSession.getAttribute("IS_INITED");
        } catch (NullPointerException | ClassCastException npe) {
            LongResponse = 0;
        }
        return LongResponse;
    }

    public long getCurrentPayId() {
        long LongResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LongResponse = (long) httpSession.getAttribute("CURRENT_PAY_ID");
        } catch (NullPointerException | ClassCastException npe) {
            LongResponse = 0;
        }
        return LongResponse;
    }

    public long getCurrentPayIdChild() {
        long LongResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LongResponse = (long) httpSession.getAttribute("CURRENT_PAY_ID_CHILD");
        } catch (NullPointerException | ClassCastException npe) {
            LongResponse = 0;
        }
        return LongResponse;
    }

    public long getCurrentItemId() {
        long LongResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LongResponse = (long) httpSession.getAttribute("ITEM_ID");
        } catch (NullPointerException | ClassCastException npe) {
            LongResponse = 0;
        }
        return LongResponse;
    }

    /**
     * @return the ReportRetrievalOption
     */
    public boolean isReportRetrievalOption() {
        return ReportRetrievalOption;
    }

    /**
     * @param ReportRetrievalOption the ReportRetrievalOption to set
     */
    public void setReportRetrievalOption(boolean ReportRetrievalOption) {
        this.ReportRetrievalOption = ReportRetrievalOption;
    }

    public void initReportRetrievalOption(boolean ReportRetrievalOption) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.ReportRetrievalOption = ReportRetrievalOption;
        }
    }

    public int getLoginType() {//1=Branch, 2=InterBranch
        int IntResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            IntResponse = (int) httpSession.getAttribute("LOGIN_TYPE");
        } catch (NullPointerException npe) {
            IntResponse = 0;
        }
        return IntResponse;
    }

    public long getDaysFromDateToLicenseExpiryDate(Date aFromDate) {
        Date d1 = null;
        Date d2 = null;
        long diffDays = 0;
        try {
            d2 = aFromDate;
            d1 = CompanySetting.getLICENSE_EXPIRY_DATE();
            //in milliseconds
            long diff = d1.getTime() - d2.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
            diffDays = diffDays + 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return diffDays;
    }

    public int getHIRE_MODULE_ON() {
        int HIRE_MODULE_ON = 0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            HIRE_MODULE_ON = (int) httpSession.getAttribute("HIRE_MODULE_ON");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            HIRE_MODULE_ON = 0;
        }
        return HIRE_MODULE_ON;
    }

    public String getDEFAULT_CURRENCY_CODE() {
        String DEFAULT_CURRENCY_CODE = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            DEFAULT_CURRENCY_CODE = (String) httpSession.getAttribute("DEFAULT_CURRENCY_CODE");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            DEFAULT_CURRENCY_CODE = "";
        }
        return DEFAULT_CURRENCY_CODE;
    }

    public String getITEM_IMAGE_BASE_URL() {
        String ITEM_IMAGE_BASE_URL = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            ITEM_IMAGE_BASE_URL = (String) httpSession.getAttribute("ITEM_IMAGE_BASE_URL");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            ITEM_IMAGE_BASE_URL = "";
        }
        return ITEM_IMAGE_BASE_URL;
    }

    public String getITEM_IMAGE_LOCAL_LOCATION() {
        String ITEM_IMAGE_LOCAL_LOCATION = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            ITEM_IMAGE_LOCAL_LOCATION = (String) httpSession.getAttribute("ITEM_IMAGE_LOCAL_LOCATION");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            ITEM_IMAGE_LOCAL_LOCATION = "";
        }
        return ITEM_IMAGE_LOCAL_LOCATION;
    }

    public String getDEFAULT_DURATION_TYPE() {
        String DEFAULT_DURATION_TYPE = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            DEFAULT_DURATION_TYPE = (String) httpSession.getAttribute("DEFAULT_DURATION_TYPE");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            DEFAULT_DURATION_TYPE = "";
        }
        return DEFAULT_DURATION_TYPE;
    }

    public String getDEPLETE_SOLD_STOCK_UPON() {
        String DEPLETE_SOLD_STOCK_UPON = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            DEPLETE_SOLD_STOCK_UPON = (String) httpSession.getAttribute("DEPLETE_SOLD_STOCK_UPON");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            DEPLETE_SOLD_STOCK_UPON = "";
        }
        return DEPLETE_SOLD_STOCK_UPON;
    }

    public OutputDetail getOutputDetailParent() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (OutputDetail) httpSession.getAttribute("OUTPUT_DETAIL_PARENT");
        } catch (NullPointerException | ClassCastException npe) {
            return null;
        }
    }

    public OutputDetail getOutputDetailChild() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (OutputDetail) httpSession.getAttribute("OUTPUT_DETAIL_CHILD");
        } catch (NullPointerException | ClassCastException npe) {
            return null;
        }
    }

    public String getTransactorType() {
        String StrResponse;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            StrResponse = (String) httpSession.getAttribute("TRANSACTOR_TYPE");
        } catch (NullPointerException npe) {
            StrResponse = "";
        }
        return StrResponse;
    }

    public String getLOCALE_COUNTRY_CODE() {
        String LOCALE_COUNTRY_CODE = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LOCALE_COUNTRY_CODE = (String) httpSession.getAttribute("LOCALE_COUNTRY_CODE");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            LOCALE_COUNTRY_CODE = "";
        }
        return LOCALE_COUNTRY_CODE;
    }

    public String getLOCALE_LANGUAGE_CODE() {
        String LOCALE_LANGUAGE_CODE = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LOCALE_LANGUAGE_CODE = (String) httpSession.getAttribute("LOCALE_LANGUAGE_CODE");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            LOCALE_LANGUAGE_CODE = "";
        }
        return LOCALE_LANGUAGE_CODE;
    }

    public String getLOCALE_COUNT_LANG_CODE() {
        String LOCALE_COUNT_LANG_CODE = "";
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LOCALE_COUNT_LANG_CODE = (String) httpSession.getAttribute("LOCALE_COUNT_LANG_CODE");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {
            LOCALE_COUNT_LANG_CODE = "";
        }
        return LOCALE_COUNT_LANG_CODE;
    }

    public AccCurrency getLOCAL_CURRENCY() {
        AccCurrency LOCAL_CURRENCY = null;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            LOCAL_CURRENCY = (AccCurrency) httpSession.getAttribute("LOCAL_CURRENCY");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {

        }
        return LOCAL_CURRENCY;
    }

    public Trans getORDER_FOR_EDIT() {
        Trans ORDER_FOR_EDIT = null;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            ORDER_FOR_EDIT = (Trans) httpSession.getAttribute("ORDER_FOR_EDIT");
        } catch (NullPointerException | ClassCastException | NumberFormatException npe) {

        }
        return ORDER_FOR_EDIT;
    }

    public List<Alert_general> getUserUnreadStockAlertsList() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (List<Alert_general>) httpSession.getAttribute("USER_UNREAD_STOCK_ALERTS_LIST");
        } catch (NullPointerException | ClassCastException npe) {
            return null;
        }
    }

    public long getUserUnreadStockAlertsCount() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (long) httpSession.getAttribute("USER_UNREAD_STOCK_ALERTS_COUNT");
        } catch (NullPointerException | ClassCastException npe) {
            return 0;
        }
    }

    public String getClientComputerName() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            return (String) httpSession.getAttribute("CLIENT_COMPUTER_NAME");
        } catch (NullPointerException | ClassCastException npe) {
            return "";
        }
    }

}
