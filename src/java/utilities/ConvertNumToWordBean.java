package utilities;

import java.io.Serializable;
import java.sql.Types;
import static java.sql.Types.BIGINT;
import java.util.StringTokenizer;
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

    private static String convertNumber(long number) {
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

//    public static void main(String[] args) {
//        System.out.println("1234123456789- " + convertNumber(1234123456789L));
//        System.out.println("123456789- " + convertNumber(123456789));
//        System.out.println("37565820- " + convertNumber(37565820));
//        System.out.println("9341947- " + convertNumber(9341947));
//        System.out.println("37000- " + convertNumber(37000));
//        System.out.println("1387- " + convertNumber(1387));
//        System.out.println("10- " + convertNumber(10));
//        System.out.println("41- " + convertNumber(41));
//        double AmountToConvert = 12345672;
//        String FirstPart = "";
//        String LastPart = "";
//        int FirstNumber = 0;
//        int LastNumber = 0;
//        String FirstWord = "";
//        String LastWord = "";
//        String FinalWord = "";
//
//        StringTokenizer token = new StringTokenizer(Double.toString(AmountToConvert), ".");
//        FirstPart = token.nextToken();
//        LastPart = token.nextToken();
//        if (!FirstPart.equals("0")) {
//            try {
//                FirstNumber = Integer.parseInt(FirstPart);
//                FirstWord = convertNumber(FirstNumber);
//            } catch (Exception e) {
//                FirstWord = "";
//            }
//        }
//        if (!LastPart.equals("0")) {
//            try {
//                LastNumber = Integer.parseInt(LastPart);
//                LastWord = convertNumber(LastNumber);
//            } catch (Exception e) {
//                LastWord = "";
//            }
//        }
//        if (FirstWord.length() > 0) {
//            FinalWord = FirstWord;
//        }
//        if (LastWord.length() > 0) {
//            FinalWord = FinalWord + " AND " + LastWord + " CENTS";
//        }
//        System.out.println(FinalWord);
//    }
}
