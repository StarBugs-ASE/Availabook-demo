package demo.spark;
import java.sql.*;


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


    public void signUp(Connection c, String name, String passwd, String email) {


        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO USER (NAME,PASSWORD,EMAIL) " +
                    "VALUES ( '" + name + "','" + passwd + "','" + email + "');";
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
            System.out.println("Opened database successfully");

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
            System.out.println("Opened database successfully");

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
        System.out.println("Operation done successfully");
        return 0; //0 means that we can't find the user in database

        }




    public String passwdQuery(Connection c, String name) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

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
        System.out.println("Operation done successfully");
        return null;

    }
}