package beans;

import api_tax.efris.EFRIS_excise_duty_list;
import api_tax.efris_bean.EFRIS_excise_duty_listBean;
import connections.DBConnection;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.Item_unit;
import entities.TransItem;
import entities.Transaction_item_cr_dr_note_unit;
import entities.Transaction_item_excise;
import entities.Transaction_item_hist_unit;
import entities.Transaction_item_unit;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
@ManagedBean
@SessionScoped
public class TransItemExtBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransItemExtBean.class.getName());

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void calVatFromAmountAtPurchase(TransItem aTransItem, String aCurrencyCode) {
        try {
            if (null != aTransItem) {
                double VatPerc = CompanySetting.getVatPerc();
                double VatAmount = 0;
                double AmountExcVat = 0;
                double AmountIncVat = aTransItem.getUnitPrice();
                if (VatPerc > 0 && AmountIncVat > 0) {
                    AmountExcVat = AmountIncVat / (1 + (VatPerc / 100));
                    AmountExcVat = new AccCurrencyBean().roundAmount(aCurrencyCode, AmountExcVat);
                } else {
                    AmountExcVat = AmountIncVat;
                }
                VatAmount = AmountIncVat - AmountExcVat;
                aTransItem.setUnitPrice(AmountExcVat);
                aTransItem.setUnitVat(VatAmount);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransaction_item_unitFromResultset(Transaction_item_unit aTransaction_item_unit, ResultSet aResultSet) {
        try {
            try {
                aTransaction_item_unit.setTransaction_item_id(aResultSet.getLong("transaction_item_id"));
            } catch (Exception e) {
                aTransaction_item_unit.setTransaction_item_id(0);
            }
            try {
                aTransaction_item_unit.setUnit_id(aResultSet.getInt("unit_id"));
            } catch (Exception e) {
                aTransaction_item_unit.setUnit_id(0);
            }
            try {
                aTransaction_item_unit.setBase_unit_qty(aResultSet.getDouble("base_unit_qty"));
            } catch (Exception e) {
                aTransaction_item_unit.setBase_unit_qty(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransaction_item_exciseFromResultset(Transaction_item_excise aTransaction_item_excise, ResultSet aResultSet) {
        try {
            try {
                aTransaction_item_excise.setTransaction_item_excise_id(aResultSet.getLong("transaction_item_excise_id"));
            } catch (Exception e) {
                aTransaction_item_excise.setTransaction_item_excise_id(0);
            }
            try {
                aTransaction_item_excise.setTransaction_item_id(aResultSet.getLong("transaction_item_id"));
            } catch (Exception e) {
                aTransaction_item_excise.setTransaction_item_id(0);
            }
            try {
                aTransaction_item_excise.setExcise_duty_code(aResultSet.getString("excise_duty_code"));
            } catch (Exception e) {
                aTransaction_item_excise.setExcise_duty_code("");
            }
            try {
                aTransaction_item_excise.setRate_name(aResultSet.getString("rate_name"));
            } catch (Exception e) {
                aTransaction_item_excise.setRate_name("");
            }
            try {
                aTransaction_item_excise.setRate_perc(aResultSet.getDouble("rate_perc"));
            } catch (Exception e) {
                aTransaction_item_excise.setRate_perc(0);
            }
            try {
                aTransaction_item_excise.setRate_value(aResultSet.getDouble("rate_value"));
            } catch (Exception e) {
                aTransaction_item_excise.setRate_value(0);
            }
            try {
                aTransaction_item_excise.setCalc_excise_tax_amount(aResultSet.getDouble("calc_excise_tax_amount"));
            } catch (Exception e) {
                aTransaction_item_excise.setCalc_excise_tax_amount(0);
            }
            try {
                aTransaction_item_excise.setRate_currency_code_tax(aResultSet.getString("rate_currency_code_tax"));
            } catch (Exception e) {
                aTransaction_item_excise.setRate_currency_code_tax("");
            }
            try {
                aTransaction_item_excise.setRate_unit_code_tax(aResultSet.getString("rate_unit_code_tax"));
            } catch (Exception e) {
                aTransaction_item_excise.setRate_unit_code_tax("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public long getTransItemId(long aTransId, long aItemId, String aBatchno, String aCodeSpecific, String aDescSpecific) {
        long TransItemId = 0;
        String sql = "SELECT * FROM transaction_item WHERE transaction_id=" + aTransId + " AND item_id=" + aItemId + " AND batchno='" + aBatchno + "' AND code_specific='" + aCodeSpecific + "' AND desc_specific='" + aDescSpecific + "'";
        ResultSet rs = null;
        TransItem ti = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                TransItemId = rs.getLong("transaction_item_id");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return TransItemId;
    }

    public long insertTransaction_item_unit(Transaction_item_unit aTransaction_item_unit) {
        long newId = 0;
        String sql = "INSERT INTO transaction_item_unit"
                + "(transaction_item_id,unit_id,base_unit_qty)"
                + " VALUES"
                + "(?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setLong(1, aTransaction_item_unit.getTransaction_item_id());
            ps.setInt(2, aTransaction_item_unit.getUnit_id());
            ps.setDouble(3, aTransaction_item_unit.getBase_unit_qty());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return newId;
    }

    public int updateTransaction_item_unit(long aTransaction_item_id, double aBase_unit_qty) {
        int success = 0;
        String sql = "UPDATE transaction_item_unit SET base_unit_qty=? WHERE transaction_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setLong(1, aTransaction_item_id);
            ps.setDouble(2, aBase_unit_qty);
            ps.executeUpdate();
            success = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return success;
    }

    public void insertTransaction_item_cr_dr_note_unit(Transaction_item_cr_dr_note_unit aTransaction_item_cr_dr_note_unit) {
        String sql = "INSERT INTO transaction_item_cr_dr_note_unit"
                + "(transaction_item_id,unit_id,base_unit_qty)"
                + " VALUES"
                + "(?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setLong(1, aTransaction_item_cr_dr_note_unit.getTransaction_item_id());
            ps.setInt(2, aTransaction_item_cr_dr_note_unit.getUnit_id());
            ps.setDouble(3, aTransaction_item_cr_dr_note_unit.getBase_unit_qty());
            ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertTransaction_item_hist_unit(Transaction_item_hist_unit aTransaction_item_hist_unit) {
        int inserted = 0;
        String sql = "INSERT INTO transaction_item_hist_unit"
                + "(transaction_item_hist_id,unit_id,base_unit_qty)"
                + " VALUES"
                + "(?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransaction_item_hist_unit.getTransaction_item_hist_id());
            ps.setInt(2, aTransaction_item_hist_unit.getUnit_id());
            ps.setDouble(3, aTransaction_item_hist_unit.getBase_unit_qty());
            ps.executeUpdate();
            inserted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return inserted;
    }

    public long insertTransaction_item_excise(Transaction_item_excise aTransaction_item_excise) {
        long newId = 0;
        String sql = "INSERT INTO transaction_item_excise"
                + "(transaction_item_id,excise_duty_code,rate_name,rate_perc,rate_value,calc_excise_tax_amount,rate_currency_code_tax,rate_unit_code_tax)"
                + " VALUES"
                + "(?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setLong(1, aTransaction_item_excise.getTransaction_item_id());
            ps.setString(2, aTransaction_item_excise.getExcise_duty_code());
            ps.setString(3, aTransaction_item_excise.getRate_name());
            ps.setDouble(4, aTransaction_item_excise.getRate_perc());
            ps.setDouble(5, aTransaction_item_excise.getRate_value());
            ps.setDouble(6, aTransaction_item_excise.getCalc_excise_tax_amount());
            ps.setString(7, aTransaction_item_excise.getRate_currency_code_tax());
            ps.setString(8, aTransaction_item_excise.getRate_unit_code_tax());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return newId;
    }

    public int deleteTransItemsUnitByTransId(long aTransId) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_item_unit WHERE transaction_item_id IN("
                + "SELECT ti.transaction_item_id FROM transaction_item ti WHERE ti.transaction_id=?"
                + ")";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransId);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            deleted = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
    }

    public int deleteTransaction_item_unit(long aTransaction_item_id) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_item_unit WHERE transaction_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransaction_item_id);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            deleted = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
    }

    public Transaction_item_excise getExciseDutyTax(String aExciseDutyCode, long aItemId, int aToUnitId, String aToCurrencyCode, double aQty, double aUnitPrice, int aIncludesED) {
        Transaction_item_excise obj = new Transaction_item_excise();
        double ExciseTaxAmount = 0;
        try {
            EFRIS_excise_duty_list ExciseDutyDtl = null;
            String FromUnitCodeTax = "";
            int FromUnitId = 0;
            String FromCurrencyCodeTax = "";
            String FromCurrencyCode = "";
            if (aExciseDutyCode.length() > 0) {
                ExciseDutyDtl = new EFRIS_excise_duty_listBean().getEFRIS_invoice_detailByExciseDutyCode(aExciseDutyCode);
                if (null != ExciseDutyDtl) {
                    FromUnitCodeTax = ExciseDutyDtl.getUnit();
                    FromCurrencyCodeTax = ExciseDutyDtl.getCurrency();
                    if (null == FromUnitCodeTax) {
                        FromUnitCodeTax = "";
                    }
                    if (null == FromCurrencyCodeTax) {
                        FromCurrencyCodeTax = "";
                    }
                    if (FromCurrencyCodeTax.length() > 0) {
                        AccCurrency AccCur = new AccCurrencyBean().getCurrencyByTaxCode(FromCurrencyCodeTax);
                        if (null != AccCur) {
                            FromCurrencyCode = AccCur.getCurrencyCode();
                            if (null == FromCurrencyCode) {
                                FromCurrencyCode = "";
                            }
                        }
                    }
                    double RatePerc = 0;
                    double FromRateVal = 0;
                    double ToRateVal = 0;
                    try {
                        RatePerc = Double.parseDouble(ExciseDutyDtl.getRate_perc());
                    } catch (Exception e) {
                    }
                    try {
                        FromRateVal = Double.parseDouble(ExciseDutyDtl.getRate_value());
                    } catch (Exception e) {
                    }
                    try {
                        ToRateVal = Double.parseDouble(ExciseDutyDtl.getRate_value());
                    } catch (Exception e) {
                    }
                    double TaxViaPerc = 0;
                    double TaxViaValue = 0;
                    double UnitConvertRatio = 1.0;
                    if (FromUnitCodeTax.length() > 0) {
                        Item_unit iu = new ItemBean().getItemUnitFrmDb(aItemId, FromUnitCodeTax);
                        if (null != iu) {
                            FromUnitId = iu.getUnit_id();
                        }
                        if (FromUnitId > 0 && aToUnitId > 0) {
                            UnitConvertRatio = new ItemBean().getUnitConversionRate(aItemId, FromUnitId, aToUnitId);
                        }
                    }
                    if (RatePerc > 0) {
                        if (aIncludesED == 1) {
                            TaxViaPerc = UnitConvertRatio * aQty * (aUnitPrice - (aUnitPrice / (1 + (0.01 * RatePerc))));
                        } else {
                            TaxViaPerc = 0.01 * RatePerc * UnitConvertRatio * aQty * aUnitPrice;
                        }
                    }
                    if (FromRateVal > 0) {
                        if (aIncludesED == 1) {
                            if (aToCurrencyCode.length() > 0 && FromCurrencyCode.length() > 0) {
                                ToRateVal = new AccXrateBean().convertCurrency(FromRateVal, FromCurrencyCode, aToCurrencyCode);
                            }
                            //TaxViaValue = (aUnitPrice - ToRateVal) * UnitConvertRatio * aQty;
                            TaxViaValue = ToRateVal * UnitConvertRatio * aQty;
                        } else {
                            if (aToCurrencyCode.length() > 0 && FromCurrencyCode.length() > 0) {
                                ToRateVal = new AccXrateBean().convertCurrency(FromRateVal, FromCurrencyCode, aToCurrencyCode);
                            }
                            TaxViaValue = ToRateVal * UnitConvertRatio * aQty;
                        }
                    }
                    if (TaxViaPerc >= TaxViaValue) {
                        ExciseTaxAmount = TaxViaPerc;
                    } else {
                        ExciseTaxAmount = TaxViaValue;
                    }
                    //update the obj
                    obj.setCalc_excise_tax_amount(ExciseTaxAmount);
                    obj.setExcise_duty_code(aExciseDutyCode);
                    obj.setRate_name(ExciseDutyDtl.getRateText());
                    obj.setRate_perc(RatePerc);
                    obj.setRate_value(FromRateVal);
                    obj.setRate_currency_code_tax(FromCurrencyCodeTax);
                    obj.setRate_unit_code_tax(FromUnitCodeTax);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return obj;
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
