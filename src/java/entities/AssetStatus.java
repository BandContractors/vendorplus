package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class AssetStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    private int AssetStatusId;
    private String AssetStatusName;

    /**
     * @return the AssetStatusId
     */
    public int getAssetStatusId() {
        return AssetStatusId;
    }

    /**
     * @param AssetStatusId the AssetStatusId to set
     */
    public void setAssetStatusId(int AssetStatusId) {
        this.AssetStatusId = AssetStatusId;
    }

    /**
     * @return the AssetStatusName
     */
    public String getAssetStatusName() {
        return AssetStatusName;
    }

    /**
     * @param AssetStatusName the AssetStatusName to set
     */
    public void setAssetStatusName(String AssetStatusName) {
        this.AssetStatusName = AssetStatusName;
    }
}
