package beans;

import static beans.TransBean.LOGGER;
import connections.DBConnection;
import entities.AccCoa;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.GroupRight;
import entities.Item;
import entities.Item_unit;
import entities.Pay;

import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.TransactionPackage;
import entities.TransactionPackageItem;
import entities.TransactionReason;
import entities.TransactionType;
import entities.Transaction_approval;
import entities.Transaction_item_unit;
import entities.Transactor;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class TransactionPackageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransactionPackageBean.class.getName());

    private TransactionPackage transactionPackage;
    private List<TransactionPackageItem> transactionPackageItemist;
    private String ActionMessage;
    private String ActionMessageChild;
    private List<TransactionPackage> transactionPackageList;
    private String transactionNumber;
    private String ActionType;
    private Trans transObj;
    private List<Trans> TransListHist = new ArrayList<>();
    private String FieldName;
    private List<Trans> TransList;
    private List<Trans> TransListSummary;
    private List<Trans> TranssDraft;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private boolean AutoPrintAfterSave;

    public void setTransactionPackageFromResultset(TransactionPackage transPackage, ResultSet aResultSet) {
        try {
            try {
                transPackage.setTransactionNumber(aResultSet.getString("transaction_number"));
            } catch (Exception npe) {
                transPackage.setTransactionNumber("");
            }
            try {
                transPackage.setTransactionPackageId(aResultSet.getLong("transaction_package_id"));
            } catch (Exception npe) {
                transPackage.setTransactionPackageId(0);
            }
            try {
                transPackage.setTransactionId(aResultSet.getLong("transaction_id"));
            } catch (Exception npe) {
                transPackage.setTransactionId(0);
            }
            try {
                transPackage.setTransactionReasonId(aResultSet.getInt("transaction_reason_id"));
            } catch (Exception npe) {
                transPackage.setTransactionReasonId(0);
            }
            try {
                transPackage.setTransactionTypeId(aResultSet.getInt("transaction_type_id"));
            } catch (Exception npe) {
                transPackage.setTransactionTypeId(0);
            }
            try {
                transPackage.setTransactionRef(aResultSet.getString("transaction_ref"));
            } catch (Exception npe) {
                transPackage.setTransactionRef("");
            }
            try {
                transPackage.setTotalTax(aResultSet.getDouble("total_tax"));
            } catch (Exception npe) {
                transPackage.setTotalTax(0);
            }
            try {
                transPackage.setTransactionDate(aResultSet.getDate("transaction_date"));
            } catch (Exception npe) {
                transPackage.setTransactionDate(null);
            }
            try {
                transPackage.setAddDate(aResultSet.getDate("add_date"));
            } catch (Exception npe) {
                transPackage.setAddDate(null);
            }
            try {
                transPackage.setGrandTotal(aResultSet.getDouble("grand_total"));
            } catch (Exception npe) {
                transPackage.setGrandTotal(0);
            }
            try {
                transPackage.setStoreId(aResultSet.getInt("store_id"));
            } catch (Exception npe) {
                transPackage.setStoreId(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveTransPackageCECNew(String statusCode, String aLevel, int aStoreId, int aTransTypeId, int aTransReasonId, Trans trans, TransactionPackage transactionPage, List<TransactionPackageItem> aTransactionPackageItemList, Transactor aSelectedTransactor, Transactor aSelectedBillTransactor) {

        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        int InsertedTransItems = 0;
        double CheckPackageValueBfr = 0;
        double CheckPackageValueAfr = 0;

        double CheckTransPackageValueBfr = 0;
        double CheckTransPackageValueAfr = 0;

        int DeleteInserted = 0;
        String ValidationMessage = "";
        CheckPackageValueBfr = this.checkTrans(0, aTransactionPackageItemList);
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        //TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        //Store store = new StoreBean().getStore(aStoreId);
        ValidationMessage = this.validateTransCEC(aStoreId, aTransTypeId, aTransReasonId, "", trans, aTransactionPackageItemList, aSelectedTransactor, aSelectedBillTransactor);
        long payid = 0;
        //-------
        String sql = null;
        String sql2 = null;

        TransItemBean TransItemBean = new TransItemBean();

        //first clear current session
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(true);
        switch (aLevel) {
            case "PARENT":
                httpSession.setAttribute("CURRENT_TRANSACTION_ID", 0);
                httpSession.setAttribute("CURRENT_PAY_ID", 0);
                break;
            case "CHILD":
                httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", 0);
                httpSession.setAttribute("CURRENT_PAY_ID_CHILD", 0);
                break;
        }
        if (statusCode.length() > 0) {
            transactionPage.setStatusCode(statusCode);
            trans.setStatus_code(statusCode);
            if(statusCode.equals("1")&& transactionPage.getTransactionId()>0 && trans.getTransactionId()>0){
                 this.deleteTranPackagesFromHist(trans.getTransactionId(), transactionPage.getTransactionPackageId());
            }
        }
        if (ValidationMessage.length() > 0) {
            switch (aLevel) {
                case "PARENT":
                    msg = "Transaction NOT saved";
                    //this.setActionMessage("Transaction NOT saved");
                    this.setActionMessage(ub.translateWordsInText(BaseName, msg));
                    break;
                case "CHILD":
                    //this.setActionMessageChild("Transaction NOT saved");
                    msg = "Transaction Not Saved";
                    this.setActionMessageChild(ub.translateWordsInText(BaseName, msg));
                    break;
            }
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, ValidationMessage)));
        } else {
            try {
                //save transaction
                new TransBean().insertTransCEC(aStoreId, aTransTypeId, aTransReasonId, "", trans, null);

                if (trans.getTransactionId() == 0) {
                    switch (aLevel) {
                        case "PARENT":
                            msg = "Transaction Not Saved";
                            this.setActionMessage(ub.translateWordsInText(BaseName, msg));
                            break;
                        case "CHILD":
                            msg = "Transaction Not Saved";
                            this.setActionMessageChild(ub.translateWordsInText(BaseName, msg));
                            break;
                    }
                    msg = "Transaction Not Saved due to Error";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else {

                    //save transaction package88
                    Long transactionPackageId = this.insertTransactionPackage(aStoreId, aTransTypeId, aTransReasonId, transactionPage, trans, aTransactionPackageItemList);

                    //set store2 for transfer in session!
                    switch (aLevel) {
                        case "PARENT":
                            httpSession.setAttribute("CURRENT_TRANSACTION_ID", trans.getTransactionId());
                            if ("TRANSFER".equals(transtype.getTransactionTypeName())) {
                                httpSession.setAttribute("CURRENT_STORE2_ID", trans.getStore2Id());
                            } else {
                                httpSession.setAttribute("CURRENT_STORE2_ID", 0);
                            }
                            break;
                        case "CHILD":
                            httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", trans.getTransactionId());
                            if ("TRANSFER".equals(transtype.getTransactionTypeName())) {
                                httpSession.setAttribute("CURRENT_STORE2_ID_CHILD", trans.getStore2Id());
                            } else {
                                httpSession.setAttribute("CURRENT_STORE2_ID_CHILD", 0);
                            }
                            break;
                    }
                    //save trans items
                    //trans.setStoreId(VARCHAR);
                    if (trans.getTransactionTypeId() == 88) {
                        InsertedTransItems = new TransactionPackageItemBean().saveTransPackageItemsCEC(aStoreId, aTransTypeId, aTransReasonId, trans, aTransactionPackageItemList, transactionPackageId);
                        //check if transaction package items where saved
                        CheckPackageValueAfr = this.checkTrans(transactionPackageId, null);//check if transaction package items where saved well
                        //CheckTransPackageValueBfr = this.checkTransPackage(trans.getTransactionId());//check if package was saved

                        if (CheckPackageValueBfr == CheckPackageValueAfr) {
                            DeleteInserted = 0;
                        } else {
                            DeleteInserted = 1;
                        }
                        if (DeleteInserted == 1) {

                            int deleted1 = new TransactionPackageItemBean().deleteTransPackageItemsUnitByTransPackageItemId(transactionPackageId);

                            int deleted2 = 0;
                            int deleted3 = 0;
                            if (deleted1 == 1) {
                                deleted2 = new TransactionPackageItemBean().deleteTransPackageItemsCEC(trans.getTransactionId());
                            }
                            if (deleted2 == 1) {
                                deleted3 = new TransactionPackageItemBean().deleteTransactionPackageByTransId(trans.getTransactionId());
                            }
                            if (deleted3 == 1) {
                                new TransBean().deleteTransCEC(trans.getTransactionId());
                            }
                        }
                        if (DeleteInserted == 1) {
                            //delete inserted
                            //int deleted1 = new TransItemBean().deleteTransItemsCEC(trans.getTransactionId());
                            //display msg
                            switch (aLevel) {
                                case "PARENT":
                                    httpSession.setAttribute("CURRENT_TRANSACTION_ID", 0);
                                    httpSession.setAttribute("CURRENT_PAY_ID", 0);
                                    // httpSession.setAttribute("CURRENT_TRANSACTION_PACKAGE_ID", 0);
                                    this.setActionMessage("Transaction Not Saved");
                                    break;
                                case "CHILD":
                                    httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", 0);
                                    httpSession.setAttribute("CURRENT_PAY_ID_CHILD", 0);
                                    this.setActionMessageChild("Transaction Not Saved");
                                    break;
                            }
                            msg = "Transaction Not Saved";
                            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                        } else {
                        //insert approvals

                            //  clearAll2(Trans t, TransactionPackage transactionPackage, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem ti, Item aSelectedItem, aSelectedTransactor, 2, Transactor aSelectedBillTransactor, aTransUserDetail, aSelectedSchemeTransactor, aAuthorisedByUserDetail, aSelectedAccCoa) {
                            org.primefaces.PrimeFaces.current().executeScript("doClearClick()");

                            TransItemBean = null;
                            switch (aLevel) {
                                case "PARENT":
                                    msg = "Saved Successfully (Transaction Id: " + new GeneralUserSetting().getCurrentTransactionId() + ")";
                                    this.setActionMessage(ub.translateWordsInText(BaseName, msg));
                                    break;
                                case "CHILD":
                                    msg = "Saved Successfully (Transaction Id : " + new GeneralUserSetting().getCurrentTransactionIdChild() + ")";
                                    this.setActionMessageChild(ub.translateWordsInText(BaseName, msg));
                                    break;
                            }
                            //Refresh Print output
                            new OutputDetailBean().refreshPackageOutput(aLevel, "");

                            //Auto Printing Invoice
                            if (statusCode.equals("100")) {
                                this.refreshTranssDraft(aStoreId, new GeneralUserSetting().getCurrentUser().getUserDetailId(), aTransTypeId, aTransReasonId);
                            } else {
                                if ("PACKAGING".equals(transtype.getTransactionTypeName())) {
                                    //1. Update Invoice
                                    //2. Auto Printing Invoice
                                    if (this.AutoPrintAfterSave) {
                                        try {
                                            org.primefaces.PrimeFaces.current().executeScript("doPrintHiddenClick()");
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                                //Refresh stock alerts
                                new UtilityBean().refreshAlertsThread();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                switch (aLevel) {
                    case "PARENT":
                        this.setActionMessage(ub.translateWordsInText(BaseName, "Transaction Not Saved"));
                        break;
                    case "CHILD":
                        this.setActionMessageChild(ub.translateWordsInText(BaseName, "Transaction Not Saved"));
                        break;
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, "Transaction NOT saved! Double check details, ensure transaction ref numbers have not been captured already")));
            }
        }
    }

    public void loadDraftTransPackage(String aHistFlag, Trans aTrans, TransactionPackage transactionPackage, List<TransactionPackageItem> aActiveTransPackageItems, TransactorBean aTransactorBean, UserDetailBean aUserDetailBean, TransactionPackageBean aTransactionPackageBean) {
        //Transactor aSelectedTransactor, Transactor aSelectedBillTransactor, UserDetail aTransUserDetail, Transactor aSelectedSchemeTransactor, UserDetail aAuthorisedByUserDetail, AccCoa aSelectedAccCoa
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = null;
        long aTransactionId = 0;
        if (aHistFlag.equals("Draft")) {
            aTransactionId = aTrans.getTransactionId();
        } else if (aHistFlag.equals("Approval")) {
            Transaction_approval transapp = new Transaction_approvalBean().getTransaction_approval(aTrans.getTransaction_approval_id());
            if (null != transapp) {
                aTransactionId = transapp.getTransaction_hist_id();
            }
        }
        try {
            if (aTransactionId > 0) {
                aTrans = new TransBean().getTrans(aTrans.getTransactionId());
                 new TransactionPackageItemBean().setTransPackageItemsByTransactionId(aActiveTransPackageItems, aTransactionId);
                     this.transactionPackageItemist= new ArrayList<>();
                 for(TransactionPackageItem t: aActiveTransPackageItems){
                     this.transactionPackageItemist.add(t);
                 }
                try {
                    if (aTrans.getTransactorId() > 0) {
                        aTransactorBean.setSelectedTransactor(new TransactorBean().getTransactor(aTrans.getTransactorId()));
                    } else {
                        aTransactorBean.setSelectedTransactor(null);
                    }
                } catch (NullPointerException npe) {
                }
                try {
                    if (aTrans.getBillTransactorId() > 0) {
                        aTransactorBean.setSelectedBillTransactor(new TransactorBean().getTransactor(aTrans.getBillTransactorId()));
                    } else {
                        aTransactorBean.setSelectedBillTransactor(null);
                    }
                } catch (NullPointerException npe) {
                }
                try {
                    if (aTrans.getSchemeTransactorId() > 0) {
                        aTransactorBean.setSelectedSchemeTransactor(new TransactorBean().getTransactor(aTrans.getSchemeTransactorId()));
                    } else {
                        aTransactorBean.setSelectedSchemeTransactor(null);
                    }
                } catch (NullPointerException npe) {
                }
                try {
                    if (aTrans.getTransactionUserDetailId() > 0) {
                        aUserDetailBean.setSelectedUserDetail(new UserDetailBean().getUserDetail(aTrans.getTransactionUserDetailId()));
                    } else {
                        aUserDetailBean.setSelectedUserDetail(null);
                    }
                } catch (NullPointerException npe) {
                } 
                 //Customer Display
                if (new GeneralUserSetting().getCurrentTransactionTypeId() == 2) {
                    String PortName = new Parameter_listBean().getParameter_listByContextNameMemory("CUSTOMER_DISPLAY", "COM_PORT_NAME").getParameter_value();
                    String ClientPcName = new GeneralUserSetting().getClientComputerName();
                    String SizeStr = new Parameter_listBean().getParameter_listByContextNameMemory("CUSTOMER_DISPLAY", "MAX_CHARACTERS_PER_LINE").getParameter_value();
                    int Size = 0;
                    if (SizeStr.length() > 0) {
                        Size = Integer.parseInt(SizeStr);
                    }
                    if (PortName.length() > 0 && ClientPcName.length() > 0 && Size > 0 && (new GeneralUserSetting().getCurrentTransactionTypeId() == 2 || new GeneralUserSetting().getCurrentTransactionTypeId() == 11)) {
                        //UtilityBean ub = new UtilityBean();
                        ub.invokeLocalCustomerDisplay(ClientPcName, PortName, Size, ub.formatDoubleToString(aTrans.getGrandTotal()), "");
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, "Select Valid " + aHistFlag + " Record")));
                this.setActionMessage(ub.translateWordsInText(BaseName, aHistFlag + " Not Loaded"));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void deleteTranPackagesFromHist(long aTransactionId, long aTransactionPackageId) {
        String sql = "{call sp_delete_transaction_package_draft_id(?,?)}";
        try {
            aTransactionPackageId = this.getTransactionPackageOut(aTransactionId).getTransactionPackageId();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }

        if (aTransactionId > 0 && aTransactionPackageId > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, aTransactionId);
                ps.setLong(2, aTransactionPackageId);
                ps.executeUpdate();
                this.refreshTranssDraft(new GeneralUserSetting().getCurrentStore().getStoreId(), new GeneralUserSetting().getCurrentUser().getUserDetailId(), new GeneralUserSetting().getCurrentTransactionTypeId(), new GeneralUserSetting().getCurrentTransactionReasonId());
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public void refreshTranssDraft(int aStoreId, int aAddUserDetailId, int aTransTypeId, int aTransReasonId) {
        String sql = "";
        try {
            this.TranssDraft.clear();
        } catch (NullPointerException npe) {
            this.TranssDraft = new ArrayList<>();
        }
        sql = "{call sp_search_transaction_package_draft_by_user_type(?,?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aTransTypeId);
            ps.setInt(2, aTransReasonId);
            ps.setInt(3, aStoreId);
            ps.setInt(4, new GeneralUserSetting().getCurrentUser().getUserDetailId());
            rs = ps.executeQuery();
            Trans t = null;
            while (rs.next()) {
                t = new TransBean().getTransFromResultset(rs);
                this.TranssDraft.add(t);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String validateTransCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans trans, List<TransactionPackageItem> aActiveTransItems, Transactor aSelectedTransactor, Transactor aSelectedBillTransactor) {
        String msg = "";
        Store store2 = new StoreBean().getStore(trans.getStore2Id());
        int Store2Id = 0;
        try {
            Store2Id = store2.getStoreId();
        } catch (Exception e) {
            Store2Id = 0;
        }
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);

            String ItemMessage = "";

            int IsNewTransNoUsed = 0;
            if (transtype.getTransactionTypeId() == 2 && trans.getTransactionId() == 0 && trans.getTransactionNumber().length() > 0) {
                IsNewTransNoUsed = new Trans_number_controlBean().getIsTrans_number_used(transtype.getTransactionTypeId(), trans.getTransactionNumber());
            }
            String MsgCkeckApproval = "";
            if (trans.getTransaction_approval_id() > 0) {
                Transaction_approval apprtrans = new Transaction_approvalBean().getTransaction_approval(trans.getTransaction_approval_id());
                if (null == apprtrans) {
                    MsgCkeckApproval = "Invalid Approval Transaction Reference";
                } else {
                    if (apprtrans.getGrand_total() != trans.getGrandTotal() || apprtrans.getAmount_tendered() != trans.getAmountTendered() || apprtrans.getTransactor_id() != trans.getTransactorId()) {
                        MsgCkeckApproval = "Approved and Transaction to Save  are Different";
                    }
                    if (apprtrans.getApproval_status() != 1) {
                        MsgCkeckApproval = "Transaction is Not Approved for Processing";
                    }
                }
            }
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            if (null == transtype) {
                msg = "Invalid Transaction";
            } else if (trans.getTransactionId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(transreason.getTransactionReasonId()), "Add") == 0) {
                msg = "Access Denied";
            } else if (IsNewTransNoUsed == 1) {
                msg = "Specify New Transaction Number";
            } else if (trans.getTransactionDate() == null) {
                msg = "Select " + transtype.getTransactionDateLabel();
                if ("UNPACK".equals(transtype.getTransactionTypeName())) {
                    aActiveTransItems.clear();
                }
            } else if ((new GeneralUserSetting().getDaysFromDateToLicenseExpiryDate(trans.getTransactionDate()) <= 0 || new GeneralUserSetting().getDaysFromDateToLicenseExpiryDate(new CompanySetting().getCURRENT_SERVER_DATE()) <= 0) && CompanySetting.getLicenseType() != 9) {
                msg = "Server Date is Wrong or Lincese is Expired";
            } else if (trans.getTransactorId() == 0 && transtype.getIsTransactorMandatory().equals("Yes")) {
                msg = "Select Valid Customer";
            } else if (aActiveTransItems.size() < 1 & !"UNPACK".equals(transtype.getTransactionTypeName()) & trans.getTransactionId() == 0) {
                msg = "Item not Found for " + transtype.getTransactionOutputLabel();
            } else if (aSelectedTransactor != null && aSelectedTransactor.getIsSuspended().equals("Yes")) {
                msg = "Suspended ##" + aSelectedTransactor.getTransactorNames() + " for " + aSelectedTransactor.getSuspendedReason();
            } else if (aSelectedBillTransactor != null && aSelectedBillTransactor.getIsSuspended().equals("Yes")) {
                msg = "Suspended ##" + aSelectedBillTransactor.getTransactorNames() + " for " + aSelectedBillTransactor.getSuspendedReason();
            }

            /*else if (trans.getTransactionId() > 0 && new TransItemBean().countItemsWithQtyChanged(new TransItemBean().getTransItemListCurLessPrevQty(aActiveTransItems, trans), transtype.getTransactionTypeName()) == 0) {
             msg = "Cannot Save where Item Qty has Not Changed";
             } */
        } catch (Exception e) {
            msg = "An Error has Occured During the Validation Process";
            //System.err.println("--:validateTransCEC:--" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
        return msg;
    }

    //create a sale package
    //save/update sale packege
    //get sale packege by id
    //get sale packege by status
    //get sale packege by date
    public TransactionPackage createTransactionPackage(int aStoreId, int aTransTypeId, Trans aTrans, int aTransReasonId, TransactionPackage tPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        tPackage.setAddUserDetailId(aStoreId);
        tPackage.setCurrencyCode(aTrans.getCurrencyCode());
        //tPackage.setGrandTotal(aTrans.getGrandTotal());
        tPackage.setStatusCode(aTrans.getStatus_code());
        tPackage.setTotalTradeDiscount(aTransReasonId);
        tPackage.setTransactionDate(aTrans.getTransactionDate());
        TransactionType transtype = new TransactionTypeBean().getTransactionType(1);
        String NewTransNo = new Trans_number_controlBean().getNewTransNumber(transtype);
        tPackage.setTransactionNumber(NewTransNo);
        //tPackage.setTransactionId();
        tPackage.setTotalTax(aStoreId);
        tPackage.setStatusCode("1");
        tPackage.setStoreId(aStoreId);
        return tPackage;
    }

    public void saveTransactionPackageAsTransItem(int aStoreId, int aTransTypeId, Trans aTrans, int aTransReasonId, TransItem aTransItem, TransactionPackage tPackage) {

        String sql = "insert"; //insert into transaction item table
        //after, create a transaction

        if (aTransItem.getTransactionItemId() == 0) {
            //sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            sql = "{call sp_insert_transaction_item_out(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (aTransItem.getTransactionItemId() > 0) {
            sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {

            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);
            Store store2 = new StoreBean().getStore(aTrans.getStore2Id());

            if (aTransItem.getTransactionItemId() == 0) {
                //clean batch
                if (aTransItem.getBatchno() == null) {
                    aTransItem.setBatchno("");
                }

                cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
                cs.setLong("in_transaction_id", aTransItem.getTransactionId());
                cs.setLong("in_item_id", aTransItem.getItemId());
                cs.setString("in_batchno", aTransItem.getBatchno());
                try {
                    cs.setDate("in_item_mnf_date", new java.sql.Date(aTransItem.getItemMnfDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_item_mnf_date", null);
                }
                try {
                    cs.setDate("in_item_expiry_date", new java.sql.Date(aTransItem.getItemExpryDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_item_expiry_date", null);
                }
                cs.setDouble("in_item_qty", aTransItem.getItemQty());
                cs.setDouble("in_unit_price", aTransItem.getUnitPrice());
                cs.setDouble("in_unit_trade_discount", aTransItem.getUnitTradeDiscount());
                cs.setDouble("in_unit_vat", aTransItem.getUnitVat());
                cs.setDouble("in_amount", aTransItem.getAmount());
                cs.setString("in_vat_rated", aTransItem.getVatRated());
                cs.setDouble("in_vat_perc", aTransItem.getVatPerc());
                cs.setDouble("in_unit_price_inc_vat", aTransItem.getUnitPriceIncVat());
                cs.setDouble("in_unit_price_exc_vat", aTransItem.getUnitPriceExcVat());
                cs.setDouble("in_amount_inc_vat", aTransItem.getAmountIncVat());
                cs.setDouble("in_amount_exc_vat", aTransItem.getAmountExcVat());

                try {
                    cs.setString("in_code_specific", aTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    cs.setString("in_code_specific", "");
                }
                try {
                    cs.setString("in_desc_specific", aTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    cs.setString("in_desc_specific", "");
                }
                try {
                    cs.setString("in_desc_more", aTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    cs.setString("in_desc_more", "");
                }
                try {
                    cs.setString("in_warranty_desc", aTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    cs.setString("in_warranty_desc", "");
                }
                try {
                    cs.setDate("in_warranty_expiry_date", new java.sql.Date(aTransItem.getWarrantyExpiryDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_warranty_expiry_date", null);
                }
                try {
                    cs.setString("in_account_code", aTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    cs.setString("in_account_code", "");
                }
                try {
                    cs.setDate("in_purchase_date", new java.sql.Date(aTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_purchase_date", null);
                }
                try {
                    cs.setDate("in_dep_start_date", new java.sql.Date(aTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_dep_start_date", null);
                }
                try {
                    cs.setInt("in_dep_method_id", aTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_dep_method_id", 0);
                }
                try {
                    cs.setDouble("in_dep_rate", aTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_dep_rate", 0);
                }
                try {
                    cs.setInt("in_average_method_id", aTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_average_method_id", 0);
                }
                try {
                    cs.setInt("in_effective_life", aTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    cs.setInt("in_effective_life", 0);
                }
                try {
                    cs.setDouble("in_residual_value", aTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_residual_value", 0);
                }
                try {
                    cs.setString("in_narration", aTransItem.getNarration());
                } catch (NullPointerException npe) {
                    cs.setString("in_narration", "");
                }
                try {
                    cs.setDouble("in_qty_balance", aTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_qty_balance", 0);
                }
                try {
                    cs.setDouble("in_duration_value", aTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_duration_value", 0);
                }
                try {
                    cs.setDouble("in_qty_damage", aTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_qty_damage", 0);
                }
                try {
                    cs.setDouble("in_duration_passed", aTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_duration_passed", 0);
                }
                try {
                    if (aTransItem.getSpecific_size() > 0) {
                        cs.setDouble("in_specific_size", aTransItem.getSpecific_size());
                    } else {
                        cs.setDouble("in_specific_size", 1);
                    }
                } catch (NullPointerException npe) {
                    cs.setDouble("in_specific_size", 1);
                }
                cs.registerOutParameter("out_transaction_item_id", VARCHAR);
                //save
                cs.executeUpdate();
                long InsertedId1 = cs.getLong("out_transaction_item_id");
                try {
                    if (InsertedId1 > 0) {
                        Transaction_item_unit tiu = new Transaction_item_unit();
                        tiu.setTransaction_item_id(InsertedId1);
                        tiu.setUnit_id(aTransItem.getUnit_id());
                        tiu.setBase_unit_qty(aTransItem.getBase_unit_qty());
                        new TransItemExtBean().insertTransaction_item_unit(tiu);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                }
                if (InsertedId1 > 0) {
                    org.primefaces.PrimeFaces.current().executeScript("doPrintHiddenClick()");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    //package auto complete
    public List<TransactionPackage> getSalePackageTransNumberForAutoComplete(String transNumber) {
        String sql = "";
        ResultSet rs = null;
        String queryLowerCase = transNumber.toLowerCase();
        this.transactionPackageList = new ArrayList<>();
        sql = "select transaction_number from transaction_package where store_id=1 and transaction_number like'%" + transNumber + "%'";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                // this.transactionPackageList.add(rs.getString("transaction_number"));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        //return transactionNumbersList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
        return transactionPackageList;
    }

    public void saveTransCECcallFromSINew(int aStoreId, int aTransTypeId, int aTransReasonId, Trans trans, TransactionPackage transactionPackage, List<TransactionPackageItem> aTransactionPackageItemList) {

        transactionPackage = createTransactionPackage(aStoreId, aTransTypeId, trans, aTransReasonId, transactionPackage, aTransactionPackageItemList);
        double CheckValueBfr = 0;
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);

        this.insertTransactionPackage(aStoreId, aTransTypeId, aTransReasonId, transactionPackage, trans, aTransactionPackageItemList);
    }

    public double checkTrans(long aTransactionPackageId, List<TransactionPackageItem> aTransactionPackageItemList) {
        double value = 0;
        int CountItems = 0;
        double CountQty = 0;
        try {
            if (aTransactionPackageId == 0) {
                // CountItems = aTransactionPackageItemList.size();
                CountQty = new TransactionPackageItemBean().getTransPackageItemsTotalQty(aTransactionPackageItemList);
            } else if (aTransactionPackageId > 0) {
                List<TransactionPackageItem> tis = new TransactionPackageItemBean().getTransPackageItemsByTransactionPackageId(aTransactionPackageId);
                //CountItems = tis.size();
                CountQty = new TransactionPackageItemBean().getTransPackageItemsTotalQty(tis);
                //value = new UtilityBean().getD("SELECT (count(*)+sum(item_qty)) as d FROM transaction_item ti WHERE ti.transaction_id=" + aTransactionId);
            }
            value = CountQty + CountItems;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return value;
    }

    public double checkTransPackage(long transaction_Id) {
        double value = 0;
        try {
            if (transaction_Id > 0) {
                value = new UtilityBean().getD("SELECT (count(*) as d FROM transaction_package WHERE transaction_package_id=" + transaction_Id);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return value;
    }

    public void setTransTotalsAndUpdateCEC(int aTransTypeId, int aTransReasonId, Trans aTrans, TransactionPackage aTransPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        aTransPackage.setSubTotal(this.getSubTotal(aTransactionPackageItemList));
        aTransPackage.setTotalTradeDiscount(this.getTotalTradeDiscount(aTransPackage, aTransactionPackageItemList));
        aTransPackage.setTotalTax(this.getTotalTax(aTransactionPackageItemList));
        aTransPackage.setGrandTotal(this.getGrandTotalCEC(aTransTypeId, aTransReasonId, aTransPackage));

        //Customer Display
        String PortName = new Parameter_listBean().getParameter_listByContextNameMemory("CUSTOMER_DISPLAY", "COM_PORT_NAME").getParameter_value();
        String ClientPcName = new GeneralUserSetting().getClientComputerName();
        String SizeStr = new Parameter_listBean().getParameter_listByContextNameMemory("CUSTOMER_DISPLAY", "MAX_CHARACTERS_PER_LINE").getParameter_value();
        int Size = 0;
        if (SizeStr.length() > 0) {
            Size = Integer.parseInt(SizeStr);
        }
        if (PortName.length() > 0 && ClientPcName.length() > 0 && Size > 0 && (aTransTypeId == 2 || aTransTypeId == 11)) {
            UtilityBean ub = new UtilityBean();
            ub.invokeLocalCustomerDisplay(ClientPcName, PortName, Size, ub.formatDoubleToString(aTransPackage.getGrandTotal()), "");
        }
    }

    public double getGrandTotalCEC(int aTransTypeId, int aTransReasonId, TransactionPackage aTransPackage) {
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        double GTotal = 0;
        if ("SALE PACKAGE".equals(transtype.getTransactionTypeName())) {
            GTotal = (aTransPackage.getSubTotal() + aTransPackage.getTotalTax()) - (aTransPackage.getTotalTradeDiscount() + aTransPackage.getCashDiscount());
        }
        GTotal = (double) new AccCurrencyBean().roundAmount(aTransPackage.getCurrencyCode(), GTotal, "TOTAL");
        return GTotal;
    }

    public double getTotalTradeDiscount(TransactionPackage aTransPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TotTradeDisc = 0;
        while (ListItemIndex < ListItemNo) {
            TotTradeDisc = TotTradeDisc + (ati.get(ListItemIndex).getUnitTradeDiscount() * ati.get(ListItemIndex).getItemQty());
            ListItemIndex = ListItemIndex + 1;
        }
        // TotTradeDisc = (double) new AccCurrencyBean().roundAmount(aTransPackage.getCurrencyCode(), TotTradeDisc);
        return TotTradeDisc;
    }

    public double getTotalTax(List<TransactionPackageItem> aTransactionPackageItemList) {
        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TVat = 0;
        while (ListItemIndex < ListItemNo) {
            TVat = TVat + (ati.get(ListItemIndex).getUnitVat() * ati.get(ListItemIndex).getItemQty());
            //we shall excise tax here on items applicable
            ListItemIndex = ListItemIndex + 1;
        }
        return TVat;
    }

    public double getSubTotal(List<TransactionPackageItem> aTransactionPackageItemList) {
        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double SubT = 0;
        while (ListItemIndex < ListItemNo) {
            SubT = SubT + (ati.get(ListItemIndex).getUnitPrice() * ati.get(ListItemIndex).getItemQty());
            ListItemIndex = ListItemIndex + 1;
        }
        return SubT;
    }

    public double getGrandTotal(List<TransactionPackageItem> aTransactionPackageItemList) {
        double GTotal = 0;

        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        GTotal = 0;
        while (ListItemIndex < ListItemNo) {
            GTotal = GTotal + (ati.get(ListItemIndex).getItemQty() * ati.get(ListItemIndex).getUnitPrice());
            ListItemIndex = ListItemIndex + 1;
        }
        return GTotal;
    }

    public long insertTransactionPackage(int aStoreId, int aTransTypeId, int aTransReasonId, TransactionPackage tPackage, Trans aTrans, List<TransactionPackageItem> aTransactionPackageItemList) {
        long InsertedTransId = 0;
        String sql = "{call sp_insert_transaction_package("
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);
            long aTransId = 0;
            if (aTrans != null) {
                aTransId = aTrans.getTransactionId();
            }
            if (aTransId > 0) {
                cs.setInt("in_transaction_type_id", aTransTypeId);
                cs.setInt("in_transaction_reason_id", aTransReasonId);
                cs.setLong("in_transaction_id", aTransId);
                cs.setInt("in_store_id", store.getStoreId());
                tPackage.setStoreId(store.getStoreId());
                cs.setInt("in_store2_id", tPackage.getStore2Id());
                cs.setLong("in_transactor_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                cs.setString("in_transaction_number", aTrans.getTransactionNumber());
                cs.setDouble("in_total_tax", tPackage.getTotalTax());
                cs.setInt("in_add_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                tPackage.setAddUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                cs.setTimestamp("in_add_date", new java.sql.Timestamp(new java.util.Date().getTime()));
                cs.setInt("in_edit_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());//will be made null by the SP
                cs.setTimestamp("in_edit_date", new java.sql.Timestamp(new java.util.Date().getTime()));//will be made null by the SP
                cs.setString("in_transaction_ref", aTrans.getTransactionRef());
                cs.registerOutParameter("out_transaction_package_id", VARCHAR);
                cs.setDouble("in_sub_total", tPackage.getSubTotal());
                cs.setDouble("in_grand_total", tPackage.getGrandTotal());
                cs.setDouble("in_cash_discount", tPackage.getTotalTradeDiscount());
                cs.setDouble("in_total_trade_discount", tPackage.getTotalTradeDiscount());
                cs.setDouble("in_vat_perc", CompanySetting.getVatPerc());
                cs.setString("in_transaction_comment", "");
                cs.setString("in_status_code", tPackage.getStatusCode());

                //for profit margin
                try {
                    if (tPackage.getTransactionUserDetailId() == 0) {
                        tPackage.setTransactionUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    }
                } catch (NullPointerException npe) {
                    tPackage.setTransactionUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                }
                cs.setInt("in_transaction_user_detail_id", tPackage.getTransactionUserDetailId());

                try {
                    cs.setString("in_currency_code", tPackage.getCurrencyCode());
                } catch (NullPointerException npe) {
                    cs.setString("in_currency_code", "");
                }

                //bought in after order module
                try {
                    cs.setLong("in_location_id", tPackage.getLocationId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_location_id", 0);
                }
                if (null == tPackage.getStatusCode()) {
                    cs.setString("in_status_code", "");
                } else {
                    cs.setString("in_status_code", tPackage.getStatusCode());
                }
                if (null == tPackage.getStatusDate()) {
                    cs.setTimestamp("in_status_date", new java.sql.Timestamp(new java.util.Date().getTime()));
                } else {
                    cs.setTimestamp("in_status_date", new java.sql.Timestamp(tPackage.getStatusDate().getTime()));
                }
                if (null == tPackage.getTransactionDate()) {
                    cs.setTimestamp("in_transaction_date", new java.sql.Timestamp(new java.util.Date().getTime()));
                } else {
                    cs.setTimestamp("in_transaction_date", new java.sql.Timestamp(tPackage.getTransactionDate().getTime()));
                }
                //save
                cs.executeUpdate();
                InsertedTransId = cs.getLong("out_transaction_package_id");
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            e.printStackTrace();
        }
        return InsertedTransId;
    }

    public boolean updateTransactionPackageTable(Trans aNewTrans) {
        boolean isTransUpdateSuccess = false;
        String newSQL = "{call sp_update_transaction2(?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(newSQL);) {
            cs.setLong("in_transaction_id", aNewTrans.getTransactionId());
            //cs.setLong("in_transaction_package_id", aNewTrans.getTransactionId());
            cs.setDouble("in_cash_discount", aNewTrans.getCashDiscount());
            cs.setDouble("in_total_vat", aNewTrans.getTotalVat());
            cs.setInt("in_edit_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
            cs.setDouble("in_sub_total", aNewTrans.getSubTotal());
            cs.setDouble("in_grand_total", aNewTrans.getGrandTotal());
            cs.setDouble("in_total_trade_discount", aNewTrans.getTotalTradeDiscount());

            cs.executeUpdate();
            isTransUpdateSuccess = true;
        } catch (Exception e) {
            isTransUpdateSuccess = false;
            LOGGER.log(Level.ERROR, e);
        }
        return isTransUpdateSuccess;
    }

    public void setCashDiscountPercAndUpdateCEC(int aTransTypeId, int aTransReasonId, Trans aTrans, TransactionPackage aTransPackage, List<TransactionPackageItem> aActiveTransItems) {
        /* if (aTransPackage.getCash_dicsount_perc() > 0) {
         aTransPackage.setCashDiscount(((aTransPackage.getSubTotal() - aTransPackage.getTotalTradeDiscount()) * aTransPackage.getCash_dicsount_perc()) / 100);
         } else {
         aTransPackage.setCashDiscount(0);
         }
         */
        aTransPackage.setCashDiscount(0);
        aTransPackage.setCashDiscount((double) new AccCurrencyBean().roundAmount(aTransPackage.getCurrencyCode(), aTransPackage.getCashDiscount()));
        this.setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aTransPackage, aActiveTransItems);
    }

    public void setCashDiscountPercAndUpdateCEC_err(int aTransTypeId, int aTransReasonId, Trans aTrans, TransactionPackage aTransPackage, List<TransactionPackageItem> aActiveTransItems) {
        aTransPackage.setCashDiscount(0);
        this.setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aTransPackage, aActiveTransItems);
        //aTrans.setCashDiscount((aTrans.getGrandTotal() * aTrans.getCash_dicsount_perc()) / 100);
        //aTransPackage.setCashDiscount(((aTransPackage.getSubTotal() - aTransPackage.getTotalTradeDiscount()) * aTransPackage.getCash_dicsount_perc()) / 100);
        aTransPackage.setCashDiscount((double) new AccCurrencyBean().roundAmount(aTransPackage.getCurrencyCode(), aTransPackage.getCashDiscount()));
        this.setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aTransPackage, aActiveTransItems);
    }

    public void initClearAllCEC(String aLevel, Trans t, List<TransactionPackageItem> aActiveTransItems, TransactionPackage transPackage, TransactionPackageItem ti, Item aSelectedItem, Transactor aSelectedTransactor, int ClearNo, Transactor aSelectedBillTransactor, UserDetail aTransUserDetail, Transactor aSelectedSchemeTransactor) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all  
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            TransactionPackageItemBean tib = new TransactionPackageItemBean();
            ItemBean itmB = new ItemBean();
            TransactorBean trB = new TransactorBean();

            if (ClearNo == 1 || ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
                //clear autoCompletetd item
                itmB.clearSelectedItem();
                itmB.clearItem(aSelectedItem);
                //clear the selcted trans item
                tib.clearTransactionPackageItem(ti);
            }
            if (ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
                //code for clearing customer/supplier/transactor
                trB.clearSelectedTransactor();
                trB.clearTransactor(aSelectedTransactor);
                //code for clearing customer/supplier/transactor
                //trB.clearSelectedBillTransactor();
                trB.clearTransactor(aSelectedBillTransactor);
                trB.clearTransactor(aSelectedSchemeTransactor);
                //clear all the item LIST
                //--//tib.getActiveTransItems().clear();
                aActiveTransItems.clear();

                //clear Trans
                new TransBean().clearTrans(t);
                //clear transaction user / service offered by
                new UserDetailBean().clearUserDetail(aTransUserDetail);

                this.clearTransactionPackage(transPackage);

                //clear current trans and pay ids in session
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSession = request.getSession(false);
                if (aLevel.equals("PARENT")) {
                    httpSession.setAttribute("CURRENT_TRANSACTION_ID", 0);
                    httpSession.setAttribute("CURRENT_PAY_ID", 0);
                    //clear action message
                    this.setActionMessage("");
                } else if (aLevel.equals("CHILD")) {
                    httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", 0);
                    httpSession.setAttribute("CURRENT_PAY_ID_CHILD", 0);
                    //clear action message
                    this.setActionMessageChild("");
                }
            }
            new OutputDetailBean().refreshOutput(aLevel, "");
            //new OutputDetailBean().clearOutput(aLevel, "");
        }
    }

    public void clearTransactionPackage(TransactionPackage transPackage) {
        if (null != transPackage) {
            transPackage.setTransactionId(0);

            try {
                transPackage.setTransactionDate(new CompanySetting().getCURRENT_SERVER_DATE());
            } catch (NullPointerException npe) {
                transPackage.setTransactionDate(new CompanySetting().getCURRENT_SERVER_DATE());
            }
            transPackage.setStoreId(0);
            transPackage.setStore2Id(0);
            transPackage.setTransactorId(0);
            transPackage.setTransactionTypeId(0);//
            transPackage.setTransactionReasonId(0);//
            transPackage.setCashDiscount(0);
            //transPackage.setTransactionComment("");
            transPackage.setAddUserDetailId(0);
            //trans.setAddDate(null);//
            transPackage.setEditUserDetailId(0);
            //trans.setEditDate(null);//
            transPackage.setTransactionRef("");

            transPackage.setSubTotal(0);

            transPackage.setSubTotal(0);
            transPackage.setTotalTradeDiscount(0);
            transPackage.setTotalTax(0);
            transPackage.setGrandTotal(0);

            //clear current trans and pay ids in session
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("APPROVE_USER_ID", 0);
            httpSession.setAttribute("APPROVE_DISCOUNT_STATUS", "");
            httpSession.setAttribute("APPROVE_POINTS_STATUS", "");
            //for profit margin

            transPackage.setTransactionNumber("");

            //init currency
            int TransTypeId = transPackage.getTransactionTypeId();
            if (TransTypeId == 0) {
                TransTypeId = new GeneralUserSetting().getCurrentTransactionTypeId();
            }
            // if (TransTypeId > 0) {
            //      this.initCurrencyCode(TransTypeId, transPackage);
            //  }
            //init other trans type defaults
            if (TransTypeId > 0) {
                try {
                    TransactionType TransType = new TransactionTypeBean().getTransactionType(TransTypeId);
                    //trans.setTermsConditions(TransType.getDefault_term_condition());
                } catch (Exception e) {
                    //do nothing
                }
            }
        }
    }

    public List<TransactionPackage> getTransactionPackageObjectListForSale(String Query) {
        String sql, sqlDesc = "", sqlCode = "", sqlCodeOther = "";
        //desc
        String[] ArrayDesc = new UtilityBean().getStringArrayFromXSeperatedStr(Query, " ");
        if (menuItemBean.getMenuItemObj().getITEM_FULL_SEARCH_ON() == 1 && ArrayDesc.length > 1) {
            for (String ArrayDesc1 : ArrayDesc) {
                if (sqlDesc.length() == 0) {
                    sqlDesc = " tp.transaction_number LIKE '%" + ArrayDesc1 + "%' ";
                } else {
                    sqlDesc = sqlDesc + " AND tp.transaction_number LIKE '%" + ArrayDesc1 + "%' ";
                }
            }
        } else {
            sqlDesc = " tp.transaction_number LIKE '%" + Query + "%' ";
        }
        sql = "SELECT * FROM transaction_package tp WHERE tp.status_code=1 AND("
                + "(" + sqlDesc + ") "
                + ") ORDER BY tp.transaction_number ASC LIMIT " + menuItemBean.getMenuItemObj().getSEARCH_ITEMS_LIST_LIMIT();
        //System.out.println("SQL:" + sql);
        ResultSet rs = null;
        this.transactionPackageList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                TransactionPackage t = new TransactionPackage();
                this.setTransactionPackageFromResultset(t, rs);
                this.getTransactionPackageList().add(t);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getTransactionPackageList();
    }

    public TransactionPackage getTransactionPackageOut(long aTransactionId) {
        String sql = "{call sp_search_transaction_package_transaction_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            TransactionPackage t = new TransactionPackage();
            if (rs.next()) {
                this.setTransactionPackageFromResultset(t, rs);
                return t;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public TransactionPackage findTransactionPackage(long packageId) {
        String sql = "{call sp_search_transaction_package_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, packageId);
            rs = ps.executeQuery();
            if (rs.next()) {
                TransactionPackage t = new TransactionPackage();
                this.setTransactionPackageFromResultset(t, rs);
                return t;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void loadPackageForInvoiceTrans(Trans aTrans, List<TransItem> aActiveTransItems, TransItem aTransItem) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        long TransferId = 0;
        String PackageNumber = aTransItem.getItemCode();
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(new GeneralUserSetting().getCurrentTransactionTypeId());
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(new GeneralUserSetting().getCurrentTransactionReasonId());
            Store store = new StoreBean().getStore(new GeneralUserSetting().getCurrentStore().getStoreId());
            if (PackageNumber.length() > 0) {
                Trans packageTrans = new Trans();
                List<TransItem> TransferItems = new ArrayList<>();
                this.setTransFromPackageNumber(packageTrans, PackageNumber, store.getStoreId());
                if ("0".equals(packageTrans.getStatus_code())) {
                    msg = "Package " + PackageNumber + " was reject";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                    this.setActionMessage(ub.translateWordsInText(BaseName, msg));
                } else if ("2".equals(packageTrans.getStatus_code())) {
                    msg = "Package " + PackageNumber + " already sold out";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                    this.setActionMessage(ub.translateWordsInText(BaseName, msg));
                } else {
                    TransferId = packageTrans.getTransactionId();
                    if (TransferId > 0) {
                        new TransItemBean().setTransItemsByTransactionId(TransferItems, TransferId);
                    }
                    if (TransferId > 0 && TransferItems.size() > 0) {
                        //some cleanups and reset
                        //1. for trans
                        //trans.setTransactionNumber("");
                        //trans.setTransactionRef(PackageNumber);
                        //2. for trans items
                        TransItemBean tib = new TransItemBean();
                        ItemBean ib = new ItemBean();
                        Item item = null;
                        TransItem transitem = null;
                        for (int i = 0; i < TransferItems.size(); i++) {
                            item = new ItemBean().getItem(TransferItems.get(i).getItemId());
                            transitem = new TransItem();
                            transitem.setItemId(item.getItemId());
                            transitem.setItemQty(TransferItems.get(i).getItemQty());
                            try {
                                if (null == TransferItems.get(i).getBatchno()) {
                                    transitem.setBatchno("");
                                } else {
                                    transitem.setBatchno(TransferItems.get(i).getBatchno());
                                }
                            } catch (NullPointerException npe) {
                                transitem.setBatchno("");
                            }
                            try {
                                if (null == TransferItems.get(i).getCodeSpecific()) {
                                    transitem.setCodeSpecific("");
                                } else {
                                    transitem.setCodeSpecific(TransferItems.get(i).getCodeSpecific());
                                }
                            } catch (NullPointerException npe) {
                                transitem.setCodeSpecific("");
                            }
                            try {
                                if (null == TransferItems.get(i).getDescSpecific()) {
                                    transitem.setDescSpecific("");
                                } else {
                                    transitem.setDescSpecific(TransferItems.get(i).getDescSpecific());
                                }
                            } catch (NullPointerException npe) {
                                transitem.setDescSpecific("");
                            }
                            transitem.setAccountCode(tib.getTransItemInventCostAccount(transtype, transreason, item));
                            transitem.setUnit_id(TransferItems.get(i).getUnit_id());
                            transitem.setBase_unit_qty(TransferItems.get(i).getBase_unit_qty());
                            Item_unit iu = new ItemBean().getItemUnitFrmDb(transitem.getItemId(), transitem.getUnit_id());
                            if (null != iu) {
                                item.setUnitRetailsalePrice(iu.getUnit_retailsale_price());
                                item.setUnitWholesalePrice(iu.getUnit_wholesale_price());
                                item.setUnitSymbol(iu.getUnit_symbol());
                            }
                            tib.updateModelTransItemAutoAddFrmTransfer(store, transtype, transreason, new GeneralUserSetting().getCurrentSaleType(), aTrans, new StatusBean(), aActiveTransItems, transitem, item);
                        }
                        tib.clearTransItem(aTransItem);
                    } else {
                        msg = "Select Valid Transfer Number";
                        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                        this.setActionMessage(ub.translateWordsInText(BaseName, msg));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransFromPackageNumber(Trans aTrans, String aTransNumber, int aStore2Id) {
        String sql = "";
        sql = "SELECT * FROM transaction WHERE transaction_type_id=88 AND transaction_number='" + aTransNumber + "' and store_id=" + aStore2Id;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setLong(1, aTransactionHistId);
            rs = ps.executeQuery();
            if (rs.next()) {
                new TransBean().setTransFromResultset(aTrans, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void initSalesPackageSession(long aTransId, String aAction) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg = "";
        this.setActionMessage("");
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        this.transObj = new TransBean().getTrans(aTransId);

        this.transactionPackage = this.getTransactionPackageOut(aTransId);
        List<Trans> aList = new ArrayList<>();
        if (this.transObj.getTransactionTypeId() == 88) {
            aList = new CreditDebitNoteBean().getTrans_cr_dr_notes(this.transObj.getTransactionNumber(), 0);
        }
        if (aAction.equals("Edit") && !aList.isEmpty()) {
            this.setActionType("None");
            msg = "Transaction Has Credit or Debit Note";
            this.setActionMessage(ub.translateWordsInText(BaseName, msg));
        } else {
            //first set current selection in session
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("CURRENT_TRANSACTION_ID", aTransId);
            httpSession.setAttribute("CURRENT_TRANSACTION_ACTION", aAction);
            httpSession.setAttribute("CURRENT_PAY_ID", 0);
            this.setActionType(aAction);
            //this.TransObj = new TransBean().getTrans(aTransId);
            new TransBean().updateLookup(this.transObj);
            this.transactionPackageItemist = new ArrayList<>();
            new TransactionPackageItemBean().setTransPackageItemsByTransactionId(this.transactionPackageItemist, aTransId);
            //refresh output
            new OutputDetailBean().refreshPackageOutput("PARENT", "");
            //refresh history
            this.setTransListHist(new ReportBean().getTransHistory(aTransId));
        }
    }

    public void updateTransPackage(String aLevel, int aStoreId, int aTransTypeId, int aTransReasonId, Trans aNewTrans, TransactionPackage aNewTransPackage, List<TransactionPackageItem> aNewTransPackageItems) {
        //get some details
        String OrderTransNo = aNewTrans.getTransactionRef();
        //save
        this.updateTransCEC(aLevel, aStoreId, aTransTypeId, aTransReasonId, "", aNewTrans, aNewTransPackage, aNewTransPackageItems);
        //update a few things needed after sales invoice saving
        if (OrderTransNo.length() > 0) {
            Trans OrderTrans = new TransBean().getTransByNumberType(OrderTransNo, 88);
            //get order's invoioce pay status 0,1,2
            //save invoice status if not 0
            //this.updatePackageStatus(OrderTrans.getTransactionId(), "status_code", "status_date", InvoiceStatus);
        }
    }

    public void updatePackageStatus(long aOrderId, int status_code, Trans aTrans, TransBean aTransBean) {
        //status_code 0=package cancelled, 1=not yet sold, 2=package sold

        try {
            String sql = "UPDATE transaction SET status_code =?, status_date=? WHERE transaction_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, String.valueOf(status_code));
                ps.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
                ps.setLong(3, aOrderId);
                ps.executeUpdate();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            sql = "UPDATE transaction_package SET status_code =?, status_date=? WHERE transaction_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(1, String.valueOf(status_code));
                ps.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
                ps.setLong(3, aOrderId);
                ps.executeUpdate();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            if (status_code == 0) {
                aTrans.setTransactionNumber("");
                this.reportSalesPackageDetails(aTrans, aTransBean);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void initResetSalesPackageDetail(Trans aTrans, TransBean aTransBean, TransactionPackage transactionPackage, Transactor aBillTransactor, Transactor aTransactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetSalesPackageDetail(aTrans, aTransBean, transactionPackage, aBillTransactor, aTransactor);
        }
    }

    public void reportSalesPackageDetails(Trans aTrans, TransBean aTransBean) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        aTransBean.setActionMessage("");
        try {
            if ((aTransBean.getDate1() != null && aTransBean.getDate2() != null) || aTrans.getTransactionNumber().length() > 0 || aTrans.getTransactionRef().length() > 0) {
                //okay no problem
            } else {
                msg = "Either Select Date Range or Specify Invoice Number or Specify Reference Number";
            }
        } catch (Exception e) {
            //do nothing
        }
        if (aTransBean.getDateType().length() == 0) {
            aTransBean.setDateType("Add Date");
        }
        ResultSet rs = null;
        this.setTransList(new ArrayList<>());
        this.setTransListSummary(new ArrayList<>());
        if (msg.length() > 0) {
            aTransBean.setActionMessage(ub.translateWordsInText(BaseName, msg));
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "SELECT * FROM transaction WHERE transaction_type_id=88";
            String sqlsum = "";
            if (aTransBean.getFieldName().length() > 0) {
                sqlsum = "SELECT " + aTransBean.getFieldName() + ",currency_code,sum(grand_total) as grand_total,sum(total_vat) as total_vat,sum(cash_discount) FROM transaction WHERE transaction_type_id IN(88,65,68)";
            } else {
                sqlsum = "SELECT currency_code,sum(grand_total) as grand_total,sum(total_vat) as total_vat FROM transaction WHERE transaction_type_id IN(88,65,68)";
            }
            String wheresql = "";
            String ordersql = "";
            String ordersqlsum = "";
            String groupbysql = "";
            if (aTransBean.getFieldName().length() > 0) {
                groupbysql = " GROUP BY " + aTransBean.getFieldName() + ",currency_code";
            } else {
                groupbysql = " GROUP BY currency_code";
            }
            if (aTrans.getStoreId() > 0) {
                wheresql = wheresql + " AND store_id=" + aTrans.getStoreId();
            }
            if (aTrans.getTransactionNumber().length() > 0) {
                wheresql = wheresql + " AND transaction_number='" + aTrans.getTransactionNumber() + "'";
            }
            if (aTrans.getTransactionRef().length() > 0) {
                wheresql = wheresql + " AND transaction_ref='" + aTrans.getTransactionRef() + "'";
            }
            if (aTrans.getAddUserDetailId() > 0) {
                wheresql = wheresql + " AND add_user_detail_id=" + aTrans.getAddUserDetailId();
            }
            if (aTrans.getTransactionUserDetailId() > 0) {
                wheresql = wheresql + " AND transaction_user_detail_id=" + aTrans.getTransactionUserDetailId();
            }
            if (aTrans.getBillTransactorId() > 0) {
                wheresql = wheresql + " AND bill_transactor_id=" + aTrans.getBillTransactorId();
            }
            if (aTrans.getTransactorId() > 0) {
                wheresql = wheresql + " AND transactor_id=" + aTrans.getTransactorId();
            }
            if (aTransBean.getDateType().length() > 0 && aTransBean.getDate1() != null && aTransBean.getDate2() != null) {
                switch (aTransBean.getDateType()) {
                    case "Package Date":
                        wheresql = wheresql + " AND transaction_date BETWEEN '" + new java.sql.Date(aTransBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aTransBean.getDate2().getTime()) + "'";
                        break;
                    case "Add Date":
                        wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aTransBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aTransBean.getDate2().getTime()) + "'";
                        break;
                }
            }
            ordersql = " ORDER BY add_date DESC,transaction_id DESC";
            if (aTransBean.getFieldName().length() > 0) {
                ordersqlsum = " ORDER BY " + aTransBean.getFieldName() + ",currency_code";
            } else {
                ordersqlsum = " ORDER BY currency_code";
            }
            sql = sql + wheresql + ordersql;
            sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                Trans trans = null;
                while (rs.next()) {
                    trans = new Trans();
                    new TransBean().setTransFromResultset(trans, rs);
                    double TotalPaid = trans.getTotalPaid();
                    trans.setTotalPaid(TotalPaid);
                    if (TotalPaid >= trans.getGrandTotal()) {
                        trans.setIs_paid(1);
                    } else if (TotalPaid > 0 && TotalPaid < trans.getGrandTotal()) {
                        trans.setIs_paid(2);
                    } else {
                        trans.setIs_paid(0);
                    }
                    this.getTransList().add(trans);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                Trans transsum = null;
                while (rs.next()) {
                    transsum = new Trans();
                    if (aTransBean.getFieldName().length() > 0) {
                        switch (aTransBean.getFieldName()) {
                            case "add_user_detail_id":
                                try {
                                    transsum.setAddUserDetailId(rs.getInt("add_user_detail_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setAddUserDetailId(0);
                                }
                                break;
                            case "transaction_user_detail_id":
                                try {
                                    transsum.setTransactionUserDetailId(rs.getInt("transaction_user_detail_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setTransactionUserDetailId(0);
                                }
                                break;
                            case "bill_transactor_id":
                                try {
                                    transsum.setBillTransactorId(rs.getLong("bill_transactor_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setBillTransactorId(0);
                                }
                                break;
                            case "transactor_id":
                                try {
                                    transsum.setTransactorId(rs.getLong("transactor_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setTransactorId(0);
                                }
                                break;
                            case "transaction_date":
                                try {
                                    transsum.setTransactionDate(new Date(rs.getDate("transaction_date").getTime()));
                                } catch (Exception npe) {
                                    transsum.setTransactionDate(null);
                                }
                                break;
                            case "store_id":
                                try {
                                    transsum.setStoreId(rs.getInt("store_id"));
                                    Store st = new StoreBean().getStore(transsum.getStoreId());
                                    transsum.setStoreName(st.getStoreName());
                                } catch (NullPointerException npe) {
                                    transsum.setStoreName("");
                                }
                                break;
                        }
                    }
                    try {
                        transsum.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        transsum.setCurrencyCode("");
                    }
                    try {
                        transsum.setGrandTotal(rs.getDouble("grand_total"));
                    } catch (NullPointerException npe) {
                        transsum.setGrandTotal(0);
                    }

                    try {
                        transsum.setTotalVat(rs.getDouble("total_vat"));
                    } catch (NullPointerException npe) {
                        transsum.setTotalVat(0);
                    }
                    this.getTransListSummary().add(transsum);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public void updateTransCEC(String aLevel, int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aNewTrans, TransactionPackage transactionPackage, List<TransactionPackageItem> aNewTransItems) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);
        String ValidationMessage = this.validateTransCEC(aStoreId, aTransTypeId, aTransReasonId, aSaleType, aNewTrans, aNewTransItems, null, null);

        String sql = null;
        String msg = "";
        boolean isTransCopySuccess = false;
        boolean isTransItemCopySuccess = false;
        boolean isTransUpdateSuccess = false;
        boolean isTransItemReverseSuccess = false;

        this.setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aNewTrans, transactionPackage, aNewTransItems);

        //first clear current session
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(true);
        switch (aLevel) {
            case "PARENT":
                httpSession.setAttribute("CURRENT_TRANSACTION_ID", 0);
                httpSession.setAttribute("CURRENT_PAY_ID", 0);
                break;
            case "CHILD":
                httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", 0);
                httpSession.setAttribute("CURRENT_PAY_ID_CHILD", 0);
                break;
        }

        if (ValidationMessage.length() > 0) {
            switch (aLevel) {
                case "PARENT":
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Transaction Not Saved"));
                    break;
                case "CHILD":
                    this.setActionMessageChild(ub.translateWordsInText(BaseName, "Transaction Not Saved"));
                    break;
            }
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, ValidationMessage)));
        } else {
            //Copy Trans
            sql = "{call sp_copy_transaction(?,?,?)}";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                cs.setLong("in_transaction_id", aNewTrans.getTransactionId());
                cs.setString("in_hist_flag", "Edit");
                cs.registerOutParameter("out_transaction_hist_id", VARCHAR);
                cs.executeUpdate();
                isTransCopySuccess = true;
            } catch (Exception e) {
                isTransCopySuccess = false;
                LOGGER.log(Level.ERROR, e);
            }

            //Update by Reversing trans items qty differences
            if (isTransCopySuccess) {
                TransactionPackageItemBean tib = new TransactionPackageItemBean();
                isTransItemReverseSuccess = tib.updateTransPackageItems(aNewTrans.getTransactionId(), aNewTransItems);
            }
            //update trans
            if (isTransCopySuccess) {
                isTransUpdateSuccess = new TransBean().updateTransactionTable(aNewTrans);

                switch (aLevel) {
                    case "PARENT":
                        httpSession.setAttribute("CURRENT_TRANSACTION_ID", aNewTrans.getTransactionId());
                        this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully ( Transaction Id : " + new GeneralUserSetting().getCurrentTransactionId() + " )"));
                        break;
                    case "CHILD":
                        httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", aNewTrans.getTransactionId());
                        this.setActionMessageChild(ub.translateWordsInText(BaseName, "Saved Successfully ( Transaction Id : " + new GeneralUserSetting().getCurrentTransactionIdChild() + " )"));
                        break;
                }
            }
        }
        //Refresh Print output
        new OutputDetailBean().refreshPackageOutput(aLevel, "");
        //Auto Printing Invoice
        if ("PACKAGING".equals(transtype.getTransactionTypeName())) {
            //1. Update Package
            //2. Auto Printing Package
            if (this.AutoPrintAfterSave) {
                try {
                    org.primefaces.PrimeFaces.current().executeScript("doPrintHiddenClick()");
                } catch (Exception e) {
                }
            }
        }
        //Refresh stock alerts
        new UtilityBean().refreshAlertsThread();
    }

    public int updateTransItemCECOpenBalance(TransactionPackageItem aTransPackageItem) {
        int success = 0;
        String sql = "UPDATE transaction_package_item SET amount=?, item_qty=? WHERE transaction_package_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setDouble(1, aTransPackageItem.getAmount());
            ps.setDouble(2, aTransPackageItem.getItemQty());
            ps.setLong(3, aTransPackageItem.getTransactionPackageItemId());
            //save
            ps.executeUpdate();
            success = 1;
        } catch (Exception e) {
            success = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return success;
    }

    public void resetSalesPackageDetail(Trans aTrans, TransBean aTransBean, TransactionPackage transactionPackage, Transactor aBillTransactor, Transactor aTransactor) {
        aTransBean.setActionMessage("");
        try {
            new TransBean().clearTrans(aTrans);
            aTrans.setTransactionTypeId(0);
            aTrans.setTransactionReasonId(0);
        } catch (NullPointerException npe) {
        }
        try {
            new TransactorBean().clearTransactor(aBillTransactor);
        } catch (NullPointerException npe) {
        }
        try {
            new TransactorBean().clearTransactor(aTransactor);
        } catch (NullPointerException npe) {
        }
        try {
            aTransBean.setDateType("");
            aTransBean.setDate1(null);
            aTransBean.setDate2(null);
            aTransBean.setFieldName("");
            this.getTransList().clear();
            this.getTransListSummary().clear();
        } catch (NullPointerException npe) {
        }
    }

    public void clearAll2(Trans t, TransactionPackage transactionPackage, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem ti, Item aSelectedItem, Transactor aSelectedTransactor, int ClearNo, Transactor aSelectedBillTransactor, UserDetail aTransUserDetail, Transactor aSelectedSchemeTransactor, UserDetail aAuthorisedByUserDetail, AccCoa aSelectedAccCoa) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all  
        TransactionPackageItemBean tib = new TransactionPackageItemBean();
        ItemBean itmB = new ItemBean();
        TransactorBean trB = new TransactorBean();
        AccCoaBean acBean = new AccCoaBean();

        if (ClearNo == 1 || ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            //clear autoCompletetd item
            itmB.clearSelectedItem();
            itmB.clearItem(aSelectedItem);
            //clear the selcted trans item
            tib.clearTransactionPackageItem(ti);
            this.clearTransactionPackage(transactionPackage);
            //clear selected AccCoa
            acBean.clearAccCoa(aSelectedAccCoa);
        }
        if (ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            trB.clearTransactor(aSelectedTransactor);
            //code for clearing BILL customer/supplier/transactor
            //trB.clearSelectedBillTransactor();
            trB.clearTransactor(aSelectedBillTransactor);
            trB.clearTransactor(aSelectedSchemeTransactor);
            //clear all the item LIST
            //--//tib.getActiveTransItems().clear();
            aActiveTransItems.clear();
            this.clearTransactionPackage(transactionPackage);
            //clear Trans inc. payments
            new TransBean().clearTrans(t);

            //clear TransUser / Service Offered by
            new UserDetailBean().clearUserDetail(aTransUserDetail);

            //clear Authorised By UserDetail
            new UserDetailBean().clearUserDetail(aAuthorisedByUserDetail);
        }
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
     * @return the ActionMessageChild
     */
    public String getActionMessageChild() {
        return ActionMessageChild;
    }

    /**
     * @param ActionMessageChild the ActionMessageChild to set
     */
    public void setActionMessageChild(String ActionMessageChild) {
        this.ActionMessageChild = ActionMessageChild;
    }

    /**
     * @return the transactionNumber
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * @param transactionNumber the transactionNumber to set
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * @return the transactionPackageList
     */
    public List<TransactionPackage> getTransactionPackageList() {
        return transactionPackageList;
    }

    /**
     * @param transactioPackageList the transactionPackageList to set
     */
    public void setTransactionPackageList(List<TransactionPackage> transactioPackageList) {
        this.transactionPackageList = transactioPackageList;
    }

    /**
     * @return the AutoPrintAfterSave
     */
    public boolean isAutoPrintAfterSave() {
        return AutoPrintAfterSave;
    }

    /**
     * @param AutoPrintAfterSave the AutoPrintAfterSave to set
     */
    public void setAutoPrintAfterSave(boolean AutoPrintAfterSave) {
        this.AutoPrintAfterSave = AutoPrintAfterSave;
    }

    /**
     * @return the transactionPackageItemist
     */
    public List<TransactionPackageItem> getTransactionPackageItemist() {
        return transactionPackageItemist;
    }

    /**
     * @param transactionPackageItemist the transactionPackageItemist to set
     */
    public void setTransactionPackageItemist(List<TransactionPackageItem> transactionPackageItemist) {
        this.transactionPackageItemist = transactionPackageItemist;
    }

    /**
     * @return the transactionPackage
     */
    public TransactionPackage getTransactionPackage() {
        return transactionPackage;
    }

    /**
     * @param transactionPackage the transactionPackage to set
     */
    public void setTransactionPackage(TransactionPackage transactionPackage) {
        this.transactionPackage = transactionPackage;
    }

    /**
     * @return the ActionType
     */
    public String getActionType() {
        return ActionType;
    }

    /**
     * @param ActionType the ActionType to set
     */
    public void setActionType(String ActionType) {
        this.ActionType = ActionType;
    }

    /**
     * @return the transObj
     */
    public Trans getTransObj() {
        return transObj;
    }

    /**
     * @param transObj the transObj to set
     */
    public void setTransObj(Trans transObj) {
        this.transObj = transObj;
    }

    /**
     * @return the TransListHist
     */
    public List<Trans> getTransListHist() {
        return TransListHist;
    }

    /**
     * @param TransListHist the TransListHist to set
     */
    public void setTransListHist(List<Trans> TransListHist) {
        this.TransListHist = TransListHist;
    }

    /**
     * @return the FieldName
     */
    public String getFieldName() {
        return FieldName;
    }

    /**
     * @param FieldName the FieldName to set
     */
    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    /**
     * @return the TransList
     */
    public List<Trans> getTransList() {
        return TransList;
    }

    /**
     * @param TransList the TransList to set
     */
    public void setTransList(List<Trans> TransList) {
        this.TransList = TransList;
    }

    /**
     * @return the TransListSummary
     */
    public List<Trans> getTransListSummary() {
        return TransListSummary;
    }

    /**
     * @param TransListSummary the TransListSummary to set
     */
    public void setTransListSummary(List<Trans> TransListSummary) {
        this.TransListSummary = TransListSummary;
    }

    /**
     * @return the TranssDraft
     */
    public List<Trans> getTranssDraft() {
        return TranssDraft;
    }

    /**
     * @param TranssDraft the TranssDraft to set
     */
    public void setTranssDraft(List<Trans> TranssDraft) {
        this.TranssDraft = TranssDraft;
    }

}
