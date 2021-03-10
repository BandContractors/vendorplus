package beans;

import connections.DBConnection;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.Store;
import entities.LoginSession;
import entities.UserDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessageSuccess = null;
    private String ActionMessageFailure = null;
    private UserDetail LoggedInUserDetail = null;
    private String LoggedInUserName;
    private String LoggedInPassword;
    private int LoggedInStoreId;
    private String TransactorType;
    private List<Store> StoresList;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private long LICENSE_DAYS_LEFT;
    private long LICENSE_TYPE;

    public void refreshLicenseDaysLeft() {
        this.setLICENSE_DAYS_LEFT(CompanySetting.getLicenseDaysLeft());
        this.setLICENSE_TYPE(CompanySetting.getLicenseType());
    }

    public void confirmUser() {
        if (new DBConnection().isMySQLConnectionAvailable().equals("ON")) {
            UserDetailBean udb = new UserDetailBean();
            this.setLoggedInUserDetail(udb.getUserDetailByUserName(this.LoggedInUserName));
            if (this.LoggedInUserDetail != null && LoggedInPassword.equals(this.LoggedInUserDetail.getUserPassword())) {
                //it means username and password are valid
                this.refreshStoresList();
                this.ActionMessageSuccess = "Select Store and Click Login to proceed...";
                this.ActionMessageFailure = "";
            } else {
                this.LoggedInUserDetail = null;
                this.ActionMessageSuccess = "";
                this.ActionMessageFailure = "Invalid Username and/or Password...";
            }
        } else {
            this.ActionMessageSuccess = "";
            this.ActionMessageFailure = "Branch database connection is off, contact systems administrator please...";
        }
    }

    public void refreshStoresList() {
        try {
            this.StoresList.clear();
        } catch (NullPointerException npe) {
            this.StoresList = new ArrayList<>();
        }
        if (this.LoggedInUserDetail != null) {
            if ("Yes".equals(this.LoggedInUserDetail.getIsUserGenAdmin())) {
                this.StoresList = new StoreBean().getStoresAll();
            } else {
                this.StoresList = new StoreBean().getStoresByUser(this.LoggedInUserDetail.getUserDetailId());
            }
        }
    }

    public void initStoresList() {
        try {
            this.StoresList.clear();
        } catch (NullPointerException npe) {
            this.StoresList = new ArrayList<>();
        }
    }

    public List<Store> getUserStores() {
        StoreBean sb = new StoreBean();
        if (this.LoggedInUserDetail != null) {
            if ("Yes".equals(this.LoggedInUserDetail.getIsUserGenAdmin())) {
                return sb.getStoresAll();
            } else {
                return sb.getStoresByUser(this.LoggedInUserDetail.getUserDetailId());
            }
        } else {
            return null;
        }
    }

    public void userLogin(int aLoginType) {
        if (new DBConnection().isMySQLConnectionAvailable().equals("ON")) {
            UserDetailBean udb = new UserDetailBean();
            this.setLoggedInUserDetail(udb.getUserDetailByUserName(this.LoggedInUserName));
            if (this.LoggedInUserDetail != null && this.LoggedInPassword.equals(this.LoggedInUserDetail.getUserPassword()) && "No".equals(this.LoggedInUserDetail.getIsUserLocked())) {
                //it means username and password are valid and un-locked
                //check store
                if (this.LoggedInStoreId != 0) {
                    //create seesion
                    FacesContext context = FacesContext.getCurrentInstance();
                    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                    HttpSession httpSession = request.getSession(true);
                    //set LoggedIn/Current User in session
                    httpSession.setAttribute("CURRENT_USER", this.LoggedInUserDetail);
                    //set LoggedIn/Current store in session
                    httpSession.setAttribute("CURRENT_STORE", new StoreBean().getStore(this.LoggedInStoreId));
                    //Set user rights for all the groups the user belongs to in session
                    httpSession.setAttribute("CURRENT_GROUP_RIGHTS", new GroupRightBean().getCurrentGroupRights(this.LoggedInStoreId, this.LoggedInUserDetail.getUserDetailId()));
                    //Set is approve discount needed in session
                    if (new GroupRightBean().IsUserGroupsFunctionAccessAllowed(this.LoggedInUserDetail, new GroupRightBean().getCurrentGroupRights(this.LoggedInStoreId, this.LoggedInUserDetail.getUserDetailId()), "86", "Add") == 1) {
                        httpSession.setAttribute("IS_APPROVE_DISCOUNT_NEEDED", 0);
                    } else {
                        httpSession.setAttribute("IS_APPROVE_DISCOUNT_NEEDED", 1);
                    }
                    //Set is approve points needed in session
                    if (new GroupRightBean().IsUserGroupsFunctionAccessAllowed(this.LoggedInUserDetail, new GroupRightBean().getCurrentGroupRights(this.LoggedInStoreId, this.LoggedInUserDetail.getUserDetailId()), "89", "Add") == 1) {
                        httpSession.setAttribute("IS_APPROVE_POINTS_NEEDED", 0);
                    } else {
                        httpSession.setAttribute("IS_APPROVE_POINTS_NEEDED", 1);
                    }
                    //Set is allowed to backdate in session
                    if (new GroupRightBean().IsUserGroupsFunctionAccessAllowed(this.LoggedInUserDetail, new GroupRightBean().getCurrentGroupRights(this.LoggedInStoreId, this.LoggedInUserDetail.getUserDetailId()), "113", "View") == 1) {
                        httpSession.setAttribute("IS_ALLOWED_TO_BACKDATE", 1);
                    } else {
                        httpSession.setAttribute("IS_ALLOWED_TO_BACKDATE", 0);
                    }
                    //Set LOGIN_TYPE in session
                    httpSession.setAttribute("LOGIN_TYPE", aLoginType); //1=BRANCH, 2=INTER-BRANCH
                    //set MODULES sessions
                    int HIRE_MODULE_ON = 0;
                    try {
                        HIRE_MODULE_ON = Integer.parseInt(new Parameter_listBean().getParameter_listByContextName("MODULE", "HIRE_MODULE_ON").getParameter_value());
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    httpSession.setAttribute("HIRE_MODULE_ON", HIRE_MODULE_ON);
                    //set DEFAULT_CURRENCY_CODE sessions
                    String DEFAULT_CURRENCY_CODE = "";
                    try {
                        DEFAULT_CURRENCY_CODE = new Parameter_listBean().getParameter_listByContextName("CURRENCY", "DEFAULT_CURRENCY_CODE").getParameter_value();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    httpSession.setAttribute("DEFAULT_CURRENCY_CODE", DEFAULT_CURRENCY_CODE);
                    //set DEFAULT_DURATION_TYPE sessions
                    String ITEM_IMAGE_BASE_URL = "";
                    try {
                        ITEM_IMAGE_BASE_URL = new Parameter_listBean().getParameter_listByContextName("IMAGE", "ITEM_IMAGE_BASE_URL").getParameter_value();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    httpSession.setAttribute("ITEM_IMAGE_BASE_URL", ITEM_IMAGE_BASE_URL);

                    String ITEM_IMAGE_LOCAL_LOCATION = "";
                    try {
                        ITEM_IMAGE_LOCAL_LOCATION = new Parameter_listBean().getParameter_listByContextName("IMAGE", "ITEM_IMAGE_LOCAL_LOCATION").getParameter_value();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    httpSession.setAttribute("ITEM_IMAGE_LOCAL_LOCATION", ITEM_IMAGE_LOCAL_LOCATION);

                    String DEFAULT_DURATION_TYPE = "";
                    try {
                        DEFAULT_DURATION_TYPE = new Parameter_listBean().getParameter_listByContextName("DURATION", "DEFAULT_DURATION_TYPE").getParameter_value();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    httpSession.setAttribute("DEFAULT_DURATION_TYPE", DEFAULT_DURATION_TYPE);

                    String DEPLETE_SOLD_STOCK_UPON = "";
                    try {
                        DEPLETE_SOLD_STOCK_UPON = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    httpSession.setAttribute("DEPLETE_SOLD_STOCK_UPON", DEPLETE_SOLD_STOCK_UPON);

                    String LOCALE_COUNTRY_CODE = "en";
                    String LOCALE_LANGUAGE_CODE = "US";
                    String LOCALE_COUNT_LANG_CODE = "";
                    try {
                        LOCALE_COUNTRY_CODE = new Parameter_listBean().getParameter_listByContextNameMemory("LOCALE", "LANGUAGE_CODE").getParameter_value();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    try {
                        LOCALE_LANGUAGE_CODE = new Parameter_listBean().getParameter_listByContextNameMemory("LOCALE", "COUNTRY_CODE").getParameter_value();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    LOCALE_COUNT_LANG_CODE = LOCALE_COUNTRY_CODE + "_" + LOCALE_LANGUAGE_CODE;
                    httpSession.setAttribute("LOCALE_COUNTRY_CODE", LOCALE_COUNTRY_CODE);
                    httpSession.setAttribute("LOCALE_LANGUAGE_CODE", LOCALE_LANGUAGE_CODE);
                    httpSession.setAttribute("LOCALE_COUNT_LANG_CODE", LOCALE_COUNT_LANG_CODE);

                    //local currency setting
                    AccCurrency LOCAL_CURRENCY = null;
                    try {
                        LOCAL_CURRENCY = new AccCurrencyBean().getLocalCurrency();
                    } catch (NullPointerException | ClassCastException npe) {
                    }
                    httpSession.setAttribute("LOCAL_CURRENCY", LOCAL_CURRENCY);
                    //set computer name session
                    try {
                        String clientipaddress = new UtilityBean().getClientIp(request);
                        String clientcomputername = new UtilityBean().getClientComputerName(clientipaddress);
                        if (clientcomputername.startsWith("0")) {
                            clientcomputername = "localhost";
                        }
                        httpSession.setAttribute("CLIENT_COMPUTER_NAME", clientcomputername);
                    } catch (Exception e) {
                    }
                    //first delete all un-logged out sessions of this user that are older than 12 hours
                    new LoginSessionBean().deleteOldUnloggedOutSessions();

                    //---------------add login session to the session database---
                    LoginSession ls = new LoginSession();
                    ls.setUserDetailId(this.LoggedInUserDetail.getUserDetailId());
                    ls.setStoreId(this.LoggedInStoreId);
                    ls.setSessionId(FacesContext.getCurrentInstance().getExternalContext().getSessionId(false));

                    String aRemoteIp = "";
                    String aRemoteHost = "";
                    String aRemoteUser = "";

                    aRemoteIp = request.getHeader("X-FORWARDED-FOR");
                    if (aRemoteIp == null) {
                        aRemoteIp = request.getRemoteAddr();
                    }
                    ls.setRemoteIp(aRemoteIp);

                    try {
                        aRemoteHost = request.getRemoteHost();
                        if (aRemoteHost == null) {
                            aRemoteHost = "";
                        }
                    } catch (NullPointerException npe) {
                        aRemoteHost = "";
                    }
                    ls.setRemoteHost(aRemoteHost);

                    try {
                        aRemoteUser = request.getRemoteUser();
                        if (aRemoteUser == null) {
                            aRemoteUser = "";
                        }
                    } catch (NullPointerException npe) {
                        aRemoteUser = "";
                    }
                    ls.setRemoteUser(aRemoteUser);

                    //System.out.println("SID:" + ls.getSessionId() + " UID:" + ls.getUserDetailId() + "Saved:" + new LoginSessionBean().saveLoginSession(ls));
                    if (new LoginSessionBean().saveLoginSession(ls) == 1) {
                        //added successfully
                    }
                    //refresh menu item
                    menuItemBean.refreshMenuItemObj();
                    //take stock snapshot
                    new Cdc_generalBean().takeNewSnapshot_stockAtLogin();
                    //take cash balance snapshot
                    new Cdc_generalBean().takeNewSnapshot_cash_balanceAtLogin();
                    //Refresh stock and expiry alerts
                    new Alert_generalBean().refreshAlerts();
                    //API-TAX - take AES public key snapshot
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0 && new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_TAX_MODE").getParameter_value().equals("ONLINE")) {
                        new Cdc_generalBean().takeNewSnapshot_AesPublicKeyAtLogin();
                    }
                    //Navigate to the Menu or Home page
                    FacesContext fc = FacesContext.getCurrentInstance();
                    ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                    nav.performNavigation("Home?faces-redirect=true");
                } else {
                    this.ActionMessageSuccess = "";
                    this.ActionMessageFailure = "Invalid Store...";
                }
            } else {
                this.LoggedInUserDetail = null;
                this.ActionMessageSuccess = "";
                this.ActionMessageFailure = "User account is either Invalid or Locked, contact system admin...";
            }
        } else {
            this.ActionMessageSuccess = "";
            this.ActionMessageFailure = "Branch database connection is off, contact systems administrator please...";
        }
    }

    public void userApprove(String aUserName, int aStoreId, String aUserPassword, String aFunctionName, String aRole) {
        int ApproveUserId = 0;
        String ApproveDiscountStatus = "";
        String ApprovePointsStatus = "";

        UserDetailBean udb = new UserDetailBean();
        UserDetail ud = new UserDetail();

        ud = udb.getUserDetailByUserName(aUserName);
        if (ud != null && aUserPassword.equals(ud.getUserPassword()) && "No".equals(ud.getIsUserLocked())) {
            //it means username and password are valid and un-locked
            //check right to approve
            GroupRightBean grb = new GroupRightBean();
            if (grb.IsUserGroupsFunctionAccessAllowed2(ud, new GroupRightBean().getCurrentGroupRights(aStoreId, ud.getUserDetailId()), aFunctionName, aRole) == 1) {
                ApproveUserId = ud.getUserDetailId();
                if (aFunctionName.equals("86")) {//86 DISCOUNT
                    ApproveDiscountStatus = "APPROVED";
                } else if (aFunctionName.equals("89")) {//89 SPEND POINT
                    ApprovePointsStatus = "APPROVED";
                }
            } else {
                ApproveUserId = 0;
                if (aFunctionName.equals("86")) {
                    ApproveDiscountStatus = "REJECTED";
                } else if (aFunctionName.equals("89")) {
                    ApprovePointsStatus = "REJECTED";
                }
            }
        } else {
            ApproveUserId = 0;
            if (aFunctionName.equals("86")) {
                ApproveDiscountStatus = "REJECTED";
            } else if (aFunctionName.equals("89")) {
                ApprovePointsStatus = "REJECTED";
            }
        }
        //update session for discount and points approvals
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        httpSession.setAttribute("APPROVE_USER_ID", ApproveUserId);
        if (aFunctionName.equals("86")) {
            httpSession.setAttribute("APPROVE_DISCOUNT_STATUS", ApproveDiscountStatus);
        } else if (aFunctionName.equals("89")) {
            httpSession.setAttribute("APPROVE_POINTS_STATUS", ApprovePointsStatus);
        }
    }

    public void userLogout() {
        ////this.LoggedInUserDetail=null;
        ////this.confirmUser();
        //first delete session from database
        ////String aSessionId=FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        ////if(!aSessionId.isEmpty()){
        ////if(new LoginSessionBean().deleteLoginSessionBySession(aSessionId)==1){
        //deleted success
        ////}
        ////}
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ////return "Login?faces-redirect=true";
    }

    /**
     * @return the LoggedInUserName
     */
    public String getLoggedInUserName() {
        return LoggedInUserName;
    }

    /**
     * @return the LoggedInUserDetail
     */
    public UserDetail getLoggedInUserDetail() {
        return this.LoggedInUserDetail;
    }

    /**
     * @param aLoggedInUserDetail the LoggedInUserDetail to set
     */
    public void setLoggedInUserDetail(UserDetail aLoggedInUserDetail) {
        this.LoggedInUserDetail = aLoggedInUserDetail;
    }

    /**
     * @param LoggedInUserName the LoggedInUserName to set
     */
    public void setLoggedInUserName(String LoggedInUserName) {
        this.LoggedInUserName = LoggedInUserName;
    }

    /**
     * @return the LoggedInStoreId
     */
    public int getLoggedInStoreId() {
        return LoggedInStoreId;
    }

    /**
     * @param LoggedInStoreId the LoggedInStoreId to set
     */
    public void setLoggedInStoreId(int LoggedInStoreId) {
        this.LoggedInStoreId = LoggedInStoreId;
    }

    /**
     * @return the LoggedInPassword
     */
    public String getLoggedInPassword() {
        return LoggedInPassword;
    }

    /**
     * @param LoggedInPassword the LoggedInPassword to set
     */
    public void setLoggedInPassword(String LoggedInPassword) {
        this.LoggedInPassword = LoggedInPassword;
    }

    /**
     * @return the ActionMessageSuccess
     */
    public String getActionMessageSuccess() {
        return ActionMessageSuccess;
    }

    /**
     * @param ActionMessageSuccess the ActionMessageSuccess to set
     */
    public void setActionMessageSuccess(String ActionMessageSuccess) {
        this.ActionMessageSuccess = ActionMessageSuccess;
    }

    /**
     * @return the ActionMessageFailure
     */
    public String getActionMessageFailure() {
        return ActionMessageFailure;
    }

    /**
     * @param ActionMessageFailure the ActionMessageFailure to set
     */
    public void setActionMessageFailure(String ActionMessageFailure) {
        this.ActionMessageFailure = ActionMessageFailure;
    }

    /**
     * @return the TransactorType
     */
    public String getTransactorType() {
        return TransactorType;
    }

    /**
     * @param TransactorType the TransactorType to set
     */
    public void setTransactorType(String TransactorType) {
        this.TransactorType = TransactorType;
    }

    /**
     * @return the StoresList
     */
    public List<Store> getStoresList() {
        return StoresList;
    }

    /**
     * @param StoresList the StoresList to set
     */
    public void setStoresList(List<Store> StoresList) {
        this.StoresList = StoresList;
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
     * @return the LICENSE_DAYS_LEFT
     */
    public long getLICENSE_DAYS_LEFT() {
        return LICENSE_DAYS_LEFT;
    }

    /**
     * @param LICENSE_DAYS_LEFT the LICENSE_DAYS_LEFT to set
     */
    public void setLICENSE_DAYS_LEFT(long LICENSE_DAYS_LEFT) {
        this.LICENSE_DAYS_LEFT = LICENSE_DAYS_LEFT;
    }

    /**
     * @return the LICENSE_TYPE
     */
    public long getLICENSE_TYPE() {
        return LICENSE_TYPE;
    }

    /**
     * @param LICENSE_TYPE the LICENSE_TYPE to set
     */
    public void setLICENSE_TYPE(long LICENSE_TYPE) {
        this.LICENSE_TYPE = LICENSE_TYPE;
    }
}
