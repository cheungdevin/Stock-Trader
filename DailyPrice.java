import java.time.*;
import java.util.*;


public class DailyPrice {
	public String stockID;
	public Date date;
	public double close;
	public double open;
  	   			  		  	 			 	 	
	public DailyPrice(String[] data) throws Exception{ 	  	
		stockID = data[0];
		// new Date(2000, 11, 21);
		// Date date = parse(data[1]);
		// LocalDate date = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d-MMM-yyyy"));
		close = Double.parseDouble(data[2]);
		open = Double.parseDouble(data[3]);
	} 	  	   			  		  	 			 	 	
}
