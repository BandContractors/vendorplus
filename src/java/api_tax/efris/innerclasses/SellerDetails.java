/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

/**
 *
 * @author bajuna
 */
public class SellerDetails {

    private String tin;
    private String ninBrn;
    private String legalName;
    private String businessName;
    private String address;
    private String mobilePhone;
    private String linePhone;
    private String emailAddress;
    private String placeOfBusiness;
    private String referenceNo;

    public SellerDetails(String tin, String ninBrn, String legalName, String businessName, String address, String mobilePhone, String linePhone, String emailAddress, String placeOfBusiness, String referenceNo) {
        this.tin = tin;
        this.ninBrn = ninBrn;
        this.legalName = legalName;
        this.businessName = businessName;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.linePhone = linePhone;
        this.emailAddress = emailAddress;
        this.placeOfBusiness = placeOfBusiness;
        this.referenceNo = referenceNo;
    }

    public SellerDetails() {
        this.address = "";
        this.businessName="";
        this.emailAddress="";
        this.legalName="";
        this.linePhone="";
        this.mobilePhone="";
        this.ninBrn="";
        this.placeOfBusiness="";
        this.referenceNo="";
        this.tin="";
    }

    // Getter Methods 
    public String getTin() {
        return tin;
    }

    public String getNinBrn() {
        return ninBrn;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getAddress() {
        return address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getLinePhone() {
        return linePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPlaceOfBusiness() {
        return placeOfBusiness;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    // Setter Methods 
    public void setTin(String tin) {
        this.tin = tin;
    }

    public void setNinBrn(String ninBrn) {
        this.ninBrn = ninBrn;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setLinePhone(String linePhone) {
        this.linePhone = linePhone;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPlaceOfBusiness(String placeOfBusiness) {
        this.placeOfBusiness = placeOfBusiness;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
}
