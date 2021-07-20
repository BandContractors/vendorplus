package api_sm_bi;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class GetLoyaltyCardBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionType = "LOYALTY CARD";//LOYALTY CARD
    private String cardNumber;
    private String sectionCode;
    private String branchCode;
    private String businessCode;
    private String groupCode;

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
     * @return the cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber the cardNumber to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return the sectionCode
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * @param sectionCode the sectionCode to set
     */
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode the branchCode to set
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * @return the businessCode
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode the businessCode to set
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * @return the groupCode
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * @param groupCode the groupCode to set
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

}
