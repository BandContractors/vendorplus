/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris;

import api_tax.efris.innerclasses.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bajuna
 */
public class EFRISInvoice {

    public EFRISInvoice() {
    }

    public EFRISInvoice(SellerDetails sellerDetails, BasicInformation basicInformation, BuyerDetails buyerDetails, Summary summary, Extend extend) {
        this.sellerDetails = sellerDetails;
        this.basicInformation = basicInformation;
        this.buyerDetails = buyerDetails;
        this.summary = summary;
        this.extend = extend;
    }

    private SellerDetails sellerDetails;
    private BasicInformation basicInformation;
    private BuyerDetails buyerDetails;
    private List<GoodsDetails> goodsDetails = new ArrayList<>();
    private List<TaxDetails> taxDetails = new ArrayList();
    private Summary summary;
    private List<PayWay> payWay = new ArrayList();
    private Extend extend;

    public SellerDetails getSellerDetails() {
        return sellerDetails;
    }

    public void setSellerDetails(SellerDetails sellerDetails) {
        this.sellerDetails = sellerDetails;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

    public BuyerDetails getBuyerDetails() {
        return buyerDetails;
    }

    public void setBuyerDetails(BuyerDetails buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    public List<GoodsDetails> getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(List<GoodsDetails> goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public List<TaxDetails> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetails> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<PayWay> getPayWay() {
        return payWay;
    }

    public void setPayWay(List<PayWay> payWay) {
        this.payWay = payWay;
    }

    public Extend getExtend() {
        return extend;
    }

    public void setExtend(Extend extend) {
        this.extend = extend;
    }
}
