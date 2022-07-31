public class ShareHolding {
	public String stockID;
	public int share;
	public double averagePrice;
	public double totalProfit;
  	   			  		  	 			 	 	
	public ShareHolding(String id,int s){
		stockID = id;
		share = s;
	}

	public ShareHolding(String[] data){ 	  	
		stockID = data[0];
		share = Integer.parseInt(data[1]);
		averagePrice = Double.parseDouble(data[2]);
		totalProfit = Double.parseDouble(data[3]);
	} 
	
	public void updateAveragePriceAndTotalProfit(TradeRecord[] tradeRecordArray){
		double sum = 0;
		int count = 0;
		double totalBuy = 0;
		double totalSell = 0;
		for(int i=0;i<tradeRecordArray.length;i++){
			if(tradeRecordArray[i].stockID.equals(stockID)){
				sum += tradeRecordArray[i].price;
				count ++;
				if(tradeRecordArray[i].direction==1){
					totalBuy += tradeRecordArray[i].amount;
				}
				if(tradeRecordArray[i].direction==2){
					totalSell += tradeRecordArray[i].amount;
				}
			}
		}
		averagePrice = sum/(double)count;
		totalProfit = totalBuy - totalSell;
	}
}


