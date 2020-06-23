/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author bajuna
 */
public class BasicInformation {

    private String invoiceNo;
    private String antifakeCode;
    private String deviceNo;
    private String issuedDate;
    private String operator;
    private String currency;
    private String oriInvoiceId;
    private String invoiceType;
    private String invoiceKind;
    private String dataSource;

    public BasicInformation(String invoiceNo, String antifakeCode, String deviceNo, String issuedDate, String operator, String currency, String oriInvoiceId, String invoiceType, String invoiceKind, String dataSource) {
        this.invoiceNo = invoiceNo;
        this.antifakeCode = antifakeCode;
        this.deviceNo = deviceNo;
        this.issuedDate = issuedDate;
        this.operator = operator;
        this.currency = currency;
        this.oriInvoiceId = oriInvoiceId;
        this.invoiceType = invoiceType;
        this.invoiceKind = invoiceKind;
        this.dataSource = dataSource;
    }

    public BasicInformation() {
        this.antifakeCode="";
        this.currency="";
        this.dataSource="";
        this.deviceNo="";
        this.invoiceKind="";
        this.invoiceNo="";
        this.invoiceType="";
        this.issuedDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.operator="";
        this.oriInvoiceId="";
    }

    // Getter Methods 
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getAntifakeCode() {
        return antifakeCode;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getOperator() {
        return operator;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOriInvoiceId() {
        return oriInvoiceId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getInvoiceKind() {
        return invoiceKind;
    }

    public String getDataSource() {
        return dataSource;
    }

    // Setter Methods 
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public void setAntifakeCode(String antifakeCode) {
        this.antifakeCode = antifakeCode;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setOriInvoiceId(String oriInvoiceId) {
        this.oriInvoiceId = oriInvoiceId;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void setInvoiceKind(String invoiceKind) {
        this.invoiceKind = invoiceKind;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
