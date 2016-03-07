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

	static Scanner Reader = new Scanner(System.in);

	public static void main(String[] args) throws InterruptedException {

		// Menu actions
		Menu menu = new Menu();
		menu.mainMenu();
		(new Thread(menu)).start();
		// http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html
	}
}
