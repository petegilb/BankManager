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
    //stocks to be added later

    //constructor
    public BankStorage(BaseBank bank){
        um = new UserManagement(bank);

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
            /*if(hashUser.getType() == UserType.NORMAL){
                hashUser = (NormalUser) hashUser;
                //fill out their account info
                JSONArray accountInfo = writeAccounts(hashUser.getAccounts());
            }*/
            //else if they are an admin
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

    public JSONArray writeAccounts(List<Account> accounts){
        JSONArray accountInfo = new JSONArray();
        return null;
    }

    //method to read the info from the file and set the object accordingly
    public void readStorage(){

    }

    //getters/setters
    public UserManagement getUM(){
        return um;
    }

    public void setUM(UserManagement um){
        this.um = um;
    }
}
