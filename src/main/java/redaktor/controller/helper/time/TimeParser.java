package redaktor.controller.helper.time;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeParser {
    public static Time parseTimeFromString(String timeString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = sdf.parse(timeString);
        Time czasOdtworzenia = new Time(date.getTime());

        return czasOdtworzenia;
    }
}
