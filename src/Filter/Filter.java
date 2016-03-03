package Filter;

public abstract class Filter implements Runnable{

	protected Pipe inPipe, outPipe;

	public void input(Pipe in) {
		inPipe = in;
	}

	public void output(Pipe out) {
		outPipe = out;
	}

	public void write(String data) {
		if (data != null) {
			outPipe.write(data);
		} else {
			outPipe.close();
		}
	}
	
	public String read() throws Exception
	{
		return inPipe.read();
	}

}


