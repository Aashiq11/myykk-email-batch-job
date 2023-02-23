package com.myykk.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	public static boolean isValidEmailAddress(String hex) {
		String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
		
	}
	
	public static String rtrim(String s) {
      	if (s == null) {
      		return "";
      	}
      	
        StringBuffer sb = new StringBuffer(s);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1)))
          sb.setLength(sb.length() - 1);
        return sb.toString();
      }

}
