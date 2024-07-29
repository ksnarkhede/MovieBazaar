/*import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.matches;

public class seatValidator {
    private static final String seatPattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static boolean isValidTime(String time){
        Pattern pattern = Pattern.compile(timePattern);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
 /*   public static void main(String[] args){
        String[] showTimings = {"00:00", "12:34","23:59","24:00","2:06","7:15"};

        for(String time : showTimings){
            System.out.println("Is" + time + "a valid time?" + isValidTime(time));
        }
    }
}
        */