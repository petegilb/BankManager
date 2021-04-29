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
    private UserManagement um;
    private BaseBank bankPointer;
    //stocks to be added later

    //constructor
    public BankStorage(BaseBank bank){
        um = new UserManagement(bank);
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
        //add them 1 by 1 to the array
        for(String key : usersHash.keySet()){
            JSONObject user = new JSONObject();
            User hashUser = usersHash.get(key);
            user.put("username", hashUser.getUsername());
            user.put("password", hashUser.getPassword());
            //if the user is a normalUser
            if(hashUser.getType() == UserType.NORMAL){
                NormalUser normUser = (NormalUser) hashUser;
                //fill out their account info
                JSONArray accountInfo = writeAccounts(normUser.getAccounts());
                user.put("type", "normal");
                user.put("Accounts", accountInfo);
            }
            //else if they are an admin
            else{
                user.put("type", "admin");
            }

            //add it
            users.add(user);
        }
        storage.put("users", users);

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
            account.put("balance", curr.getBalance());
            if(curr.getType() == AccountType.CHECKING){
                account.put("type", "Checking");
            }
            else if(curr.getType() == AccountType.SAVING){
                account.put("type", "Saving");
            }
            else if(curr.getType() == AccountType.INVESTMENT){
                account.put("type", "Investment");
            }
            accountInfo.add(account);
        }
        return accountInfo;
    }

    //method to read the info from the file and set the object accordingly
    public void readStorage(){
        //reset the current storage info
        um = new UserManagement(bankPointer);

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
                        //TODO add to the BASEBANK account variable
                        JSONObject account = (JSONObject) accounts.get(x);
                        String accType = (String) account.get("type");
                        int id = ((Number) account.get("id")).intValue();
                        int balance = ((Number) account.get("balance")).intValue();
                        Account acc;
                        if(type.equals("Checking")){
                            acc = new CheckingAccount(id, username);
                        }
                        else if(type.equals("Saving")){
                            acc = new SavingAccount(id, username);
                        }
                        else{
                            acc = new InvestmentAccount(id, username);
                        }
                        acc.addMoney(balance);
                        user.addAccount(acc);
                        bankPointer.addAccount(acc);
                    }
                    //add the user
                    um.addUser(user);
                }
                //TODO fix bankmanager addition
                else{
                    String username = (String) currUser.get("username");
                    String password = (String) currUser.get("password");
                    BankManager bm = new BankManager(username, password, bankPointer);
                    um.addUser(bm);
                }
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
}
