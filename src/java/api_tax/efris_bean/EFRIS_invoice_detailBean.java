/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris_bean;

import api_tax.efris.EFRIS_invoice_detail;
import api_tax.efris.innerclasses.T106;
import connections.DBConnection;
import entities.CompanySetting;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class EFRIS_invoice_detailBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(EFRIS_invoice_detailBean.class.getName());

    public int insertEFRIS_invoice_detail(EFRIS_invoice_detail aEFRIS_invoice_detail) {
        int saved = 0;
        String sql = "INSERT INTO efris_invoice_detail"
                + "(id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount,"
                + "taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo,"
                + "uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount
            ps.setString(1, aEFRIS_invoice_detail.getId());
            ps.setString(2, aEFRIS_invoice_detail.getInvoiceNo());
            ps.setString(3, aEFRIS_invoice_detail.getOriInvoiceId());
            ps.setString(4, aEFRIS_invoice_detail.getOriInvoiceNo());
            ps.setString(5, aEFRIS_invoice_detail.getIssuedDate());
            ps.setString(6, aEFRIS_invoice_detail.getBuyerTin());
            ps.setString(7, aEFRIS_invoice_detail.getBuyerLegalName());
            ps.setString(8, aEFRIS_invoice_detail.getBuyerNinBrn());
            ps.setString(9, aEFRIS_invoice_detail.getCurrency());
            ps.setString(10, aEFRIS_invoice_detail.getGrossAmount());
            //taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo
            ps.setString(11, aEFRIS_invoice_detail.getTaxAmount());
            ps.setString(12, aEFRIS_invoice_detail.getDataSource());
            ps.setString(13, aEFRIS_invoice_detail.getIsInvalid());
            ps.setString(14, aEFRIS_invoice_detail.getIsRefund());
            ps.setString(15, aEFRIS_invoice_detail.getInvoiceType());
            ps.setString(16, aEFRIS_invoice_detail.getInvoiceKind());
            ps.setString(17, aEFRIS_invoice_detail.getInvoiceIndustryCode());
            ps.setString(18, aEFRIS_invoice_detail.getBranchName());
            ps.setString(19, aEFRIS_invoice_detail.getDeviceNo());
            //uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date
            ps.setString(20, aEFRIS_invoice_detail.getUploadingTime());
            ps.setString(21, aEFRIS_invoice_detail.getReferenceNo());
            ps.setString(22, aEFRIS_invoice_detail.getOperator());
            ps.setString(23, aEFRIS_invoice_detail.getUserName());
            ps.setInt(24, 0);
            ps.setTimestamp(25, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(26, null);
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertEFRIS_invoice_detail(T106 aT106) {
        int saved = 0;
        String sql = "INSERT INTO efris_invoice_detail"
                + "(id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount,"
                + "taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo,"
                + "uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //id, invoiceNo, oriInvoiceId, oriInvoiceNo, issuedDate, buyerTin, buyerLegalName, buyerNinBrn, currency, grossAmount
            ps.setString(1, aT106.getId());
            ps.setString(2, aT106.getInvoiceNo());
            ps.setString(3, aT106.getOriInvoiceId());
            ps.setString(4, aT106.getOriInvoiceNo());
            //ps.setString(3, "");
            //ps.setString(4, "");
            ps.setString(5, aT106.getIssuedDate());
            //ps.setTimestamp(5, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(6, aT106.getBuyerTin());
            ps.setString(7, aT106.getBuyerLegalName());
            ps.setString(8, aT106.getBuyerNinBrn());
            ps.setString(9, aT106.getCurrency());
            ps.setString(10, aT106.getGrossAmount());
            //taxAmount, dataSource, isInvalid, isRefund, invoiceType, invoiceKind, invoiceIndustryCode, branchName, deviceNo
            ps.setString(11, aT106.getTaxAmount());
            ps.setString(12, aT106.getDataSource());
            ps.setString(13, aT106.getIsInvalid());
            ps.setString(14, aT106.getIsRefund());
            ps.setString(15, aT106.getInvoiceType());
            ps.setString(16, aT106.getInvoiceKind());
            ps.setString(17, aT106.getInvoiceIndustryCode());
            ps.setString(18, aT106.getBranchName());
            ps.setString(19, aT106.getDeviceNo());
            //uploadingTime, referenceNo, operator, userName, process_flag, add_date, process_date
            ps.setString(20, aT106.getUploadingTime());
            ps.setString(21, aT106.getReferenceNo());
            ps.setString(22, aT106.getOperator());
            ps.setString(23, aT106.getUserName());
            ps.setInt(24, 0);
            ps.setTimestamp(25, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(26, null);
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

}
