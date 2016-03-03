package Filter;

public class Output extends Filter{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try{
				System.out.println(read());
			}catch(Exception ex)
			{
				System.out.println("Output Filter closed");
				break;
			}
		}
	}

	
}
