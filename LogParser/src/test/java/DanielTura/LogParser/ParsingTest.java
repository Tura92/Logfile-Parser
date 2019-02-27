package DanielTura.LogParser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import control.logcontrol.LogfileParser;
import control.logcontrol.LogfileReader;
import customExceptions.ContextFormatException;
import customExceptions.DateFormatException;
import customExceptions.NameFormatException;
import customExceptions.SessionIdFormatException;
import customExceptions.SeverityFormatException;
import customExceptions.TextFormatException;


class ParsingTest {
	
	static LogfileParser logfileParser;
	
	static File logOkay = new File("src/testResources/logfileOKAY.log");
	static File logDateError = new File("src/testResources/logfileDateError.log");
	static File logSessionIdError = new File("src/testResources/logfileSessionIDError.log");
	static File logNameError = new File("src/testResources/logfileNameError.log");
	static File logSeverityError = new File("src/testResources/logfileSeverityError.log");
	static File logTextError = new File("src/testResources/logfileTextError.log");
	static File logContextError = new File("src/testResources/logfileContextError.log");
	
	static ArrayList<String> rawOkayEntries;
	static ArrayList<String> rawDateErrorEntries;
	static ArrayList<String> rawSessionIdErrorEntries;
	static ArrayList<String> rawNameErrorEntries;
	static ArrayList<String> rawSeverityErrorEntries;
	static ArrayList<String> rawTextErrorEntries;
	static ArrayList<String> rawContextErrorEntries;
	
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
		rawDateErrorEntries = dateErrorReader.getRawEntryStrings();
		rawSessionIdErrorEntries = sessionIdErrorReader.getRawEntryStrings();
		rawNameErrorEntries = nameErrorReader.getRawEntryStrings();
		rawSeverityErrorEntries = severityErrorReader.getRawEntryStrings();
		rawTextErrorEntries = textErrorReader.getRawEntryStrings();
		rawContextErrorEntries = contextErrorReader.getRawEntryStrings();
		
	}
	
	
	@Test
	void testOkay_shouldNotThrow() throws Exception  {
		
		Exception exc = null;
		try {
			logfileParser.parseFile(rawOkayEntries);
		} catch(Exception e) {
			exc = e;
		}
		
		assertNull(exc);
	}
	
	@Test
	void testDateError_shouldThrowDateFormatException() throws Exception  {
		
		DateFormatException exc = null;
		try {
			logfileParser.parseFile(rawDateErrorEntries);
		} catch(DateFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testSessionIdError_shouldthrowSessionIdFormatException() throws Exception  {
		
		SessionIdFormatException exc = null;
		try {
			logfileParser.parseFile(rawSessionIdErrorEntries);
		} catch(SessionIdFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testNameError_shouldThrowNameFormatException() throws Exception  {
		
		NameFormatException exc = null;
		try {
			logfileParser.parseFile(rawNameErrorEntries);
		} catch(NameFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testSeverityError_ShouldThrowSeverityFormatException() throws Exception  {
		
		SeverityFormatException exc = null;
		try {
			logfileParser.parseFile(rawSeverityErrorEntries);
		} catch(SeverityFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testTextError_shouldThrowTextFormatException() throws Exception  {
		
		TextFormatException exc = null;
		try {
			logfileParser.parseFile(rawTextErrorEntries);
		} catch(TextFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
	
	@Test
	void testContextError_shouldThrowContextFormatException() throws Exception  {
		
		ContextFormatException exc = null;
		try {
			logfileParser.parseFile(rawContextErrorEntries);
		} catch(ContextFormatException e) {
			exc = e;
		}
		
		assertNotNull(exc);
	}
}
