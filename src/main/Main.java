package main;

import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

import Filter.CreateDirectories;
import Filter.Output;
import Filter.Pipeline;
import Filter.UrlParser;
import Filter.WordFind;
import Utils.Menu;
import Utils.RobotParser;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ConcurrentSkipListSet storage = new ConcurrentSkipListSet<String>();
		// Menu actions
		Menu menu = new Menu();
		menu.mainMenu();
		String url = menu.getUrl();
		int layers = menu.getLayer();
		String wordList = menu.getWords();

		storage.add(url + ",0");

		UrlParser urlParser = new UrlParser(storage, layers, wordList);
		Output output = new Output();
		CreateDirectories createDir= new CreateDirectories();
		Pipeline pipeline = new Pipeline(urlParser, output);

		Thread pipelineThread = new Thread(pipeline);
		pipelineThread.start();

	
		// http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html
	}
}
