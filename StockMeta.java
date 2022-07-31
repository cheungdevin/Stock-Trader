public class StockMeta {
	public String stockID;
	public int type;
	public String[] includedList;
	// public DailyPrice[] dailyPrice;
  	   			  		  	 			 	 	
	public StockMeta(String[] data) throws Exception{ 	  	
		stockID = data[0];
		type = Integer.parseInt(data[1]);
		if(data.length>2){
			includedList = new String[data.length-2];
			int j=0; 
			for(int i=2;i<data.length;i++){
				includedList[j]=data[i];
				j++;
			}
		}
	} 	  	   			  		  	 			 	 	
}


