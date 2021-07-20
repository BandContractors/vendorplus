package api_sm_bi;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class LoyaltyCard implements Serializable {

    private static final long serialVersionUID = 1L;
    private String card_number;
    private String first_name;
    private String second_name;
    private String third_name;
    private String email;
    private String phone;
    private Date dob;
    private String currency_code;
    private double points_balance;

    public LoyaltyCard() {
        this.card_number = "";
        this.first_name = "";
        this.second_name = "";
        this.third_name = "";
        this.email = "";
        this.phone = "";
        this.dob = new Date();
        this.currency_code = "";
        this.points_balance = 0;
    }

    /**
     * @return the card_number
     */
    public String getCard_number() {
        return card_number;
    }

    /**
     * @param card_number the card_number to set
     */
    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the second_name
     */
    public String getSecond_name() {
        return second_name;
    }

    /**
     * @param second_name the second_name to set
     */
    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    /**
     * @return the third_name
     */
    public String getThird_name() {
        return third_name;
    }

    /**
     * @param third_name the third_name to set
     */
    public void setThird_name(String third_name) {
        this.third_name = third_name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * @return the currency_code
     */
    public String getCurrency_code() {
        return currency_code;
    }

    /**
     * @param currency_code the currency_code to set
     */
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    /**
     * @return the points_balance
     */
    public double getPoints_balance() {
        return points_balance;
    }

    /**
     * @param points_balance the points_balance to set
     */
    public void setPoints_balance(double points_balance) {
        this.points_balance = points_balance;
    }

}
