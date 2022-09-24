package com.mindblown.util;




import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class CalendarHelper {
    
    private static Instant instant = Instant.now();
    
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURADY = 6;
    public static final int SUNDAY = 7;
    
    public static ZonedDateTime convert(Instant i){
        return i.atZone(ZoneOffset.systemDefault());
    }
    
    public static ZonedDateTime convert(Instant i, ZoneId id){
        return i.atZone(id);
    }
    
    public static int dayOfTheWeek() {
        resetInstant();
        return dayOfTheWeek(instant);
    }
    
    public static int dayOfTheWeek(Instant i) {
        return dayOfTheWeek(convert(i));
    }
    
    public static int dayOfTheWeek(ZonedDateTime zone){
        return zone.getDayOfWeek().getValue();
    }
    
    /**
     * Returns the time that the Instant passed to this function in the format of Hours:Minutes:Seconds.
     * @param i The instant to convert
     * @return 
     */
    public static String timeHMSFormat(Instant i) {
        resetInstant();
        String specFormat = "";
        ZonedDateTime zone = convert(i);
        specFormat += hour(zone);
        specFormat += ":" + minute(zone);
        specFormat += ":" + second(zone);
        return specFormat;
    }
    
    /**
     * Returns the current time in the format of Hours:Minutes:Seconds.
     * @return 
     */
    public static String timeHMSFormat(){
        return timeHMSFormat(now());
    }
    
    public static String hour(Instant i){
        return hour(convert(i));
    }
    
    public static String hour(ZonedDateTime zone){
        int h = zone.getHour();
        if(h < 10){
            return "0" + h;
        }
        return h + "";
    }
    
    public static String minute(Instant i){
        return minute(convert(i));
    }
    
    public static String minute(ZonedDateTime zone){
        int m = zone.getMinute();
        if(m < 10){
            return "0" + m;
        }
        return m + "";
    }
    
    public static String second(Instant i){
        return second(convert(i));
    }
    
    public static String second(ZonedDateTime zone){
        int s = zone.getSecond();
        if(s < 10){
            return "0" + s;
        }
        return s + "";
    }
    
    public static String month(Instant i){
        return month(convert(i));
    }
    
    public static String month(ZonedDateTime zone){
        int num = zone.getMonthValue();
        if(num < 10){
            return "0" + num;
        }
        return num + "";
    }
    
    public static String day(Instant i){
        return day(convert(i));
    }
    
    public static String day(ZonedDateTime zone){
        int num = zone.getDayOfMonth();
        if(num < 10){
            return "0" + num;
        }
        return num + "";
    }
    
    public static String year(Instant i){
        return year(convert(i));
    }
    
    public static String year(ZonedDateTime zone){
        int num = zone.getYear();
        return num + "";
    }
    
    public static String dayOfYearFormat(Instant i){
        String format = "";
        ZonedDateTime zone = convert(i);
        format += month(zone) + "/";
        format += day(zone) + "/";
        format += year(zone);
        return format;
    }
    
    public static String dayOfYearFormat(){
        resetInstant();
        return dayOfYearFormat(instant);
    }
    
    /**
     * Resets the instant object.
     */
    private static void resetInstant() {
        instant = Instant.now();
    }
    
    /**
     * Get the Instant version of right now.
     * @return instant object
     */
    public static Instant now() {
        resetInstant();
        return instant;
    }
    
    public static int timeToNextSec(Instant i){
        long now = Date.from(i).getTime();
        long timeTill = now % 1000;
        return (int) timeTill;
    }
    
    public static String nowFormat(){
        return nowFormat(convert(now()));
    }
    
    public static String nowFormat(Instant i){
        return nowFormat(convert(i));
    }
    
    public static String nowFormat(ZonedDateTime zone){
        return year(zone) + "-" + month(zone) + "-" + day(zone) + "-" + hour(zone) + "-" + minute(zone) + "-" + second(zone) + "-" + (1000 - timeToNextSec(zone.toInstant()));
    }
}
