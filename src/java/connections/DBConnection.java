package connections;

import entities.CompanySetting;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import utilities.Security;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class DBConnection implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    private static String MySQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String MySQL_DB_HOST_IP_OR_NAME = "";
    private static String MySQL_DB_NAME = "";
    private static String MySQL_DB_URL = "";
    private static String MySQL_USER = "";
    private static String MySQL_PASSWORD = "";
    private static Connection MySQL_Conn;

    private static String PASSWORDS_ARE_ENCRYPTED;
    private static String BRANCH_PASSWORD_CODE;

    public static void readConnectionConfigurations(String aConfigFile) throws FileNotFoundException {
        ResourceBundle properties = ResourceBundle.getBundle(aConfigFile);

        DBConnection.PASSWORDS_ARE_ENCRYPTED = properties.getString("passwords_are_encrypted");

        DBConnection.MySQL_DB_HOST_IP_OR_NAME = properties.getString("branch_host");
        DBConnection.MySQL_DB_NAME = properties.getString("branch_database");
        DBConnection.MySQL_USER = properties.getString("branch_user");
        if (DBConnection.PASSWORDS_ARE_ENCRYPTED.equals("Yes")) {
            DBConnection.BRANCH_PASSWORD_CODE = properties.getString("branch_password");
            DBConnection.MySQL_PASSWORD = Security.Decrypt(DBConnection.BRANCH_PASSWORD_CODE);
        } else {
            DBConnection.BRANCH_PASSWORD_CODE = Security.Encrypt(properties.getString("branch_password"));
            DBConnection.MySQL_PASSWORD = properties.getString("branch_password");
        }

        //refresh co stgs
        CompanySetting.RefreshStaticCompanySettings();
    }

    public static void readConnectionConfigurationsClient(String aConfigFile) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(aConfigFile);
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
        }
        try {
            in.close();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
        }

        DBConnection.PASSWORDS_ARE_ENCRYPTED = properties.getProperty("passwords_are_encrypted", "passwords_are_encrypted");

        DBConnection.MySQL_DB_HOST_IP_OR_NAME = properties.getProperty("branch_host", "branch_host");
        DBConnection.MySQL_DB_NAME = properties.getProperty("branch_database", "branch_database");
        DBConnection.MySQL_USER = properties.getProperty("branch_user", "branch_user");
        DBConnection.MySQL_PASSWORD = properties.getProperty("branch_password", "branch_password");
        if (DBConnection.PASSWORDS_ARE_ENCRYPTED.equals("Yes")) {
            DBConnection.BRANCH_PASSWORD_CODE = properties.getProperty("branch_password", "branch_password");
            DBConnection.MySQL_PASSWORD = Security.Decrypt(DBConnection.BRANCH_PASSWORD_CODE);
        } else {
            DBConnection.BRANCH_PASSWORD_CODE = Security.Encrypt(properties.getProperty("branch_password", "branch_password"));
            DBConnection.MySQL_PASSWORD = properties.getProperty("branch_password", "branch_password");
        }

        //refresh co stgs
        CompanySetting.RefreshStaticCompanySettings();
    }

    public static Connection getMySQLConnection_Orig() {
        try {
            Class.forName(MySQL_JDBC_DRIVER);
            DBConnection.MySQL_DB_URL = "jdbc:mysql://" + DBConnection.MySQL_DB_HOST_IP_OR_NAME + "/" + DBConnection.MySQL_DB_NAME;
            MySQL_Conn = DriverManager.getConnection(MySQL_DB_URL, MySQL_USER, MySQL_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
        }
        return MySQL_Conn;
    }

    public static Connection getMySQLConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/BranchPool");
            MySQL_Conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            //LOGGER.log(Level.ERROR, e);
        }
        return MySQL_Conn;
    }

    /**
     * @return the PASSWORDS_ARE_ENCRYPTED
     */
    public static String getPASSWORDS_ARE_ENCRYPTED() {
        return PASSWORDS_ARE_ENCRYPTED;
    }

    /**
     * @param aPASSWORDS_ARE_ENCRYPTED the PASSWORDS_ARE_ENCRYPTED to set
     */
    public static void setPASSWORDS_ARE_ENCRYPTED(String aPASSWORDS_ARE_ENCRYPTED) {
        PASSWORDS_ARE_ENCRYPTED = aPASSWORDS_ARE_ENCRYPTED;
    }

    /**
     * @return the BRANCH_PASSWORD_CODE
     */
    public static String getBRANCH_PASSWORD_CODE() {
        return BRANCH_PASSWORD_CODE;
    }

    /**
     * @param aBRANCH_PASSWORD_CODE the BRANCH_PASSWORD_CODE to set
     */
    public static void setBRANCH_PASSWORD_CODE(String aBRANCH_PASSWORD_CODE) {
        BRANCH_PASSWORD_CODE = aBRANCH_PASSWORD_CODE;
    }

    public String isMySQLConnectionAvailable() {
        try {
            //Class.forName(MySQL_JDBC_DRIVER);
            //DBConnection.MySQL_DB_URL = "jdbc:mysql://" + DBConnection.MySQL_DB_HOST_IP_OR_NAME + "/" + DBConnection.MySQL_DB_NAME;
            //DriverManager.getConnection(MySQL_DB_URL, MySQL_USER, MySQL_PASSWORD);
            return "ON";
        } catch (Exception e) {
            return "OFF";
        }
    }
}
