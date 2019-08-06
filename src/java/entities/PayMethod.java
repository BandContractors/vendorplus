package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class PayMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    private int PayMethodId;
    private String PayMethodName;
    private int DisplayOrder;
    private int IsDeleted;
    private int IsActive;
    private int IsDefault;

    /**
     * @return the PayMethodId
     */
    public int getPayMethodId() {
        return PayMethodId;
    }

    /**
     * @param PayMethodId the PayMethodId to set
     */
    public void setPayMethodId(int PayMethodId) {
        this.PayMethodId = PayMethodId;
    }

    /**
     * @return the PayMethodName
     */
    public String getPayMethodName() {
        return PayMethodName;
    }

    /**
     * @param PayMethodName the PayMethodName to set
     */
    public void setPayMethodName(String PayMethodName) {
        this.PayMethodName = PayMethodName;
    }

    /**
     * @return the DisplayOrder
     */
    public int getDisplayOrder() {
        return DisplayOrder;
    }

    /**
     * @param DisplayOrder the DisplayOrder to set
     */
    public void setDisplayOrder(int DisplayOrder) {
        this.DisplayOrder = DisplayOrder;
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
     * @return the IsDefault
     */
    public int getIsDefault() {
        return IsDefault;
    }

    /**
     * @param IsDefault the IsDefault to set
     */
    public void setIsDefault(int IsDefault) {
        this.IsDefault = IsDefault;
    }
}
