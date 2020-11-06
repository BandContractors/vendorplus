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
public class CashFlowStatement implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String cash_category;
    private double Jan;
    private double Feb;
    private double Mar;
    private double Apr;
    private double May;
    private double Jun;
    private double Jul;
    private double Aug;
    private double Sep;
    private double Oct;
    private double Nov;
    private double Dec;

    /**
     * @return the Jan
     */
    public double getJan() {
        return Jan;
    }

    /**
     * @param Jan the Jan to set
     */
    public void setJan(double Jan) {
        this.Jan = Jan;
    }

    /**
     * @return the Feb
     */
    public double getFeb() {
        return Feb;
    }

    /**
     * @param Feb the Feb to set
     */
    public void setFeb(double Feb) {
        this.Feb = Feb;
    }

    /**
     * @return the Mar
     */
    public double getMar() {
        return Mar;
    }

    /**
     * @param Mar the Mar to set
     */
    public void setMar(double Mar) {
        this.Mar = Mar;
    }

    /**
     * @return the Apr
     */
    public double getApr() {
        return Apr;
    }

    /**
     * @param Apr the Apr to set
     */
    public void setApr(double Apr) {
        this.Apr = Apr;
    }

    /**
     * @return the May
     */
    public double getMay() {
        return May;
    }

    /**
     * @param May the May to set
     */
    public void setMay(double May) {
        this.May = May;
    }

    /**
     * @return the Jun
     */
    public double getJun() {
        return Jun;
    }

    /**
     * @param Jun the Jun to set
     */
    public void setJun(double Jun) {
        this.Jun = Jun;
    }

    /**
     * @return the Jul
     */
    public double getJul() {
        return Jul;
    }

    /**
     * @param Jul the Jul to set
     */
    public void setJul(double Jul) {
        this.Jul = Jul;
    }

    /**
     * @return the Aug
     */
    public double getAug() {
        return Aug;
    }

    /**
     * @param Aug the Aug to set
     */
    public void setAug(double Aug) {
        this.Aug = Aug;
    }

    /**
     * @return the Sep
     */
    public double getSep() {
        return Sep;
    }

    /**
     * @param Sep the Sep to set
     */
    public void setSep(double Sep) {
        this.Sep = Sep;
    }

    /**
     * @return the Oct
     */
    public double getOct() {
        return Oct;
    }

    /**
     * @param Oct the Oct to set
     */
    public void setOct(double Oct) {
        this.Oct = Oct;
    }

    /**
     * @return the Nov
     */
    public double getNov() {
        return Nov;
    }

    /**
     * @param Nov the Nov to set
     */
    public void setNov(double Nov) {
        this.Nov = Nov;
    }

    /**
     * @return the Dec
     */
    public double getDec() {
        return Dec;
    }

    /**
     * @param Dec the Dec to set
     */
    public void setDec(double Dec) {
        this.Dec = Dec;
    }

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
}
