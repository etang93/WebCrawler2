package Utils;

import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Menu {

	static Scanner Reader = new Scanner(System.in);

	private String userUrl;
	private int userLayer;
	private String userFilters;
	
	public void mainMenu(){
		System.out.println("1) Start");
		System.out.println("2) Pause");
		System.out.println("3) Resume");
		System.out.println("4) Quit");
		
		System.out.println("The above commands are available, press those keys to activate");
		
	}
	
	private void start()
	{
		userUrl = askURL();
		userLayer = askLayers();
		userFilters = askWords();
	}
	
	private String askURL() {
		boolean validURL = false;
		String url = "";

		do {
			System.out.println("Please enter in a url");
			url = Reader.nextLine();
			try {
				Document htmlPage = Jsoup.connect(url).get();
				validURL = true;
			} catch (Exception ex) {
				System.out.println(ex.toString() + "\nError with url, please try again ");
			}
		} while (!validURL);

		return url;
	}

	private int askLayers() {
		boolean validNumber = false;
		String number = "";
		int returnVal = 0;

		do {
			System.out.println("Please enter in the max amount of layers you want");
			number = Reader.nextLine();
			try {
				returnVal = Integer.parseInt(number);
				validNumber = true;
			} catch (Exception ex) {
				System.out.println(ex.toString() + "\nError with amount of layers, please try again \n");
			}
		} while (!validNumber);

		return returnVal;
	}

	private String askWords() {
		ArrayList<String> words = new ArrayList<String>();
		String word;
		Boolean add = true;
		Boolean addMore = true;
		String answer;

		String retVal = "";
		StringBuilder sb = new StringBuilder(retVal);

		do {
			System.out.println("Do you wish to filter out any words? (y/n) \n");
			answer = Reader.nextLine();

			if (answer.toLowerCase().equals("n") || answer.toLowerCase().equals("y") ){
				add = false;
			} else {
				System.out.println("Invalid Choice! \n");
			}
		} while (add);

		if (answer.toLowerCase().equals("y")) {
			do {
				System.out.println("Please enter word to include ");
				word = Reader.nextLine();
				words.add(word);

				System.out.println("Do you wish to add another word? (y/n)");
				answer = Reader.nextLine();

				if (answer.toLowerCase().equals("n")) {
					addMore = false;
				}

			} while (addMore);

			for (int i = 0; i < words.size(); i++) {
				sb.append(words.get(i));
				if (i != words.size() - 1) {
					sb.append("|");
				}
			}
		}
		retVal = sb.toString();
		sb = new StringBuilder("(");
		sb.append(retVal);
		sb.append(")");

		retVal = sb.toString();
		return retVal;
	}
	
	public String getUrl()
	{
		return userUrl;
	}
	
	public int getLayer()
	{
		return userLayer;
	}
	
	public String getWords()
	{
		return userFilters;
	}
}
