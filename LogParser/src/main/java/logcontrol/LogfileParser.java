package logcontrol;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import customExceptions.ContextFormatException;
import customExceptions.DateFormatException;
import customExceptions.NameSeverityFormatException;
import customExceptions.SessionIdFormatException;
import logdata.LogfileEntry;
import logdata.Severity;


public class LogfileParser {
		
	private String dateRegex = "^(\\[[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}\\])";
	private String sessionIdRegex = "^(\\[[a-z0-9]{17}\\])";
	private String nameAndSevRegex = "^(([a-zA-Z0-9]+)\\.((INFO)|(WARNING)|(ERROR)):)"; 
	private String contextRegex = "(\\[(.+)\\])$";
	
	
	public ArrayList<LogfileEntry> parseFile(ArrayList<String> rawEntries) throws Exception {
		ArrayList<LogfileEntry> logfileEntries = new ArrayList<>();
				
		SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		
		String rawDate = null;
		Date date = null;
		
		String sessionId = null;
	
		String[] nameAndSev= null;
		String name = null;
		String rawSeverity = null;		
		Severity severity = null;
		
		String text= null;		
		String context= null;
		
		for(String re : rawEntries) {	
			
			String rest = re;	
			
			if(checkSyntax(dateRegex, rest) == true) {
				rawDate = getRegexMatch(dateRegex, rest);
				rawDate = rawDate.substring(1, rawDate.indexOf("]"));
				rest = rest.substring(rawDate.length() + 3);
			}
			else {
				throw new DateFormatException();
			}
			
			if(checkSyntax(sessionIdRegex, rest) == true) {
				sessionId = getRegexMatch(sessionIdRegex, rest);
				sessionId  = sessionId.substring(1, sessionId.indexOf("]"));
				rest = rest.substring(sessionId.length() + 3);
			}
			else {
				throw new SessionIdFormatException();
			}
				
			if(checkSyntax(nameAndSevRegex, rest) == true)  {
				nameAndSev = getRegexMatch(nameAndSevRegex, rest).split(Pattern.quote("."));
				name = nameAndSev[0];		
				rawSeverity = nameAndSev[1].replace(":", "");			
				rest = rest.substring(name.length() + rawSeverity.length() + 3);
			}
			else {
				throw new NameSeverityFormatException();
			}
			
			if(checkSyntax(contextRegex, rest) == true)  {
				context = getRegexMatch(contextRegex, rest);
				text = rest.substring(0, rest.indexOf(context));
				context = context.substring(1, context.length()-1);
			}
			else {
				throw new ContextFormatException();
			}
			
			//rawDate wird von String zu Date überführt
			//Kann ParseException werfen. Sollte danke
			//der Regexüberprüfung aber nicht.
			date = myDateFormat.parse(rawDate);			
			
			//Hier die Severity einmal richtig parsen
			for(Severity sev : Severity.values()) {
				if(rawSeverity.equals(sev.toString())) {
					severity = sev;
				}
			}
			
			logfileEntries.add(LogfileEntry.builder()
					.date(date)
					.sessionId(sessionId)
					.appName(name)
					.severity(severity)
					.text(text)
					.context(context)
					.build()
			);	
		}
		return logfileEntries;
	}
	
	public String parseBack(LogfileEntry le) {
		String reconstructed = "["+le.getDate()+"] ["+le.getSessionId()+"] "
				+le.getAppName()+"."+le.getSeverity()+": "+le.getText()+" ["
				+le.getContext()+"]";
		return reconstructed;
	}
	
	private boolean checkSyntax(String theRegex, String str2Check) {
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(str2Check);
				
		while(regexMatcher.find()) {
			if(regexMatcher.group().length() != 0) {				
				return true;
			}
		}
		
		return false;
	}
	
	private String getRegexMatch(String theRegex, String str2Check) {
		
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(str2Check);
		
		
		while(regexMatcher.find()) {
			if(regexMatcher.group().length() != 0) {				
				return regexMatcher.group().trim();
			}
		}
		
		return null;
	}
		
}
