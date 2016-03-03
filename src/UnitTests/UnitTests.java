package UnitTests;

import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.Test;

import Filter.*;

public class UnitTests {

	@Test
	public void test() {
		ConcurrentSkipListSet<String> testUrls = new ConcurrentSkipListSet<String>();
		testUrls.add("http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html,0");
		String words = "(Facts)";
		int layer = 4;
		UrlParser urlParser = new UrlParser(testUrls, layer);
		Pipe p = new Pipe();
		WordFind wf = new WordFind(words);
		Output output = new Output();
		
		urlParser.output(p);
		wf.input(p);
		p = new Pipe();
		wf.output(p);
		output.input(p);
		
		
		urlParser.run();
		wf.run();
		output.run();
	}

}
