package groovy.util

import org.apache.commons.logging.LogFactory
import org.apache.commons.validator.EmailValidator

import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern;

/**
 * @author Jared
 *
 */
public class ValidateUtil {
	// Standard character validation constants
	public static final String ALPHA = "\\u0041-\\u005A\\u0061-\\u007A";
	public static final String NUMERIC = "\\u0030-\\u0039";
	public static final String ALPHANUMERIC = ALPHA + NUMERIC;
	public static final String PUNCTUATION = "\\u0021-\\u002F\\u003A-\\u003F\\u0040\\u005B-\\u005F\\u0060\\u007B-\\u007E";
	public static final String URL_PUNCTUATION = "\\u0021-\\u0024\\u0026-\\u002A\\u002C-\\u002E\\u003A\\u003C-\\u003E\\u0040\\u005B-\\u005F\\u0060\\u007B-\\u007E";
	public static final String LATIN = "\\u0021-\\u007E\\u00A1-\\u00FF";

	public static final String NEW_LINE = "\\u000A\\u000D";
	public static final String SPACE = "\\u0020\\u00A0";
	public static final String DASH = "\\u002D";
	public static final String DOT = "\\u002E";
	public static final String UNDERSCORE = "\\u005F";

    private static final log = LogFactory.getLog(this)
	
	// Function to check any string against a string of character groups that will be combined
	//  into a regex
	// Return null if the string is valid
	// Returns a list of invalid characters if string is invalid
	public static String getInvalidChars(String str, String validChars, boolean bCanBeBlank) {
		if (bCanBeBlank && (str == null || str == "")) return null;

		// Pattern to find all characters that are not (^) the valid characters
		Pattern pattern = Pattern.compile("[^" + validChars + "]+");
		Matcher matcher = pattern.matcher(str);
		
		String retStr = "";
		ArrayList<Character> usedChars = new ArrayList<Character>();
		while (matcher.find()) {
	        String invalidString = str.substring(matcher.start(), matcher.end());
	        for (int i = 0; i < invalidString.length(); i++) {
	        	Character c = invalidString.charAt(i);
	        	if (!usedChars.contains(c)) {
	        		String display = "" + c;
	        		if (c == '\n' || c == '\r') {
	        			display = "carriage return";
		        		usedChars.add('\n');
		        		usedChars.add('\r');
	        		}
	        		else {
	        			if (c == ' ')
	        				display = "space";
		        		else if (c == '\t')
		        			display = "tab";
		        		else if (c == '\'')
		        			display = "single quote";
		        		else if (c == '\"')
		        			display = "double quote";
		        		else
		        			display = "'" + c + "'";
	        			
		        		usedChars.add(c);
	        		}
	        		
	        		retStr += display + ", ";
	        	}
	        }
	    }
		
		if (retStr == "") {
			return null;
		}
		else {
			// Remove the last comma 
			retStr = retStr.trim().substring(0, retStr.length()-2);
		}
		
		return retStr;
	}
	
	// Function to check any string against a string of character groups that will be combined
	//  into a regex
	// Return true if string is valid, false otherwise
	public static boolean isValidString(String str, String validChars, boolean bCanBeBlank) {
		return (getInvalidChars(str, validChars, bCanBeBlank) == null);
	}
	
	// Function to check any string to determine if it is a valid e-mail address
	public static boolean isValidEmail(String str, boolean bCanBeBlank) {
		if (bCanBeBlank && (str == null || str == "")) return true;
		
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(str);
	}
	
	// Function to check any string to determine if it is a valid list of e-mail addresses
	public static boolean isValidEmailList(String str, boolean bCanBeBlank) {
		if (bCanBeBlank && (str == null || str == "")) return true;
		
		EmailValidator emailValidator = EmailValidator.getInstance();
		
		String[] addresses = str.split("[ ]*[,][ ]*");
		for (int i = 0; i < addresses.length; i++) {
			if (!isValidEmail(addresses[i].trim(), false)) return false;
		}
		
		return true;
	}
	
	// Function to check any string against a string of character groups that will be combined
	//  into a regex
	// If any invalid characters are found, they will not be returned in the string
	public static String makeValidString(String str, String validChars) {
		// Pattern to find all characters that are not (^) the valid characters
		Pattern pattern = Pattern.compile("[^" + validChars + "]*");
		Matcher matcher = pattern.matcher(str);
		// Remove all invalid characters and return the string
		return matcher.replaceAll("");
	}
	
	// Function to check a string to see if it is valid currency
	//   Valid is: ###.##, $###.##, #,###.##, $#,###.##
	// If valid, returns string in format ####.##
	// If not valid, returns empty string ('')
	public static String makeValidCurrency(String str) {
		if (str == null) return null;
		else if (str.trim() == "") return "";
		
		String cleanStr = str.replaceAll("[\$,]", "");
		
		double val;

		try {
			val = Double.parseDouble(cleanStr);
		} catch (NumberFormatException exc) {
			return "";
		}
		
		if(val < 0) {
			return "";
		}
		
		DecimalFormat df = new DecimalFormat("0.00");		
		return df.format(val).toString();
	}
	
	/**
	 * Determines if given string is of sufficient password strength
	 * Requirements are:
	 *  - Must contain at least 1 letter
	 *  - Must contain at least 1 number
	 *  - Must contain at least 1 punctuation character
	 */
	public static boolean isStrongPassword(String password) {
		if (password.length() < 8) {
			// passwrod too short
			return false
		}

		if (password.length() > 64) {
			// password too long
			return false
		}
		
		// Pattern to find at least 1 letter, number, and punctuation
		String alphaRegex = "(?=.*[" + ValidateUtil.ALPHA + "])";
		String numberRegex = "(?=.*[" + ValidateUtil.NUMERIC + "])";
		String punctRegex = "(?=.*[" + ValidateUtil.PUNCTUATION + "])";
		
		// See if the password matches the requirements
		Pattern pattern = Pattern.compile(alphaRegex + numberRegex + punctRegex);
		Matcher matcher = pattern.matcher(password);
		return matcher.find();
    }

    /**
     *
     * @param scriptFile
     * @return
     */
    public static parseRevisionNumberFromScript(File scriptFile) {

        //Expected header in a channel or distribution script should contain the revision number in the following format:
        /**
         *
         * Revision number   : $Rev: 31704 $
         * Last Updated Date : $LastChangedDate: 2013-04-01 20:13:28 -0400 (Mon, 01 Apr 2013) $
         *
         */

        def revisionNumber
        //Regular expression to capture revision number
        def errorRegex = ~/[$]Rev[:]\s*\d+\s*[$]/
        //Parse through each line of the script file
        scriptFile.eachLine {
            //Create a Matcher object from the RegEx
            def result = it =~ errorRegex
            if (result.find()) {
                log.info "Found a line '$it' matching regEx ${result.pattern()}"
                def revision = it.substring(it.lastIndexOf(":"))
                log.info "Substring from the last colon found: $revision"
                def number = revision.replaceAll("[^0-9]", "")
                number = number.trim()
                log.info "Cleaned value: $number"
                revisionNumber = Long.parseLong(number)
            }
        }

        log.info "Returning revision number $revisionNumber"
        return revisionNumber
    }
}