package logcontrol;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import customExceptions.ContextFormatException;
import customExceptions.DateFormatException;
import customExceptions.NameFormatException;
import customExceptions.SeverityFormatException;
import customExceptions.TextFormatException;
import customExceptions.SessionIdFormatException;
import logdata.LogfileEntry;
import logdata.Severity;


public class LogfileParser {
		
	private Pattern dateRegex = Pattern.compile("(\\[([\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2})\\])");
	private Pattern sessionIdRegex = Pattern.compile("(\\[([a-z0-9]{17})\\])");
	private Pattern nameRegex = Pattern.compile("(\\]\\W([a-zA-z0-9]+)\\.)");
	private Pattern severityRegex = Pattern.compile("(\\.(INFO|ERROR|WARNING):)");
	private Pattern textRegex = Pattern.compile("(:\\W(.+)\\W\\[)");
	private Pattern contextRegex = Pattern.compile("(\\[([A-Z]+)\\])");
	
	
	
	public ArrayList<LogfileEntry> parseFile(ArrayList<String> rawEntries) throws Exception {
		ArrayList<LogfileEntry> logfileEntries = new ArrayList<>();
		
		String datePattern = "yyyy-MM-dd' 'HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
		
		String rawDate = null;
		Date date = null;
		
		String sessionId = null;
		String name = null;
		String rawSeverity = null;		
		Severity severity = null;
		
		String text= null;		
		String context= null;
		
		int ENTRY_INDEX = 1;
		
		for(String re : rawEntries) {	
						
			
			rawDate = getRegexMatch(dateRegex, re, 2);
			if(rawDate == null) throw new DateFormatException(ENTRY_INDEX);
			date = dateFormat.parse(rawDate);
			
			
			
			sessionId = getRegexMatch(sessionIdRegex, re, 2);
			if(sessionId == null) throw new SessionIdFormatException(ENTRY_INDEX);
			
			name = getRegexMatch(nameRegex, re, 2);
			if(name == null) throw new NameFormatException(ENTRY_INDEX);
			
			rawSeverity = getRegexMatch(severityRegex, re, 2);
			if(rawSeverity == null) throw new SeverityFormatException(ENTRY_INDEX);
			for(Severity sev : Severity.values()) {
				if(rawSeverity.equals(sev.toString())) {
					severity = sev;
				}
			}
			
			text = getRegexMatch(textRegex, re, 2);
			if(text == null) throw new TextFormatException(ENTRY_INDEX);
			
			context = getRegexMatch(contextRegex, re, 2);
			if(context == null) throw new ContextFormatException(ENTRY_INDEX);
	
	
			logfileEntries.add(LogfileEntry.builder()
					.date(date)
					.sessionId(sessionId)
					.appName(name)
					.severity(severity)
					.text(text)
					.context(context)
					.build()
			);	
			
			ENTRY_INDEX++;
		}
		
		return logfileEntries;
	}
	 
	/**
	 * This function takes a regular expression und checks the entry for 
	 * the needed data. It returns the data as a String.  
	 * */
	private String getRegexMatch(Pattern theRegex, String str2Check, int groupIndex) {
		
		Matcher regexMatcher = theRegex.matcher(str2Check);
				
		if(regexMatcher.find()) {
			if(regexMatcher.group().length() != 0) {				
				return regexMatcher.group(groupIndex).trim();
			}
		}
		
		return null;
	}
		
}
