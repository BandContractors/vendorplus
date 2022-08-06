/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author emuwonge
 */
public class EFRIS_invoice_detail implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long EFRIS_invoice_detail_id;
    private String id;
    private String invoiceNo;
    private String oriInvoiceId;
    private String oriInvoiceNo;
    private String issuedDate;
    private String buyerTin;
    private String buyerLegalName;
    private String buyerNinBrn;
    private String currency;
    private String grossAmount;
    private String taxAmount;
    private String dataSource;
    private String isInvalid;
    private String isRefund;
    private String invoiceType;
    private String invoiceKind;
    private String invoiceIndustryCode;
    private String branchName;
    private String deviceNo;
    private String uploadingTime;
    private String referenceNo;
    private String operator;
    private String userName;
    
    //Additionals
    private int process_flag;
    private Date add_date;
    private Date process_date;
    private String process_desc;    
    
    //qrCode, antifakeCode
    private String antifakeCode;
    private String qrCode;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the oriInvoiceId
     */
    public String getOriInvoiceId() {
        return oriInvoiceId;
    }

    /**
     * @param oriInvoiceId the oriInvoiceId to set
     */
    public void setOriInvoiceId(String oriInvoiceId) {
        this.oriInvoiceId = oriInvoiceId;
    }

    /**
     * @return the oriInvoiceNo
     */
    public String getOriInvoiceNo() {
        return oriInvoiceNo;
    }

    /**
     * @param oriInvoiceNo the oriInvoiceNo to set
     */
    public void setOriInvoiceNo(String oriInvoiceNo) {
        this.oriInvoiceNo = oriInvoiceNo;
    }

    /**
     * @return the issuedDate
     */
    public String getIssuedDate() {
        return issuedDate;
    }

    /**
     * @param issuedDate the issuedDate to set
     */
    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    /**
     * @return the buyerTin
     */
    public String getBuyerTin() {
        return buyerTin;
    }

    /**
     * @param buyerTin the buyerTin to set
     */
    public void setBuyerTin(String buyerTin) {
        this.buyerTin = buyerTin;
    }

    /**
     * @return the buyerLegalName
     */
    public String getBuyerLegalName() {
        return buyerLegalName;
    }

    /**
     * @param buyerLegalName the buyerLegalName to set
     */
    public void setBuyerLegalName(String buyerLegalName) {
        this.buyerLegalName = buyerLegalName;
    }

    /**
     * @return the buyerNinBrn
     */
    public String getBuyerNinBrn() {
        return buyerNinBrn;
    }

    /**
     * @param buyerNinBrn the buyerNinBrn to set
     */
    public void setBuyerNinBrn(String buyerNinBrn) {
        this.buyerNinBrn = buyerNinBrn;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the grossAmount
     */
    public String getGrossAmount() {
        return grossAmount;
    }

    /**
     * @param grossAmount the grossAmount to set
     */
    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    /**
     * @return the taxAmount
     */
    public String getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount the taxAmount to set
     */
    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return the dataSource
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return the isInvalid
     */
    public String getIsInvalid() {
        return isInvalid;
    }

    /**
     * @param isInvalid the isInvalid to set
     */
    public void setIsInvalid(String isInvalid) {
        this.isInvalid = isInvalid;
    }

    /**
     * @return the isRefund
     */
    public String getIsRefund() {
        return isRefund;
    }

    /**
     * @param isRefund the isRefund to set
     */
    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund;
    }

    /**
     * @return the invoiceType
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * @return the invoiceKind
     */
    public String getInvoiceKind() {
        return invoiceKind;
    }

    /**
     * @param invoiceKind the invoiceKind to set
     */
    public void setInvoiceKind(String invoiceKind) {
        this.invoiceKind = invoiceKind;
    }

    /**
     * @return the invoiceIndustryCode
     */
    public String getInvoiceIndustryCode() {
        return invoiceIndustryCode;
    }

    /**
     * @param invoiceIndustryCode the invoiceIndustryCode to set
     */
    public void setInvoiceIndustryCode(String invoiceIndustryCode) {
        this.invoiceIndustryCode = invoiceIndustryCode;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName the branchName to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return the deviceNo
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * @param deviceNo the deviceNo to set
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    /**
     * @return the uploadingTime
     */
    public String getUploadingTime() {
        return uploadingTime;
    }

    /**
     * @param uploadingTime the uploadingTime to set
     */
    public void setUploadingTime(String uploadingTime) {
        this.uploadingTime = uploadingTime;
    }

    /**
     * @return the referenceNo
     */
    public String getReferenceNo() {
        return referenceNo;
    }

    /**
     * @param referenceNo the referenceNo to set
     */
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the process_flag
     */
    public int getProcess_flag() {
        return process_flag;
    }

    /**
     * @param process_flag the process_flag to set
     */
    public void setProcess_flag(int process_flag) {
        this.process_flag = process_flag;
    }

    /**
     * @return the add_date
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * @param add_date the add_date to set
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * @return the process_date
     */
    public Date getProcess_date() {
        return process_date;
    }

    /**
     * @param process_date the process_date to set
     */
    public void setProcess_date(Date process_date) {
        this.process_date = process_date;
    }

    /**
     * @return the EFRIS_invoice_detail_id
     */
    public long getEFRIS_invoice_detail_id() {
        return EFRIS_invoice_detail_id;
    }

    /**
     * @param EFRIS_invoice_detail_id the EFRIS_invoice_detail_id to set
     */
    public void setEFRIS_invoice_detail_id(long EFRIS_invoice_detail_id) {
        this.EFRIS_invoice_detail_id = EFRIS_invoice_detail_id;
    }

    /**
     * @return the process_desc
     */
    public String getProcess_desc() {
        return process_desc;
    }

    /**
     * @param process_desc the process_desc to set
     */
    public void setProcess_desc(String process_desc) {
        this.process_desc = process_desc;
    }

    /**
     * @return the antifakeCode
     */
    public String getAntifakeCode() {
        return antifakeCode;
    }

    /**
     * @param antifakeCode the antifakeCode to set
     */
    public void setAntifakeCode(String antifakeCode) {
        this.antifakeCode = antifakeCode;
    }

    /**
     * @return the qrCode
     */
    public String getQrCode() {
        return qrCode;
    }

    /**
     * @param qrCode the qrCode to set
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    
}
