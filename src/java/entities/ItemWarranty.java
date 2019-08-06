package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class ItemWarranty implements Serializable {

    private static final long serialVersionUID = 1L;
    private int ItemWarrantyId;
    private String ItemWarrantyName;
    private String ItemWarrantyDesc;
    private int DurationMonths;

    /**
     * @return the ItemWarrantyId
     */
    public int getItemWarrantyId() {
        return ItemWarrantyId;
    }

    /**
     * @param ItemWarrantyId the ItemWarrantyId to set
     */
    public void setItemWarrantyId(int ItemWarrantyId) {
        this.ItemWarrantyId = ItemWarrantyId;
    }

    /**
     * @return the ItemWarrantyName
     */
    public String getItemWarrantyName() {
        return ItemWarrantyName;
    }

    /**
     * @param ItemWarrantyName the ItemWarrantyName to set
     */
    public void setItemWarrantyName(String ItemWarrantyName) {
        this.ItemWarrantyName = ItemWarrantyName;
    }

    /**
     * @return the ItemWarrantyDesc
     */
    public String getItemWarrantyDesc() {
        return ItemWarrantyDesc;
    }

    /**
     * @param ItemWarrantyDesc the ItemWarrantyDesc to set
     */
    public void setItemWarrantyDesc(String ItemWarrantyDesc) {
        this.ItemWarrantyDesc = ItemWarrantyDesc;
    }

    /**
     * @return the DurationMonths
     */
    public int getDurationMonths() {
        return DurationMonths;
    }

    /**
     * @param DurationMonths the DurationMonths to set
     */
    public void setDurationMonths(int DurationMonths) {
        this.DurationMonths = DurationMonths;
    }
}
