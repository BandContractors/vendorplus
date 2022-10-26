/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_goods_commodity;
import api_tax.efris.innerclasses.GoodsCommodity;
import beans.ItemBean;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Item_unspsc;
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
public class EFRIS_goods_commodityBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(EFRIS_goods_commodityBean.class.getName());

    public void setEFRIS_goods_commodityFromResultset(EFRIS_goods_commodity aEFRIS_goods_commodity, ResultSet aResultSet) {
        try {
            try {
                aEFRIS_goods_commodity.setEFRIS_goods_commodity_id(aResultSet.getLong("efris_goods_commodity_id"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setEFRIS_goods_commodity_id(0);
            }
            try {
                aEFRIS_goods_commodity.setCommodityCategoryCode(aResultSet.getString("commodityCategoryCode"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setCommodityCategoryCode("");
            }
            try {
                aEFRIS_goods_commodity.setParentCode(aResultSet.getString("parentCode"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setParentCode("");
            }
            try {
                aEFRIS_goods_commodity.setCommodityCategoryName(aResultSet.getString("commodityCategoryName"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setCommodityCategoryName("");
            }
            try {
                aEFRIS_goods_commodity.setCommodityCategoryLevel(aResultSet.getString("commodityCategoryLevel"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setCommodityCategoryLevel("");
            }
            try {
                aEFRIS_goods_commodity.setRate(aResultSet.getString("rate"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setRate("");
            }
            try {
                aEFRIS_goods_commodity.setIsLeafNode(aResultSet.getString("isLeafNode"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setIsLeafNode("");
            }
            try {
                aEFRIS_goods_commodity.setServiceMark(aResultSet.getString("serviceMark"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setServiceMark("");
            }
            try {
                aEFRIS_goods_commodity.setIsZeroRate(aResultSet.getString("isZeroRate"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setIsZeroRate("");
            }
            try {
                aEFRIS_goods_commodity.setZeroRateStartDate(aResultSet.getString("zeroRateStartDate"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setZeroRateStartDate("");
            }
            try {
                aEFRIS_goods_commodity.setZeroRateEndDate(aResultSet.getString("zeroRateEndDate"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setZeroRateEndDate("");
            }
            try {
                aEFRIS_goods_commodity.setIsExempt(aResultSet.getString("isExempt"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setIsExempt("");
            }
            try {
                aEFRIS_goods_commodity.setExemptRateStartDate(aResultSet.getString("exemptRateStartDate"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setExemptRateStartDate("");
            }
            try {
                aEFRIS_goods_commodity.setExemptRateEndDate(aResultSet.getString("exemptRateEndDate"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setExemptRateEndDate("");
            }
            try {
                aEFRIS_goods_commodity.setEnableStatusCode(aResultSet.getString("enableStatusCode"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setEnableStatusCode("");
            }
            try {
                aEFRIS_goods_commodity.setExclusion(aResultSet.getString("exclusion"));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setExclusion("");
            }
            try {
                aEFRIS_goods_commodity.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aEFRIS_goods_commodity.setAdd_date(null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setItem_unspscFromResultset(Item_unspsc aItem_unspsc, ResultSet aResultSet) {
        try {
            try {
                aItem_unspsc.setSegment_code(aResultSet.getString("segment_code"));
            } catch (Exception e) {
                aItem_unspsc.setSegment_code("");
            }
            try {
                aItem_unspsc.setSegment_name(aResultSet.getString("segment_name"));
            } catch (Exception e) {
                aItem_unspsc.setSegment_name("");
            }
            try {
                aItem_unspsc.setFamily_code(aResultSet.getString("family_code"));
            } catch (Exception e) {
                aItem_unspsc.setFamily_code("");
            }
            try {
                aItem_unspsc.setFamily_name(aResultSet.getString("family_name"));
            } catch (Exception e) {
                aItem_unspsc.setFamily_name("");
            }
            try {
                aItem_unspsc.setClass_code(aResultSet.getString("class_code"));
            } catch (Exception e) {
                aItem_unspsc.setClass_code("");
            }
            try {
                aItem_unspsc.setClass_name(aResultSet.getString("class_name"));
            } catch (Exception e) {
                aItem_unspsc.setClass_name("");
            }
            try {
                aItem_unspsc.setCommodity_code(aResultSet.getString("commodity_code"));
            } catch (Exception e) {
                aItem_unspsc.setCommodity_code("");
            }
            try {
                aItem_unspsc.setCommodity_name(aResultSet.getString("commodity_name"));
            } catch (Exception e) {
                aItem_unspsc.setCommodity_name("");
            }
            try {
                aItem_unspsc.setVat_rate(aResultSet.getString("vat_rate"));
            } catch (Exception e) {
                aItem_unspsc.setVat_rate("");
            }
            try {
                if (aResultSet.getString("service_mark").equals("101")) {
                    aItem_unspsc.setService_mark("Y");
                } else {
                    aItem_unspsc.setService_mark("N");
                }
            } catch (Exception e) {
                aItem_unspsc.setService_mark("");
            }
            try {
                if (aResultSet.getString("zero_rate").equals("101")) {
                    aItem_unspsc.setZero_rate("Y");
                } else {
                    aItem_unspsc.setZero_rate("N");
                }
            } catch (Exception e) {
                aItem_unspsc.setZero_rate("");
            }
            try {
                if (aResultSet.getString("exempt_rate").equals("101")) {
                    aItem_unspsc.setExempt_rate("Y");
                } else {
                    aItem_unspsc.setExempt_rate("N");
                }
            } catch (Exception e) {
                aItem_unspsc.setExempt_rate("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int saveEFRIS_goods_commodity(List<GoodsCommodity> aGoodsCommoditys) {
        int saved = 0;
        try {
            int goodsSaved = 0;
            //save goods commodity
            for (int i = 0, size = aGoodsCommoditys.size(); i < size; i++) {
                //check if EFRIS_goods_commodity is already in the db
                EFRIS_goods_commodity aEFRIS_goods_commodity = this.getEFRIS_invoice_detailByCommodityCategoryCode(aGoodsCommoditys.get(i).getCommodityCategoryCode());
                if (aEFRIS_goods_commodity == null) {
                    goodsSaved = goodsSaved + this.insertEFRIS_goods_commodity(aGoodsCommoditys.get(i));
                } else {
                    //EFRIS_goods_commodity already exists
                    goodsSaved = goodsSaved + 1;
                }
            }

            if (goodsSaved == aGoodsCommoditys.size()) {
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertEFRIS_goods_commodity(GoodsCommodity aGoodsCommodity) {
        int saved = 0;
        String sql = "INSERT INTO efris_goods_commodity"
                + "(commodityCategoryCode, parentCode, commodityCategoryName, commodityCategoryLevel, rate, isLeafNode, serviceMark, isZeroRate,"
                + "zeroRateStartDate, zeroRateEndDate,isExempt,exemptRateStartDate, exemptRateEndDate, enableStatusCode, exclusion, add_date)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //commodityCategoryCode, parentCode, commodityCategoryName, commodityCategoryLevel, rate, isLeafNode, serviceMark, isZeroRate
            if (aGoodsCommodity.getCommodityCategoryCode() != null) {
                ps.setString(1, aGoodsCommodity.getCommodityCategoryCode());
            } else {
                ps.setString(1, "");
            }
            if (aGoodsCommodity.getParentCode() != null) {
                ps.setString(2, aGoodsCommodity.getParentCode());
            } else {
                ps.setString(2, "");
            }
            if (aGoodsCommodity.getCommodityCategoryName() != null) {
                ps.setString(3, aGoodsCommodity.getCommodityCategoryName());
            } else {
                ps.setString(3, "");
            }
            if (aGoodsCommodity.getCommodityCategoryLevel() != null) {
                ps.setString(4, aGoodsCommodity.getCommodityCategoryLevel());
            } else {
                ps.setString(4, "");
            }
            if (aGoodsCommodity.getRate() != null) {
                ps.setString(5, aGoodsCommodity.getRate());
            } else {
                ps.setString(5, "");
            }
            if (aGoodsCommodity.getIsLeafNode() != null) {
                ps.setString(6, aGoodsCommodity.getIsLeafNode());
            } else {
                ps.setString(6, "");
            }
            if (aGoodsCommodity.getServiceMark() != null) {
                ps.setString(7, aGoodsCommodity.getServiceMark());
            } else {
                ps.setString(7, "");
            }
            if (aGoodsCommodity.getIsZeroRate() != null) {
                ps.setString(8, aGoodsCommodity.getIsZeroRate());
            } else {
                ps.setString(8, "");
            }
            //zeroRateStartDate, zeroRateEndDate,isExempt,exemptRateStartDate, exemptRateEndDate, enableStatusCode, exclusion, add_date
            if (aGoodsCommodity.getZeroRateStartDate() != null) {
                ps.setString(9, aGoodsCommodity.getZeroRateStartDate());
            } else {
                ps.setString(9, "");
            }
            if (aGoodsCommodity.getZeroRateEndDate() != null) {
                ps.setString(10, aGoodsCommodity.getZeroRateEndDate());
            } else {
                ps.setString(10, "");
            }
            if (aGoodsCommodity.getIsExempt() != null) {
                ps.setString(11, aGoodsCommodity.getIsExempt());
            } else {
                ps.setString(11, "");
            }
            if (aGoodsCommodity.getExemptRateStartDate() != null) {
                ps.setString(12, aGoodsCommodity.getExemptRateStartDate());
            } else {
                ps.setString(12, "");
            }
            if (aGoodsCommodity.getExemptRateEndDate() != null) {
                ps.setString(13, aGoodsCommodity.getExemptRateEndDate());
            } else {
                ps.setString(13, "");
            }
            if (aGoodsCommodity.getEnableStatusCode() != null) {
                ps.setString(14, aGoodsCommodity.getEnableStatusCode());
            } else {
                ps.setString(14, "");
            }
            if (aGoodsCommodity.getExclusion() != null) {
                ps.setString(15, aGoodsCommodity.getExclusion());
            } else {
                ps.setString(15, "");
            }
            ps.setTimestamp(16, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertEFRIS_goods_commodity(EFRIS_goods_commodity aEFRIS_goods_commodity) {
        int saved = 0;
        String sql = "INSERT INTO efris_goods_commodity"
                + "(commodityCategoryCode, parentCode, commodityCategoryName, commodityCategoryLevel, rate, isLeafNode, serviceMark, isZeroRate,"
                + "zeroRateStartDate, zeroRateEndDate,isExempt,exemptRateStartDate, exemptRateEndDate, enableStatusCode, exclusion, add_date)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //commodityCategoryCode, parentCode, commodityCategoryName, commodityCategoryLevel, rate, isLeafNode, serviceMark, isZeroRate
            ps.setString(1, aEFRIS_goods_commodity.getCommodityCategoryCode());
            ps.setString(2, aEFRIS_goods_commodity.getParentCode());
            ps.setString(3, aEFRIS_goods_commodity.getCommodityCategoryName());
            ps.setString(4, aEFRIS_goods_commodity.getCommodityCategoryLevel());
            ps.setString(5, aEFRIS_goods_commodity.getRate());
            ps.setString(6, aEFRIS_goods_commodity.getIsLeafNode());
            ps.setString(7, aEFRIS_goods_commodity.getServiceMark());
            ps.setString(8, aEFRIS_goods_commodity.getIsZeroRate());
            //zeroRateStartDate, zeroRateEndDate,isExempt,exemptRateStartDate, exemptRateEndDate, enableStatusCode, exclusion, add_date
            ps.setString(9, aEFRIS_goods_commodity.getZeroRateStartDate());
            ps.setString(10, aEFRIS_goods_commodity.getZeroRateEndDate());
            ps.setString(11, aEFRIS_goods_commodity.getIsExempt());
            ps.setString(12, aEFRIS_goods_commodity.getExemptRateStartDate());
            ps.setString(13, aEFRIS_goods_commodity.getExemptRateEndDate());
            ps.setString(14, aEFRIS_goods_commodity.getEnableStatusCode());
            ps.setString(15, aEFRIS_goods_commodity.getExclusion());
            ps.setTimestamp(16, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public EFRIS_goods_commodity getEFRIS_goods_commodityById(long aEFRIS_goods_commodity_id) {
        String sql = "SELECT * FROM efris_goods_commodity WHERE efris_goods_commodity_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aEFRIS_goods_commodity_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                EFRIS_goods_commodity obj = new EFRIS_goods_commodity();
                this.setEFRIS_goods_commodityFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<EFRIS_goods_commodity> getEFRIS_goods_commodity_All() {
        String sql = "SELECT * FROM efris_goods_commodity";
        ResultSet rs;
        List<EFRIS_goods_commodity> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                EFRIS_goods_commodity obj = new EFRIS_goods_commodity();
                this.setEFRIS_goods_commodityFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    public EFRIS_goods_commodity getEFRIS_invoice_detailByCommodityCategoryCode(String aCommodityCategoryCode) {
        String sql = "SELECT * FROM efris_goods_commodity where commodityCategoryCode=?";
        ResultSet rs;
        EFRIS_goods_commodity aEFRIS_goods_commodity = new EFRIS_goods_commodity();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCommodityCategoryCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.setEFRIS_goods_commodityFromResultset(aEFRIS_goods_commodity, rs);
            } else {
                aEFRIS_goods_commodity = null;
            }
        } catch (Exception e) {
            aEFRIS_goods_commodity = null;
            LOGGER.log(Level.ERROR, e);
        }
        return aEFRIS_goods_commodity;
    }

    public Date getGoodsCommodityLastSyncDate() {
        Date lastDate = null;
        try {
            List<EFRIS_goods_commodity> aEFRIS_goods_commodity = this.getEFRIS_goods_commodity_All();
            //check if the table is empty
            if (aEFRIS_goods_commodity.size() > 0) {
                //get last add date
                int size = aEFRIS_goods_commodity.size();
                lastDate = aEFRIS_goods_commodity.get(size - 1).getAdd_date();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            lastDate = null;
        }
        return lastDate;
    }

    public void mergeGoodCommodity_Item_UNSPSC() {
        String sql = "select lv4.commodityCategoryCode as commodity_code,lv4.rate as vat_rate,lv4.serviceMark as service_mark,lv4.isZeroRate as zero_rate,lv4.isExempt as exempt_rate,lv4.commodityCategoryName as commodity_name,"
                + "lv3.commodityCategoryName as class_name, lv3.commodityCategoryCode as class_code,"
                + "lv2.commodityCategoryName as family_name, lv2.commodityCategoryCode as family_code,"
                + "lv1.commodityCategoryName as segment, lv1.commodityCategoryCode as segment_code from efris_goods_commodity lv4 "
                + "inner join efris_goods_commodity lv3 on lv3.commodityCategoryLevel=3 and lv4.parentCode=lv3.commodityCategoryCode "
                + "inner join efris_goods_commodity lv2 on lv2.commodityCategoryLevel=2 and lv3.parentCode=lv2.commodityCategoryCode "
                + "inner join efris_goods_commodity lv1 on lv1.commodityCategoryLevel=1 and lv2.parentCode=lv1.commodityCategoryCode "
                + "where lv4.commodityCategoryLevel=4;";
        ResultSet rs;

        List<Item_unspsc> Item_unspscList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Item_unspsc obj = new Item_unspsc();
                this.setItem_unspscFromResultset(obj, rs);
                Item_unspscList.add(obj);
            }
            //if item_unspsc is empty
            if (Item_unspscList.size() > 0) {
                int isItemUNSPSCDeleted = new ItemBean().deleteItem_unspsc_All();
                if (isItemUNSPSCDeleted == 1){
                    new ItemBean().saveItem_unspsc(Item_unspscList);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
