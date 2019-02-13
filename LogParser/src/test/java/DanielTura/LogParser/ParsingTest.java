package DanielTura.LogParser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import customExceptions.ContextFormatException;
import customExceptions.DateFormatException;
import customExceptions.LogfileParserException;
import customExceptions.NameFormatException;
import customExceptions.SessionIdFormatException;
import customExceptions.SeverityFormatException;
import customExceptions.TextFormatException;
import logcontrol.LogfileParser;
import logcontrol.LogfileReader;
import logdata.LogfileEntry;


class ParsingTest {
	
	static LogfileParser logfileParser;
	
	static File logOkay = new File("testResources/logfileOKAY.log");
	static File logDateError = new File("testResources/logfileDateError.log");
	static File logSessionIdError = new File("testResources/logfileSessionIDError.log");
	static File logNameError = new File("testResources/logfileNameError.log");
	static File logSeverityError = new File("testResources/logfileSeverityError.log");
	static File logTextError = new File("testResources/logfileTextError.log");
	static File logContextError = new File("testResources/logfileContextError.log");
	
	static ArrayList<String> rawOkayEntries;
	static ArrayList<String> rawDateEntries;
	static ArrayList<String> rawSessionIdEntries;
	static ArrayList<String> rawNameEntries;
	static ArrayList<String> rawSeverityEntries;
	static ArrayList<String> rawTextEntries;
	static ArrayList<String> rawContextEntries;
	
	static ArrayList<LogfileEntry> parsedOkayEntries;
	static ArrayList<LogfileEntry> parsedDateEntries;
	static ArrayList<LogfileEntry> parsedSessionIdEntries;
	static ArrayList<LogfileEntry> parsedNameEntries;
	static ArrayList<LogfileEntry> parsedSeverityEntries;
	static ArrayList<LogfileEntry> parsedTextEntries;
	static ArrayList<LogfileEntry> parsedContextEntries;
	
	static LogfileReader okayReader;
	static LogfileReader dateErrorReader;
	static LogfileReader sessionIdErrorReader;
	static LogfileReader nameErrorReader;
	static LogfileReader severityErrorReader;
	static LogfileReader textErrorReader;
	static LogfileReader contextErrorReader;
	
	
	@BeforeAll
	public static void setUp() throws IOException {
		
		logfileParser = new LogfileParser();
		
		okayReader = new LogfileReader();
		okayReader.loadFile(logOkay);
		okayReader.readFile();
		
		dateErrorReader = new LogfileReader();
		dateErrorReader.loadFile(logDateError);
		dateErrorReader.readFile();
		
		sessionIdErrorReader = new LogfileReader();
		sessionIdErrorReader.loadFile(logSessionIdError);
		sessionIdErrorReader.readFile();
		
		nameErrorReader = new LogfileReader();
		nameErrorReader.loadFile(logNameError);
		nameErrorReader.readFile();
		
		severityErrorReader = new LogfileReader();
		severityErrorReader.loadFile(logSeverityError);
		severityErrorReader.readFile();
		
		textErrorReader = new LogfileReader();
		textErrorReader.loadFile(logTextError);
		textErrorReader.readFile();
		
		contextErrorReader = new LogfileReader();
		contextErrorReader.loadFile(logContextError);
		contextErrorReader.readFile();
		
		rawOkayEntries = okayReader.getRawEntryStrings();
		rawDateEntries = dateErrorReader.getRawEntryStrings();
		rawSessionIdEntries = sessionIdErrorReader.getRawEntryStrings();
		rawNameEntries = nameErrorReader.getRawEntryStrings();
		rawSeverityEntries = severityErrorReader.getRawEntryStrings();
		rawTextEntries = textErrorReader.getRawEntryStrings();
		rawContextEntries = contextErrorReader.getRawEntryStrings();
		
	}
	
	
	@Test
	void testOkay_shouldNotThrow() throws Exception  {
		
		LogfileParserException exc = null;
		try {
			parsedOkayEntries = logfileParser.parseFile(rawOkayEntries);
		} catch(LogfileParserException e) {
			exc = e;
		}
		
		assertNull(exc);
	}
	
	@Test
	void testDateError_shouldThrowDateFormatException() throws Exception  {
		
		DateFormatException exc = null;
		try {
			parsedDateEntries = logfileParser.parseFile(rawDateEntries);
		} catch(DateFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testSessionIdError_shouldthrowSessionIdFormatException() throws Exception  {
		
		SessionIdFormatException exc = null;
		try {
			parsedSessionIdEntries = logfileParser.parseFile(rawSessionIdEntries);
		} catch(SessionIdFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testNameError_shouldThrowNameFormatException() throws Exception  {
		
		NameFormatException exc = null;
		try {
			parsedNameEntries = logfileParser.parseFile(rawNameEntries);
		} catch(NameFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testSeverityError_ShouldThrowSeverityFormatException() throws Exception  {
		
		SeverityFormatException exc = null;
		try {
			parsedSeverityEntries = logfileParser.parseFile(rawSeverityEntries);
		} catch(SeverityFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testTextError_shouldThrowTextFormatException() throws Exception  {
		
		TextFormatException exc = null;
		try {
			parsedTextEntries = logfileParser.parseFile(rawTextEntries);
		} catch(TextFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testContextError_shouldThrowContextFormatException() throws Exception  {
		
		ContextFormatException exc = null;
		try {
			parsedContextEntries = logfileParser.parseFile(rawContextEntries);
		} catch(ContextFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
}
