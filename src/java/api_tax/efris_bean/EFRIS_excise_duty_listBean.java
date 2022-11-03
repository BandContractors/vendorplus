/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_excise_duty_list;
import connections.DBConnection;
import entities.CompanySetting;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author emuwonge
 */
@ManagedBean
@SessionScoped
public class EFRIS_excise_duty_listBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(EFRIS_excise_duty_listBean.class.getName());

    public void setEFRIS_excise_duty_listFromResultset(EFRIS_excise_duty_list aEFRIS_excise_duty_list, ResultSet aResultSet) {
        try {
            try {
                aEFRIS_excise_duty_list.setEFRIS_excise_duty_list_id(aResultSet.getLong("efris_excise_duty_list_id"));
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setEFRIS_excise_duty_list_id(0);
            }
            try {
                aEFRIS_excise_duty_list.setId(aResultSet.getString("id"));
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setId("");
            }
            try {
                if (null == aResultSet.getString("exciseDutyCode")) {
                    aEFRIS_excise_duty_list.setExciseDutyCode("");
                } else {
                    aEFRIS_excise_duty_list.setExciseDutyCode(aResultSet.getString("exciseDutyCode"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setExciseDutyCode("");
            }
            try {
                if (null == aResultSet.getString("goodService")) {
                    aEFRIS_excise_duty_list.setGoodService("");
                } else {
                    aEFRIS_excise_duty_list.setGoodService(aResultSet.getString("goodService"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setGoodService("");
            }
            try {
                if (null == aResultSet.getString("parentCode")) {
                    aEFRIS_excise_duty_list.setParentCode("");
                } else {
                    aEFRIS_excise_duty_list.setParentCode(aResultSet.getString("parentCode"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setParentCode("");
            }
            try {
                if (null == aResultSet.getString("rateText")) {
                    aEFRIS_excise_duty_list.setRateText("");
                } else {
                    aEFRIS_excise_duty_list.setRateText(aResultSet.getString("rateText"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setRateText("");
            }
            try {
                if (null == aResultSet.getString("isLeafNode")) {
                    aEFRIS_excise_duty_list.setIsLeafNode("");
                } else {
                    aEFRIS_excise_duty_list.setIsLeafNode(aResultSet.getString("isLeafNode"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setIsLeafNode("");
            }
            try {
                if (null == aResultSet.getString("effectiveDate")) {
                    aEFRIS_excise_duty_list.setEffectiveDate("");
                } else {
                    aEFRIS_excise_duty_list.setEffectiveDate(aResultSet.getString("effectiveDate"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setEffectiveDate("");
            }
            try {
                if (null == aResultSet.getString("unit")) {
                    aEFRIS_excise_duty_list.setUnit("");
                } else {
                    aEFRIS_excise_duty_list.setUnit(aResultSet.getString("unit"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setUnit("");
            }
            try {
                if (null == aResultSet.getString("currency")) {
                    aEFRIS_excise_duty_list.setCurrency("");
                } else {
                    aEFRIS_excise_duty_list.setCurrency(aResultSet.getString("currency"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setCurrency("");
            }
            try {
                if (null == aResultSet.getString("rate_perc")) {
                    aEFRIS_excise_duty_list.setRate_perc("");
                } else {
                    aEFRIS_excise_duty_list.setRate_perc(aResultSet.getString("rate_perc"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setRate_perc("");
            }
            try {
                if (null == aResultSet.getString("rate_qty")) {
                    aEFRIS_excise_duty_list.setRate_qty("");
                } else {
                    aEFRIS_excise_duty_list.setRate_qty(aResultSet.getString("rate_qty"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setRate_qty("");
            }
            try {
                aEFRIS_excise_duty_list.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setAdd_date(null);
            }
            try {
                if (null == aResultSet.getString("rateText_perc")) {
                    aEFRIS_excise_duty_list.setRateText_perc("");
                } else {
                    aEFRIS_excise_duty_list.setRateText_perc(aResultSet.getString("rateText_perc"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setRateText_perc("");
            }
            try {
                if (null == aResultSet.getString("rateText_qty")) {
                    aEFRIS_excise_duty_list.setRateText_qty("");
                } else {
                    aEFRIS_excise_duty_list.setRateText_qty(aResultSet.getString("rateText_qty"));
                }
            } catch (Exception e) {
                aEFRIS_excise_duty_list.setRateText_qty("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int saveEFRIS_excise_duty_list(List<EFRIS_excise_duty_list> aEFRIS_excise_duty_lists) {
        int saved = 0;
        try {
            int goodsSaved = 0;
            //save goods commodity
            for (int i = 0, size = aEFRIS_excise_duty_lists.size(); i < size; i++) {
                //check if EFRIS_excise_duty_list is already in the db
                EFRIS_excise_duty_list aEFRIS_excise_duty_list = this.getEFRIS_invoice_detailByExciseDutyCode(aEFRIS_excise_duty_lists.get(i).getExciseDutyCode());
                if (aEFRIS_excise_duty_list == null) {
                    goodsSaved = goodsSaved + this.insertEFRIS_excise_duty_list(aEFRIS_excise_duty_lists.get(i));
                } else {
                    //EFRIS_excise_duty_list already exists
                    goodsSaved = goodsSaved + 1;
                }
            }

            if (goodsSaved == aEFRIS_excise_duty_lists.size()) {
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertEFRIS_excise_duty_list(EFRIS_excise_duty_list aEFRIS_excise_duty_list) {
        int saved = 0;
        String sql = "INSERT INTO efris_excise_duty_list"
                + "(id, exciseDutyCode, goodService, parentCode, rateText, isLeafNode, effectiveDate, unit,"
                + "currency, rate_perc, rate_qty, add_date,rateText_perc,rateText_qty)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //id, exciseDutyCode, goodService, parentCode, rateText, isLeafNode, effectiveDate, unit
            if (aEFRIS_excise_duty_list.getId() != null) {
                ps.setString(1, aEFRIS_excise_duty_list.getId());
            } else {
                ps.setString(1, "");
            }
            if (aEFRIS_excise_duty_list.getExciseDutyCode() != null) {
                ps.setString(2, aEFRIS_excise_duty_list.getExciseDutyCode());
            } else {
                ps.setString(2, "");
            }
            if (aEFRIS_excise_duty_list.getGoodService() != null) {
                ps.setString(3, aEFRIS_excise_duty_list.getGoodService());
            } else {
                ps.setString(3, "");
            }
            if (aEFRIS_excise_duty_list.getParentCode() != null) {
                ps.setString(4, aEFRIS_excise_duty_list.getParentCode());
            } else {
                ps.setString(4, "");
            }
            if (aEFRIS_excise_duty_list.getRateText() != null) {
                ps.setString(5, aEFRIS_excise_duty_list.getRateText());
            } else {
                ps.setString(5, "");
            }
            if (aEFRIS_excise_duty_list.getIsLeafNode() != null) {
                ps.setString(6, aEFRIS_excise_duty_list.getIsLeafNode());
            } else {
                ps.setString(6, "");
            }
            if (aEFRIS_excise_duty_list.getEffectiveDate() != null) {
                ps.setString(7, aEFRIS_excise_duty_list.getEffectiveDate());
            } else {
                ps.setString(7, "");
            }
            if (aEFRIS_excise_duty_list.getUnit() != null) {
                ps.setString(8, aEFRIS_excise_duty_list.getUnit());
            } else {
                ps.setString(8, "");
            }
            //currency, rate_perc, rate_qty, add_date
            if (aEFRIS_excise_duty_list.getCurrency() != null) {
                ps.setString(9, aEFRIS_excise_duty_list.getCurrency());
            } else {
                ps.setString(9, "");
            }
            if (aEFRIS_excise_duty_list.getRate_perc() != null) {
                ps.setString(10, aEFRIS_excise_duty_list.getRate_perc());
            } else {
                ps.setString(10, "");
            }
            if (aEFRIS_excise_duty_list.getRate_qty() != null) {
                ps.setString(11, aEFRIS_excise_duty_list.getRate_qty());
            } else {
                ps.setString(11, "");
            }
            ps.setTimestamp(12, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            if (aEFRIS_excise_duty_list.getRateText_perc() != null) {
                ps.setString(13, aEFRIS_excise_duty_list.getRateText_perc());
            } else {
                ps.setString(13, "");
            }
            if (aEFRIS_excise_duty_list.getRateText_qty() != null) {
                ps.setString(14, aEFRIS_excise_duty_list.getRateText_qty());
            } else {
                ps.setString(14, "");
            }
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public EFRIS_excise_duty_list getEFRIS_excise_duty_listById(long aEFRIS_excise_duty_list_id) {
        String sql = "SELECT * FROM efris_excise_duty_list WHERE efris_excise_duty_list_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aEFRIS_excise_duty_list_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                EFRIS_excise_duty_list obj = new EFRIS_excise_duty_list();
                this.setEFRIS_excise_duty_listFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<EFRIS_excise_duty_list> getEFRIS_excise_duty_list_All() {
        String sql = "SELECT * FROM efris_excise_duty_list";
        ResultSet rs;
        List<EFRIS_excise_duty_list> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                EFRIS_excise_duty_list obj = new EFRIS_excise_duty_list();
                this.setEFRIS_excise_duty_listFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    public EFRIS_excise_duty_list getEFRIS_invoice_detailByExciseDutyCode(String aExciseDutyCode) {
        String sql = "SELECT * FROM efris_excise_duty_list where exciseDutyCode=?";
        ResultSet rs;
        EFRIS_excise_duty_list aEFRIS_excise_duty_list = new EFRIS_excise_duty_list();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aExciseDutyCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.setEFRIS_excise_duty_listFromResultset(aEFRIS_excise_duty_list, rs);
            } else {
                aEFRIS_excise_duty_list = null;
            }
        } catch (Exception e) {
            aEFRIS_excise_duty_list = null;
            LOGGER.log(Level.ERROR, e);
        }
        return aEFRIS_excise_duty_list;
    }
}
