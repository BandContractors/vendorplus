package beans;

import entities.Contact_list;
import entities.EmailEntity;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import sessions.GeneralUserSetting;
import utilities.Security;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;

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
    static Logger LOGGER = Logger.getLogger(EmailEntityBean.class.getName());
    private String ActionMessage = null;
    private List<EmailEntity> EmailEntitys;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

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
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        this.ActionMessage = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage(ub.translateWordsInText(BaseName, "Not Allowed to Access this Function")));
        } else if (aEmailEntity.getSubject().length() <= 1) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage(ub.translateWordsInText(BaseName, "Enter Subject")));
        } else if (aEmailEntity.getMessage().length() <= 1) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage(ub.translateWordsInText(BaseName, "Enter Message")));
        } else if (aContact_list.getCategory().length() == 0 && aEmailEntity.getToEmail().length() == 0) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage(ub.translateWordsInText(BaseName, "Enter To Email")));
        } else if (aContact_list.getCategory().length() > 0 && aEmailEntity.getToEmail().length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage(ub.translateWordsInText(BaseName, "Either Select Category or Subcategory or Enter To Email Adreess but Not Both")));
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
            this.setActionMessage(ub.translateWordsInText(BaseName, "Emails Sent ##" + sent));
            FacesContext.getCurrentInstance().addMessage("Email Feedback", new FacesMessage(ub.translateWordsInText(BaseName, this.getActionMessage())));
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
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return counter;
    }

    public static void check(String host, String storeType, String user, String password) {
        try {

            // create properties
            Properties properties = new Properties();

            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "465");//993
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.trust", host);

            Session emailSession = Session.getDefaultInstance(properties);

            // create the imap store object and connect to the imap server
            javax.mail.Store store = emailSession.getStore("imaps");

            store.connect(host, user, password);

            // create the inbox object and open it
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                message.setFlag(Flag.SEEN, true);
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());

            }

            inbox.close(false);
            store.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//
//        String host = "smtp.googlemail.com";//imap.gmail.com
//        String mailStoreType = "imap";
//        String username = "twenceb@gmail.com";
//        String password = "jesusiloveyou123";
//        EmailEntityBean eb=new EmailEntityBean();
//        eb.check(host, mailStoreType, username, password);
//
//    }

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

    /**
     * @return the menuItemBean
     */
    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    /**
     * @param menuItemBean the menuItemBean to set
     */
    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }
}
