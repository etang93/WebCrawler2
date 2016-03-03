package main;

import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

import Filter.Output;
import Filter.Pipeline;
import Filter.UrlParser;
import Filter.WordFind;
import Utils.Menu;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ConcurrentSkipListSet storage = new ConcurrentSkipListSet<String>();
		// Menu actions
		Menu menu = new Menu();
		String url = menu.getURL();
		int layers = menu.getLayers();
		String wordList = menu.getWords();

		storage.add(url + ",0");

		UrlParser urlParser = new UrlParser(storage, layers);
		WordFind wordFind = new WordFind(wordList);
		Output output = new Output();

		Pipeline pipeline = new Pipeline(urlParser, wordFind, output);

		Thread pipelineThread = new Thread(pipeline);
		pipelineThread.start();

		/*while (true) {
			boolean pause = false;
			Scanner Reader = new Scanner(System.in);
			String test;
			System.out.println("Please enter word to include ");
			test = Reader.nextLine();

			if (test.equals("pause")) {
				pause = true;
			}
			while (pause) {
				
				urlParser.wait();
				output.wait();
				pipelineThread.wait();
			}
			if (test.equals("resume")) {
				pause=false;
			}
		}*/
		/*
		 * Pipe p = new Pipe(); urlParser.output(p); output.input(p);
		 * urlParser.run(); output.run();
		 */
		// http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html
	}
}
