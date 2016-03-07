package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.Test;

import Filter.*;
import Utils.RobotParser;
import junit.framework.Assert;

public class UnitTests {

	@Test
	public void test() {
		ConcurrentSkipListSet<String> testUrls = new ConcurrentSkipListSet<String>();
		testUrls.add("http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html 0");
		String words = "";
		int layer = 3;
		UrlParser urlParser = new UrlParser(testUrls, layer, words);
		Pipe p = new Pipe();
		Output output = new Output();
		CreateDirectories cd = new CreateDirectories();
		urlParser.output(p);
	    cd.input(p);
		
	    p = new Pipe();
	    cd.output(p);
	    output.input(p);
		
		urlParser.run();
		cd.run();
		
		//output.run();
	}
	
	@Test
	public void RobotTest_False()
	{
		HashMap<String,String> sites = new HashMap<String, String>();
		RobotParser robot = new RobotParser("http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html", sites);
		
		Boolean actual = robot.getRobotRules();
		
		Assert.assertFalse(actual);
	} 
	
	@Test
	public void RobotTest_actualFail()
	{
		HashMap<String,String> sites = new HashMap<String,String>();
		RobotParser robot = new RobotParser("http://www.robotstxt.org/", sites);
		Boolean actual = robot.getRobotRules();
		
		Assert.assertFalse(actual); 
	}
	
	@Test
	public void RobotTest_actualPass()
	{
		//RobotParser robot = new RobotParser("");
				
	}
}
