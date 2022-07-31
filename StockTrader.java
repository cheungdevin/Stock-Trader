import java.io.*;
import java.util.Scanner;

public class StockTrader {
    public static DailyPrice[] dailyPriceArray;
    public static StockMeta[] stockMetaArray;
    public static TradeRecord[] tradeRecordArray;
    public static ShareHolding[] shareHoldingsArray;
    public static Performance[] performanceArray;
    public static double accCash = 1000000;
    public static double overallProfit = 0;
    public static String today;
    public static String[] avaliableDate;
    
    
    public static void main(String[] args) throws Exception {
        dailyPriceArray = initDailyPrice();
        stockMetaArray = initStockMeta();
        tradeRecordArray = initTradeRecord();
        shareHoldingsArray = initShareHolding();
        performanceArray = initPerformance();

        updateCash();

        Screen.printDateInsertion();

        today = Screen.keyboard.nextLine();

        for(int i=0;i < dailyPriceArray.length;i++){
            if(dailyPriceArray[i].date.equals(today)){
                break;
            }else if(compareDate(today, dailyPriceArray[i+1].date)){
                today = dailyPriceArray[i+1].date;
                break;
            }
        }

        int option = 0;

        do{
            Screen.printMainMenu(today);
            boolean optValid = false;
            while(!optValid){
                try { 
                    Screen.printSelectOption();
                    String tempOpt = Screen.keyboard.nextLine();
                    option = Integer.parseInt(tempOpt); 
                    optValid = true;
                }  
                catch (Exception e) { 
                    Screen.printInvalidMainMenuOption();
                } 
                if(optValid == true){
                    if(option>0 && option <7){
                        optValid = true;
                    }else{
                        optValid = false;
                        Screen.printInvalidMainMenuOption();
                    }
                }
            }
            

            if(option == 1){
                while(true){
                    Screen.printAccInfo(accCash,overallProfit);
                    int recordNum = 0;//TO-DO
                    if(shareHoldingsArray!=null){
                        for(int i=0;i<shareHoldingsArray.length;i++){
                            recordNum++;
                            Screen.printShareHoldingOption(recordNum,shareHoldingsArray[i].stockID,shareHoldingsArray[i].share,shareHoldingsArray[i].averagePrice,shareHoldingsArray[i].totalProfit);
                        }
                    }

                    Screen.printEnquiryTradingRecordsInPeriodOption(recordNum+1);
                    Screen.printBackOption(recordNum+2);

                    Screen.printSelectOption();
                    int subOption = Screen.keyboard.nextInt();
                    Screen.keyboard.nextLine();

                    if(subOption<=recordNum){
                        String targetID = shareHoldingsArray[subOption-1].stockID;
                        int count = 0;
                        for(int i=0;i<tradeRecordArray.length;i++){
                            if(tradeRecordArray[i].stockID.equals(targetID)){
                                count++;
                            }
                        }
                        TradeRecord[] recordInRange = new TradeRecord[count];
                        count=0;
                        for(int i=0;i<tradeRecordArray.length;i++){
                            if(tradeRecordArray[i].stockID.equals(targetID)){
                                recordInRange[count] = tradeRecordArray[i];
                                count++;
                            }
                        }
                        t5(recordInRange);
                    }

                    if(subOption==recordNum+1){
                        boolean valid = false;
                        String startDay;
                        String endDay;
                        do{
                            Screen.printStartDatePrompt();
                            startDay = Screen.keyboard.nextLine();

                            Screen.printEndDatePrompt();
                            endDay = Screen.keyboard.nextLine();

                            if(compareDate(endDay, startDay)){ 
                                Screen.printInvalidDatePrompt();
                                valid = false;
                            }else{
                                valid = true;
                            }
                        }while(!valid);

                        int count = 0;
                        for(int i=0;i<tradeRecordArray.length;i++){
                            if(compareDate(startDay,tradeRecordArray[i].date) && compareDate(tradeRecordArray[i].date, endDay)){
                                count++;
                            }
                        }
                        TradeRecord[] recordInRange = new TradeRecord[count];
                        count=0;
                        for(int i=0;i<tradeRecordArray.length;i++){
                            if(compareDate(startDay,tradeRecordArray[i].date) && compareDate(tradeRecordArray[i].date, endDay)){
                                recordInRange[count] = tradeRecordArray[i];
                                count++;
                            }
                        }
                        t5(recordInRange);
                    }

                    if(subOption==recordNum+2){
                        break;
                    }
                }
            }
            if(option == 2){
                //T6
                String inputStockId = inputValidStockID();
                DailyPrice selectedDailyPrice = getDailyPrice(inputStockId,today);
                StockMeta selectedMeta = getStockMeta(inputStockId);
                
                if(selectedMeta.type==1){
                    double nav = 0;
                    double profit = 0;
                    double dividend = 0;
                    for(int i=0;i<performanceArray.length-1;i++){
                        if(inputStockId.equals(performanceArray[i].stockID) &&  compareDate(performanceArray[i].date,today)){
                            nav = performanceArray[i].nav;
                            profit = performanceArray[i].profit;
                            dividend = performanceArray[i].dividend;
                            break;
                        }
                    }
                    Screen.printListedCompanyInfo(inputStockId,selectedDailyPrice.close,profit,nav,dividend);//To DO
                    int subOption = t6(0);
                    if(subOption==3){
                        continue;
                    }
                    if(subOption==1){
                        t7(selectedDailyPrice);
                    }
                    if(subOption==2){
                        boolean valid = false;
                        String startDay;
                        String endDay;
                        do{
                            Screen.printStartDatePrompt();
                            startDay = Screen.keyboard.nextLine();

                            Screen.printEndDatePrompt();
                            endDay = Screen.keyboard.nextLine();

                            if(compareDate(endDay, startDay)){ 
                                Screen.printInvalidDatePrompt();
                                valid = false;
                            }else{
                                valid = true;
                            }
                        }while(!valid);
                        if(compareDate(today,endDay)){
                            endDay = today;
                        }
                        for(int i=0;i<dailyPriceArray.length;i++){
                            if(dailyPriceArray[i].stockID.equals(selectedDailyPrice.stockID)){
                                if(dailyPriceArray[i].date.equals(startDay) || dailyPriceArray[i].date.equals(endDay) || (compareDate(startDay, dailyPriceArray[i].date) && compareDate(dailyPriceArray[i].date, endDay))){
                                    Screen.printPrice(dailyPriceArray[i].date,dailyPriceArray[i].close);
                                }
                            }
                                
                        }
                    }
                }
                if(selectedMeta.type==2){
                    while(true){
                        Screen.printStockPriceInfo(inputStockId,selectedDailyPrice.close);
                        for(int i=0;i<selectedMeta.includedList.length;i++){
                            Screen.printStockOption(i+1,selectedMeta.includedList[i]);
                        }
                        int subOption = t6(selectedMeta.includedList.length);

                        if(subOption==selectedMeta.includedList.length+3){
                            break;
                        }
                        inputStockId = selectedMeta.includedList[subOption-1];
                        selectedDailyPrice = getDailyPrice(inputStockId,today);
                        selectedMeta = getStockMeta(inputStockId);

                        if(selectedMeta.type==1){
                            Screen.printListedCompanyInfo(inputStockId,selectedDailyPrice.close,0,0,0);//Todo
                            subOption = t6(0);
                            if(subOption==3){
                                continue;
                            }
                            if(subOption==1){
                                t7(selectedDailyPrice);
                            }
                            if(subOption==2){
                                boolean valid = false;
                                String startDay;
                                String endDay;
                                do{
                                    Screen.printStartDatePrompt();
                                    startDay = Screen.keyboard.nextLine();

                                    Screen.printEndDatePrompt();
                                    endDay = Screen.keyboard.nextLine();

                                    if(compareDate(endDay, startDay)){ 
                                        Screen.printInvalidDatePrompt();
                                        valid = false;
                                    }else{
                                        valid = true;
                                    }
                                }while(!valid);
                                if(compareDate(today,endDay)){
                                    endDay = today;
                                }
                                for(int i=0;i<dailyPriceArray.length;i++){
                                    if(dailyPriceArray[i].stockID.equals(selectedDailyPrice.stockID)){
                                        if(dailyPriceArray[i].date.equals(startDay) || dailyPriceArray[i].date.equals(endDay) || (compareDate(startDay, dailyPriceArray[i].date) && compareDate(dailyPriceArray[i].date, endDay))){
                                            Screen.printPrice(dailyPriceArray[i].date,dailyPriceArray[i].close);
                                        }
                                    }
                                        
                                }
                            }
                        }
                    }
                    continue;
                }
            }

            if(option == 3){
                String inputStockId = inputValidStockID();
                DailyPrice selectedDailyPrice = getDailyPrice(inputStockId, today);
                t7(selectedDailyPrice);
                continue;
            }

            if(option==4){
                Screen.printEndDatePrompt();
                String endDay = Screen.keyboard.nextLine();
                while(compareDate(endDay, today)){
                    Screen.printInvalidAutoTradeEndDatePrompt();
                    endDay = Screen.keyboard.nextLine();
                }
                int count=0;
                for(int i=0;i<dailyPriceArray.length;i++){
                    if(dailyPriceArray[i].stockID.equals("00700")){
                        if(dailyPriceArray[i].date.equals(today) || dailyPriceArray[i].date.equals(endDay) || (compareDate(today, dailyPriceArray[i].date) && compareDate(dailyPriceArray[i].date, endDay))){
                            count++;
                        }
                    }
                }
                DailyPrice[] autoDailyPrice = new DailyPrice[count];
                count=0;
                for(int i=0;i<dailyPriceArray.length;i++){
                    if(dailyPriceArray[i].stockID.equals("00700")){
                        if(dailyPriceArray[i].date.equals(today) || dailyPriceArray[i].date.equals(endDay) || (compareDate(today, dailyPriceArray[i].date) && compareDate(dailyPriceArray[i].date, endDay))){
                            autoDailyPrice[count] = dailyPriceArray[i];
                            count++;
                            if(count==autoDailyPrice.length){
                                break;
                            }
                        }
                    }
                }
                today = autoDailyPrice[0].date;
                boolean holding = false;
                if(getShareHolding("00700")!=null){
                    holding = true;
                }
                
                for(int i=1;i<autoDailyPrice.length;i++){
                    if(holding && autoDailyPrice[i].close < autoDailyPrice[i-1].close){
                        ShareHolding selectedHolding = getShareHolding("00700");
                        Screen.printAutoTradeMsg(autoDailyPrice[i].date,"00700",2,autoDailyPrice[i].close,selectedHolding.share,selectedHolding.share*autoDailyPrice[i].close);
                        sellStock(autoDailyPrice[i], selectedHolding, selectedHolding.share);
                        holding = false;
                    }
                    if((!holding) && autoDailyPrice[i].close > autoDailyPrice[i-1].close){
                        int shares = (int)(accCash/autoDailyPrice[i].close);
                        Screen.printAutoTradeMsg(autoDailyPrice[i].date,"00700",1,autoDailyPrice[i].close,shares,shares*autoDailyPrice[i].close);
                        buyStock(autoDailyPrice[i], shares);
                        holding = true;
                    }
                }
                today = autoDailyPrice[autoDailyPrice.length-1].date;
            }
            if(option == 5){
                for(int i=0;i < dailyPriceArray.length-1;i++){
                    if(dailyPriceArray[i].date.equals(today) && compareDate(today, dailyPriceArray[i+1].date) ){
                        today = dailyPriceArray[i+1].date;
                        break;
                    }
                }
            }
            writeTradeRecord();
            writeShareHolding();
        }while(option != 6);
    }

    private static boolean compareDate(String a, String b){
        String yyyy = a.substring(6, 10);
        String mm = a.substring(3, 5);
        String dd = a.substring(0, 2);
        String strA = yyyy + mm + dd;

        yyyy = b.substring(6, 10);
        mm = b.substring(3, 5);
        dd = b.substring(0, 2);
        String strB = yyyy + mm + dd;

        return Integer.parseInt(strA) < Integer.parseInt(strB);
    }

    private static DailyPrice[] initDailyPrice() throws Exception{

        File file = new File("daily-price.csv");
        Scanner sc = new Scanner(file);
		int dpLines = 0;
		while (sc.hasNextLine()) {
            sc.nextLine();
			dpLines++;
		}
		sc.close();

		DailyPrice[] dailyPriceArray = new DailyPrice[dpLines];

		file = new File("daily-price.csv");
        sc = new Scanner(file);
        int index = 0;
        while(sc.hasNextLine()){
            String[] data = sc.nextLine().split(",");
            dailyPriceArray[index] = new DailyPrice(data);
            index++;
        }
		sc.close();

        return dailyPriceArray;
    }

    private static StockMeta[] initStockMeta() throws Exception{

        File file = new File("stock-meta.csv");
        Scanner sc = new Scanner(file);
		int dpLines = 0;
		while (sc.hasNextLine()) {
            sc.nextLine();
			dpLines++;
		}
		sc.close();

		StockMeta[] stockMetaArray = new StockMeta[dpLines];

		file = new File("stock-meta.csv");
        sc = new Scanner(file);
        int index = 0;
        while(sc.hasNextLine()){
            String[] data = sc.nextLine().split(",");
            stockMetaArray[index] = new StockMeta(data);
            index++;
        }
		sc.close();

        return stockMetaArray;
    }

    private static TradeRecord[] initTradeRecord() throws Exception{

        File file = new File("trade-record.csv");
        if (!file.createNewFile()) {
            Scanner sc = new Scanner(file);
            int dpLines = 0;
            while (sc.hasNextLine()) {
                sc.nextLine();
                dpLines++;
            }
            sc.close();

            TradeRecord[] tradeRecordArray = new TradeRecord[dpLines];

            file = new File("trade-record.csv");
            sc = new Scanner(file);
            int index = 0;
            while(sc.hasNextLine()){
                String[] data = sc.nextLine().split(",");
                tradeRecordArray[index] = new TradeRecord(data);
                index++;
            }
            sc.close();

            return tradeRecordArray;
        }
        return null;
    }

    private static ShareHolding[] initShareHolding() throws Exception{

        File file = new File("shares-holding.csv");
        if (!file.createNewFile()) {
            Scanner sc = new Scanner(file);
            int dpLines = 0;
            while (sc.hasNextLine()) {
                sc.nextLine();
                dpLines++;
            }
            sc.close();

            ShareHolding[] shareHoldingArray = new ShareHolding[dpLines];

            file = new File("shares-holding.csv");
            sc = new Scanner(file);
            int index = 0;
            while(sc.hasNextLine()){
                String[] data = sc.nextLine().split(",");
                shareHoldingArray[index] = new ShareHolding(data);
                index++;
            }
            sc.close();

            return shareHoldingArray;
        }
        return null;
    }

    private static Performance[] initPerformance() throws Exception{
        
        File file = new File("performance.csv");
        Scanner sc = new Scanner(file);
		int dpLines = 0;
		while (sc.hasNextLine()) {
            sc.nextLine();
			dpLines++;
		}
		sc.close();

		Performance[] performanceArray = new Performance[dpLines];

		file = new File("performance.csv");
        sc = new Scanner(file);
        int index = 0;
        while(sc.hasNextLine()){
            String[] data = sc.nextLine().split(",");
            performanceArray[index] = new Performance(data);
            index++;
        }
		sc.close();

        return performanceArray;
       
    }

    private static String inputValidStockID() throws Exception{
        
        boolean valid = true;
        String inputStockId;
        
        do{
            if(valid){
                Screen.printEnterStockId();
                inputStockId = Screen.keyboard.nextLine();
            }else{
                Screen.printInvalidStockId();
                Screen.printEnterStockId();
                inputStockId = Screen.keyboard.nextLine();  
            }
            
            if(inputStockId==null || inputStockId.trim().length()==0){
                valid = false;
            }else{
                valid = false;
                for(int i=0;i<stockMetaArray.length;i++){
                    if(stockMetaArray[i].stockID.equals(inputStockId)){
                        valid = true;
                        break;
                    }
                }
            }
        }while(!valid);
        
        return inputStockId;
    }

    private static StockMeta getStockMeta(String stockID){
        for(int i=0;i<stockMetaArray.length;i++){
            if(stockMetaArray[i].stockID.equals(stockID)){
                return stockMetaArray[i];
            }
        }
        return null;
    }

    private static DailyPrice getDailyPrice(String stockID, String today) {
        for(int i=0;i<dailyPriceArray.length;i++){
            if(dailyPriceArray[i].date.equals(today) && dailyPriceArray[i].stockID.equals(stockID)){
                return dailyPriceArray[i];
            }
        }
        return null;
    }   

    private static ShareHolding getShareHolding(String stockID) {
        for(int i=0;i<shareHoldingsArray.length;i++){
            if(shareHoldingsArray[i].stockID.equals(stockID)){
                return shareHoldingsArray[i];
            }
        }
        return null;
    }

    private static void t5(TradeRecord[] recordInRange){
        for(int i=recordInRange.length-1;i>=0;i--){
            Screen.printTradeRecord(recordInRange[i].date,recordInRange[i].stockID,recordInRange[i].direction,recordInRange[i].price,recordInRange[i].shares,recordInRange[i].price*recordInRange[i].shares);
        }
        do{
            Screen.printOrderMenu();
            int subOption = Screen.keyboard.nextInt();
            Screen.keyboard.nextLine();
            if(subOption==6) return;
            if(subOption==1){//todo sort again
                for(int i=recordInRange.length-1;i>=0;i--){
                    Screen.printTradeRecord(recordInRange[i].date,recordInRange[i].stockID,recordInRange[i].direction,recordInRange[i].price,recordInRange[i].shares,recordInRange[i].price*recordInRange[i].shares);
                }
                continue;
            }
            if(subOption==2){
                boolean sorted = false;
                TradeRecord temp;
                while(!sorted) {
                    sorted = true;
                    for (int i = 0; i < recordInRange.length - 1; i++) {
                        if (recordInRange[i].price < recordInRange[i+1].price) {
                            temp = recordInRange[i];
                            recordInRange[i] = recordInRange[i+1];
                            recordInRange[i+1] = temp;
                            sorted = false;
                        }
                    }
                }
            }
            if(subOption==3){
                boolean sorted = false;
                TradeRecord temp;
                while(!sorted) {
                    sorted = true;
                    for (int i = 0; i < recordInRange.length - 1; i++) {
                        if (recordInRange[i].shares < recordInRange[i+1].shares) {
                            temp = recordInRange[i];
                            recordInRange[i] = recordInRange[i+1];
                            recordInRange[i+1] = temp;
                            sorted = false;
                        }
                    }
                }
            }
            if(subOption==4){
                boolean sorted = false;
                TradeRecord temp;
                while(!sorted) {
                    sorted = true;
                    for (int i = 0; i < recordInRange.length - 1; i++) {
                        if (recordInRange[i].amount < recordInRange[i+1].amount) {
                            temp = recordInRange[i];
                            recordInRange[i] = recordInRange[i+1];
                            recordInRange[i+1] = temp;
                            sorted = false;
                        }
                    }
                }
            }
            if(subOption==5){
                boolean sorted = false;
                TradeRecord temp;
                while(!sorted) {
                    sorted = true;
                    for (int i = 0; i < recordInRange.length - 1; i++) {
                        if (Integer.parseInt(recordInRange[i].stockID) > Integer.parseInt(recordInRange[i+1].stockID)) {
                            temp = recordInRange[i];
                            recordInRange[i] = recordInRange[i+1];
                            recordInRange[i+1] = temp;
                            sorted = false;
                        }
                    }
                } 
            }
            for(int i=0;i<recordInRange.length;i++){
                Screen.printTradeRecord(recordInRange[i].date,recordInRange[i].stockID,recordInRange[i].direction,recordInRange[i].price,recordInRange[i].shares,recordInRange[i].amount);
            }
        }while(true);
    }

    private static int t6(int i){    
        Screen.printTradeOption(i+1);
        Screen.printPriceInDateRangeOption(i+2);
        Screen.printBackOption(i+3);
        Screen.printSelectOption();
        int subOption = Screen.keyboard.nextInt();
        Screen.keyboard.nextLine();
        
        return subOption;
    }

    private static void t7(DailyPrice selectedDailyPrice){
        do{
            Screen.printTradeMenu();
            Screen.printSelectOption();
            int opt = Screen.keyboard.nextInt();
            Screen.keyboard.nextLine();
            if(opt==1){
                int shares=0;
                if(accCash<selectedDailyPrice.close){
                    Screen.printInsufficientCash();
                    continue;
                }else{
                    int max = (int)(accCash/selectedDailyPrice.close);
                    Screen.printAmountOfTradeSharePrompt(max);
                    shares = Screen.keyboard.nextInt();
                    Screen.keyboard.nextLine();
                    while(shares>max){
                        Screen.printInsufficientCash();
                        Screen.printAmountOfTradeSharePrompt(max);
                        shares = Screen.keyboard.nextInt();
                        Screen.keyboard.nextLine();
                    }
                }
                buyStock(selectedDailyPrice,shares);
                
            }
            if(opt==2){
                boolean canSell = false;
                ShareHolding selectedHolding=null;
                for(int i=0;i<shareHoldingsArray.length;i++){
                    if(shareHoldingsArray[i].stockID.equals(selectedDailyPrice.stockID)){
                        selectedHolding=shareHoldingsArray[i];
                        canSell = true;
                        break;
                    }
                }
                if(!canSell){
                    Screen.printInsufficientShare();
                    return;
                }
                Screen.printAmountOfTradeSharePrompt(selectedHolding.share);
                int shares = Screen.keyboard.nextInt();
                Screen.keyboard.nextLine();
                while(shares>selectedHolding.share){
                    Screen.printInsufficientShare();
                    Screen.printAmountOfTradeSharePrompt(selectedHolding.share);
                    shares = Screen.keyboard.nextInt();
                    Screen.keyboard.nextLine();
                }
                sellStock(selectedDailyPrice,selectedHolding,shares);
            }
            if(opt==3){
                return;
            }
        }while(true);
    }

    private static void newTradeRecord(DailyPrice selecteDailyPrice, int shares, int direction){
	    TradeRecord[] temp = tradeRecordArray;
	    tradeRecordArray = new TradeRecord[tradeRecordArray.length+1];
	    for(int i=0;i<tradeRecordArray.length-1;i++){
	        tradeRecordArray[i]=temp[i];
	    }
	    tradeRecordArray[tradeRecordArray.length-1] = new TradeRecord();
        tradeRecordArray[tradeRecordArray.length-1].date = selecteDailyPrice.date;
        tradeRecordArray[tradeRecordArray.length-1].stockID = selecteDailyPrice.stockID;
        tradeRecordArray[tradeRecordArray.length-1].price = selecteDailyPrice.close;
        tradeRecordArray[tradeRecordArray.length-1].shares = shares;
        tradeRecordArray[tradeRecordArray.length-1].direction = direction;
        tradeRecordArray[tradeRecordArray.length-1].updateAmount();

    }

    private static void newShareHolding(String stockID, int share){
	    ShareHolding[] temp = shareHoldingsArray;
	    shareHoldingsArray = new ShareHolding[shareHoldingsArray.length+1];
	    for(int i=0;i<shareHoldingsArray.length-1;i++){
	        shareHoldingsArray[i]=temp[i];
	    }
	    shareHoldingsArray[shareHoldingsArray.length-1] = new ShareHolding(stockID,share);
    }

    private static void deleteHolding(ShareHolding selectedHolding){
	    ShareHolding[] temp = shareHoldingsArray;
	    shareHoldingsArray = new ShareHolding[shareHoldingsArray.length-1];
        int count = 0;
	    for(int i=0;i<shareHoldingsArray.length;i++){
            if(temp[count]==selectedHolding){
                count++;
            }
	        shareHoldingsArray[i]=temp[count];
	    }
    }

    private static void buyStock(DailyPrice selectedDailyPrice,int shares){
        for(int i=0;i<shareHoldingsArray.length;i++){
            if(shareHoldingsArray[i].stockID.equals(selectedDailyPrice.stockID)){
                shareHoldingsArray[i].share += shares;
                newTradeRecord(selectedDailyPrice,shares,1);
                accCash -= (selectedDailyPrice.close*shares);
                return;
            }
        }
        newTradeRecord(selectedDailyPrice,shares,1);
        newShareHolding(selectedDailyPrice.stockID,shares);
        accCash -= (selectedDailyPrice.close*shares);
    }

    private static void sellStock(DailyPrice selectedDailyPrice, ShareHolding selectedHolding, int shares){
        newTradeRecord(selectedDailyPrice,shares,2);
        selectedHolding.share -= shares;
        accCash += (selectedDailyPrice.close*shares);
        if(selectedHolding.share==0){
            deleteHolding(selectedHolding);
        }
    }

    private static void writeTradeRecord() throws IOException {
        FileWriter fw = new FileWriter("trade-record.csv");
 
        for (int i = 0; i < tradeRecordArray.length; i++) {
            String result = tradeRecordArray[i].date + "," + 
                            tradeRecordArray[i].stockID + "," + 
                            tradeRecordArray[i].price + "," + 
                            tradeRecordArray[i].shares + "," + 
                            tradeRecordArray[i].direction + "\n";
            fw.write(result);
        }
        fw.close();
    }

    private static void writeShareHolding() throws IOException {
        FileWriter fw = new FileWriter("shares-holding.csv");
 
        for (int i = 0; i < shareHoldingsArray.length; i++) {
            shareHoldingsArray[i].updateAveragePriceAndTotalProfit(tradeRecordArray);
            String result = shareHoldingsArray[i].stockID + "," + 
                            shareHoldingsArray[i].share + "," + 
                            shareHoldingsArray[i].averagePrice + "," + 
                            shareHoldingsArray[i].totalProfit + "\n";
            fw.write(result);
        }
        fw.close();
    }

    private static void updateCash(){
        for(int i=0;i<tradeRecordArray.length;i++){
            if(tradeRecordArray[i].direction==1){
                accCash -= tradeRecordArray[i].amount;
            }
            if(tradeRecordArray[i].direction==2){
                accCash += tradeRecordArray[i].amount;
            }
        }
    }
}
