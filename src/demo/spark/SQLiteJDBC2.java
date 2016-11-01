package demo.spark;
import java.sql.*;


public class SQLiteJDBC2
{
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test2.db");
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
    }

    public static void SignUp(String name, String passwd, String email) {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test2.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO USER (NAME,PASSWORD,EMAIL) " +
                    "VALUES ( '" + name + "','" + passwd + "','" + email + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }


    public String PasswdQuery(String name) throws SQLException {
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test2.db");
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
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        c.close();
        return null;

    }
}