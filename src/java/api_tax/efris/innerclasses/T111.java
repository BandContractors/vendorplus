/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

import java.io.Serializable;

/**
 *
 * @author bajuna
 */
public class T111 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String invoiceNo;
    private String oriInvoiceNo;
    private String legalName;
    private String grossAmount;

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
     * @return the legalName
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * @param legalName the legalName to set
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
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
}
