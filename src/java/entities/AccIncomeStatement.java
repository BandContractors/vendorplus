package entities;

import java.io.Serializable;
import java.util.Date;
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
public class AccIncomeStatement implements Serializable {
    private static final long serialVersionUID = 1L;

    private int AccPeriodId;
    private double RevORSaleProduct;
    private double RevORSaleService;
    private double RevORSaleHire;
    private double RevORSaleDisc;
    private double RevORSaleReturn;
    private double RevORSaleTotal;
    private double RevNORInterest;
    private double RevNORDividend;
    private double RevNORCommission;
    private double RevNORRental;
    private double RevNORGainSaleAsset;
    private double RevNORGainGift;
    private double RevNORGainExchange;
    private double RevNOROther;
    private double RevNORTotal;
    private double RevTotal;
    private double ExpCOGSProduct;
    private double ExpCOGSService;
    private double ExpCOGSFreight;
    private double ExpCOGSInvAdj;
    private double ExpCOGSReturn;
    private double ExpCOGSDisc;
    private double ExpCOGSManfSold;
    private double ExpCOGSLoyalty;
    private double ExpCOGSInvWriteOff;
    private double ExpCOGSTotal;
    private double GrossProfit;
    private double ExpOEAdvertise;
    private double ExpOEAudit;
    private double ExpOEBadDebts;
    private double ExpOECommission;
    private double ExpOEComputer;
    private double ExpOEDonations;
    private double ExpOEEntertainment;
    private double ExpOEFreightTransport;
    private double ExpOEGift;
    private double ExpOEHotelLodging;
    private double ExpOELegal;
    private double ExpOEUtility;
    private double ExpOERent;
    private double ExpOERates;
    private double ExpOERepairMaint;
    private double ExpOESalesPromotion;
    private double ExpOEStaffWelfare;
    private double ExpOEStartupPreOperate;
    private double ExpOEStationeryPrint;
    private double ExpOESubsAllowance;
    private double ExpOETelephone;
    private double ExpOETraining;
    private double ExpOETravel;
    private double ExpOEWorkshopConf;
    private double ExpOEInternet;
    private double ExpOEDepriciation;
    private double ExpOELossDisposalAsset;
    private double ExpOEManagementFees;
    private double ExpOEScientificResearch;
    private double ExpOEEmployment;
    private double ExpOEFinancial;
    private double ExpOEShortInsurance;
    private double ExpOEIncomeTax;
    private double ExpOEProposedDividend;
    private double ExpOEOther;
    private double ExpOETotal;
    private double ExpNOE;
    private double ExpNOETotal;
    private double ExpOENOETotal;
    private double NetProfit;
    private Date date1;
    private Date date2;

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
     * @return the RevORSaleProduct
     */
    public double getRevORSaleProduct() {
        return RevORSaleProduct;
    }

    /**
     * @param RevORSaleProduct the RevORSaleProduct to set
     */
    public void setRevORSaleProduct(double RevORSaleProduct) {
        this.RevORSaleProduct = RevORSaleProduct;
    }

    /**
     * @return the RevORSaleService
     */
    public double getRevORSaleService() {
        return RevORSaleService;
    }

    /**
     * @param RevORSaleService the RevORSaleService to set
     */
    public void setRevORSaleService(double RevORSaleService) {
        this.RevORSaleService = RevORSaleService;
    }

    /**
     * @return the RevORSaleDisc
     */
    public double getRevORSaleDisc() {
        return RevORSaleDisc;
    }

    /**
     * @param RevORSaleDisc the RevORSaleDisc to set
     */
    public void setRevORSaleDisc(double RevORSaleDisc) {
        this.RevORSaleDisc = RevORSaleDisc;
    }

    /**
     * @return the RevORSaleReturn
     */
    public double getRevORSaleReturn() {
        return RevORSaleReturn;
    }

    /**
     * @param RevORSaleReturn the RevORSaleReturn to set
     */
    public void setRevORSaleReturn(double RevORSaleReturn) {
        this.RevORSaleReturn = RevORSaleReturn;
    }

    /**
     * @return the RevORSaleTotal
     */
    public double getRevORSaleTotal() {
        return RevORSaleTotal;
    }

    /**
     * @param RevORSaleTotal the RevORSaleTotal to set
     */
    public void setRevORSaleTotal(double RevORSaleTotal) {
        this.RevORSaleTotal = RevORSaleTotal;
    }

    /**
     * @return the RevNORInterest
     */
    public double getRevNORInterest() {
        return RevNORInterest;
    }

    /**
     * @param RevNORInterest the RevNORInterest to set
     */
    public void setRevNORInterest(double RevNORInterest) {
        this.RevNORInterest = RevNORInterest;
    }

    /**
     * @return the RevNORDividend
     */
    public double getRevNORDividend() {
        return RevNORDividend;
    }

    /**
     * @param RevNORDividend the RevNORDividend to set
     */
    public void setRevNORDividend(double RevNORDividend) {
        this.RevNORDividend = RevNORDividend;
    }

    /**
     * @return the RevNORCommission
     */
    public double getRevNORCommission() {
        return RevNORCommission;
    }

    /**
     * @param RevNORCommission the RevNORCommission to set
     */
    public void setRevNORCommission(double RevNORCommission) {
        this.RevNORCommission = RevNORCommission;
    }

    /**
     * @return the RevNORRental
     */
    public double getRevNORRental() {
        return RevNORRental;
    }

    /**
     * @param RevNORRental the RevNORRental to set
     */
    public void setRevNORRental(double RevNORRental) {
        this.RevNORRental = RevNORRental;
    }

    /**
     * @return the RevNORGainSaleAsset
     */
    public double getRevNORGainSaleAsset() {
        return RevNORGainSaleAsset;
    }

    /**
     * @param RevNORGainSaleAsset the RevNORGainSaleAsset to set
     */
    public void setRevNORGainSaleAsset(double RevNORGainSaleAsset) {
        this.RevNORGainSaleAsset = RevNORGainSaleAsset;
    }

    /**
     * @return the RevNORGainGift
     */
    public double getRevNORGainGift() {
        return RevNORGainGift;
    }

    /**
     * @param RevNORGainGift the RevNORGainGift to set
     */
    public void setRevNORGainGift(double RevNORGainGift) {
        this.RevNORGainGift = RevNORGainGift;
    }

    /**
     * @return the RevNORGainExchange
     */
    public double getRevNORGainExchange() {
        return RevNORGainExchange;
    }

    /**
     * @param RevNORGainExchange the RevNORGainExchange to set
     */
    public void setRevNORGainExchange(double RevNORGainExchange) {
        this.RevNORGainExchange = RevNORGainExchange;
    }

    /**
     * @return the RevNOROther
     */
    public double getRevNOROther() {
        return RevNOROther;
    }

    /**
     * @param RevNOROther the RevNOROther to set
     */
    public void setRevNOROther(double RevNOROther) {
        this.RevNOROther = RevNOROther;
    }

    /**
     * @return the RevNORTotal
     */
    public double getRevNORTotal() {
        return RevNORTotal;
    }

    /**
     * @param RevNORTotal the RevNORTotal to set
     */
    public void setRevNORTotal(double RevNORTotal) {
        this.RevNORTotal = RevNORTotal;
    }

    /**
     * @return the RevTotal
     */
    public double getRevTotal() {
        return RevTotal;
    }

    /**
     * @param RevTotal the RevTotal to set
     */
    public void setRevTotal(double RevTotal) {
        this.RevTotal = RevTotal;
    }

    /**
     * @return the ExpCOGSProduct
     */
    public double getExpCOGSProduct() {
        return ExpCOGSProduct;
    }

    /**
     * @param ExpCOGSProduct the ExpCOGSProduct to set
     */
    public void setExpCOGSProduct(double ExpCOGSProduct) {
        this.ExpCOGSProduct = ExpCOGSProduct;
    }

    /**
     * @return the ExpCOGSService
     */
    public double getExpCOGSService() {
        return ExpCOGSService;
    }

    /**
     * @param ExpCOGSService the ExpCOGSService to set
     */
    public void setExpCOGSService(double ExpCOGSService) {
        this.ExpCOGSService = ExpCOGSService;
    }

    /**
     * @return the ExpCOGSFreight
     */
    public double getExpCOGSFreight() {
        return ExpCOGSFreight;
    }

    /**
     * @param ExpCOGSFreight the ExpCOGSFreight to set
     */
    public void setExpCOGSFreight(double ExpCOGSFreight) {
        this.ExpCOGSFreight = ExpCOGSFreight;
    }

    /**
     * @return the ExpCOGSInvAdj
     */
    public double getExpCOGSInvAdj() {
        return ExpCOGSInvAdj;
    }

    /**
     * @param ExpCOGSInvAdj the ExpCOGSInvAdj to set
     */
    public void setExpCOGSInvAdj(double ExpCOGSInvAdj) {
        this.ExpCOGSInvAdj = ExpCOGSInvAdj;
    }

    /**
     * @return the ExpCOGSReturn
     */
    public double getExpCOGSReturn() {
        return ExpCOGSReturn;
    }

    /**
     * @param ExpCOGSReturn the ExpCOGSReturn to set
     */
    public void setExpCOGSReturn(double ExpCOGSReturn) {
        this.ExpCOGSReturn = ExpCOGSReturn;
    }

    /**
     * @return the ExpCOGSDisc
     */
    public double getExpCOGSDisc() {
        return ExpCOGSDisc;
    }

    /**
     * @param ExpCOGSDisc the ExpCOGSDisc to set
     */
    public void setExpCOGSDisc(double ExpCOGSDisc) {
        this.ExpCOGSDisc = ExpCOGSDisc;
    }

    /**
     * @return the ExpCOGSManfSold
     */
    public double getExpCOGSManfSold() {
        return ExpCOGSManfSold;
    }

    /**
     * @param ExpCOGSManfSold the ExpCOGSManfSold to set
     */
    public void setExpCOGSManfSold(double ExpCOGSManfSold) {
        this.ExpCOGSManfSold = ExpCOGSManfSold;
    }

    /**
     * @return the ExpCOGSLoyalty
     */
    public double getExpCOGSLoyalty() {
        return ExpCOGSLoyalty;
    }

    /**
     * @param ExpCOGSLoyalty the ExpCOGSLoyalty to set
     */
    public void setExpCOGSLoyalty(double ExpCOGSLoyalty) {
        this.ExpCOGSLoyalty = ExpCOGSLoyalty;
    }

    /**
     * @return the ExpCOGSInvWriteOff
     */
    public double getExpCOGSInvWriteOff() {
        return ExpCOGSInvWriteOff;
    }

    /**
     * @param ExpCOGSInvWriteOff the ExpCOGSInvWriteOff to set
     */
    public void setExpCOGSInvWriteOff(double ExpCOGSInvWriteOff) {
        this.ExpCOGSInvWriteOff = ExpCOGSInvWriteOff;
    }

    /**
     * @return the ExpCOGSTotal
     */
    public double getExpCOGSTotal() {
        return ExpCOGSTotal;
    }

    /**
     * @param ExpCOGSTotal the ExpCOGSTotal to set
     */
    public void setExpCOGSTotal(double ExpCOGSTotal) {
        this.ExpCOGSTotal = ExpCOGSTotal;
    }

    /**
     * @return the GrossProfit
     */
    public double getGrossProfit() {
        return GrossProfit;
    }

    /**
     * @param GrossProfit the GrossProfit to set
     */
    public void setGrossProfit(double GrossProfit) {
        this.GrossProfit = GrossProfit;
    }

    /**
     * @return the ExpOEAdvertise
     */
    public double getExpOEAdvertise() {
        return ExpOEAdvertise;
    }

    /**
     * @param ExpOEAdvertise the ExpOEAdvertise to set
     */
    public void setExpOEAdvertise(double ExpOEAdvertise) {
        this.ExpOEAdvertise = ExpOEAdvertise;
    }

    /**
     * @return the ExpOEAudit
     */
    public double getExpOEAudit() {
        return ExpOEAudit;
    }

    /**
     * @param ExpOEAudit the ExpOEAudit to set
     */
    public void setExpOEAudit(double ExpOEAudit) {
        this.ExpOEAudit = ExpOEAudit;
    }

    /**
     * @return the ExpOEBadDebts
     */
    public double getExpOEBadDebts() {
        return ExpOEBadDebts;
    }

    /**
     * @param ExpOEBadDebts the ExpOEBadDebts to set
     */
    public void setExpOEBadDebts(double ExpOEBadDebts) {
        this.ExpOEBadDebts = ExpOEBadDebts;
    }

    /**
     * @return the ExpOECommission
     */
    public double getExpOECommission() {
        return ExpOECommission;
    }

    /**
     * @param ExpOECommission the ExpOECommission to set
     */
    public void setExpOECommission(double ExpOECommission) {
        this.ExpOECommission = ExpOECommission;
    }

    /**
     * @return the ExpOEComputer
     */
    public double getExpOEComputer() {
        return ExpOEComputer;
    }

    /**
     * @param ExpOEComputer the ExpOEComputer to set
     */
    public void setExpOEComputer(double ExpOEComputer) {
        this.ExpOEComputer = ExpOEComputer;
    }

    /**
     * @return the ExpOEDonations
     */
    public double getExpOEDonations() {
        return ExpOEDonations;
    }

    /**
     * @param ExpOEDonations the ExpOEDonations to set
     */
    public void setExpOEDonations(double ExpOEDonations) {
        this.ExpOEDonations = ExpOEDonations;
    }

    /**
     * @return the ExpOEEntertainment
     */
    public double getExpOEEntertainment() {
        return ExpOEEntertainment;
    }

    /**
     * @param ExpOEEntertainment the ExpOEEntertainment to set
     */
    public void setExpOEEntertainment(double ExpOEEntertainment) {
        this.ExpOEEntertainment = ExpOEEntertainment;
    }

    /**
     * @return the ExpOEFreightTransport
     */
    public double getExpOEFreightTransport() {
        return ExpOEFreightTransport;
    }

    /**
     * @param ExpOEFreightTransport the ExpOEFreightTransport to set
     */
    public void setExpOEFreightTransport(double ExpOEFreightTransport) {
        this.ExpOEFreightTransport = ExpOEFreightTransport;
    }

    /**
     * @return the ExpOEGift
     */
    public double getExpOEGift() {
        return ExpOEGift;
    }

    /**
     * @param ExpOEGift the ExpOEGift to set
     */
    public void setExpOEGift(double ExpOEGift) {
        this.ExpOEGift = ExpOEGift;
    }

    /**
     * @return the ExpOEHotelLodging
     */
    public double getExpOEHotelLodging() {
        return ExpOEHotelLodging;
    }

    /**
     * @param ExpOEHotelLodging the ExpOEHotelLodging to set
     */
    public void setExpOEHotelLodging(double ExpOEHotelLodging) {
        this.ExpOEHotelLodging = ExpOEHotelLodging;
    }

    /**
     * @return the ExpOELegal
     */
    public double getExpOELegal() {
        return ExpOELegal;
    }

    /**
     * @param ExpOELegal the ExpOELegal to set
     */
    public void setExpOELegal(double ExpOELegal) {
        this.ExpOELegal = ExpOELegal;
    }

    /**
     * @return the ExpOEUtility
     */
    public double getExpOEUtility() {
        return ExpOEUtility;
    }

    /**
     * @param ExpOEUtility the ExpOEUtility to set
     */
    public void setExpOEUtility(double ExpOEUtility) {
        this.ExpOEUtility = ExpOEUtility;
    }

    /**
     * @return the ExpOERent
     */
    public double getExpOERent() {
        return ExpOERent;
    }

    /**
     * @param ExpOERent the ExpOERent to set
     */
    public void setExpOERent(double ExpOERent) {
        this.ExpOERent = ExpOERent;
    }

    /**
     * @return the ExpOERates
     */
    public double getExpOERates() {
        return ExpOERates;
    }

    /**
     * @param ExpOERates the ExpOERates to set
     */
    public void setExpOERates(double ExpOERates) {
        this.ExpOERates = ExpOERates;
    }

    /**
     * @return the ExpOERepairMaint
     */
    public double getExpOERepairMaint() {
        return ExpOERepairMaint;
    }

    /**
     * @param ExpOERepairMaint the ExpOERepairMaint to set
     */
    public void setExpOERepairMaint(double ExpOERepairMaint) {
        this.ExpOERepairMaint = ExpOERepairMaint;
    }

    /**
     * @return the ExpOESalesPromotion
     */
    public double getExpOESalesPromotion() {
        return ExpOESalesPromotion;
    }

    /**
     * @param ExpOESalesPromotion the ExpOESalesPromotion to set
     */
    public void setExpOESalesPromotion(double ExpOESalesPromotion) {
        this.ExpOESalesPromotion = ExpOESalesPromotion;
    }

    /**
     * @return the ExpOEStaffWelfare
     */
    public double getExpOEStaffWelfare() {
        return ExpOEStaffWelfare;
    }

    /**
     * @param ExpOEStaffWelfare the ExpOEStaffWelfare to set
     */
    public void setExpOEStaffWelfare(double ExpOEStaffWelfare) {
        this.ExpOEStaffWelfare = ExpOEStaffWelfare;
    }

    /**
     * @return the ExpOEStartupPreOperate
     */
    public double getExpOEStartupPreOperate() {
        return ExpOEStartupPreOperate;
    }

    /**
     * @param ExpOEStartupPreOperate the ExpOEStartupPreOperate to set
     */
    public void setExpOEStartupPreOperate(double ExpOEStartupPreOperate) {
        this.ExpOEStartupPreOperate = ExpOEStartupPreOperate;
    }

    /**
     * @return the ExpOEStationeryPrint
     */
    public double getExpOEStationeryPrint() {
        return ExpOEStationeryPrint;
    }

    /**
     * @param ExpOEStationeryPrint the ExpOEStationeryPrint to set
     */
    public void setExpOEStationeryPrint(double ExpOEStationeryPrint) {
        this.ExpOEStationeryPrint = ExpOEStationeryPrint;
    }

    /**
     * @return the ExpOESubsAllowance
     */
    public double getExpOESubsAllowance() {
        return ExpOESubsAllowance;
    }

    /**
     * @param ExpOESubsAllowance the ExpOESubsAllowance to set
     */
    public void setExpOESubsAllowance(double ExpOESubsAllowance) {
        this.ExpOESubsAllowance = ExpOESubsAllowance;
    }

    /**
     * @return the ExpOETelephone
     */
    public double getExpOETelephone() {
        return ExpOETelephone;
    }

    /**
     * @param ExpOETelephone the ExpOETelephone to set
     */
    public void setExpOETelephone(double ExpOETelephone) {
        this.ExpOETelephone = ExpOETelephone;
    }

    /**
     * @return the ExpOETraining
     */
    public double getExpOETraining() {
        return ExpOETraining;
    }

    /**
     * @param ExpOETraining the ExpOETraining to set
     */
    public void setExpOETraining(double ExpOETraining) {
        this.ExpOETraining = ExpOETraining;
    }

    /**
     * @return the ExpOETravel
     */
    public double getExpOETravel() {
        return ExpOETravel;
    }

    /**
     * @param ExpOETravel the ExpOETravel to set
     */
    public void setExpOETravel(double ExpOETravel) {
        this.ExpOETravel = ExpOETravel;
    }

    /**
     * @return the ExpOEWorkshopConf
     */
    public double getExpOEWorkshopConf() {
        return ExpOEWorkshopConf;
    }

    /**
     * @param ExpOEWorkshopConf the ExpOEWorkshopConf to set
     */
    public void setExpOEWorkshopConf(double ExpOEWorkshopConf) {
        this.ExpOEWorkshopConf = ExpOEWorkshopConf;
    }

    /**
     * @return the ExpOEInternet
     */
    public double getExpOEInternet() {
        return ExpOEInternet;
    }

    /**
     * @param ExpOEInternet the ExpOEInternet to set
     */
    public void setExpOEInternet(double ExpOEInternet) {
        this.ExpOEInternet = ExpOEInternet;
    }

    /**
     * @return the ExpOEDepriciation
     */
    public double getExpOEDepriciation() {
        return ExpOEDepriciation;
    }

    /**
     * @param ExpOEDepriciation the ExpOEDepriciation to set
     */
    public void setExpOEDepriciation(double ExpOEDepriciation) {
        this.ExpOEDepriciation = ExpOEDepriciation;
    }

    /**
     * @return the ExpOELossDisposalAsset
     */
    public double getExpOELossDisposalAsset() {
        return ExpOELossDisposalAsset;
    }

    /**
     * @param ExpOELossDisposalAsset the ExpOELossDisposalAsset to set
     */
    public void setExpOELossDisposalAsset(double ExpOELossDisposalAsset) {
        this.ExpOELossDisposalAsset = ExpOELossDisposalAsset;
    }

    /**
     * @return the ExpOEManagementFees
     */
    public double getExpOEManagementFees() {
        return ExpOEManagementFees;
    }

    /**
     * @param ExpOEManagementFees the ExpOEManagementFees to set
     */
    public void setExpOEManagementFees(double ExpOEManagementFees) {
        this.ExpOEManagementFees = ExpOEManagementFees;
    }

    /**
     * @return the ExpOEScientificResearch
     */
    public double getExpOEScientificResearch() {
        return ExpOEScientificResearch;
    }

    /**
     * @param ExpOEScientificResearch the ExpOEScientificResearch to set
     */
    public void setExpOEScientificResearch(double ExpOEScientificResearch) {
        this.ExpOEScientificResearch = ExpOEScientificResearch;
    }

    /**
     * @return the ExpOEEmployment
     */
    public double getExpOEEmployment() {
        return ExpOEEmployment;
    }

    /**
     * @param ExpOEEmployment the ExpOEEmployment to set
     */
    public void setExpOEEmployment(double ExpOEEmployment) {
        this.ExpOEEmployment = ExpOEEmployment;
    }

    /**
     * @return the ExpOEFinancial
     */
    public double getExpOEFinancial() {
        return ExpOEFinancial;
    }

    /**
     * @param ExpOEFinancial the ExpOEFinancial to set
     */
    public void setExpOEFinancial(double ExpOEFinancial) {
        this.ExpOEFinancial = ExpOEFinancial;
    }

    /**
     * @return the ExpOEShortInsurance
     */
    public double getExpOEShortInsurance() {
        return ExpOEShortInsurance;
    }

    /**
     * @param ExpOEShortInsurance the ExpOEShortInsurance to set
     */
    public void setExpOEShortInsurance(double ExpOEShortInsurance) {
        this.ExpOEShortInsurance = ExpOEShortInsurance;
    }

    /**
     * @return the ExpOEIncomeTax
     */
    public double getExpOEIncomeTax() {
        return ExpOEIncomeTax;
    }

    /**
     * @param ExpOEIncomeTax the ExpOEIncomeTax to set
     */
    public void setExpOEIncomeTax(double ExpOEIncomeTax) {
        this.ExpOEIncomeTax = ExpOEIncomeTax;
    }

    /**
     * @return the ExpOEProposedDividend
     */
    public double getExpOEProposedDividend() {
        return ExpOEProposedDividend;
    }

    /**
     * @param ExpOEProposedDividend the ExpOEProposedDividend to set
     */
    public void setExpOEProposedDividend(double ExpOEProposedDividend) {
        this.ExpOEProposedDividend = ExpOEProposedDividend;
    }

    /**
     * @return the ExpOEOther
     */
    public double getExpOEOther() {
        return ExpOEOther;
    }

    /**
     * @param ExpOEOther the ExpOEOther to set
     */
    public void setExpOEOther(double ExpOEOther) {
        this.ExpOEOther = ExpOEOther;
    }

    /**
     * @return the ExpOETotal
     */
    public double getExpOETotal() {
        return ExpOETotal;
    }

    /**
     * @param ExpOETotal the ExpOETotal to set
     */
    public void setExpOETotal(double ExpOETotal) {
        this.ExpOETotal = ExpOETotal;
    }

    /**
     * @return the ExpNOE
     */
    public double getExpNOE() {
        return ExpNOE;
    }

    /**
     * @param ExpNOE the ExpNOE to set
     */
    public void setExpNOE(double ExpNOE) {
        this.ExpNOE = ExpNOE;
    }

    /**
     * @return the ExpNOETotal
     */
    public double getExpNOETotal() {
        return ExpNOETotal;
    }

    /**
     * @param ExpNOETotal the ExpNOETotal to set
     */
    public void setExpNOETotal(double ExpNOETotal) {
        this.ExpNOETotal = ExpNOETotal;
    }

    /**
     * @return the ExpOENOETotal
     */
    public double getExpOENOETotal() {
        return ExpOENOETotal;
    }

    /**
     * @param ExpOENOETotal the ExpOENOETotal to set
     */
    public void setExpOENOETotal(double ExpOENOETotal) {
        this.ExpOENOETotal = ExpOENOETotal;
    }

    /**
     * @return the NetProfit
     */
    public double getNetProfit() {
        return NetProfit;
    }

    /**
     * @param NetProfit the NetProfit to set
     */
    public void setNetProfit(double NetProfit) {
        this.NetProfit = NetProfit;
    }

    /**
     * @return the RevORSaleHire
     */
    public double getRevORSaleHire() {
        return RevORSaleHire;
    }

    /**
     * @param RevORSaleHire the RevORSaleHire to set
     */
    public void setRevORSaleHire(double RevORSaleHire) {
        this.RevORSaleHire = RevORSaleHire;
    }

    /**
     * @return the date1
     */
    public Date getDate1() {
        return date1;
    }

    /**
     * @param date1 the date1 to set
     */
    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    /**
     * @return the date2
     */
    public Date getDate2() {
        return date2;
    }

    /**
     * @param date2 the date2 to set
     */
    public void setDate2(Date date2) {
        this.date2 = date2;
    }

}
