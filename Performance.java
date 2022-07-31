public class Performance {
	public String stockID;
	public String date;
	public double profit;
    public double nav;
    public double dividend;
  	   			  		  	 			 	 	
	public Performance(String[] data) throws Exception{ 	  	
		stockID = data[0];
		date = data[1];
	    profit = Double.parseDouble(data[2]);
        nav = Double.parseDouble(data[3]);
        dividend = Double.parseDouble(data[4]);
	} 	  	   			  		  	 			 	 	
}


