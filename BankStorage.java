import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//we will use json-simple for the json files
//i have included the library in the org folder

//this class is the basic class for storage within a json file
public class BankStorage extends Storage{
    //variables
    private final String storagePath = "storage.json";
    private final int numTransactions = 10;
    private UserManagement um;
    private LoanManagement lm;
    private StockManagement sm;
    private BaseBank bankPointer;
    //stocks to be added later

    //constructor
    public BankStorage(BaseBank bank){
        um = new UserManagement();
        lm = new LoanManagement();
        sm = new StockManagement();
        bankPointer = bank;
    }

    //method to write the current object to the filepath
    public void writeStorage(){
        //main object to hold the storage
        JSONObject storage = new JSONObject();
        //array of users
        JSONArray users = new JSONArray();
        //get the users from the usermanagement
        HashMap<String, User> usersHash = um.getUsers();
        //get the loans from LoanManagement
        HashMap<String, List<LoanReceipt>> loanInfo = lm.getLoanReceipts();
        //add them 1 by 1 to the array
        for(String key : usersHash.keySet()){
            JSONObject user = new JSONObject();
            User hashUser = usersHash.get(key);
            user.put("username", hashUser.getUsername());
            user.put("password", hashUser.getPassword());
            //if the user is a normalUser
            if(hashUser.getType() == UserType.NORMAL){
                NormalUser normUser = (NormalUser) hashUser;
                String username = normUser.getUsername();
                //fill out their account info
                JSONArray accountInfo = writeAccounts(normUser.getAccounts());
                user.put("type", "normal");
                user.put("Accounts", accountInfo);

                //add their loan info
                JSONArray loanArr = new JSONArray();
                if(loanInfo.containsKey(username)){
                    ArrayList<LoanReceipt> loans = (ArrayList<LoanReceipt>)loanInfo.get(username);
                    for(int x=0; x<loans.size(); x++){
                        LoanReceipt curr = loans.get(x);
                        JSONObject currJSON = new JSONObject();
                        currJSON.put("amount", curr.getAmount());
                        currJSON.put("currency", curr.getCurrency());
                        loanArr.add(currJSON);
                    }
                }
                user.put("Loans", loanArr);

            }
            //else if they are an admin
            else{
                user.put("type", "admin");
            }

            //add it
            users.add(user);
        }
        storage.put("users", users);
        //store the loan interest rate
        storage.put("Interest", "" + lm.getRate());
        //add stock info
        HashMap<String, Stock> stocks = sm.getStocks();
        JSONArray stocksJ = new JSONArray();
        for(String key : stocks.keySet()){
            Stock stock = stocks.get(key);
            JSONObject stockJ = new JSONObject();
            stockJ.put("name", stock.getName());
            stockJ.put("price", stock.getPrice());
            stocksJ.add(stockJ);
        }
        storage.put("Stocks", stocksJ);

        //add exchange rates
        JSONArray rates = new JSONArray();
        HashMap<String, Double> exchangeRates = bankPointer.getRates();
        for(String key : exchangeRates.keySet()){
            JSONObject curr = new JSONObject();
            double rate = exchangeRates.get(key);
            curr.put("currency", key);
            curr.put("rate", rate);
            rates.add(curr);
        }
        storage.put("ExchangeRates", rates);

        //write it all to the file
        try(FileWriter file = new FileWriter(storagePath)){
            file.write(storage.toJSONString());
            file.flush();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //helper method to write the accounts to a jsonArray
    public JSONArray writeAccounts(List<Account> accounts){
        JSONArray accountInfo = new JSONArray();
        for(int i=0; i<accounts.size(); i++){
            JSONObject account = new JSONObject();
            Account curr = accounts.get(i);
            account.put("id", curr.getID());
            account.put("balance", curr.getBalance("USD"));
            if(curr.getType() == AccountType.CHECKING){
                account.put("type", "Checking");
            }
            else if(curr.getType() == AccountType.SAVING){
                account.put("type", "Saving");
            }
            else if(curr.getType() == AccountType.INVESTMENT){
                InvestmentAccount iCurr = (InvestmentAccount) curr;
                account.put("type", "Investment");
                //add stock info since it's an investment account
                JSONArray holdStocksJ = new JSONArray();
                JSONArray soldStocksJ = new JSONArray();
                HashMap<String, List<Share>> holdStocks = iCurr.getHoldStocks();
                HashMap<String, List<Share>> soldStocks = iCurr.getSoldStocks();
                for(String key : holdStocks.keySet()){
                    ArrayList<Share> shares = new ArrayList<Share>(holdStocks.get(key));
                    JSONArray sharesJ = new JSONArray();
                    for(int x=0; x<shares.size(); x++){
                        Share share = shares.get(x);
                        JSONObject shareJ = new JSONObject();
                        shareJ.put("name", share.getName());
                        shareJ.put("boughtPrice", share.getBoughtPrice());
                        shareJ.put("soldPrice", share.getSoldPrice());
                        shareJ.put("isSold", share.getSold());
                        sharesJ.add(shareJ);
                    }
                    holdStocksJ.add(sharesJ);
                }
                for(String key : soldStocks.keySet()){
                    ArrayList<Share> shares = new ArrayList<Share>(soldStocks.get(key));
                    JSONArray sharesJ = new JSONArray();
                    for(int x=0; x<shares.size(); x++){
                        Share share = shares.get(x);
                        JSONObject shareJ = new JSONObject();
                        shareJ.put("name", share.getName());
                        shareJ.put("boughtPrice", share.getBoughtPrice());
                        shareJ.put("soldPrice", share.getSoldPrice());
                        shareJ.put("isSold", share.getSold());
                        sharesJ.add(shareJ);
                    }
                    soldStocksJ.add(sharesJ);
                }
                account.put("holdStocks", holdStocksJ);
                account.put("soldStocks", soldStocksJ);

            }
            //add transaction information
            //add up to the past specified transactions
            JSONArray transactions = new JSONArray();
            int count = 0;
            for(Transaction t : curr.getTransactions()){
                if(count == numTransactions){
                    break;
                }
                JSONObject tran = new JSONObject();
                tran.put("username", t.getUsername());
                tran.put("type", t.getType().name());
                tran.put("amount", "" + t.getAmount());
                tran.put("currency", t.getCurrency());
                tran.put("success", t.isSuccess());
                transactions.add(tran);
                count++;
            }
            account.put("transactions", transactions);
            accountInfo.add(account);
        }
        return accountInfo;
    }

    //method to read the info from the file and set the object accordingly
    public void readStorage(){
        //reset the current storage info
        um = new UserManagement();
        lm = new LoanManagement();
        sm = new StockManagement();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(storagePath)) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject infoStorage = (JSONObject) obj;
            JSONArray users = (JSONArray) infoStorage.get("users");
            //make a new user for each user in the array
            for(int i=0; i<users.size(); i++){
                JSONObject currUser = (JSONObject) users.get(i);
                String type = (String) currUser.get("type");
                if(type.equals("normal")){
                    String username = (String) currUser.get("username");
                    String password = (String) currUser.get("password");
                    NormalUser user = new NormalUser(username, password);

                    //now for each account we add it to the user
                    JSONArray accounts = (JSONArray) currUser.get("Accounts");
                    for(int x=0; x<accounts.size(); x++){
                        //add it to the user accounts
                        JSONObject account = (JSONObject) accounts.get(x);
                        String accType = (String) account.get("type");
                        int id = ((Number) account.get("id")).intValue();
                        int balance = ((Number) account.get("balance")).intValue();
                        Account acc;
                        if(accType.equals("Checking")){
                            acc = new CheckingAccount(id, username);
                        }
                        else if(accType.equals("Saving")){
                            acc = new SavingAccount(id, username);
                        }
                        else{ //for investment accounts also add in the stock info
                            acc = new InvestmentAccount(id, username);
                            JSONArray holdStocksJ = (JSONArray) account.get("holdStocks");
                            JSONArray soldStocksJ = (JSONArray) account.get("soldStocks");
                            HashMap<String, List<Share>> holdStocks = new HashMap<String, List<Share>>();
                            HashMap<String, List<Share>> soldStocks = new HashMap<String, List<Share>>();
                            //go through the held stocks
                            if(holdStocksJ!=null && soldStocksJ!=null){
                                for(int z=0; z<holdStocksJ.size(); z++){
                                    JSONArray currArr = (JSONArray) holdStocksJ.get(z);
                                    ArrayList<Share> shares = new ArrayList<Share>();
                                    String arrName = "";
                                    //go through each type of stock and its shares and add them to an arraylist
                                    for(int a=0; a<currArr.size(); a++){
                                        JSONObject currShare = (JSONObject) currArr.get(a);
                                        String name = (String) currShare.get("name");
                                        arrName = name;
                                        Double boughtPrice = ((Number) currShare.get("boughtPrice")).doubleValue();
                                        Double soldPrice = ((Number) currShare.get("soldPrice")).doubleValue();
                                        Share share;
                                        if(soldPrice > 0){
                                            share = new Share(name, boughtPrice, soldPrice, true);
                                        }
                                        else{
                                            share = new Share(name, boughtPrice, soldPrice, false);
                                        }
                                        shares.add(share);
                                    }
                                    holdStocks.put(arrName, shares);
                                }
                                //go through the sold stocks
                                for(int z=0; z<soldStocksJ.size(); z++){
                                    JSONArray currArr = (JSONArray) soldStocksJ.get(z);
                                    ArrayList<Share> shares = new ArrayList<Share>();
                                    String arrName = "";
                                    //go through each type of stock and its shares and add them to an arraylist
                                    for(int a=0; a<currArr.size(); a++){
                                        JSONObject currShare = (JSONObject) currArr.get(a);
                                        String name = (String) currShare.get("name");
                                        arrName = name;
                                        Double boughtPrice = ((Number) currShare.get("boughtPrice")).doubleValue();
                                        Double soldPrice = ((Number) currShare.get("soldPrice")).doubleValue();
                                        Share share;
                                        if(soldPrice > 0){
                                            share = new Share(name, boughtPrice, soldPrice, true);
                                        }
                                        else{
                                            share = new Share(name, boughtPrice, soldPrice, false);
                                        }
                                        shares.add(share);
                                    }
                                    soldStocks.put(arrName, shares);
                                }
                                ((InvestmentAccount)acc).setHoldStocks(holdStocks);
                                ((InvestmentAccount)acc).setSoldStocks(soldStocks);
                            }

                        }
                        //add the transactions
                        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
                        JSONArray transJSON = (JSONArray) account.get("transactions");
                        for(int j = 0; j<transJSON.size(); j++){
                            Transaction transaction;
                            JSONObject curr = (JSONObject) transJSON.get(j);
                            String typeTran = (String) curr.get("type");
                            String usernameTran = (String) curr.get("username");
                            double amount = Double.parseDouble((String) curr.get("amount"));
                            String currency = (String) curr.get("currency");
                            //assume that it is always a success since if it is in the list it is a success
                            if(type.equals("DEPOSIT")){
                                transaction = new Transaction(usernameTran, TransactionType.DEPOSIT, amount, currency, true);
                            }
                            else{
                                transaction = new Transaction(usernameTran, TransactionType.WITHDRAW, amount, currency, true);
                            }
                            transactions.add(transaction);
                        }

                        acc.setTransactions(transactions);
                        acc.addMoney(balance);
                        user.addAccount(acc);
                        bankPointer.addAccount(acc);
                    }
                    //add loans for each user
                    JSONArray loansJ = (JSONArray) currUser.get("Loans");
                    ArrayList<LoanReceipt> loanInfo = new ArrayList<LoanReceipt>();
                    for(int b = 0; b<loansJ.size(); b++){
                        JSONObject currLoan = (JSONObject) loansJ.get(b);
                        String currency = (String) currLoan.get("currency");
                        double amount = ((Number) currLoan.get("amount")).doubleValue();
                        LoanReceipt loan = new LoanReceipt(username, amount, currency);
                        loanInfo.add(loan);
                    }
                    lm.addLoanReceipt(username, loanInfo);

                    //add the user
                    um.addUser(user);
                }
                // if it's a bank manager
                else{
                    String username = (String) currUser.get("username");
                    String password = (String) currUser.get("password");
                    BankManager bm = new BankManager(username, password);
                    um.addUser(bm);
                    //set the interest rate from file
                    String intRateStr = (String) infoStorage.get("Interest");
                    double intRate = Double.parseDouble(intRateStr);
                    bm.setLoanInterestRate(intRate);
                }
            }

            //set stock info
            JSONArray stocks = (JSONArray) infoStorage.get("Stocks");
            for(int i=0; i<stocks.size(); i++){
                JSONObject stockJ = (JSONObject) stocks.get(i);
                String name = (String) stockJ.get("name");
                double price = ((Number) stockJ.get("price")).doubleValue();
                Stock stock = new Stock(name, price);
                sm.addStock(stock);
            }

            JSONArray rates = (JSONArray) infoStorage.get("ExchangeRates");
            for(int i=0; i<rates.size(); i++){
                JSONObject rateJ = (JSONObject) rates.get(i);
                String currency = (String) rateJ.get("currency");
                double rate = ((Number)rateJ.get("rate")).doubleValue();
                bankPointer.AddExchangeRate(currency, rate);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //getters/setters
    public UserManagement getUM(){
        return um;
    }

    public void setUM(UserManagement um){
        this.um = um;
    }

    public LoanManagement getLM(){
        return lm;
    }

    public void setLM(){
        this.lm = lm;
    }

    public StockManagement getSM(){
        return sm;
    }

    public void setSM(){
        this.sm = sm;
    }
}
