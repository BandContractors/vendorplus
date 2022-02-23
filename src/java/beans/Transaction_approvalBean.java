package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.GroupRight;
import entities.Trans;
import entities.Transaction_approval;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "transaction_approvalBean")
@SessionScoped
public class Transaction_approvalBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Transaction_approvalBean.class.getName());

    private int filterStore = 0;
    private int filterTransactionType = 0;
    private int filterRequestedBy = 0;
    private Date filterRequestDate = new CompanySetting().getCURRENT_SERVER_DATE();
    private int filterApprovalStatus = -1;
    private List<Transaction_approval> transaction_approvalList;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setTransaction_approvalFromResultset(Transaction_approval aTransaction_approval, ResultSet aResultSet) {
        try {
            try {
                aTransaction_approval.setTransaction_approval_id(aResultSet.getLong("transaction_approval_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setTransaction_approval_id(0);
            }
            try {
                aTransaction_approval.setTransaction_hist_id(aResultSet.getLong("transaction_hist_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setTransaction_hist_id(0);
            }
            try {
                aTransaction_approval.setTransaction_type_id(aResultSet.getInt("transaction_type_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setTransaction_type_id(0);
            }
            try {
                aTransaction_approval.setTransaction_reason_id(aResultSet.getInt("transaction_reason_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setTransaction_reason_id(0);
            }
            try {
                aTransaction_approval.setRequest_date(new Date(aResultSet.getTimestamp("request_date").getTime()));
            } catch (NullPointerException npe) {
                aTransaction_approval.setRequest_date(null);
            }
            try {
                aTransaction_approval.setRequest_by_id(aResultSet.getInt("request_by_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setRequest_by_id(0);
            }
            try {
                aTransaction_approval.setApproval_status(aResultSet.getInt("approval_status"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setApproval_status(0);
            }
            try {
                aTransaction_approval.setStatus_date(new Date(aResultSet.getTimestamp("status_date").getTime()));
            } catch (NullPointerException npe) {
                aTransaction_approval.setStatus_date(null);
            }
            try {
                aTransaction_approval.setStatus_desc(aResultSet.getString("status_desc"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setStatus_desc("");
            }
            try {
                aTransaction_approval.setStatus_by_id(aResultSet.getInt("status_by_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setStatus_by_id(0);
            }
            try {
                aTransaction_approval.setGrand_total(aResultSet.getDouble("grand_total"));
            } catch (Exception e) {
                aTransaction_approval.setGrand_total(0);
            }
            try {
                aTransaction_approval.setTransactor_names(aResultSet.getString("transactor_names"));
            } catch (Exception e) {
                aTransaction_approval.setTransactor_names("");
            }
            try {
                aTransaction_approval.setCurrency_code(aResultSet.getString("currency_code"));
            } catch (Exception e) {
                aTransaction_approval.setCurrency_code("");
            }
            try {
                aTransaction_approval.setStore_id(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setStore_id(0);
            }
            try {
                aTransaction_approval.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setTransactor_id(0);
            }
            try {
                aTransaction_approval.setTransaction_id(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                aTransaction_approval.setTransaction_id(0);
            }
            try {
                aTransaction_approval.setAmount_tendered(aResultSet.getDouble("amount_tendered"));
            } catch (Exception e) {
                aTransaction_approval.setAmount_tendered(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public long insertTransaction_approval(Transaction_approval aTransaction_approval) {
        long InsertedId = 0;
        String sql = "INSERT INTO transaction_approval "
                + "(transaction_hist_id,transaction_type_id,transaction_reason_id,"
                + "request_date,request_by_id,approval_status,status_date,status_desc,status_by_id,"
                + "transactor_id,store_id,amount_tendered,grand_total) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (null != aTransaction_approval) {
                try {
                    ps.setLong(1, aTransaction_approval.getTransaction_hist_id());
                } catch (Exception e) {
                    ps.setLong(1, 0);
                }
                try {
                    ps.setInt(2, aTransaction_approval.getTransaction_type_id());
                } catch (Exception e) {
                    ps.setInt(2, 0);
                }
                try {
                    ps.setInt(3, aTransaction_approval.getTransaction_reason_id());
                } catch (Exception e) {
                    ps.setInt(3, 0);
                }
                try {
                    ps.setTimestamp(4, new java.sql.Timestamp(aTransaction_approval.getRequest_date().getTime()));
                } catch (Exception e) {
                    ps.setTimestamp(4, null);
                }
                try {
                    ps.setInt(5, aTransaction_approval.getRequest_by_id());
                } catch (Exception e) {
                    ps.setInt(5, 0);
                }
                try {
                    ps.setInt(6, aTransaction_approval.getApproval_status());
                } catch (Exception e) {
                    ps.setInt(6, 0);
                }
                try {
                    ps.setTimestamp(7, new java.sql.Timestamp(aTransaction_approval.getStatus_date().getTime()));
                } catch (Exception e) {
                    ps.setTimestamp(7, null);
                }
                try {
                    ps.setString(8, aTransaction_approval.getStatus_desc());
                } catch (Exception e) {
                    ps.setString(8, "");
                }
                try {
                    ps.setInt(9, aTransaction_approval.getStatus_by_id());
                } catch (Exception e) {
                    ps.setInt(9, 0);
                }
                //transactor_id,store_id,amount_tendered,grand_total
                try {
                    ps.setLong(10, aTransaction_approval.getTransactor_id());
                } catch (Exception e) {
                    ps.setLong(10, 0);
                }
                try {
                    ps.setInt(11, aTransaction_approval.getStore_id());
                } catch (Exception e) {
                    ps.setInt(11, 0);
                }
                try {
                    ps.setDouble(12, aTransaction_approval.getAmount_tendered());
                } catch (Exception e) {
                    ps.setDouble(12, 0);
                }
                try {
                    ps.setDouble(13, aTransaction_approval.getGrand_total());
                } catch (Exception e) {
                    ps.setDouble(13, 0);
                }
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    InsertedId = rs.getLong(1);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return InsertedId;
    }

    public int updateTransaction_approval(Transaction_approval aTransaction_approval) {
        int Updated = 0;
        String sql = "UPDATE transaction_approval SET "
                + "approval_status=?,status_date=?,status_desc=?,status_by_id=? "
                + "WHERE transaction_approval_id=" + aTransaction_approval.getTransaction_approval_id();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (null != aTransaction_approval) {
                try {
                    ps.setInt(1, aTransaction_approval.getApproval_status());
                } catch (Exception e) {
                    ps.setInt(1, 0);
                }
                try {
                    ps.setTimestamp(2, new java.sql.Timestamp(aTransaction_approval.getStatus_date().getTime()));
                } catch (Exception e) {
                    ps.setTimestamp(2, null);
                }
                try {
                    ps.setString(3, aTransaction_approval.getStatus_desc());
                } catch (Exception e) {
                    ps.setString(3, "");
                }
                try {
                    ps.setInt(4, aTransaction_approval.getStatus_by_id());
                } catch (Exception e) {
                    ps.setInt(4, 0);
                }
                ps.executeUpdate();
                Updated = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Updated;
    }

    public int updateTransaction_approval(Transaction_approval aTransaction_approval, long aTransaction_id) {
        int Updated = 0;
        String sql = "UPDATE transaction_approval SET "
                + "approval_status=?,status_date=?,status_desc=?,status_by_id=?,transaction_id=? "
                + "WHERE transaction_approval_id=" + aTransaction_approval.getTransaction_approval_id();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (null != aTransaction_approval) {
                try {
                    ps.setInt(1, aTransaction_approval.getApproval_status());
                } catch (Exception e) {
                    ps.setInt(1, 0);
                }
                try {
                    ps.setTimestamp(2, new java.sql.Timestamp(aTransaction_approval.getStatus_date().getTime()));
                } catch (Exception e) {
                    ps.setTimestamp(2, null);
                }
                try {
                    ps.setString(3, aTransaction_approval.getStatus_desc());
                } catch (Exception e) {
                    ps.setString(3, "");
                }
                try {
                    ps.setInt(4, aTransaction_approval.getStatus_by_id());
                } catch (Exception e) {
                    ps.setInt(4, 0);
                }
                try {
                    ps.setLong(5, aTransaction_id);
                } catch (Exception e) {
                    ps.setLong(5, 0);
                }
                ps.executeUpdate();
                Updated = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Updated;
    }

    public int markProcessed(long aTransaction_approval_id, long aTransaction_id) {
        int Updated = 0;

        try {
            Transaction_approval ta = new Transaction_approval();
            ta.setTransaction_approval_id(aTransaction_approval_id);
            //approval_status: 0 Submitted, 1 Approved, 2 Processed, 3 Rejected, 4 Recalled
            ta.setApproval_status(2);
            ta.setStatus_by_id(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            ta.setStatus_date(new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ta.setStatus_desc("Processed");
            Updated = this.updateTransaction_approval(ta, aTransaction_id);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Updated;
    }

    public void markApprovedCall(Transaction_approval aTransaction_approval) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();
            int CanApproveSalesInvoice = 0;
            String msg = "";
            if (aTransaction_approval.getTransaction_type_id() == 2) {
                CanApproveSalesInvoice = grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(130), "Add");
            }
            if (CanApproveSalesInvoice == 1) {
                Transaction_approval ta = this.getTransaction_approval(aTransaction_approval.getTransaction_approval_id());
                if (null != ta && ta.getApproval_status() != 0) {
                    msg = "Transaction Cannot be Approved";
                } else {
                    int x = this.markApproved(aTransaction_approval.getTransaction_approval_id());
                    if (x == 1) {
                        msg = "Approved Successfully";
                        this.getFilteredTransactionApprovals();
                    }
                }
            } else {
                msg = "Access Denied";
            }
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int markApproved(long aTransaction_approval_id) {
        int Updated = 0;

        try {
            Transaction_approval ta = new Transaction_approval();
            ta.setTransaction_approval_id(aTransaction_approval_id);
            //approval_status: 0 Submitted, 1 Approved, 2 Processed, 3 Rejected, 4 Recalled
            ta.setApproval_status(1);
            ta.setStatus_by_id(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            ta.setStatus_date(new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ta.setStatus_desc("Approved");
            Updated = this.updateTransaction_approval(ta);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Updated;
    }

    public void markRejectedCall(Transaction_approval aTransaction_approval) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();
            int CanRejectSalesInvoice = 0;
            String msg = "";
            if (aTransaction_approval.getTransaction_type_id() == 2) {
                CanRejectSalesInvoice = grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(130), "Add");
            }
            if (CanRejectSalesInvoice == 1) {
                Transaction_approval ta = this.getTransaction_approval(aTransaction_approval.getTransaction_approval_id());
                if (null != ta && ta.getApproval_status() != 0) {
                    msg = "Transaction Cannot be Rejected";
                } else {
                    int x = this.markRejected(aTransaction_approval.getTransaction_approval_id());
                    if (x == 1) {
                        msg = "Rejected Successfully";
                        this.getFilteredTransactionApprovals();
                    }
                }
            } else {
                msg = "Access Denied";
            }
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int markRejected(long aTransaction_approval_id) {
        int Updated = 0;

        try {
            Transaction_approval ta = new Transaction_approval();
            ta.setTransaction_approval_id(aTransaction_approval_id);
            //approval_status: 0 Submitted, 1 Approved, 2 Processed, 3 Rejected, 4 Recalled
            ta.setApproval_status(3);
            ta.setStatus_by_id(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            ta.setStatus_date(new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ta.setStatus_desc("Rejected");
            Updated = this.updateTransaction_approval(ta);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Updated;
    }

    public int markRecalled(long aTransaction_approval_id) {
        int Updated = 0;

        try {
            Transaction_approval ta = new Transaction_approval();
            ta.setTransaction_approval_id(aTransaction_approval_id);
            //approval_status: 0 Submitted, 1 Approved, 2 Processed, 3 Rejected, 4 Recalled
            ta.setApproval_status(4);
            ta.setStatus_by_id(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            ta.setStatus_date(new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ta.setStatus_desc("Recalled");
            Updated = this.updateTransaction_approval(ta);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Updated;
    }

    public void insertTransaction_approvalCall(long aTransHistId) {
        try {
            Trans transhist = new Trans();
            Transaction_approval transapp = new Transaction_approval();
            try {
                transhist = new TransBean().getTransFromHist(aTransHistId);
            } catch (Exception e) {
                //
            }
            if (null != transhist) {
                transapp.setTransaction_hist_id(transhist.getTransactionHistId());
                transapp.setTransaction_type_id(transhist.getTransactionTypeId());
                transapp.setTransaction_reason_id(transhist.getTransactionReasonId());
                transapp.setRequest_date(transhist.getAddDate());
                transapp.setRequest_by_id(transhist.getAddUserDetailId());
                transapp.setApproval_status(0);
                transapp.setStatus_date(transhist.getAddDate());
                //approval_status: 0 Submitted, 1 Approved, 2 Processed, 3 Rejected, 4 Recalled
                transapp.setStatus_desc("Submitted");
                transapp.setStatus_by_id(transhist.getAddUserDetailId());
                transapp.setGrand_total(transhist.getGrandTotal());
                transapp.setAmount_tendered(transhist.getAmountTendered());
                transapp.setStore_id(transhist.getStoreId());
                transapp.setTransactor_id(transhist.getTransactorId());
                long x = this.insertTransaction_approval(transapp);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public Transaction_approval getTransaction_approval(long aTransAppId) {
        String sql = "SELECT * FROM transaction_approval WHERE transaction_approval_id=" + aTransAppId;
        ResultSet rs = null;
        Transaction_approval ta = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ta = new Transaction_approval();
                this.setTransaction_approvalFromResultset(ta, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ta;
    }

    public void recallApprovalCall(List<Transaction_approval> aList, int aStoreId, int aRequestById, int aTransTypeId, int aTransReasonId, long aTransaction_approval_id) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            Transaction_approval ta = this.getTransaction_approval(aTransaction_approval_id);
            if (null != ta && ta.getApproval_status() != 0) {
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, "This Transaction Cannot be Recalled")));
            } else {
                int x = this.markRecalled(aTransaction_approval_id);
                this.refreshTransaction_approvalList(aList, aStoreId, aRequestById, aTransTypeId, aTransReasonId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshTransaction_approvalList(List<Transaction_approval> aList, int aStoreId, int aRequestById, int aTransTypeId, int aTransReasonId) {
        try {
            aList.clear();
        } catch (NullPointerException npe) {
            aList = new ArrayList<>();
        }
        //approval_status: 0 Submitted, 1 Approved, 2 Processed, 3 Rejected, 4 Recalled
        String sql;
        sql = "SELECT ta.*,'' AS transactor_names,'' AS currency_code FROM transaction_approval ta "
                + "WHERE ta.approval_status IN(0,1,3) AND ta.transaction_type_id=? AND ta.transaction_reason_id=? AND "
                + "ta.store_id=? AND ta.request_by_id=? "
                + "ORDER BY transaction_approval_id DESC LIMIT 10";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aTransTypeId);
            ps.setInt(2, aTransReasonId);
            ps.setInt(3, aStoreId);
            ps.setInt(4, aRequestById);
            rs = ps.executeQuery();
            Transaction_approval ta = null;
            while (rs.next()) {
                ta = new Transaction_approval();
                this.setTransaction_approvalFromResultset(ta, rs);
                aList.add(ta);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displayTransactionApproval(Transaction_approval Transaction_approvalFrom, Transaction_approval Transaction_approvalTo) {
        try {
            this.clearTransaction_approval(Transaction_approvalTo);
            Transaction_approvalTo.setTransaction_approval_id(Transaction_approvalFrom.getTransaction_approval_id());
            Transaction_approvalTo.setTransaction_hist_id(Transaction_approvalFrom.getTransaction_hist_id());
            Transaction_approvalTo.setTransaction_type_id(Transaction_approvalFrom.getTransaction_type_id());
            Transaction_approvalTo.setTransaction_reason_id(Transaction_approvalFrom.getTransaction_reason_id());
            Transaction_approvalTo.setRequest_date(Transaction_approvalFrom.getRequest_date());
            Transaction_approvalTo.setRequest_by_id(Transaction_approvalFrom.getRequest_by_id());
            Transaction_approvalTo.setApproval_status(Transaction_approvalFrom.getApproval_status());
            Transaction_approvalTo.setStatus_date(Transaction_approvalFrom.getStatus_date());
            Transaction_approvalTo.setStatus_desc(Transaction_approvalFrom.getStatus_desc());
            Transaction_approvalTo.setStatus_by_id(Transaction_approvalFrom.getStatus_by_id());
            Transaction_approvalTo.setGrand_total(Transaction_approvalFrom.getGrand_total());
            Transaction_approvalTo.setTransactor_names(Transaction_approvalFrom.getTransactor_names());
            Transaction_approvalTo.setCurrency_code(Transaction_approvalFrom.getCurrency_code());
            Transaction_approvalTo.setTransactor_id(Transaction_approvalFrom.getTransactor_id());
            Transaction_approvalTo.setStore_id(Transaction_approvalFrom.getStore_id());
            Transaction_approvalTo.setAmount_tendered(Transaction_approvalFrom.getAmount_tendered());
            Transaction_approvalTo.setTransaction_id(Transaction_approvalFrom.getTransaction_id());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearTransaction_approval(Transaction_approval aTransaction_approval) {
        try {
            if (aTransaction_approval != null) {
                aTransaction_approval.setTransaction_approval_id(0);
                aTransaction_approval.setTransaction_hist_id(0);
                aTransaction_approval.setTransaction_type_id(0);
                aTransaction_approval.setTransaction_reason_id(0);
                aTransaction_approval.setRequest_date(null);
                aTransaction_approval.setRequest_by_id(0);
                aTransaction_approval.setApproval_status(0);
                aTransaction_approval.setStatus_date(null);
                aTransaction_approval.setStatus_desc("");
                aTransaction_approval.setStatus_by_id(0);
                aTransaction_approval.setGrand_total(0);
                aTransaction_approval.setTransactor_names("");
                aTransaction_approval.setCurrency_code("");
                aTransaction_approval.setTransactor_id(0);
                aTransaction_approval.setStore_id(0);
                aTransaction_approval.setAmount_tendered(0);
                aTransaction_approval.setTransaction_id(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearFilter() {
        try {
            this.setFilterStore(0);
            this.setFilterTransactionType(0);
            this.setFilterRequestedBy(0);
            this.setFilterRequestDate(new CompanySetting().getCURRENT_SERVER_DATE());
            this.setFilterApprovalStatus(-1);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void getFilteredTransactionApprovals() {
        String sql;
        sql = "select * from transaction_approval where transaction_approval_id > 0";
        String wheresql = "";
        String ordersql = " ORDER BY request_date DESC";
        try {
            if (this.filterStore > 0) {
                wheresql = wheresql + " AND store_id=" + this.filterStore;
            }
            if (this.filterTransactionType > 0) {
                wheresql = wheresql + " AND transaction_type_id=" + this.filterTransactionType;
            }
            if (this.filterRequestedBy > 0) {
                wheresql = wheresql + " AND request_by_id=" + this.filterRequestedBy;
            }
            if (this.filterRequestDate != null) {
                wheresql = wheresql + " AND DATE_FORMAT(request_date,'%Y-%m-%d')='" + new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(this.filterRequestDate.getTime())) + "'";
            }
            if (this.filterApprovalStatus >= 0) {
                wheresql = wheresql + " AND approval_status=" + this.filterApprovalStatus;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        sql = sql + wheresql + ordersql;
        ResultSet rs;
        transaction_approvalList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Transaction_approval aTransaction_approval = new Transaction_approval();
                this.setTransaction_approvalFromResultset(aTransaction_approval, rs);
                getTransaction_approvalList().add(aTransaction_approval);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int approvalRequiredTrans(Trans aTrans, int aTransTypeId, int aTransReasId) {
        int x = 0;
        try {
            String TransactionsForApproval = new Parameter_listBean().getParameter_listByContextNameMemory("GENERAL", "TRANSACTIONS_FOR_APPROVAL").getParameter_value();
            if (TransactionsForApproval.length() == 0 || TransactionsForApproval.contains("0")) {
                x = 0;
            } else {
                if (aTransTypeId == 2 && TransactionsForApproval.contains("2") && aTrans.getTransaction_approval_id() == 0) {
                    x = 1;
                } else if (aTransTypeId == 2 && TransactionsForApproval.contains("1") && aTrans.getTransaction_approval_id() == 0 && aTrans.getGrandTotal() > aTrans.getAmountTendered()) {
                    x = 1;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return x;
    }

    /**
     * @return the filterStore
     */
    public int getFilterStore() {
        return filterStore;
    }

    /**
     * @param filterStore the filterStore to set
     */
    public void setFilterStore(int filterStore) {
        this.filterStore = filterStore;
    }

    /**
     * @return the filterTransactionType
     */
    public int getFilterTransactionType() {
        return filterTransactionType;
    }

    /**
     * @param filterTransactionType the filterTransactionType to set
     */
    public void setFilterTransactionType(int filterTransactionType) {
        this.filterTransactionType = filterTransactionType;
    }

    /**
     * @return the filterRequestedBy
     */
    public int getFilterRequestedBy() {
        return filterRequestedBy;
    }

    /**
     * @param filterRequestedBy the filterRequestedBy to set
     */
    public void setFilterRequestedBy(int filterRequestedBy) {
        this.filterRequestedBy = filterRequestedBy;
    }

    /**
     * @return the filterRequestDate
     */
    public Date getFilterRequestDate() {
        return filterRequestDate;
    }

    /**
     * @param filterRequestDate the filterRequestDate to set
     */
    public void setFilterRequestDate(Date filterRequestDate) {
        this.filterRequestDate = filterRequestDate;
    }

    /**
     * @return the filterApprovalStatus
     */
    public int getFilterApprovalStatus() {
        return filterApprovalStatus;
    }

    /**
     * @param filterApprovalStatus the filterApprovalStatus to set
     */
    public void setFilterApprovalStatus(int filterApprovalStatus) {
        this.filterApprovalStatus = filterApprovalStatus;
    }

    /**
     * @return the transaction_approvalList
     */
    public List<Transaction_approval> getTransaction_approvalList() {
        return transaction_approvalList;
    }

    /**
     * @param transaction_approvalList the transaction_approvalList to set
     */
    public void setTransaction_approvalList(List<Transaction_approval> transaction_approvalList) {
        this.transaction_approvalList = transaction_approvalList;
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
