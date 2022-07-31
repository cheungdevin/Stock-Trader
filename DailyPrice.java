
public class DailyPrice {
	public String stockID;
	public String date;
	public double close;
	public double open;
  	   			  		  	 			 	 	
	public DailyPrice(String[] data) throws Exception{ 	  	
		stockID = data[0];
        date = data[1];  
		close = Double.parseDouble(data[2]);
		open = Double.parseDouble(data[3]);
	} 	  	   			  		  	 			 	 	
}
