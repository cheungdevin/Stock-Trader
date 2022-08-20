import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyPrice {
	public String stockID;
	public LocalDate date;
	public double close;
	public double open;
  	   			  		  	 			 	 	
	public DailyPrice(String[] data) throws Exception{ 	  	
		stockID = data[0];
		date = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		close = Double.parseDouble(data[2]);
		open = Double.parseDouble(data[3]);
		StockTrader.dailyPrice.put(date, this);
	} 	  	   			  		  	 			 	 	
}
