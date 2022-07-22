/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_good_detail;
import api_tax.efris.innerclasses.GoodsDetails;
import beans.ItemBean;
import beans.Item_tax_mapBean;
import beans.TransItemBean;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Item;
import entities.Item_tax_map;
import entities.TransItem;
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
public class EFRIS_good_detailBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(EFRIS_good_detailBean.class.getName());

    public String validateSaleInvoiceItems(List<EFRIS_good_detail> aEFRIS_good_detailList) {
        String status = "";
        try {
            //List<Bi_stg_sale_invoice_item> invItems = invoiceItems;
            for (int i = 0, size = aEFRIS_good_detailList.size(); i < size; i++) {
                EFRIS_good_detail aEFRIS_good_detail = aEFRIS_good_detailList.get(i);
                //get Item_tax_mapBean
                Item_tax_map aItem_tax_map = new Item_tax_mapBean().getItem_tax_mapByIdTax(aEFRIS_good_detail.getItemCode());

                int itemQty = Integer.parseInt(aEFRIS_good_detail.getQty());
                if (aItem_tax_map == null) {
                    status = "Item code at position " + (i + 1) + " not in item_tax_map";
                    break;
                } else if (new ItemBean().findItem(aItem_tax_map.getItem_id()) == null) {
                    status = "Item at position " + (i + 1) + "does not exist";
                    break;
                } else if (new ItemBean().getItemCurrentStockStatus(aItem_tax_map.getItem_id()).getQty_total() < itemQty) {
                    status = "Item at position " + (i + 1) + "Has less Stock";
                    break;
                } else {
                    status = "success";
                }

                //update validation status
                if (status.equals("success")) {
                    this.updateEFRIS_good_detailValidation(aEFRIS_good_detail.getEFRIS_good_detail_id(), 1, status);
                } else {
                    this.updateEFRIS_good_detailValidation(aEFRIS_good_detail.getEFRIS_good_detail_id(), 2, status);
                }
            }
        } catch (Exception e) {
            status = e.getMessage();
            LOGGER.log(Level.ERROR, e);
        }
        return status;
    }

    public void setEFRIS_good_detailFromResultset(EFRIS_good_detail aEFRIS_good_detail, ResultSet aResultSet) {
        try {
            try {
                aEFRIS_good_detail.setEFRIS_good_detail_id(aResultSet.getLong("efris_good_detail_id"));
            } catch (Exception e) {
                aEFRIS_good_detail.setEFRIS_good_detail_id(0);
            }
            try {
                aEFRIS_good_detail.setInvoiceNo(aResultSet.getString("invoiceNo"));
            } catch (Exception e) {
                aEFRIS_good_detail.setInvoiceNo("");
            }
            try {
                aEFRIS_good_detail.setReferenceNo(aResultSet.getString("referenceNo"));
            } catch (Exception e) {
                aEFRIS_good_detail.setReferenceNo("");
            }
            try {
                aEFRIS_good_detail.setItem(aResultSet.getString("item"));
            } catch (Exception e) {
                aEFRIS_good_detail.setItem("");
            }
            try {
                aEFRIS_good_detail.setItemCode(aResultSet.getString("itemCode"));
            } catch (Exception e) {
                aEFRIS_good_detail.setItemCode("");
            }
            try {
                aEFRIS_good_detail.setQty(aResultSet.getString("qty"));
            } catch (Exception e) {
                aEFRIS_good_detail.setQty("");
            }
            try {
                aEFRIS_good_detail.setUnitOfMeasure(aResultSet.getString("unitOfMeasure"));
            } catch (Exception e) {
                aEFRIS_good_detail.setUnitOfMeasure("");
            }
            try {
                aEFRIS_good_detail.setUnitPrice(aResultSet.getString("unitPrice"));
            } catch (Exception e) {
                aEFRIS_good_detail.setUnitPrice("");
            }
            try {
                aEFRIS_good_detail.setTotal(aResultSet.getString("total"));
            } catch (Exception e) {
                aEFRIS_good_detail.setTotal("");
            }
            try {
                aEFRIS_good_detail.setTaxRate(aResultSet.getString("taxRate"));
            } catch (Exception e) {
                aEFRIS_good_detail.setTaxRate("");
            }
            try {
                aEFRIS_good_detail.setTax(aResultSet.getString("tax"));
            } catch (Exception e) {
                aEFRIS_good_detail.setTax("");
            }
            try {
                aEFRIS_good_detail.setDiscountTotal(aResultSet.getString("discountTotal"));
            } catch (Exception e) {
                aEFRIS_good_detail.setDiscountTotal("");
            }
            try {
                aEFRIS_good_detail.setDiscountTaxRate(aResultSet.getString("discountTaxRate"));
            } catch (Exception e) {
                aEFRIS_good_detail.setDiscountTaxRate("");
            }
            try {
                aEFRIS_good_detail.setOrderNumber(aResultSet.getString("orderNumber"));
            } catch (Exception e) {
                aEFRIS_good_detail.setOrderNumber("");
            }
            try {
                aEFRIS_good_detail.setDiscountFlag(aResultSet.getString("discountFlag"));
            } catch (Exception e) {
                aEFRIS_good_detail.setDiscountFlag("");
            }
            try {
                aEFRIS_good_detail.setDeemedFlag(aResultSet.getString("deemedFlag"));
            } catch (Exception e) {
                aEFRIS_good_detail.setDeemedFlag("");
            }
            try {
                aEFRIS_good_detail.setExciseFlag(aResultSet.getString("exciseFlag"));
            } catch (Exception e) {
                aEFRIS_good_detail.setExciseFlag("");
            }
            try {
                aEFRIS_good_detail.setCategoryId(aResultSet.getString("categoryId"));
            } catch (Exception e) {
                aEFRIS_good_detail.setCategoryId("");
            }
            try {
                aEFRIS_good_detail.setCategoryName(aResultSet.getString("categoryName"));
            } catch (Exception e) {
                aEFRIS_good_detail.setCategoryName("");
            }
            try {
                aEFRIS_good_detail.setGoodsCategoryId(aResultSet.getString("goodsCategoryId"));
            } catch (Exception e) {
                aEFRIS_good_detail.setGoodsCategoryId("");
            }
            try {
                aEFRIS_good_detail.setGoodsCategoryName(aResultSet.getString("goodsCategoryName"));
            } catch (Exception e) {
                aEFRIS_good_detail.setGoodsCategoryName("");
            }
            try {
                aEFRIS_good_detail.setExciseRate(aResultSet.getString("exciseRate"));
            } catch (Exception e) {
                aEFRIS_good_detail.setExciseRate("");
            }
            try {
                aEFRIS_good_detail.setExciseRule(aResultSet.getString("exciseRule"));
            } catch (Exception e) {
                aEFRIS_good_detail.setExciseRule("");
            }
            try {
                aEFRIS_good_detail.setExciseTax(aResultSet.getString("exciseTax"));
            } catch (Exception e) {
                aEFRIS_good_detail.setExciseTax("");
            }
            try {
                aEFRIS_good_detail.setPack(aResultSet.getString("pack"));
            } catch (Exception e) {
                aEFRIS_good_detail.setPack("");
            }
            try {
                aEFRIS_good_detail.setStick(aResultSet.getString("stick"));
            } catch (Exception e) {
                aEFRIS_good_detail.setStick("");
            }
            try {
                aEFRIS_good_detail.setExciseUnit(aResultSet.getString("exciseUnit"));
            } catch (Exception e) {
                aEFRIS_good_detail.setExciseUnit("");
            }
            try {
                aEFRIS_good_detail.setExciseCurrency(aResultSet.getString("exciseCurrency"));
            } catch (Exception e) {
                aEFRIS_good_detail.setExciseCurrency("");
            }
            try {
                aEFRIS_good_detail.setExciseRateName(aResultSet.getString("exciseRateName"));
            } catch (Exception e) {
                aEFRIS_good_detail.setExciseRateName("");
            }
            try {
                aEFRIS_good_detail.setProcess_flag(aResultSet.getInt("process_flag"));
            } catch (Exception e) {
                aEFRIS_good_detail.setProcess_flag(0);
            }
            try {
                aEFRIS_good_detail.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aEFRIS_good_detail.setAdd_date(null);
            }
            try {
                aEFRIS_good_detail.setProcess_date(new Date(aResultSet.getTimestamp("process_date").getTime()));
            } catch (Exception e) {
                aEFRIS_good_detail.setProcess_date(null);
            }
            try {
                aEFRIS_good_detail.setProcess_desc(aResultSet.getString("process_desc"));
            } catch (Exception e) {
                aEFRIS_good_detail.setProcess_desc("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransItemFromEFRIS_good_detail(List<TransItem> aTransItem, List<EFRIS_good_detail> aEFRIS_good_detail) {
        try {
            for (int i = 0; i < aEFRIS_good_detail.size(); i++) {
                TransItem item = new TransItem();
                EFRIS_good_detail goodDetail = aEFRIS_good_detail.get(i);

                this.setTransItemFromEFRIS_good_detail(item, goodDetail);

                aTransItem.add(item);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransItemFromEFRIS_good_detail(TransItem aTransItem, EFRIS_good_detail aEFRIS_good_detail) {
        try {
            //get Item_tax_mapBean
            Item_tax_map aItem_tax_map = new Item_tax_mapBean().getItem_tax_mapByIdTax(aEFRIS_good_detail.getItemCode());
            //get item details in SM
            //Item aItem = new ItemBean().getItemByDesc(aEFRIS_good_detail.getItem());
            Item aItem = new ItemBean().findItem(aItem_tax_map.getItem_id());
            try {
                aTransItem.setItemId(aItem.getItemId());
            } catch (Exception e) {
                aTransItem.setItemId(0);
            }
            try {
                aTransItem.setItemQty(Integer.parseInt(aEFRIS_good_detail.getQty()));
            } catch (Exception e) {
                aTransItem.setItemQty(0);
            }
            try {
                aTransItem.setUnitPrice(Double.parseDouble(aEFRIS_good_detail.getUnitPrice()));
            } catch (Exception e) {
                aTransItem.setUnitPrice(0);
            }
            try {
                aTransItem.setAmount(Double.parseDouble(aEFRIS_good_detail.getTotal()));
            } catch (Exception e) {
                aTransItem.setAmount(0);
            }
            try {
                if (aEFRIS_good_detail.getTaxRate().equals("0.18") && aEFRIS_good_detail.getDeemedFlag().equals("1")) {
                    aTransItem.setUnitVat(0);
                } else {
                    aTransItem.setUnitVat(Double.parseDouble(aEFRIS_good_detail.getTax()) / Integer.parseInt(aEFRIS_good_detail.getQty()));
                }
            } catch (Exception e) {
                aTransItem.setUnitVat(0);
            }
            try {
                if (aEFRIS_good_detail.getTaxRate().equals("0.18") && aEFRIS_good_detail.getDeemedFlag().equals("1")) {
                    aTransItem.setVatRated("DEEMED");
                } else if (aEFRIS_good_detail.getTaxRate().equals("0.18")) {
                    aTransItem.setVatRated("STANDARD");
                } else if (aEFRIS_good_detail.getTaxRate().equals("0")) {
                    aTransItem.setVatRated("ZERO");
                } else if (aEFRIS_good_detail.getTaxRate().equals("-")) {
                    aTransItem.setVatRated("EXEMPT");
                } else {
                    aTransItem.setVatRated("");
                }
            } catch (Exception e) {
                aTransItem.setVatRated("");
            }
            try {
                if (aEFRIS_good_detail.getTaxRate().equals("0.18") && aEFRIS_good_detail.getDeemedFlag().equals("1")) {
                    aTransItem.setVatPerc(0);
                } else {
                    aTransItem.setVatPerc(Double.parseDouble(aEFRIS_good_detail.getTaxRate()) * 100);
                }
            } catch (Exception e) {
                aTransItem.setVatPerc(0);
            }
            try {
                //aTransItem.setItemCode(aEFRIS_good_detail.getItemCode());
                aTransItem.setItemCode(aItem.getItemCode());
            } catch (Exception e) {
                aTransItem.setItemCode("");
            }
            try {
                aTransItem.setUnitPriceIncVat(Double.parseDouble(aEFRIS_good_detail.getUnitPrice()));
            } catch (Exception e) {
                aTransItem.setUnitPriceIncVat(0);
            }
            try {
                if (aEFRIS_good_detail.getTaxRate().equals("0.18") && aEFRIS_good_detail.getDeemedFlag().equals("1")) {
                    aTransItem.setUnitPriceExcVat(Double.parseDouble(aEFRIS_good_detail.getUnitPrice()) - (0 / Integer.parseInt(aEFRIS_good_detail.getQty())));
                } else {
                    aTransItem.setUnitPriceExcVat(Double.parseDouble(aEFRIS_good_detail.getUnitPrice()) - ((Double.parseDouble(aEFRIS_good_detail.getTax()) / Integer.parseInt(aEFRIS_good_detail.getQty()))));
                }
            } catch (Exception e) {
                aTransItem.setUnitPriceExcVat(0);
            }
            try {
                if (aEFRIS_good_detail.getTaxRate().equals("0.18") && aEFRIS_good_detail.getDeemedFlag().equals("1")) {
                    aTransItem.setAmountIncVat(Double.parseDouble(aEFRIS_good_detail.getTotal()) - Double.parseDouble(aEFRIS_good_detail.getTax()));
                } else {
                    aTransItem.setAmountIncVat(Double.parseDouble(aEFRIS_good_detail.getTotal()));
                }
            } catch (Exception e) {
                aTransItem.setAmountIncVat(0);
            }
            try {
                aTransItem.setAmountExcVat(Double.parseDouble(aEFRIS_good_detail.getTotal()) - Double.parseDouble(aEFRIS_good_detail.getTax()));
            } catch (Exception e) {
                aTransItem.setAmountExcVat(0);
            }
            try {
                aTransItem.setDescription(aEFRIS_good_detail.getItem());
                //aTransItem.setDescription(aItem.getDescription());
            } catch (Exception e) {
                aTransItem.setDescription("");
            }
            try {
                aTransItem.setUnitSymbol(aEFRIS_good_detail.getUnitOfMeasure());
            } catch (Exception e) {
                aTransItem.setUnitSymbol("");
            }
            try {
                //aTransItem.setUnitCostPrice(aItem.getUnitCostPrice());
                aTransItem.setUnitCostPrice(new TransItemBean().getItemLatestUnitCostPrice(aItem.getItemId(), "", "", ""));
            } catch (Exception e) {
                aTransItem.setUnitCostPrice(0);
            }
            try {
                aTransItem.setUnitProfitMargin(aTransItem.getUnitPriceExcVat() - aTransItem.getUnitCostPrice());
            } catch (Exception e) {
                aTransItem.setUnitProfitMargin(0);
            }
            try {
                aTransItem.setItem_currency_code(aItem.getCurrencyCode());
            } catch (Exception e) {
                aTransItem.setItem_currency_code("");
            }
            try {
                aTransItem.setSpecific_size(1);
            } catch (Exception e) {
                aTransItem.setSpecific_size(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int saveEFRIS_good_detail(List<GoodsDetails> aGoodsDetails, String taxInvoiceNo, String referenceNo) {
        int saved = 0;
        try {
            int goodsSaved = 0;
            //save goods/items of the invoice
            for (int i = 0, size = aGoodsDetails.size(); i < size; i++) {
                goodsSaved = goodsSaved + this.insertEFRIS_good_detail(aGoodsDetails.get(i), taxInvoiceNo, referenceNo);
            }

            if (goodsSaved == aGoodsDetails.size()) {
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertEFRIS_good_detail(EFRIS_good_detail aEFRIS_good_detail) {
        int saved = 0;
        String sql = "INSERT INTO efris_good_detail"
                + "(invoiceNo, referenceNo, item, itemCode, qty, unitOfMeasure, unitPrice, total, taxRate, tax, discountTotal, discountTaxRate,"
                + "orderNumber,discountFlag, deemedFlag, exciseFlag, categoryId, categoryName, goodsCategoryId, goodsCategoryName, exciseRate,"
                + "exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date, process_desc)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //invoiceNo, referenceNo, item, itemCode, qty, unitOfMeasure, unitPrice, total, taxRate, tax, discountTotal, discountTaxRate
            ps.setString(1, aEFRIS_good_detail.getInvoiceNo());
            ps.setString(2, aEFRIS_good_detail.getReferenceNo());
            ps.setString(3, aEFRIS_good_detail.getItem());
            ps.setString(4, aEFRIS_good_detail.getItemCode());
            ps.setString(5, aEFRIS_good_detail.getQty());
            ps.setString(6, aEFRIS_good_detail.getUnitOfMeasure());
            ps.setString(7, aEFRIS_good_detail.getUnitPrice());
            ps.setString(8, aEFRIS_good_detail.getTotal());
            ps.setString(9, aEFRIS_good_detail.getTaxRate());
            ps.setString(10, aEFRIS_good_detail.getTax());
            ps.setString(11, aEFRIS_good_detail.getDiscountTotal());
            ps.setString(12, aEFRIS_good_detail.getDiscountTaxRate());
            //orderNumber,discountFlag, deemedFlag, exciseFlag, categoryId, categoryName, goodsCategoryId, goodsCategoryName, exciseRate
            ps.setString(13, aEFRIS_good_detail.getOrderNumber());
            ps.setString(14, aEFRIS_good_detail.getDiscountFlag());
            ps.setString(15, aEFRIS_good_detail.getDeemedFlag());
            ps.setString(16, aEFRIS_good_detail.getExciseFlag());
            ps.setString(17, aEFRIS_good_detail.getCategoryId());
            ps.setString(18, aEFRIS_good_detail.getCategoryName());
            ps.setString(19, aEFRIS_good_detail.getGoodsCategoryId());
            ps.setString(20, aEFRIS_good_detail.getGoodsCategoryName());
            ps.setString(21, aEFRIS_good_detail.getExciseRate());
            //exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date, process_desc
            ps.setString(22, aEFRIS_good_detail.getExciseRule());
            ps.setString(23, aEFRIS_good_detail.getExciseTax());
            ps.setString(24, aEFRIS_good_detail.getPack());
            ps.setString(25, aEFRIS_good_detail.getStick());
            ps.setString(26, aEFRIS_good_detail.getExciseUnit());
            ps.setString(27, aEFRIS_good_detail.getExciseCurrency());
            ps.setString(28, aEFRIS_good_detail.getExciseRateName());
            ps.setInt(29, 0);
            ps.setTimestamp(30, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(31, null);
            ps.setString(32, "");
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertEFRIS_good_detail(GoodsDetails aGoodsDetails, String taxInvoiceNo, String referenceNo) {
        int saved = 0;
        String sql = "INSERT INTO efris_good_detail"
                + "(invoiceNo, referenceNo, item, itemCode, qty, unitOfMeasure, unitPrice, total, taxRate, tax, discountTotal, discountTaxRate,"
                + "orderNumber,discountFlag, deemedFlag, exciseFlag, categoryId, categoryName, goodsCategoryId, goodsCategoryName, exciseRate,"
                + "exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date, process_desc)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //invoiceNo, referenceNo, item, itemCode, qty, unitOfMeasure, unitPrice, total, taxRate, tax, discountTotal, discountTaxRate
            ps.setString(1, taxInvoiceNo);
            ps.setString(2, referenceNo);
            ps.setString(3, aGoodsDetails.getItem());
            ps.setString(4, aGoodsDetails.getItemCode());
            ps.setString(5, aGoodsDetails.getQty());
            ps.setString(6, aGoodsDetails.getUnitOfMeasure());
            ps.setString(7, aGoodsDetails.getUnitPrice());
            ps.setString(8, aGoodsDetails.getTotal());
            ps.setString(9, aGoodsDetails.getTaxRate());
            ps.setString(10, aGoodsDetails.getTax());
            ps.setString(11, aGoodsDetails.getDiscountTotal());
            ps.setString(12, aGoodsDetails.getDiscountTaxRate());
            //orderNumber,discountFlag, deemedFlag, exciseFlag, categoryId, categoryName, goodsCategoryId, goodsCategoryName, exciseRate
            ps.setString(13, aGoodsDetails.getOrderNumber());
            ps.setString(14, aGoodsDetails.getDiscountFlag());
            ps.setString(15, aGoodsDetails.getDeemedFlag());
            ps.setString(16, aGoodsDetails.getExciseFlag());
            ps.setString(17, aGoodsDetails.getCategoryId());
            ps.setString(18, aGoodsDetails.getCategoryName());
            ps.setString(19, aGoodsDetails.getGoodsCategoryId());
            ps.setString(20, aGoodsDetails.getGoodsCategoryName());
            ps.setString(21, aGoodsDetails.getExciseRate());
            //exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date, process_desc
            ps.setString(22, aGoodsDetails.getExciseRule());
            ps.setString(23, aGoodsDetails.getExciseTax());
            ps.setString(24, aGoodsDetails.getPack());
            ps.setString(25, aGoodsDetails.getStick());
            ps.setString(26, aGoodsDetails.getExciseUnit());
            ps.setString(27, aGoodsDetails.getExciseCurrency());
            ps.setString(28, aGoodsDetails.getExciseRateName());
            ps.setInt(29, 0);
            ps.setTimestamp(30, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(31, null);
            ps.setString(32, "");
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public void updateEFRIS_good_detailValidation(long aEFRIS_good_detail_id, int status, String statusMsg) {
        //int saved = 0;
        String sql = "UPDATE efris_good_detail SET process_flag = ?, process_desc = ?, process_date = ? WHERE efris_good_detail_id = ?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, status);
            ps.setString(2, statusMsg);
            ps.setTimestamp(3, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setLong(4, aEFRIS_good_detail_id);
            ps.executeUpdate();
            //saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        //return saved;
    }

    public EFRIS_good_detail getEFRIS_good_detailById(long aEFRIS_good_detail_id) {
        String sql = "SELECT * FROM efris_good_detail WHERE efris_good_detail_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aEFRIS_good_detail_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                EFRIS_good_detail obj = new EFRIS_good_detail();
                this.setEFRIS_good_detailFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<EFRIS_good_detail> getEFRIS_invoice_detail_All() {
        String sql = "SELECT * FROM efris_good_detail";
        ResultSet rs;
        List<EFRIS_good_detail> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                EFRIS_good_detail obj = new EFRIS_good_detail();
                this.setEFRIS_good_detailFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    public List<EFRIS_good_detail> getEFRIS_invoice_detailByInvoiceNo(String invoiceNo) {
        String sql = "SELECT * FROM efris_good_detail where invoiceNo=?";
        ResultSet rs;
        List<EFRIS_good_detail> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, invoiceNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                EFRIS_good_detail obj = new EFRIS_good_detail();
                this.setEFRIS_good_detailFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }
}
