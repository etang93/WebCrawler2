package Filter;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;

public class Pipe {

	private boolean close;
	private ConcurrentSkipListSet<String> data;

	public Pipe()
	{
		data = new ConcurrentSkipListSet<String>();;
		close = false;
	}
	
	public String read() throws Exception {
		while (true) {

			if (data.isEmpty()) {
				if(close)
				{
					throw new Exception();
				}
				try {
					
					Thread.sleep(20);
				} catch (Exception ex) {
					System.err.println("Error found in pipe \n" + ex.toString());
				}
			}
			else{
				return data.pollFirst();
			}
		}
	}

	public String readLast() throws Exception {
		while (true) {

			if (data.isEmpty()) {
				if(close)
				{
					throw new Exception();
				}
				try {
					
					Thread.sleep(20);
				} catch (Exception ex) {
					System.err.println("Error found in pipe \n" + ex.toString());
				}
			}
			else{
				return data.pollLast();
			}
		}
	}
	
	public void write(String line) {
		if (close) {
			return;
		}
		data.add(line);
	}

	public void close() {
		close = true;
	}

}
