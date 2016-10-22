package demo.spark;

import javax.jws.soap.SOAPBinding;

/**
 * Created by xx on 10/19/16.
 */
public class Operation {
    private Availatime availatime;
    private User user;

    public Operation(Availatime availatime, User user){
        this.user = user;
        this.availatime = availatime;
    }

    public User getUser(){
        return user;
    }

    public Availatime getAvailatime(){
        return availatime;
    }

    public void addAvailatime(){

    }

   // public void isPasswdCorrrect(String passwd){
     //   if(user)
   // }



}
