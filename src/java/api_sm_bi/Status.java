package api_sm_bi;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;
    private int success = 0;
    private String description = "";

    /**
     * @return the success
     */
    public int getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(int success) {
        this.success = success;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
