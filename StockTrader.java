import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

public class StockTrader {

    public static HashMap<LocalDate,DailyPrice> dailyPrice = new HashMap<>();
    private static LocalDate today;

    private static void initDailyPrice() throws Exception{

        BufferedReader br = new BufferedReader(new FileReader("daily-price.csv"));
        String strCurrentLine;
        while ((strCurrentLine = br.readLine()) != null) {
            new DailyPrice(strCurrentLine.split(","));
        }
        br.close();
    }

    public static void main(String args[]) throws Exception{

        initDailyPrice();

        Screen.printDateInsertion();
        LocalDate inputDate = LocalDate.parse(Screen.keyboard.nextLine());
        System.out.println("Input Date: " + inputDate);

        for(int i=0;i<3;i++){
            if(dailyPrice.containsKey(inputDate)){
                today = inputDate;
                break;
            }
            inputDate = inputDate.plusDays(1);
        }
        if(today==null){
            System.out.println("Invalid input!");
        }
        Screen.printMainMenu(today);

        String option = "";

        while (!option.equals("Q")) {
            option = Screen.keyboard.nextLine();

            switch (option){
                case "1":
                    // split();
                    break;
                case "2":
                    // removeSubstring();
                    break;
                default:
                    if (option.equals("Q"))
                        System.out.println("Goodbye!");
                    else
                        Screen.printInvalidMainMenuOption();
                    break;
            }
        }
    }
}