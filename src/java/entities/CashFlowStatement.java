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
public class CashFlowStatement implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String cash_category;
    private Double Jan;
    private Double Feb;
    private Double Mar;
    private Double Apr;
    private Double May;
    private Double Jun;
    private Double Jul;
    private Double Aug;
    private Double Sep;
    private Double Oct;
    private Double Nov;
    private Double Dec;

    /**
     * @return the cash_category
     */
    public String getCash_category() {
        return cash_category;
    }

    /**
     * @param cash_category the cash_category to set
     */
    public void setCash_category(String cash_category) {
        this.cash_category = cash_category;
    }

    /**
     * @return the Jan
     */
    public Double getJan() {
        return Jan;
    }

    /**
     * @param Jan the Jan to set
     */
    public void setJan(Double Jan) {
        this.Jan = Jan;
    }

    /**
     * @return the Feb
     */
    public Double getFeb() {
        return Feb;
    }

    /**
     * @param Feb the Feb to set
     */
    public void setFeb(Double Feb) {
        this.Feb = Feb;
    }

    /**
     * @return the Mar
     */
    public Double getMar() {
        return Mar;
    }

    /**
     * @param Mar the Mar to set
     */
    public void setMar(Double Mar) {
        this.Mar = Mar;
    }

    /**
     * @return the Apr
     */
    public Double getApr() {
        return Apr;
    }

    /**
     * @param Apr the Apr to set
     */
    public void setApr(Double Apr) {
        this.Apr = Apr;
    }

    /**
     * @return the May
     */
    public Double getMay() {
        return May;
    }

    /**
     * @param May the May to set
     */
    public void setMay(Double May) {
        this.May = May;
    }

    /**
     * @return the Jun
     */
    public Double getJun() {
        return Jun;
    }

    /**
     * @param Jun the Jun to set
     */
    public void setJun(Double Jun) {
        this.Jun = Jun;
    }

    /**
     * @return the Jul
     */
    public Double getJul() {
        return Jul;
    }

    /**
     * @param Jul the Jul to set
     */
    public void setJul(Double Jul) {
        this.Jul = Jul;
    }

    /**
     * @return the Aug
     */
    public Double getAug() {
        return Aug;
    }

    /**
     * @param Aug the Aug to set
     */
    public void setAug(Double Aug) {
        this.Aug = Aug;
    }

    /**
     * @return the Sep
     */
    public Double getSep() {
        return Sep;
    }

    /**
     * @param Sep the Sep to set
     */
    public void setSep(Double Sep) {
        this.Sep = Sep;
    }

    /**
     * @return the Oct
     */
    public Double getOct() {
        return Oct;
    }

    /**
     * @param Oct the Oct to set
     */
    public void setOct(Double Oct) {
        this.Oct = Oct;
    }

    /**
     * @return the Nov
     */
    public Double getNov() {
        return Nov;
    }

    /**
     * @param Nov the Nov to set
     */
    public void setNov(Double Nov) {
        this.Nov = Nov;
    }

    /**
     * @return the Dec
     */
    public Double getDec() {
        return Dec;
    }

    /**
     * @param Dec the Dec to set
     */
    public void setDec(Double Dec) {
        this.Dec = Dec;
    }
}
