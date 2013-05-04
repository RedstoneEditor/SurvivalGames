package sg.Utils;

import sg.main.SurvivalGames;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;

public class Logger {
	SurvivalGames plugin;
	public Logger(SurvivalGames instance) { this.plugin = instance; }

	private String ERROR_FORMAT(Exception ex) { return "----------( ERROR )----------\n" + ex.getMessage() + "\n\n----------( CAUSE )----------\n" + ex.getCause().toString(); }
	
	/**
	 * Logs a string into console!
	 * @param message String to log
	 */
	public void log(String message) {
		plugin.getLogger().log(Level.INFO, message); }
	
	/**
	 * Logs an error into a text file
	 * @param ex Exception to log
	 */
	public void log(Exception ex) {
		plugin.getLogger().log(Level.WARNING, ex.getMessage());
		logToFile(ERROR_FORMAT(ex));
	}
	
	private void logToFile(String message) {
		File file = new File("sg-error.log");	
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.append(message + "\n\n");
			output.close();
		} catch (Exception ex) {
			log("LOG");
		}
	}
}
