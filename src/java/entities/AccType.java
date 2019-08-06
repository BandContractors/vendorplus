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
public class AccType implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccTypeId;
    private String TypeCode;
    private String AccTypeName;
    private String AccTypeDesc;
    private int OrderType;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;

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
     * @return the TypeCode
     */
    public String getTypeCode() {
        return TypeCode;
    }

    /**
     * @param TypeCode the TypeCode to set
     */
    public void setTypeCode(String TypeCode) {
        this.TypeCode = TypeCode;
    }

    /**
     * @return the AccTypeName
     */
    public String getAccTypeName() {
        return AccTypeName;
    }

    /**
     * @param AccTypeName the AccTypeName to set
     */
    public void setAccTypeName(String AccTypeName) {
        this.AccTypeName = AccTypeName;
    }

    /**
     * @return the AccTypeDesc
     */
    public String getAccTypeDesc() {
        return AccTypeDesc;
    }

    /**
     * @param AccTypeDesc the AccTypeDesc to set
     */
    public void setAccTypeDesc(String AccTypeDesc) {
        this.AccTypeDesc = AccTypeDesc;
    }

    /**
     * @return the OrderType
     */
    public int getOrderType() {
        return OrderType;
    }

    /**
     * @param OrderType the OrderType to set
     */
    public void setOrderType(int OrderType) {
        this.OrderType = OrderType;
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
