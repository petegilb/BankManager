import org.json.simple.*;
//we will use json-simple for the json files
//i have included the library in the org folder

//this class is the basic class for storage within a json file
public class Storage{
    protected JSONObject storage;
    public Storage(){
        storage = new JSONObject();
    }

}
