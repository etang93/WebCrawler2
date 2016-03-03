package Filter;

import java.io.IOException;
import java.util.regex.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordFind extends Filter {

	String words;

	public WordFind(String words) {
		this.words = words;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			String data;
			String[] url;
			Pattern p = Pattern.compile(words);
			Matcher m;
			try {
				data = read();
			} catch (Exception e) {
				write(null);
				System.out.println("WordFind Filter closed");
				break;
			}
			url = data.split(",");

			Document htmlPage;
			try {
				htmlPage = Jsoup.connect(url[0]).get();
				Elements links = htmlPage.select("body");
				for (Element link : links) {
					String body = link.tagName("body").text();
					m = p.matcher(body);
					if(m.find())
					{
						write(data);
					}					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}

}
