package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Subscription_freq_amount implements Serializable {

    private static final long serialVersionUID = 1L;
    private String frequency;
    private double amount_at_reg;
    private double amount_at_renewal;

    /**
     * @return the frequency
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * @return the amount_at_reg
     */
    public double getAmount_at_reg() {
        return amount_at_reg;
    }

    /**
     * @param amount_at_reg the amount_at_reg to set
     */
    public void setAmount_at_reg(double amount_at_reg) {
        this.amount_at_reg = amount_at_reg;
    }

    /**
     * @return the amount_at_renewal
     */
    public double getAmount_at_renewal() {
        return amount_at_renewal;
    }

    /**
     * @param amount_at_renewal the amount_at_renewal to set
     */
    public void setAmount_at_renewal(double amount_at_renewal) {
        this.amount_at_renewal = amount_at_renewal;
    }
}
