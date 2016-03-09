package Utils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Filter.CreateDirectories;
import Filter.Output;
import Filter.Pipeline;
import Filter.UrlParser;

public class Menu extends Thread {

	static Scanner Reader = new Scanner(System.in);

	private String userUrl;
	private int userLayer;
	private String userFilters;
	private Pipeline pipeLine;
	private Boolean started = false;
	private Thread pipeLineThread;
	private Boolean stop = false;
	
	public void mainMenu() throws InterruptedException {

		System.out.println("1) Start");
		System.out.println("2) Pause");
		System.out.println("3) Resume");
		System.out.println("4) Quit");
		System.out.println("The above commands are available, press those keys to activate");

	}

	public void run() {
		while (true) {
			try {
				if(stop){
					break;
				}
				System.out.println("Choose something");
				mainMenuOptions();
				Thread.sleep(500);

				if (!pipeLine.Alive()) {
					System.out.println("pipeLineThread is dead");
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error Starting mainmenu thread");
			}
		}
	}

	private void mainMenuOptions() throws InterruptedException {

		String userInput;
		int userOption = 0;
		Boolean validNumber = false;
		do {
			userInput = Reader.nextLine();
			try {
				userOption = Integer.parseInt(userInput);
				if (userOption > 0 || userOption < 5) {
					validNumber = true;
				}
			} catch (Exception ex) {
				System.out.println(ex.toString() + "\nInvalid command, please try again \n");
			}
		} while (!validNumber);

		MenuChoice(userOption);
	}

	private void MenuChoice(int choice) throws InterruptedException {
		if (choice == 1 && !started) {
			startProgram();
		} else if (choice == 2) {
			try {
				pipeLine.Pause();

			} catch (Exception ex) {
				System.out.println("Start first, program will error out now restart");
			}
		} else if (choice == 3) {
			try{
			pipeLine.Resume();
		
			}catch(Exception ex)
			{
				System.out.println("start first, program will error out now restart");
			}
		}else if (choice == 4) {
			try{pipeLine.Stop();
			stop = true;
			}catch(Exception ex)
			{
				System.out.println("start first, program will error out now restart");
			}
		}
		// http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html
	}

	private void startProgram() {
		started = true;
		ConcurrentSkipListSet storage = new ConcurrentSkipListSet<String>();
		userUrl = askURL();
		userLayer = askLayers();
		userFilters = askWords();

		storage.add(userUrl + " 0 ");

		UrlParser urlParser = new UrlParser(storage, userLayer, userFilters);
		Output output = new Output();
		CreateDirectories createDir = new CreateDirectories();
		// Pipeline pipeline = new Pipeline(urlParser, output);
		pipeLine = new Pipeline(urlParser, createDir, output);
		pipeLineThread = new Thread(pipeLine);
		pipeLineThread.setName("PipeLineThread");
		pipeLineThread.start();
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

			if (answer.toLowerCase().equals("n") || answer.toLowerCase().equals("y")) {
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

}
