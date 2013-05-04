package sg.Utils;

import sg.main.SurvivalGames;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;

public class Logger {
	SurvivalGames plugin;
	File errorlogFile = new File("sg-error.log");
	
	public Logger(SurvivalGames instance) { this.plugin = instance; }
	
	public void log(String TYPE, String message) {
		switch (TYPE) {
		case "LOG":
			plugin.getLogger().log(Level.INFO, message);
			break;
		case "ERROR":
			plugin.getLogger().log(Level.SEVERE, message);
			logToFile(message, errorlogFile);
			break;
		default:
			log("LOG", "Wrong log TYPE!");
			break;
		}
	}

	void logToFile(String message, File dest) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(dest));
			output.append(message + "\n\n");
			output.close();
		} catch (Exception ex) {
			log("LOG", ex.getMessage());
		}
	}
}
