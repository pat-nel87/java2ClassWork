import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;

public class TwentyFourTwelve {

    public static String newTime;

    public String convert(String originalTime) throws ParseException {
        //System.out.println(originalTime);
        DateFormat inputFormat = new SimpleDateFormat("HH:mm");
        DateFormat outputFormat = new SimpleDateFormat("hh:mm");
        Date givenDate = null;
        givenDate = inputFormat.parse(originalTime);

        return outputFormat.format(givenDate);
    }

    TwentyFourTwelve(String originalTime) {
        try {
            this.newTime = convert(originalTime);
        } catch(ParseException x) {
           System.out.println("Illegal Time Format Entry!!");
        }

    }

    public static void main(String[] args) {
        TwentyFourTwelve x = new TwentyFourTwelve("22:22");
        System.out.println(x.newTime);
        TwentyFourTwelve y = new TwentyFourTwelve("#4!567");

    }

}
