package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class SalaryDeduction implements Serializable {

    private static final long serialVersionUID = 1L;
    private int SalaryDeductionId;
    private long TransactorId;
    private String AccountCode;
    private double Perc;
    private double Amount;
    private String DeductionName;

    /**
     * @return the SalaryDeductionId
     */
    public int getSalaryDeductionId() {
        return SalaryDeductionId;
    }

    /**
     * @param SalaryDeductionId the SalaryDeductionId to set
     */
    public void setSalaryDeductionId(int SalaryDeductionId) {
        this.SalaryDeductionId = SalaryDeductionId;
    }

    /**
     * @return the TransactorId
     */
    public long getTransactorId() {
        return TransactorId;
    }

    /**
     * @param TransactorId the TransactorId to set
     */
    public void setTransactorId(long TransactorId) {
        this.TransactorId = TransactorId;
    }

    /**
     * @return the Perc
     */
    public double getPerc() {
        return Perc;
    }

    /**
     * @param Perc the Perc to set
     */
    public void setPerc(double Perc) {
        this.Perc = Perc;
    }

    /**
     * @return the Amount
     */
    public double getAmount() {
        return Amount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the DeductionName
     */
    public String getDeductionName() {
        return DeductionName;
    }

    /**
     * @param DeductionName the DeductionName to set
     */
    public void setDeductionName(String DeductionName) {
        this.DeductionName = DeductionName;
    }

    /**
     * @return the AccountCode
     */
    public String getAccountCode() {
        return AccountCode;
    }

    /**
     * @param AccountCode the AccountCode to set
     */
    public void setAccountCode(String AccountCode) {
        this.AccountCode = AccountCode;
    }

    
}
