package Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RobotParser {

	String url;
	Boolean userAgentFound = false;
	HashMap<String, String> sitesRobot;

	public RobotParser(String url, HashMap<String, String> sitesRobot) {
		this.url = url;
		this.sitesRobot = sitesRobot;
	}

	public Boolean getRobotRules() {
		try {
			String root = getRootUrl();

			if (checkHashExists(url)) {
				if (url.contains(sitesRobot.get(root))) {
					return true;
				} else {
					return false;
				}
			} else {
				return addAndCheck(root);
			}

		} catch (Exception ex) {
			return false;
		}
	}

	private String getRootUrl() {
		try {
			URL parsed = new URL(url);
			String protocal = parsed.getProtocol();
			String root = parsed.getHost();

			StringBuilder sb = new StringBuilder(protocal);
			sb.append("://");
			sb.append(root);
			return sb.toString();

		} catch (Exception ex) {
			return null;
		}
	}

	private Boolean addAndCheck(String root) {
		addHash(root);
		return checkHashMap(root);

	}

	private Boolean checkHashExists(String root) {
		if (sitesRobot.containsKey(root))
			return true;
		else
			return false;
	}

	private Boolean checkHashMap(String root) {
		try {
			if (url.contains(sitesRobot.get(root))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.err.println("");
			return false;
		}
	}

	private void addHash(String root) {
		StringBuilder robotUrl = new StringBuilder(root);
		robotUrl.append("/robots.txt");

		StringBuilder regex = new StringBuilder("(");

		try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(robotUrl.toString()).openStream()))) {
			String line = null;
			while ((line = in.readLine()) != null) {

				if (userAgentFound) {
					if (line.contains("Disallow")) {
						line = line.replace("Disallow:", "");
						if (line.trim().equals("")) {
							// do nothing swallow it
						} else {
							regex.append(line + "|");
						}
					}

					if (line.contains("User-agent")) {
						if (regex.length() > 1) {
							regex.setLength(regex.length() - 1);
						}
						break;
					}
				}
				if (line.contains("User-agent: *")) {
					userAgentFound = true;
				}
			}

			regex.append(")");
			sitesRobot.put(root, regex.toString());
		} catch (Exception ex) {
			System.out.println("robot.txt doesn't exist for " + root);
		}
	}
}
