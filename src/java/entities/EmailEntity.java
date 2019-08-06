package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean
@SessionScoped
public class EmailEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String Host;
    private int Port;
    private boolean SslFlag;
    private String Username;
    private String Password;
    private String FromAddress;
    private String ToEmail;
    private String Subject;
    private String Message;
    private String MessageTXT;
    private String MessageHTML;

    /**
     * @return the Host
     */
    public String getHost() {
        return Host;
    }

    /**
     * @param Host the Host to set
     */
    public void setHost(String Host) {
        this.Host = Host;
    }

    /**
     * @return the Port
     */
    public int getPort() {
        return Port;
    }

    /**
     * @param Port the Port to set
     */
    public void setPort(int Port) {
        this.Port = Port;
    }

    /**
     * @return the SslFlag
     */
    public boolean isSslFlag() {
        return SslFlag;
    }

    /**
     * @param SslFlag the SslFlag to set
     */
    public void setSslFlag(boolean SslFlag) {
        this.SslFlag = SslFlag;
    }

    /**
     * @return the Username
     */
    public String getUsername() {
        return Username;
    }

    /**
     * @param Username the Username to set
     */
    public void setUsername(String Username) {
        this.Username = Username;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the FromAddress
     */
    public String getFromAddress() {
        return FromAddress;
    }

    /**
     * @param FromAddress the FromAddress to set
     */
    public void setFromAddress(String FromAddress) {
        this.FromAddress = FromAddress;
    }

    /**
     * @return the ToEmail
     */
    public String getToEmail() {
        return ToEmail;
    }

    /**
     * @param ToEmail the ToEmail to set
     */
    public void setToEmail(String ToEmail) {
        this.ToEmail = ToEmail;
    }

    /**
     * @return the Subject
     */
    public String getSubject() {
        return Subject;
    }

    /**
     * @param Subject the Subject to set
     */
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    /**
     * @return the Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message the Message to set
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return the MessageTXT
     */
    public String getMessageTXT() {
        return MessageTXT;
    }

    /**
     * @param MessageTXT the MessageTXT to set
     */
    public void setMessageTXT(String MessageTXT) {
        this.MessageTXT = MessageTXT;
    }

    /**
     * @return the MessageHTML
     */
    public String getMessageHTML() {
        return MessageHTML;
    }

    /**
     * @param MessageHTML the MessageHTML to set
     */
    public void setMessageHTML(String MessageHTML) {
        this.MessageHTML = MessageHTML;
    }
}
