package appdulich.booking.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    public static boolean regexEmailValidationPattern(String email) {
        //Set pattern
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!pattern.matcher(email).matches()) {
            return true;
        }
        else {
            return false;
        }


    }


}
