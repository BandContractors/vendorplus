/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author HP
 */
@ManagedBean
@SessionScoped
public class Subcategory_activity implements Serializable {

    private static final long serialVersionUID = 1L;
    private int subcategory_activity_id;
    private int category_activity_id;
    private String subcategory_name;

    /**
     * @return the subcategory_activity_id
     */
    public int getSubcategory_activity_id() {
        return subcategory_activity_id;
    }

    /**
     * @param subcategory_activity_id the subcategory_activity_id to set
     */
    public void setSubcategory_activity_id(int subcategory_activity_id) {
        this.subcategory_activity_id = subcategory_activity_id;
    }

    /**
     * @return the category_activity_id
     */
    public int getCategory_activity_id() {
        return category_activity_id;
    }

    /**
     * @param category_activity_id the category_activity_id to set
     */
    public void setCategory_activity_id(int category_activity_id) {
        this.category_activity_id = category_activity_id;
    }

    /**
     * @return the subcategory_name
     */
    public String getSubcategory_name() {
        return subcategory_name;
    }

    /**
     * @param subcategory_name the subcategory_name to set
     */
    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

}
