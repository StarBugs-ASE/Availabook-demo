package demo.spark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xx on 10/12/16.
 */
public class User {
    private String name;
    private String passwd;
    private String email;
    private Availatime availatime;
   // private List<String> friendList;

    public User(String name, String passwd, String email){
        this.name = name;
        this.passwd = passwd;
        this.email = email;
      //  this.friendList = new ArrayList<String>();
    }
    public void setUser(String name, String passwd, String email){
        this.name = name;
        this.passwd = passwd;
        this.email = email;
    }

    public void setAvailatime(Availatime availatime){
        this.availatime = availatime;
    }
    public void deleteAvailatime(Availatime availatime){
        this.availatime = null;
    }

  //  public void addFriend(String friendName){

    //}


    public String getName(){
        return name;
    }
    public String getPasswd(){
        return passwd;
    }
    public String getEmail() {
        return email;
    }

  //  public List<String> getFriendList() {return friendList;}
}
