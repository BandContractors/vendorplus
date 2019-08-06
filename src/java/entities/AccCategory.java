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
public class AccCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccCategoryId;
    private int AccGroupId;
    private String CategoryCode;
    private String AccCategoryName;
    private String AccCategoryDesc;
    private int OrderCategory;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;

    /**
     * @return the AccCategoryId
     */
    public int getAccCategoryId() {
        return AccCategoryId;
    }

    /**
     * @param AccCategoryId the AccCategoryId to set
     */
    public void setAccCategoryId(int AccCategoryId) {
        this.AccCategoryId = AccCategoryId;
    }

    /**
     * @return the AccGroupId
     */
    public int getAccGroupId() {
        return AccGroupId;
    }

    /**
     * @param AccGroupId the AccGroupId to set
     */
    public void setAccGroupId(int AccGroupId) {
        this.AccGroupId = AccGroupId;
    }

    /**
     * @return the CategoryCode
     */
    public String getCategoryCode() {
        return CategoryCode;
    }

    /**
     * @param CategoryCode the CategoryCode to set
     */
    public void setCategoryCode(String CategoryCode) {
        this.CategoryCode = CategoryCode;
    }

    /**
     * @return the AccCategoryName
     */
    public String getAccCategoryName() {
        return AccCategoryName;
    }

    /**
     * @param AccCategoryName the AccCategoryName to set
     */
    public void setAccCategoryName(String AccCategoryName) {
        this.AccCategoryName = AccCategoryName;
    }

    /**
     * @return the AccCategoryDesc
     */
    public String getAccCategoryDesc() {
        return AccCategoryDesc;
    }

    /**
     * @param AccCategoryDesc the AccCategoryDesc to set
     */
    public void setAccCategoryDesc(String AccCategoryDesc) {
        this.AccCategoryDesc = AccCategoryDesc;
    }

    /**
     * @return the OrderCategory
     */
    public int getOrderCategory() {
        return OrderCategory;
    }

    /**
     * @param OrderCategory the OrderCategory to set
     */
    public void setOrderCategory(int OrderCategory) {
        this.OrderCategory = OrderCategory;
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
