package Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class RobotParser {

	String url;

	public RobotParser(String url) {
		this.url = url;
	}

	public String[] getRobotRules() {
		StringBuilder robotUrl = new StringBuilder(url);
		robotUrl.append("/robots.txt");
		ArrayList<String> disallows = new ArrayList<String>();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(robotUrl.toString()).openStream()))) {
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.contains("Disallow")) {
					line.replace("Disallow:", "");
					disallows.add(line.trim());
				}
			}
			
			return (String[]) disallows.toArray();
			
		} catch (Exception ex) {
			return null;
		}
	}
}
