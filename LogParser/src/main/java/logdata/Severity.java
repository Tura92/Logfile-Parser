package logdata;

public enum Severity {
	
	INFO {
	    public String toString() {
	        return "INFO";
	    }
	},

	WARNING {
	    public String toString() {
	        return "WARNING";
	    }
	},
	
	ERROR {
	    public String toString() {
	        return "ERROR";
	    }
	}
}
