package beans;

import connections.DBConnection;
import entities.AccCurrency;
import entities.AccXrate;
import entities.Acc_currency_tax_list;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.math3.util.Precision;
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
@ManagedBean(name = "accCurrencyBean")
@SessionScoped
public class AccCurrencyBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(AccCurrencyBean.class.getName());
    private String ActionMessage = null;
    private List<AccCurrency> IsoCurrencyList;
    private AccCurrency IsoCurrencyObject;
    private int IsoCurrencyId;
    private List<AccCurrency> CurrencyList;
    private static List<AccCurrency> SavedCurrencyLists = new ArrayList<>();
    private List<Acc_currency_tax_list> Acc_currency_tax_lists;

    public void setAccCurrencyFromResultset(AccCurrency acccurrency, ResultSet aResultSet) {
        try {
            try {
                acccurrency.setAccCurrencyId(aResultSet.getInt("acc_currency_id"));
            } catch (NullPointerException npe) {
                acccurrency.setAccCurrencyId(0);
            }
            try {
                acccurrency.setCurrencyName(aResultSet.getString("currency_name"));
            } catch (NullPointerException npe) {
                acccurrency.setCurrencyName("");
            }
            try {
                acccurrency.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                acccurrency.setCurrencyCode("");
            }
            try {
                acccurrency.setCurrency_code_tax(aResultSet.getString("currency_code_tax"));
            } catch (NullPointerException npe) {
                acccurrency.setCurrency_code_tax("");
            }
            try {
                acccurrency.setCurrencyNo(aResultSet.getInt("currency_no"));
            } catch (NullPointerException npe) {
                acccurrency.setCurrencyNo(0);
            }
            try {
                acccurrency.setIsLocalCurrency(aResultSet.getInt("is_local_currency"));
            } catch (NullPointerException npe) {
                acccurrency.setIsLocalCurrency(0);
            }
            try {
                acccurrency.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                acccurrency.setIsActive(0);
            }
            try {
                acccurrency.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                acccurrency.setIsDeleted(0);
            }
            try {
                acccurrency.setAddBy(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                acccurrency.setAddBy(0);
            }
            try {
                acccurrency.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (NullPointerException npe) {
                acccurrency.setLastEditBy(0);
            }
            try {
                acccurrency.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                acccurrency.setAddDate(null);
            }
            try {
                acccurrency.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (NullPointerException npe) {
                acccurrency.setLastEditDate(null);
            }
            AccXrate FXRecord = null;
            FXRecord = new AccXrateBean().getXrateByFX(acccurrency.getCurrencyCode());
            try {
                acccurrency.setBuying(FXRecord.getBuying());
            } catch (NullPointerException npe) {
                acccurrency.setBuying(1);
            }
            try {
                acccurrency.setSelling(FXRecord.getSelling());
            } catch (NullPointerException npe) {
                acccurrency.setSelling(1);
            }
            try {
                acccurrency.setDecimal_places(aResultSet.getInt("decimal_places"));
            } catch (NullPointerException npe) {
                acccurrency.setDecimal_places(3);
            }
            try {
                acccurrency.setRounding_mode(aResultSet.getInt("rounding_mode"));
            } catch (NullPointerException npe) {
                acccurrency.setRounding_mode(4);
            }
            try {
                acccurrency.setCurrency_unit(aResultSet.getString("currency_unit"));
            } catch (Exception e) {
                acccurrency.setCurrency_unit("");
            }
            try {
                acccurrency.setDecimal_unit(aResultSet.getString("decimal_unit"));
            } catch (Exception e) {
                acccurrency.setDecimal_unit("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveAccCurrency(AccCurrency aAccCurrency) {
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (aAccCurrency.getAccCurrencyId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR..."));
        } else if (aAccCurrency.getAccCurrencyId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR..."));
        } else if (aAccCurrency.getBuying() <= 0 || aAccCurrency.getSelling() <= 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Exchange rates for buying and selling cannot be 0(zero)..."));
        } else {
            String sql = "{call sp_save_acc_currency(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (null != aAccCurrency) {
                    try {
                        cs.setInt("in_acc_currency_id", aAccCurrency.getAccCurrencyId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_currency_id", 0);
                    }
                    try {
                        cs.setString("in_currency_name", aAccCurrency.getCurrencyName());
                    } catch (NullPointerException npe) {
                        cs.setString("in_currency_name", "");
                    }
                    try {
                        cs.setString("in_currency_code", aAccCurrency.getCurrencyCode());
                    } catch (NullPointerException npe) {
                        cs.setString("in_currency_code", "");
                    }
                    try {
                        cs.setString("in_currency_code_tax", aAccCurrency.getCurrency_code_tax());
                    } catch (NullPointerException npe) {
                        cs.setString("in_currency_code_tax", "");
                    }
                    try {
                        cs.setInt("in_currency_no", aAccCurrency.getCurrencyNo());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_currency_no", 0);
                    }
                    try {
                        cs.setInt("in_is_local_currency", aAccCurrency.getIsLocalCurrency());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_local_currency", 0);
                    }
                    try {
                        cs.setInt("in_is_active", aAccCurrency.getIsActive());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_active", 0);
                    }
                    try {
                        cs.setInt("in_is_deleted", aAccCurrency.getIsDeleted());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_deleted", 0);
                    }
                    try {
                        cs.setInt("in_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_user_detail_id", 0);
                    }
                    try {
                        cs.setInt("in_decimal_places", aAccCurrency.getDecimal_places());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_decimal_places", 3);
                    }
                    try {
                        cs.setInt("in_rounding_mode", aAccCurrency.getRounding_mode());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_rounding_mode", 4);
                    }
                    try {
                        cs.setString("in_currency_unit", aAccCurrency.getCurrency_unit());
                    } catch (Exception e) {
                        cs.setString("in_currency_unit", "");
                    }
                    try {
                        cs.setString("in_decimal_unit", aAccCurrency.getDecimal_unit());
                    } catch (Exception e) {
                        cs.setString("in_decimal_unit", "");
                    }
                    cs.executeUpdate();
                    new AccXrateBean().saveAccXrate(aAccCurrency);//save Xrate
                    new AccPeriodBean().saveSnapshotXrate(new AccPeriodBean().getAccPeriodCurrent());//take snapshots of xrates
                    this.setActionMessage("Saved Successfully!");
                    this.clearAccCurrency(aAccCurrency);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public AccCurrency getCurrency(int aCurrencyId) {
        String sql;
        sql = "SELECT * FROM acc_currency WHERE acc_currency_id=" + aCurrencyId;
        ResultSet rs = null;
        AccCurrency ac = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ac;
    }

    public AccCurrency getCurrency(String aCurrencyCode) {
        String sql;
        sql = "SELECT * FROM acc_currency WHERE currency_code='" + aCurrencyCode + "'";
        ResultSet rs = null;
        AccCurrency ac = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ac;
    }

    public void deleteAccCurrency(AccCurrency aAccCurrency) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            String sql = "UPDATE acc_currency SET is_deleted=1 WHERE acc_currency_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aAccCurrency.getAccCurrencyId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.clearAccCurrency(aAccCurrency);
            } catch (Exception e) {
                this.setActionMessage("Currency not deleted");
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public List<AccCurrency> getAccCurrenciesActive() {
        String sql;
        sql = "SELECT * FROM acc_currency WHERE is_deleted=0 AND is_Active=1 ORDER BY is_local_currency DESC,currency_code ASC";
        ResultSet rs = null;
        List<AccCurrency> acs = new ArrayList<AccCurrency>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCurrency ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public List<AccCurrency> getAccCurrenciesActiveByChildAccount(String aChildAccountCode) {
        String AndCurrency = "";
        String AccountCurrencyCode = "";
        try {
            AccountCurrencyCode = new AccChildAccountBean().getAccChildAccByCode(aChildAccountCode).getCurrencyCode();
            if (AccountCurrencyCode.length() > 0) {
                AndCurrency = " AND currency_code='" + AccountCurrencyCode + "'";
            }
        } catch (Exception e) {
            AccountCurrencyCode = "";
        }
        String sql;
        sql = "SELECT * FROM acc_currency WHERE is_deleted=0 AND is_Active=1" + AndCurrency + " ORDER BY is_local_currency DESC,currency_code ASC";
        ResultSet rs = null;
        List<AccCurrency> acs = new ArrayList<AccCurrency>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCurrency ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public void initCurrencyList() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.refreshCurrencyListActive();
        }
    }

    public void refreshCurrencyListActive() {
        String sql;
        sql = "SELECT * FROM acc_currency WHERE is_deleted=0 AND is_Active=1 ORDER BY is_local_currency DESC,currency_code ASC";
        ResultSet rs = null;
        this.CurrencyList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccCurrency ac;
            while (rs.next()) {
                ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
                this.CurrencyList.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<AccCurrency> getAccCurrenciesActiveByDefaultCur() {
        String sql;
        sql = "SELECT c.* FROM acc_currency c LEFT JOIN parameter_list p ON c.currency_code=p.parameter_value AND p.parameter_name='DEFAULT_CURRENCY_CODE' "
                + "WHERE c.is_deleted=0 AND c.is_Active=1 ORDER BY p.parameter_value desc,c.is_local_currency desc,c.currency_code asc";
        ResultSet rs = null;
        List<AccCurrency> acs = new ArrayList<AccCurrency>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCurrency ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public List<AccCurrency> getAccCurrenciesLocal() {
        String sql;
        sql = "SELECT * FROM acc_currency WHERE is_deleted=0 AND is_Active=1 AND is_local_currency=1";
        ResultSet rs = null;
        List<AccCurrency> acs = new ArrayList<AccCurrency>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCurrency ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public List<AccCurrency> getAccCurrenciesAll() {
        String sql;
        sql = "SELECT * FROM acc_currency WHERE is_deleted=0 ORDER BY is_local_currency DESC,currency_code ASC";
        ResultSet rs = null;
        List<AccCurrency> acs = new ArrayList<AccCurrency>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCurrency ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public AccCurrency getLocalCurrency() {
        String sql;
        sql = "SELECT * FROM acc_currency WHERE is_deleted=0 AND is_Active=1 AND is_local_currency=1";
        ResultSet rs = null;
        AccCurrency ac = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ac = new AccCurrency();
                this.setAccCurrencyFromResultset(ac, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ac;
    }

    public void refreshIsoCurrencies() {
        int IsoId = 0;
        String sql;
        sql = "SELECT distinct currency as currency_name,alphabetic_code as currency_code,numeric_code as currency_no,minor_unit as decimal_places FROM iso_currency WHERE length(alphabetic_code)>1 ORDER BY alphabetic_code ASC,currency ASC";
        ResultSet rs = null;
        this.IsoCurrencyList = new ArrayList<AccCurrency>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                IsoId = IsoId + 1;
                AccCurrency ac = new AccCurrency();
                ac.setAccCurrencyId(IsoId);
                try {
                    ac.setCurrencyName(rs.getString("currency_name"));
                } catch (NullPointerException npe) {
                    ac.setCurrencyName("");
                }
                try {
                    ac.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    ac.setCurrencyCode("");
                }
                try {
                    ac.setCurrencyNo(rs.getInt("currency_no"));
                } catch (NullPointerException npe) {
                    ac.setCurrencyNo(0);
                }
                try {
                    ac.setDecimal_places(rs.getInt("decimal_places"));
                } catch (NullPointerException npe) {
                    ac.setDecimal_places(3);
                }
                this.IsoCurrencyList.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void initClearAccCurrency(AccCurrency aAccCurrency) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (aAccCurrency != null) {
                this.clearAccCurrency(aAccCurrency);
            }
        }
    }

    public void clearAccCurrency(AccCurrency aAccCurrency) {
        if (null != aAccCurrency) {
            aAccCurrency.setAccCurrencyId(0);
            aAccCurrency.setCurrencyName("");
            aAccCurrency.setCurrencyCode("");
            aAccCurrency.setCurrency_code_tax("");
            aAccCurrency.setCurrencyNo(0);
            aAccCurrency.setIsLocalCurrency(0);
            aAccCurrency.setIsActive(0);
            aAccCurrency.setIsDeleted(0);
            aAccCurrency.setAddBy(0);
            aAccCurrency.setLastEditBy(0);
            aAccCurrency.setAddDate(null);
            aAccCurrency.setLastEditDate(null);
            aAccCurrency.setBuying(1);
            aAccCurrency.setSelling(1);
            aAccCurrency.setDecimal_places(3);
            aAccCurrency.setRounding_mode(4);
            aAccCurrency.setCurrency_unit("");
            aAccCurrency.setDecimal_unit("");
        }
    }

    public void copyAccCurrency(AccCurrency aFrom, AccCurrency aTo) {
        aTo.setAccCurrencyId(aFrom.getAccCurrencyId());
        aTo.setCurrencyCode(aFrom.getCurrencyCode());
        aTo.setCurrency_code_tax(aFrom.getCurrency_code_tax());
        aTo.setCurrencyName(aFrom.getCurrencyName());
        aTo.setCurrencyNo(aFrom.getCurrencyNo());
        aTo.setIsLocalCurrency(aFrom.getIsLocalCurrency());
        aTo.setIsActive(aFrom.getIsActive());
        aTo.setIsDeleted(aFrom.getIsDeleted());
        aTo.setAddBy(aFrom.getAddBy());
        aTo.setAddDate(aFrom.getAddDate());
        aTo.setLastEditBy(aFrom.getLastEditBy());
        aTo.setLastEditDate(aFrom.getLastEditDate());
        aTo.setBuying(aFrom.getBuying());
        aTo.setSelling(aFrom.getSelling());
        aTo.setDecimal_places(aFrom.getDecimal_places());
        aTo.setRounding_mode(aFrom.getRounding_mode());
        aTo.setCurrency_unit(aFrom.getCurrency_unit());
        aTo.setDecimal_unit(aFrom.getDecimal_unit());
    }

    public void setCurrencyFromIso(AccCurrency aAccCurrencyIso, AccCurrency aAccCurrency) {
        if (null != aAccCurrency) {
            aAccCurrency.setCurrencyCode(aAccCurrencyIso.getCurrencyCode());
            aAccCurrency.setCurrencyName(aAccCurrencyIso.getCurrencyName());
            aAccCurrency.setCurrencyNo(aAccCurrencyIso.getCurrencyNo());
            aAccCurrency.setDecimal_places(aAccCurrencyIso.getDecimal_places());
        }
    }

    public void setCurrencyFromIso(int aAccCurrencyIdIso, AccCurrency aAccCurrency) {
        if (null != aAccCurrency && aAccCurrencyIdIso > 0) {
            int ListItemIndex = 0;
            int ListItemNo = this.IsoCurrencyList.size();
            while (ListItemIndex < ListItemNo) {
                if (this.IsoCurrencyList.get(ListItemIndex).getAccCurrencyId() == aAccCurrencyIdIso) {
                    aAccCurrency.setCurrencyCode(this.IsoCurrencyList.get(ListItemIndex).getCurrencyCode());
                    aAccCurrency.setCurrencyName(this.IsoCurrencyList.get(ListItemIndex).getCurrencyName());
                    aAccCurrency.setCurrencyNo(this.IsoCurrencyList.get(ListItemIndex).getCurrencyNo());
                    aAccCurrency.setDecimal_places(this.IsoCurrencyList.get(ListItemIndex).getDecimal_places());
                    break;
                }
                ListItemIndex = ListItemIndex + 1;
            }
        }
    }

    public void initIsoCurrencyList() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.refreshIsoCurrencies();
        }
    }

    public double roundAmount(String aCurrencyCode, double aMount) {
        double RoundedAmount = 0;
        try {
            AccCurrency currency = new AccCurrencyBean().getCurrency(aCurrencyCode);
            int DecimalPlaces = currency.getDecimal_places();
            int RoundingMode = currency.getRounding_mode();
            RoundedAmount = Precision.round(aMount, DecimalPlaces, RoundingMode);
        } catch (Exception e) {
        }
        return RoundedAmount;
    }

    /**
     * Rounds off to at least 2 decimal places If currency rounds off to less 2
     * decimal places, values will be rounded off to 2 decimal places otherwise
     * decimal places rounding takes precedence
     *
     * @param aCurrencyCode
     * @param aMount
     * @return RoundedAmount
     */
    public double roundAmountMinTwoDps(String aCurrencyCode, double aMount) {
        double RoundedAmount = 0;
        try {
            AccCurrency currency = new AccCurrencyBean().getCurrency(aCurrencyCode);
            int DecimalPlaces = currency.getDecimal_places();
            int RoundingMode = currency.getRounding_mode();
            if (DecimalPlaces < 2) {
                DecimalPlaces = 2;
            }
            RoundedAmount = Precision.round(aMount, DecimalPlaces, RoundingMode);
        } catch (Exception e) {
        }
        return RoundedAmount;
    }

    public void refreshSavedCurrencyLists() {
        String sql;
        sql = "SELECT * FROM acc_currency";
        ResultSet rs = null;
        SavedCurrencyLists = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCurrency cur = new AccCurrency();
                this.setAccCurrencyFromResultset(cur, rs);
                SavedCurrencyLists.add(cur);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public AccCurrency getCurrencyFromListMemory(String aCurrencyCode) {
        int ListItemIndex = 0;
        AccCurrency cur = null;
        try {
            int ListItemNo = SavedCurrencyLists.size();
            while (ListItemIndex < ListItemNo) {
                if (aCurrencyCode.equals(SavedCurrencyLists.get(ListItemIndex).getCurrencyCode())) {
                    cur = new AccCurrency();
                    cur = SavedCurrencyLists.get(ListItemIndex);
                    break;
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return cur;
    }

    public String getNumberFormatByCurrency(String aCurrencyCode) {
        String NumberFormat = "###,###.###";
        String DecimalPlaceSubStr = "";
        int decimal_places = 0;
        try {
            decimal_places = this.getCurrencyFromListMemory(aCurrencyCode).getDecimal_places();
            for (int i = 1; i <= decimal_places; i++) {
                DecimalPlaceSubStr = DecimalPlaceSubStr + "0";
            }
        } catch (Exception e) {
        }
        if (decimal_places > 0) {
            NumberFormat = "###,###." + DecimalPlaceSubStr;
        } else {
            NumberFormat = "###,###";
        }
        return NumberFormat;
    }

    public String getNumberFormatByCurrencyPlain(String aCurrencyCode) {
        String NumberFormat = "###.###";
        String DecimalPlaceSubStr = "";
        int decimal_places = 0;
        try {
            decimal_places = this.getCurrencyFromListMemory(aCurrencyCode).getDecimal_places();
            for (int i = 1; i <= decimal_places; i++) {
                DecimalPlaceSubStr = DecimalPlaceSubStr + "0";
            }
        } catch (Exception e) {
        }
        if (decimal_places > 0) {
            NumberFormat = "###." + DecimalPlaceSubStr;
        } else {
            NumberFormat = "###";
        }
        return NumberFormat;
    }

    public List<Acc_currency_tax_list> getAcc_currency_tax_lists() {
        String sql;
        sql = "SELECT * FROM acc_currency_tax_list ORDER BY currency_name_tax ASC";
        ResultSet rs = null;
        List<Acc_currency_tax_list> lst = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Acc_currency_tax_list obj = new Acc_currency_tax_list();
                obj.setAcc_currency_list_id(rs.getInt("acc_currency_list_id"));
                obj.setCurrency_code_tax(rs.getString("currency_code_tax"));
                obj.setCurrency_name_tax(rs.getString("currency_name_tax"));
                lst.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return lst;
    }

    public void refreshAcc_currency_tax_lists() {
        this.setAcc_currency_tax_lists(this.getAcc_currency_tax_lists());
    }

    /**
     * @return the IsoCurrencyList
     */
    public List<AccCurrency> getIsoCurrencyList() {
        return IsoCurrencyList;
    }

    /**
     * @param IsoCurrencyList the IsoCurrencyList to set
     */
    public void setIsoCurrencyList(List<AccCurrency> IsoCurrencyList) {
        this.IsoCurrencyList = IsoCurrencyList;
    }

    /**
     * @return the IsoCurrencyObject
     */
    public AccCurrency getIsoCurrencyObject() {
        return IsoCurrencyObject;
    }

    /**
     * @param IsoCurrencyObject the IsoCurrencyObject to set
     */
    public void setIsoCurrencyObject(AccCurrency IsoCurrencyObject) {
        this.IsoCurrencyObject = IsoCurrencyObject;
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
     * @return the IsoCurrencyId
     */
    public int getIsoCurrencyId() {
        return IsoCurrencyId;
    }

    /**
     * @param IsoCurrencyId the IsoCurrencyId to set
     */
    public void setIsoCurrencyId(int IsoCurrencyId) {
        this.IsoCurrencyId = IsoCurrencyId;
    }

    /**
     * @return the CurrencyList
     */
    public List<AccCurrency> getCurrencyList() {
        return CurrencyList;
    }

    /**
     * @param CurrencyList the CurrencyList to set
     */
    public void setCurrencyList(List<AccCurrency> CurrencyList) {
        this.CurrencyList = CurrencyList;
    }

    /**
     * @param Acc_currency_tax_lists the Acc_currency_tax_lists to set
     */
    public void setAcc_currency_tax_lists(List<Acc_currency_tax_list> Acc_currency_tax_lists) {
        this.Acc_currency_tax_lists = Acc_currency_tax_lists;
    }

    /**
     * @return the SavedCurrencyLists
     */
    public static List<AccCurrency> getSavedCurrencyLists() {
        return SavedCurrencyLists;
    }

    /**
     * @param aSavedCurrencyLists the SavedCurrencyLists to set
     */
    public static void setSavedCurrencyLists(List<AccCurrency> aSavedCurrencyLists) {
        SavedCurrencyLists = aSavedCurrencyLists;
    }

}
