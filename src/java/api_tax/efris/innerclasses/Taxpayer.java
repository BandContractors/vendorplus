/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

import java.io.Serializable;

public class Taxpayer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tin = "";
    private String ninBrn = "";
    private String legalName = "";
    private String businessName = "";
    private String contactNumber = "";
    private String contactEmail = "";
    private String address = "";

    /**
     * @return the tin
     */
    public String getTin() {
        return tin;
    }

    /**
     * @param tin the tin to set
     */
    public void setTin(String tin) {
        this.tin = tin;
    }

    /**
     * @return the ninBrn
     */
    public String getNinBrn() {
        return ninBrn;
    }

    /**
     * @param ninBrn the ninBrn to set
     */
    public void setNinBrn(String ninBrn) {
        this.ninBrn = ninBrn;
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
     * @return the businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @param businessName the businessName to set
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
