package beans;

import entities.CompanySetting;
import entities.Contact_list;
import entities.EmailEntity;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import sessions.GeneralUserSetting;
import utilities.Security;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "emailEntityBean")
@SessionScoped
public class EmailEntityBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private List<EmailEntity> EmailEntitys;

    public void setEmail(EmailEntity aEmailEntity) {
        String x1 = "";
        try {
            x1 = new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "HOST").getParameter_value();
        } catch (Exception e) {
        }
        aEmailEntity.setHost(x1);
        int x2 = 0;
        try {
            x2 = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "PORT").getParameter_value());
        } catch (Exception e) {
        }
        aEmailEntity.setPort(x2);
        boolean x3 = false;
        try {
            if (new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "SSL_FLAG").getParameter_value().equals("1")) {
                x3 = true;
            } else {
                x3 = false;
            }
        } catch (Exception e) {
        }
        aEmailEntity.setSslFlag(x3);
        String x4 = "";
        try {
            x4 = new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "USERNAME").getParameter_value();
        } catch (Exception e) {
        }
        aEmailEntity.setUsername(x4);
        String x5 = "";
        try {
            x5 = new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "PASSWORD").getParameter_value();
            x5 = Security.Decrypt(x5);
        } catch (Exception e) {
        }
        aEmailEntity.setPassword(x5);
        String x6 = "";
        try {
            x6 = new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "FROM_ADDRESS").getParameter_value();
        } catch (Exception e) {
        }
        aEmailEntity.setFromAddress(x6);
    }

    public int countList(Contact_list aContact_list) {
        int n = 0;
        try {
            List<Contact_list> cList;
            //System.out.println("C:" + aContact_list.getCategory() + ", SC:" + aContact_list.getSubcategory());
            if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() == 0) {
                n = new Contact_listBean().getContact_listByCat(aContact_list.getCategory(), 0).size();
            } else if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() > 0) {
                n = new Contact_listBean().getContact_listByCatSubcat(aContact_list.getCategory(), aContact_list.getSubcategory(), 0).size();
            } else {
                return 0;
            }
        } catch (Exception e) {
            n = 0;
        }
        return n;
    }

    public void validateSendEmail(Contact_list aContact_list, EmailEntity aEmailEntity) {
        this.ActionMessage = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage("YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR..."));
        } else if (aEmailEntity.getSubject().length() <= 1) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage("Enter Subject..."));
        } else if (aEmailEntity.getMessage().length() <= 1) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage("Enter Message..."));
        } else if (aContact_list.getCategory().length() == 0 && aEmailEntity.getToEmail().length() == 0) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage("Enter To Email..."));
        } else if (aContact_list.getCategory().length() > 0 && aEmailEntity.getToEmail().length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage("Either you select category>subcategory OR enter to email adreess BUT not both..."));
        } else {
            this.setEmail(aEmailEntity);
            String msg = "";
            int Pass = 0;

            List<String> EmailsList = this.createEmailsList(aContact_list, aEmailEntity);
            int SendBatchSize = 10;
            try {
                SendBatchSize = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "SEND_BATCH_SIZE").getParameter_value());
            } catch (Exception e) {
            }
            String RecipientType = "";
            if (aEmailEntity.getToEmail().isEmpty()) {
                RecipientType = "BCC";
            } else {
                RecipientType = "TO";
            }
            int sent = this.sendEmail(EmailsList, aEmailEntity, RecipientType, SendBatchSize);
            this.setActionMessage(sent + " Emails Sent");
            FacesContext.getCurrentInstance().addMessage("Email Feedback", new FacesMessage(this.getActionMessage()));
        }
    }

    public int validateSendEmailBackground(EmailEntity aEmailEntity) {
        int sent = 0;
        int validation = 1;

        if (aEmailEntity.getSubject().length() <= 1) {
            validation = 0;
        } else if (aEmailEntity.getMessage().length() <= 1) {
            validation = 0;
        } else if (aEmailEntity.getToEmail().length() == 0) {
            validation = 0;
        }

        if (validation == 1) {
            this.setEmail(aEmailEntity);
            List<String> EmailsList = this.createEmailsList(aEmailEntity);
            int SendBatchSize = 10;
            try {
                SendBatchSize = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "SEND_BATCH_SIZE").getParameter_value());
            } catch (Exception e) {
            }
            String RecipientType = "";
            if (aEmailEntity.getToEmail().isEmpty()) {
                RecipientType = "BCC";
            } else {
                RecipientType = "TO";
            }
            sent = this.sendEmail(EmailsList, aEmailEntity, RecipientType, SendBatchSize);
        }
        return sent;
    }

    public List<String> createEmailsList(Contact_list aContact_list, EmailEntity aEmailEntity) {
        List<String> EmailsList = new ArrayList<>();
        List<Contact_list> cList;
        if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() == 0) {
            cList = new Contact_listBean().getContact_listByCat(aContact_list.getCategory(), 1);
        } else if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() > 0) {
            cList = new Contact_listBean().getContact_listByCatSubcat(aContact_list.getCategory(), aContact_list.getSubcategory(), 1);
        } else {
            cList = new ArrayList<>();
        }
        if (cList.isEmpty()) {
            EmailsList = Arrays.asList(aEmailEntity.getToEmail().split(","));
        } else {
            for (Contact_list cl : cList) {
                String email1 = cl.getEmail1();
                String email2 = cl.getEmail2();
                if (!email1.isEmpty()) {
                    EmailsList.add(email1);
                }
                if (!email2.isEmpty()) {
                    EmailsList.add(email2);
                }
            }
        }
        return EmailsList;
    }

    public List<String> createEmailsList(EmailEntity aEmailEntity) {
        List<String> EmailsList = new ArrayList<>();
        try {
            EmailsList = Arrays.asList(aEmailEntity.getToEmail().split(","));
        } catch (Exception e) {
            //do nothing
        }
        return EmailsList;
    }

    public int sendEmail(List<String> aEmailList, EmailEntity aEmailEntity, String aRecipientType, int aBatchSize) {
        int counter = 0;
        // from email address
        final String username = aEmailEntity.getUsername();
        // make sure you put your correct password
        final String password = aEmailEntity.getPassword();
        // smtp email server
        final String smtpHost = aEmailEntity.getHost();

        // We will put some properties for smtp configurations
        Properties props = new Properties();

        // do not change - start
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.host", smtpHost);
        // props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        //new
        props.put("mail.smtp.port", aEmailEntity.getPort());
        props.put("mail.smtp.ssl.enable", aEmailEntity.isSslFlag());
        // do not change - end

        Transport transport = null;
        String emails = "";
        try {
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            transport = session.getTransport("smtp");
            transport.connect();

            // we create new message
            Message message = new MimeMessage(session);
            // set the from 'email address'
            //message.setFrom(new InternetAddress(username));
            message.setFrom(new InternetAddress(aEmailEntity.getFromAddress()));
            // set email subject
            message.setSubject(aEmailEntity.getSubject());
            // set email message
            // this will send html mail to the intended recipients
            // if you do not want to send html mail then you do not need to wrap the message inside html tags
            String content = "<html>\n<body>\n";
            content += aEmailEntity.getMessage() + "\n";
            content += "\n";
            content += "</body>\n</html>";
            message.setContent(content, "text/html");
            int n = aEmailList.size();
            for (String email : aEmailList) {
                counter = counter + 1;
                if (!emails.isEmpty()) {
                    emails = emails.concat(",").concat(email);
                } else {
                    emails = email;
                }
                if (counter % aBatchSize == 0 || counter == n) {
                    //send
                    // set 'to email address'
                    if (aRecipientType.equals("TO")) {
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emails));
                    } else if (aRecipientType.equals("CC")) {
                        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emails));
                    } else {
                        message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(emails));
                    }
                    transport.sendMessage(message, message.getAllRecipients());
                    //reset
                    emails = "";
                }
            }
        } catch (Exception ex) {
            System.out.println("Email sending failed");
            Logger.getLogger(EmailEntityBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                transport.close();
            } catch (MessagingException ex) {
                Logger.getLogger(EmailEntityBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return counter;
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param ActionMessage the ActionMessage to set
     */
    public void setActionMessage(String ActionMessage) {
        this.ActionMessage = ActionMessage;
    }
}
