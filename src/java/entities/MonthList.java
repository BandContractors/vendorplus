package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MonthList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int MonthNo;
    private String MonthName;
    private String MonthYearName;

    /**
     * @return the MonthNo
     */
    public int getMonthNo() {
        return MonthNo;
    }

    /**
     * @param MonthNo the MonthNo to set
     */
    public void setMonthNo(int MonthNo) {
        this.MonthNo = MonthNo;
    }

    /**
     * @return the MonthName
     */
    public String getMonthName() {
        return MonthName;
    }

    /**
     * @param MonthName the MonthName to set
     */
    public void setMonthName(String MonthName) {
        this.MonthName = MonthName;
    }

    /**
     * @return the MonthYearName
     */
    public String getMonthYearName() {
        return MonthYearName;
    }

    /**
     * @param MonthYearName the MonthYearName to set
     */
    public void setMonthYearName(String MonthYearName) {
        this.MonthYearName = MonthYearName;
    }
    
}
