package utilities;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class ListOption implements Serializable {
   private static final long serialVersionUID = 1L;
   private String StrOption1;
   private String StrOption2;
   private int IntOption1;
   private int IntOption2;

    /**
     * @return the StrOption1
     */
    public String getStrOption1() {
        return StrOption1;
    }

    /**
     * @param StrOption1 the StrOption1 to set
     */
    public void setStrOption1(String StrOption1) {
        this.StrOption1 = StrOption1;
    }

    /**
     * @return the StrOption2
     */
    public String getStrOption2() {
        return StrOption2;
    }

    /**
     * @param StrOption2 the StrOption2 to set
     */
    public void setStrOption2(String StrOption2) {
        this.StrOption2 = StrOption2;
    }

    /**
     * @return the IntOption1
     */
    public int getIntOption1() {
        return IntOption1;
    }

    /**
     * @param IntOption1 the IntOption1 to set
     */
    public void setIntOption1(int IntOption1) {
        this.IntOption1 = IntOption1;
    }

    /**
     * @return the IntOption2
     */
    public int getIntOption2() {
        return IntOption2;
    }

    /**
     * @param IntOption2 the IntOption2 to set
     */
    public void setIntOption2(int IntOption2) {
        this.IntOption2 = IntOption2;
    }
   
}
