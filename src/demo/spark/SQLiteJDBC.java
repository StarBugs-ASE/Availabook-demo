package demo.spark;
import java.sql.*;

/**
 * Created by xx on 10/21/16.
 */
public class SQLiteJDBC {
    public String PasswdQuery() throws SQLException {
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            PreparedStatement st = c.prepareStatement("SELECT password FROM User where name=?");

            String ed = "xx";

            st.setString(1, ed);

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
