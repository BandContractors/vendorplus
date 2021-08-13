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
public class BuyerDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String buyerTin;
    private String buyerNinBrn;
    private String buyerPassportNum;
    private String buyerLegalName;
    private String buyerBusinessName;
    private String buyerAddress;
    private String buyerEmail;
    private String buyerMobilePhone;
    private String buyerLinePhone;
    private String buyerPlaceOfBusi;
    private String buyerType;
    private String buyerCitizenship;
    private String buyerSector;
    private String buyerReferenceNo;

    public BuyerDetails(String buyerTin, String buyerNinBrn, String buyerPassportNum, String buyerLegalName, String buyerBusinessName, String buyerAddress, String buyerEmail, String buyerMobilePhone, String buyerLinePhone, String buyerPlaceOfBusi, String buyerType, String buyerCitizenship, String buyerSector, String buyerReferenceNo) {
        this.buyerTin = buyerTin;
        this.buyerNinBrn = buyerNinBrn;
        this.buyerPassportNum = buyerPassportNum;
        this.buyerLegalName = buyerLegalName;
        this.buyerBusinessName = buyerBusinessName;
        this.buyerAddress = buyerAddress;
        this.buyerEmail = buyerEmail;
        this.buyerMobilePhone = buyerMobilePhone;
        this.buyerLinePhone = buyerLinePhone;
        this.buyerPlaceOfBusi = buyerPlaceOfBusi;
        this.buyerType = buyerType;
        this.buyerCitizenship = buyerCitizenship;
        this.buyerSector = buyerSector;
        this.buyerReferenceNo = buyerReferenceNo;
    }

    public BuyerDetails() {
        this.buyerAddress="";
        this.buyerBusinessName="";
        this.buyerCitizenship="";
        this.buyerEmail="";
        this.buyerLegalName="";
        this.buyerLinePhone="";
        this.buyerMobilePhone="";
        this.buyerNinBrn="";
        this.buyerPassportNum="";
        this.buyerPlaceOfBusi="";
        this.buyerReferenceNo="";
        this.buyerSector="";
        this.buyerTin="";
        this.buyerType="";
    }

    // Getter Methods 
    public String getBuyerTin() {
        return buyerTin;
    }

    public String getBuyerNinBrn() {
        return buyerNinBrn;
    }

    public String getBuyerPassportNum() {
        return buyerPassportNum;
    }

    public String getBuyerLegalName() {
        return buyerLegalName;
    }

    public String getBuyerBusinessName() {
        return buyerBusinessName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String getBuyerMobilePhone() {
        return buyerMobilePhone;
    }

    public String getBuyerLinePhone() {
        return buyerLinePhone;
    }

    public String getBuyerPlaceOfBusi() {
        return buyerPlaceOfBusi;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public String getBuyerCitizenship() {
        return buyerCitizenship;
    }

    public String getBuyerSector() {
        return buyerSector;
    }

    public String getBuyerReferenceNo() {
        return buyerReferenceNo;
    }

    // Setter Methods 
    public void setBuyerTin(String buyerTin) {
        this.buyerTin = buyerTin;
    }

    public void setBuyerNinBrn(String buyerNinBrn) {
        this.buyerNinBrn = buyerNinBrn;
    }

    public void setBuyerPassportNum(String buyerPassportNum) {
        this.buyerPassportNum = buyerPassportNum;
    }

    public void setBuyerLegalName(String buyerLegalName) {
        this.buyerLegalName = buyerLegalName;
    }

    public void setBuyerBusinessName(String buyerBusinessName) {
        this.buyerBusinessName = buyerBusinessName;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public void setBuyerMobilePhone(String buyerMobilePhone) {
        this.buyerMobilePhone = buyerMobilePhone;
    }

    public void setBuyerLinePhone(String buyerLinePhone) {
        this.buyerLinePhone = buyerLinePhone;
    }

    public void setBuyerPlaceOfBusi(String buyerPlaceOfBusi) {
        this.buyerPlaceOfBusi = buyerPlaceOfBusi;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public void setBuyerCitizenship(String buyerCitizenship) {
        this.buyerCitizenship = buyerCitizenship;
    }

    public void setBuyerSector(String buyerSector) {
        this.buyerSector = buyerSector;
    }

    public void setBuyerReferenceNo(String buyerReferenceNo) {
        this.buyerReferenceNo = buyerReferenceNo;
    }
}
