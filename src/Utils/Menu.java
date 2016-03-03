package Utils;

import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Menu {

	static Scanner Reader = new Scanner(System.in);
	
	public String getURL()
	{
		boolean validURL = false;
		String url = "";
		do{
			System.out.println("Please enter in a url");
			url = Reader.nextLine();
			try{
				Document htmlPage = Jsoup.connect(url).get();
				validURL = true;
			}catch(Exception ex)
			{
				System.out.println(ex.toString() + "\nError with url, please try again ");
			}
		}while(!validURL);
		
		return url;
	}
	
	public int getLayers()
	{
		boolean validNumber = false;
		String number = "";
		int returnVal = 0;
		do{
			System.out.println("Please enter in the max amount of layers you want");
			number = Reader.nextLine();
			try{
				returnVal = Integer.parseInt(number);
				validNumber = true;
			}catch(Exception ex)
			{
				System.out.println(ex.toString() + "\nError with amount of layers, please try again \n");
			}
		}while(!validNumber);
		
		return returnVal;
	}
	
	public String getWords()
	{
		ArrayList<String> words = new ArrayList<String>();
		String word;
		Boolean addMore = true;
		String answer;

		String retVal = "";
		StringBuilder sb = new StringBuilder(retVal);
		
		do{
			System.out.println("Please enter word to include ");
			word = Reader.nextLine();
			words.add(word);
			
			System.out.println("Do you wish to add another word? (y/n)"); 
			answer = Reader.nextLine();
			
			if(answer.equals("n") || answer.equals("N"))
			{
				addMore = false;
			}
			
		}while(addMore);
		
		for(int i = 0; i < words.size(); i++)
		{
			sb.append(words.get(i));
			if(i!=words.size()-1){
				sb.append("|");
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
