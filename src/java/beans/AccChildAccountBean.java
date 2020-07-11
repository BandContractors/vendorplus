package beans;

import connections.DBConnection;
import entities.AccChildAccount;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessions.GeneralUserSetting;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "accChildAccountBean")
@SessionScoped
public class AccChildAccountBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private AccChildAccount AccChildAccountObj = new AccChildAccount();
    private List<AccChildAccount> AccChildAccountList;
    private String ChildAccountCategory;

    public void setAccChildAccountFromResultset(AccChildAccount accchildaccount, ResultSet aResultSet) {
        try {
            try {
                accchildaccount.setAccChildAccountId(aResultSet.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                accchildaccount.setAccChildAccountId(0);
            }
            try {
                accchildaccount.setAccCoaId(aResultSet.getInt("acc_coa_id"));
            } catch (NullPointerException npe) {
                accchildaccount.setAccCoaId(0);
            }
            try {
                accchildaccount.setAccCoaAccountCode(aResultSet.getString("acc_coa_account_code"));
            } catch (NullPointerException npe) {
                accchildaccount.setAccCoaAccountCode("");
            }
            try {
                accchildaccount.setChildAccountCode(aResultSet.getString("child_account_code"));
            } catch (NullPointerException npe) {
                accchildaccount.setChildAccountCode("");
            }
            try {
                accchildaccount.setChildAccountName(aResultSet.getString("child_account_name"));
            } catch (NullPointerException npe) {
                accchildaccount.setChildAccountName("");
            }
            try {
                accchildaccount.setChildAccountDesc(aResultSet.getString("child_account_desc"));
            } catch (NullPointerException npe) {
                accchildaccount.setChildAccountDesc("");
            }
            try {
                accchildaccount.setUserDetailId(aResultSet.getInt("user_detail_id"));
            } catch (NullPointerException npe) {
                accchildaccount.setUserDetailId(0);
            }
            try {
                accchildaccount.setStoreId(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                accchildaccount.setStoreId(0);
            }
            try {
                accchildaccount.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                accchildaccount.setIsActive(0);
            }
            try {
                accchildaccount.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                accchildaccount.setIsDeleted(0);
            }
            try {
                accchildaccount.setAddBy(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                accchildaccount.setAddBy(0);
            }
            try {
                accchildaccount.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (NullPointerException npe) {
                accchildaccount.setLastEditBy(0);
            }
            try {
                accchildaccount.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                accchildaccount.setAddDate(null);
            }
            try {
                accchildaccount.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (NullPointerException npe) {
                accchildaccount.setLastEditDate(null);
            }
            try {
                accchildaccount.setCurrencyId(aResultSet.getInt("currency_id"));
            } catch (NullPointerException npe) {
                accchildaccount.setCurrencyId(0);
            }
            try {
                accchildaccount.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                accchildaccount.setCurrencyCode("");
            }
            try {
                accchildaccount.setBalance_checker_on(aResultSet.getInt("balance_checker_on"));
            } catch (NullPointerException npe) {
                accchildaccount.setBalance_checker_on(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public int saveAccChildAccount(AccChildAccount aAccChildAccount) {
        String sql = "";
        int x = 0;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (aAccChildAccount.getAccChildAccountId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "85", "Add") == 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR..."));
        } else if (aAccChildAccount.getAccChildAccountId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "85", "Edit") == 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR..."));
        } else {
            if (aAccChildAccount.getAccCoaId() == 0) {
                this.setActionMessage("Please select Parent Accout...");
            } else if (aAccChildAccount.getChildAccountCode().length() <= 0) {
                this.setActionMessage("Please specify Child Account Code...");
            } else if (aAccChildAccount.getChildAccountName().length() <= 0) {
                this.setActionMessage("Please specify Child Account Name...");
            } else {
                try {
                    aAccChildAccount.setAccCoaAccountCode(new AccCoaBean().getAccCoaByCodeOrId("", aAccChildAccount.getAccCoaId()).getAccountCode());
                } catch (NullPointerException npe) {
                    aAccChildAccount.setAccCoaAccountCode("");
                }
                try {
                    aAccChildAccount.setCurrencyCode(new AccCurrencyBean().getCurrency(aAccChildAccount.getCurrencyId()).getCurrencyCode());
                } catch (NullPointerException npe) {
                    aAccChildAccount.setCurrencyCode("");
                }
                if (aAccChildAccount.getAccChildAccountId() == 0) {//Insert
                    sql = "{call sp_insert_acc_child_account(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                } else {//Update
                    sql = "{call sp_update_acc_child_account(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                }
                try (
                        Connection conn = DBConnection.getMySQLConnection();
                        CallableStatement cs = conn.prepareCall(sql);) {
                    if (aAccChildAccount.getAccChildAccountId() == 0) {//Insert
                        aAccChildAccount.setIsActive(1);
                        aAccChildAccount.setIsDeleted(0);
                    } else {//Update
                        try {
                            cs.setInt("in_acc_child_account_id", aAccChildAccount.getAccChildAccountId());
                        } catch (NullPointerException npe) {
                            cs.setInt("in_acc_child_account_id", 0);
                        }
                    }
                    try {
                        cs.setInt("in_acc_coa_id", aAccChildAccount.getAccCoaId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_coa_id", 0);
                    }
                    cs.setString("in_acc_coa_account_code", aAccChildAccount.getAccCoaAccountCode());
                    cs.setString("in_child_account_code", aAccChildAccount.getChildAccountCode());
                    cs.setString("in_child_account_name", aAccChildAccount.getChildAccountName());
                    cs.setString("in_child_account_desc", aAccChildAccount.getChildAccountDesc());
                    try {
                        cs.setInt("in_user_detail_id", aAccChildAccount.getUserDetailId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_user_detail_id", 0);
                    }
                    try {
                        cs.setInt("in_store_id", aAccChildAccount.getStoreId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_store_id", 0);
                    }
                    try {
                        cs.setInt("in_currency_id", aAccChildAccount.getCurrencyId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_currency_id", 0);
                    }
                    cs.setString("in_currency_code", aAccChildAccount.getCurrencyCode());
                    try {
                        cs.setInt("in_is_active", aAccChildAccount.getIsActive());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_active", 0);
                    }
                    try {
                        cs.setInt("in_is_deleted", aAccChildAccount.getIsDeleted());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_deleted", 0);
                    }
                    try {
                        cs.setInt("in_user_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_user_id", 0);
                    }
                    try {
                        cs.setInt("in_balance_checker_on", aAccChildAccount.getBalance_checker_on());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_balance_checker_on", 0);
                    }
                    cs.executeUpdate();
                    this.clearAccChildAccount(aAccChildAccount);
                    this.refreshAccChildAccounts();
                    x = 1;
                } catch (SQLException se) {
                    x = 0;
                    System.err.println(se.getMessage());
                }
            }
        }
        return x;
    }

    public List<AccChildAccount> getChildAccountsByPayReason(String aAccCode1, String aAccCode2, String aAccCode3, int aPayReasonId) {
        if (aPayReasonId == 23 || aPayReasonId == 34) {
            return this.getAccChildAccounts(aAccCode1, 0, 0);
        } else if (aPayReasonId == 24 || aPayReasonId == 33) {
            return this.getAccChildAccounts(aAccCode2, aAccCode3, 0, 0);
        } else {
            return null;
        }
    }

    public void clearAccChildAccount(AccChildAccount accchildaccount) {
        accchildaccount.setAccChildAccountId(0);
        accchildaccount.setAccCoaId(0);
        accchildaccount.setAccCoaAccountCode("");
        accchildaccount.setChildAccountCode("");
        accchildaccount.setChildAccountName("");
        accchildaccount.setChildAccountDesc("");
        accchildaccount.setUserDetailId(0);
        accchildaccount.setStoreId(0);
        accchildaccount.setIsActive(0);
        accchildaccount.setIsDeleted(0);
        accchildaccount.setAddBy(0);
        accchildaccount.setLastEditBy(0);
        accchildaccount.setAddDate(null);
        accchildaccount.setLastEditDate(null);
        accchildaccount.setCurrencyId(0);
        accchildaccount.setCurrencyCode("");
        accchildaccount.setBalance_checker_on(0);
    }

    public void initClearChildAccount(AccChildAccount aAccChildAccount) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.clearAccChildAccount(aAccChildAccount);
        }
    }

    public void refreshAccChildAccounts() {
        String sql;
        sql = "SELECT * FROM acc_child_account WHERE is_deleted=0 order by acc_coa_account_code ASC";
        ResultSet rs = null;
        this.AccChildAccountList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                this.AccChildAccountList.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public List<AccChildAccount> getAccChildAccounts(String aParentAccCode, int aStoredId, int aUserDetailId) {
        String sql;
        sql = "SELECT * FROM acc_child_account WHERE acc_coa_account_code='" + aParentAccCode + "' AND is_active=1 AND is_deleted=0";
        if (aStoredId > 0) {
            sql = sql + " AND store_id=" + aStoredId;
        }
        if (aUserDetailId > 0) {
            sql = sql + " AND user_detail_id=" + aUserDetailId;
        }
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public List<AccChildAccount> getAccChildAccounts(String aParentAccCode1, String aParentAccCode2, int aStoredId, int aUserDetailId) {
        String sql;
        sql = "SELECT * FROM acc_child_account WHERE is_active=1 AND is_deleted=0 AND (acc_coa_account_code='" + aParentAccCode1 + "' OR acc_coa_account_code='" + aParentAccCode2 + "') ";
        if (aStoredId > 0) {
            sql = sql + " AND store_id=" + aStoredId;
        }
        if (aUserDetailId > 0) {
            sql = sql + " AND user_detail_id=" + aUserDetailId;
        }
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public List<AccChildAccount> getAccChildAccountsByCurrency(String aCurrencyCode) {
        String sql;
        sql = "SELECT * FROM acc_child_account WHERE currency_code='" + aCurrencyCode + "' AND is_active=1 AND is_deleted=0";
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public List<AccChildAccount> getAccChildAccountsByCurrencyParent(String aCurrencyCode, String aParentCode) {
        String sql;
        sql = "SELECT * FROM acc_child_account WHERE (currency_code='" + aCurrencyCode + "' OR currency_code is null OR currency_code='') AND (acc_coa_account_code='" + aParentCode + "')  AND is_active=1 AND is_deleted=0";
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public List<AccChildAccount> getAccChildAccountsByCurrencyParentStartWith(String aCurrencyCode, String aParentCodeStartWith) {
        String sql;
        sql = "SELECT * FROM acc_child_account WHERE (currency_code='" + aCurrencyCode + "' OR currency_code is null OR currency_code='') AND (acc_coa_account_code LIKE '" + aParentCodeStartWith + "%')  AND is_active=1 AND is_deleted=0";
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }
    
    public List<AccChildAccount> getAccChildAccountsByParentStartWith(String aParentCodeStartWith) {
        String sql;
        sql = "SELECT * FROM acc_child_account WHERE (acc_coa_account_code LIKE '" + aParentCodeStartWith + "%')  AND is_active=1 AND is_deleted=0 ORDER BY child_account_name ASC";
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public List<AccChildAccount> getAccChildAccountsForCashReceipt(String aCurrencyCode, int aPayMethodId, int aStoreId, int aUserDetailId) {
        String ParentAccCode;
        String sql;
        switch (aPayMethodId) {
            case 1://1	CASH
                ParentAccCode = "1-00-000-010";
                break;
            case 2://2	MM
                ParentAccCode = "1-00-000-050";
                break;
            case 3://3	CHEQUE
                ParentAccCode = "1-00-000-030";
                break;
            case 4://4	EFT
                ParentAccCode = "1-00-000-030";
                break;
            case 5://5	BANK
                ParentAccCode = "1-00-000-030";
                break;
            case 6://6	PREPAID INCOME
                ParentAccCode = "2-00-000-070";
                break;
            case 7://7	PREPAID EXPENSE
                ParentAccCode = "1-00-030-050";
                break;
            case 8://8	WHT ACC
                ParentAccCode = "1-00-010-080";
                break;
            case 11://11	PETTY CASH
                ParentAccCode = "1-00-000-020";
                break;
            default:
                ParentAccCode = "";
                break;
        }
        sql = "SELECT * FROM acc_child_account WHERE is_active=1 AND is_deleted=0 AND acc_coa_account_code='" + ParentAccCode + "' AND (currency_code='" + aCurrencyCode + "' OR currency_code is null OR currency_code='') AND (store_id=" + aStoreId + " OR store_id is null) AND (user_detail_id=" + aUserDetailId + " OR user_detail_id is null)";
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public List<AccChildAccount> getAccChildAccountsForCashPayment(String aCurrencyCode, int aPayMethodId, int aStoreId, int aUserDetailId) {
        String ParentAccCode;
        String sql;
        switch (aPayMethodId) {
            case 1://1	CASH
                ParentAccCode = "1-00-000-010";
                break;
            case 2://2	MM
                ParentAccCode = "1-00-000-050";
                break;
            case 3://3	CHEQUE
                ParentAccCode = "1-00-000-030";
                break;
            case 4://4	EFT
                ParentAccCode = "1-00-000-030";
                break;
            case 5://5	BANK
                ParentAccCode = "1-00-000-030";
                break;
            case 6://6	PREPAID INCOME
                ParentAccCode = "2-00-000-070";
                break;
            case 7://7	PREPAID EXPENSE
                ParentAccCode = "1-00-030-050";
                break;
            case 11://11	PETTY CASH
                ParentAccCode = "1-00-000-020";
                break;
            default:
                ParentAccCode = "";
                break;
        }
        sql = "SELECT * FROM acc_child_account WHERE is_active=1 AND is_deleted=0 AND acc_coa_account_code='" + ParentAccCode + "' AND (currency_code='" + aCurrencyCode + "' OR currency_code is null OR currency_code='') AND (store_id=" + aStoreId + " OR store_id is null) AND (user_detail_id=" + aUserDetailId + " OR user_detail_id is null)";
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public List<AccChildAccount> getAccChildAccountsForCashExpenseEntry(String aCurrencyCode, int aPayMethodId, int aStoreId, int aUserDetailId) {
        String ParentAccCode;
        String sql;
        switch (aPayMethodId) {
            case 1://1	CASH
                ParentAccCode = "'1-00-000-010','1-00-000-020'";
                break;
            case 2://2	MM
                ParentAccCode = "'1-00-000-050'";
                break;
            case 3://3	CHEQUE
                ParentAccCode = "'1-00-000-030'";
                break;
            case 4://4	EFT
                ParentAccCode = "'1-00-000-030'";
                break;
            case 5://5	BANK
                ParentAccCode = "'1-00-000-030'";
                break;
            default:
                ParentAccCode = "''";
                break;
        }
        sql = "SELECT * FROM acc_child_account WHERE is_active=1 AND is_deleted=0 AND acc_coa_account_code IN (" + ParentAccCode + ") AND (currency_code='" + aCurrencyCode + "' OR currency_code is null OR currency_code='') AND (store_id=" + aStoreId + " OR store_id is null) AND (user_detail_id=" + aUserDetailId + " OR user_detail_id is null)";
        ResultSet rs = null;
        List<AccChildAccount> cas = new ArrayList<AccChildAccount>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccChildAccount ca = new AccChildAccount();
                this.setAccChildAccountFromResultset(ca, rs);
                cas.add(ca);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return cas;
    }

    public String getParentAccCodeByChildAccId(int aChildAccId) {
        String sql = "";
        String ParentAccCode = "";
        if (aChildAccId > 0) {
            sql = "SELECT * FROM acc_child_account WHERE is_active=1 AND is_deleted=0 AND acc_child_account_id=" + aChildAccId;
        } else {
            return "";
        }
        ResultSet rs = null;
        AccChildAccount aca = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                aca = new AccChildAccount();
                this.setAccChildAccountFromResultset(aca, rs);
                ParentAccCode = aca.getAccCoaAccountCode();
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return ParentAccCode;
    }

    public AccChildAccount getAccChildAccById(int aChildAccId) {
        String sql = "";
        if (aChildAccId > 0) {
            sql = "SELECT * FROM acc_child_account WHERE acc_child_account_id=" + aChildAccId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccChildAccount aca = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                aca = new AccChildAccount();
                this.setAccChildAccountFromResultset(aca, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return aca;
    }

    public AccChildAccount getAccChildAccByCode(String aChildAccCode) {
        String sql = "";
        if (aChildAccCode.length() > 0) {
            sql = "SELECT * FROM acc_child_account WHERE child_account_code='" + aChildAccCode + "'";
        } else {
            return null;
        }
        ResultSet rs = null;
        AccChildAccount aca = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                aca = new AccChildAccount();
                this.setAccChildAccountFromResultset(aca, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return aca;
    }

    public void initAccChildAccountSession(int aAccChildAccountId) {
        try {
            this.setAccChildAccountObj(new AccChildAccountBean().getAccChildAccById(aAccChildAccountId));
        } catch (Exception e) {

        }
    }

    /**
     * @return the AccChildAccountObj
     */
    public AccChildAccount getAccChildAccountObj() {
        return AccChildAccountObj;
    }

    /**
     * @param AccChildAccountObj the AccChildAccountObj to set
     */
    public void setAccChildAccountObj(AccChildAccount AccChildAccountObj) {
        this.AccChildAccountObj = AccChildAccountObj;
    }

    /**
     * @return the AccChildAccountList
     */
    public List<AccChildAccount> getAccChildAccountList() {
        return AccChildAccountList;
    }

    /**
     * @param AccChildAccountList the AccChildAccountList to set
     */
    public void setAccChildAccountList(List<AccChildAccount> AccChildAccountList) {
        this.AccChildAccountList = AccChildAccountList;
    }

    /**
     * @return the ChildAccountCategory
     */
    public String getChildAccountCategory() {
        return ChildAccountCategory;
    }

    /**
     * @param ChildAccountCategory the ChildAccountCategory to set
     */
    public void setChildAccountCategory(String ChildAccountCategory) {
        this.ChildAccountCategory = ChildAccountCategory;
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

}
