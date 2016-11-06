package demo.spark;

import static spark.Spark.*;


import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.template.jade.JadeTemplateEngine;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        port(5050);


        Map<String, String> map = new HashMap<>();
        map.put("message", "Hello!");

        Database sqlitemethod2 = new Database();
        sqlitemethod2.openDatabase();
        Connection c = sqlitemethod2.c;

        HashMap<String, User> usermap = new HashMap<>();

        User user = new User("no", "no", "no");

        // The hello.jade template file is in the resources/templates directory

        get("/hello", (rq, rs) -> new ModelAndView(map, "hello"), new JadeTemplateEngine());

        get("/SignUp", (rq, rs) -> new ModelAndView(map, "SignUp"), new JadeTemplateEngine());

        post("/CreateUser", (rq, rs) -> {
            QueryParamsMap body = rq.queryMap();
            String signUpInputName = body.get("name").value();
            String signUpInputPassword = body.get("password").value();
            String signUpInputEmail = body.get("email").value();
            System.out.println(signUpInputName);
            System.out.println(signUpInputPassword);
            System.out.println(signUpInputEmail);
            sqlitemethod2.signUp(c, signUpInputName, signUpInputPassword, signUpInputEmail);
            rs.redirect("/hello");
            return null;
        });


        post("/hp", (rq, rs) -> {
            QueryParamsMap body = rq.queryMap();
            String loginInputEmail = body.get("email").value();
            String loginInputName = body.get("username").value();
            String loginInputPassword = body.get("password").value();

            usermap.put(user.getName(),user);
            for(String key : usermap.keySet()) {
                System.out.println("LoginUser " + key);
            } //output login users
            String passwdInDB = sqlitemethod2.passwdQuery(c,body.get("username").value());

            rs.redirect("/addAvailatime");
            String encryptedInputPasswd = sqlitemethod2.encryptedPasswd(loginInputPassword);
            user.setUser(loginInputName, encryptedInputPasswd, loginInputEmail);
            if (encryptedInputPasswd.equals(passwdInDB)) {
                System.out.println(passwdInDB);

                System.out.println("right");
            } else {
                System.out.println(passwdInDB);
                System.out.println(encryptedInputPasswd);
                System.out.println("wrong");
            }
            return null;
        });


        before("/addAvailatime", (rq, rs) -> {
            String name = user.getName();
            String passwd = sqlitemethod2.passwdQuery(c,name);
            if (!user.getPasswd().equals(passwd)) {
                rs.redirect("/hello");
            }

        });

        get("/addAvailatime", (rq, rs) -> new ModelAndView(map, "addAvailatime"), new JadeTemplateEngine());

        get("/friend", (rq, rs) -> new ModelAndView(map, "friend"), new JadeTemplateEngine());

        get("/availatime", (rq, rs) -> new ModelAndView(map, "availatime"), new JadeTemplateEngine());

        post("/new", (rq, rs) -> {
            QueryParamsMap body = rq.queryMap();
            String date = body.get("date").value();
            String start = body.get("start").value();
            String end = body.get("end").value();
            String tendency = body.get("tendency").value();
            sqlitemethod2.addAvailatime(c, date, start, end, tendency,user.getName());

            ArrayList<Availatime> availatimeList = sqlitemethod2.availaTimeQuery(c);
            map.put("message2","availatimeList"+"\n");

            ArrayList<Friendship> friendshipList = sqlitemethod2.friendshipQuery(c);

            for(int i=0; i<availatimeList.size();i++){
                Availatime availatime = availatimeList.get(i);
                int userID1 = sqlitemethod2.IDQuery(c, user.getName());
                int userID2 = sqlitemethod2.IDQuery(c, availatime.getUserName());
                boolean isFriend = false;
                for(int j=0; j<friendshipList.size();j++){
                    isFriend = isFriend || friendshipList.get(j).isFriendOrNot(userID1,userID2);
                }
                if(isFriend) {
                    String newAvailatime = map.get("message2") + availatime.getUserName() + ": " + availatime.getDate() + " " + availatime.getStartTime() + " " + availatime.getEndTime() + " " + availatime.getTendency() + "\n";
                    map.put("message2", newAvailatime);
                }
                System.out.println(availatime.getUserName()+": " + availatime.getDate()+" "+availatime.getStartTime()+" "+availatime.getEndTime()+" "+availatime.getTendency()+"\n");
            }
            return map.get("message2");
        });

        post("/success", (rq, rs) -> {
            QueryParamsMap body = rq.queryMap();
            String friendName = body.get("name").value();
            int UserID1 = sqlitemethod2.IDQuery(c, user.getName());
            int UserID2 = sqlitemethod2.IDQuery(c, friendName);
            if (UserID1!=UserID2 && UserID1!=0 && UserID2 !=0) {
                sqlitemethod2.addFriend(c, UserID1, UserID2);
                return "success";
            }
            else return "fail";
        });
    }
}