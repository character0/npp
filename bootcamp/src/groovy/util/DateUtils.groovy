package groovy.util

import org.apache.commons.logging.LogFactory

import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.sql.Timestamp;


/**
 * User: jtanner
 * Date: Jun 7, 2008
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

    private static final log = LogFactory.getLog(this)

	public static final String DEFAULT_TIME_ZONE_ID = "US/Eastern";
	public static final int MILLIS_IN_HOUR = 3600000;  
	public static final int MILLIS_IN_SECOND = 1000;  
	public static final int SECONDS_IN_DAY = 86400;

	public static Calendar getDayStartCal (Calendar inCal) {
	  	Calendar cal = (Calendar) inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		return cal;
	}

    public static Calendar getDayStartCal(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
		return getDayStartCal(cal);
	}

	public static Calendar getDayEndCal (Calendar inCal) {
		Calendar cal = (Calendar) inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);		
		return cal;
	}
	
	/**
	 * @return Date object set to the first millisecond of the dayOfMonth passed in
	 */
	public static Date getDayStart(Calendar cal) {
		return getDayStartCal(cal).getTime();		
	}
	
	/**
	 * @return Date object set to the last millisecond of the dayOfMonth passed in
	 */
	public static Date getDayEnd(Calendar cal) {
		return getDayEndCal(cal).getTime();
	}
	
	/**
	 * @return Date object set to the first millisecond of the dayOfMonth passed in
	 */
	public static Date getDayStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getDayStart(cal);
	}

	/**
	 * @return Date object set to the last millisecond of the dayOfMonth passed in
	 */
	public static Date getDayEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getDayEnd(cal);
	}

	public static Calendar getMonthStartCal (Calendar inCal) {
		Calendar cal = (Calendar) inCal.clone();
		cal.set(Calendar.DAY_OF_MONTH, 1);	
		return getDayStartCal(cal);
	}

	public static Calendar getMonthEndCal (Calendar inCal) {
		Calendar cal = (Calendar) inCal.clone();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));	
		return getDayEndCal(cal);
	}
	
	/**
	 * @return Date object set to the first millisecond of the month date is within
	 */
	public static Date getMonthStart(Calendar cal) {
		return getMonthStartCal(cal).getTime();
	}
	
	/**
	 * @return Date object set to the last millisecond of the month date is within
	 */
	public static Date getMonthEnd(Calendar cal) {
		return getMonthEndCal(cal).getTime();
	}
	
	/**
	 * @return Date object set to the first millisecond of the month date is within
	 */
	public static Date getMonthStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getMonthStart(cal);
	}
	
	/**
	 * @return Date object set to the last millisecond of the month date is within
	 */
	public static Date getMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getMonthEnd(cal);
	}
	
	/**
	 * If date is Sunday, returns start of date
	 * Else get the start of the Sunday that came before date
	 */
	public static Date getWeekStart(Date date) {
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		cal.set(Calendar.DAY_OF_WEEK, 1)
		
		return getDayStart(cal.getTime())
	}
	
	
	/**
	 * If date is Saturday, returns end of date
	 * Else get the end of the Saturday that comes after date
	 */
	public static Date getWeekEnd(Date date) {
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		cal.set(Calendar.DAY_OF_WEEK, 7)
		
		return getDayEnd(cal.getTime())
	}

    /**
     *
     * @param inCal
     * @return
     */
    public static Date get10MinutesBefore(Calendar inCal) {
        Calendar cal = (Calendar) inCal.clone();
		cal.set(Calendar.MINUTE, inCal.get(Calendar.MINUTE) - 10);
        return cal.getTime();
    }

    /**
     *
     * @param inCal
     * @return
     */
    public static Date get10MinutesAfter(Calendar inCal) {
        Calendar cal = (Calendar) inCal.clone();
		cal.set(Calendar.MINUTE, inCal.get(Calendar.MINUTE) + 10);
        return cal.getTime();
    }

    /**
     *
     * @param date
     * @return
     */
    public static Date get10MinutesBefore(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return get10MinutesBefore(cal);
    }

    /**
     * 
     * @param date
     * @return
     */
    public static Date get10MinutesAfter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return get10MinutesAfter(cal);
    }
	
	/**
	 * In Java, days run from 00:00:00.000 to 23:59:59.999.  This method returns
	 * the start of today.
	 *
	 * @return A Date object set to the first millisecond of today.
	 */
	public static Date getTodayStart() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return getDayStart(cal);
	}

	/**
	 * In Java, days run from 00:00:00.000 to 23:59:59.999.  This method returns
	 * the end of today.
	 *
	 * @return A Date object set to the last millisecond of today.
	 */
	public static Date getTodayEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return getDayEnd(cal);
	}


	/**
	 * In Java, days run from 00:00:00.000 to 23:59:59.999.  This method returns
	 * the start of tomorrow.
	 *
	 * @return A Date object set to the first millisecond of tomorrow.
	 */
	public static Date getTomorrowStart() {
		return nextDay(getTodayStart());
	}

	/**
	 * In Java, days run from 00:00:00.000 to 23:59:59.999.  This method returns
	 * the end of tomorrow.
	 *
	 * @return A Date object set to the last millisecond of tomorrow.
	 */
	public static Date getTomorrowEnd() {
		return nextDay(getTodayEnd());
	}


	/**
	 * This method returns the same time the next day.
	 *
	 * @param day A Date object that you want to increment.
	 * @return A Date object pointing at the same time at the next day.
	 */
	public static Date nextDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH,1);
		return cal.getTime();
	}


	/**
	 * This method returns the same time tomorrow, regardless of what day is
	 * passed in.
	 *
	 * @param day A Date object that has the time of day you want.
	 * @return A Date object pointing at the same time tomorrow.
	 */
	public static Date sameTimeTomorrow(Date day) {
		Date tomorrow = addDays(new Date(), 1);
		return nowTimeOnDate(tomorrow);
	}
	
	/**
	 * Given 2 dates, compares them to the second
	 * 
	 * @param date1 First date to compare
	 * @param date2 Second date to compare
	 * 
	 * @return Return negative number if date1 is before date2, 0 if they are the same, positive number if date1 is after date2
	 */
	public static int compareDatesToSecond(Date date1, Date date2, int diffBuffer = 0) {
		 // Remove anything smaller than second from date1 and date2
		 Date d1 = getDateToSecond(date1)
		 Date d2 = getDateToSecond(date2)
		 
		 // Calculate the second difference between the 2 dates
		 double secDiff = (d1.time-d2.time) / MILLIS_IN_SECOND
		 
		 // if the difference is inside the max buffer, return 0
		 if (secDiff >= (-1 * diffBuffer) && secDiff <= diffBuffer) {
			 return 0
		 }
		 
		 // Else return actual value
		 return secDiff
	}
	
	/**
	 * Given a date, sets any values after second to 0
	 * 
	 * @param date Date to use
	 * @return Date value that matches date but with no values after the second.
	 */
	public static Date getDateToSecond(Date date) {
		Calendar cal = Calendar.getInstance()
        cal.setTime(date)
		cal.set(Calendar.MILLISECOND, 0)
		
		return cal.getTime()
	}

	/**
	 * This method returns the same time it is right now on give day, regardless of what day is
	 * passed in.
	 *
	 * @param day A Date object that has the time you want.
	 * @return A Date object pointing at the same time on given day.
	 */
	public static Date nowTimeOnDate(Date day) {
		Calendar orig = Calendar.getInstance();
		orig.setTime(new Date());

		Calendar cal = Calendar.getInstance();
		cal.setTime(day);

		cal.set(Calendar.HOUR_OF_DAY, orig.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, orig.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, orig.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, orig.get(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static Date getFirstDayPartStart(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);				
		return cal.getTime();
	}

	public static Date getFirstDayPartEnd(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 5);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getSecondDayPartStart(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 6);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		return cal.getTime();
	}

	public static Date getSecondDayPartEnd(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getThirdDayPartStart(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		return cal.getTime();
	}

	public static Date getThirdDayPartEnd(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getFourthDayPartStart(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 18);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		return cal.getTime();
	}

	public static Date getFourthDayPartEnd(Calendar inCal) {
    Calendar cal = (Calendar)inCal.clone();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);						
		return cal.getTime();
	}

		
	public static Date getFirstDayPartStart() {
		return getTodayStart();
	}

	public static Date getFirstDayPartEnd() {
		Calendar cal = Calendar.getInstance();
		return getFirstDayPartEnd(cal);
	}

	public static Date getSecondDayPartStart() {
		Calendar cal = Calendar.getInstance();
		return getSecondDayPartStart(cal);
	}

	public static Date getSecondDayPartEnd() {
		Calendar cal = Calendar.getInstance();
		return getSecondDayPartEnd(cal);
	}

	public static Date getThirdDayPartStart() {
		Calendar cal = Calendar.getInstance();
		return getThirdDayPartStart(cal);
	}

	public static Date getThirdDayPartEnd() {
		Calendar cal = Calendar.getInstance();		
		return getThirdDayPartEnd(cal);
	}


	public static Date getFourthDayPartStart() {
		Calendar cal = Calendar.getInstance();
		return getFourthDayPartStart(cal);
	}

	public static Date getFourthDayPartEnd() {
		return getTodayEnd();
	}

	public static int getMilliSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);		
		return cal.get(Calendar.MILLISECOND);
	}
	
	public static int getSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);		
		return cal.get(Calendar.SECOND);
	}
	
	public static int getMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.MINUTE);
	}	

	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.HOUR);
	}

	public static int getHourOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.HOUR_OF_DAY);
	}	
	
	public static int getAMPM(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.AM_PM);
	}
		
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.DAY_OF_WEEK);
	}
		
	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.MONTH);
	}		

	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);				
		return cal.get(Calendar.YEAR);
	}
	
	public static Date createDate(int year, int month, int day, int hour, int min, int sec, milliSeconds = null) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, min, sec)
		if (milliSeconds != null) {
			cal.set(Calendar.MILLISECOND, milliSeconds)
		}
		return cal.getTime();
	}
	
	public static Date createDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day)
		return cal.getTime();
	}
	
	public static Date setHourOfDay(Date date, int hourOfDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		return cal.getTime();
	}
	
	public static Date setAMPM(Date date, int am_pm) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.AM_PM, am_pm);
		return cal.getTime();
	}

    public static int getFourHourBeginBlock(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int hourOfTheDay = c.get(Calendar.HOUR_OF_DAY);
        if (hourOfTheDay < 23 && hourOfTheDay%4 >= 3)
            ++hourOfTheDay;
        int blockBegin = hourOfTheDay/4;
        return blockBegin * 4;
    }
    
    public static boolean isToday(Date date) {
    	Date todayStart = getTodayStart();
    	Date todayEnd = getTodayEnd();
    	
    	return ( date.compareTo(todayStart) >= 0 &&
    			 date.compareTo(todayEnd) <= 0 );
    }
    
    /**
     * Tries to parse a date string given a format.
     * 
     * @param dateStr Date string to be parsed.
     * @param format SimpleDateFormat to use as parser.
     * @return Return date object if successfully parsed, null otherwise.
     */
    public static Date getDate(String dateStr, SimpleDateFormat format) {
    	Date retDate = null;
    	
    	try {
    		retDate = format.parse(dateStr);
    	}
    	catch (Exception e) {
    		return null;
    	}
    	
    	return retDate;
    }
    
    /**
     * Tries to format a date to a string given a format.
     * 
     * @param date String to be formatted.
     * @param format SimpleDateFormat to use as formatter.
     * @return Return string object if successfully parsed, blank otherwise.
     */
    public static String getDateString(Date date, SimpleDateFormat format) {
    	String retStr = "";
    	
    	try {
    		StringBuilder sb = new StringBuilder(format.format(date));
    		retStr = sb.toString();
    	}
    	catch (Exception e) {
    		return "";
    	}
    	
    	return retStr;
    }

    /**
     * Get a date object corresponding to january 1st, 1970
     * @return The beginning of time.
     */
    public static Date getOldestDate() {
        Date beginTime = new Date();
        beginTime.setTime(0);
        return beginTime;
    }
    
    /**
     * Returns a String array of US Time Zone Ids
     */
    public static String[] getUSTimeZoneIds() {
    	return ["US/Eastern", "US/Central", "US/Mountain", "US/Pacific", 
    			"US/Hawaii", "US/Alaska", "US/Arizona"] as String[];
    }
    
    /**
     * Given a timezone id, returns its abbreviation in proper daylight savings time
     *  for today
     */
    public static String getTimeZoneAbbreviation(String timeZoneId) {
    	 return getTimeZoneAbbreviation(new Date(), timeZoneId)
    }
    
    /**
     * Given a timezone id, returns its abbreviation in proper daylight savings time
     *  for given date
     */
    public static String getTimeZoneAbbreviation(Date date, String timeZoneId) {
		TimeZone tz = TimeZone.getTimeZone(timeZoneId)
		
		return tz.getDisplayName(tz.inDaylightTime(date), TimeZone.SHORT)
    }
    
    /**
     * Given a time zone, returns it's offset from the default time zone in hours
     */
    public static int getTimeZoneOffsetInHours(String timeZoneId) {
        TimeZone defaultTimeZone = TimeZone.getTimeZone(DateUtils.DEFAULT_TIME_ZONE_ID);
        TimeZone givenTimeZone = TimeZone.getTimeZone(timeZoneId);
        long now = new Date().getTime();
		 
        int timeZoneOffset = (givenTimeZone.getOffset(now) - defaultTimeZone.getOffset(now));
        
        // Return the offset in hours
        return timeZoneOffset/MILLIS_IN_HOUR;
    }
    
    /**
     * Given a date and its time zone, returns the date in the timezone of the default timezone
     * 
     * For example:
     *  date: 04/29/2010 12:00
     *  timeZoneId: "US/Pacific"
     *  DEFAULT_TIME_ZONE_ID: "US/Eastern"
     *  
     * Returns: 04/29/2010 15:00
     */
    public static Date getDateInDefaultTimezone(Date date, String timeZoneId) {
    	int offset = getTimeZoneOffsetInHours(timeZoneId)
    	
    	return addHours(date, offset * -1)
    }
    
    /**
     * Given a date in the default timezone and another time zone, 
     * 	returns the default timezone date in the given timezone
     * 
     * For example:
     *  date: 04/29/2010 12:00
     *  timeZoneId: "US/Pacific"
     *  DEFAULT_TIME_ZONE_ID: "US/Eastern"
     *  
     * Returns: 04/29/2010 09:00
     */
    public static Date getDateInTimezone(Date date, String timeZoneId) {
    	int offset = getTimeZoneOffsetInHours(timeZoneId)
    	
    	return addHours(date, offset)
    }
    
    /** 
     * Returns a hashmap in the form [(String)timeZoneId: (int)timeZoneOffset]
     */
    public static HashMap getUSTimeZonesWithOffsets() {
		HashMap map = new HashMap();
    	String[] timeZoneIds = getUSTimeZoneIds();
    	
    	for (String timeZoneId : timeZoneIds) {
    		map.put(timeZoneId, getTimeZoneOffsetInHours(timeZoneId));
    	}
    	
    	return map;
    }

    /**
     * Returns the number of milliseconds to end of day for the given date. 
     */
    public static long getMillisToDayEnd(Date date) {
       return getDayEnd(date).getTime() - date.getTime();
    }
    
    /**
     * Converts a sql Timestamp to a Date
     */
    public static Date convertTimestampToDate(Timestamp timestamp) {
	    return new Date(timestamp.getTime());
	}

    //Set time of the target date to match the source date.
    public static Date setTime(Date srcDate, Date tarDate) {

        Calendar cal = Calendar.instance
        cal.time = srcDate

        Calendar tarCal = Calendar.instance
        tarCal.time = tarDate

        tarCal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY))
        tarCal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE))
        tarCal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND))
        tarCal.set(Calendar.SECOND, cal.get(Calendar.SECOND))

        return tarCal.time;

    }

     /**
     * For a given day of month, find the last date in which it would have occurred.
      * If day of month is equal to today, then today's date will be returned.
      * If last month didn't have as many days as day of month, then the last date from last month would be returned.
     * For passed day of 31st: If today = Apr 8th, method returns Mar 31st.
     *                         If today = Mar 1st, method returns Feb 28th
      *                        If today = Mar 31st, method returns Mar 31st.
      *                        If today = Apr 30th, method returns Apr 30th (Passed date is not going to occur this month, so return the last day of this month)
     */
    private static Date getLastOccurrenceDate(Integer dayOfMonth) {
        //get current day of month
        Calendar cal = Calendar.instance
        Integer currentDayOfMonth =  cal.get(Calendar.DAY_OF_MONTH)
        Integer maxDaysCurrentMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        //Handle last day of month scenario.
        //If today is the last day of the month and passed day is > than max. number of days in the current month, return today as the last occurrence day
        if (dayOfMonth > maxDaysCurrentMonth && currentDayOfMonth == maxDaysCurrentMonth) {
            return cal.time
        }

        //If passed day is > today, then it must have occurred last month
        if (dayOfMonth > currentDayOfMonth) {cal.add(Calendar.MONTH, -1)}

        //If last month doesn't have as many days as the passed day, then set it the max. possible.
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth < cal.getActualMaximum(Calendar.DAY_OF_MONTH) ? dayOfMonth : cal.getActualMaximum(Calendar.DAY_OF_MONTH))

        return cal.time
    }
	
	// Get short name of a month
	public static String getMonthShortName(Date date) {
		def shortMonths = new DateFormatSymbols().getShortMonths()
		
		return shortMonths[getMonth(date)]
	}
	
	// Get name of a month	
	public static String getMonthName(Date date) {
		def months = new DateFormatSymbols().getMonths()
		
		return months[getMonth(date)]
	}

    //Check if the passed date's time is between 11pm and midnight
    public static boolean isTimeToCopyForward(Date date = new Date()) {
        return getMillisToDayEnd(date) < MILLIS_IN_HOUR
    }
    
    public static Date getCopyForwardStartTime(Date date = new Date()) {
        return DateUtils.addHours(getDayStart(date), 23)
    }

 }
