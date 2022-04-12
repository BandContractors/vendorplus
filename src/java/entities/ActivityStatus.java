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
public class ActivityStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private int activity_status_id;
    private String activity_status_name;

    /**
     * @return the activity_status_id
     */
    public int getActivity_status_id() {
        return activity_status_id;
    }

    /**
     * @param activity_status_id the activity_status_id to set
     */
    public void setActivity_status_id(int activity_status_id) {
        this.activity_status_id = activity_status_id;
    }

    /**
     * @return the activity_status_name
     */
    public String getActivity_status_name() {
        return activity_status_name;
    }

    /**
     * @param activity_status_name the activity_status_name to set
     */
    public void setActivity_status_name(String activity_status_name) {
        this.activity_status_name = activity_status_name;
    }

   

}
