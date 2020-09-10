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
public class Item_unspsc implements Serializable {
    private static final long serialVersionUID = 1L;

    private long item_unspsc_id;
    private String segment_code;
    private String segment_name;
    private String family_code;
    private String family_name;
    private String class_code;
    private String class_name;
    private String commodity_code;
    private String commodity_name;
    private String excise_duty_product_type;
    private String vat_rate;
    private String service_mark;
    private String zero_rate;
    private String exempt_rate;

    /**
     * @return the item_unspsc_id
     */
    public long getItem_unspsc_id() {
        return item_unspsc_id;
    }

    /**
     * @param item_unspsc_id the item_unspsc_id to set
     */
    public void setItem_unspsc_id(long item_unspsc_id) {
        this.item_unspsc_id = item_unspsc_id;
    }

    /**
     * @return the segment_code
     */
    public String getSegment_code() {
        return segment_code;
    }

    /**
     * @param segment_code the segment_code to set
     */
    public void setSegment_code(String segment_code) {
        this.segment_code = segment_code;
    }

    /**
     * @return the segment_name
     */
    public String getSegment_name() {
        return segment_name;
    }

    /**
     * @param segment_name the segment_name to set
     */
    public void setSegment_name(String segment_name) {
        this.segment_name = segment_name;
    }

    /**
     * @return the family_code
     */
    public String getFamily_code() {
        return family_code;
    }

    /**
     * @param family_code the family_code to set
     */
    public void setFamily_code(String family_code) {
        this.family_code = family_code;
    }

    /**
     * @return the family_name
     */
    public String getFamily_name() {
        return family_name;
    }

    /**
     * @param family_name the family_name to set
     */
    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    /**
     * @return the class_code
     */
    public String getClass_code() {
        return class_code;
    }

    /**
     * @param class_code the class_code to set
     */
    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    /**
     * @return the class_name
     */
    public String getClass_name() {
        return class_name;
    }

    /**
     * @param class_name the class_name to set
     */
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    /**
     * @return the commodity_code
     */
    public String getCommodity_code() {
        return commodity_code;
    }

    /**
     * @param commodity_code the commodity_code to set
     */
    public void setCommodity_code(String commodity_code) {
        this.commodity_code = commodity_code;
    }

    /**
     * @return the commodity_name
     */
    public String getCommodity_name() {
        return commodity_name;
    }

    /**
     * @param commodity_name the commodity_name to set
     */
    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    /**
     * @return the excise_duty_product_type
     */
    public String getExcise_duty_product_type() {
        return excise_duty_product_type;
    }

    /**
     * @param excise_duty_product_type the excise_duty_product_type to set
     */
    public void setExcise_duty_product_type(String excise_duty_product_type) {
        this.excise_duty_product_type = excise_duty_product_type;
    }

    /**
     * @return the vat_rate
     */
    public String getVat_rate() {
        return vat_rate;
    }

    /**
     * @param vat_rate the vat_rate to set
     */
    public void setVat_rate(String vat_rate) {
        this.vat_rate = vat_rate;
    }

    /**
     * @return the service_mark
     */
    public String getService_mark() {
        return service_mark;
    }

    /**
     * @param service_mark the service_mark to set
     */
    public void setService_mark(String service_mark) {
        this.service_mark = service_mark;
    }

    /**
     * @return the zero_rate
     */
    public String getZero_rate() {
        return zero_rate;
    }

    /**
     * @param zero_rate the zero_rate to set
     */
    public void setZero_rate(String zero_rate) {
        this.zero_rate = zero_rate;
    }

    /**
     * @return the exempt_rate
     */
    public String getExempt_rate() {
        return exempt_rate;
    }

    /**
     * @param exempt_rate the exempt_rate to set
     */
    public void setExempt_rate(String exempt_rate) {
        this.exempt_rate = exempt_rate;
    }
}
