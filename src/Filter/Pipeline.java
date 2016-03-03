package Filter;

public class Pipeline implements Runnable{

	private Filter[] filters;
	
	public Pipeline(Filter ... filters){
		this.filters = filters;
		
		for(int i = 0; i<filters.length-1; i++)
		{
			Pipe p = new Pipe();
			filters[i].output(p);
			filters[i+1].input(p);
		}
	}
	
	public void run()
	{
		for(Filter filter : filters)
		{
			(new Thread(filter)).start();
		}
	}
}
