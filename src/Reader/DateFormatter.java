package Reader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    String today1;
    String today2;

    DateFormatter(){
        String pattern1 = "yyyyMMdd";
        String pattern2 = "MM-dd-yyyy";
        SimpleDateFormat df1 = new SimpleDateFormat(pattern1);
        SimpleDateFormat df2 = new SimpleDateFormat(pattern2);
        this.today1 = df1.format(new Date());
        this.today2 = df2.format(new Date());
    }
}
