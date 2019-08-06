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

    public int sendEmail(String aHost, int aPort, boolean aSslFlag, String aUsername, String aPassword, String aFromAddress, String aToEmail, String aSubject, String aMessage) {
        int flag = 0;
        //mail.wingersoft.co.ug,587,false; smtp.googlemail.com,465,true
        final String HOST = aHost;
        final int PORT = aPort;//587
        final boolean SSL_FLAG = aSslFlag;//true or false
        String userName = aUsername;//sales@wingersoft.co.ug
        String password = aPassword;//pw
        String fromAddress = aFromAddress;//"Sales<sales@wingersoft.co.ug>"

        String toAddress = aToEmail;
        String subjectline = aSubject;
        String Message = aMessage;

        //System.out.println(toAddress);
        //System.out.println(subjectline);
        //System.out.println(Message);
        try {
            Email email = new SimpleEmail();
            email.setHostName(HOST);
            email.setSmtpPort(PORT);
            email.setAuthentication(userName, password);
            //email.setAuthenticator(new DefaultAuthenticator(userName, password));
            //email.setSSLOnConnect(SSL_FLAG);
            email.setStartTLSEnabled(SSL_FLAG);
            email.setFrom(fromAddress);
            email.setSubject(subjectline);
            email.setMsg(Message);
            email.addTo(toAddress);
            email.send();
            flag = 1;

        } catch (Exception ex) {
            flag = 0;
            System.out.println("sendEmail:" + ex.getMessage());
        }
        return flag;
    }

    public String sendEmail(EmailEntity aEmailEntity) {
        String msg = "Not processed";
        //mail.wingersoft.co.ug,587,false; smtp.googlemail.com,465,true
        final String HOST = aEmailEntity.getHost();
        final int PORT = aEmailEntity.getPort();//587
        final boolean SSL_FLAG = aEmailEntity.isSslFlag();//true or false
        String userName = aEmailEntity.getUsername();//sales@wingersoft.co.ug
        String password = aEmailEntity.getPassword();//pw
        String fromAddress = aEmailEntity.getFromAddress();//"Sales<sales@wingersoft.co.ug>"

        String toAddress = aEmailEntity.getToEmail();
        String subjectline = aEmailEntity.getSubject();
        String Message = aEmailEntity.getMessage();

        //System.out.println(toAddress);
        //System.out.println(subjectline);
        //System.out.println(Message);
        try {
            Email email = new SimpleEmail();
            email.setHostName(HOST);
            email.setSmtpPort(PORT);
            email.setAuthentication(userName, password);
            //email.setAuthenticator(new DefaultAuthenticator(userName, password));
            //email.setSSLOnConnect(SSL_FLAG);
            email.setStartTLSEnabled(SSL_FLAG);
            email.setFrom(fromAddress);
            email.setSubject(subjectline);
            //email.setMsg(Message);
            email.setContent(aEmailEntity.getMessage(), "text/html");
            email.addTo(toAddress);
            email.send();
            msg = "";

        } catch (Exception e) {
            msg = "Error occurred : " + e.getMessage();
        }
        return msg;
    }

    public String sendEmail(List<Contact_list> aContactList, EmailEntity aEmailEntity) {
        String msg = "Not processed";
        int Pass = 0;
        //mail.wingersoft.co.ug,587,false; smtp.googlemail.com,465,true
        final String HOST = aEmailEntity.getHost();
        final int PORT = aEmailEntity.getPort();//587
        final boolean SSL_FLAG = aEmailEntity.isSslFlag();//true or false
        String userName = aEmailEntity.getUsername();//sales@wingersoft.co.ug
        String password = aEmailEntity.getPassword();//pw
        String fromAddress = aEmailEntity.getFromAddress();//"Sales<sales@wingersoft.co.ug>"

        String toAddress = "";
        String subjectline = aEmailEntity.getSubject();
        String Message = aEmailEntity.getMessage();
        // form all emails in a comma separated string
        //StringBuilder sb = new StringBuilder();
        String EmailList = "";
        List<InternetAddress> EmailCollection = new ArrayList<>();
        try {
            Email email = new SimpleEmail();
            email.setHostName(HOST);
            email.setSmtpPort(PORT);
            email.setAuthentication(userName, password);
            email.setStartTLSEnabled(SSL_FLAG);
            email.setFrom(fromAddress);
            email.addReplyTo(fromAddress);
            email.setSubject(subjectline);
            email.setContent(aEmailEntity.getMessage(), "text/html");
            email.setBounceAddress(fromAddress);
            email.setSentDate(new CompanySetting().getCURRENT_SERVER_DATE());
            if (aContactList.size() == 0) {//Single
                toAddress = aEmailEntity.getToEmail();
                String[] EmailArray = toAddress.split(",");
                for (int j = 0; j < EmailArray.length; j++) {
                    EmailCollection.add(new InternetAddress(EmailArray[j]));
                }
                email.setTo(EmailCollection);
                try {
                    msg = email.send();
                    Pass = EmailArray.length;
                    msg = Integer.toString(Pass);
                } catch (Exception e) {
                    msg = "Error occurred : " + e.getMessage();
                    System.err.println(e.getMessage());
                }
            } else {//Bulk
                int n = 0;
                try {
                    n = aContactList.size();
                } catch (Exception e) {
                }
                for (int i = 0; i < n; i++) {
                    //Email 1
                    toAddress = "";
                    try {
                        try {
                            toAddress = aContactList.get(i).getEmail1();
                        } catch (Exception e) {
                        }
                        if (toAddress.length() > 1) {
                            EmailCollection.add(new InternetAddress(toAddress));
                        }
                    } catch (Exception e) {
                        System.err.println("1:" + e.getMessage());
                    }
                    //Email 2
                    toAddress = "";
                    try {
                        try {
                            toAddress = aContactList.get(i).getEmail2();
                        } catch (Exception e) {
                        }
                        if (toAddress.length() > 1) {
                            EmailCollection.add(new InternetAddress(toAddress));
                        }
                    } catch (Exception e) {
                        System.err.println("2:" + e.getMessage());
                    }
                }
                //send bulk email
                email.setBcc(EmailCollection);
                try {
                    msg = email.send();
                    Pass = EmailCollection.size();
                    msg = Integer.toString(Pass);
                } catch (Exception e) {
                    msg = "Error occurred : " + e.getMessage();
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            msg = "Error occurred : " + e.getMessage();
        }
        return msg;
    }

    public String sendEmail_Prev(String aCategory, String aSubcategory, EmailEntity aEmailEntity) {
        String msg = "Not processed";
        //mail.wingersoft.co.ug,587,false; smtp.googlemail.com,465,true
        final String HOST = aEmailEntity.getHost();
        final int PORT = aEmailEntity.getPort();//587
        final boolean SSL_FLAG = aEmailEntity.isSslFlag();//true or false
        String userName = aEmailEntity.getUsername();//sales@wingersoft.co.ug
        String password = aEmailEntity.getPassword();//pw
        String fromAddress = aEmailEntity.getFromAddress();//"Sales<sales@wingersoft.co.ug>"

        String toAddress = "";
        String subjectline = aEmailEntity.getSubject();
        String Message = aEmailEntity.getMessage();
        // form all emails in a comma separated string
        //StringBuilder sb = new StringBuilder();
        String EmailList = "";
        List<InternetAddress> EmailCollection = new ArrayList<>();
        try {
            Email email = new SimpleEmail();
            email.setHostName(HOST);
            email.setSmtpPort(PORT);
            email.setAuthentication(userName, password);
            email.setStartTLSEnabled(SSL_FLAG);
            email.setFrom(fromAddress);
            email.setSubject(subjectline);
            email.setContent(aEmailEntity.getMessage(), "text/html");
            email.setBounceAddress(fromAddress);
            email.setSentDate(new CompanySetting().getCURRENT_SERVER_DATE());
            if (aCategory.length() == 0 && aSubcategory.length() == 0) {//Single
                toAddress = aEmailEntity.getToEmail();
                String[] EmailArray = toAddress.split(",");
                for (int j = 0; j < EmailArray.length; j++) {
                    EmailCollection.add(new InternetAddress(EmailArray[j]));
                }
                email.setTo(EmailCollection);
                try {
                    msg = email.send();
                } catch (Exception e) {
                    msg = "Error occurred : " + e.getMessage();
                    System.err.println(e.getMessage());
                }
            } else {//Bulk
                List<Contact_list> cList;
                if (aSubcategory.length() == 0) {
                    cList = new Contact_listBean().getContact_listByCat(aCategory, 1);
                } else {
                    cList = new Contact_listBean().getContact_listByCatSubcat(aCategory, aSubcategory, 1);
                }
                int n = 0;
                try {
                    n = cList.size();
                } catch (Exception e) {
                }
                for (int i = 0; i < n; i++) {
                    //Email 1
                    toAddress = "";
                    try {
                        try {
                            toAddress = cList.get(i).getEmail1();
                        } catch (Exception e) {
                        }
                        if (toAddress.length() > 1) {
                            //email.addTo(toAddress);
                            EmailCollection.add(new InternetAddress(toAddress));
                        }
                    } catch (Exception e) {
                        System.err.println("1:" + e.getMessage());
                    }
                    //Email 2
                    toAddress = "";
                    try {
                        try {
                            toAddress = cList.get(i).getEmail2();
                        } catch (Exception e) {
                        }
                        if (toAddress.length() > 1) {
                            //email.addTo(toAddress);
                            EmailCollection.add(new InternetAddress(toAddress));
                        }
                    } catch (Exception e) {
                        System.err.println("2:" + e.getMessage());
                    }
                }
                //send bulk email
                email.setBcc(EmailCollection);
                try {
                    msg = email.send();
                } catch (Exception e) {
                    msg = "Error occurred : " + e.getMessage();
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            msg = "Error occurred : " + e.getMessage();
        }
        return msg;
    }

    public void validateSendEmail_Prev(Contact_list aContact_list, EmailEntity aEmailEntity) {
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
            //aEmailEntity.setMessage(aEmailEntity.getMessageHTML());
            this.setEmail(aEmailEntity);
            //String msg = this.sendEmail(aEmailEntity);
            String msg = this.sendEmail_Prev(aContact_list.getCategory(), aContact_list.getSubcategory(), aEmailEntity);
            FacesContext.getCurrentInstance().addMessage("Email Feedback", new FacesMessage(msg));
            //if (msg.length() > 0) {
            //    FacesContext.getCurrentInstance().addMessage("Error", new FacesMessage(msg));
            //} else {
            //    FacesContext.getCurrentInstance().addMessage("Send", new FacesMessage("Email sent successfully"));
            //}
        }
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
            int SendBatchSize = 20;
            try {
                SendBatchSize = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "SEND_BATCH_SIZE").getParameter_value());
            } catch (Exception e) {
            }
            List<Contact_list> cList;
            if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() == 0) {
                cList = new Contact_listBean().getContact_listByCat(aContact_list.getCategory(), 1);
            } else if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() > 0) {
                cList = new Contact_listBean().getContact_listByCatSubcat(aContact_list.getCategory(), aContact_list.getSubcategory(), 1);
            } else {
                cList = new ArrayList<>();
            }
            if (cList.size() == 0) {
                String msgBatch = this.sendEmail(cList, aEmailEntity);
                int PassBatch = Integer.parseInt(msgBatch);
                if (PassBatch > 0) {
                    Pass = Pass + PassBatch;
                } else {
                    msg = msg + msgBatch;
                }
            } else {
                int counter = 1;
                List<Contact_list> BatchList = new ArrayList<>();
                int n = cList.size();
                for (Contact_list cl : cList) {
                    BatchList.add(cl);
                    if (counter % SendBatchSize == 0 || counter == n) {
                        String msgBatch = this.sendEmail(BatchList, aEmailEntity);
                        int PassBatch = 0;
                        try {
                            PassBatch = Integer.parseInt(msgBatch);
                        } catch (Exception e) {
                        }
                        if (PassBatch > 0) {
                            Pass = Pass + PassBatch;
                        } else {
                            msg = msg + msgBatch;
                        }
                        System.out.println("counter at:" + counter + ", batch sent/msg:" + msgBatch);
                        BatchList = new ArrayList<>();
                    }
                    counter = counter + 1;
                }
            }
            this.setActionMessage("Sent:" + Pass + ", Message:" + msg + ", Email list:" + cList.size());
            FacesContext.getCurrentInstance().addMessage("Email Feedback", new FacesMessage(this.getActionMessage()));
        }
    }

    public void validateSendEmailBulk(Contact_list aContact_list, EmailEntity aEmailEntity) {
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
            int sent = this.sendEmailBulk(EmailsList, aEmailEntity, RecipientType, SendBatchSize);
            this.setActionMessage(sent + " Emails Sent");
            FacesContext.getCurrentInstance().addMessage("Email Feedback", new FacesMessage(this.getActionMessage()));
        }
    }

    public List<String> createEmailsStrList(Contact_list aContact_list, EmailEntity aEmailEntity) {
        List<String> EmailsStrList = new ArrayList<>();
        int SendBatchSize = 20;
        try {
            SendBatchSize = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("EMAIL", "SEND_BATCH_SIZE").getParameter_value());
        } catch (Exception e) {
        }
        List<Contact_list> cList;
        if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() == 0) {
            cList = new Contact_listBean().getContact_listByCat(aContact_list.getCategory(), 1);
        } else if (aContact_list.getCategory().length() > 0 && aContact_list.getSubcategory().length() > 0) {
            cList = new Contact_listBean().getContact_listByCatSubcat(aContact_list.getCategory(), aContact_list.getSubcategory(), 1);
        } else {
            cList = new ArrayList<>();
        }
        if (cList.isEmpty()) {
            EmailsStrList.add(aEmailEntity.getToEmail());
        } else {
            int counter = 1;
            String BatchStr = "";
            int n = cList.size();
            for (Contact_list cl : cList) {
                //get string of email1,email2 and add to the previous string until batch size is reached.
                String email1 = cl.getEmail1();
                String email2 = cl.getEmail2();
                String email1and2 = "";
                if (!email1.isEmpty()) {
                    email1and2 = email1;
                } else if (!email2.isEmpty()) {
                    if (!email1and2.isEmpty()) {
                        email1and2 = email1and2.concat(",").concat(email2);
                    } else {
                        email1and2 = email2;
                    }
                    email1and2 = email1;
                }
                if (BatchStr.isEmpty()) {
                    BatchStr = email1and2;
                } else {
                    BatchStr = BatchStr.concat(",").concat(email1and2);
                }
                //check if bacth size is reached to add the batch string to the list
                if (counter % SendBatchSize == 0 || counter == n) {
                    EmailsStrList.add(BatchStr);
                    BatchStr = "";
                }
                counter = counter + 1;
            }
        }
        return EmailsStrList;
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

    public void sendBulk(Properties props, List<Message> aMessages, String aFrom, String aPassword) {
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(aFrom, aPassword);
            }
        });
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            transport.connect();
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEntityBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            for (Message m : aMessages) {
                transport.sendMessage(m, m.getAllRecipients()); // time decreased to 2 second/message
            }
        } catch (MessagingException ex) {
            Logger.getLogger(EmailEntityBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                transport.close();
            } catch (MessagingException ex) {
                Logger.getLogger(EmailEntityBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int sendEmailBulk(List<String> aEmailList, EmailEntity aEmailEntity, String aRecipientType, int aBatchSize) {
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
