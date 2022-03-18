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
public class Category_activity implements Serializable {

    private static final long serialVersionUID = 1L;
    public int category_activity_id;
    public String category_name;

    public int getCategory_activity_id() {
        return category_activity_id;
    }

    public void setCategory_activity_id(int category_activity_id) {
        this.category_activity_id = category_activity_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

}
