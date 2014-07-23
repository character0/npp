package util

import java.text.SimpleDateFormat;

class DateFormatter {
	// DEFAULT
	public static SimpleDateFormat getDEFAULT() {
		return new SimpleDateFormat("MM-dd-yyyy HH:mm")
	} 
	
	// DEFAULT_NO_TIME
	public static SimpleDateFormat getDEFAULT_NO_TIME() {
		return new SimpleDateFormat("MM-dd-yyyy")
	} 
	
	// DEFAULT_SECS
	public static SimpleDateFormat getDEFAULT_SECS() {
		return new SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
	} 
	
	// DATE_TIME
	public static SimpleDateFormat getDATE_TIME() {
		return new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a")
	} 

	// DATE_TIME_TIMEZONE
	public static SimpleDateFormat getDATE_TIME_TIMEZONE() {
		return new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a z")
	}

	// DATE_NO_TIME
	public static SimpleDateFormat getDATE_NO_TIME() {
		return new SimpleDateFormat("EEE, MMM dd, yyyy")
	}

	// DAY_OF_WEEK_AND_DATE
	public static SimpleDateFormat getDAY_OF_WEEK_AND_DATE() {
		return new SimpleDateFormat("EE, MM/dd")
	}

	// DATE_AND_TIME
	public static SimpleDateFormat getDATE_AND_TIME() {
		return new SimpleDateFormat("MM/dd/yyyy hh:mm a")
	}

    // DATE_TIME_AND_SECONDS
    public static SimpleDateFormat getDATE_TIME_AND_SECONDS() {
        return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")
    }

    public static SimpleDateFormat getDATE_TIME_SECONDS() {
        return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
    }

	// TIME_ONLY
	public static SimpleDateFormat getTIME_ONLY() {
		return new SimpleDateFormat("hh:mm a")
	}

	// TIME_WITH_SECONDS
	public static SimpleDateFormat getTIME_WITH_SECONDS() {
		return new SimpleDateFormat("hh:mm:ss.S a")
	}

	// MILITARY_TIME_SECS
	public static SimpleDateFormat getMILITARY_TIME_SECS() {
		return new SimpleDateFormat("HH:mm:ss")
	}

	// MILITARY_TIME_MINS
	public static SimpleDateFormat getMILITARY_TIME_MINS() {
		return new SimpleDateFormat("HH:mm")
	}

	// DATE_ONLY
	public static SimpleDateFormat getDATE_ONLY() {
		return new SimpleDateFormat("EEE, MM/dd/yy")
	}

	// ADI
	public static SimpleDateFormat getADI() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	}

	// ADI_NO_TIME
	public static SimpleDateFormat getADI_NO_TIME() {
		return new SimpleDateFormat("yyyy-MM-dd")
	}

	// ADI_TIME_ALTERNATIVE
	public static SimpleDateFormat getADI_TIME_ALTERNATIVE() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
	}

	// SYSTEM_EVENT
	public static SimpleDateFormat getSYSTEM_EVENT() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
	}

	// PROGRAM_EVENT
	public static SimpleDateFormat getPROGRAM_EVENT() {
		return new SimpleDateFormat("yyMMddkkmmss")
	}

	// WHATS_ON
	public static SimpleDateFormat getWHATS_ON() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS")
	}

	// DATE_INPUT
	public static SimpleDateFormat getDATE_INPUT() {
		return new SimpleDateFormat("MM/dd/yy HH:mm")
	}

	// DATE_INPUT_SECONDS
	public static SimpleDateFormat getDATE_INPUT_SECONDS() {
		return new SimpleDateFormat("MM/dd/yy HH:mm:ss")
	}

	// DATE_INPUT_NO_TIME
	public static SimpleDateFormat getDATE_INPUT_NO_TIME() {
		return new SimpleDateFormat("MM/dd/yy")
	}

	// DATE_INPUT_NO_TIME_FULLYEAR
	public static SimpleDateFormat getDATE_INPUT_NO_TIME_FULLYEAR() {
		return new SimpleDateFormat("M/dd/yyyy")
	}

	// PLAYDATA_REPORT
	public static SimpleDateFormat getPLAYDATA_REPORT() {
		return new SimpleDateFormat("yyyyMMdd")
	}

	// SQL_DATE
	public static SimpleDateFormat getSQL_DATE() {
		return new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS")
	}

	// MONTH_ONLY
	public static SimpleDateFormat getMONTH_ONLY() {
		return new SimpleDateFormat("MM/yyyy")
	}

	// BROADCAST_DASHBOARD_TODAY
	public static SimpleDateFormat getBROADCAST_DASHBOARD_TODAY() {
		return new SimpleDateFormat("HH:mm:ss")
	}

	// BROADCAST_DASHBOARD_NOT_TODAY
	public static SimpleDateFormat getBROADCAST_DASHBOARD_NOT_TODAY() {
		return new SimpleDateFormat("MM/dd/yy HH:mm:ss")
	}

	// BROADCAST_SCHEDULE_TODAY
	public static SimpleDateFormat getBROADCAST_SCHEDULE_TODAY() {
		return new SimpleDateFormat("HH:mm:ss")
	}

	// BROADCAST_SCHEDULE_NOT_TODAY
	public static SimpleDateFormat getBROADCAST_SCHEDULE_NOT_TODAY() {
		return new SimpleDateFormat("MM/dd HH:mm:ss")
	}
}