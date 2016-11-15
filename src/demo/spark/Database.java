package demo.spark;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.security.MessageDigest;



public class Database{
   /* public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE USER " +
                    "(ID INTEGER PRIMARY KEY      NOT NULL," + //Auto Increment
                    " NAME           TEXT     NOT NULL UNIQUE, " +
                    " PASSWORD       CHAR(50) NOT NULL, " +
                    " EMAIL          CHAR(50) NOT NULL, " +
                    " AVAILATIME     TEXT)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }*/


    public Connection c = null;

    public void openDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("opened database successfully");
    }


    public String encryptedPasswd(String password){
        String generatedPassword = null;

        // Create MessageDigest instance for MD5
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");

            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;


    }


    public boolean isValidEmailAddress(String email) {
        String ePattern =
                "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                        "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public boolean isValidName(String name) {
        String ePattern =
                "[a-z|A-Z]+";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(name);
        return m.matches();
    }

    public String signUp(Connection c, String name, String passwd, String email) {


        Statement stmt = null;
        if (isValidEmailAddress(email)&& isValidName(name)) {
            try {

                c.setAutoCommit(false);

                stmt = c.createStatement();

                String sql = "INSERT INTO USER (NAME,PASSWORD,EMAIL) " +
                        "VALUES ( '" + name + "','" + encryptedPasswd(passwd) + "','" + email + "');";
                stmt.executeUpdate(sql);
                stmt.close();
                c.commit();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
            System.out.println("Records created successfully");
            return "Success";
        }
        else if (!isValidEmailAddress(email)){
            System.out.println("Wrong Email Address");
            return "Email";

        } else if (!isValidName(name)){
            System.out.println("Wrong Name Input");
            return "Name";
        } else return "fail";
    }

    public void addAvailatime(Connection c, String date, String start, String end, String tendency, String userName) {


        Statement stmt = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO AVAILATIME (date,startTime,endTime,tendency,userName) " +
                    "VALUES ( '" + date + "','" + start + "','" + end + "','"+tendency+"','"+userName+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public void addFriend(Connection c, int UserID1, int UserID2){
        Statement stmt = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO FRIENDSHIP (UserID1,UserID2) " +
                    "VALUES ('"+UserID1+"','"+UserID2+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public int IDQuery(Connection c, String name) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();

            PreparedStatement st = c.prepareStatement("SELECT ID FROM User where name=?");

            st.setString(1, name);

            rs = st.executeQuery();
            System.out.println(rs.getInt("ID"));
            c.commit();
            return rs.getInt("ID");

        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("no such ID");
        return 0; //0 means that we can't find the user in database

    }

    public String passwdQuery(Connection c, String name) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();

            PreparedStatement st = c.prepareStatement("SELECT password FROM User where name=?");

            st.setString(1, name);

            rs = st.executeQuery();
            System.out.println(rs.getString("password"));
            c.commit();
            System.out.println(rs);
            stmt.close();
            return rs.getString("password");


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("passwordQuery failed");
        return null;
    }
    public ArrayList<Availatime> availaTimeQuery(Connection c) throws SQLException {
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AVAILATIME;");
            ArrayList<Availatime> availatimeList = new ArrayList<>();
            while (rs.next()) {
                Availatime availatime = new Availatime(rs.getString("date"), rs.getString("startTime"),
                        rs.getString("endTime"), rs.getString("tendency"), rs.getString("userName"));
                availatimeList.add(availatime);
            }
            rs.close();
            stmt.close();
            return availatimeList;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("AvailatimeQuery failed");
        return null;
    }
    public ArrayList<Friendship> friendshipQuery(Connection c) throws SQLException{
        Statement stmt = null;
        try{
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM FRIENDSHIP;");
            ArrayList<Friendship> friendshipList = new ArrayList<>();
            while(rs.next()){
                Friendship friendship = new Friendship(rs.getInt("UserID1"),rs.getInt("UserID2"));
                friendshipList.add(friendship);
            }
            rs.close();
            stmt.close();
            return friendshipList;
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("FriendshipQuery failed");
        return null;
    }

}