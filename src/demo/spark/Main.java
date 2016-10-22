package demo.spark;

import static spark.Spark.*;

import com.google.gson.Gson;
import org.omg.CORBA.Request;
import org.tmatesoft.sqljet.core.SqlJetException;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;
import java.sql.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Main {

    public static void main(String[] args) throws SqlJetException {
        port(5050);

        // Database database = new Database();
        //  database.createDatabase("data.db");
        // File dbFile = new File("data.db");
        // SqlJetDb db = SqlJetDb.open(dbFile, true);
        //     database.createTable("User", db);
        //  ISqlJetTable table = db.getTable("User");
        // database.insertTable(table, db);
        //    database.queryTable(table, db);
           Map<String, String> map = new HashMap<>();
          map.put("message", "Hello x!");

      /*  Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String ed = "3ed";
            stmt = c.createStatement();
            PreparedStatement st = c.prepareStatement("update user set email = ? where name = ?");
            st.setString(1, ed);
            st.setString(2, "xx");
            st.executeUpdate();
            c.commit();



          //  ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
         //   while ( rs.next() ) {
               // int id = rs.getInt("id");
               // String  name = rs.getString("name");
              //  int age  = rs.getInt("age");
              //  String  address = rs.getString("address");
             //   float salary = rs.getFloat("salary");
               // System.out.println( "ID = " + id );
               // System.out.println( "NAME = " + name );
               // System.out.println( "AGE = " + age );
               // System.out.println( "ADDRESS = " + address );
               // System.out.println( "SALARY = " + salary );
               // System.out.println();
           // }
          //  rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
          //  Connection c = null;
           // try {
         //       Class.forName("org.sqlite.JDBC");
           //     c = DriverManager.getConnection("jdbc:sqlite:test.db");
           // } catch ( Exception e ) {
             //   System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               // System.exit(0);
           // }
           // System.out.println("Opened database successfully");

*/

        User user = new User("no","no","no");
        Availatime availatime = new Availatime("no","no","no");
        // The hello.jade template file is in the resources/templates directory

        get("/hello", (rq, rs) -> new ModelAndView(map, "hello"), new JadeTemplateEngine());

        post("/hp", (rq, rs) -> {
            String[] body = rq.body().split("&");
            System.out.println(body[1]);
            user.setUser(body[0],body[1],body[2]);
            System.out.println(user.getName()+" "+ user.getPasswd()+" "+user.getEmail());
            rs.redirect("/addAvailatime");
            return  null;
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