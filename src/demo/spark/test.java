package demo.spark;

import static spark.Spark.*;

import com.google.gson.Gson;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.omg.CORBA.Request;
import org.tmatesoft.sqljet.core.SqlJetException;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.template.jade.JadeTemplateEngine;
import java.sql.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class test {

    public static void main(String[] args) throws SqlJetException, SQLException {
        port(5050);


        Map<String, String> map = new HashMap<>();
        map.put("message", "Hello!");

        SQLiteJDBC2 sqlitemethod2 = new SQLiteJDBC2();

        User user = new User("no","no","no");

        Availatime availatime = new Availatime("no","no","no");
        // The hello.jade template file is in the resources/templates directory

        get("/hello", (rq, rs) -> new ModelAndView(map, "hello"), new JadeTemplateEngine());

        get("/SignUp", (rq, rs) -> new ModelAndView(map, "SignUp"), new JadeTemplateEngine());

        post("/CreateUser", (rq, rs) -> {
            QueryParamsMap body = rq.queryMap();
            String name3 = body.get("name").value();
            String passwd3 = body.get("password").value();
            String email3 = body.get("email").value();
            System.out.println(name3);
            System.out.println(passwd3);
            System.out.println(email3);
            user.setUser(name3,passwd3,email3);
            System.out.println(user.getName()+" "+ user.getPasswd()+" "+user.getEmail());
            SQLiteJDBC2 sqliteSignUp = new SQLiteJDBC2();
            sqliteSignUp.SignUp(name3,passwd3,email3);

            rs.redirect("/hello");
            return null;
        });


        post("/hp", (rq, rs) -> {
            QueryParamsMap body = rq.queryMap();
            String email2 = body.get("email").value();
            System.out.println(email2);
            String name2 = body.get("username").value();
            String passwd2 = body.get("password").value();
            System.out.println(name2);
            System.out.println(passwd2);
            System.out.println(email2);
            user.setUser(name2,passwd2,email2);

            String passwd4 = sqlitemethod2.PasswdQuery(body.get("username").value());
            System.out.print(passwd4);


            System.out.println(user.getName()+" "+ user.getPasswd()+" "+user.getEmail());
            rs.redirect("/addAvailatime");
            if(user.getPasswd().equals(passwd4)){
                System.out.println(passwd4);

                System.out.println("right");
            }
            else{
                System.out.println(passwd4);
                System.out.println(user.getPasswd());
                System.out.println("wrong");
            }
            return  null;
        });


        before("/addAvailatime", (rq, rs) -> {
            String name = user.getName();
            String passwd = sqlitemethod2.PasswdQuery(name);
            if(!user.getPasswd().equals(passwd)){
                rs.redirect("/hello");
            }

        });

        get("/addAvailatime",(rq, rs) -> new ModelAndView(map, "addAvailatime"), new JadeTemplateEngine());



        get("/availatime",(rq, rs) -> new ModelAndView(map, "availatime"), new JadeTemplateEngine());

        post("/new", (rq, rs) -> {
            String[] body = rq.body().split("&");
            System.out.println(body[1]);
            availatime.setAvailatime(body[0],body[1],body[2]);
            map.put("message2", body[0] + body[1] + body[2]);
            return map.get("message2");
        });
    }
}