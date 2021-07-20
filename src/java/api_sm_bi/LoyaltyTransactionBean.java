package api_sm_bi;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class LoyaltyTransactionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String transactionType = "LOYALTY";//LOYALTY
    private LoyaltyTransaction loyaltyTransaction;

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the loyaltyTransaction
     */
    public LoyaltyTransaction getLoyaltyTransaction() {
        return loyaltyTransaction;
    }

    /**
     * @param loyaltyTransaction the loyaltyTransaction to set
     */
    public void setLoyaltyTransaction(LoyaltyTransaction loyaltyTransaction) {
        this.loyaltyTransaction = loyaltyTransaction;
    }

}
