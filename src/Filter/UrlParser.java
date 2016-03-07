package Filter;

import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Utils.RobotParser;

public class UrlParser extends Filter {

	private ConcurrentSkipListSet URLS;
	private int Layer;
	private String regex;
	private RobotParser robot;
	private HashMap<String, String> sitesRobot = new HashMap<String,String>();
	public UrlParser(ConcurrentSkipListSet urls, int layer, String regex) {
		URLS = urls;
		Layer = layer;
		this.regex = regex;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName() + " started");
		while (true) {

			try {
				Pause();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Error Pausing");
			}
			if(stop)
			{
				System.out.println(Thread.currentThread().getName() + " Stopped");
				break;
			}
			String data;
			String[] url;
			try {
				data = URLS.pollFirst().toString();
				url = data.split(" ");
			} catch (Exception e) {
				write(null);
				System.out.println("UrlParser Filter closed");
				break;
			}

			try {
				int currLayer = Integer.parseInt(url[1]);
				if (currLayer < Layer) {

					try {
						robot = new RobotParser(url[0], sitesRobot);
						if (!robot.getRobotRules()) {
							write(data);
							Document htmlPage = Jsoup.connect(url[0]).get();
							Elements links = htmlPage.select("a[href]");

							for (Element link : links) {
								int newLayer = currLayer + 1;
								String childUrl = link.attr("abs:href").toString();
								if (wordFind(childUrl)) {
									URLS.add(link.attr("abs:href") + " " + newLayer + " " + url[0] +"-"+ currLayer);
								}
							}
						}
					} catch (Exception ex) {

						// System.out.println("Error in UrlParser.run \n" +
						// ex.toString());
					}
				}
			} catch (Exception e) {
				System.err.println("UrlParser.run - Error parsing to int " + data);
			}
		}

	}

	public Boolean wordFind(String url) {
		Pattern p = Pattern.compile(regex);
		Matcher m;

		try {
			Document htmlPage = Jsoup.connect(url).get();
			Elements links = htmlPage.select("body");
			for (Element link : links) {
				String body = link.tagName("body").text();
				m = p.matcher(body);
				if (m.find()) {
					return true;
				}
			}
		} catch (Exception ex) {

		}
		return false;
	}

}
