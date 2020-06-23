/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

/**
 *
 * @author bajuna
 */

    public class PayWay {

    public PayWay() {
        this.orderNumber="";
        this.paymentAmount="";
        this.paymentMode="";
    }

    public PayWay(String paymentMode, String paymentAmount, String orderNumber) {
        this.paymentMode = paymentMode;
        this.paymentAmount = paymentAmount;
        this.orderNumber = orderNumber;
    }

        private String paymentMode;
        private String paymentAmount;
        private String orderNumber;

        // Getter Methods 
        public String getPaymentMode() {
            return paymentMode;
        }

        public String getPaymentAmount() {
            return paymentAmount;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        // Setter Methods 
        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public void setPaymentAmount(String paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }
    }