package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class ItemPurpose implements Serializable {

    private static final long serialVersionUID = 1L;
    private int ItemPurposeId;
    private String ItemPurposeName;

    /**
     * @return the ItemPurposeId
     */
    public int getItemPurposeId() {
        return ItemPurposeId;
    }

    /**
     * @param ItemPurposeId the ItemPurposeId to set
     */
    public void setItemPurposeId(int ItemPurposeId) {
        this.ItemPurposeId = ItemPurposeId;
    }

    /**
     * @return the ItemPurposeName
     */
    public String getItemPurposeName() {
        return ItemPurposeName;
    }

    /**
     * @param ItemPurposeName the ItemPurposeName to set
     */
    public void setItemPurposeName(String ItemPurposeName) {
        this.ItemPurposeName = ItemPurposeName;
    }
}
