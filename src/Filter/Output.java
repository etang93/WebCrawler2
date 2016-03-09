package Filter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Output extends Filter {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName() + " started");
		while (true) {
			try {
				Pause();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Pause failed");
			}

			if (stop) {
				System.out.println(Thread.currentThread().getName() + " Stopped");
				break;
			}
			try {
				String data = read();
				String[] urlInfo = data.split(" ");
				String url = urlInfo[1];
				String body = getBody(url);
				System.out.println(data + " " + body);
			} catch (Exception ex) {
				System.out.println("Output Filter closed");
				break;
			}
		}

	}

	private String getBody(String url) {
		try {
			Document htmlPage = Jsoup.connect(url).get();
			Elements body = htmlPage.select("body");

			//String bodyText = body.tagName("body").text();

			return body.toString();

		} catch (Exception ex) {
			return "";
		}
	}
}
