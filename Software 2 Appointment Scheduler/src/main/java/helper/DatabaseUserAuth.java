package helper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseUserAuth {
    public static int login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ? AND PASSWORD = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();
        if (rs.next() == true)
        {
            return 1;
        }
        else{
            return 0;
        }

    }

}
