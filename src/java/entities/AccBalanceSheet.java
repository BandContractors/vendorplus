package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean
@SessionScoped
public class AccBalanceSheet implements Serializable {
    private static final long serialVersionUID = 1L;

    private int AccPeriodId;
    private double AssetFixedPPE;
    private double AssetFixedAccumDep;
    private double AssetFixedOtherNonCur;
    private double AssetCurCash;
    private double AssetCurRec;
    private double AssetCurInv;
    private double AssetCurPreExp;
    private double AssetCurOtherCur;
    private double AssetTotal;
    private double LiabLongDebt;
    private double LiabLongDefTax;
    private double LiabLongOtherNonCur;
    private double LiabCurPay;
    private double LiabCurAccrComp;
    private double LiabCurAccrTax;
    private double LiabCurOtherCur;
    private double LiabTotal;
    private double NetAssets;
    private double EquityPaidCap;
    private double EquityPartCap;
    private double EquityCommStock;
    private double EquityPrefStock;
    private double EquityMembCont;
    private double EquityRetEarn;
    private double EquityDrawing;
    private double EquityDividend;
    private double EquityTotal;

    /**
     * @return the AccPeriodId
     */
    public int getAccPeriodId() {
        return AccPeriodId;
    }

    /**
     * @param AccPeriodId the AccPeriodId to set
     */
    public void setAccPeriodId(int AccPeriodId) {
        this.AccPeriodId = AccPeriodId;
    }

    /**
     * @return the AssetFixedPPE
     */
    public double getAssetFixedPPE() {
        return AssetFixedPPE;
    }

    /**
     * @param AssetFixedPPE the AssetFixedPPE to set
     */
    public void setAssetFixedPPE(double AssetFixedPPE) {
        this.AssetFixedPPE = AssetFixedPPE;
    }

    /**
     * @return the AssetFixedAccumDep
     */
    public double getAssetFixedAccumDep() {
        return AssetFixedAccumDep;
    }

    /**
     * @param AssetFixedAccumDep the AssetFixedAccumDep to set
     */
    public void setAssetFixedAccumDep(double AssetFixedAccumDep) {
        this.AssetFixedAccumDep = AssetFixedAccumDep;
    }

    /**
     * @return the AssetFixedOtherNonCur
     */
    public double getAssetFixedOtherNonCur() {
        return AssetFixedOtherNonCur;
    }

    /**
     * @param AssetFixedOtherNonCur the AssetFixedOtherNonCur to set
     */
    public void setAssetFixedOtherNonCur(double AssetFixedOtherNonCur) {
        this.AssetFixedOtherNonCur = AssetFixedOtherNonCur;
    }

    /**
     * @return the AssetCurCash
     */
    public double getAssetCurCash() {
        return AssetCurCash;
    }

    /**
     * @param AssetCurCash the AssetCurCash to set
     */
    public void setAssetCurCash(double AssetCurCash) {
        this.AssetCurCash = AssetCurCash;
    }

    /**
     * @return the AssetCurRec
     */
    public double getAssetCurRec() {
        return AssetCurRec;
    }

    /**
     * @param AssetCurRec the AssetCurRec to set
     */
    public void setAssetCurRec(double AssetCurRec) {
        this.AssetCurRec = AssetCurRec;
    }

    /**
     * @return the AssetCurInv
     */
    public double getAssetCurInv() {
        return AssetCurInv;
    }

    /**
     * @param AssetCurInv the AssetCurInv to set
     */
    public void setAssetCurInv(double AssetCurInv) {
        this.AssetCurInv = AssetCurInv;
    }

    /**
     * @return the AssetCurPreExp
     */
    public double getAssetCurPreExp() {
        return AssetCurPreExp;
    }

    /**
     * @param AssetCurPreExp the AssetCurPreExp to set
     */
    public void setAssetCurPreExp(double AssetCurPreExp) {
        this.AssetCurPreExp = AssetCurPreExp;
    }

    /**
     * @return the AssetCurOtherCur
     */
    public double getAssetCurOtherCur() {
        return AssetCurOtherCur;
    }

    /**
     * @param AssetCurOtherCur the AssetCurOtherCur to set
     */
    public void setAssetCurOtherCur(double AssetCurOtherCur) {
        this.AssetCurOtherCur = AssetCurOtherCur;
    }

    /**
     * @return the AssetTotal
     */
    public double getAssetTotal() {
        return AssetTotal;
    }

    /**
     * @param AssetTotal the AssetTotal to set
     */
    public void setAssetTotal(double AssetTotal) {
        this.AssetTotal = AssetTotal;
    }

    /**
     * @return the LiabLongDebt
     */
    public double getLiabLongDebt() {
        return LiabLongDebt;
    }

    /**
     * @param LiabLongDebt the LiabLongDebt to set
     */
    public void setLiabLongDebt(double LiabLongDebt) {
        this.LiabLongDebt = LiabLongDebt;
    }

    /**
     * @return the LiabLongDefTax
     */
    public double getLiabLongDefTax() {
        return LiabLongDefTax;
    }

    /**
     * @param LiabLongDefTax the LiabLongDefTax to set
     */
    public void setLiabLongDefTax(double LiabLongDefTax) {
        this.LiabLongDefTax = LiabLongDefTax;
    }

    /**
     * @return the LiabLongOtherNonCur
     */
    public double getLiabLongOtherNonCur() {
        return LiabLongOtherNonCur;
    }

    /**
     * @param LiabLongOtherNonCur the LiabLongOtherNonCur to set
     */
    public void setLiabLongOtherNonCur(double LiabLongOtherNonCur) {
        this.LiabLongOtherNonCur = LiabLongOtherNonCur;
    }

    /**
     * @return the LiabCurPay
     */
    public double getLiabCurPay() {
        return LiabCurPay;
    }

    /**
     * @param LiabCurPay the LiabCurPay to set
     */
    public void setLiabCurPay(double LiabCurPay) {
        this.LiabCurPay = LiabCurPay;
    }

    /**
     * @return the LiabCurAccrTax
     */
    public double getLiabCurAccrTax() {
        return LiabCurAccrTax;
    }

    /**
     * @param LiabCurAccrTax the LiabCurAccrTax to set
     */
    public void setLiabCurAccrTax(double LiabCurAccrTax) {
        this.LiabCurAccrTax = LiabCurAccrTax;
    }

    /**
     * @return the LiabCurOtherCur
     */
    public double getLiabCurOtherCur() {
        return LiabCurOtherCur;
    }

    /**
     * @param LiabCurOtherCur the LiabCurOtherCur to set
     */
    public void setLiabCurOtherCur(double LiabCurOtherCur) {
        this.LiabCurOtherCur = LiabCurOtherCur;
    }

    /**
     * @return the LiabTotal
     */
    public double getLiabTotal() {
        return LiabTotal;
    }

    /**
     * @param LiabTotal the LiabTotal to set
     */
    public void setLiabTotal(double LiabTotal) {
        this.LiabTotal = LiabTotal;
    }

    /**
     * @return the NetAssets
     */
    public double getNetAssets() {
        return NetAssets;
    }

    /**
     * @param NetAssets the NetAssets to set
     */
    public void setNetAssets(double NetAssets) {
        this.NetAssets = NetAssets;
    }

    /**
     * @return the EquityPaidCap
     */
    public double getEquityPaidCap() {
        return EquityPaidCap;
    }

    /**
     * @param EquityPaidCap the EquityPaidCap to set
     */
    public void setEquityPaidCap(double EquityPaidCap) {
        this.EquityPaidCap = EquityPaidCap;
    }

    /**
     * @return the EquityPartCap
     */
    public double getEquityPartCap() {
        return EquityPartCap;
    }

    /**
     * @param EquityPartCap the EquityPartCap to set
     */
    public void setEquityPartCap(double EquityPartCap) {
        this.EquityPartCap = EquityPartCap;
    }

    /**
     * @return the EquityCommStock
     */
    public double getEquityCommStock() {
        return EquityCommStock;
    }

    /**
     * @param EquityCommStock the EquityCommStock to set
     */
    public void setEquityCommStock(double EquityCommStock) {
        this.EquityCommStock = EquityCommStock;
    }

    /**
     * @return the EquityPrefStock
     */
    public double getEquityPrefStock() {
        return EquityPrefStock;
    }

    /**
     * @param EquityPrefStock the EquityPrefStock to set
     */
    public void setEquityPrefStock(double EquityPrefStock) {
        this.EquityPrefStock = EquityPrefStock;
    }

    /**
     * @return the EquityMembCont
     */
    public double getEquityMembCont() {
        return EquityMembCont;
    }

    /**
     * @param EquityMembCont the EquityMembCont to set
     */
    public void setEquityMembCont(double EquityMembCont) {
        this.EquityMembCont = EquityMembCont;
    }

    /**
     * @return the EquityRetEarn
     */
    public double getEquityRetEarn() {
        return EquityRetEarn;
    }

    /**
     * @param EquityRetEarn the EquityRetEarn to set
     */
    public void setEquityRetEarn(double EquityRetEarn) {
        this.EquityRetEarn = EquityRetEarn;
    }

    /**
     * @return the EquityDrawing
     */
    public double getEquityDrawing() {
        return EquityDrawing;
    }

    /**
     * @param EquityDrawing the EquityDrawing to set
     */
    public void setEquityDrawing(double EquityDrawing) {
        this.EquityDrawing = EquityDrawing;
    }

    /**
     * @return the EquityTotal
     */
    public double getEquityTotal() {
        return EquityTotal;
    }

    /**
     * @param EquityTotal the EquityTotal to set
     */
    public void setEquityTotal(double EquityTotal) {
        this.EquityTotal = EquityTotal;
    }

    /**
     * @return the LiabCurAccrComp
     */
    public double getLiabCurAccrComp() {
        return LiabCurAccrComp;
    }

    /**
     * @param LiabCurAccrComp the LiabCurAccrComp to set
     */
    public void setLiabCurAccrComp(double LiabCurAccrComp) {
        this.LiabCurAccrComp = LiabCurAccrComp;
    }

    /**
     * @return the EquityDividend
     */
    public double getEquityDividend() {
        return EquityDividend;
    }

    /**
     * @param EquityDividend the EquityDividend to set
     */
    public void setEquityDividend(double EquityDividend) {
        this.EquityDividend = EquityDividend;
    }

}
