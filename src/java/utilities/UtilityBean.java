package utilities;

import beans.AccCurrencyBean;
import beans.Alert_generalBean;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Item;
import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.VARCHAR;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.*;
import sessions.GeneralUserSetting;

@ManagedBean
@SessionScoped
public class UtilityBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pattern pattern;
    private Matcher matcher;
    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public boolean isTime24Hour(String time) {
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
        matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public static String combineTwoStr(String aStr1, String aStr2, int aBracket) {
        String CombStr = "";
        if (null == aStr1) {
            aStr1 = "";
        }
        if (null == aStr2) {
            aStr2 = "";
        }
        if (aBracket == 1) {
            if (aStr1.length() > 0) {
                CombStr = aStr2 + " (" + aStr1 + ")";
            } else {
                CombStr = aStr2;
            }
        } else if (aBracket == 2) {
            if (aStr2.length() > 0) {
                CombStr = aStr1 + " (" + aStr2 + ")";
            } else {
                CombStr = aStr1;
            }
        } else {
            CombStr = "";
        }
        return CombStr;
    }

    public String returnYesNoString(int aYesNo) {
        if (aYesNo == 0) {
            return "N";
        } else if (aYesNo == 1) {
            return "Y";
        } else {
            return "";
        }
    }

    public long getNewTableColumnSeqNumber(String aTableName, String aColumnName) {
        String sql = "{call sp_get_new_id(?,?,?)}";
        long NewId = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, aTableName);
            cs.setString(2, aColumnName);
            cs.registerOutParameter("out_new_id", VARCHAR);
            cs.executeUpdate();
            NewId = cs.getLong(3);
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            NewId = 0;
        }
        return NewId;
    }

    public static double daysBetween(Date d1, Date d2) {
        return (double) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24)) + 1;
    }

    public static double weeksBetween(Date d1, Date d2) {
        double daysbtn = daysBetween(d1, d2);
        return (double) Math.ceil(daysbtn / 7);
    }

    public static double weeksBetweenReturn(Date d1, Date d2) {
        double daysbtnr = 0;
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        cal1.setTime(d1);
        cal2.setTime(d2);
        //if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.DAY_OF_WEEK) == cal2.get(Calendar.DAY_OF_WEEK)) {
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
            daysbtnr = 0;
        } else {
            double daysbtn = daysBetween(d1, d2);
            daysbtnr = (double) Math.ceil(daysbtn / 7);
        }
        return daysbtnr;
    }

    public Date AddDays(Date aDate, int aDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, aDays);
        return cal.getTime();
    }

    public String formatDate(Date aDate) {
        String outs = "";
        try {
            Format formatter = new SimpleDateFormat(new CompanySetting().getEDateFormat());
            outs = formatter.format(aDate);
        } catch (Exception e) {
        }
        return outs;
    }

    public int isDatesEqual(Date aDate1, Date aDate2) {
        int outs = 0;
        try {
            Format formatter = new SimpleDateFormat(new CompanySetting().getEDateFormat());
            if (formatter.format(aDate1).equals(formatter.format(aDate2))) {
                outs = 1;
            }
        } catch (Exception e) {
        }
        return outs;
    }

    /*
     This method converts the date into the storae format of the database which in this case is MySQL.
     */
    public String formatDateServer(Date aDate) {
        String outs = "";
        try {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            outs = formatter.format(aDate);
        } catch (Exception e) {
        }
        return outs;
    }

    public String formatDateTimeServer(Date aDate) {
        String outs = "";
        try {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            outs = formatter.format(aDate);
        } catch (Exception e) {
        }
        return outs;
    }

    public String formatDateTime(Date aDate) {
        String outs = "";
        try {
            Format formatter = new SimpleDateFormat(new CompanySetting().getEDateFormat() + " HH:mm");
            outs = formatter.format(aDate);
        } catch (Exception e) {
        }
        return outs;
    }

    public String convertIntAsString(int aInt) {
        String aStr;
        try {
            aStr = Integer.toString(aInt);
        } catch (Exception e) {
            aStr = "";
        }
        return aStr;
    }

    public Date convertDateUserToDbTz(Date aFromDate) {
        Date ToDate = null;
        String sql = "{call sp_convert_timezone(?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setTimestamp("in_from_date", new java.sql.Timestamp(aFromDate.getTime()));
            cs.setString("in_from_timezone", CompanySetting.getTimeZone());
            cs.setString("in_to_timezone", CompanySetting.getTimeZoneDb());
            cs.registerOutParameter("out_to_date", VARCHAR);
            cs.executeQuery();
            ToDate = new Date(cs.getTimestamp("out_to_date").getTime());
            //System.out.println("FFFF:" + aFromDate.toString());
            //System.out.println("TTTT:" + ToDate.toString());
            //System.out.println("TTSQL:" + new java.sql.Timestamp(ToDate.getTime()).toString());
        } catch (SQLException sqe) {
            ToDate = null;
        }
        return ToDate;
    }

    public int countIntegers(String input) {
        int count = 0;
        boolean isPreviousDigit = false;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                if (isPreviousDigit || count == 0) {
                    count = count + 1;
                    isPreviousDigit = true;
                }
            } else {
                count = 0;
                isPreviousDigit = false;
            }
        }
        return count;
    }

    public String formatDoubleToString(double aAmount) {
        String aString = "";
        //DecimalFormat myFormatter = new DecimalFormat("###,###.###;(###,###.###)");
        //DecimalFormat myFormatter = new DecimalFormat("###,###.###");
        if (aAmount >= 0) {
            aString = this.formatNumber("###,###.###", aAmount) + "";
        } else if (aAmount < 0) {
            aString = this.formatNumber("###,###.###", aAmount) + "";
        }
        return aString;
    }

    public String formatDoubleToStringHide(double aAmount, int aHideResult) {
        String aString = "";
        //DecimalFormat myFormatter = new DecimalFormat("###,###.###;(###,###.###)");
        //DecimalFormat myFormatter = new DecimalFormat("###,###.###");
        if (aAmount >= 0) {
            aString = this.formatNumber("###,###.###", aAmount) + "";
        } else if (aAmount < 0) {
            aString = this.formatNumber("###,###.###", aAmount) + "";
        }
        if (aHideResult == 0) {
            return aString;
        } else {
            return "";
        }
    }

    public String formatDoubleToString(double aAmount, String aCurrencyCode) {
        String aString = "";
        String aPattern = "";
        aPattern = new AccCurrencyBean().getNumberFormatByCurrency(aCurrencyCode);
        if (aPattern.length() == 0) {
            aPattern = "###,###.###";
        }
        if (aAmount >= 0) {
            aString = this.formatNumber(aPattern, aAmount) + "";
        } else if (aAmount < 0) {
            aString = this.formatNumber(aPattern, aAmount) + "";
        }
        return aString;
    }

    public String formatDoubleToStringPlain(double aAmount, String aCurrencyCode) {
        String aString = "";
        String aPattern = "";
        aPattern = new AccCurrencyBean().getNumberFormatByCurrencyPlain(aCurrencyCode);
        if (aPattern.length() == 0) {
            aPattern = "###.###";
        }
        if (aAmount >= 0) {
            aString = this.formatNumber(aPattern, aAmount) + "";
        } else if (aAmount < 0) {
            aString = this.formatNumber(aPattern, aAmount) + "";
        }
        return aString;
    }

    public boolean isXGreaterThanY(int aX, int aY) {
        boolean aR = false;
        try {
            if (aX > aY) {
                aR = true;
            } else {
                aR = false;
            }
        } catch (Exception e) {

        }
        return aR;
    }

    public String formatNumber(String aPattern, Double aNumber) {
        String format = "";
        try {
            String pattern = aPattern;
            String language_code = new GeneralUserSetting().getLOCALE_LANGUAGE_CODE();
            String country_code = new GeneralUserSetting().getLOCALE_COUNTRY_CODE();
            Locale locale = new Locale(language_code, country_code);
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
            decimalFormat.applyPattern(pattern);
            format = decimalFormat.format(aNumber);
        } catch (Exception e) {
        }
        return format;
    }

    public String formatNumber(Double aNumber) {
        String format = "";
        try {
            String pattern = "###,###.###";
            String language_code = new GeneralUserSetting().getLOCALE_LANGUAGE_CODE();
            String country_code = new GeneralUserSetting().getLOCALE_COUNTRY_CODE();
            Locale locale = new Locale(language_code, country_code);
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
            decimalFormat.applyPattern(pattern);
            format = decimalFormat.format(aNumber);
        } catch (Exception e) {
        }
        return format;
    }

    public String convertLocaleToString() {
        String locale = "";
        try {
            locale = new GeneralUserSetting().getLOCALE_COUNT_LANG_CODE();
        } catch (Exception e) {
        }
        return locale;
    }

    public String setColorByValueFromZero(double aValue, String aCondition, String aTrueColor, String aFalseColor) {
        if (aCondition.equals("greater")) {
            if (aValue > 0) {
                return aTrueColor;
            } else {
                return aFalseColor;
            }
        } else if (aCondition.equals("Subtract")) {
            if (aValue > 0) {
                return aTrueColor;
            } else {
                return aFalseColor;
            }
        } else {
            return aFalseColor;
        }
    }

    public String getClassRedAmberGreenOrder(int aProcessed, int bPaid, int cCancelled) {
        String classname = "";
        if (aProcessed == 1 && bPaid == 1) {
            if (cCancelled == 0) {
                classname = "green";
            } else {
                classname = "green_c";
            }
        } else if ((aProcessed == 1 && bPaid == 0) || (aProcessed == 0 && bPaid == 1)) {
            if (cCancelled == 0) {
                classname = "amber";
            } else {
                classname = "amber_c";
            }
        } else if (aProcessed == 0 && bPaid == 0) {
            if (cCancelled == 0) {
                classname = "red";
            } else {
                classname = "red_c";
            }
        }
        System.out.println("Process: " + aProcessed + ", Paid: " + bPaid + ", Cancelled:" + cCancelled + " == " + classname);
        return classname;
    }

    public String getColorRedAmberGreenOrder(int aProcessed, int bPaid, int cCancelled) {
        String colorname = "";
        if (aProcessed == 1 && bPaid == 1) {
            colorname = "#238823";
        } else if ((aProcessed == 1 && bPaid == 0) || (aProcessed == 0 && bPaid == 1)) {
            colorname = "#ffbf00";
        } else if (aProcessed == 0 && bPaid == 0) {
            colorname = "#d2222d";
        }
        System.out.println("Process: " + aProcessed + ", Paid: " + bPaid + ", Cancelled:" + cCancelled + " == " + colorname);
        return colorname;
    }

    public String returnOneString(String aString1, String aString2, int aPriority) {
        //aPriority:1 for str1, 2 for str2 and 0 for any
        if (aPriority == 1) {
            if (aString1.length() > 0) {
                return aString1;
            } else {
                return aString2;
            }
        } else if (aPriority == 2) {
            if (aString2.length() > 0) {
                return aString2;
            } else {
                return aString1;
            }
        } else {
            return "";
        }
    }

    public String getItemImageAddress(Item item) {
        String ImgBaseURL = "";
        String ImageName = "";
        String ItemImageAddress = "";
        if (null == item.getItemImgUrl() || item.getItemImgUrl().length() == 0) {
            ImageName = "NoImage.png";
            ItemImageAddress = ImageName;
        } else {
            ImgBaseURL = new GeneralUserSetting().getITEM_IMAGE_BASE_URL();
            ImageName = item.getItemImgUrl();
            ItemImageAddress = ImgBaseURL + "/" + ImageName;
        }
        return ItemImageAddress;
    }

    public String convertMonthNoToName(int aMonthNumber, int aShort) {
        String ms = "";
        if (aMonthNumber == 1) {
            if (aShort == 1) {
                ms = "Jan";
            } else {
                ms = "January";
            }
        } else if (aMonthNumber == 2) {
            if (aShort == 1) {
                ms = "Feb";
            } else {
                ms = "Febuary";
            }
        } else if (aMonthNumber == 3) {
            if (aShort == 1) {
                ms = "Mar";
            } else {
                ms = "March";
            }
        } else if (aMonthNumber == 4) {
            if (aShort == 1) {
                ms = "Apr";
            } else {
                ms = "April";
            }
        } else if (aMonthNumber == 5) {
            if (aShort == 1) {
                ms = "May";
            } else {
                ms = "May";
            }
        } else if (aMonthNumber == 6) {
            if (aShort == 1) {
                ms = "Jun";
            } else {
                ms = "June";
            }
        } else if (aMonthNumber == 7) {
            if (aShort == 1) {
                ms = "Jul";
            } else {
                ms = "July";
            }
        } else if (aMonthNumber == 8) {
            if (aShort == 1) {
                ms = "Aug";
            } else {
                ms = "August";
            }
        } else if (aMonthNumber == 9) {
            if (aShort == 1) {
                ms = "Sep";
            } else {
                ms = "September";
            }
        } else if (aMonthNumber == 10) {
            if (aShort == 1) {
                ms = "Oct";
            } else {
                ms = "October";
            }
        } else if (aMonthNumber == 11) {
            if (aShort == 1) {
                ms = "Nov";
            } else {
                ms = "November";
            }
        } else if (aMonthNumber == 12) {
            if (aShort == 1) {
                ms = "Dec";
            } else {
                ms = "December";
            }
        }
        return ms;
    }

    public int convertMonthNameToNo(String aMonthName) {
        int ms = 0;
        if (aMonthName.equals("Jan") || aMonthName.equals("January")) {
            ms = 1;
        } else if (aMonthName.equals("Feb") || aMonthName.equals("Febuary")) {
            ms = 2;
        } else if (aMonthName.equals("Mar") || aMonthName.equals("March")) {
            ms = 3;
        } else if (aMonthName.equals("Apr") || aMonthName.equals("April")) {
            ms = 4;
        } else if (aMonthName.equals("May") || aMonthName.equals("May")) {
            ms = 5;
        } else if (aMonthName.equals("Jun") || aMonthName.equals("June")) {
            ms = 6;
        } else if (aMonthName.equals("Jul") || aMonthName.equals("July")) {
            ms = 7;
        } else if (aMonthName.equals("Aug") || aMonthName.equals("August")) {
            ms = 8;
        } else if (aMonthName.equals("Sep") || aMonthName.equals("September")) {
            ms = 9;
        } else if (aMonthName.equals("Oct") || aMonthName.equals("October")) {
            ms = 10;
        } else if (aMonthName.equals("Nov") || aMonthName.equals("November")) {
            ms = 11;
        } else if (aMonthName.equals("Dec") || aMonthName.equals("December")) {
            ms = 12;
        }
        return ms;
    }

    public Integer getCurrentYear() {
        int current_year = 0;
        //current_year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar cal = new GregorianCalendar();
        cal.setTime(new CompanySetting().getCURRENT_SERVER_DATE());
        current_year = cal.get(Calendar.YEAR);
        return current_year;
    }

    public Integer getYearFromDate(Date aDate) {
        int date_year = 0;
        Calendar cal = new GregorianCalendar();
        cal.setTime(aDate);
        date_year = cal.get(Calendar.YEAR);
        return date_year;
    }

    public Integer getCurrentMonth() {
        int current_month = 0;
        //current_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Calendar cal = new GregorianCalendar();
        cal.setTime(new CompanySetting().getCURRENT_SERVER_DATE());
        current_month = cal.get(Calendar.MONTH) + 1;
        return current_month;
    }

    public Integer getMonthFromDate(Date aDate) {
        int date_month = 0;
        Calendar cal = new GregorianCalendar();
        cal.setTime(aDate);
        date_month = cal.get(Calendar.MONTH) + 1;
        return date_month;
    }

    public void clearList(List<Object> aList) {
        try {
            aList.clear();
        } catch (NullPointerException npe) {
            //do nothing
        }
    }

    public void refreshAlertsThread() {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    refreshAlerts();
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            System.err.println("refreshAlertsThread:" + e.getMessage());
        }
    }

    public void refreshAlerts() {
        //Refresh stock alerts
        try {
            new Alert_generalBean().refreshAlerts();
            org.primefaces.PrimeFaces.current().executeScript("doUpdateMenuSideBarClick()");
        } catch (Exception e) {
            System.err.println("refreshStockAlerts:" + e.getMessage());
        }
    }

    public String formatCapitaliseFirstCharacter(String aString) {
        if (aString.length() < 2) {
            return aString;
        } else {
            return aString.substring(0, 1).toUpperCase() + aString.substring(1).toLowerCase();
        }
    }

    public String formatCapitaliseAllCharacter(String aString) {
        if (aString.length() == 0) {
            return aString;
        } else {
            return aString.substring(0).toUpperCase();
        }
    }

    public int setTimePartOfDateOnly(Date aDate, String aTimePart) {
        int x = 0;
        if (null == aDate || aTimePart.length() == 0 || aTimePart.length() != 5) {
            x = 0;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(aDate);
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aTimePart.substring(0, 2)));
            cal.set(Calendar.MINUTE, Integer.parseInt(aTimePart.substring(3)));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // Put it back in the Date object 
            aDate.setTime(cal.getTime().getTime());
            x = 1;
        }
        return x;
    }

    public long getN(String aSQL) {
        ResultSet rs = null;
        long n = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(aSQL);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    n = rs.getLong("n");
                } catch (Exception e) {
                    n = 0;
                }
            }
        } catch (Exception e) {
            System.err.println("getN:" + e.getMessage());
        }
        return n;
    }

//    public static void main(String[] args) {
//        Date dt1=null;
//        Date dt2=null;
//        dt1=new Date();
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        System.out.println("Print1");
//        dt2=new Date();
//        System.out.println(dt2.getTime() - dt1.getTime());
//    }

    public void backupDatabase(String aDbName, String aDbUser, String aDbPassword, String aMySQLDumpFolderPath, String aSaveFolderPath) {
        try {
            String dbName = aDbName;
            String dbUser = aDbUser;
            String dbPass = aDbPassword;

            Calendar cal = Calendar.getInstance();
            //cal.setTime(new CompanySetting().getCURRENT_SERVER_DATE());
            cal.setTime(new Date());
            String DatePart = "";
            DatePart = cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "_" + cal.get(Calendar.MINUTE);
            //String savePath = aSaveFolderPath + "/" + aDbName + "_" + DatePart + ".sql.gz";
            String savePath = aSaveFolderPath + "\\" + aDbName + "_" + DatePart + ".sql.gz";

            /*NOTE: Used to create a cmd command*/
            //String executeCmd = aMySQLDumpFolderPath + "/mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " | gzip -r " + savePath;
            String executeCmd = aMySQLDumpFolderPath + "\\mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " | gzip -r >" + savePath;
            System.out.println("executeCmd:" + executeCmd);
            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("Error at Backup" + ex.getMessage());
        }
    }

    public String getCommaSeperatedStrFromStringArray(String[] aStringArray) {
        String CommaSeperatedStr = "";
        try {
            if (aStringArray.length == 0) {
                CommaSeperatedStr = "";
            } else {
                for (int i = 0; i < aStringArray.length; i++) {
                    if (CommaSeperatedStr.length() == 0) {
                        CommaSeperatedStr = aStringArray[i];
                    } else {
                        CommaSeperatedStr = CommaSeperatedStr + "," + aStringArray[i];
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getCommaSeperatedStrFromStringArray:" + e.getMessage());
        }
        return CommaSeperatedStr;
    }

    public String[] getStringArrayFromCommaSeperatedStr(String aCommaSeperatedStr) {
        String[] StringArray = null;
        try {
            if (null == aCommaSeperatedStr) {
                //
            } else {
                if (aCommaSeperatedStr.length() > 0) {
                    StringArray = aCommaSeperatedStr.split(",");
                }
            }
        } catch (Exception e) {
            System.out.println("getStringArrayFromCommaSeperatedStr:" + e.getMessage());
        }
        return StringArray;
    }

    public String getFirstStringFromCommaSeperatedStr(String aCommaSeperatedStr) {
        String FirstString = "";
        try {
            if (null == aCommaSeperatedStr) {
                //
            } else {
                if (aCommaSeperatedStr.length() > 0) {
                    FirstString = aCommaSeperatedStr.split(",")[0];
                }
            }
        } catch (Exception e) {
            System.out.println("getFirstStringFromCommaSeperatedStr:" + e.getMessage());
        }
        return FirstString;
    }

    /**
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the matcher
     */
    public Matcher getMatcher() {
        return matcher;
    }

    /**
     * @param matcher the matcher to set
     */
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    /**
     * @return the TIME24HOURS_PATTERN
     */
    public static String getTIME24HOURS_PATTERN() {
        return TIME24HOURS_PATTERN;
    }

}
