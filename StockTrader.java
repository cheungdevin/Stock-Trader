import java.util.*;
import java.io.*;


public class StockTrader {

    private static ArrayList<DailyPrice> dailyPriceAL = new ArrayList<>();

    private static void initDailyPrice() throws Exception{

        BufferedReader br = new BufferedReader(new FileReader("daily-price.csv"));
        String strCurrentLine;
        while ((strCurrentLine = br.readLine()) != null) {
            dailyPriceAL.add(new DailyPrice(strCurrentLine.split(",")));
        }
        br.close();
    }

    public static void main(String args[]) throws Exception{

        initDailyPrice();

        for(int i=0;i<10;i++){
            System.out.println(dailyPriceAL.get(i).date);
        }

        // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // Screen.printDateInsertion();
        // String dateInString = Screen.keyboard.nextLine();
        // Date today = formatter.parse(dateInString);


        // String option = "";

        // while (!option.equals("Q")) {
        //     System.out.println("=========== Options ============");
        //     System.out.println("1: Split string");
        //     System.out.println("2: Remove all substring");
        //     System.out.println("3: Shift string");
        //     System.out.println("4: Count vowels");
        //     System.out.println("5: Ceaser cipher");
        //     System.out.println("6: First unique char");
        //     System.out.println("7: Compare string");
        //     System.out.println("8: Reverse string");
        //     System.out.println("================================");
        //     System.out.println("Please choose an option (type in Q if you want to quit): ");
        //     option = reader.next();

        //     switch (option){
        //         case "1":
        //             split();
        //             break;
        //         case "2":
        //             removeSubstring();
        //             break;
        //         default:
        //             if (option.equals("Q"))
        //                 System.out.println("Goodbye!");
        //             else
        //                 System.out.println("Invalid Option! Please try again: ");
        //             break;
        //     }
        // }
    }
}