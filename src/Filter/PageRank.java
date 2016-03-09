package Filter;

import java.util.concurrent.ConcurrentHashMap;

public class PageRank extends Filter{

	ConcurrentHashMap<String, Integer> pageRankData;
	
	public PageRank()
	{
		pageRankData = new ConcurrentHashMap<String, Integer>();
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Pause();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Failed to pause");
			}
			
			if(stop)
			{
				System.out.println(Thread.currentThread().getName() + " Stopped");
				break;
			}
			
			
		}
		
	}
	
	private void rank(String url)
	{
		
	}
}
