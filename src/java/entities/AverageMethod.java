package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class AverageMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    private int AverageMethodId;
    private String AverageMethodName;

    /**
     * @return the AverageMethodId
     */
    public int getAverageMethodId() {
        return AverageMethodId;
    }

    /**
     * @param AverageMethodId the AverageMethodId to set
     */
    public void setAverageMethodId(int AverageMethodId) {
        this.AverageMethodId = AverageMethodId;
    }

    /**
     * @return the AverageMethodName
     */
    public String getAverageMethodName() {
        return AverageMethodName;
    }

    /**
     * @param AverageMethodName the AverageMethodName to set
     */
    public void setAverageMethodName(String AverageMethodName) {
        this.AverageMethodName = AverageMethodName;
    }
}
