package Filter;

import java.util.ArrayList;

public class Pipeline implements Runnable {

	private Filter[] filters;
	//protected Boolean pause = false;
	protected Boolean messageSent = false;
	private ArrayList<Thread> threads = new ArrayList<Thread>();

	public Pipeline(Filter... filters) {
		this.filters = filters;

		for (int i = 0; i < filters.length - 1; i++) {
			Pipe p = new Pipe();
			filters[i].output(p);
			filters[i + 1].input(p);
		}
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " started");
		for (Filter filter : filters) {
			threads.add(new Thread(filter, filter.getClass().getName()));
			// (new Thread(filter)).start();
		}
		for (Thread th : threads) {
			th.start();

		}

		System.out.println(Thread.currentThread().getName() + "Finished executing");
	}

	public void Resume() {
		System.out.println("Resuming threads...");
		//pause = false;
		
		for(Thread th : threads)
		{
			for(Filter filter : filters)
			{
				filter.setPauseFalse();
			}
		}
	}

	public void Pause() throws InterruptedException {
		System.out.println("Pausing threads..");
		
		for (Thread th : threads) {

			for (Filter filter : filters) {
				filter.setPauseTrue();
			}
		}
	}

	public Boolean Alive() {
		for (Thread th : threads) {
			if (th.isAlive())
				return true;
		}
		return false;
	}

	public void Stop() {
		System.out.println("Stopping Threads..");
		for (Thread th : threads) {
			for(Filter filter : filters)
			{
				filter.Stop();
			}
		}
		
	}
}
