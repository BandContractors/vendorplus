/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris;

import api_tax.efris.innerclasses.BasicInformation;
import api_tax.efris.innerclasses.BuyerDetails;
import api_tax.efris.innerclasses.Extend;
import api_tax.efris.innerclasses.SellerDetails;
import api_tax.efris.innerclasses.Summary;
import com.google.gson.Gson;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author bajuna
 */
public class TestInvoice {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {
            // TODO code application logic here
            Gson gson = new Gson();
            EFRISInvoice eFRISInvoice = new EFRISInvoice(new SellerDetails("", "", "", "", "", "", "", "", "", ""), new BasicInformation(), new BuyerDetails(), new Summary(), new Extend());
            String json = gson.toJson(eFRISInvoice);
            System.out.println(json);
            
            System.out.println(new ExtractKeys().AESPublicKey("1015612362","202001130955"));
            String privatekey=new ExtractKeys().PrivateKey("1015612362","202001130955","","");
            PrivateKey key=ExtractKeys.loadPrivateKey(privatekey);
            System.out.println(key.getAlgorithm());
            String AESpublickeystring=ExtractKeys.decrypt(new ExtractKeys().AESPublicKey("1015612362","202001130955"), key);
            String DecryptedContent=ExtractKeys.AESdecrypt("UylBKiL0RqXXrjv+oesuYuPZBaze+MhuDeRIH8KinA1olvsCBx4zSMtp+OzswqS0JKIIU0P537aM+OeFYAG8jBW2gs8oTHuffBfoTvYFWHl+OIhCzLDWofNUhLPV3WnYUe/WMAy52Xo+7zshPLPlvG6erwjYewiCb41a/s5AVXYaLvo99KAtgLfHJCl9eKz1qvlUiwhkX6aeD9jfT05htxc+P4FcMu/+dg7mY/Q4Ui5PLLsnGL7jpx4U4dIKmH2uIiEK315EyrcPl9uGs0sQHLudjLjxhgatrXviTeFwgWJfIJswQYgCmhRtbrlJZg0I8O0wZYMAYKjvmO7vLe9Pp+EuGaCpvyYaRyoSiwkd8MO7Yz4bQhj8rN259XWSUoMR7LFBGGvdhKzzAfVTmbl0tRBzjAKHmpPlyMhIb7UIn3vvME2cYtEeBNxhQGIS3Em1XNmnNCUlxlV85VI/fXsW5Xin2DOB0PAR1cvCdbYC3+J3VvpFNq27Oa3ivB7lC4yi29oRefcCZvw9kmg2/DT3f+x6XAZ0lkvpNbhDg25F1Nbua3iFNoT7kNfhd++SWML3rw3xWoeEojUX2cQpFAFXjw1yV1vEko5nRiChqlT9atWBrdABvbFl1qz21aUX44E+TPCNnIu5t7cDrHKBa5YF3/L6wFzFiTevlXH368IqtV3kjMhClJZW3wte8NTu27G3BXr3PlefsH8VwRXvhihkxTq6bStgAS28pcx5kL46uzQew/9+8R2qpTV79Meyv8KT5OUfk0Jb/p8ECPPt9hveo8glVaO7GR1/L/+Z0L6EkNzkFBRZI8Z+sIu6aBV7HRZgYU76KWicfMbzDy8khQoTTwIL+K1c8z7qoKWTICBLrXZIjGYXyWKgR+BnsfBAfC/Y48OpKoSgoZ9kq5XwV6D6hAPuqqabBP44B/VsmbRnpoN0yym4q7Y9oON4gPGLie158TZBiPNxSUpPhBRnavqtU9DUhV+QGpcqrg05yHU5Hpcto7LB8Xsc5lGeIkSDNBFF7oSl8J4Vr/g3MY6GVxWEXALpo75W1l06oSOM/PfLHawFbPickZLEazrtL4KLlO0R8xTmhNPbNTnthd4zsbozxQ17+xU6P9YS6IJ4gvVgJSd7Q6cCizmU4k2yM9hcqolO55FLxl30CD4/zXDcE3PRTTE9WReWHtnyuQf16uJ686JW1JK4lDUye7HLY079LSVqv+dfH0S8LBqSXLIbS9fwHKsrEKKEYeO3ayiUBoIqSwoQ0Ed5by5iyl0kFOElp8DfIu8/GaDW+C5W8qhOgi72UHT68VrEiAUkyXA4wvkvfUmvFQ3MkBPpdlqoYDU3RLJku2j+9zN8JeD/u0htdxUOpt5C/GTLuW9GWz6Z/LwuIDZJqdk3rme4TLYS837kQxbyAqm4L2KmgGP2s7QtnkT4GufQSby7nQP8k+BqQmNdmFUZS6aCavxnzFWTzXTqwciJsdzUXBsk1dnxWxBQJDvKzJikyoT6/5mwsNDNdQKFBJt7V14V087XkAkdhq+R+6QSk7KdMH/Bw/iXM1ApyADa/OxvIzlyql3G3E47UUAp/aFwBFuuvdSxonpx63c6KhFACOAA3vPa4CTOb3Pd5Q1CGCGLu2QOrM/s2VOfh4+PpacY35gnKCxKfXP8C0pTTwSn",  Base64.decodeBase64(AESpublickeystring));
            String encrptedcontent=ExtractKeys.AESencrypt("Test", Base64.decodeBase64(AESpublickeystring));
            //PublicKey publickey=ExtractKeys.getKey(AESpublickeystring);
            System.out.println(encrptedcontent);
            //System.out.println(publickey.getAlgorithm());
            
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(TestInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
