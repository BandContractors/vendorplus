package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class DepMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    private int DepMethodId;
    private String DepMethodName;
    private String DepMethodCode;
    private int IsActive;

    /**
     * @return the DepMethodId
     */
    public int getDepMethodId() {
        return DepMethodId;
    }

    /**
     * @param DepMethodId the DepMethodId to set
     */
    public void setDepMethodId(int DepMethodId) {
        this.DepMethodId = DepMethodId;
    }

    /**
     * @return the DepMethodName
     */
    public String getDepMethodName() {
        return DepMethodName;
    }

    /**
     * @param DepMethodName the DepMethodName to set
     */
    public void setDepMethodName(String DepMethodName) {
        this.DepMethodName = DepMethodName;
    }

    /**
     * @return the DepMethodCode
     */
    public String getDepMethodCode() {
        return DepMethodCode;
    }

    /**
     * @param DepMethodCode the DepMethodCode to set
     */
    public void setDepMethodCode(String DepMethodCode) {
        this.DepMethodCode = DepMethodCode;
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
}
