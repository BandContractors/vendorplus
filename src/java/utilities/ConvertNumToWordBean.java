package utilities;

import beans.AccCurrencyBean;
import entities.AccCurrency;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class ConvertNumToWordBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String[] units = {
        "",
        " One",
        " Two",
        " Three",
        " Four",
        " Five",
        " Six",
        " Seven",
        " Eight",
        " Nine"
    };
    private static final String[] twoDigits = {
        " Ten",
        " Eleven",
        " Twelve",
        " Thirteen",
        " Fourteen",
        " Fifteen",
        " Sixteen",
        " Seventeen",
        " Eighteen",
        " Nineteen"
    };
    private static final String[] tenMultiples = {
        "",
        "",
        " Twenty",
        " Thirty",
        " Forty",
        " Fifty",
        " Sixty",
        " Seventy",
        " Eighty",
        " Ninety"
    };
    private static final String[] placeValues = {
        " ",
        " Thousand",
        " Million",
        " Billion",
        " Trillion"
    };

    private static String convertNumber(int number) {
        String word = "";
        int index = 0;
        do {
            // take 3 digits in each iteration
            int num = (int) (number % 1000);
            if (num != 0) {
                String str = ConversionForUptoThreeDigits(num);
                word = str + placeValues[index] + word;
            }
            index++;
            // next 3 digits
            number = number / 1000;
        } while (number > 0);
        return word;
    }

    private static String ConversionForUptoThreeDigits(int number) {
        String word = "";
        int num = number % 100;
        if (num < 10) {
            word = word + units[num];
        } else if (num < 20) {
            word = word + twoDigits[num % 10];
        } else {
            word = tenMultiples[num / 10] + units[num % 10];
        }

        word = (number / 100 > 0) ? units[number / 100] + " Hundred" + word : word;
        return word;
    }

    public static String formatNumber(Double aNumber) {
        String format = "";
        try {
            String pattern = "###";
            String language_code = "en";
            String country_code = "US";
            Locale locale = new Locale(language_code, country_code);
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
            decimalFormat.applyPattern(pattern);
            format = decimalFormat.format(aNumber);
        } catch (Exception e) {
        }
        return format;
    }

    public String convertNumToWord(double aAmount, String aCurrencyCode) {
        String words = "";
        try {
            String numberAsString = this.formatDoubleToString(aAmount, aCurrencyCode);
            String NumberStr = "";
            String DecimalStr = "";
            int psn = -1;
            psn = numberAsString.indexOf(".");
            if (psn > 0) {
                NumberStr = numberAsString.substring(0, psn);
                DecimalStr = numberAsString.substring(psn + 1);
            } else {
                NumberStr = numberAsString;
                DecimalStr = "";
            }
            int NumberInt = 0;
            int DecimalInt = 0;
            if (!NumberStr.equals("0") && !NumberStr.equals("")) {
                NumberInt = Integer.parseInt(NumberStr);
            }
            if (!DecimalStr.equals("0") && !DecimalStr.equals("")) {
                DecimalInt = Integer.parseInt(DecimalStr);
            }
            String NumberWord = "";
            String DecimalWord = "";
            if (NumberInt > 0) {
                NumberWord = convertNumber(NumberInt);
            }
            if (DecimalInt > 0) {
                DecimalWord = convertNumber(DecimalInt);
            }
            String CurUnit = "";
            String DecUnit = "";
            AccCurrency acccurr = new AccCurrencyBean().getCurrencyFromListMemory(aCurrencyCode);
            if (acccurr.getCurrency_unit().length() > 0) {
                CurUnit = acccurr.getCurrency_unit();
            }
            if (acccurr.getDecimal_unit().length() > 0) {
                DecUnit = acccurr.getDecimal_unit();
            }

            if (NumberWord.length() > 0) {
                words = NumberWord;
                if (CurUnit.length() > 0) {
                    if (NumberInt > 1) {
                        words = NumberWord + " " + CurUnit + "s";
                    } else {
                        words = NumberWord + " " + CurUnit;
                    }
                }
            }

            if (DecimalWord.length() > 0) {
                //words = words + " and " + DecimalWord;
                if (DecUnit.length() > 0) {
                    if (DecimalInt > 1) {
                        words = words + " and " + DecimalWord + " " + DecUnit + "s";
                    } else {
                        words = words + " and " + DecimalWord + " " + DecUnit;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("convertNumToWord:" + e.getMessage());
        }
        return words;
    }

    public String getNumberFormatByCurrency(String aCurrencyCode) {
        String NumberFormat = "###";
        String DecimalPlaceSubStr = "";
        int decimal_places = 0;
        try {
            decimal_places = new AccCurrencyBean().getCurrencyFromListMemory(aCurrencyCode).getDecimal_places();
            for (int i = 1; i <= decimal_places; i++) {
                DecimalPlaceSubStr = DecimalPlaceSubStr + "0";
            }
        } catch (Exception e) {
        }
        if (decimal_places > 0) {
            NumberFormat = "###." + DecimalPlaceSubStr;
        } else {
            NumberFormat = "###";
        }
        return NumberFormat;
    }

    public String formatDoubleToString(double aAmount, String aCurrencyCode) {
        String aString = "";
        String aPattern = "";
        aPattern = this.getNumberFormatByCurrency(aCurrencyCode);
        if (aPattern.length() == 0) {
            aPattern = "###";
        }
        if (aAmount >= 0) {
            aString = new UtilityBean().formatNumber(aPattern, aAmount) + "";
        } else if (aAmount < 0) {
            aString = new UtilityBean().formatNumber(aPattern, aAmount) + "";
        }
        return aString;
    }

}
