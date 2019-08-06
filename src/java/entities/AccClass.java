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
public class AccClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccClassId;
    private String AccClassName;
    private String AccClassDesc;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;

    /**
     * @return the AccClassId
     */
    public int getAccClassId() {
        return AccClassId;
    }

    /**
     * @param AccClassId the AccClassId to set
     */
    public void setAccClassId(int AccClassId) {
        this.AccClassId = AccClassId;
    }

    /**
     * @return the AccClassName
     */
    public String getAccClassName() {
        return AccClassName;
    }

    /**
     * @param AccClassName the AccClassName to set
     */
    public void setAccClassName(String AccClassName) {
        this.AccClassName = AccClassName;
    }

    /**
     * @return the AccClassDesc
     */
    public String getAccClassDesc() {
        return AccClassDesc;
    }

    /**
     * @param AccClassDesc the AccClassDesc to set
     */
    public void setAccClassDesc(String AccClassDesc) {
        this.AccClassDesc = AccClassDesc;
    }

    /**
     * @return the IsActive
     */
    public int getIsActive() {
        return IsActive;
    }

    /**
     * @param IsActive the IsActive to set
     */
    public void setIsActive(int IsActive) {
        this.IsActive = IsActive;
    }

    /**
     * @return the IsDeleted
     */
    public int getIsDeleted() {
        return IsDeleted;
    }

    /**
     * @param IsDeleted the IsDeleted to set
     */
    public void setIsDeleted(int IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    /**
     * @return the AddDate
     */
    public Date getAddDate() {
        return AddDate;
    }

    /**
     * @param AddDate the AddDate to set
     */
    public void setAddDate(Date AddDate) {
        this.AddDate = AddDate;
    }

    /**
     * @return the AddBy
     */
    public int getAddBy() {
        return AddBy;
    }

    /**
     * @param AddBy the AddBy to set
     */
    public void setAddBy(int AddBy) {
        this.AddBy = AddBy;
    }

    /**
     * @return the LastEditDate
     */
    public Date getLastEditDate() {
        return LastEditDate;
    }

    /**
     * @param LastEditDate the LastEditDate to set
     */
    public void setLastEditDate(Date LastEditDate) {
        this.LastEditDate = LastEditDate;
    }

    /**
     * @return the LastEditBy
     */
    public int getLastEditBy() {
        return LastEditBy;
    }

    /**
     * @param LastEditBy the LastEditBy to set
     */
    public void setLastEditBy(int LastEditBy) {
        this.LastEditBy = LastEditBy;
    }
}
