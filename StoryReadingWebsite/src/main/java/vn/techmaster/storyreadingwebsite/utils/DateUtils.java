package vn.techmaster.storyreadingwebsite.utils;

import java.time.*;
import java.util.Date;
public class DateUtils {
    
    //Lấy thời gian hiện tại
    public static Date getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    
}
