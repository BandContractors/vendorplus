package entities;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class TransactionType implements Serializable {

    private static final long serialVersionUID = 1L;
    private int TransactionTypeId;
    private String TransactionTypeName;
    private String TransactorLabel;
    private String TransactionNumberLabel;
    private String TransactionOutputLabel;
    private String BillTransactorLabel;
    private String TransactionRefLabel;
    private String TransactionDateLabel;
    private String TransactionUserLabel;
    private String IsTransactorMandatory ;
    private String IsTransactionUserMandatory;
    private String IsTransactionRefMandatory;
    private String IsAuthoriseUserMandatory;
    private String IsAuthoriseDateMandatory;
    private String IsDeliveryAddressMandatory;
    private String IsDeliveryDateMandatory;
    private String IsPayDueDateMandatory;
    private String IsExpiryDateMandatory;
    private String Description;
    private String GroupName;
    private String print_file_name1;
    private String print_file_name2;
    private int default_print_file;
    private String transaction_type_code;
    private String default_currency_code;
    private String trans_number_format;
    private String output_footer_message;
    private String default_term_condition;

    /**
     * @return the TransactionTypeId
     */
    public int getTransactionTypeId() {
        return TransactionTypeId;
    }

    /**
     * @param TransactionTypeId the TransactionTypeId to set
     */
    public void setTransactionTypeId(int TransactionTypeId) {
        this.TransactionTypeId = TransactionTypeId;
    }

    /**
     * @return the TransactionTypeName
     */
    public String getTransactionTypeName() {
        return TransactionTypeName;
    }

    /**
     * @param TransactionTypeName the TransactionTypeName to set
     */
    public void setTransactionTypeName(String TransactionTypeName) {
        this.TransactionTypeName = TransactionTypeName;
    }

    /**
     * @return the TransactorLabel
     */
    public String getTransactorLabel() {
        return TransactorLabel;
    }

    /**
     * @param TransactorLabel the TransactorLabel to set
     */
    public void setTransactorLabel(String TransactorLabel) {
        this.TransactorLabel = TransactorLabel;
    }

    /**
     * @return the TransactionNumberLabel
     */
    public String getTransactionNumberLabel() {
        return TransactionNumberLabel;
    }

    /**
     * @param TransactionNumberLabel the TransactionNumberLabel to set
     */
    public void setTransactionNumberLabel(String TransactionNumberLabel) {
        this.TransactionNumberLabel = TransactionNumberLabel;
    }

    /**
     * @return the TransactionOutputLabel
     */
    public String getTransactionOutputLabel() {
        return TransactionOutputLabel;
    }

    /**
     * @param TransactionOutputLabel the TransactionOutputLabel to set
     */
    public void setTransactionOutputLabel(String TransactionOutputLabel) {
        this.TransactionOutputLabel = TransactionOutputLabel;
    }

    /**
     * @return the BillTransactorLabel
     */
    public String getBillTransactorLabel() {
        return BillTransactorLabel;
    }

    /**
     * @param BillTransactorLabel the BillTransactorLabel to set
     */
    public void setBillTransactorLabel(String BillTransactorLabel) {
        this.BillTransactorLabel = BillTransactorLabel;
    }

    /**
     * @return the TransactionRefLabel
     */
    public String getTransactionRefLabel() {
        return TransactionRefLabel;
    }

    /**
     * @param TransactionRefLabel the TransactionRefLabel to set
     */
    public void setTransactionRefLabel(String TransactionRefLabel) {
        this.TransactionRefLabel = TransactionRefLabel;
    }

    /**
     * @return the TransactionDateLabel
     */
    public String getTransactionDateLabel() {
        return TransactionDateLabel;
    }

    /**
     * @param TransactionDateLabel the TransactionDateLabel to set
     */
    public void setTransactionDateLabel(String TransactionDateLabel) {
        this.TransactionDateLabel = TransactionDateLabel;
    }

    /**
     * @return the TransactionUserLabel
     */
    public String getTransactionUserLabel() {
        return TransactionUserLabel;
    }

    /**
     * @param TransactionUserLabel the TransactionUserLabel to set
     */
    public void setTransactionUserLabel(String TransactionUserLabel) {
        this.TransactionUserLabel = TransactionUserLabel;
    }

    /**
     * @return the IsTransactorMandatory
     */
    public String getIsTransactorMandatory() {
        return IsTransactorMandatory;
    }

    /**
     * @param IsTransactorMandatory the IsTransactorMandatory to set
     */
    public void setIsTransactorMandatory(String IsTransactorMandatory) {
        this.IsTransactorMandatory = IsTransactorMandatory;
    }

    /**
     * @return the IsTransactionUserMandatory
     */
    public String getIsTransactionUserMandatory() {
        return IsTransactionUserMandatory;
    }

    /**
     * @param IsTransactionUserMandatory the IsTransactionUserMandatory to set
     */
    public void setIsTransactionUserMandatory(String IsTransactionUserMandatory) {
        this.IsTransactionUserMandatory = IsTransactionUserMandatory;
    }

    /**
     * @return the IsTransactionRefMandatory
     */
    public String getIsTransactionRefMandatory() {
        return IsTransactionRefMandatory;
    }

    /**
     * @param IsTransactionRefMandatory the IsTransactionRefMandatory to set
     */
    public void setIsTransactionRefMandatory(String IsTransactionRefMandatory) {
        this.IsTransactionRefMandatory = IsTransactionRefMandatory;
    }

    /**
     * @return the IsAuthoriseUserMandatory
     */
    public String getIsAuthoriseUserMandatory() {
        return IsAuthoriseUserMandatory;
    }

    /**
     * @param IsAuthoriseUserMandatory the IsAuthoriseUserMandatory to set
     */
    public void setIsAuthoriseUserMandatory(String IsAuthoriseUserMandatory) {
        this.IsAuthoriseUserMandatory = IsAuthoriseUserMandatory;
    }

    /**
     * @return the IsAuthoriseDateMandatory
     */
    public String getIsAuthoriseDateMandatory() {
        return IsAuthoriseDateMandatory;
    }

    /**
     * @param IsAuthoriseDateMandatory the IsAuthoriseDateMandatory to set
     */
    public void setIsAuthoriseDateMandatory(String IsAuthoriseDateMandatory) {
        this.IsAuthoriseDateMandatory = IsAuthoriseDateMandatory;
    }

    /**
     * @return the IsDeliveryAddressMandatory
     */
    public String getIsDeliveryAddressMandatory() {
        return IsDeliveryAddressMandatory;
    }

    /**
     * @param IsDeliveryAddressMandatory the IsDeliveryAddressMandatory to set
     */
    public void setIsDeliveryAddressMandatory(String IsDeliveryAddressMandatory) {
        this.IsDeliveryAddressMandatory = IsDeliveryAddressMandatory;
    }

    /**
     * @return the IsDeliveryDateMandatory
     */
    public String getIsDeliveryDateMandatory() {
        return IsDeliveryDateMandatory;
    }

    /**
     * @param IsDeliveryDateMandatory the IsDeliveryDateMandatory to set
     */
    public void setIsDeliveryDateMandatory(String IsDeliveryDateMandatory) {
        this.IsDeliveryDateMandatory = IsDeliveryDateMandatory;
    }

    /**
     * @return the IsPayDueDateMandatory
     */
    public String getIsPayDueDateMandatory() {
        return IsPayDueDateMandatory;
    }

    /**
     * @param IsPayDueDateMandatory the IsPayDueDateMandatory to set
     */
    public void setIsPayDueDateMandatory(String IsPayDueDateMandatory) {
        this.IsPayDueDateMandatory = IsPayDueDateMandatory;
    }

    /**
     * @return the IsExpiryDateMandatory
     */
    public String getIsExpiryDateMandatory() {
        return IsExpiryDateMandatory;
    }

    /**
     * @param IsExpiryDateMandatory the IsExpiryDateMandatory to set
     */
    public void setIsExpiryDateMandatory(String IsExpiryDateMandatory) {
        this.IsExpiryDateMandatory = IsExpiryDateMandatory;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the GroupName
     */
    public String getGroupName() {
        return GroupName;
    }

    /**
     * @param GroupName the GroupName to set
     */
    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    /**
     * @return the print_file_name1
     */
    public String getPrint_file_name1() {
        return print_file_name1;
    }

    /**
     * @param print_file_name1 the print_file_name1 to set
     */
    public void setPrint_file_name1(String print_file_name1) {
        this.print_file_name1 = print_file_name1;
    }

    /**
     * @return the print_file_name2
     */
    public String getPrint_file_name2() {
        return print_file_name2;
    }

    /**
     * @param print_file_name2 the print_file_name2 to set
     */
    public void setPrint_file_name2(String print_file_name2) {
        this.print_file_name2 = print_file_name2;
    }

    /**
     * @return the default_print_file
     */
    public int getDefault_print_file() {
        return default_print_file;
    }

    /**
     * @param default_print_file the default_print_file to set
     */
    public void setDefault_print_file(int default_print_file) {
        this.default_print_file = default_print_file;
    }

    /**
     * @return the transaction_type_code
     */
    public String getTransaction_type_code() {
        return transaction_type_code;
    }

    /**
     * @param transaction_type_code the transaction_type_code to set
     */
    public void setTransaction_type_code(String transaction_type_code) {
        this.transaction_type_code = transaction_type_code;
    }

    /**
     * @return the default_currency_code
     */
    public String getDefault_currency_code() {
        return default_currency_code;
    }

    /**
     * @param default_currency_code the default_currency_code to set
     */
    public void setDefault_currency_code(String default_currency_code) {
        this.default_currency_code = default_currency_code;
    }

    /**
     * @return the trans_number_format
     */
    public String getTrans_number_format() {
        return trans_number_format;
    }

    /**
     * @param trans_number_format the trans_number_format to set
     */
    public void setTrans_number_format(String trans_number_format) {
        this.trans_number_format = trans_number_format;
    }

    /**
     * @return the output_footer_message
     */
    public String getOutput_footer_message() {
        return output_footer_message;
    }

    /**
     * @param output_footer_message the output_footer_message to set
     */
    public void setOutput_footer_message(String output_footer_message) {
        this.output_footer_message = output_footer_message;
    }

    /**
     * @return the default_term_condition
     */
    public String getDefault_term_condition() {
        return default_term_condition;
    }

    /**
     * @param default_term_condition the default_term_condition to set
     */
    public void setDefault_term_condition(String default_term_condition) {
        this.default_term_condition = default_term_condition;
    }

}
