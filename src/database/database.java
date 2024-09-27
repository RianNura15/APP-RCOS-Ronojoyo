package database;
import java.sql.Connection;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private static Connection koneksi;
    
    public static Connection GetConnection() throws SQLException{
        if(koneksi==null){
            new Driver();
            koneksi=DriverManager.getConnection("jdbc:mysql://localhost:3306/proyek", "root", "");
        }
        return koneksi;
}

    public static Connection getKoneksi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
