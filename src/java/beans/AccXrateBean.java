package beans;

import connections.DBConnection;
import entities.AccCurrency;
import entities.AccXrate;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessions.GeneralUserSetting;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "accXrateBean")
@SessionScoped
public class AccXrateBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(AccXrateBean.class.getName());
    private String ActionMessage = null;
    private List<AccXrate> AccXrateList;

    public void setAccXrateFromResultset(AccXrate accxrate, ResultSet aResultSet) {
        try {
            try {
                accxrate.setAccXrateId(aResultSet.getInt("acc_xrate_id"));
            } catch (NullPointerException npe) {
                accxrate.setAccXrateId(0);
            }
            try {
                accxrate.setLocalCurrencyId(aResultSet.getInt("local_currency_id"));
            } catch (NullPointerException npe) {
                accxrate.setLocalCurrencyId(0);
            }
            try {
                accxrate.setForeignCurrencyId(aResultSet.getInt("foreign_currency_id"));
            } catch (NullPointerException npe) {
                accxrate.setForeignCurrencyId(0);
            }
            try {
                accxrate.setLocalCurrencyCode(aResultSet.getString("local_currency_code"));
            } catch (NullPointerException npe) {
                accxrate.setLocalCurrencyCode("");
            }
            try {
                accxrate.setForeignCurrencyCode(aResultSet.getString("foreign_currency_code"));
            } catch (NullPointerException npe) {
                accxrate.setForeignCurrencyCode("");
            }
            try {
                accxrate.setBuying(aResultSet.getDouble("buying"));
            } catch (NullPointerException npe) {
                accxrate.setBuying(1);
            }
            try {
                accxrate.setSelling(aResultSet.getDouble("selling"));
            } catch (NullPointerException npe) {
                accxrate.setSelling(1);
            }
            try {
                accxrate.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                accxrate.setIsActive(0);
            }
            try {
                accxrate.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                accxrate.setIsDeleted(0);
            }
            try {
                accxrate.setAddBy(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                accxrate.setAddBy(0);
            }
            try {
                accxrate.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (NullPointerException npe) {
                accxrate.setLastEditBy(0);
            }
            try {
                accxrate.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                accxrate.setAddDate(null);
            }
            try {
                accxrate.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (NullPointerException npe) {
                accxrate.setLastEditDate(null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveAccXrate(AccCurrency aAccCurrency) {
        int NewCurId = 0;
        AccXrate FXRecord = null;
        AccCurrency LocalCurrency = null;
        LocalCurrency = new AccCurrencyBean().getLocalCurrency();
        if (null == aAccCurrency) {
            //do nothing
        } else {
            if (aAccCurrency.getAccCurrencyId() == 0) {
                try {
                    NewCurId = new AccCurrencyBean().getCurrency(aAccCurrency.getCurrencyCode()).getAccCurrencyId();
                    aAccCurrency.setAccCurrencyId(NewCurId);
                } catch (NullPointerException npe) {
                    NewCurId = 0;
                }
            } else {
                NewCurId = aAccCurrency.getAccCurrencyId();
            }
            if (aAccCurrency.getIsLocalCurrency() == 0 && NewCurId > 0) {
                FXRecord = this.getXrateByFX(aAccCurrency.getCurrencyCode());
                String sql = "{call sp_save_acc_xrate(?,?,?,?,?,?,?,?,?,?)}";
                try (
                        Connection conn = DBConnection.getMySQLConnection();
                        CallableStatement cs = conn.prepareCall(sql);) {
                    if (1 == 1) {
                        try {
                            if (null != FXRecord) {
                                cs.setInt("in_acc_xrate_id", FXRecord.getAccXrateId());
                            } else {
                                cs.setInt("in_acc_xrate_id", 0);
                            }
                        } catch (NullPointerException npe) {
                            cs.setInt("in_acc_xrate_id", 0);
                        }
                        try {
                            cs.setInt("in_local_currency_id", LocalCurrency.getAccCurrencyId());
                        } catch (NullPointerException npe) {
                            cs.setInt("in_local_currency_id", 0);
                        }
                        try {
                            cs.setInt("in_foreign_currency_id", aAccCurrency.getAccCurrencyId());
                        } catch (NullPointerException npe) {
                            cs.setInt("in_foreign_currency_id", 0);
                        }
                        try {
                            cs.setString("in_local_currency_code", LocalCurrency.getCurrencyCode());
                        } catch (NullPointerException npe) {
                            cs.setString("in_local_currency_code", "");
                        }
                        try {
                            cs.setString("in_foreign_currency_code", aAccCurrency.getCurrencyCode());
                        } catch (NullPointerException npe) {
                            cs.setString("in_foreign_currency_code", "");
                        }
                        try {
                            cs.setDouble("in_buying", aAccCurrency.getBuying());
                        } catch (NullPointerException npe) {
                            cs.setDouble("in_buying", 1);
                        }
                        try {
                            cs.setDouble("in_selling", aAccCurrency.getSelling());
                        } catch (NullPointerException npe) {
                            cs.setDouble("in_selling", 1);
                        }
                        try {
                            cs.setInt("in_is_active", aAccCurrency.getIsActive());
                        } catch (NullPointerException npe) {
                            cs.setInt("in_is_active", 0);
                        }
                        try {
                            cs.setInt("in_is_deleted", 0);
                        } catch (NullPointerException npe) {
                            cs.setInt("in_is_deleted", 0);
                        }
                        try {
                            cs.setInt("in_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        } catch (NullPointerException npe) {
                            cs.setInt("in_user_detail_id", 0);
                        }
                        cs.executeUpdate();
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                }
            }
        }
    }

    public AccXrate getXrateByFX(String aForeignCurrencyCode) {
        String sql;
        sql = "SELECT * FROM acc_xrate WHERE foreign_currency_code='" + aForeignCurrencyCode + "'";
        ResultSet rs = null;
        AccXrate axr = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                axr = new AccXrate();
                this.setAccXrateFromResultset(axr, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return axr;
    }

    public void initXrateList() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.refreshXrates();
        }
    }

    public void refreshXrates() {
        String sql;
        sql = "SELECT * FROM acc_xrate ORDER BY foreign_currency_code ASC";
        ResultSet rs = null;
        this.AccXrateList = new ArrayList<AccXrate>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccXrate ac = new AccXrate();
                this.setAccXrateFromResultset(ac, rs);
                this.AccXrateList.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int getSnapshotMaxNoXrate(int aAccPeriodId) {
        String sql;
        sql = "select max(snapshot_no) as snapshot_no from snapshot_xrate where acc_period_id=" + aAccPeriodId;
        ResultSet rs = null;
        int SnapshotMaxNo = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    SnapshotMaxNo = rs.getInt("snapshot_no");
                } catch (NullPointerException npe) {
                    SnapshotMaxNo = 0;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return SnapshotMaxNo;
    }

    public double getXrate(String aFromCurCode, String aToCurCode) {
        String sql;
        double xrate = 1;
        AccCurrency LocalCurrency = null;
        LocalCurrency = new AccCurrencyBean().getLocalCurrency();
        if (aFromCurCode.equals(aToCurCode)) {
            xrate = 1;
            return xrate;
        } else if (aFromCurCode.equals(LocalCurrency.getCurrencyCode()) && !aToCurCode.equals(LocalCurrency.getCurrencyCode())) {//Local-2-Foreign
            sql = "SELECT * FROM acc_xrate WHERE is_deleted=0 AND is_Active=1 AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aToCurCode + "'";
            ResultSet rs = null;
            AccXrate axr = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    axr = new AccXrate();
                    this.setAccXrateFromResultset(axr, rs);
                    xrate = axr.getBuying();
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        } else if (!aFromCurCode.equals(LocalCurrency.getCurrencyCode()) && aToCurCode.equals(LocalCurrency.getCurrencyCode())) {//Foreign-2-Local
            sql = "SELECT * FROM acc_xrate WHERE is_deleted=0 AND is_Active=1 AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aFromCurCode + "'";
            ResultSet rs = null;
            AccXrate axr = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    axr = new AccXrate();
                    this.setAccXrateFromResultset(axr, rs);
                    xrate = axr.getSelling();
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        } else if (!aFromCurCode.equals(LocalCurrency.getCurrencyCode()) && !aToCurCode.equals(LocalCurrency.getCurrencyCode())) {//Foreign-2-Foreign
            double FromXrate = 1;
            double ToXrate = 1;
            ResultSet rs = null;
            AccXrate axr = null;
            //Foreign to Local
            sql = "SELECT * FROM acc_xrate WHERE is_deleted=0 AND is_Active=1 AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aFromCurCode + "'";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    axr = new AccXrate();
                    this.setAccXrateFromResultset(axr, rs);
                    FromXrate = axr.getSelling();
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            //Local to Foreign
            sql = "SELECT * FROM acc_xrate WHERE is_deleted=0 AND is_Active=1 AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aToCurCode + "'";
            rs = null;
            axr = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    axr = new AccXrate();
                    this.setAccXrateFromResultset(axr, rs);
                    ToXrate = axr.getBuying();
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            //get rate
            xrate = FromXrate / ToXrate;
        } else {
            xrate = 1;
        }
        return xrate;
    }

    public double getXrateMultiply(String aFromCurCode, String aToCurCode) {
        double xrate = 1;
        double XrateMultiply = 1;
        AccCurrency LocalCurrency = null;
        LocalCurrency = new AccCurrencyBean().getLocalCurrency();
        try {
            xrate = this.getXrate(aFromCurCode, aToCurCode);
        } catch (NullPointerException npe) {
            xrate = 1;
        }
        try {
            if (aFromCurCode.equals(LocalCurrency.getCurrencyCode()) && !aToCurCode.equals(LocalCurrency.getCurrencyCode())) {
                XrateMultiply = 1 / xrate;
            } else {
                XrateMultiply = xrate;
            }
        } catch (NullPointerException npe) {
            XrateMultiply = 1;
        }
        return XrateMultiply;
    }

    public double getXrateSnapshot(String aFromCurCode, String aToCurCode, int aAccPeriodId) {
        int SnapshotMaxNo = 0;
        SnapshotMaxNo = this.getSnapshotMaxNoXrate(aAccPeriodId);
        String sql;
        double xrate = 1;
        AccCurrency LocalCurrency = null;
        LocalCurrency = new AccCurrencyBean().getLocalCurrency();
        if (aFromCurCode.equals(aToCurCode)) {
            xrate = 1;
            return xrate;
        } else if (aFromCurCode.equals(LocalCurrency.getCurrencyCode()) && !aToCurCode.equals(LocalCurrency.getCurrencyCode())) {//Local-2-Foreign
            sql = "SELECT * FROM snapshot_xrate WHERE is_deleted=0 AND is_Active=1 AND snapshot_no=" + SnapshotMaxNo + " AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aToCurCode + "'";
            ResultSet rs = null;
            AccXrate axr = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    //axr = new AccXrate();
                    //this.setAccXrateFromResultset(axr, rs);
                    //xrate = axr.getBuying();
                    try {
                        xrate = rs.getDouble("buying");
                    } catch (NullPointerException npe) {
                        xrate = 1;
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        } else if (!aFromCurCode.equals(LocalCurrency.getCurrencyCode()) && aToCurCode.equals(LocalCurrency.getCurrencyCode())) {//Foreign-2-Local
            sql = "SELECT * FROM snapshot_xrate WHERE is_deleted=0 AND is_Active=1 AND snapshot_no=" + SnapshotMaxNo + " AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aFromCurCode + "'";
            ResultSet rs = null;
            AccXrate axr = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    //axr = new AccXrate();
                    //this.setAccXrateFromResultset(axr, rs);
                    //xrate = axr.getSelling();
                    try {
                        xrate = rs.getDouble("selling");
                    } catch (NullPointerException npe) {
                        xrate = 1;
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        } else if (!aFromCurCode.equals(LocalCurrency.getCurrencyCode()) && !aToCurCode.equals(LocalCurrency.getCurrencyCode())) {//Foreign-2-Foreign
            double FromXrate = 1;
            double ToXrate = 1;
            ResultSet rs = null;
            AccXrate axr = null;
            //Foreign to Local
            sql = "SELECT * FROM snapshot_xrate WHERE is_deleted=0 AND is_Active=1 AND snapshot_no=" + SnapshotMaxNo + " AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aFromCurCode + "'";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    //axr = new AccXrate();
                    //this.setAccXrateFromResultset(axr, rs);
                    //FromXrate = axr.getSelling();
                    try {
                        FromXrate = rs.getDouble("selling");
                    } catch (NullPointerException npe) {
                        FromXrate = 1;
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            //Local to Foreign
            sql = "SELECT * FROM snapshot_xrate WHERE is_deleted=0 AND is_Active=1 AND snapshot_no=" + SnapshotMaxNo + " AND local_currency_id=" + LocalCurrency.getAccCurrencyId() + " AND foreign_currency_code='" + aToCurCode + "'";
            rs = null;
            axr = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    //axr = new AccXrate();
                    //this.setAccXrateFromResultset(axr, rs);
                    //ToXrate = axr.getBuying();
                    try {
                        ToXrate = rs.getDouble("buying");
                    } catch (NullPointerException npe) {
                        ToXrate = 1;
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            //get rate
            xrate = FromXrate / ToXrate;
        } else {
            xrate = 1;
        }
        return xrate;
    }

    /**
     * @return the AccXrateList
     */
    public List<AccXrate> getAccXrateList() {
        return AccXrateList;
    }

    /**
     * @param AccXrateList the AccXrateList to set
     */
    public void setAccXrateList(List<AccXrate> AccXrateList) {
        this.AccXrateList = AccXrateList;
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
