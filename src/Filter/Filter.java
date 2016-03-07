package Filter;

public abstract class Filter implements Runnable {

	protected Pipe inPipe, outPipe;
	protected Boolean pause = false;
	protected Boolean messageSent = false;
	protected Boolean stop = false;

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

	public String read() throws Exception {
		return inPipe.read();
	}

	public String readLast() throws Exception {
		return inPipe.readLast();
	}

	public void setPauseFalse() {
		pause = false;
	}

	public void setPauseTrue()
	{
		pause = true;
	}
	public void Pause() throws Exception {
		while (pause) {
			if (!messageSent) {
				System.out.println(Thread.currentThread().getName() + " Paused");
				messageSent = true;
			}
			Thread.currentThread().sleep(50);
		}
		if (messageSent) {
			System.out.println(Thread.currentThread().getName() + " Resumed");
			messageSent = false;
		}
	}
	
	public void Stop() {
		
		stop = true;
		//System.out.println(threadName + " Stopped");
		
	}

}
