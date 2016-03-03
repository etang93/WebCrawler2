package Filter;

import java.util.concurrent.ConcurrentSkipListSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlParser extends Filter {

	private ConcurrentSkipListSet URLS;
	private int Layer;

	public UrlParser(ConcurrentSkipListSet urls, int layer) {
		URLS = urls;
		Layer = layer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			String data;
			String[] url;
			try {
				data = URLS.pollFirst().toString();
				url = data.split(",");
			} catch (Exception e) {
				write(null);
				System.out.println("UrlParser Filter closed");
				break;
			}

			try {
				int currLayer = Integer.parseInt(url[1]);
				if (currLayer < Layer) {

					try {
						write(data);
						Document htmlPage = Jsoup.connect(url[0]).get();
						Elements links = htmlPage.select("a[href]");

						for (Element link : links) {
							int newLayer = currLayer + 1;
							URLS.add(link.attr("abs:href") + "," + newLayer);
						}
					} catch (Exception ex) {

						// System.out.println("Error in UrlParser.run \n" +
						// ex.toString());
					}
				}
			} catch (Exception e) {
				System.err.println("UrlParser.run - Error parsing to int");
			}
		}
		
	}

}
