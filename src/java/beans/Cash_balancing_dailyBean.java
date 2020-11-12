package beans;

import connections.DBConnection;
import entities.AccChildAccount;
import entities.Cdc_general;
import entities.Cash_balancing_daily;
import entities.CompanySetting;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
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
@ManagedBean
@SessionScoped
public class Cash_balancing_dailyBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Cash_balancing_daily> Cash_balancing_dailyList;
    private Cash_balancing_daily Cash_balancing_dailyObj;
    private List<Cash_balancing_daily> Cash_balancing_dailyListNew;
    private List<Cash_balancing_daily> Cash_balancing_dailyListExist;
    private Date FromDate;
    private Date ToDate;

    public void setCash_balancing_dailyFromResultset(Cash_balancing_daily aCash_balancing_daily, ResultSet aResultSet) {
        try {
            try {
                aCash_balancing_daily.setCash_balancing_daily_id(aResultSet.getLong("cash_balancing_daily_id"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_balancing_daily_id(0);
            }
            try {
                aCash_balancing_daily.setBalancing_date(new Date(aResultSet.getDate("balancing_date").getTime()));
            } catch (Exception e) {
                aCash_balancing_daily.setBalancing_date(null);
            }
            try {
                aCash_balancing_daily.setAcc_child_account_id(aResultSet.getInt("acc_child_account_id"));
            } catch (Exception e) {
                aCash_balancing_daily.setAcc_child_account_id(0);
            }
            try {
                aCash_balancing_daily.setCurrency_code(aResultSet.getString("currency_code"));
            } catch (Exception e) {
                aCash_balancing_daily.setCurrency_code("");
            }
            try {
                aCash_balancing_daily.setCash_begin(aResultSet.getDouble("cash_begin"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_begin(0);
            }
            try {
                aCash_balancing_daily.setCash_transfer_in(aResultSet.getDouble("cash_transfer_in"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_transfer_in(0);
            }
            try {
                aCash_balancing_daily.setCash_adjustment_pos(aResultSet.getDouble("cash_adjustment_pos"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_adjustment_pos(0);
            }
            try {
                aCash_balancing_daily.setCash_receipts(aResultSet.getDouble("cash_receipts"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_receipts(0);
            }
            try {
                aCash_balancing_daily.setCash_transfer_out(aResultSet.getDouble("cash_transfer_out"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_transfer_out(0);
            }
            try {
                aCash_balancing_daily.setCash_adjustment_neg(aResultSet.getDouble("cash_adjustment_neg"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_adjustment_neg(0);
            }
            try {
                aCash_balancing_daily.setCash_payments(aResultSet.getDouble("cash_payments"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_payments(0);
            }
            try {
                aCash_balancing_daily.setCash_balance(aResultSet.getDouble("cash_balance"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_balance(0);
            }
            try {
                aCash_balancing_daily.setActual_cash_count(aResultSet.getDouble("actual_cash_count"));
            } catch (Exception e) {
                aCash_balancing_daily.setActual_cash_count(0);
            }
            try {
                aCash_balancing_daily.setCash_over(aResultSet.getDouble("cash_over"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_over(0);
            }
            try {
                aCash_balancing_daily.setCash_short(aResultSet.getDouble("cash_short"));
            } catch (Exception e) {
                aCash_balancing_daily.setCash_short(0);
            }
            try {
                aCash_balancing_daily.setAdd_user_detail_id(aResultSet.getInt("add_user_detail_id"));
            } catch (Exception e) {
                aCash_balancing_daily.setAdd_user_detail_id(0);
            }
            try {
                aCash_balancing_daily.setEdit_user_detail_id(aResultSet.getInt("edit_user_detail_id"));
            } catch (Exception e) {
                aCash_balancing_daily.setEdit_user_detail_id(0);
            }
            try {
                aCash_balancing_daily.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aCash_balancing_daily.setAdd_date(null);
            }
            try {
                aCash_balancing_daily.setEdit_date(new Date(aResultSet.getTimestamp("edit_date").getTime()));
            } catch (Exception e) {
                aCash_balancing_daily.setEdit_date(null);
            }
        } catch (Exception e) {
            System.err.println("setCash_balancing_dailyFromResultset:" + e.getMessage());
        }
    }

    public Cash_balancing_daily getCash_balancing_daily(Long aCash_balancing_daily_id) {
        String sql = "SLECT * FROM cash_balancing_daily WHERE cash_balancing_daily_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aCash_balancing_daily_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Cash_balancing_daily ssv = new Cash_balancing_daily();
                this.setCash_balancing_dailyFromResultset(ssv, rs);
                return ssv;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("getCash_balancing_daily:" + e.getMessage());
            return null;
        }
    }

    public Cash_balancing_daily getCash_balancing_dailyByUnique(Date aBalancing_date, int aAcc_child_account_id, String aCurrency_code) {
        String sql = "SELECT * FROM cash_balancing_daily WHERE balancing_date=? and acc_child_account_id=? and currency_code=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setDate(1, new java.sql.Date(aBalancing_date.getTime()));
            ps.setInt(2, aAcc_child_account_id);
            ps.setString(3, aCurrency_code);
            rs = ps.executeQuery();
            if (rs.next()) {
                Cash_balancing_daily ssv = new Cash_balancing_daily();
                this.setCash_balancing_dailyFromResultset(ssv, rs);
                return ssv;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("getCash_balancing_dailyByUnique:" + e.getMessage());
            return null;
        }
    }

    public void refreshCash_balancing_daily() {
        String sqlNew = "";
        String sqlExist = "";
        ResultSet rsNew = null;
        ResultSet rsExist = null;
        this.Cash_balancing_dailyListNew = new ArrayList<>();
        this.Cash_balancing_dailyListExist = new ArrayList<>();

        if (this.FromDate != null && this.ToDate != null) {
            //Retrieve New
            sqlNew = "{call sp_search_cash_account_for_balancing(?,?)}";
            try (
                    Connection connNew = DBConnection.getMySQLConnection();
                    CallableStatement csNew = connNew.prepareCall(sqlNew);) {
                csNew.setDate("in_balancing_date1", new java.sql.Date(this.FromDate.getTime()));
                csNew.setDate("in_balancing_date2", new java.sql.Date(this.ToDate.getTime()));
                rsNew = csNew.executeQuery();
                while (rsNew.next()) {
                    Cash_balancing_daily cbdNew = new Cash_balancing_daily();
                    this.setCash_balancing_dailyFromResultset(cbdNew, rsNew);
                    if (cbdNew.getCash_transfer_in() == 0 && cbdNew.getCash_transfer_out() == 0 && cbdNew.getCash_adjustment_pos() == 0 && cbdNew.getCash_adjustment_neg() == 0 && cbdNew.getCash_receipts() == 0 && cbdNew.getCash_payments() == 0) {
                        //do no do anything
                    } else {
                        //do some processing
                        double CashIn = cbdNew.getCash_receipts() + cbdNew.getCash_transfer_in() + cbdNew.getCash_adjustment_pos();
                        double CashOut = cbdNew.getCash_payments() + cbdNew.getCash_transfer_out() + cbdNew.getCash_adjustment_neg();
                        if (cbdNew.getCash_begin() >= 0) {
                            cbdNew.setCash_balance(cbdNew.getCash_begin() + (CashIn - CashOut));
                        } else {
                            cbdNew.setCash_balance(CashIn - CashOut);
                        }
                        AccChildAccount acaNew = new AccChildAccountBean().getAccChildAccById(cbdNew.getAcc_child_account_id());
                        if (acaNew != null) {
                            cbdNew.setAccountName(new AccCoaBean().getAccCoaByCodeOrId(acaNew.getAccCoaAccountCode(), 0).getAccountName());
                            cbdNew.setChildAccountName(new AccChildAccountBean().getAccChildAccById(acaNew.getAccChildAccountId()).getChildAccountName());
                        }
                        Cash_balancing_daily ExistRecord = this.getCash_balancing_dailyByUnique(cbdNew.getBalancing_date(), cbdNew.getAcc_child_account_id(), cbdNew.getCurrency_code());
                        if (null != ExistRecord) {
                            cbdNew.setCash_balancing_daily_id(ExistRecord.getCash_balancing_daily_id());
                            cbdNew.setActual_cash_count(ExistRecord.getActual_cash_count());
                            this.calculateCashOverOrShort(cbdNew);
                        }
                        this.Cash_balancing_dailyListNew.add(cbdNew);
                    }
                }
            } catch (Exception e) {
                System.err.println("refreshCash_balancing_dailyList:New:" + e.getMessage());
            }
            //Retrieve Exist
            /*
             sqlExist = "SELECT * FROM cash_balancing_daily WHERE balancing_date BETWEEN '" + new java.sql.Date(this.FromDate.getTime()) + "' AND '" + new java.sql.Date(this.ToDate.getTime()) + "' "
             + "ORDER BY balancing_date DESC,acc_child_account_id,currency_code";
             try (
             Connection connExis = DBConnection.getMySQLConnection();
             PreparedStatement psExist = connExis.prepareStatement(sqlExist);) {
             rsExist = psExist.executeQuery();
             while (rsExist.next()) {
             Cash_balancing_daily cbdExist = new Cash_balancing_daily();
             this.setCash_balancing_dailyFromResultset(cbdExist, rsExist);
             //do some processing
             AccChildAccount acaExist = new AccChildAccountBean().getAccChildAccById(cbdExist.getAcc_child_account_id());
             if (acaExist != null) {
             cbdExist.setAccountName(new AccCoaBean().getAccCoaByCodeOrId(acaExist.getAccCoaAccountCode(), 0).getAccountName());
             cbdExist.setChildAccountName(new AccChildAccountBean().getAccChildAccById(acaExist.getAccChildAccountId()).getChildAccountName());
             }
             this.Cash_balancing_dailyListExist.add(cbdExist);
             }
             } catch (Exception e) {
             System.err.println("refreshCash_balancing_dailyList:Exist:" + e.getMessage());
             }
             */
        }
    }

    public void calculateCashOverOrShort(Cash_balancing_daily aCash_balancing_daily) {
        try {
            aCash_balancing_daily.setCash_over(0);
            aCash_balancing_daily.setCash_short(0);
            if (aCash_balancing_daily.getCash_balance() < 0) {
                aCash_balancing_daily.setCash_over(aCash_balancing_daily.getCash_balance() + aCash_balancing_daily.getActual_cash_count());
            } else if (aCash_balancing_daily.getCash_balance() > aCash_balancing_daily.getActual_cash_count()) {
                aCash_balancing_daily.setCash_short(aCash_balancing_daily.getCash_balance() - aCash_balancing_daily.getActual_cash_count());
            } else if (aCash_balancing_daily.getCash_balance() < aCash_balancing_daily.getActual_cash_count()) {
                aCash_balancing_daily.setCash_over(aCash_balancing_daily.getActual_cash_count() - aCash_balancing_daily.getCash_balance());
            }
        } catch (Exception e) {
            System.err.println("calculateCashOverOrShort:" + e.getMessage());
        }
    }

    public void callSaveCash_balancing_daily(Cash_balancing_daily aCash_balancing_daily) {
        String msg = null;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        try {
            if (aCash_balancing_daily.getCash_balancing_daily_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "122", "Add") == 0) {
                msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            } else if (aCash_balancing_daily.getCash_balancing_daily_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "122", "Edit") == 0) {
                msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            } else {
                int i = this.saveCash_balancing_daily(aCash_balancing_daily);
                if (i == 1) {
                    msg = "Saved Successfully";
                } else {
                    msg = "Record NOT Saved";
                }
            }
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } catch (Exception e) {
            System.out.println("callSaveCash_balancing_daily:" + e.getMessage());
        }
    }

    public int saveCash_balancing_daily(Cash_balancing_daily aCash_balancing_daily) {
        int saved = 0;
        String sql = "{call sp_save_cash_balancing_daily(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aCash_balancing_daily) {
                cs.setLong("in_cash_balancing_daily_id", aCash_balancing_daily.getCash_balancing_daily_id());
                cs.setDate("in_balancing_date", new java.sql.Date(aCash_balancing_daily.getBalancing_date().getTime()));
                cs.setLong("in_acc_child_account_id", aCash_balancing_daily.getAcc_child_account_id());
                cs.setString("in_currency_code", aCash_balancing_daily.getCurrency_code());
                cs.setDouble("in_cash_begin", aCash_balancing_daily.getCash_begin());
                cs.setDouble("in_cash_transfer_in", aCash_balancing_daily.getCash_transfer_in());
                cs.setDouble("in_cash_adjustment_pos", aCash_balancing_daily.getCash_adjustment_pos());
                cs.setDouble("in_cash_receipts", aCash_balancing_daily.getCash_receipts());
                cs.setDouble("in_cash_transfer_out", aCash_balancing_daily.getCash_transfer_out());
                cs.setDouble("in_cash_adjustment_neg", aCash_balancing_daily.getCash_adjustment_neg());
                cs.setDouble("in_cash_payments", aCash_balancing_daily.getCash_payments());
                cs.setDouble("in_cash_balance", aCash_balancing_daily.getCash_balance());
                cs.setDouble("in_actual_cash_count", aCash_balancing_daily.getActual_cash_count());
                cs.setDouble("in_cash_over", aCash_balancing_daily.getCash_over());
                cs.setDouble("in_cash_short", aCash_balancing_daily.getCash_short());
                cs.setInt("in_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                cs.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("saveCash_balancing_daily:" + e.getMessage());
        }
        return saved;
    }

    public void clearCash_balancing_daily(Cash_balancing_daily aCash_balancing_daily) {
        aCash_balancing_daily.setCash_balancing_daily_id(0);
        aCash_balancing_daily.setBalancing_date(null);
        aCash_balancing_daily.setAcc_child_account_id(0);
        aCash_balancing_daily.setCurrency_code("");
        aCash_balancing_daily.setCash_begin(0);
        aCash_balancing_daily.setCash_transfer_in(0);
        aCash_balancing_daily.setCash_adjustment_pos(0);
        aCash_balancing_daily.setCash_receipts(0);
        aCash_balancing_daily.setCash_transfer_out(0);
        aCash_balancing_daily.setCash_adjustment_neg(0);
        aCash_balancing_daily.setCash_payments(0);
        aCash_balancing_daily.setCash_balance(0);
        aCash_balancing_daily.setActual_cash_count(0);
        aCash_balancing_daily.setCash_over(0);
        aCash_balancing_daily.setCash_short(0);
        aCash_balancing_daily.setAdd_user_detail_id(0);
        aCash_balancing_daily.setEdit_user_detail_id(0);
        aCash_balancing_daily.setAdd_date(null);
        aCash_balancing_daily.setEdit_date(null);
    }

    public void setDateToToday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();
        this.setFromDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getFromDate());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setFromDate(cal.getTime());

        this.setToDate(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getToDate());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setToDate(cal2.getTime());
    }

    public void setDateToYesturday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        this.setFromDate(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getFromDate());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setFromDate(cal.getTime());

        this.setToDate(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getToDate());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setToDate(cal2.getTime());
    }

    public void resetCash_balancing_daily() {
        try {
            this.setActionMessage("");
        } catch (Exception e) {
        }
        try {
            this.setDateToToday();
        } catch (Exception e) {
        }
        try {
            this.Cash_balancing_dailyListNew.clear();
        } catch (Exception e) {
        }
        try {
            this.Cash_balancing_dailyListExist.clear();
        } catch (Exception e) {
        }
    }

    /**
     * @return the Cash_balancing_dailyList
     */
    public List<Cash_balancing_daily> getCash_balancing_dailyList() {
        return Cash_balancing_dailyList;
    }

    /**
     * @param Cash_balancing_dailyList the Cash_balancing_dailyList to set
     */
    public void setCash_balancing_dailyList(List<Cash_balancing_daily> Cash_balancing_dailyList) {
        this.Cash_balancing_dailyList = Cash_balancing_dailyList;
    }

    /**
     * @return the Cash_balancing_dailyObj
     */
    public Cash_balancing_daily getCash_balancing_dailyObj() {
        return Cash_balancing_dailyObj;
    }

    /**
     * @param Cash_balancing_dailyObj the Cash_balancing_dailyObj to set
     */
    public void setCash_balancing_dailyObj(Cash_balancing_daily Cash_balancing_dailyObj) {
        this.Cash_balancing_dailyObj = Cash_balancing_dailyObj;
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
     * @return the Cash_balancing_dailyListNew
     */
    public List<Cash_balancing_daily> getCash_balancing_dailyListNew() {
        return Cash_balancing_dailyListNew;
    }

    /**
     * @param Cash_balancing_dailyListNew the Cash_balancing_dailyListNew to set
     */
    public void setCash_balancing_dailyListNew(List<Cash_balancing_daily> Cash_balancing_dailyListNew) {
        this.Cash_balancing_dailyListNew = Cash_balancing_dailyListNew;
    }

    /**
     * @return the Cash_balancing_dailyListExist
     */
    public List<Cash_balancing_daily> getCash_balancing_dailyListExist() {
        return Cash_balancing_dailyListExist;
    }

    /**
     * @param Cash_balancing_dailyListExist the Cash_balancing_dailyListExist to
     * set
     */
    public void setCash_balancing_dailyListExist(List<Cash_balancing_daily> Cash_balancing_dailyListExist) {
        this.Cash_balancing_dailyListExist = Cash_balancing_dailyListExist;
    }

    /**
     * @return the FromDate
     */
    public Date getFromDate() {
        return FromDate;
    }

    /**
     * @param FromDate the FromDate to set
     */
    public void setFromDate(Date FromDate) {
        this.FromDate = FromDate;
    }

    /**
     * @return the ToDate
     */
    public Date getToDate() {
        return ToDate;
    }

    /**
     * @param ToDate the ToDate to set
     */
    public void setToDate(Date ToDate) {
        this.ToDate = ToDate;
    }
}
