public class TradeRecord {
	public String date;
	public String stockID;
	public double price;
	public int shares;
	public int direction;
	public double amount;
  	   			  		  	 	
	public TradeRecord(){}

	public TradeRecord(String[] data){ 	  	
		date = data[0];
		stockID = data[1];
		price = Double.parseDouble(data[2]);
		shares = Integer.parseInt(data[3]);
		direction = Integer.parseInt(data[4]);
		amount = shares*price;
	} 

	public void updateAmount(){
		amount = shares*price;
	}
	

}


