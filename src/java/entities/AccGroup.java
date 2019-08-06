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
public class AccGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccGroupId;
    private int AccTypeId;
    private String GroupCode;
    private String AccGroupName;
    private String AccGroupDesc;
    private int OrderGroup;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;

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
     * @return the AccTypeId
     */
    public int getAccTypeId() {
        return AccTypeId;
    }

    /**
     * @param AccTypeId the AccTypeId to set
     */
    public void setAccTypeId(int AccTypeId) {
        this.AccTypeId = AccTypeId;
    }

    /**
     * @return the GroupCode
     */
    public String getGroupCode() {
        return GroupCode;
    }

    /**
     * @param GroupCode the GroupCode to set
     */
    public void setGroupCode(String GroupCode) {
        this.GroupCode = GroupCode;
    }

    /**
     * @return the AccGroupName
     */
    public String getAccGroupName() {
        return AccGroupName;
    }

    /**
     * @param AccGroupName the AccGroupName to set
     */
    public void setAccGroupName(String AccGroupName) {
        this.AccGroupName = AccGroupName;
    }

    /**
     * @return the AccGroupDesc
     */
    public String getAccGroupDesc() {
        return AccGroupDesc;
    }

    /**
     * @param AccGroupDesc the AccGroupDesc to set
     */
    public void setAccGroupDesc(String AccGroupDesc) {
        this.AccGroupDesc = AccGroupDesc;
    }

    /**
     * @return the OrderGroup
     */
    public int getOrderGroup() {
        return OrderGroup;
    }

    /**
     * @param OrderGroup the OrderGroup to set
     */
    public void setOrderGroup(int OrderGroup) {
        this.OrderGroup = OrderGroup;
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
