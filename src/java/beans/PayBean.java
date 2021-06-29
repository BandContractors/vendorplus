package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.Pay;
import entities.GroupRight;
import entities.PayTrans;
import entities.Transactor;
import entities.UserDetail;
import entities.TransactorLedger;
import entities.Trans;
import entities.TransactionType;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class PayBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(PayBean.class.getName());

    private List<Pay> Pays;
    private String ActionMessage = null;
    private Pay SelectedPay = null;
    private long SelectedPayId;
    private String SearchPay = "";
    private String TypedTransactorName;
    private Transactor SelectedTransactor;
    private Transactor SelectedBillTransactor;
    private String SearchBy;
    private String DateType;
    private Date Date1;
    private Date Date2;
    private String FieldName;
    private List<Pay> PayList;
    private List<Pay> PayListSummary;
    private Pay PayObj;
    private List<PayTrans> PayTransList;
    private String ActionType;
    private int OverridePrintVersion;
    private boolean PayAll;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public long payInsertUpdate(Pay pay) {
        long PayId = 0;
        String sql = null;
        if (pay.getPayId() == 0) {
            sql = "{call sp_insert_pay(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (pay.getPayId() > 0) {
            sql = "{call sp_update_pay(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            try {
                cs.setDate("in_pay_date", new java.sql.Date(pay.getPayDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_pay_date", null);
            }
            try {
                cs.setDouble("in_paid_amount", pay.getPaidAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_paid_amount", 0);
            }
            try {
                cs.setInt("in_pay_method_id", pay.getPayMethodId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_method_id", 0);
            }
            try {
                cs.setInt("in_edit_user_detail_id", pay.getEditUserDetailId());
            } catch (NullPointerException npe) {
                cs.setInt("in_edit_user_detail_id", 0);
            }
            try {
                cs.setDouble("in_points_spent", pay.getPointsSpent());
            } catch (NullPointerException npe) {
                cs.setDouble("in_points_spent", 0);
            }
            try {
                cs.setDouble("in_points_spent_amount", pay.getPointsSpentAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_points_spent_amount", 0);
            }
            try {
                cs.setTimestamp(6, new java.sql.Timestamp(new java.util.Date().getTime()));
            } catch (NullPointerException npe) {
                cs.setTimestamp(6, null);
            }
            try {
                cs.setTimestamp("in_edit_date", new java.sql.Timestamp(new java.util.Date().getTime()));//will be made null by the SP
            } catch (NullPointerException npe) {
                cs.setTimestamp("in_edit_date", null);
            }
            try {
                cs.setString("in_pay_ref_no", pay.getPayRefNo());
            } catch (NullPointerException npe) {
                cs.setString("in_pay_ref_no", "");
            }
            try {
                cs.setString("in_pay_category", pay.getPayCategory());
            } catch (NullPointerException npe) {
                cs.setString("in_pay_category", "");
            }
            try {
                cs.setLong("in_bill_transactor_id", pay.getBillTransactorId());
            } catch (NullPointerException npe) {
                cs.setLong("in_bill_transactor_id", 0);
            }
            try {
                cs.setInt("in_pay_type_id", pay.getPayTypeId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_type_id", 0);
            }
            try {
                cs.setInt("in_pay_reason_id", pay.getPayReasonId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_reason_id", 0);
            }
            try {
                cs.setInt("in_store_id", pay.getStoreId());
            } catch (NullPointerException npe) {
                cs.setInt("in_store_id", 0);
            }
            try {
                cs.setInt("in_acc_child_account_id", pay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                cs.setInt("in_acc_child_account_id", 0);
            }
            try {
                cs.setInt("in_acc_child_account_id2", pay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                cs.setInt("in_acc_child_account_id2", 0);
            }
            try {
                cs.setString("in_currency_code", pay.getCurrencyCode());
            } catch (NullPointerException npe) {
                cs.setString("in_currency_code", "");
            }
            try {
                cs.setDouble("in_xrate", pay.getXRate());
            } catch (NullPointerException npe) {
                cs.setDouble("in_xrate", 1);
            }
            try {
                cs.setInt("in_status", pay.getStatus());
            } catch (NullPointerException npe) {
                cs.setInt("in_status", 0);
            }
            try {
                cs.setString("in_status_desc", pay.getStatusDesc());
            } catch (NullPointerException npe) {
                cs.setString("in_status_desc", "");
            }
            try {
                cs.setDouble("in_principal_amount", pay.getPrincipalAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_principal_amount", 0);
            }
            try {
                cs.setDouble("in_interest_amount", pay.getInterestAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_interest_amount", 0);
            }
            try {
                cs.setLong("in_delete_pay_id", pay.getDeletePayId());
            } catch (NullPointerException npe) {
                cs.setLong("in_delete_pay_id", 0);
            }
            if (pay.getPayId() > 0) {
                cs.setLong("in_pay_id", pay.getPayId());
            }
            if (pay.getPayId() == 0) {
                try {
                    cs.setInt("in_add_user_detail_id", pay.getAddUserDetailId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_add_user_detail_id", 0);
                }
                cs.registerOutParameter("out_pay_id", VARCHAR);
            }
            try {
                if (pay.getPay_number().length() == 0) {
                    TransactionType transtype = new TransactionTypeBean().getTransactionType(pay.getPayTypeId());
                    pay.setPay_number(new Trans_number_controlBean().getNewTransNumber(transtype, pay.getAddUserDetailId(), pay.getStoreId()));
                    cs.setString("in_pay_number", pay.getPay_number());
                    new Trans_number_controlBean().updateTrans_number_control(transtype);
                } else {
                    cs.setString("in_pay_number", pay.getPay_number());
                }
            } catch (NullPointerException npe) {
                cs.setString("in_pay_number", "");
            }
            //System.out.println(cs.toString());
            cs.executeUpdate();
            //set output to return
            if (pay.getPayId() == 0) {
                PayId = cs.getLong("out_pay_id");
            } else if (pay.getPayId() > 0) {
                PayId = pay.getPayId();
            }
        } catch (Exception e) {
            PayId = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return PayId;
    }

    public long payInsertUpdateFix(Pay pay) {
        long PayId = 0;
        String sql = null;
        if (pay.getPayId() == 0) {
            sql = "{call sp_insert_pay_fix(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (pay.getPayId() > 0) {
            sql = "{call sp_update_pay(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            try {
                cs.setDate("in_pay_date", new java.sql.Date(pay.getPayDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_pay_date", null);
            }
            try {
                cs.setDouble("in_paid_amount", pay.getPaidAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_paid_amount", 0);
            }
            try {
                cs.setInt("in_pay_method_id", pay.getPayMethodId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_method_id", 0);
            }
            try {
                cs.setInt("in_edit_user_detail_id", pay.getEditUserDetailId());
            } catch (NullPointerException npe) {
                cs.setInt("in_edit_user_detail_id", 0);
            }
            try {
                cs.setDouble("in_points_spent", pay.getPointsSpent());
            } catch (NullPointerException npe) {
                cs.setDouble("in_points_spent", 0);
            }
            try {
                cs.setDouble("in_points_spent_amount", pay.getPointsSpentAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_points_spent_amount", 0);
            }
            try {
                cs.setTimestamp(6, new java.sql.Timestamp(pay.getAddDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setTimestamp(6, null);
            }
            try {
                cs.setTimestamp("in_edit_date", new java.sql.Timestamp(new java.util.Date().getTime()));//will be made null by the SP
            } catch (NullPointerException npe) {
                cs.setTimestamp("in_edit_date", null);
            }
            try {
                cs.setString("in_pay_ref_no", pay.getPayRefNo());
            } catch (NullPointerException npe) {
                cs.setString("in_pay_ref_no", "");
            }
            try {
                cs.setString("in_pay_category", pay.getPayCategory());
            } catch (NullPointerException npe) {
                cs.setString("in_pay_category", "");
            }
            try {
                cs.setLong("in_bill_transactor_id", pay.getBillTransactorId());
            } catch (NullPointerException npe) {
                cs.setLong("in_bill_transactor_id", 0);
            }
            try {
                cs.setInt("in_pay_type_id", pay.getPayTypeId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_type_id", 0);
            }
            try {
                cs.setInt("in_pay_reason_id", pay.getPayReasonId());
            } catch (NullPointerException npe) {
                cs.setInt("in_pay_reason_id", 0);
            }
            try {
                cs.setInt("in_store_id", pay.getStoreId());
            } catch (NullPointerException npe) {
                cs.setInt("in_store_id", 0);
            }
            try {
                cs.setInt("in_acc_child_account_id", pay.getAccChildAccountId());
            } catch (NullPointerException npe) {
                cs.setInt("in_acc_child_account_id", 0);
            }
            try {
                cs.setInt("in_acc_child_account_id2", pay.getAccChildAccountId2());
            } catch (NullPointerException npe) {
                cs.setInt("in_acc_child_account_id2", 0);
            }
            try {
                cs.setString("in_currency_code", pay.getCurrencyCode());
            } catch (NullPointerException npe) {
                cs.setString("in_currency_code", "");
            }
            try {
                cs.setDouble("in_xrate", pay.getXRate());
            } catch (NullPointerException npe) {
                cs.setDouble("in_xrate", 1);
            }
            try {
                cs.setInt("in_status", pay.getStatus());
            } catch (NullPointerException npe) {
                cs.setInt("in_status", 0);
            }
            try {
                cs.setString("in_status_desc", pay.getStatusDesc());
            } catch (NullPointerException npe) {
                cs.setString("in_status_desc", "");
            }
            try {
                cs.setDouble("in_principal_amount", pay.getPrincipalAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_principal_amount", 0);
            }
            try {
                cs.setDouble("in_interest_amount", pay.getInterestAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_interest_amount", 0);
            }
            try {
                cs.setLong("in_delete_pay_id", pay.getDeletePayId());
            } catch (NullPointerException npe) {
                cs.setLong("in_delete_pay_id", 0);
            }
            if (pay.getPayId() > 0) {
                cs.setLong("in_pay_id", pay.getPayId());
            }
            if (pay.getPayId() == 0) {
                try {
                    cs.setInt("in_add_user_detail_id", pay.getAddUserDetailId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_add_user_detail_id", 0);
                }
                cs.registerOutParameter("out_pay_id", VARCHAR);
            }
            try {
                if (pay.getPay_number().length() == 0) {
                    TransactionType transtype = new TransactionTypeBean().getTransactionType(pay.getPayTypeId());
                    pay.setPay_number(new Trans_number_controlBean().getNewTransNumber(transtype, pay.getAddUserDetailId(), pay.getStoreId()));
                    cs.setString("in_pay_number", pay.getPay_number());
                    new Trans_number_controlBean().updateTrans_number_control(transtype);
                } else {
                    cs.setString("in_pay_number", pay.getPay_number());
                }
            } catch (NullPointerException npe) {
                cs.setString("in_pay_number", "");
            }
            //System.out.println(cs.toString());
            cs.executeUpdate();
            //set output to return
            if (pay.getPayId() == 0) {
                PayId = cs.getLong("out_pay_id");
            } else if (pay.getPayId() > 0) {
                PayId = pay.getPayId();
            }
        } catch (Exception e) {
            PayId = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return PayId;
    }

    public void saveCashReceiptREVENUE(Pay pay, List<PayTrans> aPayTranss, int aPayTypeId, int aPayReasId, PayTrans aPayTrans) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            if (aPayTrans.getAccount_code().length() <= 0) {
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, "Select Revenue Account")));
            } else {
                aPayTrans.setTransPaidAmount(pay.getPaidAmount());
                try {
                    aPayTranss.clear();
                } catch (NullPointerException npe) {
                    aPayTranss = new ArrayList<>();
                }
                aPayTranss.add(aPayTrans);
                this.saveCashReceipt(pay, aPayTranss, aPayTypeId, aPayReasId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void updateOrderPayStatus(List<PayTrans> aPayTranss) {
        try {
            for (int i = 0; i < aPayTranss.size(); i++) {
                long OrderId = 0;
                String OrderNumber = "";
                double OrderAmount = 0;
                double TotalPaid = 0;
                OrderNumber = aPayTranss.get(i).getTransactionRef();
                if (null == OrderNumber || OrderNumber.length() == 0) {
                    //do nothing
                } else {
                    try {
                        Trans OrderTrans = new TransBean().getTransByTransNumber(OrderNumber);
                        OrderAmount = OrderTrans.getGrandTotal();
                        OrderId = OrderTrans.getTransactionId();
                    } catch (Exception e) {
                        OrderAmount = 0;
                    }
                    TotalPaid = new PayTransBean().getTotalPaidByOrderRef(OrderNumber);
                    if (TotalPaid >= OrderAmount) {//1
                        new TransBean().updateOrderStatus(OrderId, "is_paid", 1);
                    } else if (TotalPaid > 0 && TotalPaid < OrderAmount) {//2
                        new TransBean().updateOrderStatus(OrderId, "is_paid", 2);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveCashReceipt(Pay pay, List<PayTrans> aPayTranss, int aPayTypeId, int aPayReasId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        try {
            String sql = null;
            String sql2 = null;
            TransactorLedger NewTransactorLedger;
            TransactorLedgerBean NewTransactorLedgerBean;
            Trans NewTrans;
            TransBean NewTransBean;
            Transactor NewTransactor;
            Transactor NewBillTransactor;
            TransactorBean NewTransactorBean;

            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            if (pay.getPayId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(aPayReasId), "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(aPayReasId), "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayDate() == null) {
                msg = "Select Receipt Date";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getBillTransactorId() == 0 && (aPayReasId == 21 || aPayReasId == 22 || aPayReasId == 90)) {
                msg = "Specify Customer";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Cash Receipt Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getAccChildAccountId2() == 0 && aPayReasId == 23) {
                msg = "Specify the Shareholder";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getAccChildAccountId2() == 0 && aPayReasId == 24) {
                msg = "Specify Loan Account";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayMethodId() == 0) {
                msg = "Invalid Payment Method";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getAccChildAccountId() == 0) {
                msg = "Invalid Cash Receipt Account";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getCurrencyCode().length() <= 0) {
                msg = "Specify Currency Code";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPaidAmount() <= 0) {
                msg = "Invalid Recipt Amount";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aPayReasId == 22 && new PayTransBean().getRecGreaterBalStr(aPayTranss).length() > 0) {
                msg = "Amount Cannot be Greater Than Receivable Balance ## " + new PayTransBean().getRecGreaterBalStr(aPayTranss);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (null == new AccPeriodBean().getAccPeriod(pay.getPayDate())) {
                this.setActionMessage("");
                msg = "Date Selected Does Not Match any Accounting Period";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayRefNo().length() > 100 || pay.getStatusDesc().length() > 100) {
                msg = "Reference Numner and Status Description Cannot Exceed 100 Characters";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                pay.setAddUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                pay.setEditUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());//will be made null by the SP
                pay.setPayCategory(new GeneralUserSetting().getCurrentPayCategory());
                pay.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                pay.setDeletePayId(0);
                AccCurrency LocalCurrency = null;
                LocalCurrency = new AccCurrencyBean().getLocalCurrency();
                pay.setXRate(new AccXrateBean().getXrate(pay.getCurrencyCode(), LocalCurrency.getCurrencyCode()));
                pay.setPayTypeId(aPayTypeId);
                pay.setPayReasonId(aPayReasId);
                long SavedPayId = this.payInsertUpdate(pay);
                //manage session variable
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("CURRENT_PAY_ID", SavedPayId);

                pay.setPayId(SavedPayId);
                //insert pay transs items
                if (aPayReasId == 21 || aPayReasId == 22 || aPayReasId == 115) {
                    new PayTransBean().savePayTranss(pay, aPayTranss);
                }
                //Refresh Print output
                new OutputDetailBean().refreshOutput("PARENT", "SOURCE-PAY");

                //insert Journal
                if (aPayReasId == 21 || aPayReasId == 22) {//CASH/CREDIT SALE
                    new AccJournalBean().postJournalCashReceiptReceivable(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 23) {//CAPITAL
                    new AccJournalBean().postJournalCashReceiptCapital(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 24) {//LOAN
                    new AccJournalBean().postJournalCashReceiptLoan(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 90) {//CUSTOMER DEPOSIT (Prepaid Income)
                    new AccJournalBean().postJournalCashReceiptPrepaidIncome(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 115) {//OTHER REVENUE
                    new AccJournalBean().postJournalCashReceiptOtherRevenue(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId(), aPayTranss);
                }
                this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                if (aPayReasId == 22) {
                    this.updateOrderPayStatus(aPayTranss);
                }
                //Update Total Paid for Sales/Purchase Invoice
                if (aPayReasId == 21 || aPayReasId == 22 || aPayReasId == 115) {
                    new PayTransBean().updateTranssTotalPaid(aPayTranss);
                }
                this.clearPayPayTranss(pay, aPayTranss);
                this.PayAll = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveCashPaymentLIABILITY(Pay pay, List<PayTrans> aPayTranss, int aPayTypeId, int aPayReasId, PayTrans aPayTrans) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            if (aPayTrans.getAccount_code().length() <= 0) {
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, "Select Liability Account")));
            } else {
                aPayTrans.setTransPaidAmount(pay.getPaidAmount());
                try {
                    aPayTranss.clear();
                } catch (NullPointerException npe) {
                    aPayTranss = new ArrayList<>();
                }
                aPayTranss.add(aPayTrans);
                this.saveCashPayment(pay, aPayTranss, aPayTypeId, aPayReasId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveCashPayment(Pay pay, List<PayTrans> aPayTranss, int aPayTypeId, int aPayReasId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        try {
            String sql = null;
            String sql2 = null;
            TransactorLedger NewTransactorLedger;
            TransactorLedgerBean NewTransactorLedgerBean;
            Trans NewTrans;
            TransBean NewTransBean;
            Transactor NewTransactor;
            Transactor NewBillTransactor;
            TransactorBean NewTransactorBean;

            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            if (pay.getPayId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(aPayReasId), "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(aPayReasId), "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayDate() == null) {
                msg = "Select Pay Date";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getBillTransactorId() == 0 && (aPayReasId == 25 || aPayReasId == 26 || aPayReasId == 91)) {
                msg = "Specify Client or Supplier";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayMethodId() == 0) {
                msg = "Invalid Payment Method";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getAccChildAccountId() == 0) {
                msg = "Invalid Cash Paid Account";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getAccChildAccountId2() == 0 && aPayReasId == 33) {
                msg = "Select Loan Acccount";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getAccChildAccountId2() == 0 && aPayReasId == 34) {
                msg = "Select Shareholder Acccount";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getCurrencyCode().length() <= 0) {
                msg = "Specify Currency Code";
                this.setActionMessage("Payment Not Saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else if (pay.getPaidAmount() <= 0) {
                msg = "Invalid Amount Paid";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aPayReasId == 25 && new PayTransBean().getRecGreaterBalStr(aPayTranss).length() > 0) {
                msg = "Amount Cannot be Greater Than Payable Balance ## " + new PayTransBean().getRecGreaterBalStr(aPayTranss);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (null == new AccPeriodBean().getAccPeriod(pay.getPayDate())) {
                this.setActionMessage("");
                msg = "Date Selected Does Not Match any Accounting Period";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Transaction NOT saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (!new AccLedgerBean().checkerBalancePass(pay.getPayMethodId(), pay.getAccChildAccountId(), pay.getCurrencyCode(), pay.getPaidAmount(), aPayTypeId, 0, 0, 0)) {
                msg = "Paying Account is Out of Funds";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Transaction NOT saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (pay.getPayRefNo().length() > 100 || pay.getStatusDesc().length() > 100) {
                msg = "Reference Number and Status Description Cannot Exceed 100 Characters";
                this.setActionMessage(ub.translateWordsInText(BaseName, "Payment Not Saved"));
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                pay.setAddUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                pay.setEditUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());//will be made null by the SP
                pay.setPayCategory(new GeneralUserSetting().getCurrentPayCategory());
                pay.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                pay.setDeletePayId(0);
                AccCurrency LocalCurrency = null;
                LocalCurrency = new AccCurrencyBean().getLocalCurrency();
                pay.setXRate(new AccXrateBean().getXrate(pay.getCurrencyCode(), LocalCurrency.getCurrencyCode()));
                pay.setPayTypeId(aPayTypeId);
                pay.setPayReasonId(aPayReasId);
                long SavedPayId = this.payInsertUpdate(pay);

                //manage session variable
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("CURRENT_PAY_ID", SavedPayId);
                //Refresh Print output
                new OutputDetailBean().refreshOutput("PARENT", "SOURCE-PAY");
                pay.setPayId(new GeneralUserSetting().getCurrentPayId());
                //insert pay transs items
                if (aPayReasId == 25 || aPayReasId == 26 || aPayReasId == 105) {
                    new PayTransBean().savePayTranss(pay, aPayTranss);
                }
                //insert Journal
                if (aPayReasId == 25 || aPayReasId == 26) {//CASH/CREDIT PURCHASE
                    new AccJournalBean().postJournalCashPaymentPurchase(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 33) {//LOAN
                    new AccJournalBean().postJournalCashPaymentLoan(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 34) {//CASH DRAWING
                    new AccJournalBean().postJournalCashPaymentDraw(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 91) {//PREPAID EXPENSE
                    new AccJournalBean().postJournalCashPaymentPrepaidExpense(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId());
                } else if (aPayReasId == 105) {//LIABILITY PAYMENT
                    new AccJournalBean().postJournalCashPaymentLiability(pay, new AccPeriodBean().getAccPeriod(pay.getPayDate()).getAccPeriodId(), aPayTranss);
                }
                this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                //Update Total Paid for Sales/Purchase Invoice
                if (aPayReasId == 25 || aPayReasId == 26 || aPayReasId == 105) {
                    new PayTransBean().updateTranssTotalPaid(aPayTranss);
                }
                this.clearPayPayTranss(pay, aPayTranss);
                this.PayAll = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public boolean updatePay(Pay pay) {
        String sql = "{call sp_update_pay(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_pay_id", pay.getPayId());
            cs.setDate("in_pay_date", new java.sql.Date(pay.getPayDate().getTime()));
            cs.setDouble("in_paid_amount", pay.getPaidAmount());
            cs.setInt("in_pay_method_id", pay.getPayMethodId());
            cs.setInt("in_edit_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
            cs.setTimestamp("in_edit_date", new java.sql.Timestamp(new java.util.Date().getTime()));
            cs.setDouble("in_points_spent_amount", pay.getPointsSpentAmount());
            if (CompanySetting.getSpendAmountPerPoint() > 0) {
                pay.setPointsSpent(pay.getPointsSpentAmount() / CompanySetting.getSpendAmountPerPoint());
            } else {
                pay.setPointsSpent(0);
            }
            cs.setDouble("in_points_spent", pay.getPointsSpent());
            cs.setLong("in_delete_pay_id", pay.getDeletePayId());
            cs.setString("in_pay_ref_no", pay.getPayRefNo());
            cs.setString("in_pay_category", pay.getPayCategory());
            cs.setLong("in_bill_transactor_id", pay.getBillTransactorId());
            cs.setInt("in_pay_type_id", pay.getPayTypeId());
            cs.setInt("in_pay_reason_id", pay.getPayReasonId());
            cs.setInt("in_store_id", pay.getStoreId());
            cs.setInt("in_acc_child_account_id", pay.getAccChildAccountId());
            cs.setInt("in_acc_child_account_id", pay.getAccChildAccountId2());
            cs.setString("in_currency_code", pay.getCurrencyCode());
            cs.setDouble("in_xrate", pay.getXRate());
            cs.setInt("in_status", pay.getStatus());
            cs.setString("in_status_desc", pay.getStatusDesc());
            cs.setDouble("in_principal_amount", pay.getPrincipalAmount());
            cs.setDouble("in_interest_amount", pay.getInterestAmount());
            cs.setString("in_pay_number", pay.getPay_number());
            cs.executeUpdate();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return false;
        }
    }

    public Pay getPay(long PayId) {
        String sql = "{call sp_search_pay_by_pay_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, PayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getPayFromResultset(rs);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public String getPaidLiabilityAccount(long PayId) {
        String sql = "SELECT * FROM pay_trans WHERE pay_id=" + PayId;
        ResultSet rs = null;
        String AccCode = "";
        String AccName = "";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    AccCode = rs.getString("account_code");
                } catch (Exception e) {
                    AccCode = "";
                }
            }
            if (AccCode.length() > 0) {
                AccName = new AccCoaBean().getAccCoaByCodeOrId(AccCode, 0).getAccountName();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return AccName;
    }

    public void setPayFromResultset(Pay pay, ResultSet rs) {
        try {
            try {
                pay.setPayId(rs.getLong("pay_id"));
            } catch (NullPointerException npe) {
                pay.setPayId(0);
            }
            try {
                pay.setPayDate(new Date(rs.getDate("pay_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setPayDate(null);
            }
            try {
                pay.setPaidAmount(rs.getDouble("paid_amount"));
            } catch (NullPointerException npe) {
                pay.setPaidAmount(0);
            }
            try {
                pay.setPayMethodId(rs.getInt("pay_method_id"));
            } catch (NullPointerException npe) {
                pay.setPayMethodId(0);
            }
            try {
                pay.setAddUserDetailId(rs.getInt("add_user_detail_id"));
            } catch (NullPointerException npe) {
                pay.setAddUserDetailId(0);
            }
            try {
                pay.setEditUserDetailId(rs.getInt("edit_user_detail_id"));
            } catch (NullPointerException npe) {
                pay.setEditUserDetailId(0);
            }
            try {
                pay.setAddDate(new Date(rs.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setAddDate(null);
            }
            try {
                pay.setEditDate(new Date(rs.getTimestamp("edit_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setEditDate(null);
            }
            try {
                pay.setPointsSpent(rs.getDouble("points_spent"));
            } catch (NullPointerException npe) {
                pay.setPointsSpent(0);
            }
            try {
                pay.setPointsSpentAmount(rs.getDouble("points_spent_amount"));
            } catch (NullPointerException npe) {
                pay.setPointsSpentAmount(0);
            }
            try {
                pay.setDeletePayId(rs.getLong("delete_pay_id"));
            } catch (NullPointerException npe) {
                pay.setDeletePayId(0);
            }
            try {
                pay.setPayRefNo(rs.getString("pay_ref_no"));
            } catch (NullPointerException npe) {
                pay.setPayRefNo("");
            }
            try {
                pay.setPayCategory(rs.getString("pay_category"));
            } catch (NullPointerException npe) {
                pay.setPayCategory("");
            }
            try {
                pay.setBillTransactorId(rs.getLong("bill_transactor_id"));
            } catch (NullPointerException npe) {
                pay.setBillTransactorId(0);
            }
            try {
                pay.setStoreId(rs.getInt("store_id"));
            } catch (NullPointerException npe) {
                pay.setStoreId(0);
            }
            try {
                pay.setAccChildAccountId(rs.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                pay.setAccChildAccountId(0);
            }
            try {
                pay.setAccChildAccountId2(rs.getInt("acc_child_account_id2"));
            } catch (NullPointerException npe) {
                pay.setAccChildAccountId2(0);
            }
            try {
                pay.setCurrencyCode(rs.getString("currency_code"));
            } catch (NullPointerException npe) {
                pay.setCurrencyCode("");
            }
            try {
                pay.setXRate(rs.getDouble("xrate"));
            } catch (NullPointerException npe) {
                pay.setXRate(0);
            }
            try {
                pay.setStatus(rs.getInt("status"));
            } catch (NullPointerException npe) {
                pay.setStatus(1);
            }
            try {
                pay.setStatusDesc(rs.getString("status_desc"));
            } catch (NullPointerException npe) {
                pay.setStatusDesc("");
            }
            try {
                pay.setPayTypeId(rs.getInt("pay_type_id"));
            } catch (NullPointerException npe) {
                pay.setPayTypeId(0);
            }
            try {
                pay.setPayReasonId(rs.getInt("pay_reason_id"));
            } catch (NullPointerException npe) {
                pay.setPayReasonId(0);
            }
            try {
                pay.setPrincipalAmount(rs.getDouble("principal_amount"));
            } catch (NullPointerException npe) {
                pay.setPrincipalAmount(0);
            }
            try {
                pay.setInterestAmount(rs.getDouble("interest_amount"));
            } catch (NullPointerException npe) {
                pay.setInterestAmount(0);
            }
            try {
                pay.setPay_number(rs.getString("pay_number"));
            } catch (NullPointerException npe) {
                pay.setPay_number("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public Pay getPayFromResultset(ResultSet rs) {
        try {

            Pay pay = new Pay();
            try {
                pay.setPayId(rs.getLong("pay_id"));
            } catch (NullPointerException npe) {
                pay.setPayId(0);
            }
            try {
                pay.setPayDate(new Date(rs.getDate("pay_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setPayDate(null);
            }
            try {
                pay.setPaidAmount(rs.getDouble("paid_amount"));
            } catch (NullPointerException npe) {
                pay.setPaidAmount(0);
            }
            try {
                pay.setPayMethodId(rs.getInt("pay_method_id"));
            } catch (NullPointerException npe) {
                pay.setPayMethodId(0);
            }
            try {
                pay.setAddUserDetailId(rs.getInt("add_user_detail_id"));
            } catch (NullPointerException npe) {
                pay.setAddUserDetailId(0);
            }
            try {
                pay.setEditUserDetailId(rs.getInt("edit_user_detail_id"));
            } catch (NullPointerException npe) {
                pay.setEditUserDetailId(0);
            }
            try {
                pay.setAddDate(new Date(rs.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setAddDate(null);
            }
            try {
                pay.setEditDate(new Date(rs.getTimestamp("edit_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setEditDate(null);
            }
            try {
                pay.setPointsSpent(rs.getDouble("points_spent"));
            } catch (NullPointerException npe) {
                pay.setPointsSpent(0);
            }
            try {
                pay.setPointsSpentAmount(rs.getDouble("points_spent_amount"));
            } catch (NullPointerException npe) {
                pay.setPointsSpentAmount(0);
            }
            try {
                pay.setDeletePayId(rs.getLong("delete_pay_id"));
            } catch (NullPointerException npe) {
                pay.setDeletePayId(0);
            }
            try {
                pay.setPayRefNo(rs.getString("pay_ref_no"));
            } catch (NullPointerException npe) {
                pay.setPayRefNo("");
            }
            try {
                pay.setPayCategory(rs.getString("pay_category"));
            } catch (NullPointerException npe) {
                pay.setPayCategory("");
            }
            try {
                pay.setBillTransactorId(rs.getLong("bill_transactor_id"));
            } catch (NullPointerException npe) {
                pay.setBillTransactorId(0);
            }
            try {
                pay.setStoreId(rs.getInt("store_id"));
            } catch (NullPointerException npe) {
                pay.setStoreId(0);
            }
            try {
                pay.setAccChildAccountId(rs.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                pay.setAccChildAccountId(0);
            }
            try {
                pay.setAccChildAccountId2(rs.getInt("acc_child_account_id2"));
            } catch (NullPointerException npe) {
                pay.setAccChildAccountId2(0);
            }
            try {
                pay.setCurrencyCode(rs.getString("currency_code"));
            } catch (NullPointerException npe) {
                pay.setCurrencyCode("");
            }
            try {
                pay.setXRate(rs.getDouble("xrate"));
            } catch (NullPointerException npe) {
                pay.setXRate(0);
            }
            try {
                pay.setStatus(rs.getInt("status"));
            } catch (NullPointerException npe) {
                pay.setStatus(1);
            }
            try {
                pay.setStatusDesc(rs.getString("status_desc"));
            } catch (NullPointerException npe) {
                pay.setStatusDesc("");
            }
            try {
                pay.setPayTypeId(rs.getInt("pay_type_id"));
            } catch (NullPointerException npe) {
                pay.setPayTypeId(0);
            }
            try {
                pay.setPayReasonId(rs.getInt("pay_reason_id"));
            } catch (NullPointerException npe) {
                pay.setPayReasonId(0);
            }
            try {
                pay.setPrincipalAmount(rs.getDouble("principal_amount"));
            } catch (NullPointerException npe) {
                pay.setPrincipalAmount(0);
            }
            try {
                pay.setInterestAmount(rs.getDouble("interest_amount"));
            } catch (NullPointerException npe) {
                pay.setInterestAmount(0);
            }
            try {
                pay.setPay_number(rs.getString("pay_number"));
            } catch (NullPointerException npe) {
                pay.setPay_number("");
            }
            return pay;
        } catch (Exception e) {
            return null;
        }
    }

    public static Pay getPayFromResultset2(ResultSet rs) {
        try {

            Pay pay = new Pay();
            try {
                pay.setPayId(rs.getLong("pay_id"));
            } catch (NullPointerException npe) {
                pay.setPayId(0);
            }
            try {
                pay.setPayDate(new Date(rs.getDate("pay_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setPayDate(null);
            }
            try {
                pay.setPaidAmount(rs.getDouble("paid_amount"));
            } catch (NullPointerException npe) {
                pay.setPaidAmount(0);
            }
            try {
                pay.setPayMethodId(rs.getInt("pay_method_id"));
            } catch (NullPointerException npe) {
                pay.setPayMethodId(0);
            }
            try {
                pay.setAddUserDetailId(rs.getInt("add_user_detail_id"));
            } catch (NullPointerException npe) {
                pay.setAddUserDetailId(0);
            }
            try {
                pay.setEditUserDetailId(rs.getInt("edit_user_detail_id"));
            } catch (NullPointerException npe) {
                pay.setEditUserDetailId(0);
            }
            try {
                pay.setAddDate(new Date(rs.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setAddDate(null);
            }
            try {
                pay.setEditDate(new Date(rs.getTimestamp("edit_date").getTime()));
            } catch (NullPointerException npe) {
                pay.setEditDate(null);
            }
            try {
                pay.setPointsSpent(rs.getDouble("points_spent"));
            } catch (NullPointerException npe) {
                pay.setPointsSpent(0);
            }
            try {
                pay.setPointsSpentAmount(rs.getDouble("points_spent_amount"));
            } catch (NullPointerException npe) {
                pay.setPointsSpentAmount(0);
            }
            try {
                pay.setDeletePayId(rs.getLong("delete_pay_id"));
            } catch (NullPointerException npe) {
                pay.setDeletePayId(0);
            }
            try {
                pay.setPayRefNo(rs.getString("pay_ref_no"));
            } catch (NullPointerException npe) {
                pay.setPayRefNo("");
            }
            try {
                pay.setPayCategory(rs.getString("pay_category"));
            } catch (NullPointerException npe) {
                pay.setPayCategory("");
            }
            try {
                pay.setBillTransactorId(rs.getLong("bill_transactor_id"));
            } catch (NullPointerException npe) {
                pay.setBillTransactorId(0);
            }
            try {
                pay.setStoreId(rs.getInt("store_id"));
            } catch (NullPointerException npe) {
                pay.setStoreId(0);
            }
            try {
                pay.setAccChildAccountId(rs.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                pay.setAccChildAccountId(0);
            }
            try {
                pay.setAccChildAccountId2(rs.getInt("acc_child_account_id2"));
            } catch (NullPointerException npe) {
                pay.setAccChildAccountId2(0);
            }
            try {
                pay.setCurrencyCode(rs.getString("currency_code"));
            } catch (NullPointerException npe) {
                pay.setCurrencyCode("");
            }
            try {
                pay.setXRate(rs.getDouble("xrate"));
            } catch (NullPointerException npe) {
                pay.setXRate(0);
            }
            try {
                pay.setStatus(rs.getInt("status"));
            } catch (NullPointerException npe) {
                pay.setStatus(1);
            }
            try {
                pay.setStatusDesc(rs.getString("status_desc"));
            } catch (NullPointerException npe) {
                pay.setStatusDesc("");
            }
            try {
                pay.setPayTypeId(rs.getInt("pay_type_id"));
            } catch (NullPointerException npe) {
                pay.setPayTypeId(0);
            }
            try {
                pay.setPayReasonId(rs.getInt("pay_reason_id"));
            } catch (NullPointerException npe) {
                pay.setPayReasonId(0);
            }
            try {
                pay.setPrincipalAmount(rs.getDouble("principal_amount"));
            } catch (NullPointerException npe) {
                pay.setPrincipalAmount(0);
            }
            try {
                pay.setInterestAmount(rs.getDouble("interest_amount"));
            } catch (NullPointerException npe) {
                pay.setInterestAmount(0);
            }
            try {
                pay.setPay_number(rs.getString("pay_number"));
            } catch (NullPointerException npe) {
                pay.setPay_number("");
            }
            return pay;
        } catch (Exception e) {
            return null;
        }
    }

    public void setPayById(long PayId, Pay pay) {
        String sql = "{call sp_search_pay_by_pay_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, PayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                pay = this.getPayFromResultset(rs);
            } else {
                this.clearPay(pay);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            this.clearPay(pay);
        }
    }

    public int getCountPayByDeletePayId(long aDeletePayId) {
        String sql = "{call sp_search_pay_count_by_delete_pay_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aDeletePayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count_delete_pay");
            } else {
                return 0;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return 0;
        }
    }

    public boolean isAllowCancelPay(Pay aPay) {
        if (aPay.getBillTransactorId() == 0 || (aPay.getPaidAmount() <= 0 && aPay.getPointsSpentAmount() <= 0)) {
            return false;
        } else {
            return true;
        }
    }

    public void deletePay(Pay pay) {
        String sql = "DELETE FROM pay WHERE pay_id=?";
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Delete", new FacesMessage(msg));
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, pay.getPayId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.clearPay(pay);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage("Pay NOT deleted");
            }
        }
    }

    public void cancelPayV2(Pay aPay, List<PayTrans> aPayTransList) {
        //1. copy
        //2. Zero/nullfy/cancel pay
        //3. zero/nullfy/vancel pay trans
        //4. reverse pay ledger
        String sql = null;
        String msg = "";
        boolean isPayCopySuccess = false;
        boolean isPayTransCopySuccess = false;
        boolean isPayUpdateSuccess = false;
        boolean isPayTransUpdateSuccess = false;
        Pay newPay = new Pay();
        List<PayTrans> newPayTransList = new ArrayList<>();
        long PayHistId = 0;
        long PayTransHistId = 0;
        int hasReversed = 0;
        long x = 0;
        TransactionType tt = new TransactionTypeBean().getTransactionType(aPay.getPayTypeId());

        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        //if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, grb.getFcnName(aPay.getPayTypeId(), aPay.getPayReasonId()), "Edit") == 0) {
        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(aPay.getPayReasonId()), "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            this.setActionMessage("Record NOT Cancelled");
        } else if (null == aPay || aPay.getPaidAmount() <= 0) {
            msg = "INVALID RECORD FOR CANCELLATION...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            this.setActionMessage("Cash Receipt NOT Cancelled");
        } else {
            newPay = new PayBean().getPay(aPay.getPayId());
            //copy pay and pay trans
            //copy pay
            sql = "{call sp_copy_pay(?,?,?)}";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                cs.setLong("in_pay_id", aPay.getPayId());
                cs.setString("in_hist_flag", "Cancel");
                cs.registerOutParameter("out_pay_hist_id", VARCHAR);
                cs.executeUpdate();
                PayHistId = cs.getLong("out_pay_hist_id");
                isPayCopySuccess = true;
            } catch (Exception e) {
                isPayCopySuccess = false;
                LOGGER.log(Level.ERROR, e);
            }
            //copy paytrans
            if (isPayCopySuccess) {
                if (aPay.getPayReasonId() == 22 || aPay.getPayReasonId() == 25 || aPay.getPayReasonId() == 115) {
                    int i = 0;
                    int n = aPayTransList.size();
                    //now copy item by item
                    sql = "{call sp_copy_pay_trans(?,?,?)}";
                    try (
                            Connection conn = DBConnection.getMySQLConnection();
                            CallableStatement cs = conn.prepareCall(sql);) {
                        while (i < n) {
                            cs.setLong("in_pay_id", newPay.getPayId());
                            cs.setLong("in_pay_hist_id", PayHistId);
                            cs.setLong("in_pay_trans_id", aPayTransList.get(i).getPayTransId());
                            cs.executeUpdate();
                            i = i + 1;
                        }
                        isPayTransCopySuccess = true;
                    } catch (Exception e) {
                        isPayTransCopySuccess = false;
                        LOGGER.log(Level.ERROR, e);
                    }
                } else {
                    isPayTransCopySuccess = true;
                }
            }
            //update pay
            if (isPayCopySuccess && isPayTransCopySuccess) {
                newPay.setPaidAmount(0);
                newPay.setPointsSpentAmount(0);
                newPay.setPointsSpent(0);
                newPay.setPrincipalAmount(0);
                newPay.setInterestAmount(0);
                newPay.setEditUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                newPay.setEditDate(new CompanySetting().getCURRENT_SERVER_DATE());
                x = new PayBean().payInsertUpdate(newPay);
                if (x > 0) {
                    isPayUpdateSuccess = true;
                }
            }
            //update paytrans
            if (isPayUpdateSuccess) {
                if (aPay.getPayReasonId() == 22 || aPay.getPayReasonId() == 25 || aPay.getPayReasonId() == 115) {
                    int i = 0;
                    newPayTransList = new PayTransBean().getPayTranssByPayId(aPay.getPayId());
                    int n = newPayTransList.size();
                    while (i < n) {
                        newPayTransList.get(i).setTransPaidAmount(0);
                        new PayTransBean().savePayTrans(newPayTransList.get(i));
                        i = i + 1;
                    }
                    isPayTransUpdateSuccess = true;
                } else {
                    isPayTransUpdateSuccess = true;
                }
            }
            //Reverse Journal Entry for the Payment
            Trans aEmptyTrans = new Trans();
            if (isPayTransUpdateSuccess && isPayTransUpdateSuccess) {
                //hasReversed = new AccJournalBean().postJournalReverse(aEmptyTrans, aPay);
                //for cash receipts
                if (aPay.getPayReasonId() == 22) {
                    new AccJournalBean().postJournalCashReceiptReceivableCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                if (aPay.getPayReasonId() == 23) {
                    new AccJournalBean().postJournalCashReceiptCapitalCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                if (aPay.getPayReasonId() == 24) {
                    new AccJournalBean().postJournalCashReceiptLoanCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                if (aPay.getPayReasonId() == 90) {
                    new AccJournalBean().postJournalCashReceiptPrepaidIncomeCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                //for cash payments
                if (aPay.getPayReasonId() == 25) {
                    new AccJournalBean().postJournalCashPaymentPurchaseCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                if (aPay.getPayReasonId() == 33) {
                    new AccJournalBean().postJournalCashPaymentLoanCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                if (aPay.getPayReasonId() == 34) {
                    new AccJournalBean().postJournalCashPaymentDrawCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                if (aPay.getPayReasonId() == 91) {
                    new AccJournalBean().postJournalCashPaymentPrepaidExpenseCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId());
                }
                if (aPay.getPayReasonId() == 115) {
                    new AccJournalBean().postJournalCashReceiptOtherRevenueCANCEL(aPay, new AccPeriodBean().getAccPeriod(aPay.getPayDate()).getAccPeriodId(), aPayTransList);
                }
                hasReversed = 1;
            }

            if (isPayUpdateSuccess && isPayTransUpdateSuccess && hasReversed == 1) {
                this.setActionMessage("Transaction Cancelled Successfully");
            } else {
                this.setActionMessage("Transaction NOT Cancelled");
            }
            //Update Total Paid for Sales/Purchase Invoice
            new PayTransBean().updateTranssTotalPaid(aPayTransList);
        }
    }

    public void displayPay(Pay PayFrom, Pay PayTo) {
        PayTo.setPayId(PayFrom.getPayId());
        PayTo.setPayDate(PayFrom.getPayDate());
        PayTo.setPaidAmount(PayFrom.getPaidAmount());
        PayTo.setPayMethodId(PayFrom.getPayMethodId());
        PayTo.setAddUserDetailId(PayFrom.getAddUserDetailId());
        PayTo.setEditUserDetailId(PayFrom.getEditUserDetailId());
        PayTo.setAddDate(PayFrom.getAddDate());
        PayTo.setEditDate(PayFrom.getEditDate());
        PayTo.setPointsSpent(PayFrom.getPointsSpent());
        PayTo.setPointsSpentAmount(PayFrom.getPointsSpentAmount());
        PayTo.setDeletePayId(PayFrom.getDeletePayId());
    }

    public void clearPay(Pay pay) {
        pay.setPayId(0);
        pay.setPayDate(null);
        pay.setPaidAmount(0);
        //pay.setPayMethodId(0);
        pay.setPayMethodId(new PayMethodBean().getPayMethodDefault().getPayMethodId());
        pay.setAddUserDetailId(0);
        pay.setEditUserDetailId(0);
        pay.setAddDate(null);
        pay.setEditDate(null);
        pay.setPointsSpent(0);
        pay.setPointsSpentAmount(0);
        pay.setDeletePayId(0);
        pay.setPayRefNo("");
        pay.setPayCategory("");
        pay.setBillTransactorId(0);
        this.setSelectedBillTransactor(null);
        pay.setPayTypeId(0);
        pay.setPayReasonId(0);
        pay.setStoreId(0);
        pay.setAccChildAccountId(0);
        pay.setAccChildAccountId2(0);
        //pay.setCurrencyCode("");
        pay.setCurrencyCode(new AccCurrencyBean().getLocalCurrency().getCurrencyCode());
        pay.setXRate(1);
        pay.setStatus(1);
        pay.setStatusDesc("");
        pay.setPrincipalAmount(0);
        pay.setInterestAmount(0);
        pay.setPay_number("");
    }

    public void initClearPay(Pay pay, Transactor transactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                pay.setPayId(0);
                pay.setPayDate(null);
                pay.setPaidAmount(0);
                pay.setPayMethodId(0);
                pay.setAddUserDetailId(0);
                pay.setEditUserDetailId(0);
                pay.setAddDate(null);
                pay.setEditDate(null);
                pay.setPointsSpent(0);
                pay.setPointsSpentAmount(0);
                pay.setDeletePayId(0);
                pay.setPayRefNo("");
                pay.setPayCategory("");
                pay.setBillTransactorId(0);
                pay.setPayTypeId(0);
                pay.setPayReasonId(0);
                pay.setStoreId(0);
                pay.setAccChildAccountId(0);
                pay.setAccChildAccountId2(0);
                //pay.setCurrencyCode("");
                pay.setXRate(1);
                pay.setStatus(1);
                pay.setStatusDesc("");
                pay.setPayTypeId(0);
                pay.setPayReasonId(0);
                pay.setPrincipalAmount(0);
                pay.setInterestAmount(0);
                pay.setPay_number("");
                new TransactorBean().clearTransactor(transactor);
            } catch (Exception e) {
            }
        }
    }

    public void initClearPayReceipt() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("CURRENT_PAY_ID", 0);
            } catch (Exception e) {
            }
        }
    }

    public void clearPayPayTranss(Pay pay, List<PayTrans> aPayTranss) {
        if (pay != null) {
            pay.setPayId(0);
            pay.setPayDate(new CompanySetting().getCURRENT_SERVER_DATE());
            pay.setPaidAmount(0);
            pay.setPayMethodId(0);
            pay.setAddUserDetailId(0);
            pay.setEditUserDetailId(0);
            pay.setAddDate(null);
            pay.setEditDate(null);
            pay.setPointsSpent(0);
            pay.setPointsSpentAmount(0);
            pay.setDeletePayId(0);
            pay.setPayRefNo("");
            pay.setPayCategory("");
            pay.setBillTransactorId(0);
            this.setSelectedBillTransactor(null);
            pay.setPayTypeId(0);
            pay.setPayReasonId(0);
            pay.setStoreId(0);
            pay.setAccChildAccountId(0);
            pay.setAccChildAccountId2(0);
            //pay.setCurrencyCode("");
            pay.setXRate(1);
            pay.setStatus(1);
            pay.setStatusDesc("");
            pay.setPayTypeId(0);
            pay.setPayReasonId(0);
            pay.setPrincipalAmount(0);
            pay.setInterestAmount(0);
            pay.setPay_number("");
            pay.setAccountBalance(0);
        }
        if (aPayTranss != null) {
            aPayTranss.clear();
        }
    }

    public void clearPayPayTranss(Pay pay, List<PayTrans> aPayTranss, PayBean aPayBean, Trans aTrans) {
        if (pay != null) {
            pay.setPayId(0);
            pay.setPayDate(new CompanySetting().getCURRENT_SERVER_DATE());
            pay.setPaidAmount(0);
            pay.setPayMethodId(0);
            pay.setAddUserDetailId(0);
            pay.setEditUserDetailId(0);
            pay.setAddDate(null);
            pay.setEditDate(null);
            pay.setPointsSpent(0);
            pay.setPointsSpentAmount(0);
            pay.setDeletePayId(0);
            pay.setPayRefNo("");
            pay.setPayCategory("");
            pay.setBillTransactorId(0);
            this.setSelectedBillTransactor(null);
            pay.setPayTypeId(0);
            pay.setPayReasonId(0);
            pay.setStoreId(0);
            pay.setAccChildAccountId(0);
            pay.setAccChildAccountId2(0);
            //pay.setCurrencyCode("");
            pay.setXRate(1);
            pay.setStatus(1);
            pay.setStatusDesc("");
            pay.setPayTypeId(0);
            pay.setPayReasonId(0);
            pay.setPrincipalAmount(0);
            pay.setInterestAmount(0);
            pay.setPay_number("");
            pay.setAccountBalance(0);
        }
        if (aPayTranss != null) {
            aPayTranss.clear();
        }
        if (null != aPayBean) {
            aPayBean.setDate1(null);
            aPayBean.setDate2(null);
        }
        if (null != aTrans) {
            aTrans.setTransactionNumber2("");
        }
    }

    public void initClearPayPayTranss(Pay pay, Transactor transactor, List<PayTrans> aPayTranss) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (pay != null) {
                pay.setPayId(0);
                pay.setPayDate(new CompanySetting().getCURRENT_SERVER_DATE());
                pay.setPaidAmount(0);
                pay.setPayMethodId(0);
                pay.setAddUserDetailId(0);
                pay.setEditUserDetailId(0);
                pay.setAddDate(null);
                pay.setEditDate(null);
                pay.setPointsSpent(0);
                pay.setPointsSpentAmount(0);
                pay.setDeletePayId(0);
                pay.setPayRefNo("");
                pay.setPayCategory("");
                pay.setBillTransactorId(0);
                this.setSelectedBillTransactor(null);
                pay.setPayTypeId(0);
                pay.setPayReasonId(0);
                pay.setStoreId(0);
                pay.setAccChildAccountId(0);
                pay.setAccChildAccountId2(0);
                //pay.setCurrencyCode("");
                pay.setXRate(1);
                pay.setStatus(1);
                pay.setStatusDesc("");
                pay.setPayTypeId(0);
                pay.setPayReasonId(0);
                pay.setPrincipalAmount(0);
                pay.setInterestAmount(0);
                pay.setPay_number("");
                pay.setAccountBalance(0);
            }
            new TransactorBean().clearTransactor(transactor);
            if (aPayTranss != null) {
                aPayTranss.clear();
            }
        }
    }

    public void initCurrencyCode(int aTransTypeId, Pay pay) {
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            String DefaultCurrencyCode = "";
            String TransTypeCurrencyCode = "";
            String LocalCurrencyCode = "";

            try {
                LocalCurrencyCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
                if (null == LocalCurrencyCode) {
                    LocalCurrencyCode = "";
                }
            } catch (NullPointerException npe) {
                LocalCurrencyCode = "";
            }
            try {
                DefaultCurrencyCode = new GeneralUserSetting().getDEFAULT_CURRENCY_CODE();
                if (null == DefaultCurrencyCode) {
                    DefaultCurrencyCode = "";
                }
            } catch (NullPointerException npe) {
                DefaultCurrencyCode = "";
            }
            try {
                TransTypeCurrencyCode = transtype.getDefault_currency_code();
                if (null == TransTypeCurrencyCode) {
                    TransTypeCurrencyCode = "";
                }
            } catch (NullPointerException npe) {
                TransTypeCurrencyCode = "";
            }

            if (TransTypeCurrencyCode.length() > 0) {
                pay.setCurrencyCode(TransTypeCurrencyCode);
            } else if (DefaultCurrencyCode.length() > 0) {
                pay.setCurrencyCode(DefaultCurrencyCode);
            } else {
                pay.setCurrencyCode(LocalCurrencyCode);
            }
        } catch (NullPointerException npe) {
            pay.setCurrencyCode("");
        }
    }

    public void initClearPayPayTranss(Pay pay, Transactor transactor, List<PayTrans> aPayTranss, PayBean aPayBean, Trans aTrans) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (pay != null) {
                pay.setPayId(0);
                pay.setPayDate(new CompanySetting().getCURRENT_SERVER_DATE());
                pay.setPaidAmount(0);
                pay.setPayMethodId(0);
                pay.setAddUserDetailId(0);
                pay.setEditUserDetailId(0);
                pay.setAddDate(null);
                pay.setEditDate(null);
                pay.setPointsSpent(0);
                pay.setPointsSpentAmount(0);
                pay.setDeletePayId(0);
                pay.setPayRefNo("");
                pay.setPayCategory("");
                pay.setBillTransactorId(0);
                this.setSelectedBillTransactor(null);
                pay.setPayTypeId(0);
                pay.setPayReasonId(0);
                pay.setStoreId(0);
                pay.setAccChildAccountId(0);
                pay.setAccChildAccountId2(0);
                //pay.setCurrencyCode("");
                pay.setXRate(1);
                pay.setStatus(1);
                pay.setStatusDesc("");
                pay.setPayTypeId(0);
                pay.setPayReasonId(0);
                pay.setPrincipalAmount(0);
                pay.setInterestAmount(0);
                pay.setPay_number("");
            }
            new TransactorBean().clearTransactor(transactor);
            if (aPayTranss != null) {
                aPayTranss.clear();
            }
            if (null != aPayBean) {
                aPayBean.setDate1(null);
                aPayBean.setDate2(null);
            }
            if (null != aTrans) {
                aTrans.setTransactionNumber2("");
            }
        }
    }

    /**
     * @return the Pays
     */
    public List<Pay> getPays() {
        String sql;
        if (this.SearchPay.length() > 0) {
            sql = "SELECT * FROM pay WHERE pay_id=" + this.SearchPay + " ORDER BY pay_id DESC LIMIT 5";
        } else {
            sql = "SELECT * FROM pay ORDER BY pay_id DESC LIMIT 5";
        }
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public List<Pay> getPaysByTransaction(long TransId) {
        String sql = "{call sp_search_pay_by_transaction_id(?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TransId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public List<Pay> getPaysByTransIdTransType(long TransId, int aTransTypeId) {
        String sql = "{call sp_search_pay_by_transaction_id_type(?,?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TransId);
            ps.setInt(2, aTransTypeId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public List<Pay> getPaysByTransIdPayCat(long TransId, String aPayCat) {
        String sql = "{call sp_search_pay_by_transaction_id_paycat(?,?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TransId);
            ps.setString(2, aPayCat);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public List<Pay> getPaysByTransNoPayCat(String TransNo, String aPayCat) {
        String sql = "{call sp_search_pay_by_transaction_number_paycat(?,?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, TransNo);
            ps.setString(2, aPayCat);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public Pay getTransactionFirstPay(long aTransactionId) {
        String sql = "{call sp_search_pay_first_by_transaction_id(?)}";
        ResultSet rs = null;
        Pay pay = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            if (rs.next()) {
                pay = new Pay();
                pay = PayBean.getPayFromResultset2(rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return pay;
    }

    public Pay getTransactionFirstPayByTransNo(String aTransactionNumber) {
        String sql = "{call sp_search_pay_first_by_transaction_number(?)}";
        ResultSet rs = null;
        Pay pay = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransactionNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                pay = new Pay();
                pay = PayBean.getPayFromResultset2(rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return pay;
    }

    public List<Pay> getPaysByTransactor(long TracId) {
        String sql = "{call sp_search_pay_by_transactor_id(?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TracId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public List<Pay> getPaysByTrTcTt(long aTransactionId, long aTransactorId, String aTransactorType) {
        List<Pay> aPays = new ArrayList<Pay>();
        aPays.clear();
        if (aTransactorId != 0) {
            aPays = this.getPaysByTransactorTransType(aTransactorId, aTransactorType);
        } else if (aTransactionId != 0) {
            if (aTransactorType.equals("CUSTOMER") || aTransactorType.equals("SCHEME")) {
                aPays = new PayBean().getPaysByTransIdTransType(aTransactionId, 2);//SALE
            } else if (aTransactorType.equals("SUPPLIER")) {
                aPays = new PayBean().getPaysByTransIdTransType(aTransactionId, 1);//PURCHASE
            }
        }
        return aPays;
    }

    public List<Pay> getPaysByBillTrTcTt(long aTransactionId, long aBillTransactorId, String aTransactorType) {
        List<Pay> aPays = new ArrayList<Pay>();
        aPays.clear();
        if (aBillTransactorId != 0) {
            aPays = this.getPaysByBillTransactorTransType(aBillTransactorId, aTransactorType);
        } else if (aTransactionId != 0) {
            if (aTransactorType.equals("CUSTOMER") || aTransactorType.equals("SCHEME")) {
                aPays = new PayBean().getPaysByTransIdTransType(aTransactionId, 2);//SALE
            } else if (aTransactorType.equals("SUPPLIER")) {
                aPays = new PayBean().getPaysByTransIdTransType(aTransactionId, 1);//PURCHASE
            }
        }
        return aPays;
    }

    public List<Pay> getPaysByBillTrPaCa(long aTransactionId, long aBillTransactorId, String aPayCat) {
        List<Pay> aPays = new ArrayList<Pay>();
        aPays.clear();
        if (aBillTransactorId != 0) {
            aPays = this.getPaysByBillTransactorPayCat(aBillTransactorId, aPayCat);
        } else if (aTransactionId != 0) {
            aPays = new PayBean().getPaysByTransIdPayCat(aTransactionId, aPayCat);
        }
        return aPays;
    }

    public List<Pay> getPaysByBillTrPaCaTransNo(String aTransactionNumber, long aBillTransactorId, String aPayCat) {
        List<Pay> aPays = new ArrayList<Pay>();
        aPays.clear();
        if (aBillTransactorId != 0) {
            aPays = this.getPaysByBillTransactorPayCat(aBillTransactorId, aPayCat);
        } else if (aTransactionNumber.length() > 0) {
            aPays = new PayBean().getPaysByTransNoPayCat(aTransactionNumber, aPayCat);
        }
        return aPays;
    }

    public List<Pay> getPaysByTransactorTransType(long TracId, String aTransactorType) {
        String sql = "{call sp_search_pay_by_transactor_transtype(?,?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TracId);
            if (aTransactorType.equals("CUSTOMER") || aTransactorType.equals("SCHEME")) {
                ps.setInt(2, 2);
            } else if (aTransactorType.equals("SUPPLIER")) {
                ps.setInt(2, 1);
            } else {
                ps.setInt(2, 33);//nothing
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public List<Pay> getPaysByBillTransactorTransType(long TracId, String aTransactorType) {
        String sql = "{call sp_search_pay_by_bill_transactor_transtype(?,?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TracId);
            if (aTransactorType.equals("CUSTOMER") || aTransactorType.equals("SCHEME")) {
                ps.setInt(2, 2);
            } else if (aTransactorType.equals("SUPPLIER")) {
                ps.setInt(2, 1);
            } else {
                ps.setInt(2, 33);//nothing
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public List<Pay> getPaysByBillTransactorPayCat(long aBillTransactorId, String aPayCategory) {
        String sql = "{call sp_search_pay_by_bill_transactor_paycat(?,?)}";
        ResultSet rs = null;
        Pays = new ArrayList<Pay>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aBillTransactorId);
            ps.setString(2, aPayCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pays.add(this.getPayFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Pays;
    }

    public long getOverPayIdReadyForTransFULL(long aBillTransactorId, String aPayCategory, Double aTransBalToPayAmount) {
        String sql = "{call sp_search_pay_total_trans_bal(?,?)}";
        ResultSet rs = null;
        Pay pay = null;
        long payid = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aBillTransactorId);
            ps.setString(2, aPayCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                pay = new Pay();
                try {
                    pay.setPayId(rs.getLong("pay_id"));
                } catch (NullPointerException npe) {
                    pay.setPayId(0);
                }
                try {
                    pay.setPaidAmount(rs.getDouble("balance"));
                } catch (NullPointerException npe) {
                    pay.setPaidAmount(0);
                }
                //Note: PaidAmount==Balance
                if (pay.getPaidAmount() >= aTransBalToPayAmount) {
                    payid = pay.getPayId();
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return payid;
    }

    public Pay getOverPayIdReadyForTransPART(long aBillTransactorId, String aPayCategory, Double aTransBalToPayAmount) {
        String sql = "{call sp_search_pay_total_trans_bal(?,?)}";
        ResultSet rs = null;
        Pay pay = null;
        Pay returnpay = null;
        //long payid = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aBillTransactorId);
            ps.setString(2, aPayCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                pay = new Pay();
                try {
                    pay.setPayId(rs.getLong("pay_id"));
                } catch (NullPointerException npe) {
                    pay.setPayId(0);
                }
                try {
                    pay.setPaidAmount(rs.getDouble("balance"));
                } catch (NullPointerException npe) {
                    pay.setPaidAmount(0);
                }
                //Note: PaidAmount==Balance
                //if (pay.getPaidAmount() >= aTransAmount) {
                //    payid = pay.getPayId();
                //    break;
                //}
                if (aTransBalToPayAmount > 0 && aTransBalToPayAmount <= pay.getPaidAmount()) {
                    System.out.println("Full-happen");
                    returnpay = new Pay();
                    returnpay.setPayId(pay.getPayId());
                    returnpay.setPaidAmount(aTransBalToPayAmount);
                    returnpay.setStatus(1);
                    break;
                } else if (aTransBalToPayAmount > 0 && aTransBalToPayAmount > pay.getPaidAmount()) {
                    System.out.println("Part-happen");
                    returnpay = new Pay();
                    returnpay.setPayId(pay.getPayId());
                    returnpay.setPaidAmount(pay.getPaidAmount());
                    returnpay.setStatus(2);
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return returnpay;
    }

    public void ViewReceiptVoucher(long aPayId) {
        //manage session variables
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("CURRENT_PAY_ID", aPayId);
        //: org.primefaces.context.RequestContext.getCurrentInstance().openDialog("PayReceipt.xhtml", null, null);
        org.primefaces.PrimeFaces.current().dialog().openDynamic("PayReceipt.xhtml", null, null);
    }

    public void ViewCashReceipt(long aPayId) {
        try {
            //manage session variables
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("CURRENT_PAY_ID", aPayId);
            //org.primefaces.context.RequestContext.getCurrentInstance().openDialog("CashReceipt.xhtml", null, null);
            //Refresh Print output
            new OutputDetailBean().refreshOutput("PARENT", "SOURCE-PAY");
            TransactionType tt = new TransactionTypeBean().getTransactionType(this.getPay(aPayId).getPayTypeId());
            String out_file_name = new TransBean().getPrintFileName("PARENT", tt, 0);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("modal", true);
            options.put("draggable", false);
            options.put("resizable", false);
            options.put("width", 600);
            options.put("height", 300);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("scrollable", true);
            options.put("maximizable", true);
            options.put("dynamic", true);
            //: org.primefaces.context.RequestContext.getCurrentInstance().openDialog(out_file_name, options, null);
            org.primefaces.PrimeFaces.current().dialog().openDynamic(out_file_name, options, null);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void ViewCashPayment(long aPayId) {
        //manage session variables
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("CURRENT_PAY_ID", aPayId);
        //org.primefaces.context.RequestContext.getCurrentInstance().openDialog("CashPayment.xhtml", null, null);
        org.primefaces.PrimeFaces.current().dialog().openDynamic("CashPayment.xhtml", null, null);
    }

    public int getTransTypeIdByPayCat(String aPayCat) {
        int x = 0;
        if (aPayCat.equals("IN")) {//SALES INVOICE
            x = 2;
        } else if (aPayCat.equals("OUT")) {//PURCHASE INVOICE
            x = 1;
        }
        return x;
    }

    public void setDateToToday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();
        this.setDate1(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getDate1());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate1(cal.getTime());

        this.setDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getDate2());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate2(cal2.getTime());
    }

    public void setDateToYesturday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        this.setDate1(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getDate1());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate1(cal.getTime());

        this.setDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getDate2());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate2(cal2.getTime());
    }

    public void setDateToToday(Pay aPay) {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        aPay.setAddDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(aPay.getAddDate());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setAddDate(cal.getTime());

        aPay.setAddDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(aPay.getAddDate2());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setAddDate2(cal2.getTime());
    }

    public void setTransDateToToday(Pay aPay) {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        aPay.setPayDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(aPay.getPayDate());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setPayDate(cal.getTime());

        aPay.setPayDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(aPay.getPayDate2());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setPayDate2(cal2.getTime());
    }

    public void setDateToYesturday(Pay aPay) {
        //Date CurrentServerDate=new CompanySetting().getCURRENT_SERVER_DATE();
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        aPay.setAddDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(aPay.getAddDate());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setAddDate(cal.getTime());

        aPay.setAddDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(aPay.getAddDate2());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setAddDate2(cal2.getTime());
    }

    public void setTransDateToYesturday(Pay aPay) {
        //Date CurrentServerDate=new CompanySetting().getCURRENT_SERVER_DATE();
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        aPay.setPayDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(aPay.getPayDate());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setPayDate(cal.getTime());

        aPay.setPayDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(aPay.getPayDate2());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        aPay.setPayDate2(cal2.getTime());
    }

    public void initResetPayReportDetail(Trans aTrans, Pay aPay, PayBean aPayBean, Transactor aBillTransactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetPayReportDetail(aTrans, aPay, aPayBean, aBillTransactor);
        }
    }

    public void resetPayReportDetail(Trans aTrans, Pay aPay, PayBean aPayBean, Transactor aBillTransactor) {
        aPayBean.setActionMessage("");
        try {
            new TransBean().clearTrans(aTrans);
        } catch (NullPointerException npe) {
        }
        try {
            this.clearPay(aPay);
            aPay.setPayMethodId(0);
            aPay.setCurrencyCode("");
        } catch (NullPointerException npe) {
        }
        try {
            new TransactorBean().clearTransactor(aBillTransactor);
        } catch (NullPointerException npe) {
        }
        try {
            aPayBean.setDateType("");
            aPayBean.setDate1(null);
            aPayBean.setDate2(null);
            aPayBean.setFieldName("");
            aPayBean.PayList.clear();
            aPayBean.PayListSummary.clear();
        } catch (NullPointerException npe) {
        }
    }

    public void reportCashReceiptDetail(Trans aTrans, Pay aPay, PayBean aPayBean) {
        if (aPayBean.getDateType().length() == 0) {
            aPayBean.setDateType("Add Date");
        }
        aPayBean.setActionMessage("");
        ResultSet rs = null;
        this.PayList = new ArrayList<>();
        this.PayListSummary = new ArrayList<>();
        String sql = "SELECT * FROM pay WHERE pay_type_id=14 AND pay_method_id!=6";
        String sqlsum = "";
        if (aPayBean.getFieldName().length() > 0) {
            sqlsum = "SELECT " + aPayBean.getFieldName() + ",acc_child_account_id,currency_code,sum(paid_amount) as paid_amount FROM pay WHERE pay_type_id=14 AND pay_method_id!=6 ";
        } else {
            sqlsum = "SELECT acc_child_account_id,currency_code,sum(paid_amount) as paid_amount FROM pay WHERE pay_type_id=14 AND pay_method_id!=6 ";
        }
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = "";
        if (aPayBean.getFieldName().length() > 0) {
            groupbysql = " GROUP BY " + aPayBean.getFieldName() + ",acc_child_account_id,currency_code";
        } else {
            groupbysql = " GROUP BY acc_child_account_id,currency_code";
        }
        if (aPay.getPay_number().length() > 0) {
            wheresql = wheresql + " AND pay_number='" + aPay.getPay_number() + "'";
        }
        if (aPay.getStoreId() > 0) {
            wheresql = wheresql + " AND store_id=" + aPay.getStoreId();
        }
        if (aTrans.getTransactionNumber().length() > 0) {
            wheresql = wheresql + " AND pay_id IN(select pt.pay_id from pay_trans pt where pt.transaction_number='" + aTrans.getTransactionNumber() + "')";
        }
        if (aPay.getAddUserDetailId() > 0) {
            wheresql = wheresql + " AND add_user_detail_id=" + aPay.getAddUserDetailId();
        }
        if (aPay.getPayReasonId() > 0) {
            wheresql = wheresql + " AND pay_reason_id=" + aPay.getPayReasonId();
        }
        if (aPay.getPayMethodId() > 0) {
            wheresql = wheresql + " AND pay_method_id=" + aPay.getPayMethodId();
        }
        if (aPay.getBillTransactorId() > 0) {
            wheresql = wheresql + " AND bill_transactor_id=" + aPay.getBillTransactorId();
        }
        if (aPay.getAccChildAccountId2() > 0) {
            wheresql = wheresql + " AND acc_child_account_id2=" + aPay.getAccChildAccountId2();
        }
        if (aPayBean.getDateType().length() > 0 && aPayBean.getDate1() != null && aPayBean.getDate2() != null) {
            switch (aPayBean.getDateType()) {
                case "Receipt Date":
                    wheresql = wheresql + " AND pay_date BETWEEN '" + new java.sql.Date(aPayBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aPayBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aPayBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aPayBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        ordersql = " ORDER BY add_date DESC,pay_id DESC";
        if (aPayBean.getFieldName().length() > 0) {
            ordersqlsum = " ORDER BY " + aPayBean.getFieldName() + ",acc_child_account_id,currency_code";
        } else {
            ordersqlsum = " ORDER BY acc_child_account_id,currency_code";
        }
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Pay pay = null;
            while (rs.next()) {
                pay = new Pay();
                this.setPayFromResultset(pay, rs);
                this.updateLookupPay(pay);
                this.PayList.add(pay);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            Pay paysum = null;
            while (rs.next()) {
                paysum = new Pay();
                if (aPayBean.getFieldName().length() > 0) {
                    switch (aPayBean.getFieldName()) {
                        case "add_user_detail_id":
                            try {
                                paysum.setAddUserDetailId(rs.getInt("add_user_detail_id"));
                            } catch (NullPointerException npe) {
                                paysum.setAddUserDetailId(0);
                            }
                            break;
                        case "pay_reason_id":
                            try {
                                paysum.setPayReasonId(rs.getInt("pay_reason_id"));
                            } catch (NullPointerException npe) {
                                paysum.setPayReasonId(0);
                            }
                            break;
                        case "acc_child_account_id2":
                            try {
                                paysum.setAccChildAccountId2(rs.getInt("acc_child_account_id2"));
                            } catch (NullPointerException npe) {
                                paysum.setAccChildAccountId2(0);
                            }
                            break;
                        case "bill_transactor_id":
                            try {
                                paysum.setBillTransactorId(rs.getLong("bill_transactor_id"));
                            } catch (NullPointerException npe) {
                                paysum.setBillTransactorId(0);
                            }
                            break;
                        case "pay_method_id":
                            try {
                                paysum.setPayMethodId(rs.getInt("pay_method_id"));
                            } catch (NullPointerException npe) {
                                paysum.setPayMethodId(0);
                            }
                            break;
                        case "pay_date":
                            try {
                                paysum.setPayDate(new Date(rs.getDate("pay_date").getTime()));
                            } catch (NullPointerException | SQLException npe) {
                                paysum.setPayDate(null);
                            }
                            break;
                    }
                }
                try {
                    paysum.setAccChildAccountId(rs.getInt("acc_child_account_id"));
                } catch (NullPointerException npe) {
                    paysum.setAccChildAccountId(0);
                }
                try {
                    paysum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    paysum.setCurrencyCode("");
                }
                try {
                    paysum.setPaidAmount(rs.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    paysum.setPaidAmount(0);
                }
                this.PayListSummary.add(paysum);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void reportCashPaymentDetail(Trans aTrans, Pay aPay, PayBean aPayBean) {
        if (aPayBean.getDateType().length() == 0) {
            aPayBean.setDateType("Add Date");
        }
        aPayBean.setActionMessage("");
        ResultSet rs = null;
        this.PayList = new ArrayList<>();
        this.PayListSummary = new ArrayList<>();
        String sql = "SELECT * FROM pay WHERE pay_type_id=15 AND pay_method_id!=7";
        String sqlsum = "";
        if (aPayBean.getFieldName().length() > 0) {
            sqlsum = "SELECT " + aPayBean.getFieldName() + ",acc_child_account_id,currency_code,sum(paid_amount) as paid_amount FROM pay WHERE pay_type_id=15 AND pay_method_id!=7";
        } else {
            sqlsum = "SELECT acc_child_account_id,currency_code,sum(paid_amount) as paid_amount FROM pay WHERE pay_type_id=15 AND pay_method_id!=7";
        }
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = "";
        if (aPayBean.getFieldName().length() > 0) {
            groupbysql = " GROUP BY " + aPayBean.getFieldName() + ",acc_child_account_id,currency_code";
        } else {
            groupbysql = " GROUP BY acc_child_account_id,currency_code";
        }
        if (aPay.getPay_number().length() > 0) {
            wheresql = wheresql + " AND pay_number='" + aPay.getPay_number() + "'";
        }
        if (aPay.getPayMethodId() > 0) {
            wheresql = wheresql + " AND pay_method_id=" + aPay.getPayMethodId();
        }
        if (aPay.getStoreId() > 0) {
            wheresql = wheresql + " AND store_id=" + aPay.getStoreId();
        }
        if (aTrans.getTransactionNumber().length() > 0) {
            wheresql = wheresql + " AND pay_id IN(select pt.pay_id from pay_trans pt where pt.transaction_number='" + aTrans.getTransactionNumber() + "')";
        }
        if (aPay.getAddUserDetailId() > 0) {
            wheresql = wheresql + " AND add_user_detail_id=" + aPay.getAddUserDetailId();
        }
        if (aPay.getPayReasonId() > 0) {
            wheresql = wheresql + " AND pay_reason_id=" + aPay.getPayReasonId();
        }
        if (aPay.getBillTransactorId() > 0) {
            wheresql = wheresql + " AND bill_transactor_id=" + aPay.getBillTransactorId();
        }
        if (aPay.getAccChildAccountId2() > 0) {
            wheresql = wheresql + " AND acc_child_account_id2=" + aPay.getAccChildAccountId2();
        }
        if (aPayBean.getDateType().length() > 0 && aPayBean.getDate1() != null && aPayBean.getDate2() != null) {
            switch (aPayBean.getDateType()) {
                case "Pay Date":
                    wheresql = wheresql + " AND pay_date BETWEEN '" + new java.sql.Date(aPayBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aPayBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aPayBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aPayBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        ordersql = " ORDER BY add_date DESC,pay_id DESC";
        if (aPayBean.getFieldName().length() > 0) {
            ordersqlsum = " ORDER BY " + aPayBean.getFieldName() + ",acc_child_account_id,currency_code";
        } else {
            ordersqlsum = " ORDER BY acc_child_account_id,currency_code";
        }
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Pay pay = null;
            while (rs.next()) {
                pay = new Pay();
                this.setPayFromResultset(pay, rs);
                this.updateLookupPay(pay);
                this.PayList.add(pay);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            Pay paysum = null;
            while (rs.next()) {
                paysum = new Pay();
                if (aPayBean.getFieldName().length() > 0) {
                    switch (aPayBean.getFieldName()) {
                        case "add_user_detail_id":
                            try {
                                paysum.setAddUserDetailId(rs.getInt("add_user_detail_id"));
                            } catch (NullPointerException npe) {
                                paysum.setAddUserDetailId(0);
                            }
                            break;
                        case "pay_reason_id":
                            try {
                                paysum.setPayReasonId(rs.getInt("pay_reason_id"));
                            } catch (NullPointerException npe) {
                                paysum.setPayReasonId(0);
                            }
                            break;
                        case "acc_child_account_id2":
                            try {
                                paysum.setAccChildAccountId2(rs.getInt("acc_child_account_id2"));
                            } catch (NullPointerException npe) {
                                paysum.setAccChildAccountId2(0);
                            }
                            break;
                        case "bill_transactor_id":
                            try {
                                paysum.setBillTransactorId(rs.getLong("bill_transactor_id"));
                            } catch (NullPointerException npe) {
                                paysum.setBillTransactorId(0);
                            }
                            break;
                        case "pay_method_id":
                            try {
                                paysum.setPayMethodId(rs.getInt("pay_method_id"));
                            } catch (NullPointerException npe) {
                                paysum.setPayMethodId(0);
                            }
                            break;
                        case "pay_date":
                            try {
                                paysum.setPayDate(new Date(rs.getDate("pay_date").getTime()));
                            } catch (NullPointerException | SQLException npe) {
                                paysum.setPayDate(null);
                            }
                            break;
                    }
                }
                try {
                    paysum.setAccChildAccountId(rs.getInt("acc_child_account_id"));
                } catch (NullPointerException npe) {
                    paysum.setAccChildAccountId(0);
                }
                try {
                    paysum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    paysum.setCurrencyCode("");
                }
                try {
                    paysum.setPaidAmount(rs.getDouble("paid_amount"));
                } catch (NullPointerException npe) {
                    paysum.setPaidAmount(0);
                }
                this.PayListSummary.add(paysum);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void updateLookupPay(Pay aPay) {
        if (null != aPay) {
            aPay.setPaidForSummary(new PayTransBean().getPaidForSummary(aPay.getPayId()));
        }
    }

    public void initPaySession(long aPayId, String aAction) {
        //first set current selection in session
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("CURRENT_PAY_ID", aPayId);
        httpSession.setAttribute("CURRENT_TRANSACTION_ACTION", aAction);
        this.ActionType = aAction;
        this.PayObj = new PayBean().getPay(aPayId);
        this.PayTransList = new PayTransBean().getPayTranssByPayId(aPayId);
        //refresh output
        new OutputDetailBean().refreshOutput("PARENT", "SOURCE-PAY");
    }

    public String getPaysByTransactionStr(long aTransId) {
        String PayNumbers = "";
        String sql = "SELECT p.pay_number FROM pay_trans pt INNER JOIN pay p ON pt.pay_id=p.pay_id "
                + "WHERE pt.trans_paid_amount>0 AND pt.transaction_id=" + aTransId;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (PayNumbers.length() > 0) {
                    PayNumbers = PayNumbers + "," + rs.getString("pay_number");
                } else {
                    PayNumbers = rs.getString("pay_number");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return PayNumbers;
    }

    public String getPrintoutJsfFile(int aTranstypeId, int aOverrideVersion) {
        String the_file = "";
        switch (aTranstypeId) {
            case 1:
                the_file = "TransactionViewPI.xhtml";
                break;
            case 2:
                if (aOverrideVersion == 0) {
                    if (CompanySetting.getSalesReceiptVersion() == 1) {//1-Small Width
                        the_file = "TransactionViewSI1.xhtml";
                    } else if (CompanySetting.getSalesReceiptVersion() == 2) {//2-A4 Size
                        the_file = "TransactionViewSI2.xhtml";
                    } else if (CompanySetting.getSalesReceiptVersion() == 3) {//3-Very Small Width
                        the_file = "TransactionViewSI3.xhtml";
                    }
                } else if (aOverrideVersion > 0) {
                    if (aOverrideVersion == 1) {//1-Small Width
                        the_file = "TransactionViewSI1.xhtml";
                    } else if (aOverrideVersion == 2) {//2-A4 Size
                        the_file = "TransactionViewSI2.xhtml";
                    } else if (aOverrideVersion == 3) {//3-Very Small Width
                        the_file = "TransactionViewSI3.xhtml";
                    }
                }
                break;
            case 3:
                the_file = "TransactionViewDS.xhtml";
                break;
            case 4:
                the_file = "TransactionViewST.xhtml";
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                the_file = "TransactionViewPO.xhtml";
                break;
            case 9:
                the_file = "TransactionViewGRN.xhtml";
                break;
            case 10:
                the_file = "TransactionViewSQ.xhtml";
                break;
            case 11:
                the_file = "TransactionViewSO.xhtml";
                break;
            case 12:
                the_file = "TransactionViewGDN.xhtml";
                break;
            case 13:
                the_file = "TransactionViewSTR.xhtml";
                break;
            case 14://Cash Receipt
                the_file = "CashReceipt.xhtml";
                break;
            case 15://Cash Payment
                the_file = "CashPayment.xhtml";
                break;
        }

        return the_file;
    }

    public String getStatus(int aStatusId) {
        if (aStatusId == 0) {
            return "Not Cleared";
        } else if (aStatusId == 1) {
            return "Cleared";
        } else {
            return "";
        }
    }

    public void doPayAll(List<PayTrans> aPayTranss, boolean aPayAll, Pay aPay) {
        int ListItemIndex = 0;
        int ListItemNo = 0;
        double GTotal = 0;
        try {
            ListItemNo = aPayTranss.size();
        } catch (NullPointerException npe) {
            ListItemNo = 0;
        }
        while (ListItemIndex < ListItemNo) {
            if (aPayAll) {
                aPayTranss.get(ListItemIndex).setTransPaidAmount(aPayTranss.get(ListItemIndex).getGrandTotal() - aPayTranss.get(ListItemIndex).getSumTransPaidAmount());
            } else {
                aPayTranss.get(ListItemIndex).setTransPaidAmount(0);
            }
            GTotal = GTotal + (aPayTranss.get(ListItemIndex).getTransPaidAmount());
            ListItemIndex = ListItemIndex + 1;
        }
        aPay.setPaidAmount(GTotal);
    }

    /**
     * @param Pays the Pays to set
     */
    public void setPays(List<Pay> Pays) {
        this.Pays = Pays;
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
     * @return the SelectedPay
     */
    public Pay getSelectedPay() {
        return SelectedPay;
    }

    /**
     * @param SelectedPay the SelectedPay to set
     */
    public void setSelectedPay(Pay SelectedPay) {
        this.SelectedPay = SelectedPay;
    }

    /**
     * @return the SelectedPayId
     */
    public long getSelectedPayId() {
        return SelectedPayId;
    }

    /**
     * @param SelectedPayId the SelectedPayId to set
     */
    public void setSelectedPayId(long SelectedPayId) {
        this.SelectedPayId = SelectedPayId;
    }

    /**
     * @return the SearchPay
     */
    public String getSearchPay() {
        return SearchPay;
    }

    /**
     * @param SearchPay the SearchPay to set
     */
    public void setSearchPay(String SearchPay) {
        this.SearchPay = SearchPay;
    }

    /**
     * @return the TypedTransactorName
     */
    public String getTypedTransactorName() {
        return TypedTransactorName;
    }

    /**
     * @param TypedTransactorName the TypedTransactorName to set
     */
    public void setTypedTransactorName(String TypedTransactorName) {
        this.TypedTransactorName = TypedTransactorName;
    }

    /**
     * @return the SelectedTransactor
     */
    public Transactor getSelectedTransactor() {
        return SelectedTransactor;
    }

    /**
     * @param SelectedTransactor the SelectedTransactor to set
     */
    public void setSelectedTransactor(Transactor SelectedTransactor) {
        this.SelectedTransactor = SelectedTransactor;
    }

    /**
     * @return the SelectedBillTransactor
     */
    public Transactor getSelectedBillTransactor() {
        return SelectedBillTransactor;
    }

    /**
     * @param SelectedBillTransactor the SelectedBillTransactor to set
     */
    public void setSelectedBillTransactor(Transactor SelectedBillTransactor) {
        this.SelectedBillTransactor = SelectedBillTransactor;
    }

    /**
     * @return the SearchBy
     */
    public String getSearchBy() {
        return SearchBy;
    }

    /**
     * @param SearchBy the SearchBy to set
     */
    public void setSearchBy(String SearchBy) {
        this.SearchBy = SearchBy;
    }

    /**
     * @return the DateType
     */
    public String getDateType() {
        return DateType;
    }

    /**
     * @param DateType the DateType to set
     */
    public void setDateType(String DateType) {
        this.DateType = DateType;
    }

    /**
     * @return the Date1
     */
    public Date getDate1() {
        return Date1;
    }

    /**
     * @param Date1 the Date1 to set
     */
    public void setDate1(Date Date1) {
        this.Date1 = Date1;
    }

    /**
     * @return the Date2
     */
    public Date getDate2() {
        return Date2;
    }

    /**
     * @param Date2 the Date2 to set
     */
    public void setDate2(Date Date2) {
        this.Date2 = Date2;
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
     * @return the PayList
     */
    public List<Pay> getPayList() {
        return PayList;
    }

    /**
     * @param PayList the PayList to set
     */
    public void setPayList(List<Pay> PayList) {
        this.PayList = PayList;
    }

    /**
     * @return the PayListSummary
     */
    public List<Pay> getPayListSummary() {
        return PayListSummary;
    }

    /**
     * @param PayListSummary the PayListSummary to set
     */
    public void setPayListSummary(List<Pay> PayListSummary) {
        this.PayListSummary = PayListSummary;
    }

    /**
     * @return the PayObj
     */
    public Pay getPayObj() {
        return PayObj;
    }

    /**
     * @param PayObj the PayObj to set
     */
    public void setPayObj(Pay PayObj) {
        this.PayObj = PayObj;
    }

    /**
     * @return the PayTransList
     */
    public List<PayTrans> getPayTransList() {
        return PayTransList;
    }

    /**
     * @param PayTransList the PayTransList to set
     */
    public void setPayTransList(List<PayTrans> PayTransList) {
        this.PayTransList = PayTransList;
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
     * @return the OverridePrintVersion
     */
    public int getOverridePrintVersion() {
        return OverridePrintVersion;
    }

    /**
     * @param OverridePrintVersion the OverridePrintVersion to set
     */
    public void setOverridePrintVersion(int OverridePrintVersion) {
        this.OverridePrintVersion = OverridePrintVersion;
    }

    /**
     * @return the PayAll
     */
    public boolean isPayAll() {
        return PayAll;
    }

    /**
     * @param PayAll the PayAll to set
     */
    public void setPayAll(boolean PayAll) {
        this.PayAll = PayAll;
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
