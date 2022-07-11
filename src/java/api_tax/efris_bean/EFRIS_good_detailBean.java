/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_good_detail;
import api_tax.efris.innerclasses.GoodsDetails;
import connections.DBConnection;
import entities.CompanySetting;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                + "exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            //exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date
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
                + "exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            //exciseRule, exciseTax, pack, stick, exciseUnit, exciseCurrency, exciseRateName, process_flag, add_date, process_date
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
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }
}
